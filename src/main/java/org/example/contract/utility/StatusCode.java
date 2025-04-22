package org.example.contract.utility;

/**
 * Перечисление статусных кодов для клиент-серверного взаимодействия.
 * <p>Определяет стандартные коды состояния операций, аналогичные HTTP-статусам.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public enum StatusCode {

    /**
     * Успешное выполнение операции.
     */
    _200_SUCCESS_,

    /**
     * Ошибка клиента.
     */
    _400_CLIENT_ERROR,

    /**
     * Ошибка сервера.
     */
    _500_SERVER_ERROR
}
