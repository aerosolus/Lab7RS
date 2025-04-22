package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

/**
 * Команда вывода истории выполненных команд.
 * <p>При выполнении возвращает список последних выполненных команд без их аргументов.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */
@AllArgsConstructor
@Getter
public class HistoryCommand implements Command {

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
        return "history";
    }
}
