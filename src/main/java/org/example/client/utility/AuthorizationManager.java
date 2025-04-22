package org.example.client.utility;

import org.example.client.builders.CommandBuilder;
import org.example.client.builders.SignInCommandBuilder;
import org.example.client.builders.SignUpCommandBuilder;
import org.example.contract.commands.Command;

import java.util.HashMap;

/**
 * Менеджер авторизации для обработки команд регистрации и входа.
 * <p>
 * Связывает команды аутентификации с соответствующими строителями через {@link HashMap}.
 * Обеспечивает создание команд на основе введенных пользователем данных.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandBuilder
 * @see SignInCommandBuilder
 * @see SignUpCommandBuilder
 */
public class AuthorizationManager {

    /**
     * Карта для сопоставления команд с соответствующими строителями.
     * <p>
     * Ключи: строковые идентификаторы команд ("sign_in", "sign_up")
     * <br>
     * Значения: реализации {@link CommandBuilder} для создания команд
     * </p>
     */
    public HashMap<String, CommandBuilder> extractor = new HashMap<>();

    /**
     * Инициализирует карту строителей команд авторизации.
     * <p>
     * Регистрирует строителей для:
     * </p>
     * <ul>
     *   <li>"sign_in" → {@link SignInCommandBuilder}</li>
     *   <li>"sign_up" → {@link SignUpCommandBuilder}</li>
     * </ul>
     */
    public AuthorizationManager(){
        extractor.put("sign_in", new SignInCommandBuilder());
        extractor.put("sign_up", new SignUpCommandBuilder());
    }

    /**
     * Создает команду авторизации на основе входных данных.
     * <p>
     * Алгоритм работы:
     * </p>
     * <ol>
     *   <li>Предоставляет строитель по первому элементу массива</li>
     *   <li>Делегирует создание команды соответствующему строителю</li>
     * </ol>
     *
     * @param str тип команды (должен быть "sign_in" или "sign_up")
     * @return объект {@link Command} в зависимости от типа запроса
     * @throws NullPointerException если передан неизвестный тип команды
     * @throws org.example.contract.exceptions.InvalidArgumentException при несоответствии количества аргументов
     */
    public Command authorize(String[] str){
        return extractor.get(str[0]).build(str);
    }
}
