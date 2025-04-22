package org.example.server.handlers;

import org.example.contract.commands.AddIfMaxCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;
import org.example.server.utility.WorkerComparator;

import java.sql.SQLException;

/**
 * Обработчик команды {@link AddIfMaxCommand}.
 * <p>
 * Добавляет элемент в коллекцию, только если он превышает максимальный элемент по правилам сравнения {@link WorkerComparator}.
 * </p>
 *
 * <h3>Логика работы:</h3>
 * <ol>
 *   <li>Валидация входных данных</li>
 *   <li>Проверка, является ли элемент максимальным</li>
 *   <li>Добавление элемента и сортировка коллекции</li>
 *   <li>Формирование ответа клиенту</li>
 * </ol>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see WorkerComparator
 */
public class AddIfMaxHandler extends CommandHandler<AddIfMaxCommand> {

    /**
     * Обрабатывает команду добавления элемента, если он максимален.
     *
     * @param command команда {@link AddIfMaxCommand},
     *                содержащая {@link org.example.contract.data.WorkerDTO} - данные добавляемого элемента
     * @return ответ с результатом операции:
     *         <ul>
     *           <li>{@link StatusCode#_200_SUCCESS_} - элемент добавлен</li>
     *           <li>{@link StatusCode#_400_CLIENT_ERROR} - элемент не максимальный</li>
     *           <li>{@link StatusCode#_500_SERVER_ERROR} - внутренняя ошибка сервера</li>
     *         </ul>
     */
    @Override
    public Response handle(AddIfMaxCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        if (collectionManager.isMax(command.getWorkerDTO())) {
            try {
                collectionManager.add(command.getWorkerDTO(), command.getUser().getUserName());
                WorkerComparator workerComparator = new WorkerComparator();
                collectionManager.sort(workerComparator);
                return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Элемент был добавлен в коллекцию.");
            } catch (SQLException e) {
                return new ResponseWithMessage(StatusCode._500_SERVER_ERROR, "Добавление не удалось реализовать. Проверьте конфигурацию БД.");
            }
        } else {
            return new ResponseWithMessage(StatusCode._400_CLIENT_ERROR, "Заданный объект меньше наибольшего элемента коллекции.");
        }
    }
}
