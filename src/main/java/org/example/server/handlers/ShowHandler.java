package org.example.server.handlers;

import org.example.contract.commands.ShowCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

/**
 * Обработчик команды вывода всех элементов коллекции.
 * <p>
 * Предоставляет строковое представление всех элементов коллекции через {@link CollectionManager}.
 * Наследует функциональность базового обработчика {@link CommandHandler}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see ShowCommand
 * @see CollectionManager
 */
public class ShowHandler extends CommandHandler<ShowCommand> {

    /**
     * Возвращает строковое представление элементов коллекции.
     * <p>
     * В зависимости от состояния коллекции возвращает:
     * </p>
     * <ul>
     *     <li>При пустой коллекции - уведомление о ее пустоте</li>
     *     <li>При наличии элементов - форматированный список элементов</li>
     * </ul>
     *
     * @param command объект команды {@link ShowCommand}
     *
     * @return {@link ResponseWithMessage} с:
     *         <ul>
     *             <li>Статус {@link StatusCode#_200_SUCCESS_}</li>
     *             <li>Сообщение с элементами коллекции или уведомлением о ее пустоте</li>
     *         </ul>
     */
    @Override
    public Response handle(ShowCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if(collectionManager.getSize() == 0) {
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Коллекция пуста.");
        } else{
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, collectionManager.showCollection());
        }
    }
}
