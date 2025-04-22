package org.example.contract.responses;

import lombok.Getter;
import org.example.contract.data.User;
import org.example.contract.utility.StatusCode;

/**
 * Ответ сервера на запросы аутентификации/регистрации пользователя.
 * <p>
 * Содержит результат выполнения операций:
 * <ul>
 *   <li>{@link org.example.contract.commands.SignInCommand}</li>
 *   <li>{@link org.example.contract.commands.SignUpCommand}</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Response
 */
@Getter
public class AuthorizationResponse extends Response {

    /**
     * Сообщение о результате операции:
     */
    private String message;

    /**
     * Данные пользователя
     */
    private User user;

    /**
     * Создает ответ с результатом авторизации
     *
     * @param statusCode код статуса выполнения {@link StatusCode}
     * @param message информационное сообщение
     * @param user объект пользователя
     */
    public AuthorizationResponse(StatusCode statusCode, String message, User user) {
        super(statusCode);
        this.message = message;
        this.user = user;
    }
}
