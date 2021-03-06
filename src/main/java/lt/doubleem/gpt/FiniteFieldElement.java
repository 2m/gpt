package lt.doubleem.gpt;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Class definition for finite field element.
 * Modulus is specified in the variable mod.
 */
public class FiniteFieldElement extends FieldElement {

	public static BigInteger MOD;
	
    private BigInteger mod;
    private BigInteger value;
    
    public FiniteFieldElement(BigInteger mod) {
    	this(mod, BigInteger.ZERO);
    }
    
    public FiniteFieldElement(int mod) {
    	this(BigInteger.valueOf(mod), BigInteger.ZERO);
	}

    public FiniteFieldElement(BigInteger mod, Integer value) {
    	this(mod, BigInteger.valueOf(value));
    }
    
    public FiniteFieldElement(int mod, int value) {
    	this(BigInteger.valueOf(mod), BigInteger.valueOf(value));
	}
    
    public FiniteFieldElement(int mod, BigInteger value) {
    	this(BigInteger.valueOf(mod), value);
	}
    
    /**
     * Class constructor with initial value.
     */
    public FiniteFieldElement(BigInteger mod, BigInteger value) {
    	if (mod == null) {
    		throw new IllegalArgumentException("mod can not be null");
    	}
    	
    	this.mod = mod;
        this.value = normalize(this.mod, value);
    }

	public FieldElement add(FieldElement a) {
        return new FiniteFieldElement(this.mod, this.getValue().add(a.getValue()));
    }

    public FieldElement add(Integer i) {
        return this.add(new FiniteFieldElement(this.mod, i));
    }

    public FieldElement sub(FieldElement a) {
        return new FiniteFieldElement(this.mod, this.getValue().subtract(a.getValue()));
    }

    public FieldElement sub(Integer i) {
        return this.sub(new FiniteFieldElement(this.mod, i));
    }

    public FieldElement mul(FieldElement a) {
        return new FiniteFieldElement(this.mod, this.getValue().multiply(a.getValue()));
    }

    public FieldElement mul(Integer i) {
        return this.mul(new FiniteFieldElement(this.mod, i));
    }
    
    public FieldElement mul(BigInteger i) {
        return this.mul(new FiniteFieldElement(this.mod, i));
    }
    
    public FieldElement pow(BigInteger i) {
    	FieldElement result = this;
    	while (i.compareTo(BigInteger.ONE) > 0) {
    		result = result.mul(this);
    		i = i.subtract(BigInteger.ONE);
    	}
        return result;
    }

    public FieldElement inv() {
    	BigInteger i = BigInteger.ONE;
        while (i.compareTo(this.mod) < 0) {
            if (this.mul(i).isOne()) {
                return new FiniteFieldElement(this.mod, i);
            }
            i = i.add(BigInteger.ONE);
        }
        throw new ArithmeticException(String.format("No inv of %s found. Probable mod is no prime.", this));
        //return this.getZero();
    }

    public FieldElement getZero() {
        return new FiniteFieldElement(this.mod, BigInteger.ZERO);
    }

    public boolean isZero() {
        return value.mod(mod).compareTo(BigInteger.ZERO) == 0;
    }

    public FieldElement getOne() {
        return new FiniteFieldElement(this.mod, BigInteger.ONE);
    }

    public boolean isOne() {
    	return value.mod(mod).compareTo(BigInteger.ONE) == 0;
    }
    
    public FieldElement getPrimitiveElement() {
    	// TODO look for more optimal algorithm
    	Set<FieldElement> allEmenets = new HashSet<>();
    	FieldElement element = getOne();
    	while (!element.isZero()) {
    		allEmenets.add(element);
    		element = element.add(1);
    	}

    	FieldElement possiblePrimitiveElement = getOne();
    	while (!possiblePrimitiveElement.isZero()) {
			element = possiblePrimitiveElement.getOne().mul(possiblePrimitiveElement);
			Set<FieldElement> generatedEmenets = new HashSet<>();
			while (!element.isOne()) {
				generatedEmenets.add(element);
				element = (FiniteFieldElement) element.mul(possiblePrimitiveElement);
			}
			generatedEmenets.add(element); // add one
			
			if (generatedEmenets.equals(allEmenets)) {
				return possiblePrimitiveElement;
			}
			
			possiblePrimitiveElement = possiblePrimitiveElement.add(1);
    	}
    	
    	throw new ArithmeticException("Unable to find primitive element for mod:" + mod);
	}

    public BigInteger getValue() {
        return value;
    }

    public String toString() {
        return value.toString();
    }

    /**
     * Checks if values are equal of two objects.
     * Special arithemtics must be used because,
     * e.g. -3 mod 2 == 5 mod 2
     * and so on.
     */
    public boolean equals(Object o) {
        if (!(o instanceof FiniteFieldElement)) {
            return false;
        }

        BigInteger valueA = this.getValue();
        BigInteger valueB = ((FiniteFieldElement)o).getValue();

        return normalize(this.mod, valueA).compareTo(normalize(this.mod, valueB)) == 0;
    }

    /**
     * Internal value is returned as hashcode.
     */
    public int hashCode() {
    	return normalize(this.mod, getValue()).hashCode();
    }
    
    private static BigInteger normalize(BigInteger mod, BigInteger val) {
    	return val.mod(mod).add(mod).mod(mod);
    }

}