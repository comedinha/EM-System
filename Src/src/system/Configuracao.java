package system;

import javafx.beans.value.ObservableValue;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.PropertyEditor;

/**
 * Configurações do sistema
 * @author Bruno Carvalho, Luiz Eduardo, Mateus Tabaldi
 *@version 1.0
 */
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

    /**
	 * Adiciona uma configuração ao mapa de configurações.
	 * @param key chave da configuração
	 * @param value coloca o valor da configuração
	 */
    public static void configDataPut(String key, Object value) {
    	configDataMap.put(key, value);
    }

    /**
	 * Busca uma configuração ao mapa de configurações.
	 */
    public static Set<String> configDataMapKeySet() {
    	return configDataMap.keySet();
    }

    /**
	 * Exclui o mapa de configurações
	 */
    public static void configDataClean() {
    	configDataMap.clear();
    }

    /**
	 * Busca uma configuração ao mapa de configurações baseada no valor.
	 * @param key chave da configuração
	 */
    public static String configDataGetValue(String key) {
    	return configDataMap.get(key).toString();
    }

    /**
	 * Adiciona uma configuração ao funcionário
	 * @param id id do funcionário
	 * @param key chave da configuração
	 * @param value valor da chave da configuração
	 * @throws Exception
	 */
    public static void adicionaConfiguracaoFuncionario(int id, String key, String value) throws Exception {
    	dao.Configuracao.adicionaConfiguracaoFuncionario(id, key, value);
    }

    /**
	 * Adiciona uma configuração global
	 * @param key chave da configuração
	 * @param value valor da chave da configuração
	 * @throws Exception
	 */
    public static void adicionaConfiguracaoGlobal(String key, String value) throws Exception {
    	dao.Configuracao.adicionaConfiguracaoGlobal(key, value);
    }

    /**
	 * Atualiza uma configuração ao funcionário
	 * @param id id do funcionário
	 * @param key chave da configuração
	 * @param value valor da chave da configuração
	 * @throws Exception
	 */
    public static void updateConfiguracaoFuncionario(int id, String key, String value) throws Exception {
    	dao.Configuracao.updateConfiguracaoFuncionario(id, key, value);
    }

    /**
	 * Atualiza uma configuração global
	 * @param key chave da configuração
	 * @param value valor da chave da configuração
	 * @throws Exception
	 */
    public static void updateConfiguracaoGlobal(String key, String value) throws Exception {
    	dao.Configuracao.updateConfiguracaoGlobal(key, value);
    }

    /**
	 * Busca uma configuração ao funcionário
	 * @param id id do funcionário
	 * @param key chave da configuração
	 * @throws Exception
	 */
    public static ResultSet getConfiguracaoFuncionario(int id, String key) throws Exception {
    	return dao.Configuracao.getConfigFuncionario(id, key);
    }

    /**
	 * Busca uma configuração global
	 * @param key chave da configuração
	 * @param value valor da chave da configuração
	 * @throws Exception
	 */
    public static ResultSet getConfiguracaoGlobal(String key) throws Exception {
    	return dao.Configuracao.getConfigGlobal(key);
    }
}