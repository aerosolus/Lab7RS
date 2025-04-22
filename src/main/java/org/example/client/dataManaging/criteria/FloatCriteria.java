package org.example.client.dataManaging.criteria;

/**
 * Критерий для проверки, что строка представляет собой число с плавающей точкой (float).
 * <p>
 * Проверяет возможность преобразования строки в тип {@link Float} через {@link Float#parseFloat(String)}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Criteria
 */
public class FloatCriteria implements Criteria {

    /**
     * Проверяет, может ли строка быть преобразована в число типа float.
     *
     * @param value проверяемая строка
     * @return {@code true} - если строка успешно конвертируется в float,
     *         {@code false} - во всех остальных случаях
     */
    @Override
    public boolean check(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Возвращает стандартное сообщение об ошибке для некорректных значений.
     *
     * @return Строка сообщения об ошибке
     */
    @Override
    public String errorMessage() {
        return "Поле должно быть числом с плавающей точкой!";
    }
}
