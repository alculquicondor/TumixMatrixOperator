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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tumix.MO.R;
import com.tumix.MO.objs.SquareMatrix;

public class OperateExponentiation extends Activity {
	private EditText index;
	private Button result, base;
	private LinearLayout matrixresultview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.operateexponentiation);
		result = (Button) findViewById(R.id.result_matrix_button);
		base = (Button) findViewById(R.id.matrixtoexponentiate_button);
		index = (EditText) findViewById(R.id.exponentiation_index);
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
		if (base.getText() == "") {
			Toast.makeText(getBaseContext(),
					R.string.Error_SelectMatrizesOperator, Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (index.getText().toString() == "") {
			Toast.makeText(getBaseContext(), R.string.Error_putanindex,
					Toast.LENGTH_LONG).show();
			return;
		}
		SquareMatrix baseop, ans = null;
		int idbaseop;
		idbaseop = base.getText().toString().charAt(0) - 'A';
		baseop = Main.matrizes[idbaseop].toSquare();
		if (baseop == null) {
			Toast.makeText(getBaseContext(), R.string.Error_MatrixNotSquare,
					Toast.LENGTH_LONG).show();
			return;
		}
		ans = baseop.matrixExponentiation(Integer.parseInt(index.getText()
				.toString()));
		matrixresultview.removeAllViews();
		for (int i = 0; i < ans.getSize(); i++) {
			LinearLayout tmp = new LinearLayout(this);
			for (int j = 0; j < ans.getSize(); j++) {
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
