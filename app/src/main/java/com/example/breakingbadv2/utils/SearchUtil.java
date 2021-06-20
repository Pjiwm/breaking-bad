package com.example.breakingbadv2.utils;

import com.example.breakingbadv2.domain.Character;

import java.util.ArrayList;

public class SearchUtil {

    public static ArrayList<Character> filteredByName(ArrayList<Character> characters, String filter) {
        ArrayList<Character> filterList = new ArrayList<>();
        for(Character c : characters) {
            if(c.getName().contains(filter)) {
                filterList.add(c);
            }
        }
        return filterList;
    }

    public static ArrayList<Character> filteredByStatus(ArrayList<Character> characters, String filter) {
        ArrayList<Character> filterList = new ArrayList<>();
        for(Character c : characters) {
            if(c.getStatus().toLowerCase().equals(filter.toLowerCase())) {
                filterList.add(c);
            }
        }
        return filterList;
    }
}
