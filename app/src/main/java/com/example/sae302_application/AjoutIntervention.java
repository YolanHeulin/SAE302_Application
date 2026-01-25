package com.example.sae302_application;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AjoutIntervention extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intervention);

        // Bouton de retour dans le menu AjoutIntervention
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> saveIntervention());
    }

    private void saveIntervention() {
        try {
            String titre = getInputText(R.id.editTitre);
            String date = getInputText(R.id.editDate);
            String heure = getInputText(R.id.editHeure);
            int duree = Integer.parseInt(getInputText(R.id.editDuree));
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

            Technicien t = new Technicien(newId, techNom, techTel);
            Site s = new Site(newId, siteNom, siteAdr, siteContact, siteTel);

            Intervention i = new Intervention(newId, date, titre, heure, duree, "Planifiée", priorite, desc, mat, t, s);

            DataRepository.addIntervention(this, i);

            Toast.makeText(this, "Intervention créée !", Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Erreur : Vérifiez les champs", Toast.LENGTH_LONG).show();
            e.printStackTrace(); // Utile pour voir l'erreur exacte dans le Logcat
        }
    }

    private String getInputText(int id) {
        return ((EditText) findViewById(id)).getText().toString();
    }
}