package org.example.contract.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Класс для представления данных пользователя в системе.
 * <p> Содержит учетные данные пользователя, используемые для аутентификации и регистрации.
 * Объекты этого класса сериализуемы для передачи по сети. </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see org.example.contract.commands.SignUpCommand
 * @see org.example.contract.commands.SignInCommand
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    /**
     * Уникальное имя пользователя (логин).
     * <p>
     * Требования:
     * <ul>
     *   <li>Должно быть уникальным в системе</li>
     * </ul>
     * </p>
     */
    private String userName;

    /**
     * Пароль пользователя.
     * <p>
     * Требования:
     * <ul>
     *   <li>Не может быть пустой строкой</li>
     * </ul>
     * </p>
     */
    private String password;
}
