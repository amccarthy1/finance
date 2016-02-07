package me.amccarthy.finance.places;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import me.amccarthy.finance.messages.MessageService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adam McCarthy <amccarthy@mail.rit.edu>
 */
public class Places {
    private static final String API_KEY_FILE = "api.key";
    private static Places instance;

    private String apiKey;
    private boolean canQuery;

    private Map<String, String> descriptionToType;

    private Places() {
        try {
            InputStream is = this.getClass().getResourceAsStream(API_KEY_FILE);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            apiKey = r.readLine();
            canQuery = true;
        } catch (IOException e) {
            System.err.println(MessageService.getInstance().getMessage("finance.errors.cannotReadApiKey"));
            canQuery = false;
        }
        descriptionToType = new HashMap<>();
    }

    private static Places getInstance() {
        if (instance == null) {
            instance = new Places();
        }
        return instance;
    }

    public static String getTypeOf(String placeName) {
        Places p = getInstance();
        // cache because API has limits.
        if (p.descriptionToType.containsKey(placeName)) {
            return p.descriptionToType.get(placeName);
        }
        // if we can't query the API then don't query the API.
        if (!p.canQuery) {
            return p.set(placeName, placeName);
        }

        GeoApiContext ctx = new GeoApiContext().setApiKey(p.apiKey);
        TextSearchRequest req = PlacesApi.textSearchQuery(ctx, placeName);
        try {
            PlacesSearchResponse response = req.await();
            PlacesSearchResult[] results = response.results;
            if (results.length > 0 && results[0].types.length > 0) {
                return p.set(placeName, results[0].types[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p.set(placeName, placeName);
    }

    private String set(String placeName, String type) {
        descriptionToType.put(placeName, type);
        return type;
    }
}
