package utils;

import org.apache.log4j.xml.DOMConfigurator;

public class Log4jUtil {
    /**
     * This method is used to load the configurations to be
     * used for logging with the help of the Log4j library
     * during the execution
     *
     * @param configFilePath the path of the los configurations file
     */
    public static void loadConfigurations(String configFilePath) {
        DOMConfigurator.configure(configFilePath);
    }
}
