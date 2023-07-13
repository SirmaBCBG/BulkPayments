package com.sirmabc.bulkpayments.persistance.entities;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Objects;

@Entity
@Table(name = "PROPERTIES", schema = "UBXIP", catalog = "")
public class PropertiesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private BigInteger id;
    @Basic
    @Column(name = "NAME")
    private String name;
    @Basic
    @Column(name = "VALUE")
    private String value;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertiesEntity that = (PropertiesEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(value, that.value) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, description);
    }
}
