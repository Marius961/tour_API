package ua.tour.api.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

// анотація позначає що даний клас є сутністю і для нього буде створена таблиця в БД
@Entity
public class Hotel {

    // поле позначене анотацією @id є ключовим
    @Id
    // анотація вказує що поле є автогенерованим (кожного разу при додаванні нового запису в бд, буде генеруватись id)
    @GeneratedValue(strategy = GenerationType.AUTO)
    // ідентифікатор готелю
    private Long id;

    // спеціальна анотація для полів типу String, означає що дане поле не може бути пустить і не може складатись з пробілів
    @NotBlank
    // анотація @Column з параметром unique вказує що колонка є обов'язковою
    @Column(unique = true)
    // анотація @Size вказує обмеження по розміру колонки (мінімальний та максимальний розмір)
    @Size(min = 3, max = 256)
    // ім'я готелю
    private String name;

    @NotBlank
    @Size(min = 32, max = 2048)
    // опис готелю
    private String description;

    @NotBlank
    @Size(min = 2, max = 32)
    // країна, в якій знаходиться готель
    private String country;

    @NotBlank
    @Size(min = 2, max = 86)
    // місцеположення готелю (місто та ін. дані)
    private String location;

    // анотація @Column з параметром updatable визначає те, чи колонка підлягає для оновлення
    @Column(updatable = false)
    // назва фото готелю
    private String imageSrc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
}
