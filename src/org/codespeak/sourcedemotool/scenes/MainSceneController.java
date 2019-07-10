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
import org.codespeak.sourcedemotool.objects.MiscUtil;

/**
 * Controller for the main scene
 *
 * @author Vector
 */
public class MainSceneController implements Initializable {
  
    private DemoFile loadedDemoFile = null;
    private int maxTicks = 0;
    
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
    @FXML private TextField outputFileNameInput;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        skipTickInput.setText("1");
    }    
    
    @FXML
    public void onSelectDemoFileButtonClick(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(Configuration.DEMOS_FOLDER));
        File chosenFile = chooser.showOpenDialog(null);
        
        if (chosenFile != null) {
            String demoFileName = chosenFile.getName();
            String demoFileNameLower = demoFileName.toLowerCase();
            
            if (!demoFileNameLower.endsWith(".dem")) {
                Alert alert = MiscUtil.createAlert("This program can only open .dem files.");
                alert.show();
                
                return;
            }
            
            String demoFileNameWithoutExtension = demoFileName.substring(0, demoFileNameLower.indexOf(".dem"));
            outputFileNameInput.setText(demoFileNameWithoutExtension);
            
            demoFileNameLabel.setText(demoFileName);
            loadedDemoFile = DemoFile.getDemoFile(chosenFile, demoFileName);
            DemoHeader header = loadedDemoFile.getHeader();
            maxTicks = header.getTicks();
            
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
    public void writeOutputFileButtonClick(ActionEvent event) throws IOException {
        if (loadedDemoFile == null) {
            Alert alert = MiscUtil.createAlert("No demo file has been loaded. Cannot write output file!");
            alert.show();
            
            return;
        }
        
        int skipTick = 0;
        
        try {
            String value = skipTickInput.getText();
            skipTick = Integer.parseInt(value);
            
            if (skipTick < 1 || skipTick > maxTicks) {
                Alert alert = MiscUtil.createAlert("Invalid tick value. Must be in the range 1 to " + maxTicks + ".");
                alert.show();
                
                return;
            }
        } catch (NumberFormatException ex) {
            Alert alert = MiscUtil.createAlert("The tick value specified is not a number.");
            alert.show();
            
            return;
        }
        
        DemoHeader header = loadedDemoFile.getHeader();
        List<CommandMessage> commandMessages = loadedDemoFile.getCommandMessages();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String outputFileName = outputFileNameInput.getText();
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
        
        FileOutputStream fos = new FileOutputStream(new File(Configuration.OUTPUT_FOLDER + "\\" + outputFileName + ".dem"));
        fos.write(bos.toByteArray());
        fos.close();
        
        Alert alert = MiscUtil.createAlert("Output file has been written and can be found in the output folder.");
        alert.show();
    }
    
}
