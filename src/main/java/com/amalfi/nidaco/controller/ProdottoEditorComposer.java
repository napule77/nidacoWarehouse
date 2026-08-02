package com.amalfi.nidaco.controller;

import com.amalfi.nidaco.entity.Prodotto;
import com.amalfi.nidaco.service.ProdottoService;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@Component
public class ProdottoEditorComposer
        extends SelectorComposer<org.zkoss.zk.ui.Component> {


    private final ProdottoService service;


    public ProdottoEditorComposer(
            ProdottoService service) {

        this.service = service;

    }


    @Wire
    Textbox codice;


    @Wire
    Textbox barcode;


    @Wire
    Textbox descrizione;


    @Wire
    Intbox giacenza;


    @Wire
    Doublebox prezzo;


    @Listen("onClick=#btnSalva")
    public void salva() {


        Prodotto p = new Prodotto();


        p.setCodice(
                codice.getValue()
        );


        p.setBarcode(
                barcode.getValue()
        );


        p.setDescrizione(
                descrizione.getValue()
        );


        p.setGiacenza(
                giacenza.getValue()
        );


        service.save(p);


        ((Window) codice
                .getRoot())
                .detach();


    }


}