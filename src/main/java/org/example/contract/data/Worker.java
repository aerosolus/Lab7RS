package org.example.contract.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

/**
 * Класс, представляющий сущность работника с различными характеристиками.
 * <p>Содержит информацию о работнике и реализует базовые методы работы с объектом.</p>
 *
 * <p>Использует Lombok аннотации для автоматической генерации:
 * <ul>
 *   <li>{@code @Getter} - геттеры для всех полей</li>
 *   <li>{@code @Setter} - сеттеры для всех полей</li>
 *   <li>{@code @AllArgsConstructor} - конструктор со всеми полями</li>
 *   <li>{@code @NoArgsConstructor} - пустой конструктор</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Worker {

    /**
     * Идентификатор работника. Поле не может быть null, значение должно быть больше 0,
     * должно быть уникальным и генерироваться автоматически.
     */
    private Long id;

    /**
     * Имя работника. Поле не может быть null, строка не может быть пустой.
     */
    private String name;

    /**
     * Координаты работника. Поле не может быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата создания записи о работнике. Поле не может быть null,
     * значение должно генерироваться автоматически.
     */
    private java.util.Date creationDate;

    /**
     * Зарплата работника. Значение поля должно быть больше 0.
     */
    private long salary;

    /**
     * Должность работника. Поле не может быть null.
     */
    private Position position;

    /**
     * Статус работника. Поле может быть null.
     */
    private Status status;

    /**
     * Личные данные работника. Поле не может быть null.
     */
    private Person person;

    /**
     * Конструктор для создания объекта на основе DTO.
     * <p>Автоматически инициализирует поле creationDate текущей датой.</p>
     *
     * @param id        уникальный идентификатор работника
     * @param workerDTO объект передачи данных ({@link WorkerDTO})
     */
    public Worker(Long id, WorkerDTO workerDTO) {
        this.id = id;
        this.name = workerDTO.getName();
        this.coordinates = workerDTO.getCoordinates();
        this.creationDate = Date.from(Instant.now());
        this.salary = workerDTO.getSalary();
        this.position = workerDTO.getPosition();
        this.status = workerDTO.getStatus();
        this.person = workerDTO.getPerson();
    }

    /**
     * Возвращает строковое представление объекта Worker.
     * Этот метод предоставляет подробное описание объекта,
     * включая все поля и их значения.
     *
     * @return Строковое представление объекта Worker.
     *
     * @see Worker
     */
    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", position=" + position +
                ", status=" + status +
                ", person=" + person +
                '}';
    }

    /**
     * Проверяет текущий объект Worker с другим объектом на равенство.
     *
     * @param object Объект для сравнения.
     * @return true, если объекты равны по всем значениям полей, иначе false.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Worker worker = (Worker) object;
        return salary == worker.salary &&
                Objects.equals(id, worker.id) &&
                Objects.equals(name, worker.name) &&
                Objects.equals(coordinates, worker.coordinates) &&
                Objects.equals(creationDate, worker.creationDate) &&
                position == worker.position &&
                status == worker.status &&
                Objects.equals(person, worker.person);
    }

    /**
     * Определяет хэш-код объекта Person.
     *
     * @return Хэш-код, вычисленный на основе значений всех полей.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary, position, status, person);
    }
}
