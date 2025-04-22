package org.example.server.handlers;

import org.example.contract.commands.Command;
import org.example.contract.commands.ExecuteScriptCommand;
import org.example.contract.responses.ExecuteScriptResponse;
import org.example.contract.responses.Response;
import org.example.contract.utility.StatusCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Обработчик команды {@link ExecuteScriptCommand}.
 * <p>
 * Выполняет последовательность команд из скрипта и возвращает совокупный результат.
 * </p>
 *
 * <ol>
 *   <li>Итерация по списку команд из скрипта</li>
 *   <li>Последовательное выполнение каждой команды через {@link org.example.server.utility.CommandManager}</li>
 *   <li>Сбор результатов выполнения всех команд</li>
 *   <li>Формирование общего ответа со статусом выполнения</li>
 * </ol>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see ExecuteScriptResponse
 */
public class ExecuteScriptHandler extends CommandHandler<ExecuteScriptCommand> {

    /**
     * Обрабатывает выполнение скрипта команд.
     *
     * @param command команда, содержащая {@code List<Command>} - список команд для выполнения
     *
     * @return ответ с общим результатом выполнения скрипта:
     *         <ul>
     *           <li>{@link StatusCode#_200_SUCCESS_} - скрипт выполнен полностью</li>
     *           <li>Список ответов для каждой команды в {@link ExecuteScriptResponse}</li>
     *         </ul>
     */
    @Override
    public Response handle(ExecuteScriptCommand command) {
        List<Response> scriptList = new ArrayList<>();
        for(Command c: command.getScriptList()) {
            scriptList.add(this.app.getCommandManager().executeCommand(c));
        }
        return new ExecuteScriptResponse(StatusCode._200_SUCCESS_, scriptList);
    }
}
