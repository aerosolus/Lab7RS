package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;
import org.example.contract.data.WorkerDTO;

/**
 * Команда удаления всех элементов коллекции, меньших чем заданный объект Worker.
 * <p> Сравнение элементов производится в соответствии с естественным порядком сортировки,
 * определенным для объектов Worker.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 * @see WorkerDTO
 */
@AllArgsConstructor
@Getter
public class RemoveLowerCommand implements Command {

    /**
     * Объект для сравнения элементов.
     */
    private WorkerDTO workerDTO;

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
        return "remove_lower";
    }
}
