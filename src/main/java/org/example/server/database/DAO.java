package org.example.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Абстрактный базовый класс для реализации Data Access Object (DAO).
 * <p>
 * Обеспечивает общую функциональность для работы с базой данных:
 * <ul>
 *   <li>Установка JDBC-соединения</li>
 *   <li>Централизованная обработка ошибок подключения</li>
 *   <li>Предоставление соединения наследующим классам</li>
 * </ul>
 *
 * <p>Наследование:</p>
 * Классы-потомки должны реализовывать специфичные для сущностей методы доступа к данным.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see java.sql.Connection
 */
public abstract class DAO {

    /**
     * JDBC-соединение с базой данных.
     * <p>
     * Доступно для наследников через механизм protected доступа.
     * </p>
     */
    protected Connection connection;

    /**
     * Инициализирует соединение с базой данных.
     * <p>
     * Алгоритм работы:
     * <ol>
     *   <li>Попытка подключения через {@link DriverManager}</li>
     *   <li>Вывод сообщения об успешном подключении</li>
     *   <li>При ошибке - вывод в stderr и аварийное завершение работы</li>
     * </ol>
     * </p>
     *
     * @param URL      JDBC URL БД
     * @param userName имя пользователя БД
     * @param password пароль пользователя БД
     */
    public DAO(String URL, String userName, String password) {

        final int MAX_ATTEMPTS = 5; // Количество попыток повторного подключения
        int attempts = 0;
        boolean connected = false;
        while (attempts < MAX_ATTEMPTS && !connected) {
            try {
                connection = DriverManager.getConnection(URL, userName, password);
                connected = true;
                System.out.println("Подключение к базе данных успешно выполнено.");
            } catch (SQLException e) {
                try {
                    Thread.sleep(5000); // Время ожидания между попытками повторного подключения
                } catch (InterruptedException ie) {
                    System.err.println("Ожидание переподключения прервано.");
                    System.exit(1);
                }
                attempts++;
                System.err.printf("БД недоступна! Попытка %d/%d%n", attempts, MAX_ATTEMPTS);
            }
        }
        if (!connected) {
            System.err.println("Не удалось подключиться к базе данных.");
            System.out.println("Работа программы завершена.");
            System.exit(1);
        }
    }
}
