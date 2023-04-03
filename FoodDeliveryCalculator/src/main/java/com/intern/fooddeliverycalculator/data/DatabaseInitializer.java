package com.intern.fooddeliverycalculator.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that initializes the database by filling Base fees and Additional fees with necessary information
 * When using a file to permanently store data, it will not add new rows to the table, otherwise using in-
 * memory db, the tables get completed every run.
 */
@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<String> tables = new ArrayList<>();
        tables.add("Regional_base_fee");
        tables.add("Additional_fees");
        if (event.getApplicationContext().getParent() == null){
            for (String table : tables) {
                if (!isDataPresent(table)){
                    if(table.equals("Regional_base_fee")){
                        insertRBF();
                    }else{
                        insertAF();
                    }

                }
            }
        }
    }




    private boolean isDataPresent(String table){
        String query = "SELECT COUNT(*) FROM " + table;
        int count = jdbcTemplate.queryForObject(query, Integer.class);

        return count > 0;
    }
    private void insertRBF() {
        String insert1 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (1, 'Tallinn', 'Car', 4.0)";
        String insert2 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (2, 'Tallinn', 'Scooter', 3.5)";
        String insert3 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (3, 'Tallinn', 'Bike', 3.0)";
        String insert4 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (4, 'Tartu', 'Car', 3.5)";
        String insert5 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (5, 'Tartu', 'Scooter', 3.0)";
        String insert6 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (6, 'Tartu', 'Bike', 2.5)";
        String insert7 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (7, 'Pärnu', 'Car', 3.0)";
        String insert8 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (8, 'Pärnu', 'Scooter', 2.5)";
        String insert9 = "INSERT INTO Regional_base_fee (id, city, vehicle, fee) VALUES (9, 'Pärnu', 'Bike', 2.0)";

        jdbcTemplate.update(insert1);
        jdbcTemplate.update(insert2);
        jdbcTemplate.update(insert3);
        jdbcTemplate.update(insert4);
        jdbcTemplate.update(insert5);
        jdbcTemplate.update(insert6);
        jdbcTemplate.update(insert7);
        jdbcTemplate.update(insert8);
        jdbcTemplate.update(insert9);

    }
    private void insertAF() {
        String insert1 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (1, 'phenomenon', 'light rain', 0.5)";
        String insert2 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (2, 'phenomenon', 'light shower', 0.5)";
        String insert3 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (3, 'phenomenon', 'moderate shower', 0.5)";
        String insert4 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (4, 'phenomenon', 'heavy shower', 0.5)";
        String insert5 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (5, 'phenomenon', 'moderate rain', 0.5)";
        String insert6 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (6, 'phenomenon', 'heavy rain', 0.5)";
        String insert7 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (7, 'phenomenon', 'light sleet', 1)";
        String insert8 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (8, 'phenomenon', 'moderate sleet', 1)";
        String insert9 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (9, 'phenomenon', 'light snowfall', 1)";
        String insert10 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (10, 'phenomenon', 'moderate snowfall', 1)";
        String insert11 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (11, 'phenomenon', 'heavy snowfall', 1)";
        String insert12 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (12, 'phenomenon', 'blowing snow', 1)";
        String insert13 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (13, 'phenomenon', 'drifting snowfall', 1)";
        String insert14 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (14, 'phenomenon', 'hail', -1)";
        String insert15 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (15, 'phenomenon', 'glaze', -1)";
        String insert16 = "INSERT INTO Additional_fees (id, parameter, condition, fee) VALUES (16, 'phenomenon', 'thunder', -1)";

        jdbcTemplate.update(insert1);
        jdbcTemplate.update(insert2);
        jdbcTemplate.update(insert3);
        jdbcTemplate.update(insert4);
        jdbcTemplate.update(insert5);
        jdbcTemplate.update(insert6);
        jdbcTemplate.update(insert7);
        jdbcTemplate.update(insert8);
        jdbcTemplate.update(insert9);
        jdbcTemplate.update(insert10);
        jdbcTemplate.update(insert11);
        jdbcTemplate.update(insert12);
        jdbcTemplate.update(insert13);
        jdbcTemplate.update(insert14);
        jdbcTemplate.update(insert15);
        jdbcTemplate.update(insert16);

    }
}
