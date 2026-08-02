package com.amalfi.nidaco.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.*;

public class LoginComposer extends SelectorComposer<Window> {


    @Wire
    private Textbox username;

    @Wire
    private Textbox password;


    @Listen("onClick=#loginBtn")
    public void login() {

        HttpServletRequest request =
                (HttpServletRequest)
                        Executions.getCurrent()
                                .getNativeRequest();


        try {

            request.login(
                    username.getValue(),
                    password.getValue()
            );

            Executions.sendRedirect("/index.zul");


        } catch (Exception e) {

            Messagebox.show(
                    "Credenziali errate",
                    "Login",
                    Messagebox.OK,
                    Messagebox.ERROR
            );
        }

    }
}