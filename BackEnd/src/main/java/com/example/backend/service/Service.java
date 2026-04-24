/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.service;

import com.example.backend.model.Country;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Escinf
 */
public class Service {
    private static Service uniqueInstance;
    
    public static Service instance(){
        if (uniqueInstance == null){
            uniqueInstance = new Service();
        }
        return uniqueInstance; 
    }

    HashMap<String,Country> countries;
    
    private Service(){
        countries = new HashMap();
        Country c;
        c=new Country("AR", "Argentina", "Buenos Aires", 43590400, 2780400, new ArrayList<>(Arrays.asList((new Integer[]{-34,-64}))), "https://flagcdn.com/ar.svg");
        countries.put(c.getName(), c);

        c=new Country("BZ" ,"Belize", "Belmopan", 370300, 22966, new ArrayList<>(Arrays.asList(new Integer[]{17,-88})), "https://flagcdn.com/bo.svg");
        countries.put(c.getName(), c);
    }

    public Country read(String name)throws Exception{
        Country c = countries.get(name);
        if (c!=null) return c;
        else throw new Exception("Country does not exist");
    }

    public List<Country> find(String patron){
        return countries.values().stream().
                filter( c-> c.getName().contains(patron)).
                collect(Collectors.toList());
    }

    public void delete(String id){
        try {
            Country c = readById(id);
            countries.remove(c.getName());
        } catch (Exception e) {
            System.out.println("Error al borrar: " + e.getMessage());
        }
    }

    public void create(Country c) throws Exception {
        if(countries.containsKey(c.getName())) {
            throw new Exception("El país ya existe");
        }
        countries.put(c.getName(), c);
    }

    public void update(String id, Country c) throws Exception {
        try {
            Country countryExistente = readById(id);
            countries.put(countryExistente.getName(), c);
        } catch (Exception e) {
            throw new Exception("El país no existe");
        }
    }

    public Country readById(String id) throws Exception {
        return countries.values().stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new Exception("El país con el ID especificado no existe"));
    }
}
