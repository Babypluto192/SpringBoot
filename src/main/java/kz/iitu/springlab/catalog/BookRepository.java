package kz.iitu.springlab.catalog;

import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;



@Repository
public class BookRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();
    public BookRepository() {
       save(new Book(null, "Effective Java", "Joshua Bloch", 2018));
       save(new Book(null, "Clean Code", "Robert Martin", 2008));
       save(new Book(null, "Spring in Action", "Craig Walls", 2022));
    }

    public List<Book> findAll() {
        return List.copyOf(storage.values());
    }
    public List<Book> findAllByAuthor(String author) {
        return storage.values().stream()
                .filter(book -> book.author().equals(author))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Book save(Book book) {
        long id = book.id() != null ? book.id() : sequence.incrementAndGet();
        Book stored = new Book(id, book.title(), book.author(), book.year());
        storage.put(id, stored); return stored;
    }

    public boolean deleteById(long id) {
        return storage.remove(id) != null;
    }


    public int[] stats() {
        if (storage.isEmpty()) {
            return new int[0];
        }
        IntSummaryStatistics stats = storage.values().stream()
                .mapToInt(Book::year)
                .summaryStatistics();

        return new int[] { stats.getMin(), stats.getMax() };
    }
}