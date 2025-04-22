package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

/**
 * Команда фильтрации элементов коллекции по значению поля salary.
 * <p>Оставляет только элементы, у которых значение поля salary строго меньше указанного.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */
@AllArgsConstructor
@Getter
public class FilterLessThanSalaryCommand implements Command {

    /**
     * Верхняя граница зарплаты для фильтрации
     */
    private long maxSalary;

    /**
     * Пользователь, использующий команду
     */
    private User user;

    /**
     * Возвращает имя команды
     *
     * @return имя команды
     */
    @Override
    public String getCommandName() {
        return "filter_less_than_salary";
    }
}