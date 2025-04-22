package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.ClientApplicationContainer;
import org.example.contract.commands.Command;
import org.example.contract.commands.SignUpCommand;
import org.example.contract.data.User;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация строителя для создания команды регистрации пользователя {@link SignUpCommand}.
 * <p>
 * Обеспечивает интерактивное построение команды с запросом учетных данных через консоль.
 * Реализует функциональность интерфейса {@link CommandBuilder}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandBuilder
 * @see SignUpCommand
 * @see ClientApplicationContainer
 */
@NoArgsConstructor
public class SignUpCommandBuilder implements CommandBuilder {

    /**
     * Строит команду регистрации на основе пользовательского ввода.
     * <p>
     * Алгоритм работы:
     * </p>
     * <ol>
     *   <li>Запрашивает логин и пароль через стандартный ввод</li>
     *   <li>Создает объект {@link User} с полученными данными</li>
     *   <li>Возвращает новую команду {@link SignUpCommand}</li>
     * </ol>
     *
     * @param str сигнатура команды
     * @return {@link SignUpCommand} с данными нового пользователя
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] str) {
        if(str.length != 1) throw new InvalidArgumentException();
        System.out.println("Введите имя пользователя:");
        String userName = ClientApplicationContainer.getInstance().getBufferedLineReader().nextLine();
        System.out.println("Введите пароль:");
        String password = ClientApplicationContainer.getInstance().getBufferedLineReader().nextLine();
        return new SignUpCommand(new User(userName, password));
    }
}
