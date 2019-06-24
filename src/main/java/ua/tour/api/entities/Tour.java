package ua.tour.api.entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 32)
    // назва туру
    private String title;

    @NotBlank
    @Size(min = 32, max = 2048)
    // опис туру
    private String description;

    // вказує що дане поле, як і колонка в бд не можуть мати значення null
    @NotNull
    // анотація вказує що дане поле є датою, в ній можна вказати що зберігати (дату, час чи те і інше)
    @Temporal(TemporalType.TIMESTAMP)
    // дата початку туру
    private Date startDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    // дата закінчення туру
    private Date endDate;

    @NotNull
    // кількість місць у турі
    private Integer seatCount;

    // анотація  @OneToOne вказує що один запис в цій таблиці відноситься лише до одного запису у таблиці hotel
    // FetchType.EAGER - означає що при завантаженні з бд, будуть завантажені і всі вкладені об'єкти з інших таблиць
    @OneToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "hotel_id") - вказує що колонка яка буде створена в цій таблиці повинна мати задану назву
    @JoinColumn(name = "hotel_id")
    // готель, до якого належить тур
    private Hotel hotel;

    @Column(updatable = false)
    // назва фото туру
    private String imageSrc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = convertToJDate(startDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = convertToJDate(endDate);
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    private Date convertToJDate(Date date) {
        return new Date(date.getTime()*1000 );
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
