package ua.tour.api.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3,message = "Hotel name must be longer than 2 characters")
    private String name;

    @Size(min = 32, max = 2048, message = "Hotel description must be longer than 31 but less than 2049")
    private String description;

    @Size(min = 2)
    private String country;

    @Size(min = 2)
    private String location;

    @Column(updatable = false)
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
