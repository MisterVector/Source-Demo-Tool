package org.codespeak.sourcedemotool.scenes;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.codespeak.sourcedemotool.Configuration;
import org.codespeak.sourcedemotool.Settings;
import org.codespeak.sourcedemotool.Settings.SettingFields;
import org.codespeak.sourcedemotool.objects.MiscUtil;

/**
 * FXML Controller class
 *
 * @author Vector
 */
public class SettingsSceneController implements Initializable {

    private Settings settings = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settings = Configuration.getSettings();
    }    

    @FXML
    public void onOkButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
