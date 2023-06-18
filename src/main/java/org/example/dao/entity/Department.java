package org.example.dao.entity;


import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(name = "department_unique_name_constraint",columnNames = {"name"})
)
public class Department {


    private static final long serialVersionUID = 1L;


    @CreationTimestamp
    @Column(name = "create_stamp",columnDefinition = "")
    private LocalDateTime dateTimeCreated;


    @Version
    @UpdateTimestamp
    @Column(name = "update_stamp")
    private LocalDateTime dateTimeUpdated;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JoinTable(
            name = "department_relations",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")

    )
    @ManyToOne
    private Department parent;

    private String phone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "location_department_fk"))
    private Location location;

    @Column(name = "active", nullable = false)
    @ColumnDefault("true")
    private Boolean isActive = true;


    public Department() {
    }

    public Department(String name, Department parent, String phone, Location location) {
        this.name = name;
        this.parent = parent;
        this.phone = phone;
        this.location = location;
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

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public LocalDateTime getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(LocalDateTime dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(parent, that.parent) && Objects.equals(phone, that.phone) && Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parent, phone, location);
    }
}
