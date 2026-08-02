package com.amalfi.nidaco.controller;


import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Messagebox;

public abstract class BaseComposer
        extends GenericForwardComposer {


    protected void info(String msg){

        Messagebox.show(
                msg,
                "Informazione",
                Messagebox.OK,
                Messagebox.INFORMATION
        );

    }



    protected void error(String msg){

        Messagebox.show(
                msg,
                "Errore",
                Messagebox.OK,
                Messagebox.ERROR
        );

    }


}