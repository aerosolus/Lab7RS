package org.example.server.handlers;

import org.example.contract.commands.RemoveByIdCommand;
import org.example.contract.exceptions.NotOwnerException;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

import java.sql.SQLException;

/**
 * Обработчик команды удаления элемента коллекции по идентификатору.
 * <p>
 * Обеспечивает удаление элемента через {@link CollectionManager} при выполнении условий:
 * </p>
 * <ul>
 *     <li>Коллекция не пуста</li>
 *     <li>Элемент с указанным id присутствует в ней</li>
 * </ul>
 *
 * @author Aerosolus 
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see RemoveByIdCommand
 * @see CollectionManager#removeById(Long, String)
 */
public class RemoveByIdHandler extends CommandHandler<RemoveByIdCommand> {

    /**
     * Выполняет удаление элемента на основании заданного id.
     * <p>
     * Возможные сценарии выполнения:
     * </p>
     * <ul>
     *     <li><b>Коллекция пуста</b> - возвращает сообщение о пустоте (статус 200)</li>
     *     <li><b>Элемент не найден</b> - возвращает ошибку клиента (статус 400)</li>
     *     <li><b>Успешное удаление</b> - подтверждает операцию (статус 200)</li>
     * </ul>
     *
     * @param command объект команды с полем {@code id} типа {@link Long}
     *
     * @return {@link ResponseWithMessage} с:
     *         <ul>
     *             <li>{@link StatusCode#_200_SUCCESS_} и сообщением о статусе операции</li>
     *             <li>{@link StatusCode#_400_CLIENT_ERROR} при некорректном id</li>
     *         </ul>
     */
    @Override
    public Response handle(RemoveByIdCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if(collectionManager.getSize() == 0){
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Коллекция пуста.");
        } else {
            Long findId = command.getId();
            if (!collectionManager.containsId(findId)) {
                return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "Элемента с таким id нет в коллекции.");
            } else {
                try {
                    collectionManager.removeById(findId, command.getUser().getUserName());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }catch (NotOwnerException e){
                    return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "Вы не являетесь создателем работника с id = " + findId + ".");
                }
                return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Работник с id = " + findId + " был удален из коллекции.");
            }
        }
    }
}
