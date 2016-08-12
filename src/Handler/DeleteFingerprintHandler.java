package Handler;

import json.GsonFactory;
import DataModel.Fingerprint;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.FingerprintHome;
import db.HomeFactory;

public class DeleteFingerprintHandler implements IHandler {
	FingerprintHome fingerprintHome;

    public DeleteFingerprintHandler() {
        fingerprintHome = HomeFactory.getFingerprintHome();
    }
    
    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
    	Response res;
    	
    	Fingerprint fprint = GsonFactory.getGsonInstance().fromJson(data, Fingerprint.class);

        boolean isDeleted = fingerprintHome.remove(fprint);

        if (isDeleted == false) {
            res = new Response(Status.failed, "could not delete fingerprint", null);
        } else {
            res = new Response(Status.ok, null, null);
        }

        return res;
    }

}
