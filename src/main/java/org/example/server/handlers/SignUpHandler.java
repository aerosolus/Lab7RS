package org.example.server.handlers;

import org.example.contract.commands.SignUpCommand;
import org.example.contract.responses.AuthorizationResponse;
import org.example.contract.responses.Response;
import org.example.contract.utility.StatusCode;
import org.example.server.utility.ServerApplicationContainer;

import java.sql.SQLException;

/**
 * Класс SignUpHandler обрабатывает команды регистрации новых пользователей.
 *
 * <p>
 * Он наследуется от CommandHandler и отвечает за выполнение логики
 * регистрации, включая обработку возможных исключений.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class SignUpHandler extends CommandHandler<SignUpCommand> {

    /**
     * Обрабатывает команду регистрации пользователя в системе.
     *
     * <p>
     * Этот метод выполняет регистрацию нового пользователя, используя
     * переданные в команде учетные данные (имя пользователя и пароль).
     * В случае успешной регистрации возвращает объект AuthorizationResponse
     * с кодом статуса 200. В противном случае возвращает объект
     * {@link AuthorizationResponse} с кодом ошибки и сообщением о проблеме.
     * </p>
     *
     * @param command Команда, содержащая информацию о новом пользователе,
     *                который пытается зарегистрироваться.
     * @return Response Возвращает объект AuthorizationResponse с результатами
     *                  регистрации, включая код статуса и сообщение.
     */
    @Override
    public Response handle(SignUpCommand command) {
        try {
            ServerApplicationContainer.getInstance().getUserManager().signUp(command.getUser().getUserName(), command.getUser().getPassword());
            return new AuthorizationResponse(StatusCode._200_SUCCESS_, "", command.getUser());
        } catch (SQLException e) {
            return new AuthorizationResponse(StatusCode._400_CLIENT_ERROR, "Имя пользователя уже используется.", null);
        }
    }
}
