package org.example.server.database;

import org.example.contract.data.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Класс для работы с данными работников в базе данных.
 * Реализует паттерн DAO (Data Access Object) для обеспечения взаимодействия
 * между бизнес-логикой приложения и базой данных.
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class WorkerDAO extends DAO {

    /**
     * Создает новый экземпляр WorkerDAO с заданными параметрами подключения к базе данных.
     *
     * @param URL      адрес подключения к БД
     * @param userName имя пользователя БД
     * @param password пароль доступа к БД
     */
    public WorkerDAO(String URL, String userName, String password) {
        super(URL, userName, password);
    }

    /**
     * Добавляет нового работника в базу данных.
     * Создает записи во всех связанных таблицах (координаты, персона).
     *
     * @param workerDTO DTO объект содержащий информацию о работнике
     * @param userName  имя пользователя, который добавляет объект
     * @return идентификатор созданного работника
     * @throws SQLException если возникла ошибка при работе с БД
     */
    public long addWorker(WorkerDTO workerDTO, String userName) throws SQLException {
        Integer coordinatesId = addCoordinates(workerDTO.getCoordinates());
        int personId = addPerson(workerDTO.getPerson());

        PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO workers (" +
                "name, coordinates_id, creationdate, salary, position, status, person_id)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id");

        ps.setString(1, workerDTO.getName());
        ps.setInt(2, coordinatesId);
        ps.setDate(3, Date.valueOf(LocalDate.now()));
        ps.setLong(4, workerDTO.getSalary());
        ps.setString(5, workerDTO.getPosition().toString());
        ps.setString(6, workerDTO.getStatus().toString());
        ps.setInt(7, personId);
        long id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getLong(1);
        }
        return id;
    }

    /**
     * Обновляет информацию о работнике в базе данных.
     * Выполняет транзакционное обновление всех связанных таблиц.
     *
     * @param id        идентификатор работника для обновления
     * @param workerDTO DTO объект с новыми данными работника
     * @throws SQLException если возникла ошибка при работе с БД
     */
    public void updateWorker(Long id, WorkerDTO workerDTO) throws SQLException {
        String updateWorkerSql = "UPDATE workers SET name = ?, salary = ?, position = ?, status = ? WHERE id = ?";
        String updateCoordinatesSql = "UPDATE Coordinates SET x = ?, y = ? WHERE id = (SELECT coordinates_id FROM workers WHERE id = ?)";
        String updatePersonSql = "UPDATE persons SET height = ?, eyecolor = ?, haircolor = ?, nationality = ? WHERE id = (SELECT person_id FROM workers WHERE id = ?)";
        connection.setAutoCommit(false);
        try (PreparedStatement psWorker = this.connection.prepareStatement(updateWorkerSql);
             PreparedStatement psCoordinates = this.connection.prepareStatement(updateCoordinatesSql);
             PreparedStatement psPerson = this.connection.prepareStatement(updatePersonSql)) {

            psWorker.setString(1, workerDTO.getName());
            psWorker.setLong(2, workerDTO.getSalary());
            psWorker.setString(3, workerDTO.getPosition().toString());
            psWorker.setString(4, workerDTO.getStatus().toString());
            psWorker.setLong(5, id);
            psWorker.executeUpdate();


            psCoordinates.setLong(1, workerDTO.getCoordinates().getX());
            psCoordinates.setLong(2, workerDTO.getCoordinates().getY());
            psCoordinates.setLong(3, id);
            psCoordinates.executeUpdate();


            psPerson.setFloat(1, workerDTO.getPerson().getHeight());
            psPerson.setString(2, workerDTO.getPerson().getEyeColor().toString());
            psPerson.setString(3, workerDTO.getPerson().getHairColor().toString());
            psPerson.setString(4, workerDTO.getPerson().getNationality().toString());
            psPerson.setLong(5, id);
            psPerson.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    /**
     * Добавляет новые координаты в базу данных.
     *
     * @param coordinates объект координат для добавления
     * @return идентификатор созданной записи координат
     * @throws SQLException если возникла ошибка при работе с БД
     */
    public int addCoordinates(Coordinates coordinates) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id");
        ps.setLong(1, coordinates.getX());
        ps.setLong(2, coordinates.getY());
        int id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    /**
     * Добавляет информацию о персоне в базу данных.
     *
     * @param person объект персоны для добавления
     * @return идентификатор созданной записи персоны
     * @throws SQLException если возникла ошибка при работе с БД
     */
    public int addPerson(Person person) throws SQLException {
        PreparedStatement ps = this.connection.prepareStatement(
                "INSERT INTO persons " +
                "(height, eyecolor, haircolor, nationality)" +
                "VALUES (?, ?, ?, ?) RETURNING ID");

        ps.setFloat(1, person.getHeight());
        ps.setString(2, person.getEyeColor().toString());
        ps.setString(3, person.getHairColor().toString());
        ps.setString(4, person.getNationality().toString());

        int id = -1;
        ResultSet resultSet = ps.executeQuery();
        if (resultSet.next()) {
            id = resultSet.getInt(1);
        }
        return id;
    }

    /**
     * Удаляет работника из базы данных по его идентификатору.
     * Выполняется в рамках транзакции для обеспечения целостности данных.
     *
     * @param id идентификатор работника для удаления
     * @throws SQLException если возникла ошибка при работе с БД
     */
    public void removeById(Long id) throws SQLException {
        String deleteProductSql = "DELETE FROM workers WHERE id = ?";
        connection.setAutoCommit(false);
        try (PreparedStatement psProduct = this.connection.prepareStatement(deleteProductSql)) {
            psProduct.setLong(1, id);
            psProduct.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

//    public CopyOnWriteArrayList<Worker> getAllWorkers() throws SQLException {
//        CopyOnWriteArrayList<Worker> workerCopyOnWriteArrayList = new CopyOnWriteArrayList<>();
//        String statement = "SELECT " +
//                "w.id, w.name, w.creationdate, w.salary, w.position, w.status, w.person_id, " +
//                "c.x AS coordinate_x, c.y AS coordinate_y, " +
//                "p.id AS person_id, p.height AS person_height," +
//                " p.eyecolor AS person_eyecolor, p.haircolor AS person_haircolor, p.nationality AS person_nationality " +
//                "FROM workers w " +
//                "JOIN coordinates c ON w.coordinates_id = c.id " +
//                "JOIN persons p ON w.person_id = p.id ";
//        PreparedStatement ps = this.connection.prepareStatement(statement);
//        ResultSet resultSet = ps.executeQuery();
//        while (resultSet.next()) {
//            Worker worker  = resultSetToWorker(resultSet);
//            workerCopyOnWriteArrayList.add(worker);
//        }
//        return workerCopyOnWriteArrayList;
//    }

    /**
     * Получает список всех работников из БД.
     * Возвращает вектор содержащий все записи из таблицы работников.
     *
     * @return Vector с объектами Worker
     * @throws SQLException если возникла ошибка при работе с БД
     */
    public Vector<Worker> getAllWorkers() throws SQLException {
        Vector<Worker> workerVector = new Vector<>();
        String statement = "SELECT " +
                "w.id, w.name, w.creationdate, w.salary, w.position, w.status, w.person_id, " +
                "c.x AS coordinate_x, c.y AS coordinate_y, " +
                "p.id AS person_id, p.height AS person_height," +
                " p.eyecolor AS person_eyecolor, p.haircolor AS person_haircolor, p.nationality AS person_nationality " +
                "FROM workers w " +
                "JOIN coordinates c ON w.coordinates_id = c.id " +
                "JOIN persons p ON w.person_id = p.id ";
        PreparedStatement ps = this.connection.prepareStatement(statement);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()) {
            Worker worker  = resultSetToWorker(resultSet);
            workerVector.add(worker);
        }
        return workerVector;
    }

    /**
     * Преобразует данные из ResultSet в объект Worker.
     * Используется для конвертации данных из БД.
     *
     * @param resultSet ResultSet содержащий данные о работнике
     * @return объект Worker с заполненными данными
     * @throws SQLException если возникла ошибка при чтении данных
     */
    private Worker resultSetToWorker(ResultSet resultSet) throws SQLException {
        Long id  = resultSet.getLong("id");
        String name = resultSet.getString("name");
        Long x = resultSet.getLong("coordinate_x");
        long y = resultSet.getLong("coordinate_y");
        Coordinates coordinates = new Coordinates(x, y);
        Date creationDate = resultSet.getDate("creationDate");
        long salary = resultSet.getLong("salary");
        Position position = Position.valueOf(resultSet.getString("position"));
        Status status = Status.valueOf(resultSet.getString("status"));
        Float personHeight = resultSet.getFloat("person_height");
        EyeColor personEyeColor = EyeColor.valueOf(resultSet.getString("person_eyecolor"));
        HairColor personHairColor = HairColor.valueOf(resultSet.getString("person_haircolor"));
        Country personNationality = Country.valueOf(resultSet.getString("person_nationality"));
        Person person = new Person(personHeight, personEyeColor, personHairColor, personNationality);
        return new Worker(id,
                name,
                coordinates,
                creationDate,
                salary,
                position,
                status,
                person);
    }
}
