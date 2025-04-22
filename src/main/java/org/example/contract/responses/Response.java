package org.example.contract.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.contract.utility.StatusCode;

import java.io.Serializable;

/**
 * Абстрактный базовый класс для всех типов ответов в системе.
 * <p>Определяет общую структуру ответов, включая статус выполнения операции.</p>
 *
 * <p><b>Характеристики:</b>
 * <ul>
 *   <li>Реализует {@link Serializable} для поддержки сериализации</li>
 *   <li>Использует Lombok аннотации для генерации:
 *     <ul>
 *       <li>{@code @Getter} - геттеры для всех полей</li>
 *       <li>{@code @Setter} - сеттеры для всех полей</li>
 *       <li>{@code @AllArgsConstructor} - конструктор со всеми полями</li>
 *     </ul>
 *   </li>
 *   <li>Содержит обязательное поле {@code statusCode}</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public abstract class Response implements Serializable {

    /**
     * Код статуса выполнения операции.
     * <p>Определяет результат обработки запроса:
     * <ul>
     *   <li>Успешное выполнение</li>
     *   <li>Клиентские ошибки</li>
     *   <li>Серверные ошибки</li>
     * </ul>
     * </p>
     */
    private StatusCode statusCode;
}