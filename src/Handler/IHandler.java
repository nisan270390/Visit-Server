package Handler;

import com.google.gson.JsonElement;

/**
 * Interface for an request handler
 *
 *
 */
public interface IHandler {

    /**
     * Handles a request
     *
     * @param data Data sent with the request
     * @return {@link Response}
     */
    public Response handle(JsonElement data);
}
