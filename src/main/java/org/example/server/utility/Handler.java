package org.example.server.utility;

import org.example.contract.commands.Command;
import org.example.contract.responses.Response;

/**
 * Функциональный интерфейс для обработки команд.
 *
 * <p>Определяет контракт для обработчиков команд, преобразующих команды в ответы. Используется для передачи
 * исполняемого кода лямбда-выражений в качестве параметра метода.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 * @see Response
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface Handler {

    /**
     * Обрабатывает команду и возвращает соответствующий ответ.
     *
     * @param command команда для обработки
     * @return ответ, содержащий результат обработки команды
     */
    Response apply(Command command);
}
