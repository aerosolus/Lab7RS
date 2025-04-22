package org.example.server.utility;

import org.example.contract.data.Worker;

import java.util.Comparator;

/**
 * Компаратор для сравнения объектов {@link Worker} по имени.
 * <p>Реализует интерфейс {@link Comparator} для сравнения работников в лексикографическом порядке.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Worker
 * @see Comparator
 */
public class WorkerComparator implements Comparator<Worker> {

    /**
     * Сравнивает двух работников по их именам.
     *
     * @param w1 первый работник для сравнения
     * @param w2 второй работник для сравнения
     * @return результат сравнения:
     * <ul>
     *     <li>отрицательное число, если w1 < w2</li>
     *     <li>0, если w1 == w2</li>
     *     <li>положительное число, если w1 > w2</li>
     * </ul>
     */
    @Override
    public int compare(Worker w1, Worker w2) {
        return w1.getName().compareTo(w2.getName());
    }
}
