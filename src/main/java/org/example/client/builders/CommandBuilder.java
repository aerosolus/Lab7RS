package org.example.client.builders;

import org.example.contract.commands.Command;

/**
 * Общий интерфейс для построителей команд клиентской части приложения.
 * <p>
 * Реализации этого интерфейса отвечают за:
 * <ul>
 *   <li>Парсинг входных аргументов</li>
 *   <li>Валидацию формата команды</li>
 *   <li>Создание соответствующих объектов команд</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see Command
 */
public interface CommandBuilder {

    /**
     * Создает объект команды на основе переданных аргументов.
     * <p>
     * Реализации должны:
     * <ul>
     *   <li>Проверять количество и формат аргументов</li>
     *   <li>При необходимости инициировать интерактивный ввод данных</li>
     *   <li>Генерировать соответствующий тип команды</li>
     * </ul>
     * </p>
     *
     * @param str массив строковых аргументов команды
     * @return объект команды, готовый к отправке на сервер
     * @throws org.example.contract.exceptions.InvalidArgumentException если аргументы не соответствуют формату команды
     */
    Command build(String[] str);
}
