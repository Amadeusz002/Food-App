package com.example.test.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class Ingredient extends RecursiveTreeObject<Ingredient> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long id;

    public Long getId() {
        return id;
    }

    @NotEmpty
    private String name;

    @NotEmpty
    private String caloric_value;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getCaloric_value() { return caloric_value; }

    public void setCaloric_value(String caloric_value) { this.caloric_value = caloric_value; }
}
