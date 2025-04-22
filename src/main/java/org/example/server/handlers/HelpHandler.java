package org.example.server.handlers;

import org.example.contract.commands.HelpCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utility.StatusCode;

import java.util.ArrayList;

/**
 * Обработчик команды вывода справки по доступным командам.
 * <p>
 * Класс наследует функциональность базового обработчика команд {@link CommandHandler}
 * и применяется для обработки {@link HelpCommand}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandHandler
 * @see HelpCommand
 * @see ResponseWithMessage
 */
public class HelpHandler extends CommandHandler<HelpCommand> {

    /**
     * Обрабатывает команду вывода справки, возвращая список доступных команд.
     * <p>
     * Формирует ответ с перечислением всех поддерживаемых команд и их кратким описанием.
     * </p>
     *
     * @param command объект команды {@link HelpCommand}
     *
     * @return {@link ResponseWithMessage} с:
     *         <ul>
     *             <li>Статус {@link StatusCode#_200_SUCCESS_}</li>
     *             <li>Строка со списком команд</li>
     *         </ul>
     */
    @Override
    public Response handle(HelpCommand command) {
        ArrayList<String> help_out = new ArrayList<>();
        help_out.add("help : вывести справку по доступным командам");
        help_out.add("info : вывести в стандартный поток вывода информацию о коллекции");
        help_out.add("show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        help_out.add("add : добавить новый элемент в коллекцию");
        help_out.add("update : обновить значение элемента коллекции, id которого равен заданному");
        help_out.add("remove_by_id : удалить элемент из коллекции по его id");
        help_out.add("clear : очистить коллекцию");
        //help_out.add("save : сохранить коллекцию в файл");
        help_out.add("execute_script : считать и исполнить скрипт из указанного файла");
        //help_out.add("exit : завершить программу (без сохранения в файл)");
        help_out.add("add_if_max : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        help_out.add("remove_lower : удалить из коллекции все элементы, меньшие, чем заданный");
        help_out.add("history : вывести последние 5 команд (без их аргументов)");
        help_out.add("count_greater_than_person : вывести количество элементов, значение поля person которых больше заданного");
        help_out.add("filter_less_than_salary : вывести элементы, значение поля salary которых меньше заданного");
        help_out.add("print_field_ascending_person : вывести значения поля person всех элементов в порядке возрастания");
        String output = "";
        output += "Список доступных команд:\n";
        for (String comhelp : help_out) {
            output += comhelp + "\n";
        }
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, output);
    }
}
