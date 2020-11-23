package com.example.leagueapp.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.leagueapp.R;
import com.example.leagueapp.model.DetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class SpellsAdapter extends RecyclerView.Adapter<SpellsAdapter.ViewHolder> {

    private List<DetailsResponse.Detail.Spell> spells = new ArrayList<>();
    private RequestManager requestManager;

    public SpellsAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView spellIcon;
        final TextView spellName;
        final TextView spellDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spellIcon = itemView.findViewById(R.id.spellIcon);
            spellName = itemView.findViewById(R.id.spellName);
            spellDescription = itemView.findViewById(R.id.spellDescription);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spell_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailsResponse.Detail.Spell currentSpell = spells.get(position);
        requestManager.load(currentSpell.image.getSpellIcon()).into(holder.spellIcon);
        holder.spellName.setText(currentSpell.name);
        holder.spellDescription.setText(Html.fromHtml(currentSpell.description));
    }

    @Override
    public int getItemCount() {
        return spells.size();
    }

    public void setSpells(List<DetailsResponse.Detail.Spell> spells) {
        this.spells = spells;
        notifyItemRangeChanged(0, getItemCount());
    }
}
