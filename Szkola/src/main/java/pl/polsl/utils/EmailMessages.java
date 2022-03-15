package pl.polsl.utils;

public abstract class EmailMessages {

    //Topics
    public static final String PASSWORD_RESET_CODE_TOPIC = "Resetowanie hasła.";

    public static final String ACCOUNT_CREATED_TOPIC = "Konto do aplikacji Szkoła jest już gotowe!";

    public static final String ACCOUNT_UPDATED_TOPIC = "Dane logowania do sytemu Szkoła";

    //Messages
    public static final String PASSWORD_RESET_CODE_MESSAGE = "Użyj tego kodu aby zresetować hasło: ";

    public static final String LOGIN_READY_MESSAGE = "Twój login to: ";

    public static final String PASSWORD_READY_MESSAGE = "Twoje hasło to: ";
}
