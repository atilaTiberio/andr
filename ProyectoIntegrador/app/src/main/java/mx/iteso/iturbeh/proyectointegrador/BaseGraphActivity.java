package mx.iteso.iturbeh.proyectointegrador;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by iturbeh on 3/27/18.
 */

public abstract class BaseGraphActivity<Result> extends BaseActivity {


    public abstract void onRestSuccess(Result result);
    public abstract void onRestFail();

}
