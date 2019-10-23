package commands.organizer;

public enum Flag {
    COMPLETED(":white_check_mark:", "Выполненно"),
    DEADLINE_IS_COMING(":fire:", "Скоро дедлайн"),
    DURING(":arrows_counterclockwise:", "В процессе"),
    FAILED(":black_circle:", "Потрачено");
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
