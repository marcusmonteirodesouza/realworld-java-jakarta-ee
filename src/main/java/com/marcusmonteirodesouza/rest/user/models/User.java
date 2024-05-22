package com.marcusmonteirodesouza.rest.user.models;

import com.google.common.base.Optional;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String email;
    private String username;
    private String passwordHash;
    private Optional<String> bio;
    private Optional<String> image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Optional<String> getBio() {
        return bio;
    }

    public void setBio(Optional<String> bio) {
        this.bio = bio;
    }

    public Optional<String> getImage() {
        return image;
    }

    public void setImage(Optional<String> image) {
        this.image = image;
    }
}
