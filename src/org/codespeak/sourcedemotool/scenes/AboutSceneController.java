package org.codespeak.sourcedemotool.scenes;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.codespeak.sourcedemotool.Configuration;

/**
 * Controller for the about scene
 *
 * @author Vector
 */
public class AboutSceneController implements Initializable {

    @FXML private Label programNameLabel;
    @FXML private Label websiteLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String existingText = programNameLabel.getText().replace("%p", Configuration.PROGRAM_TITLE);
        programNameLabel.setText(existingText);
    }
    
    @FXML
    public void onWebsiteLabelClick() throws URISyntaxException, IOException {
        URI uri = new URI(websiteLabel.getText());
        
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(uri);
    }
    
    @FXML
    public void closeWindowButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
