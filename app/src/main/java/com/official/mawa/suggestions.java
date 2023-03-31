package com.official.mawa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class suggestions extends AppCompatActivity {

    String name, description, token;
    TextView name1 , description1;
    ImageView im1, im2, im3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        Utils.blackiconStatus(suggestions.this, R.color.white);

        name = getIntent().getStringExtra("name");
        description = getIntent().getStringExtra("description");
        token = getIntent().getStringExtra("token");

        name1 = findViewById(R.id.Name);
        description1 = findViewById(R.id.Description);

        im1 = findViewById(R.id.sanitization);
        im2 = findViewById(R.id.water);
        im3 = findViewById(R.id.boil);

        if (token.contains("1")){
            name1.setText(name);
            description1.setText(description);
            im1.setVisibility(View.VISIBLE);
        } else if (token.contains("2")){
            name1.setText(name);
            description1.setText(description);
            im2.setVisibility(View.VISIBLE);
        } else {
            name1.setText(name);
            description1.setText(description);
            im3.setVisibility(View.VISIBLE);
        }
    }
}