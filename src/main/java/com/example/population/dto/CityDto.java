package com.example.population.dto;

import lombok.Data;

@Data
public class CityDto {
    private Integer id;
    private String cName;
    private Integer cFounded;
    private Integer cCountyId;
    private Integer cRegionId;
}
