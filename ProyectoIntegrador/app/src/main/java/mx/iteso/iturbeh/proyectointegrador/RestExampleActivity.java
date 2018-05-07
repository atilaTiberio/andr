package mx.iteso.iturbeh.proyectointegrador;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.orm.SugarRecord;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mx.iteso.iturbeh.proyectointegrador.modelo.db.InegiReporteIndicador;
import mx.iteso.iturbeh.proyectointegrador.modelo.rest.InegiResponse;
import mx.iteso.iturbeh.proyectointegrador.restclient.BaseRestCall;

/**
 * Created by iturbeh on 3/27/18.
 */

public class RestExampleActivity extends BaseGraphActivity<List<InegiResponse>> {

    GraphView graphView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        graphView=findViewById(R.id.main_graph);

        List<InegiReporteIndicador> resultado=InegiReporteIndicador.find(InegiReporteIndicador.class,"indicador=?",getString(R.string.INEGI_REPORTE_INDICADOR));

        if(resultado.isEmpty()) {

            Map<String,String> pathParams= new HashMap<>();
            pathParams.put("INEGI_ACCESS_TOKEN",getString(R.string.INEGI_ACCESS_TOKEN));
            String url=UriComponentsBuilder.fromUriString(getString(R.string.INEGI_URL)).buildAndExpand(pathParams).toString();

            BaseRestCall baseRestCall = new BaseRestCall(this,InegiResponse.class);
            baseRestCall.execute(url, "/Data/Serie");
        }
        else{
            graphInit(resultado);

        }


    }

    @Override
    public void onRestSuccess(List<InegiResponse> inegiResponseList) {

        List<InegiReporteIndicador>  inegiReporteIndicadors= new ArrayList<>();
        InegiReporteIndicador inegiReporteIndicador=null;

        for(InegiResponse ite: inegiResponseList){
            inegiReporteIndicador= new InegiReporteIndicador(getString(R.string.INEGI_REPORTE_INDICADOR),ite.getTimePeriod(),ite.getCurrentValue());
            inegiReporteIndicadors.add(inegiReporteIndicador);
        }

        SugarRecord.saveInTx(inegiReporteIndicadors);
        graphInit(inegiReporteIndicadors);

    }

    @Override
    public void onRestFail() {
        Toast.makeText(this,"Erro al ejecutar llamada Rest",Toast.LENGTH_LONG).show();

    }

    public void graphInit(List<InegiReporteIndicador> inegiReporteIndicadores){

        BarGraphSeries<DataPoint> series= new BarGraphSeries<>();
        DataPoint dp=null;
        for(InegiReporteIndicador ite: inegiReporteIndicadores){
            dp= new DataPoint(ite.getAnio(),ite.getValor());
            series.appendData(dp,true,inegiReporteIndicadores.size());
        }


        series.setTitle("Porcentaje poblacional");
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setBackgroundColor(0);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.addSeries(series);

        graphView.setTitle("Usuarios de servicios de salud pública");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Porcentaje");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Año");


    }
}
