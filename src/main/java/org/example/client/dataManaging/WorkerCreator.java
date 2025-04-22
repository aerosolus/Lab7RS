package org.example.client.dataManaging;

import org.example.contract.data.*;

/**
 * Класс для интерактивного создания объектов {@link WorkerDTO} через пользовательский ввод.
 * <p>
 * Последовательно запрашивает данные для всех полей WorkerDTO, используя методы класса {@link ValuesGetter}.
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see WorkerDTO
 * @see ValuesGetter
 */
public class WorkerCreator {

    /**
     * Создает новый объект WorkerDTO, интерактивно запрашивая все необходимые данные.
     * <p>
     * Процесс создания:
     * <ol>
     *   <li>Запрос имени работника</li>
     *   <li>Запрос координат</li>
     *   <li>Запрос зарплаты</li>
     *   <li>Запрос должности</li>
     *   <li>Запрос статуса</li>
     *   <li>Запрос личтностных параметров</li>
     * </ol>
     * </p>
     *
     * @return полностью заполненный объект {@link WorkerDTO}
     */
    public WorkerDTO createWorker() {
        WorkerDTO workerDTO = new WorkerDTO();
        InsertName(workerDTO);
        InsertCoordinates(workerDTO);
        InsertSalary(workerDTO);
        InsertPosition(workerDTO);
        InsertStatus(workerDTO);
        InsertPerson(workerDTO);
        return workerDTO;
    }

    /**
     * Устанавливает имя работника через интерактивный ввод.
     *
     * @param workerDTO целевой объект для установки значения
     * @see ValuesGetter#readName()
     */
    private void InsertName(WorkerDTO workerDTO) {
        String name = ValuesGetter.readName();
        workerDTO.setName(name);
    }

    /**
     * Устанавливает координаты через интерактивный ввод.
     *
     * @param workerDTO целевой объект для установки значения
     * @see ValuesGetter#readCoordinates()
     */
    private void InsertCoordinates(WorkerDTO workerDTO) {
        Coordinates coordinates = ValuesGetter.readCoordinates();
        workerDTO.setCoordinates(coordinates);
    }

    /**
     * Устанавливает зарплату через интерактивный ввод.
     *
     * @param workerDTO целевой объект для установки значения
     * @see ValuesGetter#readSalary()
     */
    private void InsertSalary(WorkerDTO workerDTO) {
        long salary = ValuesGetter.readSalary();
        workerDTO.setSalary(salary);
    }

    /**
     * Устанавливает должность через интерактивный ввод.
     *
     * @param workerDTO целевой объект для установки значения
     * @see ValuesGetter#readPosition()
     */
    private void InsertPosition(WorkerDTO workerDTO) {
        Position position = ValuesGetter.readPosition();
        workerDTO.setPosition(position);
    }

    /**
     * Устанавливает статус через интерактивный ввод.
     *
     * @param workerDTO целевой объект для установки значения
     * @see ValuesGetter#readStatus()
     */
    private void InsertStatus(WorkerDTO workerDTO) {
        Status status = ValuesGetter.readStatus();
        workerDTO.setStatus(status);
    }

    /**
     * Устанавливает персональные данные через интерактивный ввод.
     *
     * @param workerDTO целевой объект для установки значения
     * @see ValuesGetter#readPerson()
     */
    private void InsertPerson(WorkerDTO workerDTO) {
        Person person = ValuesGetter.readPerson();
        workerDTO.setPerson(person);
    }
}
