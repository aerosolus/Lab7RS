package org.example.server.handlers;

import org.example.contract.commands.CountGreaterThanPersonCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

/**
 * Обработчик команды {@link CountGreaterThanPersonCommand}.
 * <p>
 * Подсчитывает количество элементов коллекции, значение поля person которых превышает заданное.
 *
 * <h3>Логика работы:</h3>
 * <ol>
 *   <li>Получение объекта-критерия из команды</li>
 *   <li>Сравнение элементов коллекции через {@link CollectionManager#countGreaterThanPerson}</li>
 *   <li>Формирование ответа с результатом</li>
 * </ol>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see CollectionManager
 */
public class CountGreaterThanPersonHandler extends CommandHandler<CountGreaterThanPersonCommand> {

    /**
     * Обрабатывает команду подсчета элементов.
     *
     * @param command команда, содержащая {@link org.example.contract.data.Person} - критерий для сравнения
     *
     * @return ответ с результатом {@link StatusCode#_200_SUCCESS_} - успешный подсчет
     */
    @Override
    public Response handle(CountGreaterThanPersonCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, collectionManager.countGreaterThanPerson(command.getLowPerson()));
    }
}
