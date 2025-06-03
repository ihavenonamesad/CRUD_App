package com.example.CRUDApp.controller;

import com.example.CRUDApp.repo.BookRepo;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

import com.example.CRUDApp.model.Book;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/getallbooks")
    public ResponseEntity<List<Book>> getallbooks() {
        try {
            List<Book> booklist = new ArrayList<>();
            bookRepo.findAll().forEach(booklist::add);

            if (booklist.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(booklist, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findbookbyid/{id}")
    public ResponseEntity<Book> findbookbyid(@PathVariable Long id) {
        Optional<Book> bookobj = bookRepo.findById(id);
        if (bookobj.isPresent()) {
            return new ResponseEntity<>(bookobj.get(), HttpStatus.OK);
        } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addbook")
    public ResponseEntity<Book> addbook(@RequestBody Book book) {
        try {
            // System.out.println("Incoming book: " + book);
            // System.out.println("title: " + book.getTitle());
            // System.out.println("author: " + book.getAuthor());
            Book bookobj = bookRepo.save(book);

            List<Book> booklist = new ArrayList<>();
            Object bookid = book.getId();
            // bookRepo.findAll().forEach(booklist::add);
                for (long i = 1; i<= booklist.size(); i++) {
                    if (booklist.contains(bookid)) {
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    }
                }
            return new ResponseEntity<>(bookobj, HttpStatus.CREATED);
        } catch (Exception e) {
             e.printStackTrace();
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } 
    }

    @PostMapping("/updatebookbyid/{id}")
    public ResponseEntity<Book> updatebookbyid(@PathVariable Long id, @RequestBody Book book) {
        try {
            Optional<Book> oldbook = bookRepo.findById(id);
            if (oldbook.isPresent()) {
                Book updatedbook = oldbook.get();
                updatedbook.setTitle(book.getTitle());
                updatedbook.setAuthor(book.getAuthor());
                // updatedbook.setVersion(book.getVersion());

                

                Book bookobj = bookRepo.save(updatedbook);
                return new ResponseEntity<>(bookobj, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>( HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
 
    }

    // @PostMapping("/updatebookbyid/{id}")
    // @Transactional
    // public ResponseEntity<Book> updatebookbyid(@PathVariable Long id, @RequestBody Book book) {
    // try {
    //     Optional<Book> lockedBook = bookRepo.findByIdForUpdate(id); // Locks the row

    //     if (lockedBook.isPresent()) {
    //         Book updatedBook = lockedBook.get();

    //         // Optional: check version manually if you still want optimistic check too
    //         if (!updatedBook.getVersion().equals(book.getVersion())) {
    //             return new ResponseEntity<>(HttpStatus.CONFLICT); // Version mismatch
    //         }

    //         updatedBook.setTitle(book.getTitle());
    //         updatedBook.setAuthor(book.getAuthor());

    //         // version is managed by JPA, you usually don't set it manually
    //         // updatedBook.setVersion(book.getVersion()); // optional

    //         Book saved = bookRepo.save(updatedBook);
    //         return new ResponseEntity<>(saved, HttpStatus.OK);
    //     }

    //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    //     } catch (OptimisticLockException e) {
    //         return new ResponseEntity<>(HttpStatus.CONFLICT); // Another user updated it
    //     } catch (Exception e) {
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }


    @DeleteMapping("/deletebookbyid/{id}")
    public ResponseEntity<HttpStatus> deletebookbyid(@PathVariable Long id) {
        try {
            bookRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/deleteallbooks")
    public ResponseEntity<HttpStatus> deleteallbooks() {
        try {
            bookRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
