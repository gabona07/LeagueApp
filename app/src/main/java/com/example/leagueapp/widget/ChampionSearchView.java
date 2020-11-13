package com.example.leagueapp.widget;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;

import com.example.leagueapp.R;

public class ChampionSearchView extends SearchView {

    public ChampionSearchView(@NonNull Context context) {
        super(context);
        setIconifiedByDefault(true);
        setImeOptions(EditorInfo.IME_ACTION_DONE);
        setMaxWidth(Integer.MAX_VALUE);
        View searchPlate = findViewById(androidx.appcompat.R.id.search_plate);
        searchPlate.setBackgroundResource(R.drawable.search_plate_bg);
    }

    @Override
    public void onActionViewCollapsed() {
        clearFocus();
        super.onActionViewCollapsed();
    }
}
