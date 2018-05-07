package mx.iteso.iturbeh.proyectointegrador.services;

import com.orm.SugarRecord;

import java.util.Collection;

/**
 * Created by iturbeh on 3/25/18.
 *
 * con base en:
 * https://gist.github.com/cesarferreira/ef70baa8d64f9753b4da
 */

public interface  RestListenerInterface<T> {

    void onSuccess(T object);
    void onFailure(Exception e);
}
