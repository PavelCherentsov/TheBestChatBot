package bot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BotTestHangman {
    private String chars = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private Bot bot = new Bot();

    @Test
    void testStartGame() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("game");
        Assert.assertEquals(bot.life, 6);
    }

    @Test
    void testTrueChar() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("game");
        String word = bot.word;
        bot.getAnswer(String.valueOf(word.charAt(0)));
        Assert.assertEquals(bot.life, 6);
        Assert.assertTrue(bot.wordEncrypted.toLowerCase().contains(String.valueOf(word.charAt(0))));
    }

    @Test
    void testWrongChar() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("game");
        String word = bot.word;
        for (int i = 0; i < chars.length(); i++) {
            if (!word.contains(String.valueOf(chars.charAt(i)))) {
                bot.getAnswer(String.valueOf(chars.charAt(i)));
                break;
            }
        }
        Assert.assertEquals(bot.life, 5);
    }

    @Test
    void testWinGame() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("game");
        String word = bot.word;
        for (int i = 0; i < word.length(); i++) {
            bot.getAnswer(String.valueOf(word.charAt(i)));
        }
        Assert.assertEquals(bot.life, 6);
        Assert.assertEquals(bot.statusActive, Status.MENU);
    }

    @Test
    void testLoseGame() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("game");
        String word = bot.word;
        for (int i = 0; i < chars.length(); i++) {
            if (!word.contains(String.valueOf(chars.charAt(i)))) {
                bot.getAnswer(String.valueOf(chars.charAt(i)));
            }
        }
        Assert.assertEquals(bot.life, 0);
        Assert.assertEquals(bot.statusActive, Status.MENU);
    }
}
