package com.example.breakingbadv2.services;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuotesAPITask extends AsyncTask<String, Void, List<String>> {

    private final String TAG = getClass().getSimpleName();
    private final String JSON_QUOTE = "quote";
    private QuotesListener listener;
    public QuotesAPITask(QuotesListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<String> doInBackground(String... params) {
        Log.d(TAG, "called doInBackground");
        // build URL and fetch data
        String UrlString = params[0];
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(UrlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                String response = scanner.next();
                Log.d(TAG, "response: " + response);
                ArrayList<String> resultList = convertJSONToArrayList(response);
                return resultList;
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<String> quotes) {
        super.onPostExecute(quotes);
        Log.d(TAG, "In onPostExcecute: " + quotes.size() + " items.");
        listener.onQuotesAvailable(quotes);
    }

    public ArrayList<String> convertJSONToArrayList(String response) {
        ArrayList<String> quotesList = new ArrayList<>();
        try {
            JSONArray quotes = new JSONArray(response);
            for (int i = 0; i < quotes.length(); i++) {
                JSONObject quote = quotes.getJSONObject(i);
                quotesList.add(quote.getString(JSON_QUOTE));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Returning " + quotesList.size() + " items.");
        return quotesList;
    }

    public interface QuotesListener {
        void onQuotesAvailable(List<String> quotes);
    }
}
