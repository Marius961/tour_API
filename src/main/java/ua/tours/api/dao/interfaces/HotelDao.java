package ua.tours.api.dao.interfaces;

import ua.tours.api.models.Hotel;

import java.util.List;


public interface HotelDao {

    public Hotel getHotelById(int id);
    public List<Hotel> listHotels();
    public int insertHotel(Hotel hotel);
    public void updateHotel(Hotel hotel);
    public void removeHotel(int id);
}
