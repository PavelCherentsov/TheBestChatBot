import bot.Bot;
import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends TelegramLongPollingBot {
    private static final String BOT_NAME = "WhoPi";
    private static final String BOT_TOKEN = "745894584:AAHUqxWITerwmrexJME1_7PA3Hm1e7KQ5Fc";

    public Main() {

    }

    public static void main(String[] args) {
        if (args.length != 1)
            System.out.println("Введите `java -jar TheBestChatBot-1.0-SNAPSHOT.jar (console | telegram)`");
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
    
    private ConcurrentHashMap<Long, Bot> users = new ConcurrentHashMap<>();

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if(update.hasMessage()){
                if(update.getMessage().hasText()) {
                    Long chatId = update.getMessage().getChatId();
                    String message = update.getMessage().getText();
                    SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
                    String result = "";
                    if (!(users.containsKey(chatId))) {
                        users.put(chatId, new Bot());
                    }
                    result = users.get(chatId).getAnswer(message);
                    result = EmojiParser.parseToUnicode(result);
                    sendMessage.setText(result);
                    sendMessage.setParseMode(ParseMode.HTML);
                    execute(sendMessage);
                    /*if (update.getMessage().getText().equals("help"))
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
*/
                }
            }else if(update.hasCallbackQuery()){
                execute(new SendMessage().setText(
                        update.getCallbackQuery().getData())
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            }

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public SendMessage sendInlineKeyBoardMessage(Long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Авторы");
        inlineKeyboardButton2.setCallbackData(new Bot().getAnswer("авторы"));
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Fi4a").setCallbackData("jkl"));
        keyboardButtonsRow2.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Помощь").setReplyMarkup(inlineKeyboardMarkup);
        /*inlineKeyboardMarkup.setKeyboard(
                new ArrayList<List<InlineKeyboardButton>>() {{
                    add(new ArrayList<InlineKeyboardButton>() {{
                        add(new InlineKeyboardButton().setText("Авторы").setCallbackData(users.get(chatId).getAnswer("авторы")));
                        add(new InlineKeyboardButton().setText("Игра 'Виселица'").setCallbackData(users.get(chatId).getAnswer("игра")));
                    }});
                    add(new ArrayList<InlineKeyboardButton>() {{
                        add(new InlineKeyboardButton().setText("Учеба").setCallbackData(users.get(chatId).getAnswer("study")));
                        add(new InlineKeyboardButton().setText("Личный Time-Manager").setCallbackData(users.get(chatId).getAnswer("органайзер")));
                    }});
                }});
        return new SendMessage().setChatId(chatId).setText("").setReplyMarkup(inlineKeyboardMarkup);

         */
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
