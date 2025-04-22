package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.CommandWithID;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.RemoveByIdCommand;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link RemoveByIdCommand}.
 * <p>
 * Команда удаляет элемент коллекции по значению ID, которое было передано в качестве аргумента.
 * Требует обязательного параметра - положительного целочисленного ID.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see RemoveByIdCommand
 * @see CommandWithID
 */
@NoArgsConstructor
public class RemoveByIdCommandBuilder implements CommandBuilder, CommandWithID {

    /**
     * Создает команду удаления элемента по ID.
     * <p>
     * Требования к аргументам:
     * <ul>
     *   <li>str[0] - имя команды ("remove_by_id")</li>
     *   <li>str[1] - целочисленный ID элемента (положительное число)</li>
     * </ul>
     *
     * @param str массив аргументов команды
     * @return команду {@link RemoveByIdCommand} с указанным ID
     * @throws InvalidArgumentException если количество аргументов некорректно
     * @throws NumberFormatException если значение ID некорректно
     */
    @Override
    public Command build(String[] str) {
        if (str.length != 2) throw new InvalidArgumentException();
        if (checkArgForId(str[1])) {
            if (Long.parseLong(str[1]) <= 0) throw new NumberFormatException();
            return new RemoveByIdCommand(Long.parseLong(str[1]), TerminalManager.getUser());
        } else {
            throw new NumberFormatException();
        }
    }
}
