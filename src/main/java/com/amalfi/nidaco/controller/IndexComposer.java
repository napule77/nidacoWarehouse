package com.amalfi.nidaco.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;


public class IndexComposer extends SelectorComposer<org.zkoss.zk.ui.Component> {


    @Wire
    private Window mainWindow;


    @Wire
    private Label titleLabel;


    @Wire
    private Label messageLabel;


    @Override
    public void doAfterCompose(Component comp) throws Exception {

        super.doAfterCompose(comp);

        titleLabel.setValue("Nidaco Warehouse");

        messageLabel.setValue(
                "ZK 10 + Spring Boot MVC funzionante"
        );
    }

}