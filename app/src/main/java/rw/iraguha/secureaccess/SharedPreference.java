package rw.iraguha.secureaccess;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by miller on 2/14/18.
 */

public class SharedPreference {
    static SharedPreferences sharedPref;
    static  Context mContext;
    public SharedPreference(Context c) {
        mContext =c;
    }

    public static SharedPreferences getIntance(){
        if(sharedPref == null){
            sharedPref =  mContext.getSharedPreferences("secureaccess",mContext.MODE_PRIVATE);
        }
        return  sharedPref;
    }
}
