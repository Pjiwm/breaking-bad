package com.example.breakingbadv2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.example.breakingbadv2.domain.Character;
import com.example.breakingbadv2.services.CharacterAPITask;
import com.example.breakingbadv2.utils.CharacterAdapter;
import com.example.breakingbadv2.utils.SearchUtil;

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
    private LocalDateTime time;

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

        /* Search filter */
        EditText editText = findViewById(R.id.search_bar);
        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<Character> filteredList = SearchUtil.filteredByName(characterList, s.toString());
                characterAdapter = new CharacterAdapter(filteredList, MainActivity.this);
                characterRecyclerView.setAdapter(characterAdapter);
                showCharacterToast(filteredList.size());
            }
        });
    }

    /**
     * Creates a menu at the top of the activity with pressable options.
     * @param menu - the menu items we can select
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_menu, menu);
        return true;
    }

    /**
     * Checks what menu item was pressed.
     * It filters the character list based on the selected status.
     * @param item - the selected item from the filter_menu
     * @return - selected item
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.alive:
                filterStatus("alive");
                return true;
            case R.id.deceased:
                filterStatus("deceased");
                return true;
            case R.id.presumed_dead:
                filterStatus("presumed dead");
                return true;
            case R.id.unknown:
                filterStatus("unknown");
                return true;
            case R.id.search_all:
                filterStatus("");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * stores given values, so they can be used even after apps state resets.
     *
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
     *
     * @param savedInstanceState - the instance that restores values
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "Called onRestoreInstanceState - found saved instance");
        characterList = savedInstanceState.getParcelableArrayList(CHARACTER_STATE);
        characterAdapter = new CharacterAdapter(characterList, MainActivity.this);
        characterRecyclerView.setAdapter(characterAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart() {
        Log.d(TAG, "Called onRestart");
        super.onRestart();
        String welcome = getString(R.string.welcome);
        try {
            long seconds = ChronoUnit.SECONDS.between(time, LocalDateTime.now());
            Toast.makeText(this, seconds + " " + welcome, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Toast.makeText(this, "welcome back", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * when all API data has been fetched they'l be set to characterList.
     *
     * @param characters - the list of characters that will show up
     */
    @Override
    public void onAvailableCharacters(List<Character> characters) {
        Log.d(TAG, "Called onAvailableCharacters.");
        this.characterList.clear();
        try {
            this.characterList.addAll(characters);
            showCharacterToast(characterList.size());
            this.characterAdapter = new CharacterAdapter(characterList, this);
            characterRecyclerView.setAdapter(characterAdapter);
            this.characterAdapter.notifyDataSetChanged();
        } catch (NullPointerException e) {
            Toast.makeText(this, "API offline", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Starts API task and gives params for API link
     */
    private void loadCharacterAPIData() {
        Log.d(TAG, "Called loadCharacterAPIData");
        String[] params = {"https://www.breakingbadapi.com/api/characters"};
        new CharacterAPITask(this).execute(params);
    }
    /**
     * shows a toast with how many characters have been found
     *
     * @param size - the amount that will be shown in the toast
     */
    private void showCharacterToast(int size) {
        Log.d(TAG, "Called showCharacterToast");
        String characterFound = getString(R.string.characters_found);
        Toast.makeText(this, size + " " + characterFound, Toast.LENGTH_LONG).show();
    }

    /**
     * sets new characterlist by filtering from character status
     * @param status - the status of the character you want to dispaly
     */
    private void filterStatus(String status) {
        Log.d(TAG, "Called filterStatus");
        ArrayList<Character> filteredList = SearchUtil.filteredByStatus(characterList, status);
        characterAdapter = new CharacterAdapter(filteredList, MainActivity.this);
        characterRecyclerView.setAdapter(characterAdapter);
        showCharacterToast(filteredList.size());
    }
}