package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

import java.io.Serializable;

/**
 * Команда для регистрации нового пользователя в системе.
 * <p>
 * Содержит данные пользователя, необходимые для создания новой учетной записи.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see User
 */
@AllArgsConstructor
@Getter
public class SignUpCommand implements Command {

    /**
     * Объект пользователя с регистрационными данными.
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
        return "sign_up";
    }
}
