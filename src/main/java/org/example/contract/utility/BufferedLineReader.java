package org.example.contract.utility;

import java.io.BufferedInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Класс для построчного чтения данных из входного потока с буферизацией.
 *
 * <p>Обеспечивает чтение текстовых данных по строкам.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class BufferedLineReader {

    /**
     * Буферизированный входной поток для чтения данных.
     */
    private final BufferedInputStream bufferedInputStream;

    /**
     * Кэш для хранения считанного символа при проверке hasNextLine().
     */
    private Integer cachedCharCode = null;

    /**
     * Создает новый экземпляр BufferedLineReader для указанного потока.
     *
     * @param inputStream входной поток для чтения
     */
    public BufferedLineReader(InputStream inputStream) {
        bufferedInputStream = new BufferedInputStream(inputStream);
    }

    /**
     * Читает следующую строку из входного потока.
     *
     * @return прочитанная строка (без символов окончания строки)
     * @throws RuntimeException если произошла ошибка ввода-вывода
     */
    public String nextLine() {
        StringBuilder builder = new StringBuilder();
        if (cachedCharCode != null) {
            builder.append((char) cachedCharCode.intValue());
            cachedCharCode = null;
        }
        while (true) {
            int charCode = readCharCode();
            if (charCode == -1) {
                break;
            } else if (charCode == '\n') {
                break;
            } else if (charCode == '\r') {
                readCharCode();
                break;
            }
            builder.append((char) charCode);
        }
        return builder.toString();
    }

    /**
     * Проверяет наличие следующей строки в потоке.
     *
     * @return true, если в потоке есть еще данные для чтения, иначе false
     * @throws RuntimeException если произошла ошибка ввода-вывода
     */
    public boolean hasNextLine() {
        if (cachedCharCode != null) {
            return true;
        }
        int charCode = readCharCode();
        if (charCode == -1) {
            return false;
        }
        cachedCharCode = charCode;
        return true;
    }

    /**
     * Читает следующий символ из буферизированного потока.
     *
     * @return код считанного символа или -1 при достижении конца потока
     * @throws RuntimeException если произошла ошибка ввода-вывода
     */
    private int readCharCode() {
        int charCode;
        try {
            charCode = bufferedInputStream.read();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return charCode;
    }
}
