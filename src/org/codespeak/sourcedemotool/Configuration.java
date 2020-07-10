package org.codespeak.sourcedemotool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import org.json.JSONObject;

/**
 *
 * @author Vector
 */
public class Configuration {

    public static String PROGRAM_VERSION = "0.0.0";
    public static String PROGRAM_NAME = "Source Demo Tool";
    public static String PROGRAM_TITLE = PROGRAM_NAME + " v" + PROGRAM_VERSION;
    public static String DEMOS_FOLDER = "demos";
    public static String LOGS_FOLDER = "logs";
    public static String OUTPUT_FOLDER = "output";
    public static String SETTINGS_FILE = "settings.json";
    
    private static Settings settings = null;

    private static Settings loadSettings() {
        File settingsFile = new File(SETTINGS_FILE);
        
        if (!settingsFile.exists()) {
            return Settings.fromJSON(new JSONObject());
        }
        
        try {
            byte[] bytes = Files.readAllBytes(settingsFile.toPath());
            String jsonString = new String(bytes);
            JSONObject json = new JSONObject(jsonString);
            
            return Settings.fromJSON(json);
        } catch (IOException ex) {
            return Settings.fromJSON(new JSONObject());
        }
    }
    
    /**
     * Gets the settings. The settings will be loaded if not initialized
     * @return loaded settings
     */
    public static Settings getSettings() {
        if (settings == null) {
            settings = loadSettings();
        }
        
        return settings;
    }

    /**
     * Writes the settings to file
     */
    public static void writeSettingsToFile() {
        if (settings == null) {
            return;
        }
        
        File settingsFile = new File(SETTINGS_FILE);
        JSONObject json = settings.toJSON();

        if (settingsFile.exists()) {
            settingsFile.delete();
        }
        
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(settingsFile))) {
            writer.write(json.toString(4));
        } catch (IOException ex) {
            
        }
    }

}
