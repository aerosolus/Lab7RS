package org.example.client.utility;

import lombok.Getter;
import lombok.Setter;
import org.example.client.builders.CommandBuilder;
import org.example.contract.utility.BufferedLineReader;

import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Контейнер для управления состоянием клиентского приложения и его зависимостями.
 * <p>
 * Реализован как синглтон, обеспечивающий единую точку доступа к:
 * <ul>
 *   <li>Потоку ввода ({@link BufferedLineReader})</li>
 *   <li>Стеку выполняемых скриптов</li>
 *   <li>Провайдеру строителей команд</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see CommandBuilder
 * @see BufferedLineReader
 */
@Getter
@Setter
public class ClientApplicationContainer {

    /**
     * Единственный экземпляр контейнера (реализация синглтона)
     */
    private static ClientApplicationContainer instance;

    /**
     * Адаптер для чтения пользовательского ввода.
     */
    private BufferedLineReader bufferedLineReader;

    /**
     * Стек выполняемых скриптов для предотвращения рекурсии.
     * Хранит файлы скриптов, которые находятся в процессе выполнения.
     */
    private final Deque<File> scriptsStack = new ArrayDeque<>();

    /**
     * Провайдер реализации {@link CommandBuilder} для динамического создания команд.
     */
    private CommandBuilderProviderFrame<CommandBuilder> commandBuilderProvider = new CommandBuilderProvider();

    /**
     * Приватный конструктор для реализации паттерна синглтон.
     */
    private ClientApplicationContainer() {
    }

    /**
     * Возвращает единственный экземпляр контейнера.
     *
     * @return существующий или новый экземпляр {@link ClientApplicationContainer}
     */
    public static ClientApplicationContainer getInstance() {
        if (instance == null) {
            instance = new ClientApplicationContainer();
        }
        return instance;
    }

    /**
     * Проверяет режим работы приложения.
     *
     * @return {@code true} если выполняется обработка скрипта,
     *         {@code false} для интерактивного консольного режима
     */
    public boolean isInteractiveMode() {
        return !scriptsStack.isEmpty();
    }
}
