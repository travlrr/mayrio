import javafx.stage.Stage;

import java.util.HashMap;

public class StageManager {
    private static HashMap<String, Stage> stages;

    public StageManager() {

    }

    public static void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }
}