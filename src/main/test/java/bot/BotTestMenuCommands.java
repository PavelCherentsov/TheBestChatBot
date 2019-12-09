package bot;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BotTestMenuCommands {
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
