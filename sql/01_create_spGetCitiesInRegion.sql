CREATE PROCEDURE `spGetCitiesInRegion`(
    IN reg_id int
)
BEGIN
    select * from v_city_full_data where region_id = reg_id;
END