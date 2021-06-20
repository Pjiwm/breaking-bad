package com.example.breakingbadv2.utils;

import android.util.Log;

import com.example.breakingbadv2.domain.Character;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil {
    private static String TAG = SearchUtil.class.getSimpleName();

    public static ArrayList<Character> filteredByName(List<Character> characters, String filter) {
        Log.d(TAG, "Called filteredByName");
        ArrayList<Character> filterList = new ArrayList<>();
        for(Character c : characters) {
            if(c.getName().contains(filter)) {
                filterList.add(c);
            }
        }
        return filterList;
    }

    public static ArrayList<Character> filteredByStatus(ArrayList<Character> characters, String filter) {
        Log.d(TAG, "Called filteredByStatus");
        ArrayList<Character> filterList = new ArrayList<>();
        for(Character c : characters) {
            if(c.getStatus().toLowerCase().equals(filter.toLowerCase())) {
                filterList.add(c);
            }
        }
        return filterList;
    }
}
