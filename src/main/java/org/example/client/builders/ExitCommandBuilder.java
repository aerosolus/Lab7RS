package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.contract.commands.Command;
import org.example.contract.commands.ExitCommand;
import org.example.contract.exceptions.InvalidArgumentException;

/**
 * Реализация {@link CommandBuilder} для создания команды {@link ExitCommand}.
 * <p>
 * Команда завершает работу клиентского приложения. Не требует аргументов.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see ExitCommand
 */
@NoArgsConstructor
public class ExitCommandBuilder implements CommandBuilder {


    /**
     * Создает команду завершения работы клиента.
     *
     * @param str имя команды
     *
     * @return команду {@link ExitCommand}
     * @throws InvalidArgumentException если количество аргументов некорректно
     */
    @Override
    public Command build(String[] str) {
        if(str.length != 1) throw new InvalidArgumentException();
        return new ExitCommand();
    }
}
