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

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tumix.MO.R;
import com.tumix.MO.objs.Matrix;

public class FillMatrix extends Activity {
	private TextView matrixid;
	private LinearLayout matrixform;
	private EditText matrixrows, matrixcolumns;
	private EditText[][] matrixfilling;
	private int id, nrows, ncolumns;

	private boolean cancontinue;

	// private boolean wasmodified

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fillmatrix);

		Bundle entrada = getIntent().getExtras();
		matrixid = (TextView) findViewById(R.id.matrix_id);
		matrixid.setText(entrada.getString("MatrixID"));
		id = entrada.getString("MatrixID").charAt(0) - 'A';
		matrixform = (LinearLayout) findViewById(R.id.matrix_form);
		matrixrows = (EditText) findViewById(R.id.nrows);
		matrixcolumns = (EditText) findViewById(R.id.ncolumns);
		if (Main.matrizes[id].getRows() != 0)
			firstFill();
		// wasmodified=false
	}

	private void firstFill() {
		matrixrows.setText(Main.matrizes[id].getRows() + "");
		matrixcolumns.setText(Main.matrizes[id].getColumns() + "");
		nrows = Main.matrizes[id].getRows();
		ncolumns = Main.matrizes[id].getColumns();
		matrixfilling = new EditText[nrows][];
		EditText tmp = (EditText) findViewById(R.id.doubleinputbox);
		for (int i = 0; i < nrows; i++) {
			matrixfilling[i] = new EditText[ncolumns];
			LinearLayout row = new LinearLayout(this);
			for (int j = 0; j < ncolumns; j++) {
				matrixfilling[i][j] = new EditText(this);
				matrixfilling[i][j].setText(Main.matrizes[id].getElement(i, j)
						+ "");
				matrixfilling[i][j].setWidth(100);
				matrixfilling[i][j].setKeyListener(tmp.getKeyListener());
				row.addView(matrixfilling[i][j]);
			}
			matrixform.addView(row);
		}
	}

	public void OnGenerateMatrixClick(View button) {
		nrows = Integer.parseInt(matrixrows.getText().toString());
		ncolumns = Integer.parseInt(matrixcolumns.getText().toString());
		if (nrows > 20 || ncolumns > 20) {
			Toast.makeText(getBaseContext(), R.string.Error_LargeMatrix,
					Toast.LENGTH_LONG).show();
			return;
		}
		matrixform.removeAllViews();
		matrixfilling = new EditText[nrows][];
		EditText tmp = (EditText) findViewById(R.id.doubleinputbox);
		for (int i = 0; i < nrows; i++) {
			matrixfilling[i] = new EditText[ncolumns];
			LinearLayout row = new LinearLayout(this);
			for (int j = 0; j < ncolumns; j++) {
				matrixfilling[i][j] = new EditText(this);
				// matrixfilling[i][j].setText("0")
				matrixfilling[i][j].setWidth(100);
				matrixfilling[i][j].setKeyListener(tmp.getKeyListener());
				row.addView(matrixfilling[i][j]);
			}
			matrixform.addView(row);
		}
		// wasmodified=true
	}

	public boolean isDouble(String cadena) {
		try {
			Double.parseDouble(cadena);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public void OnOkClick(View button) {
		Main.matrizes[id] = new Matrix(nrows, ncolumns);
		cancontinue = true;
		for (int i = 0; i < nrows; i++)
			for (int j = 0; j < ncolumns; j++) {
				String cadena = matrixfilling[i][j].getText().toString();
				if (!isDouble(cadena)) {
					matrixfilling[i][j].setText("0");
					/*
					 * final int ii = i, jj = j; AlertDialog alertdialog = new
					 * AlertDialog.Builder(this).create();
					 * alertdialog.setMessage
					 * ((string)R.string.Error_invalidstring);
					 * builder.setMessage(R.string.Error_invalidstring)
					 * .setCancelable(false) .setPositiveButton("Yes", new
					 * DialogInterface.OnClickListener() { public void onClick(
					 * DialogInterface dialog, int id) {
					 * FillMatrix.this.matrixfilling[ii][jj] .setText("0"); } })
					 * .setNegativeButton(R.string.no, new
					 * DialogInterface.OnClickListener() { public void onClick(
					 * DialogInterface dialog, int id) {
					 * FillMatrix.this.cancontinue = false; } }); AlertDialog
					 * alert=builder.create(); alert.show();
					 */
					if (!cancontinue)
						return;
				}
			}
		for (int i = 0; i < nrows; i++)
			for (int j = 0; j < ncolumns; j++) {
				String cadena = matrixfilling[i][j].getText().toString();
				double value;
				value = Double.parseDouble(cadena);
				Main.matrizes[id].modifyElement(i, j, value);
			}
		Toast.makeText(getBaseContext(), R.string.Success_MatrixActualization,
				Toast.LENGTH_LONG).show();
		finish();
	}
}
