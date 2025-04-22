package org.example.server.handlers;

import org.example.contract.commands.UpdateCommand;
import org.example.contract.data.WorkerDTO;
import org.example.contract.exceptions.NotOwnerException;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

import java.sql.SQLException;

/**
 * Обработчик команды обновления элемента коллекции на основании заданного id.
 * <p>
 * Обеспечивает замену данных элемента коллекции через {@link CollectionManager} при выполнении условий:
 * </p>
 * <ul>
 *     <li>Коллекция не пуста</li>
 *     <li>Элемент с указанным id существует</li>
 *     <li>Переданные данные {@link org.example.contract.data.WorkerDTO} корректны</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see UpdateCommand
 * @see CollectionManager#updateById(Long, WorkerDTO, String)
 */
public class UpdateHandler extends CommandHandler<UpdateCommand> {

    /**
     * Обновляет элемент коллекции на основании заданного id.
     * <p>
     * Возможные сценарии выполнения:
     * </p>
     * <ul>
     *     <li><b>Коллекция пуста</b> - возвращает сообщение о невозможности обновления (статус 200)</li>
     *     <li><b>Элемент не найден</b> - возвращает ошибку клиента (статус 400)</li>
     *     <li><b>Успешное обновление</b> - подтверждает операцию (статус 200)</li>
     * </ul>
     *
     * @param command объект команды с полями:
     *               <ul>
     *                 <li>{@code id} - идентификатор элемента для обновления</li>
     *                 <li>{@code workerDTO} - данные для обновления</li>
     *               </ul>
     *
     * @return {@link ResponseWithMessage} с:
     *         <ul>
     *             <li>{@link StatusCode#_200_SUCCESS_} и сообщением о статусе операции</li>
     *             <li>{@link StatusCode#_400_CLIENT_ERROR} при некорректном id</li>
     *         </ul>
     */
    @Override
    public Response handle(UpdateCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if (collectionManager.getSize() == 0) {
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Коллекция пуста. Нет элементов для обновления.");
        }else{
            Long updateId = command.getId();
            if (!collectionManager.containsId(updateId)) {
                return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR,"В коллекции отсутствует элемент с указанным значением поля id." );
            } else {
                try {
                    collectionManager.updateById(updateId, command.getWorkerDTO(), command.getUser().getUserName());
                } catch (SQLException e) {
                    System.err.println("Обновление не удалось реализовать. Проверьте конфигурацию БД.");
                    return new ResponseWithMessage(StatusCode._500_SERVER_ERROR, "Элемент не был обновлен.");
                } catch(NotOwnerException e) {
                    return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "Вы не являетесь создателем работника с id = " + updateId + ".");
                }

            }
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Элемент коллекции с id = " + updateId + " был обновлен.");
        }
    }
}