package kz.iitu.springlab.controller;


import kz.iitu.springlab.audit.RequiresRole;
import kz.iitu.springlab.service.CatalogService;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab4")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/item/{id}")
    public String findItemById(@PathVariable String id) {
        long serviceId = Integer.parseInt(id);
        return catalogService.FindById(serviceId);
    }

    @GetMapping("/items")
    public List<String> findItems(@RequestParam String limit) {
        int serviceLimit = Integer.parseInt(limit);
        return catalogService.findAll(serviceLimit);
    }

    @RequiresRole("ADMIN")
    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable String id) {
        long serviceId = Integer.parseInt(id);
        return catalogService.remove(serviceId);
    }


    @DeleteMapping("/remove-twice/{id}")
    public String removeItem(@PathVariable String id) {
        long serviceId = Integer.parseInt(id);
        return catalogService.removeTwice(serviceId);
    }

    @GetMapping("/proxy")
    public Map<String, String> proxyInfo() {
        return Map.of( "className",
                catalogService.getClass().getName(),
                "superClass",
                catalogService.getClass().getSuperclass().getSimpleName(),
                "isAopProxy",
                String.valueOf(AopUtils.isAopProxy(catalogService)),
                "isCglib", String.valueOf(AopUtils.isCglibProxy(catalogService))); }
}
