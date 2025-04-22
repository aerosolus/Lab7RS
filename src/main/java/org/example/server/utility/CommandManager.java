package org.example.server.utility;

import org.example.contract.commands.*;
import org.example.contract.exceptions.EmptyHistoryException;
import org.example.contract.responses.Response;
import org.example.server.handlers.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Менеджер для обработки и выполнения клиентских команд.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class CommandManager {

    /**
     * Карта сопоставления классов команд с их обработчиками.
     * <p>Ключ: класс команды ({@link Command})</p>
     * <p>Значение: обработчик команды ({@link Handler})</p>
     */
    private final Map<Class, Handler> commands;

    /**
     * История выполненных команд (последние 5 команд).
     *
     * <p>Хранит только имена команд без аргументов.</p>
     */
    private final LinkedList<String> commandHistory = new LinkedList<>();

    /**
     * Создание менеджера команд и инициализация обработчиков.
     *
     * <p>Регистрирует обработчики для всех поддерживаемых команд.</p>
     */
    public CommandManager() {
        commands = new HashMap<>();
        commands.put(HelpCommand.class, command -> new HelpHandler().handle((HelpCommand) command));
        commands.put(InfoCommand.class, command -> new InfoHandler().handle((InfoCommand) command));
        commands.put(ShowCommand.class, command -> new ShowHandler().handle((ShowCommand) command));
        commands.put(AddCommand.class, command -> new AddHandler().handle((AddCommand) command));
        commands.put(UpdateCommand.class, command -> new UpdateHandler().handle((UpdateCommand) command));
        commands.put(RemoveByIdCommand.class, command -> new RemoveByIdHandler().handle((RemoveByIdCommand) command));
        commands.put(ClearCommand.class, command -> new ClearHandler().handle((ClearCommand) command));
        commands.put(ExecuteScriptCommand.class, command -> new ExecuteScriptHandler().handle((ExecuteScriptCommand) command));
        commands.put(AddIfMaxCommand.class, command -> new AddIfMaxHandler().handle((AddIfMaxCommand) command));
        commands.put(RemoveLowerCommand.class, command -> new RemoveLowerHandler().handle((RemoveLowerCommand) command));
        commands.put(HistoryCommand.class, command -> new HistoryHandler().handle((HistoryCommand) command));
        commands.put(CountGreaterThanPersonCommand.class, command -> new CountGreaterThanPersonHandler().handle((CountGreaterThanPersonCommand) command));
        commands.put(FilterLessThanSalaryCommand.class, command -> new FilterLessThanSalaryHandler().handle((FilterLessThanSalaryCommand) command));
        commands.put(PrintFieldAscendingPersonCommand.class, command -> new PrintFieldAscendingPersonHandler().handle((PrintFieldAscendingPersonCommand) command));
        commands.put(SignInCommand.class, command -> new SignInHandler().handle((SignInCommand) command));
        commands.put(SignUpCommand.class, command -> new SignUpHandler().handle((SignUpCommand) command));
    }

    /**
     * Выполняет команду и возвращает результат.
     *
     * <p>Действия:</p>
     * <ol>
     *   <li>Добавляет команду в историю</li>
     *   <li>Находит соответствующий обработчик</li>
     *   <li>Выполняет команду через обработчик</li>
     *   <li>Возвращает результат выполнения</li>
     * </ol>
     *
     * @param command команда для выполнения
     * @return результат выполнения команды
     */
    public Response executeCommand(Command command) {
        addToHistory(command.getCommandName());
        return commands.get(command.getClass()).apply(command);
    }

    /**
     * Добавляет команду в историю выполненных команд.
     * Если история превышает 5 команд, удаляет самую первую хранимую команду.
     *
     * @param commandName Имя команды для добавления в историю.
     */
    public void addToHistory(String commandName) {
        commandHistory.addLast(commandName);
        if (commandHistory.size() > 5) {
            commandHistory.removeFirst();
        }
    }

    /**
     * Возвращает историю выполненных команд.
     *
     * @return Список последних выполненных команд.
     */
    public String getHistory() {
        try {
            LinkedList<String> history = commandHistory;
            if (history.size() < 5) throw new EmptyHistoryException();
            String result = "Последние использованные команды:" + "\n";
            for (String command: history) {
                result = result + " - " + command + "\n";
            }
            return result;
        } catch (EmptyHistoryException exception) {
            return "Было использовано недостаточное количество команд.";
        } catch (NullPointerException exp) {
            return "История команд пуста.";
        }
    }
}
