package com.mmrath.sapp.domain.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "t_credential")
public class Credential implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "credentialIdGen", table = "SEQUENCE_TABLE", pkColumnName = "SEQ_NAME",
            valueColumnName = "SEQ_VALUE", pkColumnValue = "CREDENTIAL_ID_SEQ", initialValue = 101,
            allocationSize = 10)
    private Long id;

    @JsonIgnore
    private String salt;

    @JsonIgnore
    private String password;

    @Column(name = "expiry_date", nullable = false)
    private ZonedDateTime expiryDate;

    @Column(name = "invalid_attempts", nullable = false)
    private int invalidAttempts;

    @Column(name = "locked", nullable = false)
    private Boolean locked;

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

    @Override
    public String toString() {
        return "Credential{" +
                "expiryDate=" + expiryDate +
                ", invalidAttempts=" + invalidAttempts +
                ", locked=" + locked +
                '}';
    }
}
