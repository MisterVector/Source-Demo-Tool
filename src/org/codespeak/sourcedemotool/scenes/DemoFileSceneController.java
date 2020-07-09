package org.codespeak.sourcedemotool.scenes;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.codespeak.sourcedemotool.demo.DemoFile;
import org.codespeak.sourcedemotool.demo.DemoHeader;

/**
 * FXML Controller class
 *
 * @author Vector
 */
public class DemoFileSceneController implements Initializable {

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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void showDemoFile(DemoFile demo) {
        DemoHeader header = demo.getHeader();

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
    
    @FXML
    public void onCloseWindowButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
