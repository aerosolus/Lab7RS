package org.example.contract.exceptions;

/**
 * Исключение, выбрасываемое при попытке доступа к пустой или недостаточно заполненной истории команд.
 * Это исключение указывает на то, что история команд содержит менее 5 элементов.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class EmptyHistoryException extends Exception {
}