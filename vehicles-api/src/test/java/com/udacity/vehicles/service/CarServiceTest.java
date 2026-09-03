package com.udacity.vehicles.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock private CarRepository repository;
    @Mock private MapsClient mapsClient;
    @Mock private PriceClient priceClient;
    @InjectMocks private CarService service;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1L);
        car.setCondition(Condition.USED);
        car.setLocation(new Location(1.0, 2.0));
        car.setDetails(new Details());
    }

    @Test
    void listsCarsFromRepository() {
        given(repository.findAll()).willReturn(List.of(car));

        assertThat(service.list()).containsExactly(car);
    }

    @Test
    void enrichesFoundCarWithPriceAndAddress() {
        Location enriched = new Location(3.0, 4.0);
        given(repository.findById(1L)).willReturn(Optional.of(car));
        given(priceClient.getPrice(1L)).willReturn("USD 123.45");
        given(mapsClient.getAddress(car.getLocation())).willReturn(enriched);

        Car result = service.findById(1L);

        assertThat(result.getPrice()).isEqualTo("USD 123.45");
        assertThat(result.getLocation()).isSameAs(enriched);
    }

    @Test
    void throwsWhenCarIsNotFound() {
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(CarNotFoundException.class);
        assertThatThrownBy(() -> service.delete(1L)).isInstanceOf(CarNotFoundException.class);
    }

    @Test
    void savesNewCar() {
        car.setId(null);
        given(repository.save(car)).willReturn(car);

        assertThat(service.save(car)).isSameAs(car);
        verify(repository).save(car);
    }

    @Test
    void updatesExistingCar() {
        Car stored = new Car();
        given(repository.findById(1L)).willReturn(Optional.of(stored));
        given(repository.save(stored)).willReturn(stored);

        assertThat(service.save(car)).isSameAs(stored);
        assertThat(stored.getCondition()).isEqualTo(Condition.USED);
        assertThat(stored.getLocation()).isSameAs(car.getLocation());
    }

    @Test
    void deletesExistingCar() {
        given(repository.findById(1L)).willReturn(Optional.of(car));

        service.delete(1L);

        verify(repository).delete(car);
    }
}
