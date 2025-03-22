package com.example.stars.service;

import com.example.stars.beans.Star;
import com.example.stars.dao.Idao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StarService implements Idao<Star> {
    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        this.stars = new ArrayList<>();
    }

    public static StarService getInstance() {
        if(instance == null)
            instance =  new StarService();
        return instance;
    }
    public boolean create(Star o) {
        return stars.add(o);
    }

    @Override
    public boolean update(Star o) {
        for(Star s : stars){
            if(s.getStar() == o.getId()){
                s.setImg(o.getImg());
                s.setName(o.getName());
                s.setStar(o.getStar());
            }
        }
        return true;
    }
    public boolean delete(Star o) {
        return stars.remove(o);
    }
    @Override
    public Star findById(int id) {
        for(Star s : stars){
            if(s.getId() == id)
                return s;
        }
        return null;
    }
    @Override
    public List<Star> findAll() {
        return stars;
    }
    public List<Star> performSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return findAll(); // Return all stars if the query is empty
        }
        return filterResults(query);
    }


    private List<Star> filterResults(String query) {
        List<Star> filteredStars = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase(Locale.ROOT);

        for (Star star : stars) {
            // Check if the star's name contains the query (case-insensitive)
            if (star.getName().toLowerCase(Locale.ROOT).contains(lowerCaseQuery)) {
                filteredStars.add(star);
            }
        }
        return filteredStars;
    }

}
