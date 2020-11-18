package com.example.leagueapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leagueapp.R;
import com.example.leagueapp.adapter.FavoriteAdapter;
import com.example.leagueapp.database.ChampionEntity;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private FavoriteAdapter adapter = new FavoriteAdapter();
    private ViewPager2 viewPager;

    public FavoriteFragment() {
        
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialFadeThrough enterTransition = new MaterialFadeThrough();
        long duration = getResources().getInteger(R.integer.reply_motion_duration);
        enterTransition.setDuration(duration);
        setEnterTransition(enterTransition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.favoriteViewpager);
        viewPagerInit();
    }


    private void viewPagerInit() {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer pageTransformer = new CompositePageTransformer();
        pageTransformer.addTransformer(new MarginPageTransformer(R.integer.viewpager_card_margin));
        pageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });
        viewPager.setPageTransformer(pageTransformer);
        List<ChampionEntity> championEntityList = new ArrayList<>();
        championEntityList.add(new ChampionEntity("Aatrox", 266L, "Aatrox", "the Darkin Blade", "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"));
        championEntityList.add(new ChampionEntity("Aatrox", 266L, "Aatrox", "the Darkin Blade", "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"));
        championEntityList.add(new ChampionEntity("Aatrox", 266L, "Aatrox", "the Darkin Blade", "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"));
        championEntityList.add(new ChampionEntity("Aatrox", 266L, "Aatrox", "the Darkin Blade", "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"));
        championEntityList.add(new ChampionEntity("Aatrox", 266L, "Aatrox", "the Darkin Blade", "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/Aatrox_0.jpg"));
        adapter.setChampions(championEntityList);
    }
}