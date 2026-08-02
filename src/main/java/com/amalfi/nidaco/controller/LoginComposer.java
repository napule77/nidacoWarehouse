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


    @Override
    public void doAfterCompose(Window comp) throws Exception {

        super.doAfterCompose(comp);

        if (isAuthenticated()) {
            Executions.sendRedirect("/index.zul");
        }
    }


    @Listen("onClick=#loginBtn")
    public void login() {

        try {

            if (isAuthenticated()) {
                Executions.sendRedirect("/index.zul");
                return;
            }

            currentRequest().login(
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


    private boolean isAuthenticated() {

        return currentRequest().getUserPrincipal() != null;
    }


    private HttpServletRequest currentRequest() {

        return (HttpServletRequest)
                Executions.getCurrent()
                        .getNativeRequest();
    }
}