package com.example.sae302_application;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class InterventionAdapter extends RecyclerView.Adapter<InterventionAdapter.ViewHolder> {
    private List<Intervention> list;
    private final OnItemClickListener listener;
    public interface OnItemClickListener { void onItemClick(Intervention item); }
    public InterventionAdapter(List<Intervention> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }
    public void updateList(List<Intervention> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Méthode appelée lorsque le RecyclerView a besoin de créer un nouvel item visuel

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intervention, parent, false);
        // Inflation du layout XML pour transformer le fichier item_intervention.xml en une vue exploitable

        return new ViewHolder(view); // Retourne un nouveau ViewHolder contenant la vue créée
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Méthode appelée pour associer les données d’une intervention à un item affiché à l’écran

        Intervention item = list.get(position);
        // Récupération de l’intervention correspondant à la position actuelle dans la liste

        holder.tvTitle.setText(item.titre); // Affichage du titre de l’intervention
        holder.tvTime.setText("📅 " + item.date + " à " + item.heure); // Affichage de la date et de l’heure
        holder.tvSite.setText(item.site.nom); // Affichage du nom du site
        holder.tvStatus.setText(item.statut); // Affichage du statut de l’intervention

        int color = Color.GREEN; // Couleur par défaut pour une priorité basse
        if ("Haute".equals(item.priorite)) color = Color.RED; // Si la priorité est haute, couleur rouge
        else if ("Moyenne".equals(item.priorite)) color = Color.YELLOW; // Si la priorité est moyenne, couleur jaune

        holder.viewPriority.setBackgroundColor(color); // Application de la couleur sur la barre de priorité
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            // Ajout d’un écouteur de clic sur l’item entier avec une classe anonyme
            @Override
            public void onClick(View v) { // Méthode exécutée lorsque l’utilisateur clique sur l’item
                listener.onItemClick(item); // Appel du listener externe avec l’élément cliqué
            }
        });
    }
    @Override
    public int getItemCount() {
        // Retourne le nombre total d’éléments à afficher dans la liste
        return list != null ? list.size() : 0;
        // Si la liste est null, retourne 0 sinon retourne sa taille
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime, tvSite, tvStatus;
        View viewPriority;
        public ViewHolder(@NonNull View itemView) {
            super(itemView); // Appel du constructeur parent pour initialiser le ViewHolder
            // Récupération des zones de texte
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSite = itemView.findViewById(R.id.tvSite);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            viewPriority = itemView.findViewById(R.id.viewPriority);
        }
    }
}