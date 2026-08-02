package com.amalfi.nidaco.controller;


import com.amalfi.nidaco.entity.Prodotto;
import com.amalfi.nidaco.service.ProdottoService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

import java.util.List;
@org.springframework.stereotype.Component
public class ProdottoComposer
        extends SelectorComposer<Component> {



    private final ProdottoService service;



    public ProdottoComposer(
            ProdottoService service){

        this.service=service;

    }




    @Wire
    private Listbox listaProdotti;


    @Wire
    private Textbox txtRicerca;




    @Override
    public void doAfterCompose(
            Component comp)
            throws Exception{


        super.doAfterCompose(comp);

        carica();

    }





    private void carica(){


        String filtro = null;

        if(txtRicerca != null){
            filtro = txtRicerca.getValue();
        }


        List<Prodotto> lista =
                service.search(filtro);

        listaProdotti
                .setModel(
                        new ListModelList<>(lista)
                );

    }




    @Listen("onChanging=#txtRicerca")
    public void ricerca(){

        carica();

    }





    @Listen("onClick=#btnNuovo")
    public void nuovo(){


        Window win =
                (Window) Executions.createComponents(
                        "/prodottoEdit.zul",
                        null,
                        null);


        win.doModal();

    }





    @Listen("onClick=#btnAggiorna")
    public void aggiorna(){

        carica();

    }





    @Listen("onClick=#btnElimina")
    public void elimina(){



        Listitem item =
                listaProdotti
                        .getSelectedItem();



        if(item==null){

            return;

        }



        Prodotto p =
                item.getValue();



        service.delete(
                p.getId()
        );


        carica();

    }



}