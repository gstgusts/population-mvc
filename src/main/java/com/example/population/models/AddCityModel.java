package com.example.population.models;

import com.example.population.data.County;
import com.example.population.data.Region;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AddCityModel {
    private List<County> counties = new ArrayList<>();
    private List<Region> regions = new ArrayList<>();
    public String name;
}
