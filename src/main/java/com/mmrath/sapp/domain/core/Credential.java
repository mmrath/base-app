package com.mmrath.sapp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "t_credential")
public class Credential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @JsonIgnore
    @JoinColumn(name = "id")
    @OneToOne
    @MapsId
    private User user;

    @JsonIgnore
    private String salt;

    @JsonIgnore
    private String password;

    @Column(name = "expiry_date", nullable = true)
    private ZonedDateTime expiryDate;

    @Column(name = "invalid_attempts", nullable = false)
    private int invalidAttempts;

    @Column(name = "locked", nullable = false)
    private Boolean locked;

    @Size(max = 64)
    @Column(name = "activation_key", length = 64)
    @JsonIgnore
    private String activationKey;

    @NotNull
    @Column(nullable = false)
    private Boolean activated = false;

    @Size(max = 64)
    @Column(name = "reset_key", length = 64)
    private String resetKey;

    @Column(name = "reset_date", nullable = true)
    private ZonedDateTime resetDate = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getInvalidAttempts() {
        return invalidAttempts;
    }

    public void setInvalidAttempts(int invalidAttempts) {
        this.invalidAttempts = invalidAttempts;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getLocked() {
        return locked;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public ZonedDateTime getResetDate() {
        return resetDate;
    }

    public void setResetDate(ZonedDateTime resetDate) {
        this.resetDate = resetDate;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id=" + id +
                ", salt='" + salt + '\'' +
                ", password='" + password + '\'' +
                ", expiryDate=" + expiryDate +
                ", invalidAttempts=" + invalidAttempts +
                ", locked=" + locked +
                ", activationKey='" + activationKey + '\'' +
                ", activated=" + activated +
                ", resetKey='" + resetKey + '\'' +
                ", resetDate=" + resetDate +
                '}';
    }
}
