package br.com.herison.ecommercehm.customer.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "adrresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @Column(name = "public_place", length = 100)
    private String publicPlace;

    @Column(name = "number", length = 10)
    private String number;

    @Column(name = "complement", length = 100)
    private String complement;

    @Column(name = "zipcode", length = 20)
    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
