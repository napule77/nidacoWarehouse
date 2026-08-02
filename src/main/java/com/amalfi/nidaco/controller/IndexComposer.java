package com.amalfi.nidaco.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;

@org.springframework.stereotype.Component
public class IndexComposer extends SelectorComposer<org.zkoss.zk.ui.Component> {


    @Wire
    private Include contentArea;



    @Override
    public void doAfterCompose(Component comp) throws Exception {

        super.doAfterCompose(comp);

        // pagina iniziale
        caricaPagina("/layout/dashboard.zul");

    }



    /**
     * Carica una pagina nell'area centrale
     */
    public void caricaPagina(String pagina) {


        contentArea.setSrc(pagina);

    }



    /**
     * Apertura prodotti
     */
    @Listen("onClick=#menuProdotti")
    public void apriProdotti(){

        caricaPagina("/prodotti.zul");

    }



    /**
     * Apertura categorie
     */
    @Listen("onClick=#menuCategorie")
    public void apriCategorie(){

        caricaPagina("/categorie.zul");

    }



    /**
     * Dashboard
     */
    @Listen("onClick=#menuDashboard")
    public void dashboard(){

        caricaPagina("/layout/dashboard.zul");

    }



    /**
     * Logout
     */
    @Listen("onClick=#btnLogout")
    public void logout(){


        Messagebox.show(
                "Chiusura sessione?",
                "Logout",
                Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION,
                event -> {

                    if(event.getName()
                            .equals(Messagebox.ON_YES)){

                        Executions.sendRedirect("/");

                    }

                });


    }

}