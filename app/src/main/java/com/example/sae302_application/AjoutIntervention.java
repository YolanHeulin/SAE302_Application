package com.example.sae302_application;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class AjoutIntervention extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Méthode appelée automatiquement lors de la création de l’activité
        super.onCreate(savedInstanceState);
        // Appel à la méthode parent pour initialiser correctement l’activité
        setContentView(R.layout.activity_add_intervention);
        // Chargement du layout XML associé à cet écran

        // Récupération du bouton "Retour" et association d’un écouteur de clic avec une classe anonyme
        findViewById(R.id.btnBack).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Ferme l’activité actuelle et retourne à l’écran précédent
                finish();
            }
        });

        // Récupération du bouton "Enregistrer" dans l’interface
        Button btnSave = findViewById(R.id.btnSave);

        // Association d’un écouteur de clic classique (sans lambda) pour déclencher la sauvegarde
        btnSave.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // Appel de la méthode responsable de la création de l’intervention
                saveIntervention();
            }
        });
    }

    private void saveIntervention() {
        try {

            String titre = getInputText(R.id.editTitre);
            // Extraction du texte du champ "Titre"

            String date = getInputText(R.id.editDate);
            // Extraction de la date saisie

            String heure = getInputText(R.id.editHeure);
            // Extraction de l’heure saisie

            int duree = Integer.parseInt(getInputText(R.id.editDuree));
            // Conversion du champ durée en entier (peut générer une exception si invalide)

            // Bloc de récupération des champs saisies
            String priorite = getInputText(R.id.editPriorite);
            String techNom = getInputText(R.id.editTechNom);
            String techTel = getInputText(R.id.editTechTel);
            String siteNom = getInputText(R.id.editSiteNom);
            String siteAdr = getInputText(R.id.editSiteAdr);
            String siteContact = getInputText(R.id.editSiteContact);
            String siteTel = getInputText(R.id.editSiteTel);
            String desc = getInputText(R.id.editDesc);
            String mat = getInputText(R.id.editMateriel);

            int newId = DataRepository.interventions.size() + 1;
            // Génération d’un identifiant simple basé sur la taille actuelle de la liste.

            Technicien t = new Technicien(newId, techNom, techTel);
            // Création de l’objet représentant le technicien associé

            Site s = new Site(newId, siteNom, siteAdr, siteContact, siteTel);
            // Création de l’objet représentant le site d’intervention

            Intervention i = new Intervention(
                    newId, date, titre, heure, duree,
                    "Planifiée", priorite, desc, mat, t, s
            );
            // Instanciation complète de l’intervention avec tous les paramètres nécessaires

            DataRepository.addIntervention(this, i);
            // Ajout de l’intervention dans le dépôt de données centralisé

            Toast.makeText(this, "Intervention créée !", Toast.LENGTH_SHORT).show();
            // Message visuel confirmant la création

            finish(); // fermeture de l'activité quand enregistrement

        } catch (Exception e) {
            Toast.makeText(this, "Erreur : Vérifiez les champs", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String getInputText(int id) {
        // Méthode utilitaire permettant de récupérer le texte d’un champ EditText via son ID.
        return ((EditText) findViewById(id)).getText().toString();
        // Conversion du contenu en chaîne de caractères.
    }
}