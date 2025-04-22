package org.example.server.database;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Менеджер для хеширования паролей с использованием алгоритма SHA-512.
 * <p>
 * Обеспечивает одностороннее преобразование паролей в хеш для безопасного хранения.
 * </p>
 *
 * <p>Особенности реализации:</p>
 * <ul>
 *   <li>Использует алгоритм SHA-512 с преобразованием результата в HEX-строку</li>
 *   <li>Автоматическое завершение программы при ошибках инициализации алгоритма</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class PasswordManager {

    /**
     * Вычисляет SHA-512 хеш для указанного пароля.
     * <p>
     * Алгоритм работы:
     * <ol>
     *   <li>Преобразует строку в байты с кодировкой UTF-8</li>
     *   <li>Вычисляет хеш с помощью MessageDigest</li>
     *   <li>Конвертирует результат в HEX-строку</li>
     * </ol>
     *
     * @param password исходный пароль
     * @return HEX-строка длиной 128 символов (512 бит)
     */
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return convertToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Использован некорректный алгоритм хеширования.");
            System.out.println("Работа программы завершена.");
            System.exit(1);
        }
        return null;
    }

    /**
     * Конвертирует байтовый массив в HEX-строку.
     * <p>
     * Каждый байт представляется двумя HEX-символами. Если значение байта
     * занимает менее 2 символов, добавляет ведущий ноль.
     * </p>
     *
     * @param hash байтовый массив хеша
     * @return HEX-строка длиной 2 * hash.length
     */
    private String convertToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
