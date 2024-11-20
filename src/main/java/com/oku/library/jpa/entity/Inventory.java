package com.oku.library.jpa.entity;

import com.oku.library.controller.dto.InventoryDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @SequenceGenerator(name = "inventory_id", sequenceName = "inventory_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id")
    private Long inventoryId;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private Long stock;

    @Column
    private Long isbn;


    public Inventory(InventoryDto inventoryDto){
        this.price = inventoryDto.getPrice();
        this.stock = 1L;
        this.isbn = inventoryDto.getIsbn();

    }
    public void addStock(){this.stock++;}
    public void removeStock(){this.stock--;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inventory inventory = (Inventory) o;
        return Objects.equals(inventoryId, inventory.inventoryId) && Objects.equals(price, inventory.price) && Objects.equals(stock, inventory.stock) && Objects.equals(isbn, inventory.isbn);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(inventoryId);
        result = 31 * result + Objects.hashCode(price);
        result = 31 * result + Objects.hashCode(stock);
        result = 31 * result + Objects.hashCode(isbn);
        return result;
    }
}
