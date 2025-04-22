package org.example.client.utility;

import org.example.contract.responses.*;
import org.example.contract.utility.StatusCode;

import static org.example.contract.utility.StatusCode.*;

/**
 * Обработчик ответов сервера, преобразующий объекты ответов в строковый формат.
 * <p>
 * Поддерживает обработку следующих типов ответов:
 * <ul>
 *   <li>{@link ResponseWithMessage} - текстовые сообщения со статусным кодом</li>
 *   <li>{@link ExecuteScriptResponse} - ответы содержащие вложенные ответы скрипта</li>
 *   <li>{@link ExitResponse} - команда завершения работы клиента</li>
 *   <li>{@link AuthorizationResponse} - команда авторизации или верификации</li>
 * </ul>
 *
 * @author Aerosolus
 * @version 1.1
 * @since 1.0
 */
public class ResponseHandler {

    /**
     * Преобразует объект ответа в строку для отображения пользователю.
     * <p>
     * Логика обработки:
     * <ol>
     *   <li>Для {@link ResponseWithMessage}:
     *     <ul>
     *       <li>_200_SUCCESS_ - добавляет префикс "Успешно"</li>
     *       <li>_400_CLIENT_ERROR/_500_SERVER_ERROR - добавляет префикс "Ошибка"</li>
     *     </ul>
     *   </li>
     *   <li>Для {@link ExecuteScriptResponse} - рекурсивно обрабатывает все вложенные ответы</li>
     *   <li>Для {@link ExitResponse} - инициирует завершение работы клиента</li>
     *   <li>Для {@link AuthorizationResponse} - сообщает результаты аутентификации или верификации</li>
     * </ol>
     *
     * @param response объект ответа от сервера
     * @return форматированная строка с результатом выполнения команды
     */
    public String handleResponse(Response response){
        String output = "";
        if(response instanceof ResponseWithMessage responseWithMessage){
            output = switch (responseWithMessage.getStatusCode()){
                case _200_SUCCESS_ -> "Успешно.\n" + responseWithMessage.getMessage();
                case _400_CLIENT_ERROR -> "Ошибка.\n" + responseWithMessage.getMessage();
                case _500_SERVER_ERROR -> "Ошибка сервера.\n" + responseWithMessage.getMessage();
            };
            return output.trim();
        } else if (response instanceof ExecuteScriptResponse executeScriptResponse) {
            for(Response r: executeScriptResponse.getResponseList()){
                output = output.concat(this.handleResponse(r)).concat("\n");
            }
        } else if (response instanceof ExitResponse) {
            if(response.getStatusCode() == StatusCode._200_SUCCESS_){
                System.out.println("Завершение работы клиента...");
                System.exit(0);
            }else{
                output = "Ошибка: не удалось завершить работу клиента";
            }

        } else if (response instanceof AuthorizationResponse) {
            if (response.getStatusCode() == StatusCode._200_SUCCESS_) {
                AuthorizationResponse authorizationResponse = (AuthorizationResponse) response;
                output += "Авторизация выполнена успешно.";
                TerminalManager.setAuthorized(true);
                TerminalManager.setUser(authorizationResponse.getUser());
            } else {
                output += "Ошибка: " + ((AuthorizationResponse) response).getMessage();
            }
        }
        return output.trim();
    }
}
