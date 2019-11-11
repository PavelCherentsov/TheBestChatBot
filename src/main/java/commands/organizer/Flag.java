package commands.organizer;

import java.io.Serializable;

public enum Flag implements Serializable {
    COMPLETED(":white_check_mark:", "Выполнено", "вып.*"),
    DEADLINE_IS_COMING(":sos:", "Скоро дедлайн", "дед.*"),
    DURING(":thinking:", "В процессе", ".*про.*"),
    FAILED(":skull_crossbones:", "Потрачено", "потр.*");
    private String emoji;
    private String name;
    private String pattern;

    Flag(String emoji, String name, String pattern) {
        this.emoji = emoji;
        this.name = name;
        this.pattern = pattern;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }
}
