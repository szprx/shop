package com.dglazewski.shop.gui.view.anonymous;

import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/verify/:code?", layout = AppLayoutDrawer.class)
@PageTitle("Verification")
@AnonymousAllowed
public class VerificationView extends VerticalLayout implements BeforeEnterObserver {
    private String verificationCode = "code";

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        verificationCode = beforeEnterEvent.getRouteParameters().get("code").orElse("error");
    }
//cos nie dala moje drukowanie verify code ale to do zrobienia
    public VerificationView() {
        add(new Paragraph("cos sie udalo dla "));
        add(new Paragraph(verificationCode + " ale kod cos zjebalo"));
    }
}
