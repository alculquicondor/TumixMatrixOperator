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

package com.tumix.MO.acts;

import com.tumix.MO.R;
import com.tumix.MO.objs.Matrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main extends Activity {
	/** Called when the activity is first created. */
	public static Matrix[] matrizes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		matrizes = new Matrix[5];
		for (int i = 0; i < 5; i++)
			matrizes[i] = new Matrix(0, 0);
	}

	// Para editar matrices
	public void onEditClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, Matrizes.class);
		startActivity(llamada);
	}

	// Operaciones disponibles
	public void onAddingClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, OperateMatrizes.class);
		llamada.putExtra("Operation", "+");
		startActivity(llamada);
	}

	public void onSubtractingClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, OperateMatrizes.class);
		llamada.putExtra("Operation", "-");
		startActivity(llamada);
	}

	public void onMultiplicatingClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, OperateMatrizes.class);
		llamada.putExtra("Operation", "x");
		startActivity(llamada);
	}

	public void onExponentiatingClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, OperateExponentiation.class);
		startActivity(llamada);
	}

	public void onDeterminatingClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, OperateDeterminant.class);
		startActivity(llamada);
	}

	public void onSolvingSystemClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, SolveSystem.class);
		startActivity(llamada);
	}

	public void onInvertingClick(View button) {
		Intent llamada = new Intent();
		llamada.setClass(this, InvertMatrix.class);
		startActivity(llamada);
	}
}