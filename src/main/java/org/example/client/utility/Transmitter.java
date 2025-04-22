package org.example.client.utility;

import org.example.contract.commands.Command;
import org.example.contract.commands.ExitCommand;
import org.example.contract.exceptions.FailedClosureException;
import org.example.contract.responses.ExitResponse;
import org.example.contract.responses.Response;
import org.example.contract.utility.StatusCode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

/**
 * Класс для отправки команд и получения ответов от сервера через TCP-соединение.
 * <p>
 * Обеспечивает:
 * <ul>
 *   <li>Сериализацию команд в бинарный формат</li>
 *   <li>Отправку данных через сокет</li>
 *   <li>Десериализацию ответов сервера</li>
 * </ul>
 * </p>
 *
 * @author Aerosolus
 * @version 1.0
 * @since 1.0
 * @see TCPClient
 */
public class Transmitter {

    /**
     * TCP-клиент для управления сетевым соединением
     */
    private final TCPClient client;

    /**
     * Создает трансмиттер с указанным TCP-клиентом
     *
     * @param client клиент для сетевого взаимодействия
     */
    public Transmitter(TCPClient client) {
        this.client = client;
    }

    /**
     * Отправляет команду и получает ответ от сервера
     * <p>
     * Особенности обработки:
     * <ul>
     *   <li>Для ExitCommand: закрывает соединение без ожидания ответа</li>
     *   <li>При разрыве соединения инициирует аварийное завершение клиента</li>
     *   <li>Обрабатывает ошибки сериализации/десериализации</li>
     * </ul>
     * </p>
     *
     * @param request команда для отправки
     * @throws IOException при ошибках ввода-вывода
     *
     * @param <T> тип команды, наследующий {@link Command}
     */
    public <T extends Command> Response sendRequest(T request) throws IOException {
        if(request instanceof ExitCommand){
            try{
                client.close();
                return new ExitResponse(StatusCode._200_SUCCESS_);
            } catch (FailedClosureException e) {
                return new ExitResponse(StatusCode._400_CLIENT_ERROR);
            }
        }
        sendCommand(request);
        try {
            return (Response) recieveObject();
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }
    }

    /**
     * Отправляет команду на сервер
     * <p>
     * Процесс отправки:
     * <ol>
     *   <li>Сериализует команду в ByteArrayOutputStream</li>
     *   <li>Преобразует в массив байт</li>
     *   <li>Отправляет данные через сокет</li>
     * </ol>
     * </p>
     *
     * @param request команда для отправки
     * @param <T> тип команды, наследующий {@link Command}
     */
    public <T extends Command> void sendCommand(T request)  {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            oos.close();
            sendData(baos.toByteArray());
        }
        catch(SocketException e){
            System.err.println("Сервер не запущен!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет данные через выходной поток клиента
     *
     * @param bytes данные для отправки
     * @throws IOException при ошибках записи
     */
    private void sendData(byte[] bytes) throws IOException {
        client.getOutputStream().write(bytes);
    }

    /**
     * Принимает и десериализует объект ответа
     *
     * @return десериализованный объект ответа
     * @throws IOException при ошибках чтения
     * @throws ClassNotFoundException если класс ответа неизвестен
     */
    private Object recieveObject() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(this.client.getInputStream());
        return objectInputStream.readObject();
    }
}
