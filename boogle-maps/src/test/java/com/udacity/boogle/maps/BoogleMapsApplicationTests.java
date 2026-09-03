package com.udacity.boogle.maps;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BoogleMapsApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void returnsAnAddressForCoordinates() throws Exception {
		mockMvc.perform(get("/maps").param("lat", "42.36").param("lon", "-71.06"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address").isNotEmpty())
				.andExpect(jsonPath("$.city").isNotEmpty())
				.andExpect(jsonPath("$.state").isNotEmpty())
				.andExpect(jsonPath("$.zip").isNotEmpty());
	}

	@Test
	public void addressStoresItsFields() {
		Address address = new Address("street", "city", "state", "zip");
		address.setAddress("new street");
		address.setCity("new city");
		address.setState("new state");
		address.setZip("new zip");

		org.assertj.core.api.Assertions.assertThat(address.getAddress()).isEqualTo("new street");
		org.assertj.core.api.Assertions.assertThat(address.getCity()).isEqualTo("new city");
		org.assertj.core.api.Assertions.assertThat(address.getState()).isEqualTo("new state");
		org.assertj.core.api.Assertions.assertThat(address.getZip()).isEqualTo("new zip");
	}

}
