package org.example.client.utility;

/**
 * Интерфейс-маркер для команд, работающих с идентификаторами (ID).
 * <p>
 * Содержит стандартную реализацию проверки корректности ID в строковом формате.
 * Реализующие классы могут использовать этот метод для проверки аргументов на корректность.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public interface CommandWithID {

    /**
     * Проверяет, может ли строка быть преобразована в числовой ID.
     * <p>
     * Валидация выполняется по следующим правилам:
     * <ul>
     *   <li>Строка должна содержать только цифры</li>
     *   <li>Значение должно находиться в диапазоне типа {@link Long}</li>
     * </ul>
     * </p>
     *
     * @param arg проверяемая строка с ID
     * @return {@code true} если строка является корректным ID, {@code false} в противном случае
     */
    default boolean checkArgForId(String arg){
        try {
            Long.parseLong(arg);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
