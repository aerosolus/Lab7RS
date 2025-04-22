package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.dataManaging.ValuesGetter;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.CountGreaterThanPersonCommand;
import org.example.contract.data.Person;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link CountGreaterThanPersonCommand}.
 * <p>
 * Команда подсчитывает количество элементов коллекции, значение поля person которых превышает заданное.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CountGreaterThanPersonCommand
 * @see ValuesGetter
 */
@NoArgsConstructor
public class CountGreaterThanPersonCommandBuilder implements CommandBuilder {

    /**
     * Создает команду подсчета элементов с person больше заданного.
     *
     * @param str имя команды
     *
     * @return команду {@link CountGreaterThanPersonCommand} с заданным person
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] str) {
        if (str.length != 1) throw new InvalidArgumentException();
        Person lowPerson = ValuesGetter.readPerson();
        return new CountGreaterThanPersonCommand(lowPerson, TerminalManager.getUser());
    }
}
