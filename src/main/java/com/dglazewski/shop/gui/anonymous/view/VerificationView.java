package com.dglazewski.shop.gui.anonymous.view;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.service.RegistrationService;
import com.dglazewski.shop.gui.everyone.components.AppLayoutDrawer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "register/verify/:code?", layout = AppLayoutDrawer.class)
@PageTitle("Verification")
public class VerificationView extends VerticalLayout implements HasUrlParameter<String> {
    //SERVICE
    private final RegistrationService registrationService;
    private String verificationCode;

    public VerificationView(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        this.verificationCode = event.getLocation().getQueryParameters().getParameters().get("code").get(0);
        verifyCustomer();
    }

    private void verifyCustomer() {
        DataBaseStatusResponse<User> response = registrationService.verify(verificationCode);
        if (response.getStatus() == Status.USER_VERIFICATION_SUCCESS) {
            RouterLink link = new RouterLink();
            link.add(VaadinIcon.CLIPBOARD_USER.create(),new Span("Login"));
            link.setRoute(LoginView.class);

            add(new H1("Verification success"));
            add(new Paragraph("Click to login"));
            add(link);
        } else if (response.getStatus() == Status.USER_VERIFICATION_FAILURE) {
            RouterLink link = new RouterLink();
            link.add(VaadinIcon.CLIPBOARD_TEXT.create(),new Span("Register"));
            link.setRoute(LoginView.class);

            add(new H1("Verification failure"));
            add(new Paragraph("You are already verified or don't have account. Click to register"));
            add(link);
        }
    }
}