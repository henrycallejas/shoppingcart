package com.shoppingcart.app.model;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "NAME", length = 200)
    // @NotBlank
    private String name;

    @Column(name = "DESCRIPTION", length = 500, columnDefinition = "TEXT")
    private String description;

    @Column(name = "PRICE")
    // @NotBlank
    private BigDecimal price;

    @Column(name = "image", length = 200)
    private String image;

    @Column(name = "CATEGORY")
    private Integer category;
}
