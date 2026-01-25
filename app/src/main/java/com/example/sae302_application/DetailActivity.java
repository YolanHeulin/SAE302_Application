package com.example.sae302_application;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Intervention intervention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        int id = getIntent().getIntExtra("ID", -1);
        intervention = DataRepository.getById(id);

        if (intervention != null) {
            ((TextView) findViewById(R.id.detailTitle)).setText(intervention.titre);
            ((TextView) findViewById(R.id.detailHeure)).setText("⏰ " + intervention.heure);

            // Infos site
            ((TextView) findViewById(R.id.siteNom)).setText(intervention.site.nom);
            ((TextView) findViewById(R.id.siteAdresse)).setText(intervention.site.adresse);
            ((TextView) findViewById(R.id.siteContact)).setText("Contact : " + intervention.site.contactNom);
            ((TextView) findViewById(R.id.siteTel)).setText("📞 " + intervention.site.contactTel);

            // Infos technicien
            ((TextView) findViewById(R.id.techNom)).setText(intervention.technicien.nom);
            ((TextView) findViewById(R.id.techTel)).setText("📱 " + intervention.technicien.telephone);

            // Infos mission
            ((TextView) findViewById(R.id.detailDesc)).setText(intervention.description);
            ((TextView) findViewById(R.id.detailMat)).setText(intervention.materiel);
        }

        // Configuration des boutons d'actions
        setupStatusButton(R.id.btnStatusPlan, "Planifiée");
        setupStatusButton(R.id.btnStatusTerm, "Annulée");
        setupStatusButton(R.id.btnStatusTerm, "Reportée");
        setupStatusButton(R.id.btnStatusEnc, "En cours");
        setupStatusButton(R.id.btnStatusTerm, "Terminée");
    }

    private void setupStatusButton(int btnId, String newStatus) {
        findViewById(btnId).setOnClickListener(v -> {
            if (intervention != null) {
                intervention.statut = newStatus;
                Toast.makeText(this, "Statut mis à jour : " + newStatus, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}