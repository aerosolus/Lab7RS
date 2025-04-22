package org.example.server.utility;

import lombok.Getter;
import lombok.Setter;
import org.example.server.dataManaging.CollectionManager;

/**
 * Контейнер приложения сервера, реализующий шаблон Singleton.
 * <p>Обеспечивает централизованный доступ к основным компонентам сервера:</p>
 * <ul>
 *   <li>{@link CollectionManager} - менеджер коллекции данных</li>
 *   <li>{@link CommandManager} - менеджер обработки команд</li>
 * </ul>
 *
 * <p><b>Особенности реализации:</b>
 * <ul>
 *   <li>Использует шаблон Singleton для гарантии единственного экземпляра</li>
 *   <li>Применяет Lombok {@code @Getter} и {@code @Setter} для генерации методов доступа</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
public class ServerApplicationContainer {

    /**
     * Единственный экземпляр контейнера.
     */
    private static ServerApplicationContainer instance;

    /**
     * Менеджер для работы с коллекцией.
     */
    private CollectionManager collectionManager;

    /**
     * Менеджер для обработки команд.
     */
    private CommandManager commandManager;

    /**
     * Менеджер для управления пользователями
     */
    private UserManager userManager;

    /**
     * Приватный конструктор для реализации Singleton.
     */
    private ServerApplicationContainer() {
    }

    /**
     * Возвращает единственный экземпляр контейнера.
     *
     * <p>Реализует ленивую инициализацию - экземпляр создается только при первом вызове.</p>
     *
     * @return единственный экземпляр ServerApplicationContainer
     */
    public static ServerApplicationContainer getInstance() {
        if (instance == null) {
            instance = new ServerApplicationContainer();
        }
        return instance;
    }
}
