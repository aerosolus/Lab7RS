package org.example.client.dataManaging.criteria;

/**
 * Критерий для проверки соответствия значения предопределенным константам перечисления.
 * <p>
 * Используется для валидации ввода, который должен соответствовать одному из значений перечисления.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Criteria
 */
public class EnumCriteria implements Criteria {

    /**
     * Допустимые значения перечисления, используемые при проверке
     */
    private final Enum<?>[] enumValues;

    /**
     * Создает критерий с указанными значениями enum
     *
     * @param enumValues массив допустимых значений перечисления
     */
    public EnumCriteria(Enum<?>[] enumValues) {
        this.enumValues = enumValues;
    }

    /**
     * Проверяет, соответствует ли значение одному из элементов enum
     *
     * @param value проверяемая строка (чувствительна к регистру)
     * @return true - если значение совпадает с toString() любого элемента enum,
     *         false - во всех остальных случаях
     */
    public boolean check(String value) {
        try {
            for (Enum<?> enumValue : enumValues) {
                if (value.equals(enumValue.toString())) {
                    return true;
                }
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Генерирует сообщение об ошибке со списком допустимых значений
     *
     * @return Строка формата: "Поле должно принимать одно из следующих значений: VALUE1, VALUE2, ..."
     */
    public String errorMessage() {
        StringBuilder nameToString = new StringBuilder();
        for (Enum<?> enumValue : enumValues) {
            nameToString.append(enumValue).append(", ");
        }
        return "Поле должно принимать одно из следующих значений: " + nameToString.substring(0, nameToString.length()-2);
    }
}
