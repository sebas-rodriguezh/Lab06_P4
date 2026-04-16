package com.example.backend.view;

import com.example.backend.model.Country;
import com.example.backend.service.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/countries")
@CrossOrigin(origins = "*")
public class Countries {

    @GetMapping
    public List<Country> findAll() {
        return Service.instance().find("");
    }

    @PostMapping
    public void create(@RequestBody Country country) throws Exception {
        Service.instance().create(country);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        Service.instance().delete(id);
    }

    @GetMapping("/search")
    public List<Country> search(@RequestParam("name") String name) {
        return Service.instance().find(name);
    }

    @GetMapping("/{id}")
    public Country read(@PathVariable("id") String id) throws Exception {
        return Service.instance().read(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Country country) throws Exception {
        Service.instance().update(id, country);
    }
}