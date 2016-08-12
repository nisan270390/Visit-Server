package Handler;

import Handler.Response.Status;

import com.google.gson.JsonElement;

/**
 * Handler which is used if no appropriate handler could be found
 *
 * @see IHandler
 *
 */
public class UnknownHandler implements IHandler {

    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
        return new Response(Status.failed, "no such action", null);
    }

}