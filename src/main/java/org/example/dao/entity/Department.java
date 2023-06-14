package org.example.dao.entity;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Department {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    public Department() {
    }

    public Department(String name, Department parent, String phone, Location location) {
        this.name = name;
        this.parent = parent;
        this.phone = phone;
        this.location = location;
    }

    @JoinTable(
            name = "department_relations",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")

    )
    @ManyToOne
    private Department parent;

    private String phone;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Location location;


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
