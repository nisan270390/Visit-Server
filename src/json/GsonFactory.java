package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    private static Gson gson;

    /**
     * Gets a configured {@link Gson} instance
     *
     * @return {@link Gson} instance
     */
    public synchronized static Gson getGsonInstance() {
        if (gson == null) {
            GsonBuilder builder = new GsonBuilder();

            //builder.setPrettyPrinting();
            gson = builder.create();

        }

        return gson;
    }

    private GsonFactory() {
    }

}
