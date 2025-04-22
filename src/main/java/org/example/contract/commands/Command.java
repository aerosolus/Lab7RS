package org.example.contract.commands;

import java.io.Serializable;

/**
 * Интерфейс, представляющий абстрактную команду для использования в программе.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public interface Command extends Serializable {

    /**
     * Возвращает уникальное имя команды.
     *
     * @return Строка с именем команды
     */
    String getCommandName();
}