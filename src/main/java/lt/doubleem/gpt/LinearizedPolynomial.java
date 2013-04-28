package lt.doubleem.gpt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LinearizedPolynomial<T extends FieldElement> extends Polynomial<T> {
	private BigInteger powerOfPrime;
	
	public LinearizedPolynomial(BigInteger powerOfPrime) {
		this.powerOfPrime = powerOfPrime;
	}
	
	public LinearizedPolynomial(BigInteger powerOfPrime, Polynomial<T> polynomial) {
		this.powerOfPrime = powerOfPrime;
		this.p = polynomial.p;
	}
	
	@SuppressWarnings("unchecked")
	public LinearizedPolynomial(BigInteger powerOfPrime, T t, String s) {
		this(powerOfPrime);
		
        p = new ArrayList<T>();

        for (int i = 0; i < s.length(); i++) {
            try {
                p.add((T) t.add(new Integer(s.substring(i, i+1))));
            }
            catch (Exception ex) {
            	ex.printStackTrace();
            }
        }
    }
	
	public LinearizedPolynomial<T> add(Polynomial<T> b) {
        return new LinearizedPolynomial<T>(powerOfPrime, super.add(b));
    }

    /**
     * Subtract one polynomial from another.
     */
    public LinearizedPolynomial<T> sub(Polynomial<T> b) {
    	return new LinearizedPolynomial<T>(powerOfPrime, super.sub(b));
    }
	
	@SuppressWarnings("unchecked")
    public LinearizedPolynomial<T> mul(LinearizedPolynomial<T> b) {
		LinearizedPolynomial<T> result = new LinearizedPolynomial<T>(powerOfPrime);

        int degA = this.deg();
        int degB = b.deg();

        for (int i = 0; i <= degA + degB; i++) {
        	
        	T c = (T) this.getCoef(0).getZero();
        	
        	for (int k = 0; k <= degA; k++) {
        		for (int s = 0; s <= degB; s++) {
        			if (k + s == i) {
        				c = (T) c.add(this.getCoef(degA - k).mul(b.getCoef(degB - s).pow(powerOfPrime.pow(degA - k))));
        			}
        		}
        	}
        	
        	result.addCoef(degA + degB - i, c);
        }

        trim(result);
        return result;
    }
	
	public LinearizedPolynomial<T> mul(Integer i) {
		return new LinearizedPolynomial<T>(powerOfPrime, super.mul(i));
	}
	
	public LinearizedPolynomial<T> mul(T b) {
		return new LinearizedPolynomial<T>(powerOfPrime, super.mul(b));
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<LinearizedPolynomial<T>> divisionWithRemainder(LinearizedPolynomial<T> g) {

        // check for division by zero
        if (g.isZero()) {
            throw(new ArithmeticException("/ by zero"));
        }

        // result will be ArrayList with to polynomials
        ArrayList<LinearizedPolynomial<T>> result = new ArrayList<>();
        
        // integer part will be stored here
        LinearizedPolynomial<T> integer = new LinearizedPolynomial<T>(powerOfPrime);

        LinearizedPolynomial<T> quotient = null;
        LinearizedPolynomial<T> remainder = this;        
        while (remainder.deg() >= g.deg()) {
        	quotient = new LinearizedPolynomial<T>(powerOfPrime);
        	quotient.setCoef(remainder.deg() - g.deg(), (T) remainder.getCoef(remainder.deg()).mul(g.getCoef(g.deg()).inv().pow(powerOfPrime.pow(remainder.deg() - g.deg()))));
        	quotient = quotient.trim();
        	
        	if (quotient.isZero()) {
        		throw new ArithmeticException(String.format("Unable to divide %s by %s. Maybe use left division.", this, g));
        	}
        	
        	remainder = remainder.sub(quotient.mul(g));
        	
        	integer = integer.add(quotient);
        }
        
        result.add(integer);
        result.add(remainder);
        return result;
    }
	
	 /**
     * Returns the integer part of division.
     */
    public LinearizedPolynomial<T> div(LinearizedPolynomial<T> b) {
        return this.divisionWithRemainder(b).get(0);
    }

    /**
     * Returns the remainder part of division.
     */
    public LinearizedPolynomial<T> rem(LinearizedPolynomial<T> b) {
        return this.divisionWithRemainder(b).get(1);
    }
    
    public LinearizedPolynomial<T> trim() {
    	return new LinearizedPolynomial<T>(powerOfPrime, super.trim());
    }
    
    public List<T> getRoots() {
    	List<T> roots = new ArrayList<>();
    	
    	T possibleRoot = (T) getCoef(0).getOne();
    	while (true) {
    		T result = (T) possibleRoot.getZero();
    		for (int i = 0; i <= this.deg(); i++) {
    			result = (T) result.add(getCoef(i).mul(possibleRoot.pow(powerOfPrime.pow(i))));    			
    		}
    		if (result.isZero()) {
				roots.add(possibleRoot);
			}
    		possibleRoot = (T) possibleRoot.mul(possibleRoot.getPrimitiveElement());
    		
    		if (possibleRoot.isOne()) {
    			// tried everything, break the loop
    			break;
    		}
    	}
    	
    	return roots;
    }
}
