package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

import java.io.Serializable;

/**
 * Команда для аутентификации пользователя в системе.
 * <p>
 * Содержит учетные данные пользователя, которые будут проверены на сервере.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see User
 */
@AllArgsConstructor
@Getter
public class SignInCommand implements Command {

    /**
     * Объект пользователя, содержащий логин и пароль для аутентификации.
     * <p>
     * Обязательные поля:
     * <ul>
     *   <li>Логин (уникальный)</li>
     *   <li>Пароль (непустой)</li>
     * </ul>
     * </p>
     */
    private User user;

    /**
     * Возвращает системное имя команды
     *
     * @return имя команды
     */
    @Override
    public String getCommandName() {
        return "sign_in";
    }
}
