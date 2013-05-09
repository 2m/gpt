package lt.doubleem.gpt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ErrorVectors {
	
	public static Random r = new Random();
	
	static Map<Key, List<Matrix<NonPrimeFieldElement>>> errorVectorMap = new HashMap<>();
	
	static {
		addErrorVectors_2_1_4_4_2();
	}
	
	public static List<Matrix<NonPrimeFieldElement>> getErrorVecors(BigInteger p, BigInteger m, BigInteger N, int n, int k) {
		return errorVectorMap.get(new Key(p, m, N, n, k));
	}
	
	public static Matrix<NonPrimeFieldElement> getAnyErrorVector(BigInteger p, BigInteger m, BigInteger N, int n, int k) {
		List<Matrix<NonPrimeFieldElement>> l = errorVectorMap.get(new Key(p, m, N, n, k));
		return l.get(r.nextInt(l.size()));
	}
	
	public static void addErrorVectors_2_1_4_4_2() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		List<Matrix<NonPrimeFieldElement>> errorVectors = new ArrayList<>();

		Matrix<NonPrimeFieldElement> errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 0));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 9));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 1));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 8));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 1));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 10));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 2));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 9));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 2));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 11));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 3));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 10));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 3));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 12));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 4));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 11));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 4));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 13));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 5));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 12));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 5));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 14));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 6));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 13));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 6));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 0));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 7));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 14));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 7));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 1));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 8));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 0));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 8));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 2));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 9));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 1));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 9));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 3));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 10));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 2));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 10));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 4));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 11));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 3));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 11));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 5));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 12));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 4));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 12));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 6));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 13));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 5));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 13));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 7));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 14));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 6));
		errorVectors.add(errorVector);

		errorVector = new Matrix<>(1, 4);
		errorVector.set(0, 0, new NonPrimeFieldElement(p, m, N, 14));
		errorVector.set(0, 1, new NonPrimeFieldElement(p, m, N, 8));
		errorVector.set(0, 2, new NonPrimeFieldElement(p, m, N, 0));
		errorVector.set(0, 3, new NonPrimeFieldElement(p, m, N, 7));
		errorVectors.add(errorVector);
		
		errorVectorMap.put(new Key(p, m, N, 4, 2), errorVectors);
	}
	
	public static class Key {
		BigInteger p;
		BigInteger m;
		BigInteger N;

		int n;
		int k;
		
		public Key(BigInteger p, BigInteger m, BigInteger N, int n, int k) {
			this.p = p;
			this.m = m;
			this.N = N;
			
			this.n = n;
			this.k = k;
		}
		
		public int hashCode() {
			return Arrays.asList(p.intValue(), m.intValue(), N.intValue(), n, k).hashCode();
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof Key)) {
				return false;
			}
			Key b = (Key)o;
			return hashCode() == b.hashCode();
		}
	}
}
