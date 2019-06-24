package ua.tour.api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true)
    @Size(min= 3,max = 32)
    private String username;

    @NotBlank
    @Column(unique = true)
    @Size(max = 64)
    // анотація яка автоматично валідує емейл (запис в бд не може бути доданим поки дане поле не буде містити емейл)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 512)
    // означає що даний параметр може бути лише отриманим в JSON, але при цьому при перетворенні об'єкта в JSON дане поле не буде враховано
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    @Size(min = 3, max = 64)
    private String fullName;

    @NotBlank
    @Size(min = 10, max = 10)
    // вказує що дане поле типу String може містити лише 10 символів (10 цифр мобільного)
    @Digits(integer=10, fraction=0)
    private String mobileNumber;

    @NotNull
    // поле не додається в JSON
    @JsonIgnore
    // columnDefinition = "INTEGER" - вказує примусовий тип колонки
    @Column(columnDefinition = "INTEGER")
    private boolean active;

    // анотація вказує що даний список є колекцією класу Role
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    // означає що під дану колекцію має бути створена таблиця з іменем user_role а назва колонки в таблиці User повинна мати назву user_id
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    // вказує що даний набір даних є перечисленням з типом String
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }
}
