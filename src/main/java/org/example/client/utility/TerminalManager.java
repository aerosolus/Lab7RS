package org.example.client.utility;

import lombok.Getter;
import lombok.Setter;
import org.example.contract.data.User;
import org.example.contract.exceptions.InvalidArgumentException;
import org.example.contract.utility.BufferedLineReader;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Менеджер терминала для обработки пользовательского ввода и управления взаимодействием с сервером.
 * <p>
 * Обеспечивает:
 * <ul>
 *   <li>Валидацию формата ввода</li>
 *   <li>Отправку команд через {@link Transmitter}</li>
 *   <li>Обработку и отображение ответов сервера</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Transmitter
 * @see ResponseHandler
 */
public class TerminalManager {

    /**
     * Трансмиттер - передатчик данных на сервер
     */
    private final Transmitter transmitter;

    /**
     * Статус авторизации: пользователь авторизован или нет
     */
    @Getter
    @Setter
    private static boolean authorized = false;

    /**
     * Пользователь, использующий клиентскую программу
     */
    @Getter
    @Setter
    private static User user;

    /**
     * Создает менеджер терминала с указанным трансмиттером.
     *
     * @param transmitter трансмиттер для отправки команд на сервер
     */
    public TerminalManager(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * Запускает интерактивный режим работы с терминалом.
     * <p>
     * Основные задачи:
     * <ol>
     *   <li>Выводит информационное сообщение</li>
     *   <li>Ожидает авторизацию пользователя</li>
     *   <li>Ожидает ввод пользователя</li>
     *   <li>Парсит введенную строку на команду и аргументы</li>
     *   <li>Отправляет команду на сервер</li>
     *   <li>Обрабатывает и выводит ответ</li>
     * </ol>
     */
    public void run() {
        ClientApplicationContainer app = ClientApplicationContainer.getInstance();
        BufferedLineReader bufferedLineReader = app.getBufferedLineReader();
        ResponseHandler responseHandler = new ResponseHandler();
        AuthorizationManager authorizationManager = new AuthorizationManager();
        System.out.println("Программа запущена, для регистрации введите 'sign_up', для входа введите 'sign_in'");
        System.out.print('>');
        while (!authorized) {
            while (!authorized && bufferedLineReader.hasNextLine()) {
                String aLine = bufferedLineReader.nextLine().trim();
                String[] prompt = aLine.trim().split("\\s+");
                while (prompt.length != 1) {
                    System.err.println("Пройдите процесс аутентификации!");
                    System.out.print('>');
                    aLine = bufferedLineReader.nextLine().trim();
                    prompt = aLine.trim().split("\\s+");
                }
                try {
                    System.out.println(responseHandler.handleResponse(transmitter.sendRequest(authorizationManager.authorize(prompt))));
                    System.out.print('>');
                } catch (NullPointerException e) {
                    System.err.println("Пройдите процесс аутентификации!");
                    System.out.print('>');
                } catch (NoSuchElementException e) {
                    System.err.println("Принудительное завершение работы программы.");
                    System.exit(1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Для получения списка команд введите 'help'");
        System.out.print('>');
        while (bufferedLineReader.hasNextLine()) {
            String line = bufferedLineReader.nextLine().trim();
            String[] prompt = line.trim().split("\\s+");
            while (prompt.length == 0 || prompt.length > 2) {
                System.err.println("Неверный формат ввода команды!");
                System.out.print('>');
                line = bufferedLineReader.nextLine().trim();
                prompt = line.trim().split("\\s+");
            }
            try {
                System.out.println(responseHandler.handleResponse(transmitter.sendRequest(CommandExtractor.getDTO(prompt))));
                System.out.print('>');
            } catch (NullPointerException e) {
                System.err.println("Команды " + prompt[0] + " не существует!");
                System.out.print('>');
            } catch (InvalidArgumentException e) {
                System.err.println("Введен некорректный аргумент для команды!");
                System.out.print('>');
            } catch (NumberFormatException e) {
                System.err.println("Неверный формат аргумента!");
                System.out.print('>');
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Принудительное завершение работы программы.");
        System.exit(1);
    }
}
