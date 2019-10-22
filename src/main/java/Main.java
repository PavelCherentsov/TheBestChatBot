import bot.Bot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.HashMap;

public class Main extends TelegramLongPollingBot {
    private static final String BOT_NAME = "WhoPi";
    private static final String BOT_TOKEN = "745894584:AAHUqxWITerwmrexJME1_7PA3Hm1e7KQ5Fc";

    public Main() {

    }

    public static void main(String[] args) {
        if (args[0].equals("console")) {
            Bot bot = new Bot();
            bot.run();
        }
        if (args[0].equals("telegram")) {
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            Main bot = new Main();
            try {
                telegramBotsApi.registerBot(bot);

            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        }
    }

    private HashMap<Long, Bot> users = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        String result = "";
        try {
            if (!(users.containsKey(chatId))) {
                users.put(chatId, new Bot());
            }
            result = users.get(chatId).getAnswer(message);
            sendMessage.setText(result);
            sendMessage.setParseMode(ParseMode.HTML);
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
