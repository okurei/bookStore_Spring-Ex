package com.oku.library.controller.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class InventoryDto {
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    @Digits(integer = 10,fraction = 2, message = "price must be a valid monetary amount")
    private BigDecimal price;
    @NotNull
    private Long isbn;

    Random random = new Random();

    public InventoryDto(Long isbnForeign){
        this.price = randomPrice();
        this.isbn = isbnForeign;
    }

    private BigDecimal randomPrice(){
        double rValue = random.nextDouble();
        double rScale = 10 + (rValue * 20);
        return BigDecimal.valueOf(rScale).setScale(2, RoundingMode.HALF_UP);
    }
}
