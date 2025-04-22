package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.PrintFieldAscendingPersonCommand;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link PrintFieldAscendingPersonCommand}.
 * <p>
 * Команда выводит значения поля {@code person} всех элементов коллекции в порядке возрастания.
 * Не требует дополнительных аргументов, кроме обязательного имени команды.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see PrintFieldAscendingPersonCommand
 * @see CommandBuilder
 */
@NoArgsConstructor
public class PrintFieldAscendingPersonCommandBuilder implements CommandBuilder {

    /**
     * Создает команду для вывода значений поля person в возрастающем порядке.
     *
     * @param str имя команды
     *
     * @return команду {@link PrintFieldAscendingPersonCommand}
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] str) {
        if(str.length != 1) throw new InvalidArgumentException();
        return new PrintFieldAscendingPersonCommand(TerminalManager.getUser());
    }
}
