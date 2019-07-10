package org.codespeak.sourcedemotool;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.codespeak.sourcedemotool.objects.MiscUtil;
import org.codespeak.sourcedemotool.objects.StageController;
import org.codespeak.sourcedemotool.scenes.SceneTypes;

/**
 *
 * @author Vector
 */
public class SourceDemoTool extends Application {

    private static SourceDemoTool instance;
    
    public SourceDemoTool() {
        instance = this;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage = MiscUtil.getScene(stage, SceneTypes.MAIN_WINDOW).getStage();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        File demoFolder = new File(Configuration.DEMOS_FOLDER);
        
        if (!demoFolder.exists()) {
            demoFolder.mkdirs();
        }
        
        File outputFolder = new File(Configuration.OUTPUT_FOLDER);
        
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        
        launch(args);
    }

    /**
     * Gets the instance of this class
     * @return instance of this class
     */
    public static SourceDemoTool getInstance() {
        return instance;
    }
    
}
