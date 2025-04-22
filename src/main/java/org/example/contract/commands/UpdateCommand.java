package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;
import org.example.contract.data.WorkerDTO;

/**
 * Команда обновления элемента коллекции по ID.
 * <p>
 * Требования:
 * <ul>
 *   <li>ID должен существовать в коллекции</li>
 *   <li>Все поля WorkerDTO должны быть корректны</li>
 *   <li>ID должен быть положительным числом</li>
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
public class UpdateCommand implements Command {

    /**
     * ID обновляемого элемента
     */
    private Long id;

    /**
     * Пользователь, использующий команду
     */
    private User user;

    /**
     * Новые данные для обновления элемента
     */
    private WorkerDTO workerDTO;

    /**
     * Возвращает имя команды
     *
     * @return имя команды
     */
    @Override
    public String getCommandName(){
        return "update";
    }
}