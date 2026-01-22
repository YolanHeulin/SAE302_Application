package com.example.sae302_application;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        int id = getIntent().getIntExtra("ID", -1);
        Intervention intervention = DataRepository.getById(id);

        if (intervention != null) {
            ((TextView) findViewById(R.id.detailTitle)).setText(intervention.titre);
            ((TextView) findViewById(R.id.detailHeure)).setText(intervention.heure);
            ((TextView) findViewById(R.id.detailDesc)).setText(intervention.description);
        }
    }
}