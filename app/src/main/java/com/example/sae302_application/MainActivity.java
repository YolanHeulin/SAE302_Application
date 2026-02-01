package com.example.sae302_application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;
import java.util.*;

public class MainActivity extends AppCompatActivity
        implements InterventionAdapter.OnItemClickListener {
    private InterventionAdapter adapter;
    private TextView tvStats;
    private String currentFilter = "Toutes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Appel de la méthode parent pour initialiser correctement l’activité Android

        setContentView(R.layout.activity_main); // Charge le layout XML associé à l’écran principal
        DataRepository.loadFromCsv(this); // Charge les interventions depuis le fichier CSV si ce n’est pas déjà fait

        RecyclerView recyclerView = findViewById(R.id.recyclerView); // Récupère le RecyclerView affichant la liste des interventions
        tvStats = findViewById(R.id.tvStatsTotal); // Récupère la zone de texte affichant le nombre total d’interventions filtrées

        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Définit un LayoutManager vertical pour organiser les éléments du RecyclerView

        ArrayList<Intervention> listeInterventions = new ArrayList<Intervention>(); // Création d'un ArrayList d'interventions vide
        adapter = new InterventionAdapter(listeInterventions, this); // Création de l'objet adapter, "this" représente MainActivity
        recyclerView.setAdapter(adapter); // Affectation de l'adapter au RecyclerView

        // Bouton 1
        // Récup le bouton avec l'ID btnAll dans le layout, ajoute un écouteur et crée une classe anonyme
        findViewById(R.id.btnAll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Méthode appelée automatiquement au moment du clic
                currentFilter = "Toutes"; // Change le filtre  du RecyclerView
                loadData(); // Relance le chargement des données
            }
        });
        // Bouton 2
        findViewById(R.id.btnPlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = "Planifiée";
                loadData();
            }
        });
        // Bouton 3
        findViewById(R.id.btnEnc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = "En cours";
                loadData();
            }
        });
        // Bouton 4
        findViewById(R.id.btnTerm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFilter = "Terminée";
                loadData();
            }
        });
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AjoutIntervention.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume(); // Méthode appelée à chaque retour sur l’activité (ex : après ajout d’une intervention)
        loadData();// Recharge les données pour mettre à jour la liste affichée
    }
    @Override
    public void onItemClick(Intervention item) { // Méthode déclenchée lorsqu’un élément du RecyclerView est cliqué
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);// Prépare la navigation vers l’écran de détails
        intent.putExtra("ID", item.id); // Ajoute l’ID de l’intervention sélectionnée pour l’afficher dans l’autre activité
        startActivity(intent); // Lance l’activité de détails
    }
    private void loadData() {// Méthode responsable du filtrage, tri et mise à jour de la liste affichée

        List<Intervention> all = DataRepository.interventions; // Récupère toutes les interventions chargées en mémoire
        List<Intervention> filtered = new ArrayList<Intervention>(); // Liste temporaire contenant uniquement les interventions correspondant au filtre actif

        for (Intervention i : all) { // Parcourt toutes les interventions

            if (currentFilter.equals("Toutes") || i.statut.equals(currentFilter)) {
                filtered.add(i);
                // Ajoute l’intervention si elle correspond au filtre sélectionné
            }
        }

        // Tri par priorité
        Collections.sort(filtered, new Comparator<Intervention>() {
            @Override
            public int compare(Intervention o1, Intervention o2) {
                // Compare les valeurs de priorité pour trier du plus urgent au moins urgent
                return Integer.compare(
                        o2.getPrioriteValue(),
                        o1.getPrioriteValue()
                );
            }
        });

        if (adapter != null) {
            adapter.updateList(filtered);
            // Met à jour l’adapter avec la liste filtrée et triée
        }

        if (tvStats != null) {
            tvStats.setText(filtered.size() + " intervention(s)");
            // Affiche le nombre d’interventions correspondant au filtre
        }
    }
}