package br.com.herison.ecommercehm.customer.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "document", nullable = false, length = 11, unique = true)
    private String document;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "active")
    private boolean active;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.active = true;
    }
}
