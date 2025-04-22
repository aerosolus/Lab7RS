package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.HistoryCommand;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link HistoryCommand}.
 * <p>
 * Команда выводит историю выполненных команд. Не требует аргументов.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see HistoryCommand
 */
@NoArgsConstructor
public class HistoryCommandBuilder implements CommandBuilder {

    /**
     * Создает команду вывода истории команд.
     * <p>
     * @param args имя команды
     *
     * @return команду {@link HistoryCommand}
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] args) {
        if(args.length != 1) throw new InvalidArgumentException();
        return new HistoryCommand(TerminalManager.getUser());
    }
}

