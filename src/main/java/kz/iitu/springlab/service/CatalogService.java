package kz.iitu.springlab.service;

import kz.iitu.springlab.audit.Audited;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;



@Service
public class CatalogService {


    public String FindById(long id) {
        sleep(50);
        return "Item no " + id;
    }

    @Audited(action = "CATALOG_LIST", logArguments = true)
    public List<String> findAll(int limit) {
        sleep(300);
        return IntStream.rangeClosed(1, limit).mapToObj(i -> "Item no " + i).toList();
    }

    public String remove(long id) {
        if ( id <= 0) {
            throw new IllegalArgumentException("Invalid Id " + id);
        }
        return "Removed item " + id;
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {Thread.currentThread().interrupt(); }
    }

    @Audited(action = "CATALOG_REMOVE")
    public String removeTwice(long id) {
        String first = remove(id);
        String second = remove(id + 1);
        return first + "; " + second; }
}
