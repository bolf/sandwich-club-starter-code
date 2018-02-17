package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private final static String JSON_PARSING_ERROR_TAG = "JSON_PARSING_ERROR";

    public static ArrayList<Sandwich> getSandwichDetailsList(Context context){
        ArrayList<Sandwich> sandwichDetailsList = new ArrayList<>();

        String[] sandwichDetails = context.getResources().getStringArray(R.array.sandwich_details);

        try{
            for (String sandwichDetail:sandwichDetails) {
                sandwichDetailsList.add(parseSandwichJson(sandwichDetail));
            }
        }catch (JSONException e){
            Log.e(JSON_PARSING_ERROR_TAG,e.getMessage());
        }

        return sandwichDetailsList;
    }

    private static Sandwich parseSandwichJson(String json) throws JSONException{
        ArrayList<String> ingredientList = new ArrayList<>();
        ArrayList<String> akaList = new ArrayList<>();

        JSONObject rootObj = new JSONObject(json);

        JSONObject nameObj = rootObj.getJSONObject("name");
        String mainName    = nameObj.getString("mainName");

        JSONArray akaJsonArray = nameObj.getJSONArray("alsoKnownAs");
        for (int i = 0; i < akaJsonArray.length(); i++){
            akaList.add(akaJsonArray.getString(i));
        }

        String placeOfOrigin = rootObj.getString("placeOfOrigin");
        String description   = rootObj.getString("description");
        String imagePath     = rootObj.getString("image");

        JSONArray ingredientsJsonArray = rootObj.getJSONArray("ingredients");
        for (int i = 0; i < ingredientsJsonArray.length(); i++){
            ingredientList.add(ingredientsJsonArray.getString(i));
        }

        return new Sandwich(mainName,akaList,placeOfOrigin,description,imagePath,ingredientList);
    }
}
