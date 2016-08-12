package Handler;

import json.GsonFactory;
import DataModel.Fingerprint;
import DataModel.Location;
import DataModel.Measurement;
import Handler.Response.Status;

import com.google.gson.JsonElement;

import db.FingerprintHome;
import db.HomeFactory;

/**
 * @see IHandler
 *
 */
public class SetFingerprintHandler implements IHandler {

    FingerprintHome fingerprintHome;

    public SetFingerprintHandler() {
        fingerprintHome = HomeFactory.getFingerprintHome();
    }

    /**
     * @see IHandler#handle(JsonElement)
     */
    @Override
    public Response handle(JsonElement data) {
        Response res;

        Fingerprint fprint = GsonFactory.getGsonInstance().fromJson(data, Fingerprint.class);
        if (fprint.getLocation() != null && ((Location) fprint.getLocation()).getId() != null && ((Location) fprint.getLocation()).getId() != -1) {
            Location l = HomeFactory.getLocationHome().getLocation(((Location) fprint.getLocation()).getId(), null);
            fprint = new Fingerprint(l, (Measurement) fprint.getMeasurement());
        }
        fprint = fingerprintHome.add(fprint);

        if (fprint == null) {
            res = new Response(Status.failed, "could not add to database", null);
        } else {
            res = new Response(Status.ok, null, fprint);
        }

        return res;
    }

}
