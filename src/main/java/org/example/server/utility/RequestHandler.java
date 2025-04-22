package org.example.server.utility;

import org.apache.logging.log4j.Logger;
import org.example.contract.commands.Command;
import org.example.contract.responses.Response;
import org.example.contract.utility.Serializer;

import java.nio.ByteBuffer;

/**
 * Обработчик запросов, преобразующий входящие данные в команды и выполняющий их.
 * <p>Основной класс для обработки сетевых запросов, который:</p>
 * <ol>
 *   <li>Десериализует входящий ByteBuffer в объект Command</li>
 *   <li>Передает команду в CommandManager для выполнения</li>
 *   <li>Возвращает результат выполнения в виде Response</li>
 * </ol>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class RequestHandler {

    /**
     * Менеджер команд, используемый для реализации бизнес-логики
     */
    private final CommandManager commandManager;

    /**
     * Логгер для записи событий обработки
     */
    private final Logger LOGGER;

    /**
     * Создает обработчик запросов с переданными параметрами.
     *
     * @param commandManager менеджер команд для обработки
     * @param LOGGER логгер для записи событий
     */
    public RequestHandler(CommandManager commandManager, Logger LOGGER) {
        this.commandManager = commandManager;
        this.LOGGER = LOGGER;
    }

    /**
     * Обрабатывает входящий запрос и возвращает результат выполнения.
     *
     * @param buffer буфер с сериализованной командой
     * @param <T> тип ожидаемого ответа
     * @return результат выполнения команды
     * @throws RuntimeException если произошла ошибка
     */
    public <T extends Response> T handleRequest(ByteBuffer buffer) {
        T response;
        Command command;
        try {
            command = Serializer.deserializeObject(buffer);
            System.out.println("Получено: " + command.getClass()); //Вывод, что от клиента пришло
            LOGGER.info("Получено: {}", command.getClass());
            response = (T) commandManager.executeCommand(command);
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
