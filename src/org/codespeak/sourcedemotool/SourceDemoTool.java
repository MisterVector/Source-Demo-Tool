package org.codespeak.sourcedemotool;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Vector
 */
public class SourceDemoTool extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/codespeak/sourcedemotool/scenes/MainWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
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
    
}
