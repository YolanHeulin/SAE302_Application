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
        holder.tvTitle.setText(item.titre);
        holder.tvTime.setText("📅 " + item.date + " à " + item.heure);
        holder.tvSite.setText(item.site.nom);
        holder.tvStatus.setText(item.statut);

        int color = Color.GREEN;
        if ("Haute".equals(item.priorite)) color = Color.RED;
        else if ("Moyenne".equals(item.priorite)) color = Color.rgb(255, 165, 0); // Orange

        holder.viewPriority.setBackgroundColor(color);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }
    @Override
    public int getItemCount() { return list != null ? list.size() : 0; }
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