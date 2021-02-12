package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue; //added

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    private FavoritesNeighboursApiService favoriteService;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        favoriteService = DI.getFavoriteNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void createNeighbourWithSuccess() {
        List<Neighbour> neighbourList= service.getNeighbours();
        Neighbour newNeighbour = new Neighbour(2020,"Mathieu", "https://i.pravatar.cc/150?u=a042581f4e29026704d","Saint-Pierre-du-Mont ; 5km","+33 6 86 57 90 14","Coucou");
        service.createNeighbour(newNeighbour);
        assertTrue(service.getNeighbours().contains(newNeighbour));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoritesNeighboursWithSucces() {
        List<Neighbour> favoritesNeighbours = favoriteService.getFavoritesNeighbours();
        List<Neighbour> expectedFavoritesNeighbours = DummyNeighbourGenerator.DUMMY_FAVORITES_NEIGHBOURS;
        assertThat(favoritesNeighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedFavoritesNeighbours.toArray()));
    }
    @Test
    public void addNeighbourToFavorite() {
        Neighbour neighbourToAdd = service.getNeighbours().get(0);
        favoriteService.createFavoriteNeighbour(neighbourToAdd);
        assertTrue(favoriteService.getFavoritesNeighbours().contains(neighbourToAdd));
    }

    @Test
    public void removeNeighbourFromFavorites() {
        List<Neighbour> neighbourList = service.getNeighbours();
        Neighbour neighbourToRemove = service.getNeighbours().get(0);
        favoriteService.deleteFavoriteNeighbour(neighbourToRemove);
        assertFalse(favoriteService.getFavoritesNeighbours().contains(neighbourToRemove));
    }
}
