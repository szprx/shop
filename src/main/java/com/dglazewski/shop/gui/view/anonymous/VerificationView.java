package com.dglazewski.shop.gui.view.anonymous;

import com.dglazewski.shop.api.database.response.DataBaseStatusResponse;
import com.dglazewski.shop.api.entity.Customer;
import com.dglazewski.shop.api.entity.User;
import com.dglazewski.shop.api.enums.Status;
import com.dglazewski.shop.api.service.CustomerService;
import com.dglazewski.shop.gui.view.components.AppLayoutDrawer;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register/verify/:code?", layout = AppLayoutDrawer.class)
@PageTitle("Verification")
@AnonymousAllowed
public class VerificationView extends VerticalLayout implements HasUrlParameter<String> {
    private String verificationCode;
    private final CustomerService customerService;

    public VerificationView(CustomerService customerService) {
        this.customerService = customerService;
        add(new Paragraph("cos sie udalo dla "));
    }

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter String parameter) {
        this.verificationCode = event.getLocation().getQueryParameters().getParameters().get("code").get(0);
        verifyCustomer();
    }

    private void verifyCustomer() {
        DataBaseStatusResponse<User> response = customerService.verify(verificationCode);
        if (response.getStatus() == Status.USER_VERIFICATION_SUCCESS) {
            add(new Span("Succes"));
        } else if (response.getStatus() == Status.USER_VERIFICATION_FAILURE) {
            add(new Span("Failure"));
        }
    }
}
