package org.example.client.dataManaging.criteria;

/**
 * Критерий для проверки, что строка представляет собой целое число типа {@link Long}.
 * <p>
 * Проверяет возможность преобразования строки в тип {@code Long} через {@link Long#parseLong(String)}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Criteria
 */
public class LongCriteria implements Criteria {

    /**
     * Проверяет, может ли строка быть преобразована в long.
     *
     * @param value проверяемая строка
     * @return {@code true} - если строка является корректным long,
     *         {@code false} - во всех остальных случаях
     */
    public boolean check(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Возвращает сообщение об ошибке для некорректных значений.
     *
     * @return Строка сообщения об ошибке
     */
    @Override
    public String errorMessage() {
        return "Поле должно иметь целочисленный тип!";
    }
}
