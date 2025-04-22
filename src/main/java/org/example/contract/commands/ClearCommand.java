package org.example.contract.commands;

import lombok.Getter;
import lombok.AllArgsConstructor;
import org.example.contract.data.User;

/**
 * Команда очистки коллекции.
 * <p>Удаляет все элементы из текущей коллекции. Не требует дополнительных параметров.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */
@AllArgsConstructor
@Getter
public class ClearCommand implements Command {

    /**
     * Пользователь, использующий команду
     */
    private User user;

    /**
     * Возвращает имя команды.
     *
     * @return имя команды
     */
    @Override
    public String getCommandName() {
        return "clear";
    }
}