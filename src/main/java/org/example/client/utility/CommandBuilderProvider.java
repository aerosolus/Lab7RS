package org.example.client.utility;

import org.example.client.builders.*;
import org.example.contract.commands.*;

import java.util.HashMap;

/**
 * Реализация {@link CommandBuilderProviderFrame}, предоставляющая строители команд на основе их типа.
 * <p>
 * Содержит предустановленное сопоставление классов команд с соответствующими {@link CommandBuilder}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandBuilderProviderFrame
 */
public class CommandBuilderProvider implements CommandBuilderProviderFrame<CommandBuilder>{

    /**
     * Статическая карта для хранения сопоставлений:
     * <ul>
     *   <li>Ключ: класс команды, реализующий {@link Command}</li>
     *   <li>Значение: соответствующий строитель команды</li>
     * </ul>
     */
    static HashMap<Class, CommandBuilder> commandBuilders = new HashMap<>();

    /**
     * Инициализирует карту сопоставлений команд и строителей.
     * <p>
     * Регистрирует обработчики для команд.
     * </p>
     */
    public CommandBuilderProvider() {
        commandBuilders.put(HelpCommand.class, new HelpCommandBuilder());
        commandBuilders.put(InfoCommand.class, new InfoCommandBuilder());
        commandBuilders.put(ShowCommand.class,new ShowCommandBuilder());
        commandBuilders.put(AddCommand.class, new AddCommandBuilder());
        commandBuilders.put(UpdateCommand.class,new UpdateCommandBuilder());
        commandBuilders.put(RemoveByIdCommand.class,new RemoveByIdCommandBuilder());
        commandBuilders.put(ClearCommand.class, new ClearCommandBuilder());
        commandBuilders.put(ExecuteScriptCommand.class,new ExecuteScriptCommandBuilder());
        commandBuilders.put(ExitCommand.class, new ExitCommandBuilder());
        commandBuilders.put(AddIfMaxCommand.class, new AddIfMaxCommandBuilder());
        commandBuilders.put(RemoveLowerCommand.class, new RemoveLowerCommandBuilder());
        commandBuilders.put(HistoryCommand.class, new HistoryCommandBuilder());
        commandBuilders.put(CountGreaterThanPersonCommand.class, new CountGreaterThanPersonCommandBuilder());
        commandBuilders.put(FilterLessThanSalaryCommand.class, new FilterLessThanSalaryCommandBuilder());
        commandBuilders.put(PrintFieldAscendingPersonCommand.class, new PrintFieldAscendingPersonCommandBuilder());
    }

    /**
     * Возвращает строитель команд для указанного класса команды.
     *
     * @param aClass класс команды, реализующий {@link Command}
     * @return соответствующий {@link CommandBuilder}
     */
    @Override
    public CommandBuilder provide(Class<? extends Command> aClass) {
        return commandBuilders.get(aClass);
    }
}
