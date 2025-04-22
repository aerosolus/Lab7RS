package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.dataManaging.ValuesGetter;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.FilterLessThanSalaryCommand;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link FilterLessThanSalaryCommand}.
 * <p>
 * Команда фильтрует элементы коллекции, оставляя те, у которых значение зарплаты меньше заданного.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see FilterLessThanSalaryCommand
 * @see ValuesGetter#readSalary()
 */
@NoArgsConstructor
public class FilterLessThanSalaryCommandBuilder implements CommandBuilder {

    /**
     * Создает команду фильтрации по зарплате.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Проверяет наличие ровно 1 аргумента (имя команды)</li>
     *   <li>Интерактивно запрашивает значение зарплаты через {@link ValuesGetter#readSalary()}</li>
     *   <li>Инкапсулирует полученное значение в команду</li>
     * </ol>
     * </p>
     *
     * @param str имя команды
     * @return команду {@link FilterLessThanSalaryCommand} с указанной зарплатой
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] str) {
        if (str.length != 1) throw new InvalidArgumentException();
        long maxSalary = ValuesGetter.readSalary();
        return new FilterLessThanSalaryCommand(maxSalary, TerminalManager.getUser());
    }
}
