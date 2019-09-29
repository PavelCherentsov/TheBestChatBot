import java.util.ArrayList;
import java.util.List;

public enum  Status {
    MENU,
    GAME;

    private static Status active = Status.MENU;

    public static Status getStatus(){
        return active;
    }

    public static void setStatus(Status status){
        active = status;
    }

}
