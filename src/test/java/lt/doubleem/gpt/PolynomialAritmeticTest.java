package lt.doubleem.gpt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

public class PolynomialAritmeticTest {

	@Test
	public void shouldTestArithmeticMod3() {

		Polynomial<?> actual = new Polynomial(new FiniteFieldElement(3), "2021").mul(new Polynomial(new FiniteFieldElement(3), "102111"));
		Polynomial<?> expected = new Polynomial(new FiniteFieldElement(3), "200000001");
		assertEquals("Multiplication test. MOD = 3", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(3), "200000001").div(new Polynomial(new FiniteFieldElement(3), "2021"));
		expected = new Polynomial(new FiniteFieldElement(3), "102111");
		assertEquals("Division test. MOD = 3", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(3), "200000001").rem(new Polynomial(new FiniteFieldElement(3), "2021"));
		expected = new Polynomial(new FiniteFieldElement(3), "0");
		assertEquals("Remainder test. MOD = 3", expected, actual);
	}

	@Test
	public void shouldTestArithmeticMod2() {

		Polynomial<?> actual = new Polynomial(new FiniteFieldElement(2), "101").mul(new Polynomial(new FiniteFieldElement(2), "1101"));
		Polynomial<?> expected = new Polynomial(new FiniteFieldElement(2), "111001");
		assertEquals("Multiplication test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "101").mul(new Polynomial(new FiniteFieldElement(2), "1"));
		expected = new Polynomial(new FiniteFieldElement(2), "101");
		assertEquals("Multiplication by 1 test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "101").mul(new Polynomial(new FiniteFieldElement(2), "0"));
		expected = new Polynomial(new FiniteFieldElement(2), "0");
		assertEquals("Multiplication by 0 test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "101101101").rem(new Polynomial(new FiniteFieldElement(2), "100001"));
		expected = new Polynomial(new FiniteFieldElement(2), "011");
		assertEquals("Remainder test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "101101101").rem(new Polynomial(new FiniteFieldElement(2), "101101101"));
		expected = new Polynomial(new FiniteFieldElement(2), "0");
		assertEquals("Remainder by self test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "101101101").rem(new Polynomial(new FiniteFieldElement(2), "1"));
		expected = new Polynomial(new FiniteFieldElement(2), "0");
		assertEquals("Remainder by 1 test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "0101101").div(new Polynomial(new FiniteFieldElement(2), "1"));
		expected = new Polynomial(new FiniteFieldElement(2), "0101101");
		assertEquals("Division by 1 test. MOD = 2", expected, actual);

		try {
			actual = new Polynomial(new FiniteFieldElement(2), "0101101").div(new Polynomial(new FiniteFieldElement(2), "0"));
			fail("Division by zero, exception expected");
		} catch (ArithmeticException ex) {
			// ArithmeticException expected
		}

		actual = new Polynomial(new FiniteFieldElement(2), "0101101").div(new Polynomial(new FiniteFieldElement(2), "0101101"));
		expected = new Polynomial(new FiniteFieldElement(2), "1");
		assertEquals("Division by self test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "10000001").div(new Polynomial(new FiniteFieldElement(2), "1111111"));
		expected = new Polynomial(new FiniteFieldElement(2), "11");
		assertEquals("Division test. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "1000000000000001").gcd(new Polynomial(new FiniteFieldElement(2), "011010001"));
		expected = new Polynomial(new FiniteFieldElement(2), "11010001");
		assertEquals("GCD test #1. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "1000000000000001").gcd(new Polynomial(new FiniteFieldElement(2), "011010001").sub(new Polynomial(
				new FiniteFieldElement(2), "1")));
		expected = new Polynomial(new FiniteFieldElement(2), "111010001");
		assertEquals("GCD test #2. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "11010001").gcd(new Polynomial(new FiniteFieldElement(2), "0001001001001"));
		expected = new Polynomial(new FiniteFieldElement(2), "1001");
		assertEquals("GCD test #3. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "11010001").gcd(new Polynomial(new FiniteFieldElement(2), "0001001001001").sub(new Polynomial(
				new FiniteFieldElement(2), "1")));
		expected = new Polynomial(new FiniteFieldElement(2), "11001");
		assertEquals("GCD test #4. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "111010001").gcd(new Polynomial(new FiniteFieldElement(2), "0001001001001"));
		expected = new Polynomial(new FiniteFieldElement(2), "1");
		assertEquals("GCD test #5. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "111010001").gcd(new Polynomial(new FiniteFieldElement(2), "0001001001001").sub(new Polynomial(
				new FiniteFieldElement(2), "1")));
		expected = new Polynomial(new FiniteFieldElement(2), "111010001");
		assertEquals("GCD test #6. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "111010001").gcd(new Polynomial(new FiniteFieldElement(2), "000000010001011"));
		expected = new Polynomial(new FiniteFieldElement(2), "10011");
		assertEquals("GCD test #7. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "111010001").gcd(new Polynomial(new FiniteFieldElement(2), "000000010001011").sub(new Polynomial(
				new FiniteFieldElement(2), "1")));
		expected = new Polynomial(new FiniteFieldElement(2), "11111");
		assertEquals("GCD test #8. MOD = 2", expected, actual);

		actual = new Polynomial(new FiniteFieldElement(2), "11").mul(new Polynomial(new FiniteFieldElement(2), "111"))
				.mul(new Polynomial(new FiniteFieldElement(2), "10011")).mul(new Polynomial(new FiniteFieldElement(2), "11001"))
				.mul(new Polynomial(new FiniteFieldElement(2), "11111"));
		expected = new Polynomial(new FiniteFieldElement(2), "1000000000000001");
		assertEquals("Multiplication by many test. MOD = 2", expected, actual);
	}

	@Test
	public void shouldTestArithmeticMod5() {

		Polynomial<?> actual = new Polynomial(new FiniteFieldElement(5), "11").mul(new Polynomial(new FiniteFieldElement(5), "21"))
				.mul(new Polynomial(new FiniteFieldElement(5), "31")).mul(new Polynomial(new FiniteFieldElement(5), "41"))
				.mul(new Polynomial(new FiniteFieldElement(5), "201")).mul(new Polynomial(new FiniteFieldElement(5), "301"));

		Polynomial<?> expected = new Polynomial(new FiniteFieldElement(5), "400000001");

		assertEquals("Multiplication by many test. MOD = 5", expected, actual);
	}

	@Test
	public void shouldTestFactorizationMod2() {
		Factorization factorization = new Factorization(7, 2);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 7, q = 2, wrong num of factors", 3, factorization.getFactors().size());
		assertTrue("Fact. n = 7, q = 2, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "11")));
		assertTrue("Fact. n = 7, q = 2, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "1101")));
		assertTrue("Fact. n = 7, q = 2, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "1011")));

		factorization = new Factorization(9, 2);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 9, q = 2, wrong num of factors", 3, factorization.getFactors().size());
		assertTrue("Fact. n = 9, q = 2, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "11")));
		assertTrue("Fact. n = 9, q = 2, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "111")));
		assertTrue("Fact. n = 9, q = 2, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "1001001")));

		factorization = new Factorization(15, 2);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 15, q = 2, wrong num of factors", 5, factorization.getFactors().size());
		assertTrue("Fact. n = 15, q = 2, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "11")));
		assertTrue("Fact. n = 15, q = 2, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "111")));
		assertTrue("Fact. n = 15, q = 2, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "11001")));
		assertTrue("Fact. n = 15, q = 2, factor #4.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "10011")));
		assertTrue("Fact. n = 15, q = 2, factor #5.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "11111")));

		factorization = new Factorization(17, 2);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 17, q = 2, wrong num of factors", 3, factorization.getFactors().size());
		assertTrue("Fact. n = 17, q = 2, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "11")));
		assertTrue("Fact. n = 17, q = 2, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "100111001")));
		assertTrue("Fact. n = 17, q = 2, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(2), "111010111")));
	}

	@Test
	public void shouldTestFactorizationMod3() {
		FiniteFieldElement.MOD = BigInteger.valueOf(3);

		Factorization factorization = new Factorization(4, 3);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 4, q = 3, wrong num of factors", 3, factorization.getFactors().size()); 
		assertTrue("Fact. n = 4 q = 3, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "21")));
		assertTrue("Fact. n = 4 q = 3, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "11")));
		assertTrue("Fact. n = 4 q = 3, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "101")));

		factorization = new Factorization(8, 3);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 8, q = 3, wrong num of factors", 5, factorization.getFactors().size());
		assertTrue("Fact. n = 8 q = 3, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "21")));
		assertTrue("Fact. n = 8 q = 3, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "11")));
		assertTrue("Fact. n = 8 q = 3, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "101")));
		assertTrue("Fact. n = 8 q = 3, factor #4.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "211")));
		assertTrue("Fact. n = 8 q = 3, factor #5.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "221")));

		factorization = new Factorization(10, 3);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 10, q = 3, wrong num of factors", 4, factorization.getFactors().size());
		assertTrue("Fact. n = 10 q = 3, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "21")));
		assertTrue("Fact. n = 10 q = 3, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "11")));
		assertTrue("Fact. n = 10 q = 3, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "11111")));
		assertTrue("Fact. n = 10 q = 3, factor #4.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "12121")));

		factorization = new Factorization(11, 3);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 11, q = 3, wrong num of factors", 3, factorization.getFactors().size());
		assertTrue( "Fact. n = 11 q = 3, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "21")));
		assertTrue( "Fact. n = 11 q = 3, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "221201")));
		assertTrue( "Fact. n = 11 q = 3, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "201211")));

		factorization = new Factorization(13, 3);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 13, q = 3, wrong num of factors", 5, factorization.getFactors().size());
		assertTrue("Fact. n = 13 q = 3, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "21")));
		assertTrue("Fact. n = 13 q = 3, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "2201")));
		assertTrue("Fact. n = 13 q = 3, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "2011")));
		assertTrue("Fact. n = 13 q = 3, factor #4.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "2111")));
		assertTrue("Fact. n = 13 q = 3, factor #5.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(3), "2221")));
	}

	@Test
	public void shouldTestFactorizationMod5() {
		Factorization factorization = new Factorization(8, 5);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 8, q = 5, wrong num of factors", 6, factorization.getFactors().size());
		assertTrue("Fact. n = 8 q = 5, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "11")));
		assertTrue("Fact. n = 8 q = 5, factor #2.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "21")));
		assertTrue("Fact. n = 8 q = 5, factor #3.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "31")));
		assertTrue("Fact. n = 8 q = 5, factor #4.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "41")));
		assertTrue("Fact. n = 8 q = 5, factor #5.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "201")));
		assertTrue("Fact. n = 8 q = 5, factor #6.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "301")));

		factorization = new Factorization(13, 5);
		factorization.constructCosets();
		factorization.construcyGBasis();
		factorization.factorize();
		assertEquals("Fact. n = 13, q = 5, wrong num of factors", 4, factorization.getFactors().size());
		assertTrue("Fact. n = 13 q = 5, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "41")));
		assertTrue("Fact. n = 13 q = 5, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "11411")));
		assertTrue("Fact. n = 13 q = 5, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "12121")));
		assertTrue("Fact. n = 13 q = 5, factor #1.", factorization.getFactors().contains(new Polynomial(new FiniteFieldElement(5), "13031")));
	}

}