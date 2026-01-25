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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_intervention, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Intervention item = list.get(position);

        // 1. Remplissage des textes de base
        holder.tvTitle.setText(item.titre);
        holder.tvTime.setText(item.heure);

        // Note: Ces lignes planteront tant que ton binôme n'a pas créé Site/Technicien
        // Décommente-les quand tu auras fusionné ton code !
        // holder.tvSite.setText(item.site.nom);

        holder.tvStatus.setText(item.statut);

        // --- 2. GESTION DES COULEURS DU STATUT (NOUVEAU) ---
        if ("Terminée".equals(item.statut)) {
            // Vert : Fond clair / Texte foncé
            holder.tvStatus.setBackgroundColor(Color.parseColor("#E8F5E9")); // Vert très pâle
            holder.tvStatus.setTextColor(Color.parseColor("#2E7D32"));     // Vert forêt
        }
        else if ("En cours".equals(item.statut)) {
            // Orange : Fond clair / Texte foncé
            holder.tvStatus.setBackgroundColor(Color.parseColor("#FFF3E0")); // Orange très pâle
            holder.tvStatus.setTextColor(Color.parseColor("#EF6C00"));     // Orange vif
        }
        else {
            // Planifiée (Défaut) : Bleu
            holder.tvStatus.setBackgroundColor(Color.parseColor("#E3F2FD")); // Bleu très pâle
            holder.tvStatus.setTextColor(Color.parseColor("#1565C0"));     // Bleu roi
        }

        // --- 3. GESTION DE LA BARRE DE PRIORITÉ (A GAUCHE) ---
        int priorityColor = Color.GREEN; // Basse par défaut
        if ("Haute".equals(item.priorite)) {
            priorityColor = Color.RED;
        } else if ("Moyenne".equals(item.priorite)) {
            priorityColor = Color.parseColor("#FFA726"); // Orange
        }
        holder.viewPriority.setBackgroundColor(priorityColor);

        // Clic sur l'élément
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime, tvSite, tvStatus;
        View viewPriority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSite = itemView.findViewById(R.id.tvSite);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            viewPriority = itemView.findViewById(R.id.viewPriority);
        }
    }
}