package com.example.project2;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class VehicleDoa {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle vehicle) {

        entityManager.persist(vehicle);
        return;
    }

    public Vehicle getById(int id) {

        return entityManager.find(Vehicle.class, id);
    }

    public Vehicle update(Vehicle vehicle) {

        if (entityManager.find(Vehicle.class, vehicle.getId()) == null) {
            System.out.println("Unknown ID entered.");
            return vehicle;
        }
        else {
            return entityManager.merge(vehicle);
        }
    }

    public ResponseEntity<String> delete(int id) {

        if (entityManager.find(Vehicle.class, id) == null) {
            System.out.println("Unknown ID entered.");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        else {
            entityManager.remove(entityManager.find(Vehicle.class, id));
            return new ResponseEntity<String>(HttpStatus.OK);
        }
    }

    public List<Vehicle> getLatest () {

        System.out.println("list:" + entityManager.createNativeQuery("SELECT * from Inventory order by id desc limit 10").getResultList().get(0).toString());
        String tempVehicle = entityManager.createNativeQuery("SELECT * from Inventory order by id desc limit 10").getResultList().toString();
        System.out.println("vehicle: " + tempVehicle);

        return entityManager.createNativeQuery("SELECT * from Inventory order by id desc limit 10").getResultList();

    }

}
