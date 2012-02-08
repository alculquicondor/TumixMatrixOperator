/*
 * This file is part of TumixMatrixOperator.
 * TumixMatrixOperator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TumixMatrixOperator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TumixMatrixOperator.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tumix.MO.objs;

public class Matrix {
	private int columns;
	private int rows;
	private double[][] matrix;

	public Matrix(int rows, int columns) {
		this.columns = columns;
		this.rows = rows;
		matrix = new double[rows][];
		for (int i = 0; i < rows; i++)
			matrix[i] = new double[columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public double getElement(int i, int j) {
		return matrix[i][j];
	}

	public void modifyElement(int i, int j, double value) {
		matrix[i][j] = value;
	}

	public static Matrix sumofMatrizes(Matrix A, Matrix B) {
		if (A.getRows() == B.getRows() && A.getColumns() == B.getColumns()) {
			Matrix ans = new Matrix(A.getRows(), A.getColumns());
			for (int i = 0; i < A.getRows(); i++)
				for (int j = 0; j < A.getColumns(); j++)
					ans.modifyElement(i, j,
							A.getElement(i, j) + B.getElement(i, j));
			return ans;
		} else
			return null;
	}

	public static Matrix restofMatrizes(Matrix A, Matrix B) {
		if (A.getRows() == B.getRows() && A.getColumns() == B.getColumns()) {
			Matrix ans = new Matrix(A.getRows(), A.getColumns());
			for (int i = 0; i < A.getRows(); i++)
				for (int j = 0; j < A.getColumns(); j++)
					ans.modifyElement(i, j,
							A.getElement(i, j) - B.getElement(i, j));
			return ans;
		} else
			return null;
	}

	public static Matrix productofMatrizes(Matrix A, Matrix B) {
		if (A.getColumns() == B.getRows()) {
			Matrix ans = new Matrix(A.getRows(), B.getColumns());
			for (int i = 0; i < A.getRows(); i++)
				for (int j = 0; j < B.getColumns(); j++) {
					double elementvalue = 0;
					for (int k = 0; k < A.getColumns(); k++)
						elementvalue += A.getElement(i, k) * B.getElement(k, j);
					ans.modifyElement(i, j, elementvalue);
				}
			return ans;
		} else
			return null;
	}

	public SquareMatrix toSquare() {
		if (this.getColumns() != this.getRows()) {
			return null;
		}
		SquareMatrix ans = new SquareMatrix(this.getColumns());
		for (int i = 0; i < ans.getSize(); i++)
			for (int j = 0; j < ans.getSize(); j++)
				ans.modifyElement(i, j, this.getElement(i, j));
		return ans;
	}
}