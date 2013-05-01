package lt.doubleem.gpt;

import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LinearlyIndependentTest {

	@Test
	public void shouldBeLinearlyIndependent_2_3_1() {
		int p = 2;
		int m = 3;
		int n = 1;
		
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		
		for (int i = -1; i < p - 1; i++) {
			FieldElement fi = new NonPrimeFieldElement(p, m, n, i);
			for (int j = -1; j < p - 1; j++) {
				FieldElement fj = new NonPrimeFieldElement(p, m, n, j);
				for (int k = -1; k < p - 1; k++) {
					FieldElement fk = new NonPrimeFieldElement(p, m, n, k);
					if (fi.isZero() && fj.isZero() && fk.isZero()) {
						continue;
					}
					
					if (f1.mul(fi).add(f2.mul(fj)).add(f3.mul(fk)).isZero()) {
						fail(String.format("Sum equals zero when: i: %s, j: %s, k: %s", i, j, k));
					}
				}
			}
		}
	}
	
	@Test
	public void shouldBeLinearlyIndependent_2_4_1() {
		int p = 2;
		int m = 4;
		int n = 1;
		
		FieldElement f1 = new NonPrimeFieldElement(p, m, n, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, n, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, n, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, n, 3);
		
		for (int i = -1; i < p - 1; i++) {
			FieldElement fi = new NonPrimeFieldElement(p, m, n, i);
			for (int j = -1; j < p - 1; j++) {
				FieldElement fj = new NonPrimeFieldElement(p, m, n, j);
				for (int k = -1; k < p - 1; k++) {
					FieldElement fk = new NonPrimeFieldElement(p, m, n, k);
					for (int l = -1; l < p - 1; l++) {
						FieldElement fl = new NonPrimeFieldElement(p, m, n, l);
						if (fi.isZero() && fj.isZero() && fk.isZero() && fl.isZero()) {
							continue;
						}
					
						if (f1.mul(fi).add(f2.mul(fj)).add(f3.mul(fk)).add(f4.mul(fl)).isZero()) {
							fail(String.format("Sum equals zero when: i: %s, j: %s, k: %s, l: %s", i, j, k, l));
						}
					}
				}
			}
		}
	}
}
