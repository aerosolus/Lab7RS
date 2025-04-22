package org.example.client.builders;

import lombok.NoArgsConstructor;
import org.example.client.utility.ClientApplicationContainer;
import org.example.client.utility.CommandExtractor;
import org.example.client.utility.TerminalManager;
import org.example.contract.commands.Command;
import org.example.contract.commands.ExecuteScriptCommand;
import org.example.contract.exceptions.InvalidArgumentException;
import org.example.contract.exceptions.ScriptExecutionException;
import org.example.contract.utility.BufferedLineReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация {@link CommandBuilder} для построения команды {@link ExecuteScriptCommand}.
 * <p>
 * Обрабатывает выполнение скриптов, включая:
 * <ul>
 *   <li>Проверку рекурсивного вызова скриптов</li>
 *   <li>Чтение команд из файла</li>
 *   <li>Обработку ошибок формата данных</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see ClientApplicationContainer
 * @see CommandExtractor
 */
@NoArgsConstructor
public class ExecuteScriptCommandBuilder implements CommandBuilder {

    /**
     * Создает команду выполнения скрипта, обрабатывая указанный файл.
     * <p>
     * Действия метода:
     * <ol>
     *   <li>Проверяет наличие ровно 2 аргументов (команда + путь к файлу)</li>
     *   <li>Читает файл построчно, преобразуя строки в команды</li>
     *   <li>Обрабатывает ошибки ввода и форматирования</li>
     *   <li>Формирует список команд для выполнения</li>
     * </ol>
     *
     * @param str массив аргументов, где:
     *            <ul>
     *              <li>str[0] - имя команды</li>
     *              <li>str[1] - абсолютный путь к файлу скрипта</li>
     *            </ul>
     * @return {@link ExecuteScriptCommand} с подготовленным списком команд
     * @throws InvalidArgumentException если количество аргументов некорректно
     * @throws RuntimeException при ошибках ввода-вывода
     *
     */
    @Override
    public Command build(String[] str) {
        if (str.length != 2) throw new InvalidArgumentException();
        else {
            File scriptFile = new File(str[1]);
            List<Command> scriptList = new ArrayList<>();
            if (ClientApplicationContainer.getInstance().getScriptsStack().contains(scriptFile)) {
                System.err.println("Попытка вызвать уже исполняемый скрипт.");
            } else {
                ClientApplicationContainer.getInstance().getScriptsStack().add(scriptFile);
                try (FileInputStream input = new FileInputStream(scriptFile)) {
                    BufferedLineReader bufferedLineReader = new BufferedLineReader(input);
                    ClientApplicationContainer.getInstance().setBufferedLineReader(bufferedLineReader);
                    while (bufferedLineReader.hasNextLine()) {
                        try {
                            String line = bufferedLineReader.nextLine().trim();
                            String[] strScript = line.trim().split("\\s+");
                            while (strScript.length == 0 || strScript.length > 2) {
                                System.err.println("Команда введена некорректно!");
                                System.out.println(">");
                                line = bufferedLineReader.nextLine().trim();
                                strScript = line.trim().split("\\s+");
                            }
                            try {
                                scriptList.add(CommandExtractor.getDTO(strScript));
                            } catch (NullPointerException e) {
                                System.err.println("Команды " + strScript[0] + " не существует!");
                            } catch (InvalidArgumentException e) {
                                System.err.println("Введен некорректный аргумент для команды!");
                            } catch (NumberFormatException e) {
                                System.err.println("Неверный формат аргумента!");
                            }
                        } catch (ScriptExecutionException e) {
                            System.err.println("Ошибка во время исполнения скрипта!");
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("Указанный файл не найден!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ClientApplicationContainer.getInstance().getScriptsStack().removeLast();
            }
            return new ExecuteScriptCommand(scriptList, TerminalManager.getUser());
        }
    }
}
