package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.ShowCommand;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link ShowCommand}.
 * <p>
 * Команда выводит все элементы коллекции в строковом представлении. Не требует аргументов.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see ShowCommand
 * @see CommandBuilder
 */
@NoArgsConstructor
public class ShowCommandBuilder implements CommandBuilder {

    /**
     * Создает команду отображения элементов коллекции.
     *
     * @param str имя команды
     * @return команду {@link ShowCommand}
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] str) {
        if(str.length != 1) throw new InvalidArgumentException();
        return new ShowCommand(TerminalManager.getUser());
    }
}
