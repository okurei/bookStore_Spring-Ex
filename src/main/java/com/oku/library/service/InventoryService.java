package com.oku.library.service;

import com.oku.library.controller.dto.InventoryDto;
import com.oku.library.jpa.entity.Inventory;
import com.oku.library.jpa.repo.InventoryRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    @Autowired BookService bookService;


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

    private Inventory checkBookInInventory(InventoryDto inventoryDto){
        Optional<Inventory> inventoryOptional = inventoryRepo.findByIsbn(inventoryDto.getIsbn());
        if (inventoryOptional.isEmpty()){
            return new Inventory(inventoryDto);
        } else{
            inventoryOptional.get().addStock();
            return inventoryOptional.get();
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

    public Inventory findBookInInventory(Long isbn) {
        Optional<Inventory> inventoryOptional = inventoryRepo.findByIsbn(isbn);
        return inventoryOptional.orElseThrow(()-> new RuntimeException("Not present"));
    }

    public ResponseEntity<List<Inventory>> populateInventory() {
        List<Long> isbn = bookService.getAllIsbn();
        List<InventoryDto> inventoryDtoList = isbn.stream().map(InventoryDto::new).toList();
        List<Inventory> inventoryList = inventoryDtoList.stream().map(this::checkBookInInventory).toList();
        inventoryRepo.saveAll(inventoryList);
        return ResponseEntity.ok(inventoryList);
    }
}
