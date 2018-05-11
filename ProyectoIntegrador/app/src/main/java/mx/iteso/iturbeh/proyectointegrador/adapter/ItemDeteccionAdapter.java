package mx.iteso.iturbeh.proyectointegrador.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import mx.iteso.iturbeh.proyectointegrador.R;
import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionModel;

/**
 * Created by iturbeh on 5/7/18.
 */

public class ItemDeteccionAdapter  extends ArrayAdapter<ImssDeteccionModel> {


    private List<ImssDeteccionModel> imssDeteccionModels;
    private Activity activity;
    private Class<? extends Activity> nextActivity;

    public ItemDeteccionAdapter(Activity activity, Class<? extends Activity> nextActivity, int r,List<ImssDeteccionModel> imssDeteccionModels) {

        super(activity, r, imssDeteccionModels);
        this.activity = activity;
        this.imssDeteccionModels = imssDeteccionModels;
        this.nextActivity=nextActivity;
    }

    @Override
    public View getDropDownView(int p, View view, ViewGroup viewGroup) {
        return getView(p, view, viewGroup);
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = activity.getLayoutInflater();
        final View item = inflater.inflate(R.layout.item_main_detecciones,
                viewGroup, false);

        ImageView padecimientoLogo = (ImageView) item.findViewById(R.id.padecimiento_logo);
        TextView padecimientoNombre = (TextView) item.findViewById(R.id.padecimiento_nombre);
        TextView padecimientoValor = (TextView) item.findViewById(R.id.padecimiento_valor);
        String pattern = "###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);


        padecimientoNombre.setText(imssDeteccionModels.get(position).getPadecimiento());
        padecimientoValor.setText(decimalFormat.format(imssDeteccionModels.get(position).getValor()));
        padecimientoLogo.setImageResource(R.drawable.ic_launcher_background);



        RelativeLayout rl_item_auto = (RelativeLayout) item.findViewById(R.id.rl_item_deteccion);

        rl_item_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, nextActivity);
                intent.putExtra("padecimiento", imssDeteccionModels.get(position).getPadecimiento());
                activity.startActivity(intent);
            }
        });


        return item;
    }


}
