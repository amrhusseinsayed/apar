package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtil {
    private static Properties properties;

    /**
     * This method is used to get the properties inside any
     * .properties file using the given path
     *
     * @param propertyFilePath the path of the properties file
     * @throws IOException in case of having invalid file path
     */
    public static void setProperties(String propertyFilePath) throws IOException {
        properties = new Properties();
        BufferedReader reader = new BufferedReader(new FileReader(propertyFilePath));

        properties.load(reader);
        reader.close();
    }

    /**
     * This method is used to get the properties inside the
     * configuration file
     *
     * @return the properties inside the configuration file
     */
    public static Properties getProperties() {
        return properties;
    }
}
