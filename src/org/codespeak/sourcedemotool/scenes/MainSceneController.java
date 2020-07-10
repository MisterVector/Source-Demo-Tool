package org.codespeak.sourcedemotool.scenes;

import java.awt.Desktop;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.codespeak.sourcedemotool.Configuration;
import org.codespeak.sourcedemotool.Settings;
import org.codespeak.sourcedemotool.Settings.SettingFields;
import org.codespeak.sourcedemotool.demo.CommandMessage;
import org.codespeak.sourcedemotool.demo.DemoFile;
import org.codespeak.sourcedemotool.demo.DemoHeader;
import org.codespeak.sourcedemotool.objects.MiscUtil;
import org.codespeak.sourcedemotool.objects.StageController;

/**
 * Controller for the main scene
 *
 * @author Vector
 */
public class MainSceneController implements Initializable {
  
    private File chosenFile = null;
    private DemoFile loadedDemoFile = null;
    private String demoFileName = null;
    private int maxTicks = 0;
    
    @FXML private Label demoFileNameLabel;
    @FXML private TextField skipTickInput;
    @FXML private TextField outputFileNameInput;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        skipTickInput.setText("1");
    }    
    
    @FXML
    public void onMenuSettingsItemClick(ActionEvent event) throws IOException {
        Stage stage = MiscUtil.getScene(SceneTypes.SETTINGS_SCENE, "Settings for " + Configuration.PROGRAM_NAME).getStage();
        stage.show();
    }
    
    @FXML
    public void onMenuQuitItemClick(ActionEvent event) {
        Platform.exit();
    }
    
    @FXML
    public void onMenuAboutItemClick(ActionEvent event) throws IOException {
        Stage stage = MiscUtil.getScene(SceneTypes.ABOUT_SCENE, "About " + Configuration.PROGRAM_NAME).getStage();
        stage.show();
    }
    
    @FXML
    public void onSelectDemoFileButtonClick(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        Settings settings = Configuration.getSettings();
        String demosFolder = settings.getValue(SettingFields.DEMOS_FOLDER);
        File fileDemosFolder = new File(demosFolder);

        if (fileDemosFolder.exists()) {
            chooser.setInitialDirectory(fileDemosFolder);
        }

        chosenFile = chooser.showOpenDialog(null);
        
        if (chosenFile != null) {
            demoFileName = chosenFile.getName();
            String demoFileNameLower = demoFileName.toLowerCase();
            
            if (!demoFileNameLower.endsWith(".dem")) {
                Alert alert = MiscUtil.createAlert("This program can only open .dem files.");
                alert.show();
                
                return;
            }
            
            String demoFileNameWithoutExtension = demoFileName.substring(0, demoFileNameLower.indexOf(".dem"));
            outputFileNameInput.setText(demoFileNameWithoutExtension);
            
            demoFileNameLabel.setText(demoFileName);
            loadedDemoFile = DemoFile.getDemoFile(chosenFile);
            DemoHeader header = loadedDemoFile.getHeader();
            maxTicks = header.getTicks();
        }
    }

    @FXML
    public void onViewDemoFileButtonClick(ActionEvent event) throws IOException {
        if (loadedDemoFile == null) {
            Alert alert = MiscUtil.createAlert("Demo file has not been loaded. Load a demo before viewieng demo details.");
            alert.show();
            
            return;
        }
        
        StageController<DemoFileSceneController> stageController = MiscUtil.getScene(SceneTypes.DEMO_FILE_SCENE, "Details for " + demoFileName);
        DemoFileSceneController controller = stageController.getController();
        Stage stage = stageController.getStage();
        
        stage.show();
        controller.showDemoFile(loadedDemoFile);
    }
    
    @FXML
    public void writeOutputFileButtonClick(ActionEvent event) throws IOException {
        if (loadedDemoFile == null) {
            Alert alert = MiscUtil.createAlert("No demo file has been loaded. Cannot write output file!");
            alert.show();
            
            return;
        }
        
        if (chosenFile == null) {
            Alert alert = MiscUtil.createAlert(AlertType.INFORMATION, "Please select demo file again before overwriting.");
            alert.show();

            return;
        }
        
        int skippedGameTick = 0;
        
        try {
            String value = skipTickInput.getText();
            skippedGameTick = Integer.parseInt(value);
            
            if (skippedGameTick < 1 || skippedGameTick > maxTicks) {
                Alert alert = MiscUtil.createAlert("Invalid tick value. Must be in the range 1 to " + maxTicks + ".");
                alert.show();
                
                return;
            }
        } catch (NumberFormatException ex) {
            Alert alert = MiscUtil.createAlert("The tick value specified is not a number.");
            alert.show();
            
            return;
        }
        
        boolean backupBeforeOverwritingDemo = Configuration.getSettings().getValue(SettingFields.BACKUP_BEFORE_OVERWRITING_DEMO);
        
        if (backupBeforeOverwritingDemo) {
            File backupFile = new File(Configuration.BACKUPS_FOLDER + File.separator + demoFileName);

            if (backupFile.exists()) {
                Alert alert = MiscUtil.createAlert("A backup file already exists. Would you like to overwrite it?");
                alert.getButtonTypes().setAll(new ButtonType[] {ButtonType.YES, ButtonType.NO});

                ButtonType result = alert.showAndWait().get();

                if (result == ButtonType.NO) {
                    return;
                }

                backupFile.delete();
            }
            
            FileChannel chosenFileIn = new FileInputStream(chosenFile).getChannel();
            FileChannel backupFileOut = new FileOutputStream(backupFile).getChannel();
            
            backupFileOut.transferFrom(chosenFileIn, 0, Long.MAX_VALUE);
            
            chosenFileIn.close();
            backupFileOut.close();
        }

        
        DemoHeader header = loadedDemoFile.getHeader();
        List<CommandMessage> commandMessages = loadedDemoFile.getCommandMessages();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int networkProtocol = header.getNetworkProtocol();
        
        bos.write(header.getBytes());
        
        for (CommandMessage commandMessage : commandMessages) {
            int gameTick = commandMessage.getGameTick();
            
            if (gameTick == skippedGameTick) {
                continue;
            }
            
            if (gameTick > skippedGameTick) {
                gameTick--;
            }
            
            bos.write(commandMessage.getBytes(gameTick, networkProtocol));                
        }
        
        FileOutputStream fos = new FileOutputStream(chosenFile);
        fos.write(bos.toByteArray());
        fos.close();
        
        Alert alert = MiscUtil.createAlert(AlertType.INFORMATION, "Demo has been rewritten.");
        alert.show();
    }
   
    @FXML public void openOutputFolderButtonClick(ActionEvent event) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(new File(Configuration.OUTPUT_FOLDER));
    }
    
}
