package org.example.server.handlers;

import org.example.contract.commands.Command;
import org.example.contract.responses.Response;
import org.example.server.utility.ServerApplicationContainer;

/**
 * Абстрактный базовый класс для обработчиков команд сервера.
 * <p>
 * Предоставляет общую инфраструктуру для:
 * <ul>
 *   <li>Доступа к контейнеру приложения через {@link ServerApplicationContainer}</li>
 *   <li>Единого интерфейса обработки команд через метод {@link #handle}</li>
 * </ul>
 * </p>
 *
 * @param <T> конкретный тип обрабатываемой команды, должен наследовать {@link Command}
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see ServerApplicationContainer
 */
public abstract class CommandHandler <T extends Command> {

    /**
     * Контейнер приложения, предоставляющий доступ к:
     * <ul>
     *   <li>Менеджеру коллекции</li>
     *   <li>Логгеру</li>
     *   <li>Другим системным компонентам</li>
     * </ul>
     */
    protected final ServerApplicationContainer app;

    /**
     * Инициализирует обработчик, получая экземпляр контейнера приложения.
     * <p>
     * Использует паттерн Singleton для гарантии единого источника системных ресурсов.
     * </p>
     */
    public CommandHandler() {
        this.app = ServerApplicationContainer.getInstance();
    }

    /**
     * Обрабатывает команду и возвращает результат выполнения.
     *
     * @param command команда для обработки (не может быть null)
     * @return ответ с результатом выполнения
     *
     * @implSpec Реализации должны:
     * <ul>
     *   <li>Выполнять валидацию команды</li>
     *   <li>Реализовывать бизнес-логику приложения</li>
     *   <li>Генерировать соответствующий ответ</li>
     *   <li>Обрабатывать исключения, возвращая Response с ошибкой</li>
     * </ul>
     */
    public abstract Response handle(T command);
}
