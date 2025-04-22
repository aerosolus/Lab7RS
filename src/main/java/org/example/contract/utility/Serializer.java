package org.example.contract.utility;

import org.example.contract.commands.Command;
import org.example.contract.responses.Response;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Утилитарный класс для сериализации и десериализации объектов команд и ответов.
 *
 * <p>Обеспечивает преобразование объектов между Java-объектами и ByteBuffer для передачи по сети.</p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 */
public class Serializer {

    /**
     * Сериализует объект ответа в ByteBuffer.
     *
     * @param response объект ответа для сериализации (не null)
     * @param <T> тип ответа, наследующий от {@link Response}
     * @return ByteBuffer с сериализованными данными
     * @throws IOException если произошла ошибка ввода-вывода при сериализации
     * @throws IllegalArgumentException если response равен null
     */
    public static <T extends Response> ByteBuffer serializeObject(T response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(response);
        oos.close();
        ByteBuffer buffer = ByteBuffer.allocate(baos.size());
        buffer.put(baos.toByteArray());
        return buffer;
    }

    /**
     * Десериализует объект команды из ByteBuffer.
     *
     * @param buffer буфер, содержащий сериализованные данные (не null)
     * @param <T> тип команды, наследующий от {@link Command}
     * @return десериализованный объект команды
     * @throws RuntimeException если произошла ошибка десериализации
     * @throws IllegalArgumentException если buffer равен null
     */
    public static <T extends Command> T deserializeObject(ByteBuffer buffer) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}