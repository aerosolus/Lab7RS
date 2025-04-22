package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.HelpCommand;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link HelpCommand}.
 * <p>
 * Команда выводит список доступных команд с их описанием. Не требует аргументов.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see HelpCommand
 */
@NoArgsConstructor
public class HelpCommandBuilder implements CommandBuilder {

    /**
     * Создает команду вывода справочной информации.
     * <p>
     * @param args имя команды
     *
     * @return команду {@link HelpCommand}
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] args) {
        if(args.length != 1) throw new InvalidArgumentException();
        return new HelpCommand(TerminalManager.getUser());
    }
}
