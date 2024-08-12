package org.example.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

/**
 * A utility class for working with JSON data.
 *
 * @author Moustafa Elbaih
 */
public class JsonUtils {


    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * Writes a new value to a specific key in a JSON file.
     *
     * @param filePath The path of the JSON file.
     * @param key       The key whose value needs to be updated.
     * @param value     The new value to be written to the specified key.
     */
    public static void writeValueToJsonFile(String filePath, String key, String value) {
        try (FileReader fileReader = new FileReader(filePath)) {
            // Parse the JSON file into a JSON object
            JsonObject jsonObject = new JsonObject();
            // Add or update the value in the JSON object
            jsonObject.addProperty(key, value);

            // Write the modified JSON object back to the file
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                gson.toJson(jsonObject, fileWriter);
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    public static void saveValueToJsonFile(String filePath, String key, String value) {

        // Parse the JSON file into a JSON object
        JsonObject jsonObject = new JsonObject();
        // Add or update the value in the JSON object
        jsonObject.addProperty(key, value);

        // Write the modified JSON object back to the file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(jsonObject, fileWriter);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    public static void writeObjectToJsonFile(String filePath,Object object){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write JSON string to file
            gson.toJson(object, writer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    } public static void writeObjectToJsonFile(String filePath,Object object,Gson gson){
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write JSON string to file
            gson.toJson(object, writer);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
    // Generic method to read JSON file and convert to Java object
    public static <T> T readJsonFromFile(String filePath, Class<T> clazz) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);            return null;
        }
    } public static <T> T readJsonFromFile(String filePath, Class<T> clazz,Gson gson) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            log.error(e.getMessage(), e);            return null;
        }
    }

    // Generic method to read JSON file and convert to Java object (supports complex types)
    public static <T> T readJsonFromFile(String filePath, Type type) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Retrieves the value of a specific key from a JSON file.
     *
     * @param filePath The path of the JSON file.
     * @param key       The key whose value needs to be retrieved.
     * @return The value of the specified key in the JSON file.
     */
    public static String getValueFromJsonFile(String filePath, String key) {
        JsonObject jsonObject = null;
        try (FileReader fileReader = new FileReader(filePath)) {
            // Parse the JSON file into a JSON object
            jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            // Get the Key Value

            // Write the modified JSON object back to the file

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        assert jsonObject != null;
        return jsonObject.get(key).getAsString();
    }
}
