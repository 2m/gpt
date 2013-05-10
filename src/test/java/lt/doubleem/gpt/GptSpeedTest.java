package lt.doubleem.gpt;

import static lt.doubleem.gpt.ErrorVectors.getAnyErrorVector;
import static lt.doubleem.gpt.GptTest.getPublicKey;
import static lt.doubleem.gpt.Messages.getAnyMessage;
import static lt.doubleem.gpt.MrdDecodeTest.calculateErrorVector;
import static lt.doubleem.gpt.MrdEncodeTest.getGeneratorMatrix;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class GptSpeedTest {
	
	@Test
	public void encodeTest_2_1_4__4_2() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		int n = 4; // code word length <= N
		int k = 2; // code dimension
		
		int iterations = 10000;
		int testRuns = 10;
		
		timedEOperation(p, m, N, n, k, iterations, testRuns, Operation.ENCODE);
	}
	
	@Test
	public void encodeDecodeTest_2_1_4__4_2() {
		BigInteger p = BigInteger.valueOf(2);
		BigInteger m = BigInteger.valueOf(1);
		BigInteger N = BigInteger.valueOf(4);
		
		int n = 4; // code word length <= N
		int k = 2; // code dimension
		
		int iterations = 10000;
		int testRuns = 10;
		
		timedEOperation(p, m, N, n, k, iterations, testRuns, Operation.ENCODE_DECODE);
	}

	public double gptEncode(BigInteger p, BigInteger m, BigInteger N, int n, int k, long iterations) {
		
		Matrix<NonPrimeFieldElement> gMatrix = getGeneratorMatrix(p, m, N, n, k);
		Matrix<NonPrimeFieldElement> publicKey = getPublicKey(p, m, N, n, k, gMatrix);
		
		Messages.r = new Random(123);
		ErrorVectors.r = new Random(123);
		
		long testStart = System.nanoTime();
		
		for (int i = 0; i < iterations; i++) {
			Matrix<NonPrimeFieldElement> message = getAnyMessage(p, m, N, k);		
			Matrix<NonPrimeFieldElement> errorVector = getAnyErrorVector(p, m, N, n, k);
			
			Matrix<NonPrimeFieldElement> channelWord = message.mul(gMatrix).add(errorVector);
		}
		
		return (System.nanoTime() - testStart);
	}
	
	public double gptEncodeDecode(BigInteger p, BigInteger m, BigInteger N, int n, int k, long iterations) {
		
		Matrix<NonPrimeFieldElement> gMatrix = getGeneratorMatrix(p, m, N, n, k);
		Matrix<NonPrimeFieldElement> hMatrix = gMatrix.dual();
		Matrix<NonPrimeFieldElement> publicKey = getPublicKey(p, m, N, n, k, gMatrix);
		
		Messages.r = new Random(123);
		ErrorVectors.r = new Random(123);
		
		long testStart = System.nanoTime();
		
		for (int i = 0; i < iterations; i++) {
			Matrix<NonPrimeFieldElement> message = getAnyMessage(p, m, N, k);		
			Matrix<NonPrimeFieldElement> errorVector = getAnyErrorVector(p, m, N, n, k);
			
			Matrix<NonPrimeFieldElement> channelWord = message.mul(gMatrix).add(errorVector);
			Matrix<NonPrimeFieldElement> syndrome = channelWord.mul(hMatrix.transpose());
			Matrix<NonPrimeFieldElement> calculatedErrorVector = calculateErrorVector(p, m, N, n, k, syndrome, hMatrix);
			Matrix<NonPrimeFieldElement> decodedMessage = gMatrix.transpose().appendColumn(channelWord.sub(calculatedErrorVector)).getMatrix(k, k + 1, 0, 0).standardize().getColumn(k).transpose();
		}
		
		return (System.nanoTime() - testStart);
	}
	
	public void timedEOperation(BigInteger p, BigInteger m, BigInteger N, int n, int k, int iterations, int testRuns, Operation operation) {
		long bitsPerIteration = (long) Math.floor(Math.log10(p.pow(m.multiply(N).intValue()).intValue()) / Math.log10(2)) * k;
		
		System.out.println(String.format("Running test. p=%s, m=%s, N=%s, n=%s, k=%s, iterations=%s, testRuns=%s.", p, m, N, n, k, iterations, testRuns));
		System.out.println(String.format("Bits per iteration: %s.", bitsPerIteration));
		
		List<Double> testDurations = new ArrayList<>();
		for (int i = 0; i < testRuns; i++) {
			switch (operation) {
				case ENCODE: testDurations.add(gptEncode(p, m, N, n, k, iterations)); break;
				case ENCODE_DECODE: testDurations.add(gptEncodeDecode(p, m, N, n, k, iterations)); break;
				default: throw new RuntimeException("Unknown operation" + operation);
			}
			System.out.println(String.format("Tets run %s complete.", i));
		}
		
		System.out.println(String.format("Test run duration. Min: %sms, max: %sms, average: %sms", getMin(testDurations), getMax(testDurations), getAverage(testDurations)));
		System.out.println(
				String.format("Throughput. Min: %skb/s, max: %skb/s, average: %skb/s",
						getThroughput(getMax(testDurations), iterations, bitsPerIteration),
						getThroughput(getMin(testDurations), iterations, bitsPerIteration),
						getThroughput(getAverage(testDurations), iterations, bitsPerIteration)));
	}
	
	public double getAverage(List<Double> testDurations) {
		double sum = 0;
		for (int i = 0; i < testDurations.size(); i++) {
			sum += testDurations.get(i);
		}
		return sum / (double) testDurations.size() / 1000000;
	}
	
	public double getMin(List<Double> testDurations) {
		double min = Double.MAX_VALUE;
		for (int i = 0; i < testDurations.size(); i++) {
			if (testDurations.get(i) < min) {
				min = testDurations.get(i);
			}
		}
		return min / 1000000;
	}
	
	public double getMax(List<Double> testDurations) {
		double max = Double.MIN_VALUE;
		for (int i = 0; i < testDurations.size(); i++) {
			if (testDurations.get(i) > max) {
				max = testDurations.get(i);
			}
		}
		return max / 1000000;
	}
	
	public double getThroughput(double duration, int iterations, long bitsPerIteration) {
		return (double) (iterations * bitsPerIteration) / duration;
	}
	
	public enum Operation {
		ENCODE, ENCODE_DECODE;
	}
}
