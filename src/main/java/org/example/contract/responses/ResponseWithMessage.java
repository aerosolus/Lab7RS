package org.example.contract.responses;

import lombok.Getter;
import org.example.contract.utility.StatusCode;

/**
 * Ответ, содержащий текстовое сообщение в дополнение к статусу выполнения.
 *
 * <p>Наследует от базового класса {@link Response} и добавляет текстовое сообщение,
 * которое содержит результат выполнения операции.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
public class ResponseWithMessage extends Response {

    /**
     * Текстовое сообщение, содержащее результат выполнения операции.
     */
    private String message;

    /**
     * Создает ответ с сообщением.
     *
     * @param statusCode код статуса выполнения операции
     * @param message строковое сообщение
     */
    public ResponseWithMessage(StatusCode statusCode, String message) {
        super(statusCode);
        this.message = message;
    }
}