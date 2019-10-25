package commands.organizer;

public enum Flag {
    COMPLETED(":white_check_mark:", "Выполнено"),
    DEADLINE_IS_COMING(":sos:", "Скоро дедлайн"),
    DURING(":thinking:", "В процессе"),
    FAILED(":skull_crossbones:", "Потрачено");
    private String emoji;
    private String name;

    Flag(String emoji, String name) {
        this.emoji = emoji;
        this.name = name;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getName(){
        return name;
    }
}
