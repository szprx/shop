package com.dglazewski.shop.gui.view.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.lumo.Lumo;


public class DarkTheme extends VerticalLayout {

    public DarkTheme() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        themeList.remove(Lumo.LIGHT);
        themeList.add(Lumo.DARK);
    }


}
