package com.example.breakingbadv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import com.example.breakingbadv2.domain.Character;
import com.example.breakingbadv2.services.CharacterAPITask;
import com.example.breakingbadv2.utils.CharacterAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CharacterAPITask.CharacterListener{
    private List<Character> characterList = new ArrayList<Character>();
    private RecyclerView characterRecyclerView;
    private CharacterAdapter characterAdapter;
    private final String TAG = this.getClass().getSimpleName();
    private static final String SAVED_INSTANCE_OF_ADAPTER = "saved adapter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Called onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        characterRecyclerView = findViewById(R.id.character_rv);
        int orientation = RecyclerView.VERTICAL;
        Context context = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, orientation, false);
        characterRecyclerView.setLayoutManager(layoutManager);
        this.characterAdapter = new CharacterAdapter(characterList, this);
        characterRecyclerView.setAdapter(characterAdapter);
        loadCharacterAPIData();
    }

    /**
     * when all API data has been fetched they'l be set to characterList.
     * @param characters - the list of characters that will show up
     */
    @Override
    public void onAvailableCharacters(List<Character> characters) {
        Log.d(TAG, "Called onAvailableCharacters.");
        this.characterList.clear();
        this.characterList.addAll(characters);
        this.characterAdapter.notifyDataSetChanged();
    }

    /**
     * Starts API task and gives params for API link
     */
    private void loadCharacterAPIData() {
        Log.d(TAG, "Called loadCharacterAPIData");
        String[] params = {"https://www.breakingbadapi.com/api/characters"};
        new CharacterAPITask(this).execute(params);
    }

}