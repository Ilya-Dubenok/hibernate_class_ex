package org.example.dao.entity;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(
       uniqueConstraints = @UniqueConstraint(name = "location_unique_name",columnNames = {"name"})
)
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @CreationTimestamp
    @Column(name = "create_stamp")
    private Instant dateTimeCreated;


    @UpdateTimestamp
    @Column(name = "update_stamp")
    private Instant dateTimeUpdated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;



    public Location() {
    }

    public Location(String name) {
        this.name = name;
    }

    public Location(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
