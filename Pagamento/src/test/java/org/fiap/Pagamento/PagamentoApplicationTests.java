package org.fiap.Pagamento;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class PagamentoApplicationTests {

	@Test
	void contextLoads() {
		// Apenas garante que o contexto Spring carrega
		assertTrue(true);
	}
}

