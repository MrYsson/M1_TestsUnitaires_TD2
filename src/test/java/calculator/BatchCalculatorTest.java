package calculator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import calculator.model.CalculationModel;

import org.junit.jupiter.api.Test;

class BatchCalculatorTest {

	@Test
	public void givenOperationsList_whenbatchCalculate_thenReturnsCorrectAnswerList()
			throws IOException, URISyntaxException {
		// GIVEN
		Stream<String> operations;
		
		// Aller chercher la donnée fournie par un collaborateur sur Internet
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(
				"https://raw.githubusercontent.com/skatlamenace/TD3-Calculator/main/src/test/resources/dataset/operations.txt")
						.openStream()))) {
			operations = br.lines();

			BatchCalculator batchCalculator = new BatchCalculator(new Calculator());

			// WHEN
			List<CalculationModel> resultats = batchCalculator.batchCalculate(operations);

			// THEN
			assertThat(resultats).extracting("solution").containsExactly(4, 20, 14, 30);
		}
	}

}
