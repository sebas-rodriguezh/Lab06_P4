package com.example.backend.view;

import com.example.backend.model.Country;
import com.example.backend.service.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class Countries {

    @GetMapping
    public List<Country> find(@RequestParam(defaultValue = "") String name) {
        return Service.instance().find(name);
    }

    @GetMapping("/{name}")
    public Country read(@PathVariable String name) {
        try {
            return Service.instance().read(name);
        } catch (Exception ex) {
            throw new RuntimeException("Country not found");
        }
    }

    @PostMapping
    public void create(@RequestBody Country country) {
        try {
            Service.instance().create(country);
        } catch (Exception ex) {
            throw new RuntimeException("Error creating country");
        }
    }

    @PutMapping("/{name}")
    public void update(@PathVariable String name, @RequestBody Country country) {
        try {
            Service.instance().update(name, country);
        } catch (Exception ex) {
            throw new RuntimeException("Error updating country");
        }
    }

    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        Service.instance().delete(name);
    }
}