package org.example.contract.responses;

import org.example.contract.utility.StatusCode;

/**
 * Ответ, информирующий о завершении работы клиентского приложения.
 *
 * <p>Наследует от базового класса {@link Response} и используется для передачи
 * статуса завершения работы клиент-серверного взаимодействия.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class ExitResponse extends Response {

    /**
     * Создает ответ о завершении работы клиента с указанным статусом.
     *
     * @param statusCode код статуса завершения
     */
    public ExitResponse(StatusCode statusCode) {
        super(statusCode);
    }
}