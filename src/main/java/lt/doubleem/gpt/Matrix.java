package lt.doubleem.gpt;

import java.util.ArrayList;
import java.util.List;

public class Matrix<T extends FieldElement> {
	private int rows;
	private int cols;
	
	private List<List<T>> data;
	
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		data = new ArrayList<List<T>>();
		for (int i = 0; i < rows; i++) {
			List<T> row = new ArrayList<T>();
			for (int j = 0; j < cols; j++) {
				row.add(null);
			}
			data.add(row);
		}
	}
	
	public void set(int row, int col, T e) {
		data.get(row).set(col, e);
	}
	
	public T get(int row, int col) {
		return data.get(row).get(col);
	}
	
	public void multiplyRow(int row, T e) {
		for (int i = 0; i < cols; i++) {
			set(row, i, (T) get(row, i).mul(e));
		}
	}
	
	public void multiplyRow(int rowFrom, int rowTo, T e) {
		for (int i = 0; i < cols; i++) {
			set(rowTo, i, (T) get(rowFrom, i).mul(e).add(get(rowTo, i)));
		}
	}

	public void standardize() {
		for (int i = 0; i < rows; i++) {
			T multi = (T) get(i, i).inv();
			multiplyRow(i, multi);
			
			for (int j = 0; j < rows; j++) {
				if (j == i) {
					continue;
				}
				T multi2 = (T) get(j, i).mul(-1);
				multiplyRow(i, j, multi2);
			}
		}
	}
	
	public List<T> getColumn(int col) {
		List<T> column = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			column.add(get(i, col));
		}
		return column;
	}
	
	public Matrix<T> getMatrix(int rowCount, int colCount, int rowOffset, int colOffset) {
		Matrix<T> result = new Matrix<>(rowCount, colCount);
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				result.set(i, j, get(rowOffset + i, colOffset + j));
			}
		}
		return result;
	}
	
	public Matrix<T> inv() {
		Matrix<T> result = new Matrix<>(rows, cols * 2);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.set(i, j, get(i, j));
			}
			// set identity matrix
			for (int j = 0; j < cols; j++) {
				if (i == j) {
					result.set(i, j + cols, (T) get(0, 0).getOne());
				}
				else {
					result.set(i, j + cols, (T) get(0, 0).getZero());
				}
			}
		}
		result.standardize();
		return result.getMatrix(rows, cols, 0, cols);
	}
	
	public Matrix<T> mul(Matrix<T> b) {
		if (cols != b.rows) {
			throw new ArithmeticException(String.format("Unable to multiply. %s and %s should be equal", cols, b.rows));
		}
		
		Matrix<T> result = new Matrix<>(rows, b.cols);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < b.cols; col++) {
				T element = (T) get(0, 0).getZero();
				for (int k = 0; k < cols; k++) {
					element = (T) element.add(get(row, k).mul(b.get(k, col)));
				}
				result.set(row, col, element);
			}
		}
		return result;
	}
	
	public Matrix<T> transpose() {
		Matrix<T> result = new Matrix<>(cols, rows);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				result.set(col, row, get(row, col));
			}
		}
		return result;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sb.append(get(i,  j));
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public boolean equals(Object a) {
		if (!(a instanceof Matrix)) {
            return false;
        }
		
		Matrix<T> b = (Matrix) a;
		if (cols != b.cols || rows != b.rows) {
			return false;
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!get(i, j).equals(b.get(i, j))) {
					return false;
				}
			}
		}
		
		return true;
	}
}
