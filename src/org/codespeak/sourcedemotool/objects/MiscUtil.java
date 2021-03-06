package org.codespeak.sourcedemotool.objects;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.codespeak.sourcedemotool.Configuration;
import org.codespeak.sourcedemotool.SourceDemoTool;
import org.codespeak.sourcedemotool.scenes.SceneTypes;

/**
 * A class with miscellaneous utility methods
 *
 * @author Vector
 */
public class MiscUtil {

    /**
     * Checks if the specified string is null or empty
     * @param str the string to check
     * @return if the specified string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.isEmpty());
    }
    
    /**
     * Creates an alert with a message
     * @param msg message of alert
     * @return an alert with a given message
     */
    public static Alert createAlert(String msg) {
        return createAlert(AlertType.WARNING, msg);
    }

    /**
     * Creates an alert with a type and a message
     * @param type type of alert
     * @param msg message of alert
     * @return an alert with a given type and message
     */
    public static Alert createAlert(AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(Configuration.PROGRAM_TITLE);
        alert.setContentText(msg);

        return alert;
    }

    /**
     * Gets a scene from a specified SceneType
     * @param sceneType the scene type of the scene to get
     * @return a stage controller object with the scene's stage and the controller
     * @throws IOException If an IO error occurs
     */
    public static StageController getScene(SceneTypes sceneType) throws IOException  {
        return getScene(sceneType, Configuration.PROGRAM_TITLE);
    }

    /**
     * Gets a scene from a specified SceneType
     * @param sceneType the scene type of the scene to get
     * @param sceneTitle The title of the scene
     * @return a stage controller object with the scene's stage and the controller
     * @throws IOException If an IO error occurs
     */
    public static StageController getScene(SceneTypes sceneType, String sceneTitle) throws IOException  {
        return getScene(new Stage(), sceneType, sceneTitle, true);
    }
    
    /**
     * Gets a scene from a specified SceneType
     * @param existingStage an existing stage representing the scene
     * being acquired
     * @param sceneType the scene type of the scene to get
     * @return a stage controller object with the scene's stage and the controller
     * @throws IOException If an IO error occurs
     */
    public static StageController getScene(Stage existingStage, SceneTypes sceneType) throws IOException {
        return getScene(existingStage, sceneType, Configuration.PROGRAM_TITLE, false);
    }
    
    /**
     * Gets a scene from a specified SceneType
     * @param stage a stage representing the scene being acquired
     * @param sceneType the scene type of the scene to get
     * @param sceneTitle The title of the scene
     * @param newStage determines if the stage is a new stage
     * @return a stage controller object with the scene's stage and the controller
     * @throws IOException If an IO error occurs
     */
    public static StageController getScene(Stage stage, SceneTypes sceneType, String sceneTitle, boolean newStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SourceDemoTool.getInstance().getClass().getResource(sceneType.getPath()));
        
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);
        
        if (newStage) {
            stage.initModality(Modality.APPLICATION_MODAL);
        }
        
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
        
        return new StageController(stage, loader.getController());
    }
    
}
