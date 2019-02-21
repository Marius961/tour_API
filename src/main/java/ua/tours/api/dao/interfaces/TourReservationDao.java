package ua.tours.api.dao.interfaces;

import ua.tours.api.models.TourReservation;

import java.util.List;

public interface TourReservationDao {

    public TourReservation getReservationById(int id);
    public List<TourReservation> listReservationsByUserId(int userId);
    public int createReservation(TourReservation reservation);
    public void updateReservation(TourReservation reservation);
}
