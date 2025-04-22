package org.example.contract.data;

import java.io.Serializable;

/**
 * Перечисление, представляющее различные цвета глаз.
 * Каждая константа перечисления соответствует определённому цвету.
 *
 * <p> Это перечисление может использоваться для представления цвета глаз в различных контекстах, например,
 * при выборе его пользователем. </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public enum EyeColor implements Serializable {

    /**
     * Красный
     */
    RED,

    /**
     * Черный
     */
    BLACK,

    /**
     * Желтый
     */
    YELLOW;

    /**
     * Преобразует все значения перечисления в строку, разделённую запятыми.
     * Используется для отображения или логирования всех доступных цветов глаз.
     *
     * @return строка, содержащая все значения перечисления, разделённые запятыми.
     */
    public static String nameToString() {
        StringBuilder nameToString = new StringBuilder();
        for (EyeColor eyeColor : values()) {
            nameToString.append(eyeColor.name()).append(", ");
        }
        return nameToString.substring(0, nameToString.length() - 2);
    }
}
