package com.oku.library.service;

import com.oku.library.controller.dto.InventoryDto;
import com.oku.library.jpa.entity.Inventory;
import com.oku.library.jpa.repo.InventoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepo inventoryRepo;


    public ResponseEntity<Optional<Inventory>> addBookToInventory(Long isbn, InventoryDto inventoryDto) {
        Optional<Inventory> inventoryOptional = inventoryRepo.findByIsbn(isbn);
        if (inventoryOptional.isEmpty()){
            Inventory inventory = new Inventory(inventoryDto);
            inventoryRepo.save(inventory);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else{
            inventoryOptional.get().addStock();
            inventoryRepo.save(inventoryOptional.get());
            return ResponseEntity.ok(inventoryOptional);
        }
    }

    public ResponseEntity<Optional<Inventory>> removeBookFromInventory(Long isbn) {
        Optional<Inventory> inventoryOptional = inventoryRepo.findByIsbn(isbn);
        if(inventoryOptional.isEmpty() || inventoryOptional.get().getStock() < 1){
            return ResponseEntity.notFound().
                    header("Book not found", "Book not present in the inventory").
                    build();
        }else
        {
            inventoryOptional.get().removeStock();
            inventoryRepo.save(inventoryOptional.get());
            return new ResponseEntity<>(inventoryOptional, HttpStatus.ACCEPTED);
        }
    }
}
