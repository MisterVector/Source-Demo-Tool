package org.codespeak.sourcedemotool.scenes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.codespeak.sourcedemotool.Configuration;
import org.codespeak.sourcedemotool.demo.CommandMessage;
import org.codespeak.sourcedemotool.demo.DemoFile;
import org.codespeak.sourcedemotool.demo.DemoHeader;

/**
 * Controller for the main window
 *
 * @author Vector
 */
public class MainWindowController implements Initializable {
  
    private DemoFile loadedDemoFile = null;
    
    @FXML private Label demoFileNameLabel;
    @FXML private Label headerNameLabel;
    @FXML private Label demoProtocolLabel;
    @FXML private Label networkProtocolLabel;
    @FXML private Label serverNameLabel;
    @FXML private Label clientNameLabel;
    @FXML private Label mapNameLabel;
    @FXML private Label gameDirectoryLabel;
    @FXML private Label playbackTimeLabel;
    @FXML private Label ticksLabel;
    @FXML private Label framesLabel;
    @FXML private Label signOnLengthLabel;
    @FXML private TextField skipTickInput;
    
    private Alert createAlert(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(Configuration.PROGRAM_TITLE);
        alert.setContentText(msg);

        return alert;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    public void onSelectDemoFileButtonClick(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(Configuration.DEMOS_FOLDER));
        File chosenFile = chooser.showOpenDialog(null);
        
        if (chosenFile != null) {
            String demoFileName = chosenFile.getName();
            
            if (!demoFileName.endsWith(".dem")) {
                Alert alert = createAlert("This program can only open .dem files.");
                alert.show();
                
                return;
            }
            
            demoFileNameLabel.setText(chosenFile.getName());
            loadedDemoFile = DemoFile.getDemoFile(chosenFile, demoFileName);
            DemoHeader header = loadedDemoFile.getHeader();
            
            headerNameLabel.setText(header.getHeaderName());
            demoProtocolLabel.setText("" + header.getDemoProtocol());
            networkProtocolLabel.setText("" + header.getNetworkProtocol());
            serverNameLabel.setText(header.getServerName());
            clientNameLabel.setText(header.getClientName());
            mapNameLabel.setText(header.getMapName());
            gameDirectoryLabel.setText(header.getGameDirectory());
            playbackTimeLabel.setText("" + header.getPlaybackTime());
            ticksLabel.setText("" + header.getTicks());
            framesLabel.setText("" + header.getFrames());
            signOnLengthLabel.setText("" + header.getSignonLength());
        }
    }
    
    @FXML
    public void rewriteDemoButtonClick(ActionEvent event) throws IOException {
        if (loadedDemoFile == null) {
            Alert alert = createAlert("No demo file has been loaded. Cannot rewrite!");
            alert.show();
            
            return;
        }
        
        int skipTick = 0;
        
        try {
            String value = skipTickInput.getText();
            skipTick = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            Alert alert = createAlert("The tick value specified is not a number.");
            alert.show();
            
            return;
        }
        
        DemoHeader header = loadedDemoFile.getHeader();
        List<CommandMessage> commandMessages = loadedDemoFile.getCommandMessages();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String fileName = loadedDemoFile.getFileName();
        int networkProtocol = header.getNetworkProtocol();
        
        bos.write(header.getBytes());
        
        for (CommandMessage commandMessage : commandMessages) {
            int cmdTickCount = commandMessage.getTickCount();
            
            if (cmdTickCount == skipTick) {
                continue;
            }
            
            if (cmdTickCount > skipTick) {
                cmdTickCount--;
            }
            
            bos.write(commandMessage.getBytes(cmdTickCount, networkProtocol));                
        }
        
        FileOutputStream fos = new FileOutputStream(new File(Configuration.OUTPUT_FOLDER + "\\" + fileName));
        fos.write(bos.toByteArray());
        fos.close();
        
        Alert alert = createAlert(fileName + " has been re-written and can be found in the output folder.");
        alert.show();
    }
    
}
