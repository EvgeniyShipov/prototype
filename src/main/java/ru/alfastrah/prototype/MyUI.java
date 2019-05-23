package ru.alfastrah.prototype;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.ui.*;

@Theme("mytheme")
public class MyUI extends UI {
    static final String REGISTRATION_VIEW = "registration";
    private static final String START_VIEW = "";

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator navigator = new Navigator(this, this);
        navigator.addView(START_VIEW, new StartView(navigator));
        navigator.addView(REGISTRATION_VIEW, new RegistrationView(navigator));
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
