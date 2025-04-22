package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.ClientApplicationContainer;
import org.example.contract.commands.Command;
import org.example.contract.commands.SignInCommand;
import org.example.contract.data.User;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация строителя для создания команды аутентификации пользователя {@link SignInCommand}.
 * <p>
 * Обеспечивает интерактивное построение команды с запросом учетных данных через консоль.
 * Наследует функциональность интерфейса {@link CommandBuilder}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandBuilder
 * @see SignInCommand
 * @see ClientApplicationContainer
 */
@NoArgsConstructor
public class SignInCommandBuilder implements CommandBuilder {

    /**
     * Строит команду аутентификации на основе пользовательского ввода.
     * <p>
     * Логика работы:
     * </p>
     * <ol>
     *   <li>Запрашивает логин и пароль через стандартный ввод</li>
     *   <li>Создает объект {@link User} с полученными данными</li>
     *   <li>Возвращает новую команду {@link SignInCommand}</li>
     * </ol>
     *
     * @param str сигнатура команды
     * @return {@link SignInCommand} с данными пользователя
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] str) {
        if(str.length != 1) throw new InvalidArgumentException();
        System.out.println("Введите имя пользователя:");
        String userName = ClientApplicationContainer.getInstance().getBufferedLineReader().nextLine();
        System.out.println("Введите пароль:");
        String password = ClientApplicationContainer.getInstance().getBufferedLineReader().nextLine();
        return new SignInCommand(new User(userName, password));
    }
}
