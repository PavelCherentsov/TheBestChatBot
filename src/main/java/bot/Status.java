package bot;

import java.io.Serializable;

public enum Status implements Serializable {
    START,
    MENU,
    GAME,
    STUDY,
    CLASSES,
    ORGANIZER,
    ORGANIZER_ADD,
    ORGANIZER_EDIT,
    ORGANIZER_SHOW;
}
