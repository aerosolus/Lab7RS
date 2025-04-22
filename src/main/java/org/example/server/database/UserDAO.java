package org.example.server.database;

import org.example.contract.exceptions.IncorrectPasswordException;
import org.example.contract.exceptions.NoUserFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс UserDAO отвечает за взаимодействие с базой данных пользователей.
 * Он предоставляет методы для создания новых пользователей и проверки
 * существующих учетных записей.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see DAO
 * @see PasswordManager
 */
public class UserDAO extends DAO {

    /**
     * Менеджер паролей, используемый для хеширования паролей пользователей.
     */
    private final PasswordManager passwordManager = new PasswordManager();

    /**
     * Конструктор класса UserDAO.
     *
     * @param URL      URL базы данных.
     * @param userName Имя пользователя для подключения к базе данных.
     * @param password Пароль для подключения к базе данных.
     */
    public UserDAO(String URL, String userName, String password) {
        super(URL, userName, password);
    }

    /**
     * Проверяет, существует ли пользователь с заданным именем и паролем.
     *
     * <p>
     * Метод выполняет запрос к базе данных, чтобы найти пользователя по имени.
     * Если пользователь найден, проверяется правильность введенного пароля.
     * В случае неправильного пароля выбрасывается исключение
     * {@link IncorrectPasswordException}. Если пользователь не найден, выбрасывается
     * исключение {@link NoUserFoundException}.
     * </p>
     *
     * @param userName Имя пользователя, которого необходимо проверить.
     * @param password Пароль пользователя для проверки.
     * @throws SQLException Если возникает ошибка при взаимодействии с базой данных.
     * @throws IncorrectPasswordException Если введенный пароль неверен.
     * @throws NoUserFoundException Если пользователь с указанным именем не найден.
     */
    public void verifyUser(String userName, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?");
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String expectedPassword = resultSet.getString("password");
            if (!expectedPassword.equals(passwordManager.hashPassword(password))) {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new NoUserFoundException();
        }
    }

    /**
     * Создает нового пользователя в базе данных.
     *
     * <p>
     * Метод добавляет нового пользователя с указанным именем и паролем в таблицу
     * пользователей. Пароль перед сохранением хэшируется с помощью {@link PasswordManager}.
     * </p>
     *
     * @param userName Имя пользователя, которое будет добавлено в базу данных.
     * @param password Пароль пользователя, который будет хэширован и сохранен.
     * @throws SQLException Если возникает ошибка при взаимодействии с базой данных.
     */
    public void createUser(String userName, String password) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement(
                "INSERT INTO users " + "(username, password) VALUES (?,?)");
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, passwordManager.hashPassword(password));
        preparedStatement.executeUpdate();
    }
}
