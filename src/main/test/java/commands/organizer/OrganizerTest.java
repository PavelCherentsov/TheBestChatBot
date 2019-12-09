package commands.organizer;

import bot.Bot;
import bot.Status;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizerTest {
    private Bot bot = new Bot();

    @Test
    public void testStart() {
        bot.statusActive = Status.MENU;
        bot.getAnswer("organizer");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER);
        bot.getAnswer("help");
    }

    @Test
    void testAddFlags() {
        bot.statusActive = Status.ORGANIZER;
        Organizer.add(bot, "add");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER_ADD);
        bot.getAnswer("01.01.2000 Задание 1");
        Assert.assertEquals(bot.organizer.get(0).task, "Задание 1");
        Organizer.all(bot, "");
        Assert.assertEquals(bot.organizer.get(0).flag, Flag.FAILED);
        Organizer.add(bot, "add");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER_ADD);
        bot.getAnswer("01.01.3000 Задание 2");
        Assert.assertEquals(bot.organizer.get(1).task, "Задание 2");
        Organizer.all(bot, "");
        Assert.assertEquals(bot.organizer.get(1).flag, Flag.DURING);
        Organizer.completed(bot, "completed 0");
        Assert.assertEquals(bot.organizer.get(0).flag, Flag.COMPLETED);
    }

    @Test
    void testShow() {
        bot = new Bot();
        bot.statusActive = Status.ORGANIZER;
        bot.getAnswer("show");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER_SHOW);
        bot.getAnswer("Выполнено");

        Organizer.add(bot, "add");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER_ADD);
        bot.getAnswer("01.01.2000 Задание 1");
        Organizer.completed(bot, "completed 0");
        Assert.assertEquals(bot.organizer.get(0).flag, Flag.COMPLETED);

        Organizer.showByFlag(Flag.COMPLETED, bot);
    }

    @Test
    void testNoCorrectInput() {
        bot = new Bot();
        bot.statusActive = Status.ORGANIZER;
        bot.getAnswer("asddf");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER);
    }

    @Test
    void testAll() {
        bot = new Bot();
        bot.statusActive = Status.ORGANIZER;
        bot.getAnswer("all");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER);
    }

    @Test
    void testBack() {
        bot = new Bot();
        bot.statusActive = Status.ORGANIZER_ADD;
        bot.getAnswer("back");
        Assert.assertEquals(bot.statusActive, Status.ORGANIZER);
    }

    @Test
    void testQuit() {
        bot = new Bot();
        bot.statusActive = Status.ORGANIZER;
        bot.getAnswer("quit");
        Assert.assertEquals(bot.statusActive, Status.MENU);
    }


}