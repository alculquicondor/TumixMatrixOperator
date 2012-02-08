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
import android.widget.TextView;
import android.widget.Toast;

import com.tumix.MO.R;
import com.tumix.MO.objs.SquareMatrix;

public class OperateDeterminant extends Activity {
	private Button determinatingmatrix;
	private TextView determinant;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.matrixdeterminant);
		determinatingmatrix = (Button) findViewById(R.id.determinate_matrix);
		determinant = (TextView) findViewById(R.id.text_determinant);
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
		if (determinatingmatrix.getText() == "") {
			Toast.makeText(getBaseContext(), R.string.Error_SelectMatrix,
					Toast.LENGTH_LONG).show();
			return;
		}
		SquareMatrix thisdeterminantmatrix = null;
		int iddeterminantmatrix;
		iddeterminantmatrix = determinatingmatrix.getText().toString()
				.charAt(0) - 'A';
		thisdeterminantmatrix = Main.matrizes[iddeterminantmatrix].toSquare();
		if (thisdeterminantmatrix == null) {
			Toast.makeText(getBaseContext(), R.string.Error_MatrixNotSquare,
					Toast.LENGTH_LONG).show();
			return;
		}
		double ans = thisdeterminantmatrix.determinant();
		DecimalFormat df = new DecimalFormat("#0.####");
		if (ans >= 1e7)
			df.applyPattern("#0.###E0");
		determinant.setText(df.format(ans));
	}
}
