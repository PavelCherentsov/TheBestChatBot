package bot;

import commands.organizer.Flag;
import commands.organizer.Organizer;
import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    @Test
    void testFirst() {
        Bot bot = new Bot();
        Assert.assertEquals(bot.statusActive, Status.START);
    }
}

class BotTestMenuCommands {

    private Bot bot = new Bot();
    @Test
    void testStartMenu() {
        Assert.assertEquals(bot.statusActive, Status.START);
        bot.getAnswer("help");
        Assert.assertEquals(bot.statusActive, Status.MENU);
    }

    @Test
    void testEcho() {
        bot.statusActive = Status.MENU;
        echoAnswerInputIsNotEmpty("Hello");
        echoAnswerInputIsNotEmpty("Привет");
        echoAnswerInputIsNotEmpty("echo");
    }
    private void echoAnswerInputIsNotEmpty(String actual) {
        Assert.assertEquals(bot.getAnswer("echo "+actual), "Все говорят: \""+actual+"\", а ты купи слона.");
    }

    @Test
    void testEchoInputIsEmpty() {
        bot.statusActive = Status.MENU;
        Assert.assertEquals(bot.getAnswer("echo"), "Все молчат, а ты купи слона.");
        Assert.assertEquals(bot.getAnswer("echo     "), "Все молчат, а ты купи слона.");
    }

    @Test
    void testGame() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("game");
        Assert.assertEquals(bot.statusActive, Status.GAME);
        bot.getAnswer("quit");
        Assert.assertEquals(bot.statusActive, Status.MENU);
    }

    @Test
    void testStudy() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("study");
        Assert.assertEquals(bot.statusActive, Status.STUDY);
        bot.getAnswer("quit");
        Assert.assertEquals(bot.statusActive, Status.MENU);
    }

    @Test
    void testOrganizer() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("organizer");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER);
        bot.getAnswer("quit");
        Assert.assertEquals(bot.statusActive, Status.MENU);
    }

    @Test
    void testQuit() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("quit");
        Assert.assertEquals(bot.statusActive, Status.START);
    }
}

class BotTestHangman {
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