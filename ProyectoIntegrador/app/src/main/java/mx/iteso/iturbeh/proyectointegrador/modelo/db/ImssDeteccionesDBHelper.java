package mx.iteso.iturbeh.proyectointegrador.modelo.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iturbeh on 3/18/18.
 */

public class ImssDeteccionesDBHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "proyecto_integrador.db";
    private static final int DATABASE_VERSION = 1 ;
    public static final String TABLE_NAME = "imss_detecciones";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DETECCION_ENTIDAD = "entidad";
    public static final String COLUMN_DETECCION_PADECIMIENTO = "padecimiento";
    public static final String COLUMN_DETECCION_VALOR = "valor";
    public static final String COLUMN_DETECCION_YEAR = "year";



    public ImssDeteccionesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(
                " CREATE TABLE  %s  (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL,%s INTEGER NOT NULL );",
                TABLE_NAME,
                COLUMN_ID,
                COLUMN_DETECCION_ENTIDAD,
                COLUMN_DETECCION_PADECIMIENTO,
                COLUMN_DETECCION_VALOR,
                COLUMN_DETECCION_YEAR));


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLE_NAME));
        this.onCreate(db);
    }

    public void addDeteccion(ImssDeteccionModel imssDeteccionesModel){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_DETECCION_ENTIDAD,imssDeteccionesModel.getEntidad());
        values.put(COLUMN_DETECCION_PADECIMIENTO,imssDeteccionesModel.getPadecimiento());
        values.put(COLUMN_DETECCION_VALOR,imssDeteccionesModel.getValor());
        values.put(COLUMN_DETECCION_YEAR,imssDeteccionesModel.getYear());
        db.insert(TABLE_NAME,null,values);
    }

    public List<ImssDeteccionModel> getAllDetecciones(){

        SQLiteDatabase db= this.getWritableDatabase();
        List<ImssDeteccionModel> imssDeteccionModels=new ArrayList<>();

        Cursor cursor=db.rawQuery(String.format("SELECT * FROM %s ORDER BY _id asc",TABLE_NAME),null);
        ImssDeteccionModel  imssDeteccionModel;

        if(cursor.moveToFirst()){
            do{
                imssDeteccionModel= new ImssDeteccionModel();
                imssDeteccionModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                imssDeteccionModel.setEntidad(cursor.getString(cursor.getColumnIndex(COLUMN_DETECCION_ENTIDAD)));
                imssDeteccionModel.setPadecimiento(cursor.getString(cursor.getColumnIndex(COLUMN_DETECCION_PADECIMIENTO)));
                imssDeteccionModel.setValor(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_VALOR)));
                imssDeteccionModel.setYear(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_YEAR)));
                imssDeteccionModels.add(imssDeteccionModel);

            }while(cursor.moveToNext());
        }
        return imssDeteccionModels;

    }

    public ImssDeteccionModel getDeteccion(Integer id){

        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(String.format("SELECT * FROM %s WHERE _id=%d",TABLE_NAME,id),null);
        ImssDeteccionModel imssDeteccionModel=null;
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            
            imssDeteccionModel= new ImssDeteccionModel();
            imssDeteccionModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            imssDeteccionModel.setEntidad(cursor.getString(cursor.getColumnIndex(COLUMN_DETECCION_ENTIDAD)));
            imssDeteccionModel.setPadecimiento(cursor.getString(cursor.getColumnIndex(COLUMN_DETECCION_PADECIMIENTO)));
            imssDeteccionModel.setValor(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_VALOR)));
            imssDeteccionModel.setYear(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_YEAR)));

        }
        return imssDeteccionModel;
    }

    public void deleteDeteccion(Long id){

        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL(String.format("DELETE FROM %s where %s=%d",TABLE_NAME,COLUMN_ID,id));
    }

    public void updateProducto(Long id,ImssDeteccionModel imssDeteccionModel){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_DETECCION_ENTIDAD,imssDeteccionModel.getEntidad());
        values.put(COLUMN_DETECCION_PADECIMIENTO,imssDeteccionModel.getPadecimiento());
        values.put(COLUMN_DETECCION_VALOR,imssDeteccionModel.getValor());
        values.put(COLUMN_DETECCION_YEAR,imssDeteccionModel.getYear());
        db.update(TABLE_NAME,values,String.format("%s=?",COLUMN_ID),new String[]{String.valueOf(id)});

    }

    public void deleteDetecciones(){
        SQLiteDatabase db= this.getWritableDatabase();
        db.execSQL(String.format("DELETE FROM %s",TABLE_NAME));

    }


    public List<ImssDeteccionModel> groupByPadecimiento(){
        String query=String.format("SELECT padecimiento,sum(valor) as valor FROM %s GROUP BY padecimiento",TABLE_NAME);

        SQLiteDatabase db= this.getWritableDatabase();
        List<ImssDeteccionModel> imssDeteccionModels=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);

        ImssDeteccionModel  imssDeteccionModel;

        if(cursor.moveToFirst()){
            do{
                imssDeteccionModel= new ImssDeteccionModel();
                imssDeteccionModel.setPadecimiento(cursor.getString(cursor.getColumnIndex(COLUMN_DETECCION_PADECIMIENTO)));
                imssDeteccionModel.setValor(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_VALOR)));
                imssDeteccionModels.add(imssDeteccionModel);
            }while(cursor.moveToNext());
        }
        return imssDeteccionModels;

    }

    public List<ImssDeteccionModel> groupByPadecimiento(String estadoActual){
        String query=String.format("SELECT padecimiento,sum(valor) as valor FROM %s WHERE entidad='%s' GROUP BY padecimiento",TABLE_NAME,estadoActual);

        SQLiteDatabase db= this.getWritableDatabase();
        List<ImssDeteccionModel> imssDeteccionModels=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);

        ImssDeteccionModel  imssDeteccionModel;

        if(cursor.moveToFirst()){
            do{
                imssDeteccionModel= new ImssDeteccionModel();
                imssDeteccionModel.setPadecimiento(cursor.getString(cursor.getColumnIndex(COLUMN_DETECCION_PADECIMIENTO)));
                imssDeteccionModel.setValor(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_VALOR)));
                imssDeteccionModels.add(imssDeteccionModel);
            }while(cursor.moveToNext());
        }
        return imssDeteccionModels;

    }

    public List<ImssDeteccionModel> groupByYearWherePadecimiento(String padecimientoValue){
        String query=String.format("SELECT year,sum(valor) as valor FROM %s WHERE padecimiento='%s' GROUP BY year ",TABLE_NAME,padecimientoValue);

        SQLiteDatabase db= this.getWritableDatabase();
        List<ImssDeteccionModel> imssDeteccionModels=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);
        ImssDeteccionModel  imssDeteccionModel;

        if(cursor.moveToFirst()){
            do{
                imssDeteccionModel= new ImssDeteccionModel();
                imssDeteccionModel.setYear(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_YEAR)));
                imssDeteccionModel.setValor(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_VALOR)));
                imssDeteccionModels.add(imssDeteccionModel);
            }while(cursor.moveToNext());
        }
        return imssDeteccionModels;
    }

    public List<ImssDeteccionModel> groupByYearWherePadecimiento(String padecimientoValue,String estadoActual){

        String query=String.format("SELECT year,sum(valor) as valor FROM %s WHERE padecimiento='%s' and entidad='%s' GROUP BY year ",TABLE_NAME,padecimientoValue,estadoActual);

        SQLiteDatabase db= this.getWritableDatabase();
        List<ImssDeteccionModel> imssDeteccionModels=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);
        ImssDeteccionModel  imssDeteccionModel;

        if(cursor.moveToFirst()){
            do{
                imssDeteccionModel= new ImssDeteccionModel();
                imssDeteccionModel.setYear(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_YEAR)));
                imssDeteccionModel.setValor(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_VALOR)));
                imssDeteccionModels.add(imssDeteccionModel);
            }while(cursor.moveToNext());
        }
        return imssDeteccionModels;
    }



    public List<ImssDeteccionModel> groupByEntidadWherePadecimientoAndYear(String padecimientoValue,Integer year){
        String query=String.format("SELECT entidad,sum(valor) as valor FROM %s WHERE padecimiento='%s' AND year=%d GROUP BY entidad ",TABLE_NAME,padecimientoValue,year);

        SQLiteDatabase db= this.getWritableDatabase();
        List<ImssDeteccionModel> imssDeteccionModels=new ArrayList<>();
        Cursor cursor=db.rawQuery(query,null);

        ImssDeteccionModel  imssDeteccionModel;

        if(cursor.moveToFirst()){
            do{
                imssDeteccionModel= new ImssDeteccionModel();
                imssDeteccionModel.setEntidad(cursor.getString(cursor.getColumnIndex(COLUMN_DETECCION_ENTIDAD)));
                imssDeteccionModel.setValor(cursor.getInt(cursor.getColumnIndex(COLUMN_DETECCION_VALOR)));
                imssDeteccionModels.add(imssDeteccionModel);
            }while(cursor.moveToNext());
        }
        return imssDeteccionModels;
    }




}
