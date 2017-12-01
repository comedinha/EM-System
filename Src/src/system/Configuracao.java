package system;

import javafx.beans.value.ObservableValue;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

public class Configuracao implements PropertySheet.Item {
    private static Map<String, Object> configDataMap = new LinkedHashMap<>();

    private String key;
    private String category, name;

    public Configuracao(String key) 
    {
        this.key = key;
        String[] skey = key.split("\\.", 2);
        category = skey[0];
        name = skey[1];
    }

    @Override
    public Class<?> getType() {
        return configDataMap.get(key).getClass();
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Object getValue() {
        return configDataMap.get(key);
    }

    @Override
    public void setValue(Object value) {
    	configDataMap.put(key, value);
    }

    @Override
    public Optional<ObservableValue<? extends Object>> getObservableValue() {
        return Optional.empty();
    }

    @Override
    public Optional<Class<? extends PropertyEditor<?>>> getPropertyEditorClass() {
        return Optional.empty();
    }

    public static void configDataPut(String key, Object value) {
    	configDataMap.put(key, value);
    }

    public static Set<String> configDataMapKeySet() {
    	return configDataMap.keySet();
    }

    public static void configDataClean() {
    	configDataMap.clear();
    }

    public static String configDataGetValue(String key) {
    	return configDataMap.get(key).toString();
    }
}