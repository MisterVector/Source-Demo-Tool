package org.codespeak.sourcedemotool.scenes;

/**
 * An enum listing all the scene types
 *
 * @author Vector
 */
public enum SceneTypes {
    
    MAIN_WINDOW("MainWindow.fxml");
    
    private final String fxmlDocument;
    
    private SceneTypes(String fxmlDocument) {
        this.fxmlDocument = fxmlDocument;
    }
    
    /**
     * Gets the name of this window type
     * @return name of this window type
     */
    public String getFxmlDocument() {
        return fxmlDocument;
    }
    
    /**
     * Gets the path to this window
     * @return path to this window
     */
    public String getPath() {
        return "/org/codespeak/sourcedemotool/scenes/" + fxmlDocument;
    }
    
}
