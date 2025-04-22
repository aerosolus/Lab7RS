package org.example.server.handlers;

import org.example.contract.commands.ClearCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

import java.sql.SQLException;

/**
 * Обработчик команды {@link ClearCommand}.
 * <p>
 * Отвечает за очистку коллекции.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see CollectionManager#clearCollection(String)
 */
public class ClearHandler extends CommandHandler<ClearCommand> {

    /**
     * Обрабатывает команду очистки коллекции.
     *
     * @param command команда {@link ClearCommand}
     * @return ответ с результатом операции: {@link StatusCode#_200_SUCCESS_} - коллекция успешно очищена
     */
    @Override
    public Response handle (ClearCommand command){
        CollectionManager collectionManager = this.app.getCollectionManager();
        try {
            collectionManager.clearCollection(command.getUser().getUserName());
        } catch (SQLException e) {
            return new ResponseWithMessage(StatusCode._500_SERVER_ERROR, "Очищение не удалось реализовать. Проверьте конфигурацию БД.");
        }
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, "Коллекция очищена.");

    }
}
