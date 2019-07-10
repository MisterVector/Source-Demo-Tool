package org.codespeak.sourcedemotool.objects;

import javafx.scene.control.Alert;
import org.codespeak.sourcedemotool.Configuration;

/**
 * A class with miscellaneous utility methods
 *
 * @author Vector
 */
public class MiscUtil {

    public static Alert createAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(Configuration.PROGRAM_TITLE);
        alert.setContentText(msg);

        return alert;
    }
    
}
