package Handler;

import java.util.List;

import DataModel.Fingerprint;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.FingerprintHome;
import db.HomeFactory;

public class GetFingerprintListHandler implements IHandler {
	FingerprintHome fingerprintHome;

    public GetFingerprintListHandler() {
        fingerprintHome = HomeFactory.getFingerprintHome();
    }
    
    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
    	Response res;

        List<Fingerprint> fprint = fingerprintHome.getAll();

        if (fprint == null) {
            res = new Response(Status.failed, "could not fetch all fingerprints", null);
        } else {
            res = new Response(Status.ok, null, fprint);
        }

        return res;
    }

}
