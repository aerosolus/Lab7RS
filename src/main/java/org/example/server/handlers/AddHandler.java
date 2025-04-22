package org.example.server.handlers;

import org.example.contract.commands.AddCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;
import org.example.server.utility.WorkerComparator;

import java.sql.SQLException;

/**
 * Обработчик команды {@link AddCommand}, выполняющий добавление элемента в коллекцию.
 * <p>
 * После добавления элемента коллекция автоматически сортируется с помощью {@link WorkerComparator}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 */
public class AddHandler extends CommandHandler<AddCommand> {

    /**
     * Обрабатывает команду добавления элемента:
     * <ol>
     *   <li>Получает менеджер коллекции из приложения</li>
     *   <li>Добавляет элемент в коллекцию</li>
     *   <li>Сортирует коллекцию с помощью {@link WorkerComparator}</li>
     *   <li>Возвращает ответ с подтверждением</li>
     * </ol>
     *
     * @param command команда добавления с данными элемента
     * @return {@link ResponseWithMessage} с:
     * <ul>
     *   <li>Кодом статуса {@link StatusCode#_200_SUCCESS_}</li>
     *   <li>Сообщением "Элемент был добавлен в коллекцию."</li>
     * </ul>
     */
    @Override
    public Response handle(AddCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        try {
            collectionManager.add(command.getWorkerDTO(), command.getUser().getUserName());
            WorkerComparator workerComparator = new WorkerComparator();
            collectionManager.sort(workerComparator);
            return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Элемент был добавлен в коллекцию.");
        } catch (SQLException e) {
            System.err.println("Добавление не удалось реализовать. Проверьте конфигурацию БД.");
            return new ResponseWithMessage(StatusCode._500_SERVER_ERROR, "Элемент не был добавлен в коллекцию.");
        }
    }
}
