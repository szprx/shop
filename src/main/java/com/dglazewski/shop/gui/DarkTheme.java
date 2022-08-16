package com.dglazewski.shop.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("mainRoute")
@CssImport("./styles/styles.css")
public class DarkTheme extends VerticalLayout {

    public DarkTheme() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        themeList.remove(Lumo.LIGHT);
        themeList.add(Lumo.DARK);
    }


}
