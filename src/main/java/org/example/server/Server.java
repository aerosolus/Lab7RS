package org.example.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.contract.data.Worker;
import org.example.server.dataManaging.CollectionManager;
import org.example.server.database.*;
import org.example.server.utility.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Главный класс сервера приложения. Отвечает за инициализацию и запуск сервера,
 * управление подключением к базе данных и настройку компонентов приложения.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class Server {

    /**
     * Логгер для записи событий и ошибок сервера.
     */

    public static final Logger LOGGER = LogManager.getLogger(Server.class);

    /**
     * Точка входа в приложение. Запускает сервер и инициализирует все необходимые компоненты.
     *
     * @param args массив аргументов командной строки.
     *             Должен содержать один элемент - путь к файлу с учетными данными
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Введите путь к файлу с данными для подключения к БД в качестве аргумента командной строки.");
            System.exit(1);
        } else {
            try {
                Scanner credentials = new Scanner(new FileReader(args[0].trim()));
                String userName = credentials.nextLine().trim();
                String password = credentials.nextLine().trim();
                initCollection(userName, password);
            } catch (FileNotFoundException e) {
                System.err.println("Не найден файл credentials.txt с данными для подключения к БД.");
                System.out.println("Работа программы завершена.");
                System.exit(1);
            } catch (NoSuchElementException e) {
                System.err.println("Данные для подключения к БД не найдены в файле.");
                System.out.println("Работа программы завершена.");
                System.exit(1);
            }
            initServerAppContainer();
            RequestHandler requestHandler = new RequestHandler(ServerApplicationContainer.getInstance().getCommandManager(), LOGGER);
            TCPServer server = new TCPServer(ServerApplicationContainer.getInstance().getCommandManager(), requestHandler, LOGGER);
            try {
                LOGGER.info("Сервер запущен.");
                server.openConnection();
                server.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    server.close();
                } catch (IOException e) {
                    System.err.println("Ошибка ввода-вывода при завершении работы сервера.");
                    System.out.println("Работа программы прекращена.");
                    System.exit(1);
                }
            }
        }
    }

    /**
     * Инициализирует контейнер приложения и устанавливает менеджер команд.
     */
    private static void initServerAppContainer(){
        ServerApplicationContainer app = ServerApplicationContainer.getInstance();
        CommandManager commandManager = new CommandManager();
        app.setCommandManager(commandManager);
    }

    /**
     * Инициализирует коллекцию работников и устанавливает соединения с базой данных.
     *
     * @param userName имя пользователя БД
     * @param password пароль пользователя БД
     * @throws SQLException если возникла ошибка при работе с БД
     * @throws ClassNotFoundException если отсутствует драйвер БД
     */
    private static void initCollection(String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
            Database db = new DatabaseManager("jdbc:postgresql://localhost:9999/studs", userName, password);
            DAO workerDAO = db.createWorkersConnection();
            DAO userDAO = db.createUserConnection();
            DAO userWorkerReferenceConnection = db.createUserWorkerReferenceConnection();
            Vector<Worker> workerVector = new Vector<>();
            CollectionManager collectionManager = new CollectionManager(workerVector);
//            CopyOnWriteArrayList<Worker> workerCopyOnWriteArrayList = new CopyOnWriteArrayList<>();
//            CollectionManager collectionManager = new CollectionManager(workerCopyOnWriteArrayList);
            collectionManager.setWorkerDAO((WorkerDAO) workerDAO);
            collectionManager.setUserDAO((UserDAO) userDAO);
            collectionManager.setUserWorkerReferenceDAO((UserWorkerReferenceDAO) userWorkerReferenceConnection);
            UserManager userManager = new UserManager();
            userManager.setUserDAO((UserDAO) userDAO);
            ServerApplicationContainer.getInstance().setCollectionManager(collectionManager);
            ServerApplicationContainer.getInstance().getCollectionManager().loadCollectionFromDB();
            ServerApplicationContainer.getInstance().setUserManager(userManager);
        } catch (SQLException e) {
            System.err.println("Ошибка при взаимодействии с БД.");
            System.out.println("Работа программы завершена.");
            System.exit(1);
        } catch (ClassNotFoundException e ) {
            System.err.println("Отсутствует драйвер для БД.");
            System.out.println("Работа программы завершена.");
            System.exit(1);
        }
    }
}
