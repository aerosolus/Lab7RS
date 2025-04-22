package org.example.server.handlers;

import org.example.contract.commands.HistoryCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;
import org.example.server.utility.CommandManager;

/**
 * Обработчик команды вывода истории выполненных команд.
 * <p>
 * Предоставляет список последних 5 выполненных команд (без аргументов) из {@link CommandManager}.
 * Наследует функциональность базового обработчика {@link CommandHandler}.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see HistoryCommand
 * @see CommandManager
 */
public class HistoryHandler extends CommandHandler<HistoryCommand> {

    /**
     * Возвращает историю выполненных команд из менеджера команд.
     * <p>
     * Получает историю через {@link CommandManager} и формирует ответ с:
     * </p>
     * <ul>
     *     <li>Статус {@link StatusCode#_200_SUCCESS_}</li>
     *     <li>Строка с перечислением команд</li>
     * </ul>
     *
     * @param command объект команды {@link HistoryCommand}
     *
     * @return {@link ResponseWithMessage} с историей команд
     */
    @Override
    public Response handle (HistoryCommand command) {
        CommandManager commandManager = this.app.getCommandManager();
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, commandManager.getHistory());
    }
}
