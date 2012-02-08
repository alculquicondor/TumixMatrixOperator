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

import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tumix.MO.R;
import com.tumix.MO.objs.Matrix;

public class OperateMatrizes extends Activity {
	private TextView operation;
	private Button result, operator1, operator2;
	private LinearLayout matrixresultview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operatematrizes);
		// Recibiendo datos (la operacion a realizar) del intent
		Bundle entrada = getIntent().getExtras();
		operation = (TextView) findViewById(R.id.operation);
		operation.setText(entrada.getString("Operation").toString());
		result = (Button) findViewById(R.id.result_matrix_button);
		operator1 = (Button) findViewById(R.id.operator_matrix_1_button);
		operator2 = (Button) findViewById(R.id.operator_matrix_2_button);
		matrixresultview = (LinearLayout) findViewById(R.id.resultmatrix);
	}

	// Llenando el menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.whileoperating, menu);
		return true;
	}

	// Hacer al cliquear opcion de menu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_editmatrizes:
			Intent llamada = new Intent();
			llamada.setClass(this, Matrizes.class);
			startActivity(llamada);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onSelectMatrixClick(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.SelectMatrix);
		final Button button = (Button) view;
		final String[] matrizes = getResources().getStringArray(
				R.array.matrizes_array);
		builder.setItems(matrizes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				button.setText(matrizes[item]);
			}
		});
		builder.show();
	}

	public void onCalculateClick(View view) {
		if (operator1.getText().toString() == ""
				|| operator2.getText().toString() == "") {
			Toast.makeText(getBaseContext(),
					R.string.Error_SelectMatrizesOperator, Toast.LENGTH_LONG)
					.show();
			return;
		}
		char thisoperation = operation.getText().toString().charAt(0);
		Matrix op1, op2, ans = null;
		int idop1, idop2;
		idop1 = operator1.getText().toString().charAt(0) - 'A';
		idop2 = operator2.getText().toString().charAt(0) - 'A';
		op1 = Main.matrizes[idop1];
		op2 = Main.matrizes[idop2];
		if (thisoperation == '+')
			ans = Matrix.sumofMatrizes(op1, op2);
		else if (thisoperation == '-')
			ans = Matrix.restofMatrizes(op1, op2);
		else if (thisoperation == 'x')
			ans = Matrix.productofMatrizes(op1, op2);
		if (ans == null) {
			Toast.makeText(getBaseContext(),
					R.string.Error_SizesImcompatibility, Toast.LENGTH_LONG)
					.show();
			return;
		}
		matrixresultview.removeAllViews();
		for (int i = 0; i < op1.getRows(); i++) {
			LinearLayout tmp = new LinearLayout(this);
			for (int j = 0; j < op1.getColumns(); j++) {
				TextView tt = new TextView(this);
				DecimalFormat df = new DecimalFormat("#0.####");
				if (ans.getElement(i, j) >= 1e7)
					df.applyPattern("#0.###E0");
				tt.setText(df.format(ans.getElement(i, j)));
				tt.setWidth(100);
				tmp.addView(tt);
			}
			matrixresultview.addView(tmp);
		}
		if (result.getText().toString() != "") {
			int idans = result.getText().toString().charAt(0) - 'A';
			Main.matrizes[idans] = ans;
			Toast.makeText(getBaseContext(),
					R.string.Success_MatrixActualization, Toast.LENGTH_LONG)
					.show();
		} else
			Toast.makeText(getBaseContext(),
					R.string.Note_ResultingMatrixnotSaved, Toast.LENGTH_LONG)
					.show();
	}
}
