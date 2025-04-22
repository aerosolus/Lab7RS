package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.dataManaging.WorkerCreator;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.AddIfMaxCommand;
import org.example.contract.commands.Command;
import org.example.contract.data.User;
import org.example.contract.data.WorkerDTO;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link AddIfMaxCommand}.
 * <p>
 * Формирует команду добавления элемента в коллекцию, если его значение превышает максимальное в текущей коллекции.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see AddIfMaxCommand
 * @see WorkerCreator
 */
@NoArgsConstructor
public class AddIfMaxCommandBuilder implements CommandBuilder {

    /**
     * Создает команду добавления элемента с проверкой на максимальное значение.
     * <p>
     * Шаги работы:
     * <ol>
     *   <li>Проверяет наличие ровно 1 аргумента</li>
     *   <li>Интерактивно запрашивает данные работника через {@link WorkerCreator}</li>
     *   <li>Инкапсулирует данные в команду</li>
     * </ol>
     * </p>
     *
     * @param str массив аргументов
     * @return команду {@link AddIfMaxCommand} с заполненным объектом данных
     * @throws InvalidArgumentException если количество аргументов некорректно
     *
     * @implSpec
     * - Аргументы игнорируются, но их наличие требуется для совместимости с интерфейсом
     * - Ввод данных осуществляется через стандартный ввод (консоль)
     */
    @Override
    public Command build(String[] str) {
        if (str.length != 1) throw new InvalidArgumentException();
        WorkerCreator workerCreator = new WorkerCreator();
        WorkerDTO workerDTO = workerCreator.createWorker();
        User user = TerminalManager.getUser();
        return new AddIfMaxCommand(workerDTO, user);
    }
}
