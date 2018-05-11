package mx.iteso.iturbeh.proyectointegrador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;
import java.util.List;

import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionModel;
import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionesDBHelper;
import mx.iteso.iturbeh.proyectointegrador.GraphPadecimientoYearEntidadActivity;
/**
 * Created by iturbeh on 5/8/18.
 */

public class GraphPadecimientoYearActivity extends BaseActivity {

    GraphView graphView;
    ImssDeteccionesDBHelper imssDeteccionesDBHelper;
    String estadoActual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphpadecimiento_year);
        imssDeteccionesDBHelper= new ImssDeteccionesDBHelper(this);
        graphView=findViewById(R.id.graph_padecimiento_year);

        estadoActual=proyectoIntegradorSharedPreferences.getKey("estadoActual");

        Bundle vals=getIntent().getExtras();

        if(vals!=null){
            String padecimiento=vals.getString("padecimiento");
            graphInit(padecimiento);
        }
    }


    private void graphInit(String padecimiento){

        List<ImssDeteccionModel> padecimientosYear=imssDeteccionesDBHelper.groupByYearWherePadecimiento(padecimiento);


        if(estadoActual!=null &&!estadoActual.isEmpty()){
            padecimientosYear=imssDeteccionesDBHelper.groupByYearWherePadecimiento(padecimiento,estadoActual);
        }
        else{
            padecimientosYear=imssDeteccionesDBHelper.groupByYearWherePadecimiento(padecimiento);
        }


        BarGraphSeries<DataPoint> series= new BarGraphSeries<>();
        DataPoint dp=null;

        for(ImssDeteccionModel ite: padecimientosYear){
            dp= new DataPoint(ite.getYear(),ite.getValor());
            series.appendData(dp,false,padecimientosYear.size()+2);
        }

        series.setTitle("Padecimiento por año ");
        series.setSpacing(50);
        String pattern = "###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);


            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    //    Drill down functionality
                    if(estadoActual==null || estadoActual.isEmpty()) {
                        Intent intent = new Intent(GraphPadecimientoYearActivity.this, GraphPadecimientoYearEntidadActivity.class);
                        intent.putExtra("padecimiento", padecimiento);
                        intent.putExtra("year", ((Double) dataPoint.getX()).intValue());
                        GraphPadecimientoYearActivity.this.startActivity(intent);
                    }
                    else{
                        String message=String.format("Cantidad %s",decimalFormat.format(dataPoint.getY()));
                        Toast.makeText(graphView.getContext(), message, Toast.LENGTH_LONG).show();
                    }

                }
            });


        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setBackgroundColor(0);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.addSeries(series);

        graphView.setTitle(padecimiento);
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Cantidad");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Año");

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(10);
        graphView.getViewport().setScrollable(true);





    }
}
