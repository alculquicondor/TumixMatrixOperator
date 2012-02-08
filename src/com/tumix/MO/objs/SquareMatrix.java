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

public class SquareMatrix extends Matrix {
	private SquareMatrix LU;
	private int[] P;
	private int detP;

	public SquareMatrix(int size) {
		super(size, size);
		LU = null;
		P = null;
	}

	public int getSize() {
		return super.getRows();
	}

	public static SquareMatrix identityMatrix(int size) {
		SquareMatrix ans = new SquareMatrix(size);
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				if (i == j)
					ans.modifyElement(i, j, 1);
				else
					ans.modifyElement(i, j, 0);
			}
		return ans;
	}

	public SquareMatrix matrixExponentiation(int n) {
		if (n == 0)
			return identityMatrix(this.getSize());
		if (n == 1)
			return this.toSquare();
		SquareMatrix temp = this.matrixExponentiation(n / 2);
		temp = productofMatrizes(temp, temp).toSquare();
		if (n % 2 == 1)
			return productofMatrizes(temp, this).toSquare();
		return temp;
	}

	private double abs(double a) {
		if (a >= 0)
			return a;
		else
			return -a;
	}

	private int LUPdecomposition() {
		if (LU != null)
			return detP;
		LU = this.toSquare();
		// inicializando []P, matriz de permutacion
		P = new int[LU.getSize()];
		for (int i = 0; i < LU.getSize(); i++)
			P[i] = i;
		detP = 1;
		for (int k = 0; k < LU.getSize(); k++) {
			double p = 0;
			int nk = k;
			for (int i = k; i < LU.getSize(); i++)
				if (abs(LU.getElement(i, k)) > p) {
					p = abs(LU.getElement(i, k));
					nk = i;
				}
			if (p == 0)
				return 0; // Error: A es singular
			if (nk != k) {
				detP *= -1;
				int temp = P[k];
				P[k] = P[nk];
				P[nk] = temp;
				for (int i = 0; i < LU.getSize(); i++) {
					double tmp = LU.getElement(k, i);
					LU.modifyElement(k, i, LU.getElement(nk, i));
					LU.modifyElement(nk, i, tmp);
				}
			}
			for (int i = k + 1; i < LU.getSize(); i++) {
				for (int j = k + 1; j < LU.getSize(); j++) {
					double value = LU.getElement(i, j) - LU.getElement(i, k)
							* LU.getElement(k, j) / LU.getElement(k, k);
					LU.modifyElement(i, j, value);
				}
				double value = LU.getElement(i, k) / LU.getElement(k, k);
				LU.modifyElement(i, k, value);
			}
		}
		return detP;
	}

	public double determinant() {
		double det = 1;
		det *= this.LUPdecomposition();
		if (det == 0)
			return 0;
		for (int i = 0; i < LU.getSize(); i++)
			det *= LU.getElement(i, i);
		return det;
	}

	public Matrix solver(Matrix B) {
		if (B.getRows() != this.getSize())
			return null;
		if (this.LUPdecomposition() == 0)
			return null;
		Matrix Bp = new Matrix(B.getRows(), B.getColumns());
		for (int i = 0; i < this.getSize(); i++)
			for (int j = 0; j < B.getColumns(); j++)
				Bp.modifyElement(i, j, B.getElement(this.P[i], j));
		Matrix Y = new Matrix(B.getRows(), B.getColumns());
		Matrix X = new Matrix(B.getRows(), B.getColumns());
		for (int k = 0; k < B.getColumns(); k++) {

			for (int i = 0; i < this.getSize(); i++) {
				double sus = 0;
				for (int j = 0; j < i; j++)
					sus += LU.getElement(i, j) * Y.getElement(j, k);
				Y.modifyElement(i, k, Bp.getElement(i, k) - sus);
			}
			for (int i = this.getSize() - 1; i >= 0; i--) {
				double sus = 0;
				for (int j = i + 1; j < this.getSize(); j++)
					sus += LU.getElement(i, j) * X.getElement(j, k);
				double value = (Y.getElement(i, k) - sus) / LU.getElement(i, i);
				X.modifyElement(i, k, value);
			}
		}
		return X;
	}

	public SquareMatrix inverse() {
		Matrix ans = this.solver((Matrix) identityMatrix(this.getSize()));
		if (ans == null)
			return null;
		return ans.toSquare();
	}
}
