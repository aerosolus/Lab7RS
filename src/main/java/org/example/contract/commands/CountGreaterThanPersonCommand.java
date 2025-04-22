package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.Person;
import org.example.contract.data.User;

/**
 * Команда подсчета элементов коллекции, значение поля person которых превышает заданный объект.
 * <p>Сравнение производится в соответствии с логикой сравнения объектов {@link Person}.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 * @see Person
 */
@AllArgsConstructor
@Getter
public class CountGreaterThanPersonCommand implements Command {

    /**
     * Объект {@link Person}, используемый в качестве критерия сравнения.
     * <p> Все элементы коллекции с person, большим чем этот объект, будут учтены в подсчете. </p>
     */
    private Person lowPerson;

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
        return "count_greater_than_person";
    }
}