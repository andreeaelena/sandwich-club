package com.andreeamilitaru.sandwich_club.utils;

import com.andreeamilitaru.sandwich_club.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichJsonObject = new JSONObject(json);
            JSONObject nameObject = sandwichJsonObject.getJSONObject("name");
            String mainName = nameObject.getString("mainName");
            JSONArray alsoKnownAsObject = nameObject.getJSONArray("alsoKnownAs");
            String placeOfOrigin = sandwichJsonObject.getString("placeOfOrigin");
            String description = sandwichJsonObject.getString("description");
            String image = sandwichJsonObject.getString("image");
            JSONArray ingredientsObject = sandwichJsonObject.getJSONArray("ingredients");

            List<String> alsoKnownAs = jsonArrayToList(alsoKnownAsObject);
            List<String> ingredients = jsonArrayToList(ingredientsObject);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Read all values from the JSONArray into the ArrayList
     */
    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            arrayList.add(jsonArray.getString(i));
        }
        return arrayList;
    }
}
