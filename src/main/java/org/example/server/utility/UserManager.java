package org.example.server.utility;

import lombok.Setter;
import org.example.server.database.UserDAO;

import java.sql.SQLException;

/**
 * Класс UserManager отвечает за управление пользователями в системе.
 * Он предоставляет методы для регистрации и аутентификации пользователей,
 * взаимодействуя с базой данных через объект {@link UserDAO}.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Setter
public class UserManager {

    /**
     * Объект UserDAO, используемый для взаимодействия с базой данных пользователей.
     */
    private UserDAO userDAO;

    /**
     * Регистрация нового пользователя в системе.
     * <p>
     * Этот метод создает нового пользователя с заданным именем и паролем.
     * Если пользователь успешно добавлен в базу данных, выводится сообщение об успехе.
     * </p>
     *
     * @param userName Имя пользователя, которое будет использоваться для входа в систему.
     * @param password Пароль пользователя, который будет храниться в базе данных.
     * @throws SQLException Если произошла ошибка при взаимодействии с базой данных,
     *                      например, если имя пользователя уже существует или
     *                      возникли проблемы с подключением к базе данных.
     */
    public void signUp(String userName, String password) throws SQLException {
        try {
            this.userDAO.createUser(userName, password);
            System.out.println("Пользователь " + userName +" был успешно добавлен в базу данных.");
        } catch (SQLException e) {
            System.err.println("Не удалось добавить пользователя. Проверьте конфигурацию БД.");
            throw new SQLException();
        }
    }

    /**
     * Аутентификация пользователя в системе.
     *<p>
     * Этот метод проверяет учетные данные пользователя (имя и пароль).
     * Если пользователь успешно верифицирован, выводится сообщение об успехе.
     *</p>
     *
     * @param userName Имя пользователя, которое будет проверяться.
     * @param password Пароль пользователя, который будет проверяться.
     * @throws SQLException Если произошла ошибка при взаимодействии с базой данных,
     *                      например, если имя пользователя не найдено или пароль неверен,
     *                      или возникли проблемы с подключением к базе данных.
     */
    public void signIn(String userName, String password) throws SQLException {
        try {
            this.userDAO.verifyUser(userName, password);
            System.out.println("Пользователь " + userName + " был успешно верифицирован.");
        } catch (SQLException e) {
            System.err.println("Не удалось верифицировать пользователя. Проверьте конфигурацию БД.");
            throw new SQLException();
        }
    }
}
