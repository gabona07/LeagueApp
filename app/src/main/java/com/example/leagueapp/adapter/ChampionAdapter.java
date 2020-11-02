package com.example.leagueapp.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.leagueapp.R;
import com.example.leagueapp.model.ChampionResponse;

import java.util.ArrayList;
import java.util.List;


public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.ViewHolder> implements Filterable {

    private OnChampClickListener onChampClickListener;
    private List<ChampionResponse.Champion> championList = new ArrayList<>();
    private List<ChampionResponse.Champion> championListFull = new ArrayList<>(championList);

    public ChampionAdapter(OnChampClickListener onChampClickListener) {
        this.onChampClickListener = onChampClickListener;
    }

    @Override
    public Filter getFilter() {
        return championFilter;
    }

    private Filter championFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ChampionResponse.Champion> filteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(championListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (ChampionResponse.Champion champion : championListFull) {
                    if (champion.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(champion);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            championList.clear();
            championList.addAll((List<ChampionResponse.Champion>) filterResults.values);
            notifyDataSetChanged();
        }
    }; 

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnChampClickListener onChampClickListener;
        final CardView cardView;
        final ImageView championIcon;
        final TextView championName;
        final TextView championTitle;


        ViewHolder(@NonNull View itemView, OnChampClickListener onChampClickListener) {
            super(itemView);
            this.onChampClickListener = onChampClickListener;
            cardView = itemView.findViewById(R.id.championCard);
            championIcon = itemView.findViewById(R.id.championIcon);
            championName = itemView.findViewById(R.id.championName);
            championTitle = itemView.findViewById(R.id.championTitle);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.favoriteButton).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.favoriteButton) {
                ChampionResponse.Champion champion = championList.get(getAdapterPosition());
                onChampClickListener.addToFavorite(champion);
            } else {
                String championId = cardView.getTransitionName();
                onChampClickListener.onChampClick(cardView, championId);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.champion_item, parent, false);
        return new ViewHolder(itemView, onChampClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChampionResponse.Champion currentChampion = championList.get(position);
        holder.cardView.setTransitionName(currentChampion.getId());
        Glide.with(holder.championIcon)
                .load(currentChampion.getImage().getIconUrl())
                .into(holder.championIcon);
        holder.championName.setText(currentChampion.getName());
        holder.championTitle.setText(currentChampion.getTitle());
    }

    @Override
    public int getItemCount() {
        return championList.size();
    }

    public void setChampionList(List<ChampionResponse.Champion> championList) {
        this.championList = championList;
        this.championListFull = new ArrayList<>(championList);
        notifyItemRangeChanged(0, championList.size());
    }

    public boolean holdsChampions() {
        return !championListFull.isEmpty();
    }

    public void onDestroy() {
        this.onChampClickListener = null;
    }

    public interface OnChampClickListener {
        void onChampClick(CardView cardView, String championName);
        void addToFavorite(ChampionResponse.Champion champion);
    }
}
