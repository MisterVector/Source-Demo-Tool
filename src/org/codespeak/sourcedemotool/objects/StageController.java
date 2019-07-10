package org.codespeak.sourcedemotool.objects;

import javafx.stage.Stage;

/**
 * A class representing a stage and a controller
 *
 * @author Vector
 */
public class StageController<T> {
    
    private Stage stage;
    private T controller;
    
    public StageController(Stage stage, T controller) {
        this.stage = stage;
        this.controller = controller;
    }
    
    /**
     * Gets the stage
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
    
    /**
     * Gets the controller
     * @return 
     */
    public T getController() {
        return controller;
    }
    
}
