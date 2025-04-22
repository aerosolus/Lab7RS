package org.example.client.dataManaging.criteria;

/**
 * Критерий для проверки: превосходит ли числовое значение заданный минимум.
 * <p>
 * Проверяет, что строка представляет собой число с плавающей точкой,
 * которое строго больше указанного значения.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Criteria
 */
public class GreaterThanCriteria implements Criteria {

    /**
     * Минимальное допустимое значение (нижняя граница)
     */
    private final float minimum;

    /**
     * Создает критерий с заданной нижней границей
     * @param minimum значение, с которым происходит сравнение
     */
    public GreaterThanCriteria(float minimum) {
        this.minimum = minimum;
    }

    /**
     * Проверяет корректность значения:
     * <ol>
     *   <li>Пытается преобразовать строку в число типа float</li>
     *   <li>Сравнивает полученное значение с минимальным</li>
     * </ol>
     *
     * @param value проверяемая строка
     * @return true если:
     *         <ul>
     *           <li>Строка успешно конвертируется в float</li>
     *           <li>Полученное число больше minimum</li>
     *         </ul>
     *         false во всех остальных случаях
     */
    @Override
    public boolean check(String value) {
        try {
            return Float.parseFloat(value) > minimum;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Возвращает сообщение об ошибке с указанием требуемого минимума
     *
     * @return Строка сообщения об ошибке
     */
    @Override
    public String errorMessage() {
        return "Поле должно быть больше, чем " + minimum;
    }
}
