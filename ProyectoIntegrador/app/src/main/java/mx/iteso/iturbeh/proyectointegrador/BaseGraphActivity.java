package mx.iteso.iturbeh.proyectointegrador;

import android.app.Activity;

/**
 * Created by iturbeh on 3/27/18.
 */

public abstract class BaseGraphActivity<Result> extends Activity {


    public abstract void onRestSuccess(Result result);
    public abstract void onRestFail();

}
