package calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.MessageFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;



@ExtendWith(LoggingExtension.class)
@DisplayName("Test du calculator")
public class CalculatorTest {

	private static Instant startedAt;

	private Calculator calculatorUnderTest;
	
	private int cacheFactorial;
	
	private Logger logger;

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@BeforeEach
	public void initCalculator() {
		logger.info("Appel avant chaque test");
		calculatorUnderTest = new Calculator();
	}
	
	@AfterEach
	public void undefCalculator() {
		logger.info("Appel après chaque test");
		calculatorUnderTest = null;
	}
	
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
	


	@Test
	@Tag("QuatreOperations") // ce test fait partie des tests des 4 opérations de base
	@DisplayName("Vérification addition")
	public void testAddTwoPositiveNumbers() {
		// Arrange
		int a = 2;
		int b = 3;

		// Act
		int somme = calculatorUnderTest.add(a, b);

		// Assert
		assertThat(somme).isEqualTo(5);
	}

	@Test
	@Tag("QuatreOperations") // ce test fait partie des tests des 4 opérations de base
	@DisplayName("Vérification multiplication")
	public void multiply_shouldReturnTheProduct_ofTwoIntegers() {
		// Arrange
		int a = 42;
		int b = 11;

		// Act
		int produit = calculatorUnderTest.multiply(a, b);

		// Assert
		assertEquals(462, produit);
	}
	
	@ParameterizedTest(name = "{0} x 0 doit être égal à 0")
	@Tag("QuatreOperations") // ce test fait partie des tests des 4 opérations de base
	@DisplayName("Vérification multiplication par 0")
	@ValueSource(ints = { 1, 2, 42, 1011, 5089 })
	public void multiply_shouldReturnZero_ofZeroWithMultipleIntegers(int arg) {
		// Arrange -- Tout est prêt !

		// Act -- Multiplier par zéro
		int actualResult = calculatorUnderTest.multiply(arg, 0);

		// Assert -- ça vaut toujours zéro !
		assertEquals(0, actualResult);
	}
	
	@ParameterizedTest(name = "{0} + {1} should equal to {2}")
	@Tag("QuatreOperations") // ce test fait partie des tests des 4 opérations de base
	@DisplayName("Vérification multiplication param")
	@CsvSource({ "1,1,2", "2,3,5", "42,57,99" })
	public void add_shouldReturnTheSum_ofMultipleIntegers(int arg1, int arg2, int expectResult) {
		// Arrange -- Tout est prêt !

		// Act
		int actualResult = calculatorUnderTest.add(arg1, arg2);

		// Assert
		assertEquals(expectResult, actualResult);
	}
	
	
	@Timeout(1)
	@Test
	@DisplayName("Vérification TimeOut")
	public void longCalcul_shouldComputeInLessThan1Second() {
		// Arrange

		// Act
		calculatorUnderTest.longCalculation();
		
		// Assert
		// ...
	}
	
	
	
	@Test
	@DisplayName("Vérification retour valeur positive")
	public void listDigits_shouldReturnsTheListOfDigits_ofPositiveInteger() {
		// GIVEN
		int number = 95897;

		// WHEN
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

		// THEN
		assertThat(actualDigits).containsExactlyInAnyOrder(5, 7, 8, 9);
	}
	
	@Test
	@DisplayName("Vérification retour valeur positive autre assert")
	public void listDigits_shouldReturnsTheListOfDigits_ofPositiveInteger2() {
		// GIVEN
		int number = 95897;

		// WHEN
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(number);

		// THEN
		Set<Integer> expectedDigits = Stream.of(5, 7, 8, 9).collect(Collectors.toSet());
		assertEquals(expectedDigits, actualDigits);
	}
	
	@Disabled("Stoppé car cela échoue tous les mardis")
	@Test
	public void testDate() {
		// GIVEN
		LocalDateTime dateTime = LocalDateTime.now();
		
		// WHEN
		
		// THEN
		assertThat(dateTime.getDayOfWeek()).isNotEqualTo(DayOfWeek.TUESDAY);
	}
	
	@Test
	public void fact12_shouldReturnsTheCorrectAnswer() {
		// GIVEN
		int number = 12;
		
		// WHEN
		// Calculer 12! et sauve la valeur pour un autre test
		cacheFactorial = calculatorUnderTest.fact(number);
		
		// THEN
		assertThat(cacheFactorial).isEqualTo(12*11*10*9*8*7*6*5*4*3*2);
		
	}
	
	@Test
	public void digitsSetOfFact12_shouldReturnsTheCorrectAnswser() {
		// GIVEN
		// 12! est mis en cache par le test précédent
		
		// WHEN
		Set<Integer> actualDigits = calculatorUnderTest.digitsSet(cacheFactorial);
		
		// THEN
		assertThat(actualDigits).containsExactlyInAnyOrder(0, 1, 4, 6, 7, 9);
	}
	
	@Test
	public void multiplyAndDivide_shouldBeIdentity() {
		// GIVEN
		Random r = new Random();
		int a = r.nextInt() % 100;
		int b = r.nextInt() % 3;
		
		// WHEN on multiplie a par b puis on divise par b
		int c = calculatorUnderTest.divide(calculatorUnderTest.multiply(a, b), b);
		
		// THEN on ré-obtient a
		assertThat(c).isEqualTo(a);
	}

}



