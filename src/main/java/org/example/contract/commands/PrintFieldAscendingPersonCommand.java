package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

/**
 * Команда вывода значений поля {@code person} всех элементов коллекции в порядке возрастания.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 * @see org.example.contract.data.Person
 */
@AllArgsConstructor
@Getter
public class PrintFieldAscendingPersonCommand implements Command {

    /**
     * Пользователь, использующий команду
     */
    private User user;

    /**
     * Возвращает системное имя команды
     *
     * @return имя команды
     */
    @Override
    public String getCommandName() {
        return "print_field_ascending_person";
    }
}
