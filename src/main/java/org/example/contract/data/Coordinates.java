package org.example.contract.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Класс, представляющий координаты в двумерном пространстве.
 * Координаты имеют два параметра: x типа {@code Long} и y типа {@code long}.
 *
 * <p> Класс также предоставляет методы для получения и установки значений координат,
 * для сравнения объектов данного класса, представления их в виде строк.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates implements Serializable {

    /**
     * Координата y
     */
    private Long x;

    /**
     * Координата y
     */
    private long y;

    /**
     * Возвращает строковое представление объекта Coordinates.
     * Этот метод предоставляет описание объекта,
     * включая все поля и их значения.
     *
     * @return Строковое представление объекта Coordinates.
     *
     * @see Coordinates
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
