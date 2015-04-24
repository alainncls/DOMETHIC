package fr.epf.medfile.adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.epf.medfile.R;
import fr.epf.medfile.daos.ServiceProvider;
import fr.epf.medfile.daos.ressource.RessourceImageListener;
import fr.epf.medfile.models.Ressource;
import fr.epf.medfile.models.User;
import fr.epf.medfile.utils.Utils;

public class RessourceAdapter extends ArrayAdapter<Ressource> {
    private Ressource ressource;
    private TextView ressourceTitle;
    private TextView ressourceObservations;
    private TextView ressourceAuthor;
    private TextView ressourceDate;
    
    public final static String TAG = RessourceAdapter.class.getSimpleName();

    private RessourceImageListener listener;

    public void setListener(RessourceImageListener listener) {
	this.listener = listener;
    }

    public RessourceAdapter(Context context, List<Ressource> objects) {
	super(context, 0, objects);
    }

	public View getView(int position, View view, ViewGroup parent) {

		ViewHolder holder;
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ressource = getItem(position);
		//if (view == null) {
			holder = new ViewHolder();
			if (ressource.getType().equals("image")) {
				view = inflater.inflate(R.layout.entry_ressources_list_image,
						parent, false);
				holder.ressourceImage = (ImageView) view
						.findViewById(R.id.ressource_image);
				holder.img = ressource.getImg();
				holder.ressourceImage.setOnClickListener(holder);
			} else {
				view = inflater.inflate(R.layout.entry_ressources_list_text,
						parent, false);
			}
			View view2 = view.findViewById(R.id.ressource_text);

			
			ressourceTitle = (TextView) view.findViewById(R.id.ressource_title);
			ressourceObservations = (TextView) view2
					.findViewById(R.id.ressource_observations);
			ressourceAuthor = (TextView) view2
					.findViewById(R.id.ressource_author);
			ressourceDate = (TextView) view.findViewById(R.id.ressource_date);

			view.setTag(holder);
//		} else {
//			holder = (ViewHolder) view.getTag();
//		}

		if (ressource.getType().equals("image")) {
			Log.i(TAG, "Ressource avec Image");
			holder.ressourceImage.setImageBitmap(ressource.getImg());
		}
		displayRessources();
		setBackground(view, position);
		return view;
	}

	private void displayRessources() {
		
		ressourceTitle.setText(ressource.getTitle());
		ressourceObservations.setText(ressource.getText());
		ServiceProvider.getPInstance(this.getContext()).getPatient(
				ressource.getIdPatient());
		User u = ServiceProvider.getUInstance(this.getContext()).getUser(
				ressource.getIdAuthor());
		ressourceAuthor.setText(u.getFirstName() + " " + u.getLastName());
		ressourceDate.setText(Utils.formatFull(ressource.getDate()));
		Log.i(TAG, "Ressource Displayed");
	}

	private void setBackground(View view, int position) {
		int lightBlue = getContext().getResources()
				.getColor(R.color.light_blue);
		int veryLightBlue = getContext().getResources().getColor(
				R.color.very_light_blue);
		int color = (position % 2 == 0) ? veryLightBlue : lightBlue;
		view.setBackgroundColor(color);
	}

	private class ViewHolder implements View.OnClickListener {
		ImageView ressourceImage;
		Bitmap img;

		@Override
		public void onClick(View v) {
			listener.onImageSelect(img);
		}
	}
}
