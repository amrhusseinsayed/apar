package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileUtil {
    private static JSONObject jsonObject;

    /**
     * This method is used to parse the given json file using its path
     * to an actual json object
     *
     * @param jsonFilePath the json file path
     * @throws IOException    in case of having invalid path for the file
     * @throws ParseException in case of not being able to parse the
     *                        given file into a json
     */
    public static void setJsonObject(String jsonFilePath)
            throws IOException, ParseException {
        var reader = new BufferedReader(new FileReader(jsonFilePath));
        var parser = new JSONParser();
        jsonObject = (JSONObject) parser.parse(reader);
    }

    /**
     * This method is used to get the main json inside the
     * test data json file
     *
     * @return the main json inside the test data json file
     */
    public static JSONObject getJsonObject() {
        return jsonObject;
    }
}
