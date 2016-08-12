package Handler;

import Handler.Response.Status;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import json.GsonFactory;

/**
 * Handler for a request. Each request read by a connection handler is passed to
 * this handler
 *
 *
 */
public class RequestHandler {

    public static final String ACTION_TOKEN = "action";
    public static final String DATA_TOKEN = "data";
    public static final String NO_ACTION = "no action specified";

    /**
     * Does handle an request
     *
     * @param request Request
     * @return response as string
     */
    public String request(String request) {

        Response response = new Response(Status.failed, null, null);

        Gson gson = GsonFactory.getGsonInstance();
        JsonParser parser = new JsonParser();

        IHandler handler = null;

        try {

            JsonElement root = parser.parse(request);

            if (root.isJsonObject()) {
                JsonObject rootobj = root.getAsJsonObject();
                JsonElement action = rootobj.get(ACTION_TOKEN);
                JsonElement data = rootobj.get(DATA_TOKEN);

                if (action == null) {
                    throw new Exception(NO_ACTION);
                }

                RequestType type = gson.fromJson(action, RequestType.class);
                handler = HandlerFactory.findHandler(type);
                response = handler.handle(data);

            }

        } catch (JsonParseException e) {
            response = new Response(Status.jsonError, e.getMessage(), null);
        } catch (Exception e) {
            response = new Response(Status.failed, e.getMessage(), null);
        }

        String response_str = "";
        try {
            response_str = gson.toJson(response);
        } catch (Exception e) {
            response_str = "{\"status\":\"" + Status.jsonError + "\",\"message\":\"" + e + ": " + e.getMessage() + "\"}";
        }


        return response_str;
    }

    /**
     * Different request type supported by the server
     *
     * @author Pascal Brogle (broglep@student.ethz.ch)
     *
     */
    public enum RequestType {

    	setLocation, getFingerprintList, setFingerprint, deleteFingerprint, deleteAllFingerprint, getLocation, getMapList, getPath, getBuildingMaps, getBuildings,
    	setMap, removeMap, getLocationList, updateLocation, removeLocation
    }

}
