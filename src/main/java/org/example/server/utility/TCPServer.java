package org.example.server.utility;

import org.apache.logging.log4j.Logger;
import org.example.contract.responses.Response;
import org.example.contract.utility.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * NIO TCP-сервер для обработки клиентских подключений и выполнения команд.
 * <p>
 * Использует неблокирующий ввод-вывод с селекторами для мультиплексирования соединений.
 * Поддерживает асинхронную обработку запросов и отправку ответов через пулы потоков.
 * </p>
 *
 * <p>Архитектура:</p>
 * <ul>
 *   <li>Основной цикл обработки событий в методе {@link #run()}</li>
 *   <li>Разделение потоков для чтения/записи с использованием {@link ForkJoinPool} и {@link ExecutorService}</li>
 *   <li>Синхронизация доступа с помощью {@link ReentrantReadWriteLock}</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class TCPServer {

    /**
     * Размер буфера (в байтах) для операций ввода-вывода.
     * Значение по умолчанию: 4096 байт.
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Буфер для временного хранения данных при работе с каналами.
     * Инициализируется с размером {@link #BUFFER_SIZE} при создании сервера.
     */
    private ByteBuffer buffer;

    /**
     * Хост сервера по умолчанию.
     * Может быть изменен через сеттер перед запуском сервера.
     */
    private static String HOST = "localhost";

    /**
     * Порт сервера по умолчанию. Может быть изменен перед запуском сервера.
     */
    private static int PORT = 32025;

    /**
     * Основной канал сервера для приема входящих подключений.
     * Конфигурируется в неблокирующем режиме.
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * Селектор для управления событиями ввода-вывода.
     * Отслеживает состояния подключений, чтения и записи.
     */
    private Selector selector;

    /**
     * Менеджер команд для выполнения клиентских запросов.
     * Инициализируется через конструктор.
     */
    private CommandManager commandManager;

    /**
     * Обработчик входящих запросов.
     * Преобразует сырые данные в команды и формирует ответы.
     */
    private RequestHandler requestHandler;

    /**
     * Логгер для записи системных событий.
     * Использует реализацию Log4j.
     */
    private Logger logger;

    /**
     * Пул потоков для обработки запросов.
     * Использует общий пул ForkJoinPool для параллельной обработки.
     */
    private final ForkJoinPool requestProcessPool = ForkJoinPool.commonPool();

    /**
     * Пул потоков для записи ответов.
     * Фиксированный пул на 10 потоков для асинхронной отправки данных.
     */
    private final ExecutorService responseWritePool = Executors.newFixedThreadPool(10);

    /**
     * Блокировка для синхронизации доступа к ресурсам.
     * Обеспечивает безопасность при конкурентном доступе.
     */
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * Конструктор сервера.
     *
     * @param commandManager менеджер команд для обработки запросов
     * @param requestHandler обработчик входящих данных
     * @param logger логгер для записи событий
     */
    public TCPServer(CommandManager commandManager, RequestHandler requestHandler, Logger logger) {
        this.commandManager = commandManager;
        this.requestHandler = requestHandler;
        this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.logger = logger;
    }

    /**
     * Инициализирует серверное соединение.
     * <p>
     * Выполняет:
     * </p>
     * <ul>
     *   <li>Открытие серверного канала</li>
     *   <li>Настройку неблокирующего режима</li>
     *   <li>Привязку к указанному адресу и порту</li>
     *   <li>Инициализацию селектора</li>
     * </ul>
     * </p>
     *
     * @throws IOException при ошибках настройки сети
     */
    public void openConnection() throws IOException {
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(HOST, PORT);
        this.serverSocketChannel.bind(inetSocketAddress);
        this.selector = initSelector();
    }

    /**
     * Запускает основной цикл обработки событий сервера.
     * <p>
     * Алгоритм работы:
     * </p>
     * <ol>
     *   <li>Ожидает новых событий через {@link Selector#select()}</li>
     *   <li>Итерирует по ключам событий</li>
     *   <li>Обрабатывает каждое событие в {@link #handleKey(SelectionKey)}</li>
     * </ol>
     * </p>
     *
     * @throws RuntimeException при критических ошибках ввода-вывода
     */
    public void run() {
        try {
            while (true) {
                selector.select();
                Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
                while (selectedKeys.hasNext()) {
                    SelectionKey key = takeKey(selectedKeys);
                    handleKey(key);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Извлекает следующий ключ из итератора и удаляет его из коллекции.
     *
     * @param selectionKeyIterator Итератор для перебора ключей выбора.
     * @return Извлеченный объект SelectionKey.
     */
    private SelectionKey takeKey(Iterator<SelectionKey> selectionKeyIterator) {
        SelectionKey key = selectionKeyIterator.next();
        selectionKeyIterator.remove();
        return key;
    }

    /**
     * Инициализирует селектор для обработки неблокирующих сокетов.
     *
     * @return Инициализированный объект Selector.
     * @throws IOException Если не удалось открыть селектор.
     */
    private Selector initSelector() throws IOException {
        Selector socketSelector = SelectorProvider.provider().openSelector();
        this.serverSocketChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
        return socketSelector;
    }

    /**
     * Обрабатывает событие селектора.
     *
     * @param key ключ события из селектора
     * @throws IOException при ошибках обработки каналов
     */
    private void handleKey(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                accept(key);
            } else if (key.isReadable()) {
                new Thread(() -> {
                    try {
                        read(key);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } else if (key.isWritable()) {
                write(key);
            }
        }
    }

    /**
     * Принимает новое подключение.
     *
     * @param key ключ события принятия подключения
     * @throws IOException при ошибках принятия соединения
     */
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = ssc.accept();
        socketChannel.configureBlocking(false);
        System.out.println("Подключено: " + socketChannel.getRemoteAddress());
        logger.info("Подключено: {}", socketChannel.getRemoteAddress());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Выполняет чтение данных из канала.
     *
     * @param key ключ события чтения
     * @throws IOException при ошибках ввода-вывода
     */
    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer localBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        int bytesRead;
        try {
            bytesRead = socketChannel.read(localBuffer);
        } catch (IOException e) {
            key.cancel();
            socketChannel.close();
            return;
        }

        if (bytesRead == -1) {
            key.cancel();
            return;
        }
        localBuffer.flip();

        requestProcessPool.submit(() -> {
            readWriteLock.readLock().lock();
            try {
                Response response = requestHandler.handleRequest(localBuffer);
                System.out.println(response);
                logger.info("Отправлено: {}", response.toString());
                socketChannel.register(selector, SelectionKey.OP_WRITE, response);
                selector.wakeup();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
            }
        });
    }

    /**
     * Записывает ответ в сокетный канал, асинхронно выполняя операцию записи.
     *
     * @param key Ключ выбора, связанный с сокетным каналом и ответом.
     * @throws IOException Если произошла ошибка при сериализации объекта ответа или записи в канал.
     */
    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        Response response = (Response) key.attachment();
        responseWritePool.execute(() -> {
            ByteBuffer writeBuffer = null;
            try {
                writeBuffer = Serializer.serializeObject(response);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeBuffer.flip();
            while (writeBuffer.hasRemaining()) {
                try {
                    socketChannel.write(writeBuffer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * Закрывает сервер и освобождает ресурсы.
     *
     * @throws IOException при ошибках закрытия каналов
     */
    public void close() throws IOException {
        if (serverSocketChannel != null) {
            serverSocketChannel.close();
        }
        requestProcessPool.shutdown();
        responseWritePool.shutdown();
    }
}
