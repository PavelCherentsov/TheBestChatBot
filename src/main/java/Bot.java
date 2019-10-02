import java.util.HashMap;
import java.util.function.Function;

import Commands.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot {

    private static final String BOT_NAME = "@Povel";
    private static final String BOT_TOKEN = "986971120:AAH8ZN8nrGjjxdMIXA0hEDF6Q0HXNuXP9gI";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        Bot bot = new Bot();
        try {
            telegramBotsApi.registerBot(bot);

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    private Status statusActive;

    private HashMap<String, Function<String, String>> dictionary = new HashMap<String, Function<String, String>>();

    public Bot() {
        dictionary.put("/help", Help::help);
        dictionary.put("Авторы", Owners::owners);
        dictionary.put("echo", Echo::echo);
        dictionary.put("quit", Quit::quit);
        dictionary.put("game", Hangman::game);
        dictionary.put("study", Study::study);
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        try {
            String command;
            String result = "";
            try {
                if (update.getMessage().getText().equals("/start")) {
                    result = "Привет, я бот! Напиши '/help', и я расскажу, что умею :)";
                    statusActive = Status.MENU;
                } else if (statusActive == Status.MENU) {
                    command = update.getMessage().getText().split(" ")[0];
                    result = dictionary.get(command).apply(update.getMessage().getText());
                    if (command.equals("game")) {
                        statusActive = Status.GAME;
                    }
                    if (command.equals("quit")) {
                        result = "Пока";
                        statusActive = null;
                    }
                    if (command.equals("study")){
                        statusActive = Status.STUDY;
                    }
                } else if (statusActive == Status.GAME) {
                    result = Hangman.game(update.getMessage().getText());
                    if (update.getMessage().getText().equals("quit") || result.equals("Поздравляю! Ты выиграл! :)")
                            || result.equals("Ты проиграл(")) {
                        statusActive = Status.MENU;
                    }
                }
                else if (statusActive == Status.STUDY){
                    result = Study.study(update.getMessage().getText());
                    if (update.getMessage().getText().equals("quit")){
                        statusActive = Status.MENU;
                    }
                } else {
                    result = "Для запуска напишите '/start'";
                }
            } catch (NullPointerException e) {
                result = dictionary.get("/help").apply(update.getMessage().getText());
            }
            sendMessage.setText(result);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
