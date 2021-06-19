package com.example.breakingbadv2.services;

import android.os.AsyncTask;
import android.util.Log;


import com.example.breakingbadv2.MainActivity;
import com.example.breakingbadv2.domain.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CharacterAPITask extends AsyncTask<String, Void, List<Character>> {
    private final String TAG = this.getClass().getSimpleName();
    private static final String JSON_NAME = "name";
    private static final String JSON_NICK = "nickname";
    private static final String JSON_STATUS = "status";
    private static final String JSON_BIRTHDAY = "birthday";
    private static final String JSON_JOBS = "occupation";
    private static final String JSON_SEASONS = "appearance";
    private static final String JSON_IMAGE = "img";
    private CharacterListener listener;

    public CharacterAPITask(MainActivity mainActivity) {
        listener = mainActivity;
    }

    public interface CharacterListener {
        void onAvailableCharacters(List<Character> characters);
    }

    /**
     * An async task that fetches JSON data from an api and runs on different thread.
     * @param strings
     * @return - a list of Character objects
     */
    @Override
    protected List<Character> doInBackground(String... strings) {
        Log.d(TAG, "called doInBackground from AsyncTask");
        String characterUrl = strings[0];

        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(characterUrl);
            connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String response = scanner.next();
                Log.d("From CharacterAPITask", response);
                connection.disconnect();
                return convertJSONToArrayList(response);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Character> characters) {
        Log.d(TAG, "called onPostExecute from AsyncTask");
        super.onPostExecute(characters);
        listener.onAvailableCharacters(characters);
    }

    /**
     * Loops through JSON objects and converts them to a list.
     * @param response - the JSON response as a String
     * @return - an ArrayList of Characters
     */
    public ArrayList<Character> convertJSONToArrayList(String response) {
        Log.d(TAG, "called getArrayListFromJSON");
        ArrayList<Character> returnList = new ArrayList<>();
        try {
            JSONArray characters = new JSONArray(response);
            for (int i = 0; i < characters.length(); i++) {
                JSONObject characterJSON = characters.getJSONObject(i);

                String name = characterJSON.getString(JSON_NAME);
                String nick = characterJSON.getString(JSON_NICK);
                String status = characterJSON.getString(JSON_STATUS);
                String birthDay = characterJSON.getString(JSON_BIRTHDAY);

                JSONArray jobArray = characterJSON.getJSONArray(JSON_JOBS);
                String jobs = getJsonArrayAsString(jobArray);

                JSONArray seasonArray = characterJSON.getJSONArray(JSON_SEASONS);
                String Seasons = getJsonArrayAsString(seasonArray);

                String image = characterJSON.getString(JSON_IMAGE);
                Character character = new Character(name, nick, status, birthDay, jobs, Seasons, image);
                returnList.add(character);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "returning character list");
        return returnList;
    }
    /**
     * Used for JSON objects that are an array list, they get converted to a single String object.
     * @param array - the JSON array that's been passed
     * @return - a string of multiple JSONArray parts
     */
    private String getJsonArrayAsString(JSONArray array) throws JSONException {
        Log.d(TAG, "called getJsonArrayAsString");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < array.length(); i++) {
            stringBuilder.append(array.get(i) + ", ");
        }
        return stringBuilder.toString();

    }

}

