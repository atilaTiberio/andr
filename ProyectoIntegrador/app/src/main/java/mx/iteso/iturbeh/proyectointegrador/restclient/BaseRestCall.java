package mx.iteso.iturbeh.proyectointegrador.restclient;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.springframework.web.client.RestTemplate;

import java.util.List;

import mx.iteso.iturbeh.proyectointegrador.BaseGraphActivity;

/**
 * Created by iturbeh on 3/27/18.
 */

public class BaseRestCall  extends AsyncTask<String,Void,List<? extends Object>> {

    /*
        params[0] Url de invocacion
        params[1] filtro del json de respuesta en caso de que las entidades de interes se encuentren anidadas
     */


    Class resultClass;

    BaseGraphActivity baseGraphActivity;
    Exception exception;

    public BaseRestCall(BaseGraphActivity baseGraphActivity, Class resultClass){
        this.baseGraphActivity=baseGraphActivity;
        this.resultClass=resultClass;


    }

    @Override
    protected List<? extends Object> doInBackground(String[] params) {

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper= new ObjectMapper();
        TypeFactory typeFactory= mapper.getTypeFactory();
        JsonNode series=null;

       List<? > reporte=null;

        String url = params[0].toString();


        String response=restTemplate.getForObject(url,String.class);
        try{
                if(params[1]!=null) {
                    series = mapper.readTree(response).at(params[1]);
                }else{
                    series = mapper.readTree(response);
                }

            reporte= mapper.readValue(series.toString(),typeFactory.constructCollectionType(List.class,resultClass));
        }
        catch(Exception e){
            exception=e;
        }

        return reporte;
    }




    @Override
    protected  void onPostExecute(List<? extends Object> result){

        if(exception==null){
            baseGraphActivity.onRestSuccess(result);
        }
        else{
            baseGraphActivity.onRestFail();
        }


    }





}
