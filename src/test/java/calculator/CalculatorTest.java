package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

	private static Instant startedAt;
	private Calculator calculatorUnderTest;

	@BeforeAll
	static public void initStartingTime() {
		System.out.println("Appel avant tous les tests");
		startedAt = Instant.now();
	}

	@AfterAll
	static public void showTestDuration() {
		System.out.println("Appel après tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis();
		System.out.println(MessageFormat.format("Durée des tests : {0} ms", duration));
	}

	@BeforeEach
	public void initCalulator() {
		System.out.println("Appel avant chaque test");
		calculatorUnderTest = new Calculator();
	}

	@AfterEach
	public void undefCalculator() {
		System.out.println("Appel après chaque test");
		calculatorUnderTest = null;
	}

	@Test
	void Add3and5_returns8() {
		// Arrange
		int a = 3;
		int b = 5;

		// Act
		int somme = calculatorUnderTest.add(a, b);

		// Assert
		assertThat(somme).isEqualTo(8);
	}

	@Test
	void Multiply4and5_returns20() {
		// Arrange
		int a = 4;
		int b = 5;
		Calculator calculator = new Calculator();

		// Act
		int produit = calculatorUnderTest.multiply(a, b);

		// Assert
		assertThat(produit).isEqualTo(20);
	}

	@ParameterizedTest(name = "{0} x 0 doit être égal à 0")
	@ValueSource(ints = { 1, 2, 42, 1011, 5089 })
	public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
		// Arrange -- Tout est prêt !
		// Act -- Multiplier par zéro
		int actualResult = calculatorUnderTest.multiply(arg, 0);
		// Assert -- ça vaut toujours zéro !
		assertThat(actualResult).isEqualTo(0);
	}

	@ParameterizedTest(name = "{0} + {1} should equal to {2}")
	@CsvSource({ "1,1,2", "2,3,5", "42,57,99" })
	public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
		// Arrange -- Tout est prêt !
		// Act
		int actualResult = calculatorUnderTest.add(arg1, arg2);
		// Assert
		assertThat(actualResult).isEqualTo(expectResult);
	}

	@Timeout(1)
	@Test
	@Disabled
	public void longCalcul_shouldComputeInLessThan1Second() {
		// Arrange
		// Act
		calculatorUnderTest.longCalculation();
		// Assert
		// ...
		assertTrue(true);
	}
	
	@Test
	public void listDigit_shouldReturnsTheListOfDigits_ofPositiveInteger() {
		int integer = 48456;
		
		Set<Integer> actualDigitsSet = calculatorUnderTest.digitsSet(integer);
		
		assertThat(actualDigitsSet).containsExactlyInAnyOrder(4,8,5,6);
	}
}
