package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.CommonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String SANDWICH_PARCEL = "sandwichDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Sandwich sandwichDetails = null;
        try {
             sandwichDetails = intent.getParcelableExtra(SANDWICH_PARCEL);
        }catch (NullPointerException e){
            closeOnError();
        }

        populateUI(sandwichDetails);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        try{
            Picasso.with(this)
                    .load(sandwichDetails.getImage())
                    .into(ingredientsIv);
        } catch (NullPointerException e) {
            closeOnError();
        }

        setTitle(sandwichDetails.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwichDetails) {
        //place
        TextView place = findViewById(R.id.origin_tv);
        String origin = sandwichDetails.getPlaceOfOrigin();
        if(! TextUtils.isEmpty(origin)) {
            place.setText(origin);
        }else{
            place.setVisibility(View.INVISIBLE);
            TextView placeLabel = findViewById(R.id.place_of_origin_label);
            placeLabel.setVisibility(View.INVISIBLE);
        }

        //aka
        TextView akaTv = findViewById(R.id.also_known_tv);
        List<String> akaList = sandwichDetails.getAlsoKnownAs();
        String akaStr = CommonUtils.getStringOutOfStringList(akaList);
        if (!TextUtils.isEmpty(akaStr)) {
            akaTv.setText(akaStr);
        } else {
            akaTv.setVisibility(View.INVISIBLE);
            TextView akaLabel = findViewById(R.id.aka_label);
            akaLabel.setVisibility(View.INVISIBLE);
        }

        //ingredients
        List<String> ingrList = sandwichDetails.getIngredients();
        String ingrStr = CommonUtils.getStringOutOfStringList(ingrList);
        TextView ingredients = findViewById(R.id.ingredients_tv);
        ingredients.setText(ingrStr);

        //description
        TextView descr = findViewById(R.id.description_tv);
        descr.setText(sandwichDetails.getDescription());
    }
}
