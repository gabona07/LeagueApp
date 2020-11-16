package com.example.leagueapp.adapter;

import android.graphics.drawable.AnimatedVectorDrawable;
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
        setHasStableIds(true);
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
                    if (champion.name.toLowerCase().contains(filterPattern)) {
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
        final ImageView favButton;

        ViewHolder(@NonNull View itemView, OnChampClickListener onChampClickListener) {
            super(itemView);
            this.onChampClickListener = onChampClickListener;
            cardView = itemView.findViewById(R.id.championCard);
            championIcon = itemView.findViewById(R.id.championIcon);
            championName = itemView.findViewById(R.id.detailedChampionName);
            championTitle = itemView.findViewById(R.id.championTitle);
            favButton = itemView.findViewById(R.id.favoriteButton);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.favoriteButton).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.favoriteButton) {
                ChampionResponse.Champion champion = championList.get(getAdapterPosition());
                if (!champion.isFavorite) {
                    champion.isFavorite = true;
                    onChampClickListener.addToFavorites(champion);
                } else {
                    champion.isFavorite = false;
                    onChampClickListener.removeFromFavorites(champion);
                }
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) favButton.getDrawable();
                avd.start();
            } else {
                ChampionResponse.Champion champion = championList.get(getAdapterPosition());
                String championName = champion.name;
                onChampClickListener.onChampCardClick(cardView, champion, championName);
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
        holder.cardView.setTransitionName(currentChampion.id);
        if (currentChampion.image != null) {
            Glide.with(holder.championIcon)
                    .load(currentChampion.image.getIconUrl())
                    .into(holder.championIcon);
        }
        holder.championName.setText(currentChampion.name);
        holder.championTitle.setText(currentChampion.title);
        if (currentChampion.isFavorite) {
            holder.favButton.setImageResource(R.drawable.avd_favorite_on);
        } else {
            holder.favButton.setImageResource(R.drawable.avd_favorite_off);
        }
    }

    @Override
    public int getItemCount() {
        return championList.size();
    }

    @Override
    public long getItemId(int position) {
        return championList.get(position).key;
    }

    public void setChampionList(List<ChampionResponse.Champion> championList) {
        this.championList = championList;
        this.championListFull = new ArrayList<>(championList);
        notifyItemRangeChanged(0, getItemCount());
    }

    public boolean holdsChampions() {
        return !championListFull.isEmpty();
    }

    public void onDestroy() {
        this.onChampClickListener = null;
    }

    public interface OnChampClickListener {
        void onChampCardClick(CardView cardView, ChampionResponse.Champion champion, String championName);
        void addToFavorites(ChampionResponse.Champion champion);
        void removeFromFavorites(ChampionResponse.Champion champion);
    }
}
