package org.example.contract.exceptions;

import lombok.Getter;

import java.util.List;

/**
 * Исключение, выбрасываемое при ошибках валидации данных.
 *
 * <p>Содержит список всех обнаруженных ошибок валидации.</p>
 *
 * <p> Использует Lombok {@code @Getter} для доступа к ошибкам</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
public class ValidationException extends RuntimeException {

    /**
     * Список сообщений об ошибках валидации,
     * каждый элемент которого содержит описание конкретной ошибки.
     */
    private final List<String> errors;

    /**
     * Создает исключение с указанным списком ошибок.
     *
     * @param errors список сообщений об ошибках валидации.
     */
    public ValidationException(List<String> errors) {
        this.errors = errors;
    }
}
