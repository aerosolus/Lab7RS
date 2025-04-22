package org.example.client.dataManaging;

import org.example.client.dataManaging.criteria.Criteria;
import org.example.contract.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Утилита для валидации строковых значений по заданным критериям.
 * <p>
 * Обеспечивает:
 * <ul>
 *   <li>Базовую проверку на null и пустую строку</li>
 *   <li>Последовательную проверку по списку критериев</li>
 *   <li>Сбор всех нарушений в исключении {@link ValidationException}</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Criteria
 */
public class Validator {

    /**
     * Выполняет проверку корректности значения по заданным критериям.
     * <p>
     * Алгоритм работы:
     * <ol>
     *   <li>Проверяет value на null или пустую строку</li>
     *   <li>Если проверка провалена - добавляет базовую ошибку</li>
     *   <li>Иначе последовательно применяет все критерии из списка</li>
     *   <li>При наличии ошибок бросает {@link ValidationException}</li>
     * </ol>
     * </p>
     *
     * @param value         проверяемое значение
     * @param criteriaList  список критериев для проверки
     * @throws ValidationException если найдены нарушения:
     *                             <ul>
     *                               <li>Базовые (null/пустая строка)</li>
     *                               <li>Нарушения критериев из списка</li>
     *                             </ul>
     */
    public static void validate(String value, List<Criteria> criteriaList) {
        List<String> errors = new ArrayList<>();
        if (value == null || value.isBlank()) {
            errors.add("Поле не может быть null или пустой строкой!");
        } else {
            for (Criteria criteria : criteriaList) {
                if (!criteria.check(value)) {
                    errors.add(criteria.errorMessage());
                }
            }
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
