package fr.epf.medfile.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.epf.medfile.R;


public class CategoryAdapter extends ArrayAdapter<String> {

    private int numCategory;
    private TextView typeName;
    
	public CategoryAdapter(Context context, List<String> objects, int numCategory) {
		super(context, 0, objects);
		this.numCategory = numCategory;
		Log.i("Adapter Category", "Position Selected: "+numCategory);
    }
	public View getView(int position, View view, ViewGroup parent ) {
		
		int color = R.drawable.panel_list_dark;
		int colorSelected = R.drawable.panel_list_selected;

		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.entry_categories_list, parent, false);
		}
		if(position==numCategory){
			numCategory=-1;
			view.setSelected(true);
		}
		String category = getItem(position);

		typeName = (TextView) view.findViewById(R.id.type_name);
		typeName.setText(category);

		if(view.isSelected()){
			view.setBackgroundResource(colorSelected);
			typeName.setTypeface(null, Typeface.BOLD_ITALIC);

		}else{
			view.setBackgroundResource(color);
			typeName.setTypeface(null, Typeface.NORMAL);
		}

		return view;
	}
}
