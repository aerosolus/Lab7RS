package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.dataManaging.WorkerCreator;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.RemoveLowerCommand;
import org.example.contract.data.WorkerDTO;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link RemoveLowerCommand}.
 * <p>
 * Команда удаляет из коллекции все элементы, меньшие чем заданный.
 * Требует ввода данных работника.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see RemoveLowerCommand
 * @see WorkerCreator
 */
@NoArgsConstructor
public class RemoveLowerCommandBuilder implements CommandBuilder {

    /**
     * Создает команду удаления элементов, меньших чем заданный работник.
     * <p>
     * Workflow:
     * <ol>
     *   <li>Проверяет наличие ровно 1 аргумента (имя команды)</li>
     *   <li>Интерактивно запрашивает данные работника через {@link WorkerCreator}</li>
     *   <li>Формирует команду с полученными данными</li>
     * </ol>
     *
     * @param str имя команды
     *
     * @return команду {@link RemoveLowerCommand} с заданным работником
     * @throws InvalidArgumentException если количество аргументов некорректно
     * @throws org.example.contract.exceptions.ScriptExecutionException при ошибках ввода в режиме скрипта
     */
    @Override
    public Command build(String[] str) {
        if (str.length != 1) throw new InvalidArgumentException();
        WorkerCreator workerCreator = new WorkerCreator();
        WorkerDTO workerDTO = workerCreator.createWorker();
        return new RemoveLowerCommand(workerDTO, TerminalManager.getUser());
    }
}
