package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int DEFAULT_POSITION = -1;
    private static final String SANDWICH_ARRAY_BUNDLE_NAME = "sandwichArray";

    ArrayList<Sandwich> mSandwichDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the sandwich details from JSON or saved instance
        if(savedInstanceState == null || !savedInstanceState.containsKey(SANDWICH_ARRAY_BUNDLE_NAME)) {
            mSandwichDetailsList = JsonUtils.getSandwichDetailsList(this);
        }else{
            mSandwichDetailsList = savedInstanceState.getParcelableArrayList(SANDWICH_ARRAY_BUNDLE_NAME);
        }

        //set the ListView of sandwiches
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, sandwiches);

        // Simplification: Using a ListView instead of a RecyclerView
        ListView listView = findViewById(R.id.sandwiches_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != DEFAULT_POSITION) {
                    launchDetailActivity(position);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SANDWICH_ARRAY_BUNDLE_NAME,mSandwichDetailsList);
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.SANDWICH_PARCEL,mSandwichDetailsList.get(position));
        startActivity(intent);
    }
}
