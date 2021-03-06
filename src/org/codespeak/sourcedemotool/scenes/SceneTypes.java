package org.codespeak.sourcedemotool.scenes;

/**
 * An enum listing all the scene types
 *
 * @author Vector
 */
public enum SceneTypes {
    
    MAIN_SCENE("MainScene.fxml"),
    ABOUT_SCENE("AboutScene.fxml"),
    DEMO_FILE_SCENE("DemoFileScene.fxml"),
    SETTINGS_SCENE("SettingsScene.fxml");
    
    private final String fxmlDocument;
    
    private SceneTypes(String fxmlDocument) {
        this.fxmlDocument = fxmlDocument;
    }
    
    /**
     * Gets the name of this scene type
     * @return name of this scene type
     */
    public String getFxmlDocument() {
        return fxmlDocument;
    }
    
    /**
     * Gets the path to this scene
     * @return path to this scene
     */
    public String getPath() {
        return "/org/codespeak/sourcedemotool/scenes/" + fxmlDocument;
    }
    
}
