package com.example.Luna;

import com.example.Luna.api.model.Book;
import com.example.Luna.repository.BookRepository;
import com.example.Luna.repository.DiscountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LunaApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private DiscountRepository discountRepository;

	@Test
	void testGetBookByIdIntegration() {
		// Arrange: Ensure test data exists in DB
		Book book = new Book(3L, "Dune", "Frank Herbert", "1965",
				"Dune is a 1965 epic science fiction novel...",
				new BigDecimal("45.99"), "/img/book_images/dune.jpg",
				90450, 9000);
		bookRepository.save(book);

		// Act: Perform the request
		ResponseEntity<String> response = restTemplate.getForEntity("/api/books/3", String.class);

		// Assert: Verify the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(Objects.requireNonNull(response.getBody()).contains("\"title\":\"Dune\""));
		assertTrue(response.getBody().contains("\"author\":\"Frank Herbert\""));

		// Cleanup: Remove test data
		bookRepository.delete(book);
	}
}
