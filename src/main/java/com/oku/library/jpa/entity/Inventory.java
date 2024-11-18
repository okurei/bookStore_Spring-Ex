//package com.oku.library.jpa.entity;
//
//
//import jakarta.persistence.*;
//
//import lombok.*;
//
//import java.math.BigDecimal;
//
//@Entity
//@Table
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class Inventory {
//    @Id
//    @SequenceGenerator(name = "inventory_id", sequenceName = "inventory_id", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id")
//    private Long inventoryId;
//
//    @Column(precision = 10, scale = 2)
//    private BigDecimal price;
//
//    @Column
//    private Long stock;
//
//    @OneToOne(targetEntity = Book.class)
//    @JoinColumn(referencedColumnName = "isbn", unique = true)
//    private Book book;
//
//}
