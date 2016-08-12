package Handler;

import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.FingerprintHome;
import db.HomeFactory;

public class DeleteAllFingerprintsHandler implements IHandler {
	FingerprintHome fingerprintHome;

    public DeleteAllFingerprintsHandler() {
        fingerprintHome = HomeFactory.getFingerprintHome();
    }
    
    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
    	Response res;

        boolean isDeleted = fingerprintHome.removeAll();

        if (isDeleted == false) {
            res = new Response(Status.failed, "could not delete all fingerprints", null);
        } else {
            res = new Response(Status.ok, null, null);
        }

        return res;
    }

}
