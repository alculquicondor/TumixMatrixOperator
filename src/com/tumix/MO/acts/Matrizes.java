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

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Matrizes extends ListActivity implements OnItemClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] matrizes = getResources().getStringArray(
				R.array.matrizes_array);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_matrix,
				matrizes));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent llamada = new Intent();
		llamada.setClass(this, FillMatrix.class);
		String matrixid = ((TextView) view).getText().toString();
		llamada.putExtra("MatrixID", matrixid);
		startActivity(llamada);

	}
}
