package mx.iteso.iturbeh.proyectointegrador;

import android.app.Activity;
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
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DecimalFormat;
import java.util.List;

import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionModel;
import mx.iteso.iturbeh.proyectointegrador.modelo.db.ImssDeteccionesDBHelper;

/**
 * Created by iturbeh on 5/8/18.
 */

public class GraphPadecimientoYearEntidadActivity extends Activity {

    GraphView graphView;
    ImssDeteccionesDBHelper imssDeteccionesDBHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphpadecimientoyear_entidad);
        imssDeteccionesDBHelper= new ImssDeteccionesDBHelper(this);
        graphView=findViewById(R.id.graph_padecimientoyear_entidad);
        Bundle vals=getIntent().getExtras();

        if(vals!=null){
            String padecimiento=vals.getString("padecimiento");
            Integer year=vals.getInt("year");

            graphInit(padecimiento,year);
        }
    }


    private void graphInit(String padecimiento,Integer year){

        List<ImssDeteccionModel> padecimientosYear=imssDeteccionesDBHelper.groupByEntidadWherePadecimientoAndYear(padecimiento,year);

        PointsGraphSeries<DataPoint> series= new PointsGraphSeries<>();
        LineGraphSeries<DataPoint> lines= new LineGraphSeries<>();
        DataPoint dp=null;
        int x=0;
        for(ImssDeteccionModel ite: padecimientosYear){
            dp= new DataPoint(x,ite.getValor());
            series.appendData(dp,false,padecimientosYear.size());
            lines.appendData(dp,false,padecimientosYear.size()+2);
            x++;
        }
        String pattern = "###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        series.setTitle(String.format("Maximo %s \nMinimo %s",decimalFormat.format(series.getHighestValueY()),decimalFormat.format(series.getLowestValueY())));

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Integer position= ((Double)dataPoint.getX()).intValue();
                String message=String.format("Entidad %s cantidad %s",padecimientosYear.get(position).getEntidad(),decimalFormat.format(dataPoint.getY()));
                Toast.makeText(graphView.getContext(), message, Toast.LENGTH_LONG).show();


            }
        });
        graphView.getLegendRenderer().setVisible(false);
        graphView.getLegendRenderer().setBackgroundColor(0);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.addSeries(series);

        graphView.setTitle(String.format("Padecimiento %s en %d",padecimiento,year));
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Cantidad");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Entidad");

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(10);
        graphView.getViewport().setScrollable(true);

        graphView.addSeries(lines);


    }
}
