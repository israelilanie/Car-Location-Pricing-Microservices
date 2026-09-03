package com.udacity.pricing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.udacity.pricing.domain.price.Price;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getPriceByVehicleId() throws Exception {
		mockMvc.perform(get("/services/price").param("vehicleId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vehicleId").value(1))
				.andExpect(jsonPath("$.currency").value("USD"))
				.andExpect(jsonPath("$.price").exists());
	}

	@Test
	public void returnsNotFoundForUnknownVehicle() throws Exception {
		mockMvc.perform(get("/services/price").param("vehicleId", "99"))
				.andExpect(status().isNotFound());
	}

	@Test
	public void priceStoresItsFields() {
		Price price = new Price("USD", BigDecimal.TEN, 3L);
		price.setCurrency("EUR");
		price.setPrice(BigDecimal.ONE);
		price.setVehicleId(4L);

		assertThat(price.getCurrency()).isEqualTo("EUR");
		assertThat(price.getPrice()).isEqualTo(BigDecimal.ONE);
		assertThat(price.getVehicleId()).isEqualTo(4L);
	}

}
