package org.example.client;

import org.example.client.utility.ClientApplicationContainer;
import org.example.client.utility.TCPClient;
import org.example.client.utility.TerminalManager;
import org.example.client.utility.Transmitter;
import org.example.contract.utility.BufferedLineReader;

import java.io.IOException;
import java.net.*;
import java.nio.channels.UnresolvedAddressException;

/**
 * Главный класс клиентского приложения, обеспечивающий:
 * <ul>
 *   <li>Инициализацию конфигурации соединения</li>
 *   <li>Управление сетевым подключением</li>
 *   <li>Запуск интерактивного терминала</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see TCPClient
 * @see TerminalManager
 */
public class Client {

    /**
     * Адрес сервера по умолчанию "127.0.0.1"
     */
    private static String SERVER_ADDRESS = "localhost";

    /**
     * Порт сервера по умолчанию
     */
    private static int SERVER_PORT = 32025;

    /**
     * Точка входа в клиентское приложение.
     * <p>
     * Основные действия:
     * <ol>
     *   <li>Инициализация контейнера приложения</li>
     *   <li>Конфигурирование сетевых параметров</li>
     *   <li>Установка соединения с сервером</li>
     *   <li>Запуск интерактивного режима</li>
     * </ol>
     * </p>
     *
     * @throws RuntimeException при критичных ошибках ввода-вывода
     */
    public static void main(String[] args) {
        initClientAppContainer();
        TCPClient tcpClient = new TCPClient(SERVER_ADDRESS, SERVER_PORT);
        Transmitter sender = new Transmitter(tcpClient);
        TerminalManager terminalManager = new TerminalManager(sender);

        int maxAttempts = 5;
        int attempt = 0;
        boolean connected = false;

        while (attempt < maxAttempts && !connected) {
            try {
                tcpClient.run();
                terminalManager.run();
            } catch (UnresolvedAddressException | UnknownHostException e) {
                System.err.println("Сервер с выбранным хостом недоступен. Измените конфигурацию клиента.");
                System.exit(1);
            } catch (ConnectException e) {
                attempt++;
                System.err.printf("Сервер недоступен! Попытка %d/%d%n", attempt, maxAttempts);
                reconnect(attempt, maxAttempts);
            } catch (SocketTimeoutException e) {
                attempt++;
                System.err.printf("Таймаут подключения. Попытка %d/%d%n", attempt, maxAttempts);
                reconnect(attempt, maxAttempts);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (!connected) {
            System.err.println("Не удалось подключиться к серверу после " + maxAttempts + " попыток.");
            System.exit(1);
        }
    }

    /**
     * Инициализирует контейнер клиентского приложения:
     * <ul>
     *   <li>Создает экземпляр {@link ClientApplicationContainer}</li>
     *   <li>Настраивает буферизированный читатель для System.in</li>
     * </ul>
     */
    public static void initClientAppContainer() {
        ClientApplicationContainer app = ClientApplicationContainer.getInstance();
        BufferedLineReader reader = new BufferedLineReader(System.in);
        app.setBufferedLineReader(reader);
    }

    /**
     * Метод повторного подключения к серверу. Ждет 5 секунд перед следующей попыткой подключения
     */
    private static void reconnect(int attempt, int maxAttempts) {
        if (attempt < maxAttempts) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ie) {
                System.err.println("Ожидание переподключения прервано.");
                System.exit(1);
            }
        }
    }
}
