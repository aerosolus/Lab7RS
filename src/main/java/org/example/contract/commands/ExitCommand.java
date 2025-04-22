package org.example.contract.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Команда завершения работы клиентского приложения.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */

@NoArgsConstructor
@Getter
public class ExitCommand implements Command {

    /**
     * Возвращает системное имя команды.
     *
     * @return имя команды
     */
    @Override
    public String getCommandName() {
        return "exit";
    }
}
