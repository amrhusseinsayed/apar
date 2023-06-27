package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileUtil {
    /**
     * This method is used to read the data from the given file path and
     * return it as a JSON
     *
     * @param jsonFilePath the json file path
     * @throws IOException    in case of having invalid path for the file
     * @throws ParseException in case of not being able to parse the
     *                        given file into a json
     */
    public static JSONObject readJsonObject(String jsonFilePath)
            throws IOException, ParseException {
        var reader = new BufferedReader(new FileReader(jsonFilePath));
        var parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }
}
