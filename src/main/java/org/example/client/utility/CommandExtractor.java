package org.example.client.utility;

import org.example.client.builders.CommandBuilder;
import org.example.contract.commands.*;

import java.util.HashMap;

/**
 * Утилита для преобразования строковых команд в объекты команд.
 * <p>
 * Содержит статическое сопоставление строковых идентификаторов команд с классами команд,
 * а также механизм создания объектов команд через {@link CommandBuilderProvider}. </p>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandBuilderProvider
 * @see ClientApplicationContainer
 */
public class CommandExtractor {

    /**
     * Статическая карта для преобразования строковых команд в классы:
     * <ul>
     *   <li>Ключ: строковый идентификатор команды (например, "help")</li>
     *   <li>Значение: соответствующий класс команды (например, {@link HelpCommand})</li>
     * </ul>
     */
    static HashMap<String, Class<? extends Command>> extractor = new HashMap<>() {{
        put("help", HelpCommand.class);
        put("info", InfoCommand.class);
        put("show", ShowCommand.class);
        put("add", AddCommand.class);
        put("update", UpdateCommand.class);
        put("remove_by_id", RemoveByIdCommand.class);
        put("clear", ClearCommand.class);
        put("execute_script", ExecuteScriptCommand.class);
        put("exit", ExitCommand.class);
        put("add_if_max", AddIfMaxCommand.class);
        put("remove_lower", RemoveLowerCommand.class);
        put("history", HistoryCommand.class);
        put("count_greater_than_person", CountGreaterThanPersonCommand.class);
        put("filter_less_than_salary", FilterLessThanSalaryCommand.class);
        put("print_field_ascending_person", PrintFieldAscendingPersonCommand.class);
    }};

    /**
     * Создает объект команды на основе строкового ввода.
     * <p>
     * Алгоритм работы:
     * <ol>
     *   <li>Извлекает класс команды по первому элементу массива str</li>
     *   <li>Получает соответствующий {@link CommandBuilderProvider} через {@link ClientApplicationContainer}</li>
     *   <li>Строит команду с использованием аргументов</li>
     * </ol>
     * </p>
     *
     * @param str массив строк, где:
     *            <ul>
     *              <li>str[0] - строковый идентификатор команды</li>
     *              <li>str[1] - аргумент команды</li>
     *            </ul>
     * @return созданный объект команды
     */
    public static Command getDTO(String[] str) {
        Class<? extends Command> commandClass = extractor.get(str[0]);
        CommandBuilder builder = ClientApplicationContainer.getInstance().getCommandBuilderProvider().provide(commandClass);
        return builder.build(str);
    }
}
