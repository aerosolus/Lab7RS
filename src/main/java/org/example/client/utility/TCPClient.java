package org.example.client.utility;

import lombok.Getter;
import org.example.contract.exceptions.FailedClosureException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * TCP-клиент для установки соединения с сервером и обмена данными.
 * <p>
 * Обеспечивает:
 * <ul>
 *   <li>Подключение к указанному хосту и порту</li>
 *   <li>Получение потоков ввода/вывода для обмена данными</li>
 *   <li>Безопасное закрытие соединения с обработкой ошибок</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Socket
 * @see org.example.contract.exceptions.FailedClosureException
 */
public class TCPClient {

    /**
     * Хост сервера для подключения.
     */
    private final String HOST;

    /**
     * Порт сервера для подключения.
     */
    private final int PORT;

    /**
     * Сокетное соединение с сервером.
     */
    private Socket socket;

    /**
     * Поток ввода для чтения данных от сервера.
     */
    @Getter
    private InputStream inputStream;

    /**
     * Поток вывода для отправки данных серверу.
     */
    @Getter
    private OutputStream outputStream;

    /**
     * Создает клиент с указанными параметрами подключения.
     *
     * @param host IP-адрес или доменное имя сервера
     * @param port порт сервера (0-65535)
     */
    public TCPClient(String host, int port){
        this.HOST = host;
        this.PORT = port;
    }

    /**
     * Запускает подключение к серверу.
     *
     * @throws IOException если произошла ошибка ввода-вывода при подключении
     */
    public void run() throws IOException {
        connect();
    }

    /**
     * Устанавливает соединение с сервером:
     * <ol>
     *   <li>Создает новый сокет</li>
     *   <li>Выполняет подключение к указанному адресу</li>
     *   <li>Инициализирует потоки ввода/вывода</li>
     * </ol>
     *
     * @throws IOException если не удалось установить соединение
     */
    private void connect() throws IOException {
        this.socket = new Socket();
        socket.connect(new InetSocketAddress(this.HOST, this.PORT));

        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    /**
     * Закрывает соединение с сервером.
     * <p>
     * Если сокет не был инициализирован, метод завершается без действий.
     *
     * @throws FailedClosureException если произошла ошибка при закрытии соединения
     */
    public void close() throws FailedClosureException {
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            throw new FailedClosureException();
        }
    }
}
