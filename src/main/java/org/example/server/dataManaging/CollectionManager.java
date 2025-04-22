package org.example.server.dataManaging;

import lombok.Getter;
import lombok.Setter;
import org.example.contract.data.Person;
import org.example.contract.data.Worker;
import org.example.contract.data.WorkerDTO;
import org.example.contract.exceptions.NotOwnerException;
import org.example.server.database.UserDAO;
import org.example.server.database.UserWorkerReferenceDAO;
import org.example.server.database.WorkerDAO;
import org.example.server.utility.WorkerComparator;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Управляет коллекцией объектов {@link Worker}, обеспечивая взаимодействие с базой данных.
 * <p>
 * Предоставляет базовые CRUD-операции и дополнительные методы для работы с коллекцией:
 * добавление, удаление, обновление, загрузку из БД. Использует вектор ({@link Vector})
 * как потокобезопасную реализацию коллекции.
 * </p>
 *
 * <з>Архитектурные особенности:</з>
 * <ul>
 *   <li>Интеграция с DAO-слоем для работы с персистентными данными</li>
 *   <li>Автоматическая генерация getters/setters через Lombok</li>
 *   <li>Потокобезопасные операции с коллекцией</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.2
 * @since 1.0
 */
@Getter
@Setter
public class CollectionManager {

//    /**
//     * Используемая конфигурация коллекции. Потокобезопасный аналог {@code Vector}, который не устарел.
//     */
//    private CopyOnWriteArrayList<Worker> workersCollection;

    /**
     * Основная коллекция для хранения объектов {@link Worker}.
     * <p>
     * Реализована на основе {@link Vector} для обеспечения потокобезопасности.
     * Рассматривался вариант с {@link java.util.concurrent.CopyOnWriteArrayList},
     * но был заменен из-за особенностей работы с частыми операциями записи.
     * </p>
     */
    private Vector<Worker> workersCollection;

    /**
     * Дата инициализации коллекции в памяти приложения.
     * <p>
     * Фиксируется в момент создания экземпляра {@link CollectionManager}.
     * Не связана с датой загрузки данных из БД.
     * </p>
     */
    private final Date initializationDate = new Date();

    /**
     * Data Access Object для операций с сущностью {@link Worker} в базе данных.
     */
    private WorkerDAO workerDAO;

    /**
     * Data Access Object для операций с сущностью {@link org.example.contract.data.User}.
     */
    private UserDAO userDAO;

    /**
     * DAO для управления связями пользователей и работников многие ко многим.
     */
    private UserWorkerReferenceDAO userWorkerReferenceDAO;

    /**
     * Загружает коллекцию из базы данных.
     * <p>
     * Использует {@link WorkerDAO#getAllWorkers()} для получения всех записей.
     * </p>
     *
     * @throws SQLException при ошибках взаимодействия с БД
     */
    public void loadCollectionFromDB() throws SQLException {
        this.workersCollection = this.workerDAO.getAllWorkers();
    }

    /**
     * Конструктор для инициализации пустой коллекции.
     *
     * @param workersCollection начальная коллекция работников.
     * <pre>
     * Версия 1.0:
     *   - Использовал {@link java.util.concurrent.CopyOnWriteArrayList}
     * Версия 1.1:
     *   - Переход на {@link Vector} для улучшения производительности
     *     при частых операциях модификации
     * </pre>
     */
//    public CollectionManager(CopyOnWriteArrayList<Worker> workersCollection) {
//        this.workersCollection = workersCollection;
//    }
    public CollectionManager(Vector<Worker> workersCollection) {
        this.workersCollection = workersCollection;
    }

    /**
     * Возвращает дату инициализации коллекции.
     *
     * @return Дата инициализации коллекции.
     */
    public Date getInitializationDate() {
        return initializationDate;
    }

    /**
     * Проверяет, содержит ли коллекция объект с указанным идентификатором.
     *
     * @param id Идентификатор работника.
     * @return true, если коллекция содержит объект с указанным идентификатором, иначе false.
     */
    public boolean containsId(Long id) {
        return workersCollection.stream().anyMatch(v -> v.getId().equals(id));
    }

    /**
     * Возвращает размер коллекции, иначе говоря, количество объектов в ней.
     *
     * @return размер коллекции
     */
    public int getSize() {
        return workersCollection.size();
    }

    /**
     * Возвращает строку с информацией о коллекции, включая тип коллекции,
     * дату инициализации и количество элементов.
     *
     * @return Строка с информацией о коллекции.
     */
    public String getCollectionInfo() {
        return "Тип коллекции: " + workersCollection.getClass().getName().split("\\.")[2] + "\n" +
                "Дата инициализации: " + getInitializationDate().toString() +
                "\n" + "Количество элементов в коллекции: " + workersCollection.size();
    }

    /**
     * Очищает коллекцию, удаляя все элементы, которые принадлежат пользователю.
     */
    public void clearCollection(String ownerLogin) throws SQLException {
        for(Long id: userWorkerReferenceDAO.getUserWorkers(ownerLogin)) {
            workerDAO.removeById(id);
        }
        loadCollectionFromDB();
    }

    /**
     * Добавляет новый объект {@link Worker} в коллекцию.
     */
    public void add(WorkerDTO workerDTO, String ownerLogin) throws SQLException {
        long id = workerDAO.addWorker(workerDTO, ownerLogin);
        userWorkerReferenceDAO.addRelationship(id, ownerLogin);
        loadCollectionFromDB();
    }

    /**
     * Проверяет, является ли объект {@link Worker} максимальным в сравнении со всеми объектами коллекции.
     *
     * @param workerDTO объект для сравнения
     * @return true, если объект максимален, false в противном случае
     */
    public boolean isMax(WorkerDTO workerDTO) {
        WorkerComparator workerComparator = new WorkerComparator();
        Worker maxWorker = Collections.max(workersCollection, workerComparator);
        Worker worker = new Worker(generateId(), workerDTO);
        return workerComparator.compare(worker, maxWorker) > 0;
    }

    /**
     * Обновляет объект {@link Worker} в коллекции по его идентификатору.
     */
    public void updateById(Long id, WorkerDTO workerDTO, String userLogin) throws SQLException {
        if (!userWorkerReferenceDAO.isOwner(id, userLogin)) {
            throw new NotOwnerException();
        }
        this.workerDAO.updateWorker(id, workerDTO);
        loadCollectionFromDB();
    }

    /**
     * Сортирует коллекцию работников с использованием указанного компаратора.
     *
     * @param comparator компаратор для определения порядка сортировки
     * @see Comparator
     * @see Vector
     * @see CopyOnWriteArrayList
     * @see Collections#sort(List, Comparator)
     */
    public void sort(Comparator<Worker> comparator) {
        List<Worker> list = new ArrayList<>(workersCollection);
        list.sort(comparator);
        //workersCollection = new CopyOnWriteArrayList<>();
        workersCollection = new Vector<>(list);
    }

    /**
     * Удаляет объект {@link Worker} из коллекции по его идентификатору.
     *
     * @param id Идентификатор работника.
     */
    public void removeById(Long id, String userLogin) throws SQLException, NotOwnerException {
        if (!userWorkerReferenceDAO.isOwner(id, userLogin)) {
                throw new NotOwnerException();
            }
            workerDAO.removeById(id);
            loadCollectionFromDB();
        }

    /**
     * Выводит содержимое коллекции в консоль.
     */
    public String showCollection() {
        return workersCollection.stream()
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Удаляет из коллекции все элементы, которые меньше указанного объекта {@link Worker}.
     */
    public void removeLower(WorkerDTO workerDTO) {
        Worker worker = new Worker(generateId(), workerDTO);
        WorkerComparator workerComparator = new WorkerComparator();
        workersCollection.removeIf(w -> workerComparator.compare(w, worker) < 0);
    }

    /**
     * Выводит количество элементов, значение поля {@link Person} которых больше указанного.
     *
     * @param lowPerson Объект {@link Person} для сравнения.
     */
    public String countGreaterThanPerson(Person lowPerson) {
        long result = workersCollection.stream()
                .filter(worker -> worker.getPerson() != null && worker.getPerson().compareTo(lowPerson) > 0)
                .count();
        return "Количество работников, значение поля person которых больше заданного равно " + result + ".";
    }

    /**
     * Возвращает элементы коллекции, значение поля {@code salary} которых меньше указанного.
     *
     * @param maxSalary Максимальное значение зарплаты.
     * @return строка, содержащая элементы коллекции, удовлетворяющие условию
     */
    public String filterLessThanSalary(long maxSalary) {
        if (workersCollection == null || workersCollection.isEmpty()) {
            return "Коллекция пуста.";
        }
        String result = workersCollection.stream()
                .filter(worker -> worker.getSalary() < maxSalary)
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));

        return result.isEmpty()
                ? "Нет работников с зарплатой меньше " + maxSalary
                : result;
    }

    /**
     * Выводит значения поля {@link Person} всех элементов коллекции в порядке возрастания.
     */
    public String printFieldAscendingPerson() {
        return workersCollection.stream()
                .map(Worker::getPerson)
                .sorted()
                .map(Person::toString)
                .collect(Collectors.joining("\n", "Все значения поля Person в порядке возрастания:\n", ""));
    }

    /**
     * Генерирует уникальный идентификатор для нового элемента коллекции.
     *
     * @return Уникальный идентификатор.
     */
    public Long generateId() {
        int count = 0;
        Long id = 1L;
        while (count != workersCollection.size()) {
            for (Worker worker : workersCollection) {
                count++;
                if (Objects.equals(worker.getId(), id)) {
                    id++;
                    count = 0;
                    break;
                }
            }
        }
        return id;
    }
}
