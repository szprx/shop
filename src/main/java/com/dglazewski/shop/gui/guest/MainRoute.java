package com.dglazewski.shop.gui.guest;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("ds")
public class MainRoute extends VerticalLayout {

    public MainRoute() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        themeList.remove(Lumo.LIGHT);
        themeList.add(Lumo.DARK);
    }


}
