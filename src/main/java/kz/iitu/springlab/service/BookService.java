package kz.iitu.springlab.service;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(String author) {
        if (author != null && !author.isBlank()) {
            return bookRepository.findAllByAuthor(author);
        }
        return bookRepository.findAll();
    }

    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    public boolean delete(long id) {
        return bookRepository.deleteById(id);
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public HashMap<String, Integer> stats() {
        HashMap<String, Integer> newMap = new HashMap<String, Integer>();
        int[] maxAndMinId = bookRepository.stats();
        newMap.put("Min", maxAndMinId[0]);
        newMap.put("Max", maxAndMinId[1]);
        return newMap;
    }
}
