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
	
	public void swapRows(int rowNum1, int rowNum2) {
		for (int i = 0; i < cols; i++) {
			T temp = get(rowNum1, i);
			set(rowNum1, i, get(rowNum2, i));
			set(rowNum2, i, temp);
		}		
	}
	
	public void swapColumns(int colNum1, int colNum2) {
		for (int i = 0; i < rows; i++) {
			T temp = get(i, colNum1);
			set(i, colNum1, get(i, colNum2));
			set(i, colNum2, temp);
		}		
	}

	public Matrix<T> standardize() {
		Matrix<T> result = getMatrix(rows, cols, 0, 0);
		for (int i = 0; i < rows; i++) {
			if (result.get(i, i).isZero()) {
				// element in diagonal position is zero
				// no inverse element exists for zero
				// try to find a row below which has not a zero
				boolean swapped = false;
				for (int j = i + 1; j < rows; j++) {
					if (!result.get(j, i).isZero()) {
						result.swapRows(i, j);
						swapped = true;
						break;
					}
				}
				if (!swapped) {
					// no rows below has a not zero element
					// try to find a column to the right,
					// which has not a zero
					for (int j = i + 1; j < cols; j++) {
						if (!result.get(i, j).isZero()) {
							result.swapColumns(i, j);
							swapped = true;
							break;
						}
					}
					if (!swapped) {
						throw new ArithmeticException("Element is zero, and all below are zero.");
					}
				}
			}
			
			T multi = (T) result.get(i, i).inv();
			result.multiplyRow(i, multi);
			
			for (int j = 0; j < rows; j++) {
				if (j == i) {
					continue;
				}
				T multi2 = (T) result.get(j, i).mul(-1);
				result.multiplyRow(i, j, multi2);
			}
		}
		return result;
	}
	
	public Matrix<T> getColumn(int col) {
		return getMatrix(rows, 1, 0, col);
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
		if (rows != cols) {
			throw new ArithmeticException(String.format("Matrix non square. Unable to find inverse."));
		}
		
		Matrix<T> result = appendIndentity();
		result = result.standardize();
		return result.getMatrix(rows, cols, 0, cols);
	}
	
	public Matrix<T> pseudoinv() {
		if (rows <= cols) {
			return transpose().mul(mul(transpose()).inv());
		}
		else {
			return transpose().mul(this).inv().mul(transpose());
		}
	}
	
	public Matrix<T> appendIndentity() {
		Matrix<T> result = new Matrix<>(rows, cols + rows);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.set(i, j, get(i, j));
			}
			// set identity matrix
			for (int j = 0; j < rows; j++) {
				if (i == j) {
					result.set(i, j + cols, (T) get(0, 0).getOne());
				}
				else {
					result.set(i, j + cols, (T) get(0, 0).getZero());
				}
			}
		}
		return result;
	}
	
	public Matrix<T> appendColumn(Matrix<T> column) {
		Matrix<T> result = new Matrix<>(rows, cols + 1);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result.set(i, j, get(i, j));
			}
			result.set(i, cols, column.get(0, i));
		}
		return result;
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
	
	public Matrix<T> mul(Integer b) {
		Matrix<T> result = getMatrix(rows, cols, 0, 0);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				result.set(row, col, (T) result.get(row, col).mul(b));
			}
		}
		return result;
	}
	
	public Matrix<T> add(Matrix<T> b) {
		if (rows != b.rows || cols != b.cols) {
			throw new ArithmeticException(String.format("Unable to add."));
		}
		
		Matrix<T> result = getMatrix(rows, cols, 0, 0);
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				result.set(row, col, (T) result.get(row, col).add(b.get(row, col)));
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
	
	public Matrix<T> dual() {
		Matrix<T> result = getMatrix(rows, cols, 0, 0);
		result = result.standardize();
		
		result = result.getMatrix(rows, cols - rows, 0, rows);
		result = result.transpose();
		
		for (int row = 0; row < result.rows; row++) {
			for (int col = 0; col < result.cols; col++) {
				result.set(row, col, (T) result.get(row, col).mul(-1));
			}
		}
		
		return result.appendIndentity();
	}
	
	public boolean isZero() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (!get(i, j).isZero()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isOne() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (i == j) {
					if (!get(i, j).isOne()) {
						return false;
					}
				}
				else {
					if (!get(i, j).isZero()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
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
