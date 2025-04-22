package org.example.server.handlers;

import org.example.contract.commands.FilterLessThanSalaryCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.dataManaging.CollectionManager;

/**
 * Обработчик команды {@link FilterLessThanSalaryCommand}.
 * <p>
 * Фильтрует элементы коллекции, оставляя те, у которых значение зарплаты меньше указанного.
 *
 * <h3>Логика работы:</h3>
 * <ol>
 *   <li>Получение максимального значения зарплаты из команды</li>
 *   <li>Фильтрация элементов через {@link CollectionManager#filterLessThanSalary}</li>
 *   <li>Форматирование результатов</li>
 *   <li>Возврат списка подходящих элементов</li>
 * </ol>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see CollectionManager
 */
public class FilterLessThanSalaryHandler extends CommandHandler<FilterLessThanSalaryCommand> {

    /**
     * Обрабатывает команду фильтрации по зарплате
     *
     * @param command команда, содержащая {@code maxSalary} - верхняя граница зарплаты
     *
     * @return ответ с результатом {@link StatusCode#_200_SUCCESS_} - успешное выполнение
     */
    @Override
    public Response handle(FilterLessThanSalaryCommand command) {
        CollectionManager collectionManager = this.app.getCollectionManager();
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, collectionManager.filterLessThanSalary(command.getMaxSalary()));
    }
}
