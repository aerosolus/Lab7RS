package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

import java.util.List;

/**
 * Команда для выполнения скрипта, содержащего последовательность команд.
 * <p>При выполнении последовательно исполняет все команды из списка.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */
@AllArgsConstructor
@Getter
public class ExecuteScriptCommand implements Command {

    /**
     * Список команд для выполнения.
     */
    private List<Command> scriptList;

    /**
     * Пользователь, использующий команду
     */
    private User user;

    /**
     * Возвращает имя команды.
     *
     * @return имя команды
     */
    @Override
    public String getCommandName() {
        return "execute_script";
    }
}
