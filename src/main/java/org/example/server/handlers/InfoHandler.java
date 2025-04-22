package org.example.server.handlers;

import org.example.contract.commands.InfoCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

/**
 * Обработчик команды получения информации о коллекции.
 * <p>
 * Предоставляет данные коллекции, включая тип, количество элементов и дату инициализации.
 * Наследует функциональность базового обработчика {@link CommandHandler}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see InfoCommand
 * @see CollectionManager
 * @see ResponseWithMessage
 */
public class InfoHandler extends CommandHandler<InfoCommand> {

    /**
     * Формирует отчет о состоянии коллекции.
     * <p>
     * Запрашивает данные через {@link CollectionManager} и возвращает ответ с:
     * </p>
     * <ul>
     *     <li>Статус {@link StatusCode#_200_SUCCESS_}</li>
     *     <li>Строка с информацией о коллекции</li>
     * </ul>
     *
     * @param command объект команды {@link InfoCommand}
     *
     * @return {@link ResponseWithMessage} с данными коллекции
     */
    @Override
    public Response handle(InfoCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        String infoStr;
        infoStr = "Информация о коллекции:\n" + collectionManager.getCollectionInfo();
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, infoStr);
    }
}
