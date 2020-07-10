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

    @FXML private Label demosFolderLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        settings = Configuration.getSettings();
        demosFolderLabel.setText(settings.getValue(SettingFields.DEMOS_FOLDER));
    }    

    @FXML
    public void onSelectFolderButtonClick(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        String demosFolderText = demosFolderLabel.getText();
        
        if (!MiscUtil.isNullOrEmpty(demosFolderText)) {
            chooser.setInitialDirectory(new File(demosFolderText));
        }

        File chosenFolder = chooser.showDialog(null);
        
        if (chosenFolder != null) {
            demosFolderLabel.setText(chosenFolder.toString());
        }
    }

    @FXML
    public void onOkButtonClick(ActionEvent event) {
	settings.setValue(SettingFields.DEMOS_FOLDER, demosFolderLabel.getText());
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void onCloseButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
