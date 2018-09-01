package com.andreeamilitaru.sandwich_club;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andreeamilitaru.sandwich_club.model.Sandwich;
import com.andreeamilitaru.sandwich_club.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private LinearLayout alsoKnownAsContainer;
    private LinearLayout placeOfOriginContainer;
    private LinearLayout descriptionContainer;
    private LinearLayout ingredientsContainer;

    private TextView alsoKnownAsTv;
    private TextView placeOfOriginTv;
    private TextView descriptionTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        alsoKnownAsContainer = findViewById(R.id.also_known_container);
        placeOfOriginContainer = findViewById(R.id.origin_container);
        descriptionContainer = findViewById(R.id.description_container);
        ingredientsContainer = findViewById(R.id.ingredients_container);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Populate UI or hide views for which there is no data
        String alsoKnownAs = listToString(sandwich.getAlsoKnownAs());
        if (alsoKnownAs.equals("")) {
            alsoKnownAsContainer.setVisibility(View.GONE);
        } else {
            alsoKnownAsTv.setText(alsoKnownAs);
        }

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.equals("")) {
            placeOfOriginContainer.setVisibility(View.GONE);
        } else {
            placeOfOriginTv.setText(placeOfOrigin);
        }

        String description = sandwich.getDescription();
        if (description.equals("")) {
            descriptionContainer.setVisibility(View.GONE);
        } else {
            descriptionTv.setText(description);
        }

        String ingredients = listToString(sandwich.getIngredients());
        if(ingredients.equals("")){
            ingredientsContainer.setVisibility(View.GONE);
        } else {
            ingredientsTv.setText(ingredients);
        }
    }

    private String listToString(List<String> stringList) {
        StringBuilder outputString = new StringBuilder();

        for (int i = 0; i < stringList.size(); i++) {
            String element = stringList.get(i);
            outputString.append(element);

            if (i < stringList.size() - 1) {
                // Only add a comma if the loop is not at the last item
                outputString.append(", ");
            }
        }

        return outputString.toString();
    }
}
