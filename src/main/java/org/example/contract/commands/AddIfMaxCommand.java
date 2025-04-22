package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;
import org.example.contract.data.WorkerDTO;

/**
 * Команда добавления элемента в коллекцию, если его значение превышает максимальное в текущей коллекции.
 * <p>
 * Требования:
 * <ul>
 *   <li>WorkerDTO не должен быть null</li>
 *   <li>Все поля WorkerDTO должны быть корректны</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 * @see WorkerDTO
 */
@AllArgsConstructor
@Getter
public class AddIfMaxCommand implements Command {

    /**
     * Данные работника для добавления
     */
    private WorkerDTO workerDTO;

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
        return "add_if_max";
    }
}