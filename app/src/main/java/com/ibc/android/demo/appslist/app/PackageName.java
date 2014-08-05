package com.ibc.android.demo.appslist.app;

/**
 * Created by Spicycurryman on 8/5/14.
 */
public class PackageName {

    //private variables
    String _packagename;
    int _state;

    // Empty constructor
    public PackageName(){

    }
    // constructor
    public PackageName(String packagename, int state){
        this._packagename = packagename;
        this._state = state;
    }

    // constructor
    public PackageName(int state){
        this._state = state;
    }
    // getting PACKAGENAME
    public String getPACKAGENAME(){
        return this._packagename;
    }

    // setting packagename
    public void setPACKAGENAME(String packagename){
        this._packagename = packagename;
    }

    // getting state
    public int getState(){
        return this._state;
    }

    // setting state
    public void setState(int state){
        this._state = state;
    }


}


