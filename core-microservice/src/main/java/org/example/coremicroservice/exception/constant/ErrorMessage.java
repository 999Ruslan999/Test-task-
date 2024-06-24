package org.example.coremicroservice.exception.constant;

public enum ErrorMessage {

    INCORRECT_DATA("Неверный логин или пароль"),

    NOT_FOUND("Страница не найдена."),

    USER_CREDENTIAL_NOT_FOUND("Пользователь с такими данными не найден"),

    KAFKA_SEND_ERROR("Ошибка отправки сообщения в брокер Kafka");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
