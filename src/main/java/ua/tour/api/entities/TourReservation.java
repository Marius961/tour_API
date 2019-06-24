package ua.tour.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class TourReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // означає що один користувач може мати багато резервувань, FetchType.LAZY означає що з бд буде братись не весь об'єкт а лише основні дані, і тільки якщо буде потрібне якесь поле - дані будуть вибрані з бд
    @ManyToOne(fetch = FetchType.LAZY)
    // вказує що колонка, яка буде посилатись на запис з іншої таблиці буде називатись user_id
    @JoinColumn(name = "user_id")
    // означає що дане поле не буде перетворюватись у JSON при отриманні всіх резервувань з бд
    @JsonIgnore
    // Об'єкт класу User який відноситься до резервування
    private User user;

    // виводить лише id користувача у JSON
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @NotNull
    // FetchType.EAGER - при вибірці даних клас і його поля будуть вибрані повністю
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Size(max = 2048)
    private String comment;

    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
