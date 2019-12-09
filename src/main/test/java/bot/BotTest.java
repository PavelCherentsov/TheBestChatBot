package bot;

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
