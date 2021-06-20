package com.example.breakingbadv2.utils;

import android.util.Log;

import com.example.breakingbadv2.domain.Character;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil {
    private static String TAG = SearchUtil.class.getSimpleName();

    /**
     * Loops through a character list and filters them by name.
     * @param characters - the character list that will be filtered
     * @param filter - the string upon which the characters name must match
     * @return - list of characters with matching name from filter.
     */
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

    /**
     * Loops through a character list and filters them by status.
     * @param characters - the character list that will be filtered
     * @param filter - the string upon which the characters status must match
     * @return - list of characters with matching status from filter.
     */
    public static ArrayList<Character> filteredByStatus(List<Character> characters, String filter) {
        Log.d(TAG, "Called filteredByStatus");
        if(filter.isEmpty()) {
         return (ArrayList<Character>) characters;
        }

        ArrayList<Character> filterList = new ArrayList<>();
        for(Character c : characters) {
            if(c.getStatus().toLowerCase().equals(filter.toLowerCase())) {
                filterList.add(c);
            }
        }
        return filterList;
    }
}
