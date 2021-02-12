package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


public class DummyFavoritesNeighboursApiService implements FavoritesNeighboursApiService {
    private List<Neighbour> favoritesNeighboursList = DummyNeighbourGenerator.generateFavoritesNeighbours();

    @Override
    public List<Neighbour> getFavoritesNeighbours() {
        return favoritesNeighboursList;
    }

    @Override
    public void deleteFavoriteNeighbour(Neighbour neighbour) {
        favoritesNeighboursList.remove(neighbour);
    }

    @Override
    public void createFavoriteNeighbour(Neighbour neighbour) {
        favoritesNeighboursList.add(neighbour);
    }
}