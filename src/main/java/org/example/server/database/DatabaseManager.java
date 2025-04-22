package org.example.server.database;

/**
 * Реализация интерфейса {@link Database} для управления подключениями к различным сущностям БД.
 * <p>
 * Выступает фабрикой DAO-объектов, используя общие параметры подключения для всех сущностей.
 * </p>
 *
 * <p>Особенности реализации:</p>
 * <ul>
 *   <li>Единые учетные данные для всех типов подключений</li>
 *   <li>Явное разделение подключений по типам сущностей</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class DatabaseManager implements Database {

    /**
     * JDBC URL базы данных в формате: "jdbc:subprotocol:subname"
     */
    private final String URL;

    /**
     * Имя пользователя для аутентификации в СУБД
     */
    private final String userName;

    /**
     * Пароль пользователя для аутентификации
     */
    private final String password;

    /**
     * Инициализирует менеджер с параметрами подключения.
     *
     * @param URL      JDBC URL
     * @param userName логин администратора БД
     * @param password пароль администратора
     */
    public DatabaseManager(String URL, String userName, String password) {
        this.URL = URL;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Создает DAO для работы с сущностями работников.
     *
     * @return новый экземпляр {@link WorkerDAO}
     */
    @Override
    public DAO createWorkersConnection() {
        System.out.println("Подключение к БД коллекции...");
        return new WorkerDAO(URL, userName, password );
    }

    /**
     * Создает DAO для работы с пользователями.
     *
     * @return новый экземпляр {@link UserDAO}
     */
    @Override
    public DAO createUserConnection() {
        System.out.println("Подключение к БД пользователей...");
        return new UserDAO(URL, userName, password);
    }

    /**
     * Создает DAO для управления связями пользователь-работник.
     *
     * @return новый экземпляр {@link UserWorkerReferenceDAO}
     */
    @Override
    public DAO createUserWorkerReferenceConnection() {
        System.out.println("Подключение к БД пользователей и работников...");
        return new UserWorkerReferenceDAO(URL, userName, password);
    }
}
