package org.example.server.handlers;

import org.example.contract.commands.RemoveLowerCommand;
import org.example.contract.data.WorkerDTO;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

/**
 * Обработчик команды удаления элементов коллекции, меньших чем заданный.
 * <p>
 * Удаляет все элементы коллекции, которые меньше переданного в команде объекта {@link org.example.contract.data.Worker}.
 * Наследует функциональность базового обработчика {@link CommandHandler}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see RemoveLowerCommand
 * @see CollectionManager
 */
public class RemoveLowerHandler extends CommandHandler<RemoveLowerCommand> {

    /**
     * Удаляет элементы коллекции, меньшие заданного объекта.
     * <p>
     * Особенности работы:
     * </p>
     * <ul>
     *     <li>Возвращает статус {@link StatusCode#_200_SUCCESS_}</li>
     *     <li>Сообщение содержит общий результат операции независимо от количества удаленных элементов</li>
     *     <li>Сравнение элементов выполняется с использованием {@link org.example.server.utility.WorkerComparator}</li>
     * </ul>
     *
     * @param command объект команды, содержащий {@link WorkerDTO} для сравнения
     * @return {@link ResponseWithMessage} с:
     *         <ul>
     *             <li>Статус {@link StatusCode#_200_SUCCESS_}</li>
     *             <li>Сообщение об успешном выполнении операции</li>
     *         </ul>
     */
    @Override
    public Response handle(RemoveLowerCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        collectionManager.removeLower(command.getWorkerDTO());
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Из коллекции были удалены все работники, меньше заданного.");
    }
}
