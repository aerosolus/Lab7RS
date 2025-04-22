package org.example.server.utility;

import org.example.server.Server;
import org.example.server.dataManaging.CollectionManager;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Терминал сервера для обработки команд на сервере.
 * <p>Потокобезопасный терминал, обрабатывающий команды администратора сервера.</p>
 *
 * <p><b>Поддерживаемые команды:</b>
 * <ul>
 *   <li><b>exit</b> - завершает работу сервера</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class ServerTerminal extends Thread {

    /**
     * Сканер для чтения пользовательского ввода.
     * <p>Общий для всех экземпляров класса.</p>
     */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Запускает терминал сервера в отдельном потоке.
     *
     * <p>Основной цикл обработки команд:</p>
     * <ol>
     *   <li>Чтение команды из консоли</li>
     *   <li>Обработка команды</li>
     *   <li>Вывод результата</li>
     * </ol>
     *
     * @throws NoSuchElementException если поток ввода закрыт принудительно
     */
    @Override
    public void run() {
        try {
            Server.LOGGER.info("Терминал сервера запущен.");
            System.out.println("Терминал сервера запущен. Допустима команда exit.");
            while (true) {
                String line = scanner.nextLine();
                if ("exit".equalsIgnoreCase(line)) {
                    Server.LOGGER.info("Работа сервера прекращена.");
                    System.out.println("Работа сервера прекращена.");
                    System.exit(0);
                } else {
                    System.err.println("Такой команды не существует. Напишите 'exit'.");
                }
            }
        } catch (NoSuchElementException e) {
            System.err.println("Принудительное завершение работы.");
            Server.LOGGER.info("Работа сервера завершена.");
            System.exit(1);
        }
    }
}
