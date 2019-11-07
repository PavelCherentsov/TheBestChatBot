import bot.Bot;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends TelegramLongPollingBot {
    private static final String BOT_NAME = "WhoPi";
    private static final String BOT_TOKEN = "745894584:AAHUqxWITerwmrexJME1_7PA3Hm1e7KQ5Fc";

    private static ConcurrentHashMap<Long, Bot> users = new ConcurrentHashMap<>();

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args[0].equals("console")) {
            Bot bot = new Bot();
            restore();
            System.out.println(users.isEmpty());
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
        } else {
            System.out.println("Введите java -jar TheBestChatBot-1.0-SNAPSHOT.jar (console | telegram)");
        }
    }

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
            result = EmojiParser.parseToUnicode(result);
            sendMessage.setText(result);
            sendMessage.setParseMode(ParseMode.HTML);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void save() throws IOException {
        users.put(23L, new Bot());
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.substring(0, filePath.length() - 6);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream(filePath+"src/main/resources/users.out"));
        objectOutputStream.writeObject(users);
        objectOutputStream.close();
    }

    public static void restore() throws IOException, ClassNotFoundException {
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.substring(0, filePath.length() - 6);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream(filePath+"src/main/resources/users.out"));
        ConcurrentHashMap users = (ConcurrentHashMap) objectInputStream.readObject();
        objectInputStream.close();
    }
}
