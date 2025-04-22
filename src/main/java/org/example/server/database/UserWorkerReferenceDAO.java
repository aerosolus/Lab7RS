package org.example.server.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Класс отвечает за взаимодействие с БД для управления связями между работниками и пользователями.
 *
 * <p>
 * Он предоставляет методы для добавления отношений, проверки владельца
 * и получения списка работников для заданного пользователя.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class UserWorkerReferenceDAO extends DAO {

    /**
     * Конструктор класса.
     *
     * @param URL      URL базы данных.
     * @param userName Имя пользователя для подключения к базе данных.
     * @param password Пароль для подключения к базе данных.
     */
    public UserWorkerReferenceDAO(String URL, String userName, String password) {
        super(URL, userName, password);
    }

    /**
     * Добавляет отношение между работником и пользователем в базу данных.
     *
     * <p>
     * Метод сохраняет информацию о том, что указанный пользователь является
     * владельцем работника с заданным идентификатором.
     * </p>
     *
     * @param id         Идентификатор работника, который будет связан с пользователем.
     * @param ownerLogin Логин пользователя, который будет связан с работником.
     * @throws SQLException Если возникает ошибка при взаимодействии с БД.
     */
    public void addRelationship(Long id, String ownerLogin) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO worker_user (" + "worker_id, user_login) VALUES (?,?) ");
        ps.setLong(1,id);
        ps.setString(2,ownerLogin);

        ps.executeUpdate();
    }

    /**
     * Проверяет, является ли указанный пользователь владельцем работника.
     *
     * <p>
     * Метод выполняет запрос к БД и сравнивает логин пользователя
     * с логином, хранящимся в базе данных для указанного работника.
     * </p>
     *
     * @param id         Идентификатор работника, чье владение нужно проверить.
     * @param ownerLogin Логин пользователя, который необходимо проверить.
     * @return true, если пользователь является владельцем работника; false в противном случае.
     * @throws SQLException Если возникает ошибка при взаимодействии с БД.
     */
    public boolean isOwner(Long id, String ownerLogin) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT user_login FROM worker_user WHERE worker_id = ?");
        ps.setLong(1, id);
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            String realOwner = resultSet.getString("user_login");
            return realOwner.equals(ownerLogin);
        }
        return false;
    }

    /**
     * Получает список идентификаторов работников, связанных с указанным пользователем.
     *
     * <p>
     * Метод выполняет запрос к базе данных и возвращает список идентификаторов
     * работников, которые принадлежат пользователю с заданным логином.
     * </p>
     *
     * @param ownerLogin Логин пользователя, для которого нужно получить список работников.
     * @return Список идентификаторов работников, связанных с указанным пользователем.
     * @throws SQLException Если возникает ошибка при взаимодействии с БД.
     */
    public ArrayList<Long> getUserWorkers(String ownerLogin) throws SQLException {
        ArrayList<Long> ids = new ArrayList<>();
        PreparedStatement ps = this.connection.prepareStatement(
                "SELECT worker_id FROM worker_user WHERE user_login = ?");
        ps.setString(1,ownerLogin);
        ResultSet resultSet = ps.executeQuery();
        while(resultSet.next()){
            ids.add(resultSet.getLong("worker_id"));
        }
        return ids;
    }
}
