package org.example.contract.data;

import java.io.Serializable;

/**
 * Перечисление, представляющее различные цвета волос.
 * Каждая константа перечисления соответствует определённому цвету.
 *
 * <p> Это перечисление может использоваться для представления цвета волос в различных контекстах, например,
 * при выборе его пользователем. </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public enum HairColor implements Serializable {

    /**
     * Зеленый
     */
    GREEN,

    /**
     * Красный
     */
    RED,

    /**
     * Черный
     */
    BLACK,

    /**
     * Синий
     */
    BLUE,

    /**
     * Белый
     */
    WHITE;

    /**
     * Преобразует все значения перечисления в строку, разделённую запятыми.
     * Используется для отображения или логирования всех доступных цветов.
     *
     * @return строка, содержащая все значения перечисления, разделённые запятыми.
     */
    public static String nameToString() {
        StringBuilder nameToString = new StringBuilder();
        for (HairColor hairColor : values()) {
            nameToString.append(hairColor.name()).append(", ");
        }
        return nameToString.substring(0, nameToString.length()-2);
    }
}
