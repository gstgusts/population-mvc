CREATE PROCEDURE `spAddUpdateCity` (
    IN id int,
    IN name nvarchar(100),
    IN founded int,
    IN county_id int,
    IN region_id int,
    OUT new_id int
)
BEGIN
    IF id=0 THEN
        insert into city (city_name, city_founded, city_region_id, city_county_id)
        values (name, founded, region_id, county_id);

        SELECT LAST_INSERT_ID() into new_id;

    ELSE
        update city set city_name = name,
                        city_founded = founded,
                        city_region_id = region_id,
                        city_county_id = county_id
        where city_id = id;

        select id into new_id;

    END IF;
END
