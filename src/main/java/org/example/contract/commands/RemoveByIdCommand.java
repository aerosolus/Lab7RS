package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;

/**
 * Команда удаления элемента коллекции по заданному идентификатору.
 * <p>
 * Требования:
 * <ul>
 *   <li>ID должен присутствовать в коллекции</li>
 *   <li>ID должен быть положительным целым числом</li>
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
public class RemoveByIdCommand implements Command {

    /**
     * Идентификатор удаляемого элемента
     */
    private Long id;

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
        return "remove_by_id";
    }
}