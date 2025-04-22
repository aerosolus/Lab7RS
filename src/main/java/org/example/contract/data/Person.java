package org.example.contract.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс, представляющий человека с такими характеристиками, как рост, цвет глаз, цвет волос и национальность.
 * У человека приводятся следующие параметры: {@code height} типа {@code Float}, {@code eyeColor} типа {@code Color},
 * {@code hairColor} типа {@code Color} и {@code nationality} типа {@code Country}.
 *
 * <p>Этот класс поддерживает создание объектов при помощи паттерна строитель (Builder).
 * Он также предоставляет методы для получения и установки значений его параметров,
 * для сравнения объектов данного класса, представления их в виде строк.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class Person implements Comparable<Person>, Serializable {

    /**
     * Рост человека. Поле не может быть null, значение должно быть больше 0.
     */
    private Float height;

    /**
     * Цвет глаз человека. Поле может быть null.
     */
    private EyeColor eyeColor;

    /**
     * Цвет волос человека. Поле может быть null.
     */
    private HairColor hairColor;

    /**
     * Национальность человека. Поле не может быть null.
     */
    private Country nationality;

    /**
     * Конструктор, используемый для инициализации объекта Person.
     *
     * @param height Рост человека.
     * @param eyeColor Цвет глаз человека.
     * @param hairColor Цвет волос человека.
     * @param nationality Национальность человека.
     */
    public Person(Float height, EyeColor eyeColor, HairColor hairColor, Country nationality) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }

    /**
     * Возвращает строковое представление объекта Person.
     *
     * @return Строка в формате "Person{height=..., eyeColor=..., hairColor=..., nationality=...}".
     *
     * @see Person
     */
    @Override
    public String toString() {
        return "Person{" +
                "height=" + height +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                ", nationality=" + nationality +
                '}';
    }

    /**
     * Сравнивает текущий объект Person с другим объектом Person.
     * Сравнение выполняется с использованием значений всех полей объекта.
     * Первоначально для сравнения используется рост объектов класса Person.
     *
     * @param o Объект Person для сравнения.
     * @return Отрицательное целое число, ноль или положительное целое число,
     * если первый аргумент меньше, равен или больше второго соответственно.
     */
    @Override
    public int compareTo(Person o) {
        int heightComparison = this.height.compareTo(o.height);
        if (heightComparison != 0) {
            return heightComparison; // Если рост разный, возвращаем результат сравнения по росту
        }

        // Если рост одинаковый, сравниваем по цвету глаз
        int eyeColorComparison = this.eyeColor.compareTo(o.eyeColor);
        if (eyeColorComparison != 0) {
            return eyeColorComparison; // Если цвет глаз разный, возвращаем результат сравнения по цвету глаз
        }

        // Если цвет глаз одинаковый, сравниваем по цвету волос
        int hairColorComparison = this.hairColor.compareTo(o.hairColor);
        if (hairColorComparison != 0) {
            return eyeColorComparison; // Если цвет волос разный, возвращаем результат сравнения по цвету волос
        }

        // Если цвет волос одинаковый, сравниваем по национальности
        return this.nationality.compareTo(o.nationality); // Если национальность разная, возвращаем результат сравнения по национальности

        // Если все поля одинаковые, объекты считаются равными
    }

    /**
     * Проверяет текущий объект Person с другим объектом на равенство.
     *
     * @param o Объект для сравнения.
     * @return true, если объекты равны по значениям height, eyeColor, hairColor, nationality, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(height, person.height) && eyeColor == person.eyeColor && hairColor == person.hairColor && nationality == person.nationality;
    }

    /**
     * Определяет хэш-код объекта Person.
     *
     * @return Хэш-код, вычисленный на основе значений height, eyeColor, hairColor, nationality.
     */
    @Override
    public int hashCode() {
        return Objects.hash(height, eyeColor, hairColor, nationality);
    }
}
