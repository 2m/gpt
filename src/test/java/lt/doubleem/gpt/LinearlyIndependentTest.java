package lt.doubleem.gpt;

import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

public class LinearlyIndependentTest {

	@Test
	public void shouldBeLinearlyIndependent_2_3() {
		int p = 2;
		int m = 3;
		
		FieldElement f1 = new NonPrimeFieldElement(p, m, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, 2);
		
		for (int i = -1; i < p - 1; i++) {
			FieldElement fi = new NonPrimeFieldElement(p, m, i);
			for (int j = -1; j < p - 1; j++) {
				FieldElement fj = new NonPrimeFieldElement(p, m, j);
				for (int k = -1; k < p - 1; k++) {
					FieldElement fk = new NonPrimeFieldElement(p, m, k);
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
	public void shouldBeLinearlyIndependent_2_4() {
		int p = 2;
		int m = 4;
		
		FieldElement f1 = new NonPrimeFieldElement(p, m, 0);
		FieldElement f2 = new NonPrimeFieldElement(p, m, 1);
		FieldElement f3 = new NonPrimeFieldElement(p, m, 2);
		FieldElement f4 = new NonPrimeFieldElement(p, m, 3);
		
		for (int i = -1; i < p - 1; i++) {
			FieldElement fi = new NonPrimeFieldElement(p, m, i);
			for (int j = -1; j < p - 1; j++) {
				FieldElement fj = new NonPrimeFieldElement(p, m, j);
				for (int k = -1; k < p - 1; k++) {
					FieldElement fk = new NonPrimeFieldElement(p, m, k);
					for (int l = -1; l < p - 1; l++) {
						FieldElement fl = new NonPrimeFieldElement(p, m, l);
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

	@Test
	public void calcGeneratorMatrix() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(4);
		
		FieldElement f11 = new NonPrimeFieldElement(p, m, BigInteger.valueOf(0));
		FieldElement f12 = new NonPrimeFieldElement(p, m, BigInteger.valueOf(1));
		FieldElement f13 = new NonPrimeFieldElement(p, m, BigInteger.valueOf(2));
		FieldElement f14 = new NonPrimeFieldElement(p, m, BigInteger.valueOf(3));
		
		FieldElement f21 = f11.pow(p.pow(m.intValue()));
		FieldElement f22 = f12.pow(p.pow(m.intValue()));
		FieldElement f23 = f13.pow(p.pow(m.intValue()));
		FieldElement f24 = f14.pow(p.pow(m.intValue()));
		
		System.out.println(String.format("%s %s %s %s", f11, f12, f13, f14));
		System.out.println(String.format("%s %s %s %s", f21, f22, f23, f24));
		
		FieldElement multi = f11.inv();
		f11 = f11.mul(multi);
		f12 = f12.mul(multi);
		f13 = f13.mul(multi);
		f14 = f14.mul(multi);
		
		System.out.println(String.format("%s %s %s %s", f11, f12, f13, f14));
		System.out.println(String.format("%s %s %s %s", f21, f22, f23, f24));
		
		multi = new NonPrimeFieldElement(p, m, BigInteger.valueOf(-1)).sub(f21);
		f21 = f21.add(f11.mul(multi));
		f22 = f22.add(f12.mul(multi));
		f23 = f23.add(f13.mul(multi));
		f24 = f24.add(f14.mul(multi));
		
		System.out.println(String.format("%s %s %s %s", f11, f12, f13, f14));
		System.out.println(String.format("%s %s %s %s", f21, f22, f23, f24));
		
		multi = f22.inv();
		f21 = f21.mul(multi);
		f22 = f22.mul(multi);
		f23 = f23.mul(multi);
		f24 = f24.mul(multi);
				
		System.out.println(String.format("%s %s %s %s", f11, f12, f13, f14));
		System.out.println(String.format("%s %s %s %s", f21, f22, f23, f24));
		
		multi = new NonPrimeFieldElement(p, m, BigInteger.valueOf(-1)).sub(f12);
		f11 = f11.add(f21.mul(multi));
		f12 = f12.add(f22.mul(multi));
		f13 = f13.add(f23.mul(multi));
		f14 = f14.add(f24.mul(multi));
		
		System.out.println(String.format("%s %s %s %s", f11, f12, f13, f14));
		System.out.println(String.format("%s %s %s %s", f21, f22, f23, f24));
	}
}
