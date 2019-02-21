package ua.tours.api.dao.interfaces;

import ua.tours.api.models.Tour;

import java.util.List;

public interface TourDao {

    public Tour getTour(int id);
    public void createTour(Tour tour);
    public void updateTour(Tour tour);
    public void removeTour(int tourId);
}
