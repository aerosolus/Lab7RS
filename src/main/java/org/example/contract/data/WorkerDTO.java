package org.example.contract.data;

import lombok.*;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) для передачи данных о работнике.
 * Реализует интерфейс {@link Serializable} для поддержки сериализации.
 *
 * <p>Класс использует Lombok-аннотации для автоматической генерации:
 * <ul>
 *   <li>Геттеров ({@code @Getter})</li>
 *   <li>Сеттеров ({@code @Setter})</li>
 *   <li>Конструктора со всеми аргументами ({@code @AllArgsConstructor})</li>
 *   <li>Пустого конструктора ({@code @NoArgsConstructor})</li>
 *   <li>Метода {@code toString()} ({@code @ToString})</li>
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
@NoArgsConstructor
@ToString
public class WorkerDTO implements Serializable {

    /**
     * Имя работника.
     * <p><b>Ограничения:</b>
     * <ul>
     *   <li>Не может быть {@code null}</li>
     *   <li>Строка не может быть пустой</li>
     * </ul>
     * </p>
     */
    private String name;

    /**
     * Координаты работника.
     * <p><b>Ограничения:</b>
     * <ul>
     *   <li>Не может быть {@code null}</li>
     * </ul>
     * </p>
     */
    private Coordinates coordinates;

    /**
     * Зарплата работника.
     * <p><b>Ограничения:</b>
     * <ul>
     *   <li>Значение должно быть больше 0</li>
     * </ul>
     * </p>
     */
    private long salary;

    /**
     * Должность работника.
     * <p><b>Ограничения:</b>
     * <ul>
     *   <li>Не может быть {@code null}</li>
     * </ul>
     * </p>
     */
    private Position position;

    /**
     * Статус работника.
     * <p><b>Особенности:</b>
     * <ul>
     *   <li>Может быть {@code null}</li>
     * </ul>
     * </p>
     */
    private Status status;

    /**
     * Личные данные работника.
     * <p><b>Ограничения:</b>
     * <ul>
     *   <li>Не может быть {@code null}</li>
     * </ul>
     * </p>
     */
    private Person person;
}
