package com.example.sae302_application;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private Intervention intervention;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Bouton Retour
        findViewById(R.id.btnBack).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                finish();
            }
        });

        int id = getIntent().getIntExtra("ID", -1);
        intervention = DataRepository.getById(id);

        // Affichage des détails dans l'intervention choisie
        if (intervention != null) {
            ((TextView) findViewById(R.id.detailTitle)).setText(intervention.titre);
            ((TextView) findViewById(R.id.detailHeure)).setText("📅 " + intervention.date + " à " + intervention.heure);
            ((TextView) findViewById(R.id.siteNom)).setText(intervention.site.nom);
            ((TextView) findViewById(R.id.siteAdresse)).setText(intervention.site.adresse);
            ((TextView) findViewById(R.id.siteContact)).setText("Contact : " + intervention.site.contactNom);
            ((TextView) findViewById(R.id.siteTel)).setText("📞 " + intervention.site.contactTel);

            ((TextView) findViewById(R.id.techNom)).setText(intervention.technicien.nom);
            ((TextView) findViewById(R.id.techTel)).setText("📱 " + intervention.technicien.telephone);

            ((TextView) findViewById(R.id.detailDesc)).setText(intervention.description);
            ((TextView) findViewById(R.id.detailMat)).setText(intervention.materiel);
        }

        // Boutons de Statut
        setupStatusButton(R.id.btnStatusEnc, "En cours");
        setupStatusButton(R.id.btnStatusTerm, "Terminée");

        // Bouton Supprimer
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (intervention != null) {
                    DataRepository.deleteIntervention(DetailActivity.this, intervention);
                    Toast.makeText(DetailActivity.this, "Intervention supprimée", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    private void setupStatusButton(int btnId, final String newStatus) {
        // Méthode utilitaire permettant d’associer un comportement de changement de statut à un bouton
        findViewById(btnId).setOnClickListener(new android.view.View.OnClickListener() {
            // Récupère le bouton via son ID et lui attache un écouteur de clic classique avec une classe anonyme.

            @Override
            public void onClick(android.view.View v) {
                // Méthode exécutée automatiquement lorsque l’utilisateur clique sur ce bouton

                if (intervention != null) { // Vérifie qu’une intervention est bien chargée avant de modifier ses données.
                    intervention.statut = newStatus;
                    // Met à jour le statut de l’intervention avec la valeur passée en paramètre.

                    Toast.makeText(DetailActivity.this, "Statut : " + newStatus, Toast.LENGTH_SHORT).show();
                    // Affiche un message visuel confirmant le changement de statut.

                    finish(); // Ferme l’activité pour revenir à l’écran précédent après la modification.
                }
            }
        });
    }
}