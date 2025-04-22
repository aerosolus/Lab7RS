package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

/**
 * Команда вывода всех элементов коллекции в строковом представлении.
 * <p>
 * При выполнении возвращает:
 * <ul>
 *   <li>Список всех элементов коллекции</li>
 *   <li>Формат вывода: строковое представление объектов</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */
@AllArgsConstructor
@Getter
public class ShowCommand implements Command {

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
        return "show";
    }
}