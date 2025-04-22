package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.ClearCommand;
import org.example.contract.commands.Command;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link ClearCommand}.
 * <p>
 * Команда очистки коллекции не требует аргументов.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see ClearCommand
 */
@NoArgsConstructor
public class ClearCommandBuilder implements CommandBuilder{

    /**
     * Создает команду очистки коллекции.
     *
     * @return команда {@link ClearCommand}
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] args) {
        if(args.length != 1) throw new InvalidArgumentException();
        return new ClearCommand(TerminalManager.getUser());
    }
}