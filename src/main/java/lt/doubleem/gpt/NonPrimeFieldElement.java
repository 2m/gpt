package lt.doubleem.gpt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class NonPrimeFieldElement extends FieldElement {

	private static final Map<BigInteger, Map<BigInteger, List<BigInteger>>> allElements;
	static {
		allElements = new HashMap<>();

		BigInteger powerOfPrime = BigInteger.valueOf(2).pow(3);
		Map<BigInteger, List<BigInteger>> fieldElements = new HashMap<>();
		addElement(fieldElements, -1, "000");
		addElement(fieldElements, 0, "100");
		addElement(fieldElements, 1, "010");
		addElement(fieldElements, 2, "001");
		addElement(fieldElements, 3, "110");
		addElement(fieldElements, 4, "011");
		addElement(fieldElements, 5, "111");
		addElement(fieldElements, 6, "101");
		allElements.put(powerOfPrime, fieldElements);
		
		powerOfPrime = BigInteger.valueOf(2).pow(4);
		fieldElements = new HashMap<>();
		addElement(fieldElements, -1, "0000");
		addElement(fieldElements, 0, "1000");
		addElement(fieldElements, 1, "0100");
		addElement(fieldElements, 2, "0010");
		addElement(fieldElements, 3, "0001");
		addElement(fieldElements, 4, "1100");
		addElement(fieldElements, 5, "0110");
		addElement(fieldElements, 6, "0011");
		addElement(fieldElements, 7, "1101");
		addElement(fieldElements, 8, "1010");
		addElement(fieldElements, 9, "0101");
		addElement(fieldElements, 10, "1110");
		addElement(fieldElements, 11, "0111");
		addElement(fieldElements, 12, "1111");
		addElement(fieldElements, 13, "1011");
		addElement(fieldElements, 14, "1001");
		allElements.put(powerOfPrime, fieldElements);
		
		powerOfPrime = BigInteger.valueOf(3).pow(2);
		fieldElements = new HashMap<>();
		addElement(fieldElements, -1, "00");
		addElement(fieldElements, 0, "10");
		addElement(fieldElements, 1, "01");
		addElement(fieldElements, 2, "12");
		addElement(fieldElements, 3, "22");
		addElement(fieldElements, 4, "20");
		addElement(fieldElements, 5, "02");
		addElement(fieldElements, 6, "21");
		addElement(fieldElements, 7, "11");
		allElements.put(powerOfPrime, fieldElements);
	}
	
	private static void addElement(Map<BigInteger, List<BigInteger>> fieldElements, int powerOfPrime, String base) {
		List<BigInteger> baseList = new ArrayList<>();
		for (int i = 0; i < base.length(); i++) {
			baseList.add(new BigInteger(base.substring(i, i + 1)));
		}
		fieldElements.put(BigInteger.valueOf(powerOfPrime), baseList);
	}
	
	private List<BigInteger> getBase(BigInteger power) {
		return allElements.get(p.pow(m.multiply(n).intValue())).get(power);
	}
	
	private BigInteger getPower(List<BigInteger> base) {
		Map<BigInteger, List<BigInteger>> fieldElements = allElements.get(p.pow(m.multiply(n).intValue()));
		for (Entry<BigInteger, List<BigInteger>> entry: fieldElements.entrySet()) {
			if (entry.getValue().equals(base)) {
				return entry.getKey();
			}
		}
		throw new ArithmeticException(String.format("No power found for base %s, p: %s, m: %s, n: %s", base, p, m, n));
	}

	private BigInteger p;
	private BigInteger m;
	private BigInteger n;

	private BigInteger power;
	private List<BigInteger> base;

	public NonPrimeFieldElement(BigInteger p, BigInteger m, BigInteger n, BigInteger power) {
		this(p, m, n);

		this.power = power;
		this.base = getBase(power);
	}

	public NonPrimeFieldElement(BigInteger p, BigInteger m, BigInteger n, List<BigInteger> base) {
		this(p, m, n);

		this.power = getPower(base);
		this.base = base;
	}
	
	public NonPrimeFieldElement(BigInteger p, BigInteger m, BigInteger n, String baseElement) {
		this(p, m, n);

		BigInteger b = new BigInteger(baseElement).mod(p);
		List<BigInteger> base = new ArrayList<>();
		base.add(b);
		for (int i = 1; i < m.multiply(n).intValue(); i ++) {
			base.add(BigInteger.ZERO);
		}
		
		this.power = getPower(base);
	}

	private NonPrimeFieldElement(BigInteger p, BigInteger m, BigInteger N) {
		this.p = p;
		this.m = m;
		this.n = N;
	}

	public NonPrimeFieldElement(int p, int m, int n, int power) {
		this(BigInteger.valueOf(p), BigInteger.valueOf(m), BigInteger.valueOf(n), BigInteger.valueOf(power));
	}
	
	public NonPrimeFieldElement(BigInteger p, BigInteger m, BigInteger n, int power) {
		this(p, m, n, BigInteger.valueOf(power));
	}

	@Override
	public FieldElement add(FieldElement a) {
		if (!(a instanceof NonPrimeFieldElement)) {
			throw new ArithmeticException("Unable to add not NonPrimeFieldElement");
		}
		NonPrimeFieldElement b = (NonPrimeFieldElement) a;
		
		List<BigInteger> newBase = new ArrayList<>(base);
		for (int i = 0; i < base.size(); i++) {
			newBase.set(i, base.get(i).add(b.base.get(i)).mod(p));
		}
		
		return new NonPrimeFieldElement(p, m, n, newBase);
	}

	@Override
	public FieldElement add(Integer a) {
		BigInteger b = BigInteger.valueOf(a).mod(p);
		List<BigInteger> newBase = new ArrayList<>();
		newBase.add(b);
		for (int i = 1; i < m.multiply(n).intValue(); i ++) {
			newBase.add(BigInteger.ZERO);
		}
		return this.add(new NonPrimeFieldElement(p, m, n, newBase));
	}

	@Override
	public FieldElement sub(FieldElement a) {
		if (!(a instanceof NonPrimeFieldElement)) {
			throw new ArithmeticException("Unable to sub not NonPrimeFieldElement");
		}
		NonPrimeFieldElement b = (NonPrimeFieldElement) a;
		
		List<BigInteger> newBase = new ArrayList<>(base);
		for (int i = 0; i < base.size(); i++) {
			newBase.set(i, base.get(i).subtract(b.base.get(i)).mod(p));
		}
		
		return new NonPrimeFieldElement(p, m, n, newBase);
	}

	@Override
	public FieldElement sub(Integer a) {
		throw new ArithmeticException("Not implemented: sub(Integer a)");
	}

	@Override
	public FieldElement mul(FieldElement a) {
		if (!(a instanceof NonPrimeFieldElement)) {
			throw new ArithmeticException("Unable to mul not NonPrimeFieldElement");
		}
		NonPrimeFieldElement b = (NonPrimeFieldElement) a;
		
		if (isZero() || b.isZero()) {
			return getZero();
		}
		
		return new NonPrimeFieldElement(p, m, n, power.add(b.power).mod(p.pow(m.multiply(n).intValue()).subtract(BigInteger.ONE)));
	}

	@Override
	public FieldElement mul(Integer a) {		
		return this.mul(new NonPrimeFieldElement(p, m, n, a.toString()));
	}

	@Override
	public FieldElement pow(BigInteger i) {
		if (BigInteger.ZERO.equals(i)) {
			return getOne();
		}
		
		FieldElement result = new NonPrimeFieldElement(p, m, n, power);
		
		if (i.compareTo(BigInteger.ZERO) < 0) {
			result = result.inv();
			i = i.multiply(BigInteger.valueOf(-1));
		}
		
    	while (i.compareTo(BigInteger.ONE) > 0) {
    		result = result.mul(this);
    		i = i.subtract(BigInteger.ONE);
    	}
        return result;
	}

	@Override
	public FieldElement inv() {
		if (isOne()) {
			return getOne();
		}
		
		return new NonPrimeFieldElement(p, m, n, p.pow(m.multiply(n).intValue()).subtract(power).subtract(BigInteger.ONE));
	}

	@Override
	public FieldElement getZero() {
		return new NonPrimeFieldElement(p, m, n, BigInteger.valueOf(-1));
	}

	@Override
	public boolean isZero() {
		return power.equals(BigInteger.valueOf(-1));
	}

	@Override
	public FieldElement getOne() {
		return new NonPrimeFieldElement(p, m, n, BigInteger.ZERO);
	}

	@Override
	public boolean isOne() {
		return power.equals(BigInteger.ZERO);
	}
	
	public FieldElement getPrimitiveElement() {
		return new NonPrimeFieldElement(p, m, n, BigInteger.ONE);
	}

	@Override
	public BigInteger getValue() {
		throw new ArithmeticException("Not implemented: getValue()");
	}
	
	public boolean equals(Object a) {
        if (!(a instanceof NonPrimeFieldElement)) {
            return false;
        }

        NonPrimeFieldElement b = (NonPrimeFieldElement) a;
        return p.equals(b.p) && m.multiply(n).equals(b.m.multiply(b.n)) && this.power.equals(b.power);
    }

   public int hashCode() {
    	return new ArrayList<BigInteger>() {{
    		add(p);
    		add(m.multiply(n));
    		add(power);
    	}}.hashCode();
    }
   
   private String toBaseString() {
	   StringBuilder sb = new StringBuilder();
	   for (int i = 0; i < base.size(); i++) {
		   sb.append(base.get(i));
	   }
	   return sb.toString();
   }
   
   private String toPowerString() {
	   return String.valueOf(power);
   }
   
   public String toString() {
	   return toPowerString();
   }
}
