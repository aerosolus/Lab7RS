package org.example.client.dataManaging;

import org.example.client.dataManaging.criteria.*;
import org.example.client.utility.ClientApplicationContainer;
import org.example.contract.data.*;
import org.example.contract.exceptions.ScriptExecutionException;
import org.example.contract.exceptions.ValidationException;
import org.example.contract.utility.BufferedLineReader;

import java.util.List;

/**
 * Утилитарный класс для интерактивного ввода данных с валидацией.
 * <p>
 * Обеспечивает:
 * <ul>
 *   <li>Чтение данных разных типов (строки, числа, enum)</li>
 *   <li>Валидацию по заданным критериям</li>
 *   <li>Поддержку интерактивного режима и режима скриптов</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Criteria
 * @see ClientApplicationContainer
 */
public class ValuesGetter {

    /**
     * Экземпляр клиентского контейнера
     */
    static ClientApplicationContainer appContainer = ClientApplicationContainer.getInstance();

    /**
     * Читает имя работника с валидацией без специальных критериев.
     *
     * @return корректное имя работника
     */
    public static String readName() {
        if (!appContainer.isInteractiveMode()) System.out.println("Введите имя работника.");
        return readValidValue();
    }

    /**
     * Читает координату X с валидацией:
     * <ul>
     *   <li>Число типа Long</li>
     *   <li>Значение > -975</li>
     * </ul>
     *
     * @return корректное значение координаты X
     */
    public static Long readCoordinateX() {
        if (!appContainer.isInteractiveMode()) System.out.println("Введите координату X работника.");
        return Long.parseLong(readValidValue(List.of(new GreaterThanCriteria(-975), new LongCriteria())));
    }

    /**
     * Читает координату Y с валидацией:
     * <ul>
     *   <li>Число типа Long</li>
     * </ul>
     *
     * @return корректное значение координаты Y
     */
    public static long readCoordinateY() {
        if (!appContainer.isInteractiveMode()) System.out.println("Введите координату Y работника.");
        return Long.parseLong(readValidValue(List.of(new LongCriteria())));
    }

    /**
     * Читает зарплату с валидацией:
     * <ul>
     *   <li>Число типа Long</li>
     *   <li>Значение > 0</li>
     * </ul>
     *
     * @return корректное значение зарплаты
     */
    public static long readSalary() {
        if (!appContainer.isInteractiveMode()) System.out.println("Введите заработную плату работника.");
        return Long.parseLong(readValidValue(List.of(new GreaterThanCriteria(0), new LongCriteria())));
    }

    /**
     * Читает позицию работника с валидацией значений перечисления.
     *
     * @return корректное значение позиции
     */
    public static Position readPosition() {
        if (!appContainer.isInteractiveMode()) System.out.print("Введите позицию работника, выбрав одно из следующих значений: ");
        if (!appContainer.isInteractiveMode()) {
            System.out.println(Position.nameToString());
        }
        return Position.valueOf(readValidValue(List.of(new EnumCriteria(Position.values())), false));
    }

    /**
     * Читает статус работника с валидацией значений перечисления.
     *
     * @return корректное значение статуса
     */
    public static Status readStatus() {
        if (!appContainer.isInteractiveMode()) System.out.print("Введите статус работника, выбрав одно из следующих значений: ");
        if (!appContainer.isInteractiveMode()) {
            System.out.println(Status.nameToString());
        }
        return Status.valueOf(readValidValue(List.of(new EnumCriteria(Status.values())), false));
    }

    /**
     * Читает цвет глаз с валидацией значений перечисления.
     *
     * @return корректное значение цвета глаз
     */
    public static EyeColor readEyeColor() {
        if (!appContainer.isInteractiveMode()) System.out.print("Введите цвет глаз, выбрав одно из следующих значений: ");
        if (!appContainer.isInteractiveMode()) {
            System.out.println(EyeColor.nameToString());
        }
        return EyeColor.valueOf(readValidValue(List.of(new EnumCriteria(EyeColor.values())), false));
    }

    /**
     * Читает цвет волос с валидацией значений перечисления.
     *
     * @return корректное значение цвета волос
     */
    public static HairColor readHairColor() {
        if (!appContainer.isInteractiveMode()) System.out.print("Введите цвет волос, выбрав одно из следующих значений: ");
        if (!appContainer.isInteractiveMode()) {
            System.out.println(HairColor.nameToString());
        }
        return HairColor.valueOf(readValidValue(List.of(new EnumCriteria(HairColor.values())), false));
    }

    /**
     * Читает национальность с валидацией значений перечисления.
     *
     * @return корректное значение национальности
     */
    public static Country readCountry() {
        if (!appContainer.isInteractiveMode()) System.out.print("Введите национальность, выбрав одно из следующих значений: ");
        if (!appContainer.isInteractiveMode()) {
            System.out.println(Country.nameToString());
        }
        return Country.valueOf(readValidValue(List.of(new EnumCriteria(Country.values())), false));
    }

    /**
     * Читает рост с валидацией:
     * <ul>
     *   <li>Число типа Float</li>
     *   <li>Значение > 0</li>
     * </ul>
     *
     * @return корректное значение роста
     */
    public static Float readHeight() {
        if (!appContainer.isInteractiveMode()) System.out.println("Введите рост работника.");
        return Float.parseFloat(readValidValue(List.of(new GreaterThanCriteria(0), new FloatCriteria())));
    }

    /**
     * Основной метод валидации ввода.
     *
     * @param criteriaList список критериев проверки
     * @param isNull разрешает пустое значение
     * @return корректное строковое значение
     * @throws ScriptExecutionException при ошибках в режиме скрипта
     */
    private static String readValidValue(List<Criteria> criteriaList, boolean isNull) {
        BufferedLineReader reader = appContainer.getBufferedLineReader();
        while (true) {
            if (!reader.hasNextLine()) {
                System.err.println("Введенное значение не может быть использовано!");
                System.exit(1);
            }
            String input = reader.nextLine().trim();
            if (isNull && input.isBlank()) {
                return null;
            }
            try {
                Validator.validate(input, criteriaList);
                return input;
            } catch (ValidationException e) {
                for (String errorMsg : e.getErrors()) {
                    System.out.println(errorMsg);
                }
                if (appContainer.isInteractiveMode()) {
                    throw new ScriptExecutionException();
                }
            }
        }
    }

    /**
     * Перегруженный метод валидации (isNull = false)
     */
    private static String readValidValue(List<Criteria> criteria) {
        return readValidValue(criteria, false);
    }

    /**
     * Валидация без списка критериев
     */
    private static String readValidValue() {
        return readValidValue(List.of(), false);
    }

    /**
     * Создает объект координат на основе ввода
     */
    public static Coordinates readCoordinates() {
        Long x = ValuesGetter.readCoordinateX();
        long y = ValuesGetter.readCoordinateY();
        return new Coordinates(x, y);
    }

    /**
     * Создает объект Person на основе личностных данных, полученных при помощи ввода
     */
    public static Person readPerson() {
        Float height = readHeight();
        EyeColor eyeColor = readEyeColor();
        HairColor hairColor = readHairColor();
        Country nationality = readCountry();
        return new Person(height, eyeColor, hairColor, nationality);
    }
}
