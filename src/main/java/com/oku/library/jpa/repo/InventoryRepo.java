package com.oku.library.jpa.repo;

import com.oku.library.jpa.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {


    @Query("select i from Inventory i where i.isbn = :isbn")
    Optional<Inventory> findByIsbn(@Param("isbn")Long isbn);
}
