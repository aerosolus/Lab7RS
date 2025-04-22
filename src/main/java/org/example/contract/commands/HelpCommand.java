package org.example.contract.commands;

import lombok.Getter;
import lombok.AllArgsConstructor;
import org.example.contract.data.User;

/**
 * Команда вывода справочной информации о доступных командах.
 * <p> При выполнении возвращает список всех поддерживаемых команд с их кратким описанием. </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */
@AllArgsConstructor
@Getter
public class HelpCommand implements Command {

    /**
     * Пользователь, использующий команду
     */
    private User user;

    /**
     * Возвращает системное имя команды.
     *
     * @return имя команды
     */
    @Override
    public String getCommandName() {
        return "help";
    }
}
