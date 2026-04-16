/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.backend.view;

import com.example.backend.model.Country;
import com.example.backend.service.Service;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/countries")
@PermitAll
public class Countries {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Country> find(@DefaultValue("") @QueryParam("name") String name) { 
        return Service.instance().find(name);
    }
    
    @GET
    @Path("{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public Country read(@PathParam("name") String name) {
        try {
            return Service.instance().read(name);
        } catch (Exception ex) {
            throw new NotFoundException(); 
        }
    } 
    
    @DELETE
    @Path("{name}")
    public void delete(@PathParam("name") String name) {
        Service.instance().delete(name);
    }     
}
