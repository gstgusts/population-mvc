package com.example.population.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.ResultSet;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Region {
    private int id;
    private String name;

    @SneakyThrows
    public static Region create(ResultSet rs) {
        var region = new Region(rs.getInt("region_id"),
                rs.getString("region_name"));
        return region;
    }
}
