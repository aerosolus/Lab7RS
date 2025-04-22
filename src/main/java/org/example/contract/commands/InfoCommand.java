package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

/**
 * Команда вывода информации о коллекции.
 * <p>
 * При выполнении возвращает:
 * <ul>
 *   <li>Тип коллекции</li>
 *   <li>Дату инициализации</li>
 *   <li>Количество элементов</li>
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
public class InfoCommand implements Command {

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
        return "info";
    }
}