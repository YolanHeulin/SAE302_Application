package com.example.sae302_application;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private Intervention intervention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // --- 1. LE BOUTON RETOUR (C'est ici que ça se passe) ---
        Button btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // La commande finish() ferme l'écran actuel
                    // et revient donc naturellement au menu principal
                    finish();
                }
            });
        }

        // 2. Récupération de l'intervention cliquée
        int id = getIntent().getIntExtra("ID", -1);
        intervention = DataRepository.getById(id);

        if (intervention != null) {
            // Remplissage des textes
            setTextIfFound(R.id.detailTitle, intervention.titre);
            setTextIfFound(R.id.detailHeure, "⏰ " + intervention.heure + " (" + intervention.dureeMin + " min)");

            setTextIfFound(R.id.siteNom, intervention.site.nom);
            setTextIfFound(R.id.siteAdresse, intervention.site.adresse);
            setTextIfFound(R.id.siteContact, "Contact : " + intervention.site.contactNom);
            setTextIfFound(R.id.siteTel, "📞 " + intervention.site.contactTel);

            setTextIfFound(R.id.techNom, intervention.technicien.nom);
            setTextIfFound(R.id.techTel, "📱 " + intervention.technicien.telephone);

            setTextIfFound(R.id.detailDesc, intervention.description);
            setTextIfFound(R.id.detailMat, intervention.materiel);
        }

        // 3. Gestion des boutons de statut
        setupStatusButton(R.id.btnStatusPlan, "Planifiée");
        setupStatusButton(R.id.btnStatusEnc, "En cours");
        setupStatusButton(R.id.btnStatusTerm, "Terminée");
    }

    // Petite fonction utilitaire pour éviter les crashs si un ID change
    private void setTextIfFound(int id, String text) {
        TextView tv = findViewById(id);
        if (tv != null) tv.setText(text);
    }

    private void setupStatusButton(int btnId, String newStatus) {
        Button btn = findViewById(btnId);
        if (btn != null) {
            btn.setOnClickListener(v -> {
                if (intervention != null) {
                    intervention.statut = newStatus;
                    Toast.makeText(this, "Statut changé : " + newStatus, Toast.LENGTH_SHORT).show();
                    finish(); // On revient aussi en arrière quand on valide un statut
                }
            });
        }
    }
}