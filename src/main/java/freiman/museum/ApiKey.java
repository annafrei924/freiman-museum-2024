package freiman.museum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKey {
    private final String key;

    public ApiKey() {
        Properties properties = new Properties();
        InputStream in = ApiKey.class.getResourceAsStream("/apikey.properties");

        try {
            if (in != null) {
                properties.load(in);
                key = properties.getProperty("apikey");
            } else {
                key = System.getenv("apikey");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public String get() {
        return key;
    }
}
