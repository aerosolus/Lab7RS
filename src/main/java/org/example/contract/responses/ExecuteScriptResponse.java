package org.example.contract.responses;

import lombok.Getter;
import org.example.contract.utility.StatusCode;

import java.util.List;

/**
 * Ответ, содержащий результаты выполнения скрипта.
 *
 * <p>Наследует от базового класса {@link Response} и используется для передачи
 * результатов выполнения последовательности команд в файле скрипта.</p>
 *
 * <p><b>Характеристики:</b>
 * <ul>
 *   <li>Содержит список ответов на каждую выполненную команду из скрипта</li>
 *   <li>Общий статус выполнения всего скрипта</li>
 *   <li>Использует Lombok {@code @Getter} для доступа к полям</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
public class ExecuteScriptResponse extends Response {

    /**
     * Список ответов на выполненные команды из скрипта.
     *
     * <p>Каждый элемент списка содержит результат выполнения
     * соответствующей команды из скрипта.</p>
     */
    List<Response> responseList;

    /**
     * Создает ответ на выполнение скрипта.
     *
     * @param statusCode общий статус выполнения скрипта
     * @param responseList список ответов на выполненные команды
     */
    public ExecuteScriptResponse(StatusCode statusCode, List<Response> responseList) {
        super(statusCode);
        this.responseList = responseList;
    }
}