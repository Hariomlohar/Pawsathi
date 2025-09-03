package com.PawSathi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double price;
    private double offer;
    private int stock;
    private String ageSuitability;
    private String description;

    // Specifications
    private String material;
    private String color;
    private String weight;
    private String length;

    private String imageName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;   // Associated category
}
