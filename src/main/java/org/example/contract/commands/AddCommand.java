package org.example.contract.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.data.User;
import org.example.contract.data.WorkerDTO;

/**
 * Команда для добавления нового работника в коллекцию.
 * <p>Реализует интерфейс {@link Command} и содержит данные о работнике,
 * который должен быть добавлен в коллекцию.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
@Getter
public class AddCommand implements Command {

    /**
     * Объект передачи данных (DTO) с информацией о работнике.
     */
    private WorkerDTO workerDTO;

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
        return "add";
    }
}