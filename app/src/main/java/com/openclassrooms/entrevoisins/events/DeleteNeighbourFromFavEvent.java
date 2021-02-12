package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;


public class DeleteNeighbourFromFavEvent {
    /**
     * Neighbour to add to favorites
     */
    private Neighbour Neighbour;

    /**
     * Constructor of POJO
     * @param pNeighbour
     */
    public DeleteNeighbourFromFavEvent(Neighbour pNeighbour) {
        this.Neighbour = pNeighbour;
    }

    /**
     * Get the neighbour to add
     * @return
     */
    public Neighbour getNeighbour() {
        return Neighbour;
    }
}
