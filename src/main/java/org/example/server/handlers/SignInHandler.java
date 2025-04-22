package org.example.server.handlers;

import org.example.contract.commands.SignInCommand;
import org.example.contract.exceptions.IncorrectPasswordException;
import org.example.contract.exceptions.NoUserFoundException;
import org.example.contract.responses.AuthorizationResponse;
import org.example.contract.responses.Response;
import org.example.contract.utility.StatusCode;
import org.example.server.utility.ServerApplicationContainer;

import java.sql.SQLException;

/**
 * Класс SignInHandler обрабатывает команды аутентификации пользователей.
 *
 * <p>
 * Он наследуется от CommandHandler и отвечает за выполнение логики
 * аутентификации, включая обработку различных исключений.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class SignInHandler extends CommandHandler <SignInCommand> {

    /**
     * Обрабатывает команду входа пользователя в систему.
     * <p>
     * Этот метод выполняет аутентификацию пользователя, используя
     * переданные в команде учетные данные (имя пользователя и пароль).
     * В случае успешной аутентификации возвращает объект AuthorizationResponse
     * с кодом статуса 200. В противном случае возвращает соответствующий
     * объект AuthorizationResponse с кодом ошибки и сообщением.
     * </p>
     *
     * @param command Команда, содержащая информацию о пользователе,
     *                который пытается войти в систему.
     * @return Response Возвращает объект {@link AuthorizationResponse} с результатами
     *                  аутентификации, включая код статуса и сообщение.
     */
    @Override
    public Response handle(SignInCommand command) {
        try {
            ServerApplicationContainer.getInstance().getUserManager().signIn(command.getUser().getUserName(), command.getUser().getPassword());
            return new AuthorizationResponse(StatusCode._200_SUCCESS_, "", command.getUser());
        } catch (SQLException e) {
            return new AuthorizationResponse(StatusCode._500_SERVER_ERROR, "Ошибка при верификации пользователя.", null);
        } catch (IncorrectPasswordException e) {
            return new AuthorizationResponse(StatusCode._400_CLIENT_ERROR, "Введен неверный пароль.", null);
        } catch (NoUserFoundException e) {
            return new AuthorizationResponse(StatusCode._400_CLIENT_ERROR, "Введенное имя пользователя не найдено.", null);
        }
    }
}
