import bot.Bot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vdurmont.emoji.EmojiParser;
import commands.organizer.Flag;
import commands.organizer.Organizer;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends TelegramLongPollingBot {
    private static String BOT_NAME = "WhoPi";
    private static String BOT_TOKEN = "";
    private static String time;

    private static ConcurrentHashMap<Long, Bot> users = new ConcurrentHashMap<>();

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static Main bot;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args[0].equals("console")) {
            restore();
            Scanner in = new Scanner(System.in, "Cp866");
            while (true) {
                save();
                String line = in.nextLine();
                String result = users.get(0L).getAnswer(line);
                result = processing(result);
                System.out.println(result);
            }
        }
        if (args[0].equals("telegram")) {

            BOT_TOKEN = Key.get_token();
            ApiContextInitializer.init();
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            bot = new Main();
            restore();
            if (args[1].equals("test")){
                time = args[2];
            }
            try {
                telegramBotsApi.registerBot(bot);
                Thread t = new chreak();
                t.start();
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Введите java -jar TheBestChatBot-1.0-SNAPSHOT.jar (console | telegram)");
        }
    }

    public static class chreak extends Thread {
        @Override
        public void run() {
            while (true) {
                for (Long a : users.keySet()) {
                    if (!time.equals("")){
                        users.get(a).test = true;
                        users.get(a).time = time;
                    }
                    else {
                        users.get(a).test = false;
                    }
                    String res = Organizer.checkDeadlines(users.get(a), "");
                    if (!res.equals("")) {
                        SendMessage sendMessage = new SendMessage().setChatId(a);
                        sendMessage.setText(res);
                        try {
                            bot.execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
            //users.computeIfAbsent()
            result = EmojiParser.parseToUnicode(result);
            sendMessage.setText(result);
            sendMessage.setParseMode(ParseMode.HTML);
            bot.execute(sendMessage);
            try {
                save();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static String processing(String line) {
        line = line.replaceAll("<pre>", "");
        line = line.replaceAll("</pre>", "");
        for (Flag e : Flag.values()) {
            line = line.replaceAll(e.getEmoji(), e.getName());
        }
        return line;
    }

    public static void save() throws IOException {
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.substring(0, filePath.length() - 6);
        FileWriter writer = new FileWriter(filePath + "src/main/resources/users.out");
        writer.write(gson.toJson(users));
        writer.close();
    }

    public static void restore() throws IOException, ClassNotFoundException {
        Gson gson = new Gson();
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.substring(0, filePath.length() - 6);
        FileReader reader = new FileReader(filePath + "src/main/resources/users.out");
        String json = "";
        int c;
        while ((c = reader.read()) != -1) {
            json += (char) c;
        }
        Type collectionType = new TypeToken<ConcurrentHashMap<Long, Bot>>() {
        }.getType();
        users = gson.fromJson(json, collectionType);
        reader.close();
    }
}
