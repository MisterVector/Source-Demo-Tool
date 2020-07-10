package org.codespeak.sourcedemotool;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class representing various settings
 *
 * @author Vector
 */
public class Settings {
   
    public enum SettingFields {
        DEMOS_FOLDER("demos_folder", String.class, ""),
        BACKUP_BEFORE_OVERWRITING_DEMO("backup_before_overwriting_demo", Boolean.class, true);

        private final String key;
        private final Class fieldClass;
        private final Object defaultValue;
        
        private SettingFields(String key, Class fieldClass, Object defaultValue) {
            this.key = key;
            this.fieldClass = fieldClass;
            this.defaultValue = defaultValue;
        }
        
        /**
         * Gets the key of this setting field
         * @return key of this setting field
         */
        public String getKey() {
            return key;
        }

        /**
         * Gets the field class of this setting field
         * @return field class of this setting field
         */
        public Class getFieldClass() {
            return fieldClass;
        }
        
        /**
         * Gets the default value of this setting field
         * @return default value of this setting field
         */
        public Object getDefaultValue() {
            return defaultValue;
        }
    }
 
    private Map<SettingFields, Object> fieldValues = new HashMap<SettingFields, Object>();
    
    private Settings(Map<SettingFields, Object> fieldValues) {
        this.fieldValues = fieldValues;
    }
    
    /**
     * Gets the value of this setting field
     * @param field setting field
     * @return value represented by this setting field
     */
    public <T> T getValue(SettingFields field) {
        return (T) fieldValues.get(field);
    }
    
    /**
     * Sets the value of this setting field
     * @param field setting field
     * @param value value representing this setting field
     */
    public void setValue(SettingFields field, Object value) {
        fieldValues.put(field, value);
    }
    
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        
        for (EnumMap.Entry<SettingFields, Object> entry : fieldValues.entrySet()) {
            SettingFields field = entry.getKey();
            String key = field.getKey();
            Object value = entry.getValue();
            
            json.put(key, value);
        }
        
        return json;
    }
    
    public static Settings fromJSON(JSONObject json) {
        Map<SettingFields, Object> fieldValues = new HashMap<SettingFields, Object>();
        
        for (SettingFields field : SettingFields.values()) {
            String key = field.getKey();
            Class fieldClass = field.getFieldClass();
            Object value = null;

            if (json.has(key)) {
                try {
                    Object tempValue = json.get(key);
                    
                    if (fieldClass == String.class) {
                        if (tempValue instanceof String) {
                            value = tempValue;
                        }
                    } else if (fieldClass == Boolean.class) {
                        if (tempValue instanceof Boolean) {
                            value = tempValue;
                        }
                    }
                } catch (JSONException ex) {
                    
                }
            }
            
            if (value == null) {
                value = field.getDefaultValue();
            }
            
            fieldValues.put(field, value);
        }
        
        return new Settings(fieldValues);
    }
    
}
