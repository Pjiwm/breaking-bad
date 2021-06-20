package com.example.breakingbadv2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;


import com.example.breakingbadv2.domain.Character;
import com.example.breakingbadv2.services.CharacterAPITask;
import com.example.breakingbadv2.utils.CharacterAdapter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CharacterAPITask.CharacterListener {
    private static final String CHARACTER_STATE = "characters";
    private List<Character> characterList = new ArrayList<>();
    private RecyclerView characterRecyclerView;
    private CharacterAdapter characterAdapter;
    private final String TAG = this.getClass().getSimpleName();
    LocalDateTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        if (savedInstanceState != null) {
            Log.d(TAG, "Called onCreate - found saved instance");
            characterList = savedInstanceState.getParcelableArrayList(CHARACTER_STATE);
        } else {
            Log.d(TAG, "Called onCreate - no saved instance");
            loadCharacterAPIData();
        }

        this.characterAdapter = new CharacterAdapter(characterList, this);
        characterRecyclerView.setAdapter(characterAdapter);

    }

    /**
     * stores given values, so they can be used even after apps state resets.
     * @param outState - the states that are being stored
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "Called onSaveInstanceState");
        super.onSaveInstanceState(outState);
        LocalDateTime oldTime = LocalDateTime.now();
        outState.putParcelableArrayList(CHARACTER_STATE, (ArrayList<? extends Parcelable>) characterList);
        this.time = oldTime;
    }

    /**
     * used when activity is destroyed, this way API call doesn't have to be called.
     * @param savedInstanceState - the instance that restores values
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "Called onRestoreInstanceState - found saved instance");
        characterList = savedInstanceState.getParcelableArrayList(CHARACTER_STATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println(time + LocalDateTime.now().toString());
        try {
            long seconds = ChronoUnit.SECONDS.between(time, LocalDateTime.now());
            Toast.makeText(this, "Took " + seconds + " seconds", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "welcome back", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * when all API data has been fetched they'l be set to characterList.
     * @param characters - the list of characters that will show up
     */
    @Override
    public void onAvailableCharacters(List<Character> characters) {
        Log.d(TAG, "Called onAvailableCharacters.");
        this.characterList.clear();
        try {
            this.characterList.addAll(characters);
        } catch (NullPointerException e) {
            Toast.makeText(this, "API offline", Toast.LENGTH_SHORT).show();
        }
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