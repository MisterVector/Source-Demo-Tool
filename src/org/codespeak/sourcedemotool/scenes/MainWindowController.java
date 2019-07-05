package org.codespeak.sourcedemotool.scenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.codespeak.sourcedemotool.Configuration;
import org.codespeak.sourcedemotool.demo.DemoContents;

/**
 *
 * @author Vector
 */
public class MainWindowController implements Initializable {
  
    @FXML private Label demoFileNameLabel;
    @FXML private Label headerLabel;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML public void onSelectDemoFileButtoClick(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(Configuration.DEMOS_FOLDER));
        File chosenFile = chooser.showOpenDialog(null);
        
        if (chosenFile != null) {
            demoFileNameLabel.setText(chosenFile.getName());
            FileInputStream demoFileStream = new FileInputStream(chosenFile);
            DemoContents demoContents = DemoContents.getDemoContents(demoFileStream);
            
            headerLabel.setText(demoContents.getHeader());
            demoProtocolLabel.setText("" + demoContents.getDemoProtocol());
            networkProtocolLabel.setText("" + demoContents.getNetworkProtocol());
            serverNameLabel.setText(demoContents.getServerName());
            clientNameLabel.setText(demoContents.getClientName());
            mapNameLabel.setText(demoContents.getMapName());
            gameDirectoryLabel.setText(demoContents.getGameDirectory());
            playbackTimeLabel.setText("" + demoContents.getPlaybackTime());
            ticksLabel.setText("" + demoContents.getTicks());
            framesLabel.setText("" + demoContents.getFrames());
            signOnLengthLabel.setText("" + demoContents.getSignonLength());
        }
    }
    
}
