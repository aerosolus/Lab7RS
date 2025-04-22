package org.example.server.handlers;

import org.example.contract.commands.PrintFieldAscendingPersonCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

/**
 * Обработчик команды вывода значений поля person элементов в порядке возрастания.
 * <p>
 * Предоставляет отсортированные данные из коллекции через {@link CollectionManager}.
 * Наследует функциональность базового обработчика {@link CommandHandler}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see PrintFieldAscendingPersonCommand
 * @see CollectionManager
 */
public class PrintFieldAscendingPersonHandler extends CommandHandler<PrintFieldAscendingPersonCommand> {

    /**
     * Возвращает значения поля person элементов в порядке возрастания.
     * <p>
     * В зависимости от состояния коллекции возвращает:
     * </p>
     * <ul>
     *     <li>При пустой коллекции - уведомление о пустоте</li>
     *     <li>При наличии элементов - отсортированные значения</li>
     * </ul>
     *
     * @param command объект команды {@link PrintFieldAscendingPersonCommand}
     *
     * @return {@link ResponseWithMessage} с:
     *         <ul>
     *             <li>Статус {@link StatusCode#_200_SUCCESS_}</li>
     *             <li>Сообщение в зависимости от состояния коллекции</li>
     *         </ul>
     */
    @Override
    public Response handle(PrintFieldAscendingPersonCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if(collectionManager.getSize() == 0) {
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Коллекция пуста.");
        } else{
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, collectionManager.printFieldAscendingPerson());
        }
    }
}
