package org.example.client.utility;

import org.example.client.builders.CommandBuilder;
import org.example.contract.commands.Command;

/**
 * Обобщенный интерфейс для предоставления строителей команд ({@link CommandBuilder}).
 * <p>
 * Определяет механизм получения конкретной реализации строителя команд по типу команды.
 * </p>
 *
 * @param <T> тип строителя команд, должен наследовать {@link CommandBuilder}
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandBuilder
 */
public interface CommandBuilderProviderFrame <T extends CommandBuilder> {

    /**
     * Возвращает строитель команд для указанного типа команды.
     * <p>
     * Реализации должны обеспечивать соответствие между:
     * <ul>
     *   <li>Классом команды (наследником {@link Command})</li>
     *   <li>Конкретной реализацией {@link CommandBuilder}</li>
     * </ul>
     * </p>
     *
     * @param commandClass класс команды, для которой требуется строитель
     * @return реализацию строителя команд типа {@code T}
     */
    T provide(Class<? extends Command> commandClass);
}
