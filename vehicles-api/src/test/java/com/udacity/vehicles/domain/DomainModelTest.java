package com.udacity.vehicles.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.udacity.vehicles.client.maps.Address;
import com.udacity.vehicles.client.prices.Price;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DomainModelTest {

    @Test
    void storesCarDetailsAndLocation() {
        Manufacturer manufacturer = new Manufacturer(1, "Maker");
        Details details = new Details();
        details.setBody("sedan"); details.setModel("Model"); details.setManufacturer(manufacturer);
        details.setNumberOfDoors(4); details.setFuelType("electric"); details.setEngine("motor");
        details.setMileage(12); details.setModelYear(2025); details.setProductionYear(2024);
        details.setExternalColor("blue");
        Location location = new Location(1.2, 3.4);
        location.setAddress("1 Main St"); location.setCity("Boston"); location.setState("MA"); location.setZip("02101");
        Car car = new Car();
        LocalDateTime now = LocalDateTime.now();
        car.setId(1L); car.setCreatedAt(now); car.setModifiedAt(now); car.setCondition(Condition.NEW);
        car.setDetails(details); car.setLocation(location); car.setPrice("USD 10");

        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getCreatedAt()).isEqualTo(now);
        assertThat(car.getModifiedAt()).isEqualTo(now);
        assertThat(car.getCondition()).isEqualTo(Condition.NEW);
        assertThat(car.getDetails().getBody()).isEqualTo("sedan");
        assertThat(car.getDetails().getModel()).isEqualTo("Model");
        assertThat(car.getDetails().getManufacturer()).isSameAs(manufacturer);
        assertThat(manufacturer.getCode()).isEqualTo(1);
        assertThat(manufacturer.getName()).isEqualTo("Maker");
        assertThat(details.getNumberOfDoors()).isEqualTo(4);
        assertThat(details.getFuelType()).isEqualTo("electric");
        assertThat(details.getEngine()).isEqualTo("motor");
        assertThat(details.getMileage()).isEqualTo(12);
        assertThat(details.getModelYear()).isEqualTo(2025);
        assertThat(details.getProductionYear()).isEqualTo(2024);
        assertThat(details.getExternalColor()).isEqualTo("blue");
        assertThat(location.getLat()).isEqualTo(1.2);
        assertThat(location.getLon()).isEqualTo(3.4);
        assertThat(location.getAddress()).isEqualTo("1 Main St");
        assertThat(location.getCity()).isEqualTo("Boston");
        assertThat(location.getState()).isEqualTo("MA");
        assertThat(location.getZip()).isEqualTo("02101");
        assertThat(car.getPrice()).isEqualTo("USD 10");
    }

    @Test
    void storesClientResponseValues() {
        Address address = new Address();
        address.setAddress("street"); address.setCity("city"); address.setState("state"); address.setZip("zip");
        Price price = new Price();
        price.setCurrency("USD"); price.setPrice(BigDecimal.TEN); price.setVehicleId(3L);

        assertThat(address.getAddress()).isEqualTo("street");
        assertThat(address.getCity()).isEqualTo("city");
        assertThat(address.getState()).isEqualTo("state");
        assertThat(address.getZip()).isEqualTo("zip");
        assertThat(price.getCurrency()).isEqualTo("USD");
        assertThat(price.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(price.getVehicleId()).isEqualTo(3L);
    }
}
