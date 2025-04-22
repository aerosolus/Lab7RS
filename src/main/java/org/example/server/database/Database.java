package org.example.server.database;

/**
 * Фабрика для создания DAO-объектов, обеспечивающих доступ к различным сущностям базы данных.
 * <p>
 * Определяет контракт для получения реализаций Data Access Object, связанных с:
 * <ul>
 *   <li>Управлением сущностями {@link org.example.contract.data.Worker} (работники)</li>
 *   <li>Управлением сущностями {@link org.example.contract.data.User} (пользователи)</li>
 *   <li>Обработкой связей многие-ко-многим между пользователями и работниками</li>
 * </ul>
 *
 * <p>
 * Каждый вызов метода создает новый экземпляр DAO с отдельным JDBC-соединением.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see DAO Базовый класс для всех возвращаемых объектов
 */
public interface Database {

    /**
     * Создает DAO для работы с сущностями работников.
     *
     * @return объект {@link DAO}, специализированный для операций с {@link org.example.contract.data.Worker}
     */
    DAO createWorkersConnection();

    /**
     * Создает DAO для работы с сущностями пользователей.
     *
     * @return объект {@link DAO}, специализированный для операций с {@link org.example.contract.data.User}
     */
    DAO createUserConnection();

    /**
     * Создает DAO для управления связями пользователь-работник.
     *
     * @return объект {@link DAO} для работы с ассоциативными сущностями
     */
    DAO createUserWorkerReferenceConnection();
}
