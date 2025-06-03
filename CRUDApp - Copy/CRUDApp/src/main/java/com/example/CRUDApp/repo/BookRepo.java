package com.example.CRUDApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import com.example.CRUDApp.model.Book;
import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long>{

    // @Lock(LockModeType.PESSIMISTIC_WRITE)
    // @Query("SELECT b FROM Book b WHERE b.id = :id")
    // Optional<Book> findByIdForUpdate(@Param("id") Long id);
    
}
