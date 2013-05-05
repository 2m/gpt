package lt.doubleem.gpt;

import java.math.BigInteger;

/**
 * Abstract class for any field element.
 * Polynomial class uses these methods.
 * Any class that will be used as Polynomial coefficients
 * need to implement these methods.
 */
public abstract class FieldElement {

    /**
     * Addition. Must not change neither this, neither parameter a.
     * Must return result as a new object.
     */
    public abstract FieldElement add(FieldElement a);
    public abstract FieldElement add(Integer a);

    /**
     * Subtraction. Must not change neither this, neither parameter a.
     * Must return result as a new object.
     */
    public abstract FieldElement sub(FieldElement a);
    public abstract FieldElement sub(Integer a);

    /**
     * Multiplication. Must not change neither this, neither parameter a.
     * Must return result as a new object.
     */
    public abstract FieldElement mul(FieldElement a);
    public abstract FieldElement mul(Integer a);
    
    public abstract FieldElement pow(BigInteger a);

    /**
     * Must return inverse object of this, such that
     * this.mul(this.inv()).isOne()
     */
    public abstract FieldElement inv();

    /**
     * Return zero element.
     */
    public abstract FieldElement getZero();
    public abstract boolean isZero();

    /**
     * Return element one.
     */
    public abstract FieldElement getOne();
    public abstract boolean isOne();
    
    public abstract FieldElement getPrimitiveElement();

    public abstract BigInteger getValue();

}