package org.codespeak.sourcedemotool.scenes;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.codespeak.sourcedemotool.Configuration;
import org.codespeak.sourcedemotool.demo.DemoFile;
import org.codespeak.sourcedemotool.demo.DemoHeader;

/**
 * Controller for the main window
 *
 * @author Vector
 */
public class MainWindowController implements Initializable {
  
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    @FXML
    public void onSelectDemoFileButtoClick(ActionEvent event) throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(Configuration.DEMOS_FOLDER));
        File chosenFile = chooser.showOpenDialog(null);
        
        if (chosenFile != null) {
            demoFileNameLabel.setText(chosenFile.getName());
            DemoFile demoFile = DemoFile.getDemoFile(chosenFile);
            DemoHeader header = demoFile.getHeader();
            
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
    
}
