package org.codespeak.sourcedemotool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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

    private static Logger logger = Logger.getLogger("SourceDemoTool");
    private static Settings settings = null;
    private static SourceDemoTool instance;
    
    private static void setupLogger() throws Exception {
        logger.setLevel(Level.WARNING);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String outputFormat = sdf.format(date);
        
        FileHandler fh = new FileHandler("logs" + File.separator + "log-" + outputFormat + ".txt", true);
        fh.setFormatter(new SimpleFormatter());
        fh.setLevel(Level.WARNING);
        
        logger.addHandler(fh);
    }
    
    public SourceDemoTool() {
        instance = this;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage = MiscUtil.getScene(stage, SceneTypes.MAIN_SCENE).getStage();
        stage.show();
    }

    @Override
    public void stop() {
        Configuration.writeSettingsToFile();
    }
    
    public static void main(String[] args) throws Exception {
        File backupsFolder = new File(Configuration.BACKUPS_FOLDER);
        
        if (!backupsFolder.exists()) {
            backupsFolder.mkdir();
        }
        
        File logsFolder = new File(Configuration.LOGS_FOLDER);
        
        if (!logsFolder.exists()) {
            logsFolder.mkdirs();
        }
        
        File outputFolder = new File(Configuration.OUTPUT_FOLDER);
        
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        
        settings = Configuration.getSettings();
        
        setupLogger();
        launch(args);
    }

    /**
     * Gets the instance of this class
     * @return instance of this class
     */
    public static SourceDemoTool getInstance() {
        return instance;
    }

    /**
     * Gets the settings object
     * @return settings object
     */
    public static Settings getSettings() {
        return settings;
    }
    
    /**
     * Gets the logger to the program
     * @return logger to the program
     */
    public static Logger getLogger() {
        return logger;
    }
    
}
