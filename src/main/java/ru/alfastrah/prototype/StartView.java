package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;

import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.Alignment.*;
import static ru.alfastrah.prototype.MyUI.*;

class StartView extends VerticalLayout implements View {

    StartView(Navigator navigator) {
        buildLayout(navigator);
    }

    private void buildLayout(Navigator navigator) {

        setSizeFull();

        HorizontalLayout loginLayout = createField("Логин");
        HorizontalLayout passwordLayout = createField("Пароль");

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button enterButton = new Button("Войти",
                event -> {
                    String value = ((TextField) loginLayout.getComponent(1)).getValue();
                    if ("curator".equals(value)) {
                        navigator.navigateTo(CURATOR_VIEW);
                    } else if ("agent".equals(value)) {
                        navigator.addView(PRIVATE_OFFICE_VIEW, new PrivateOfficeView(navigator, true, DataBase.name));
                        navigator.navigateTo(PRIVATE_OFFICE_VIEW);
                    } else {
                        Notification.show("Введен неверный логин", Notification.Type.ERROR_MESSAGE);
                    }
                });
        enterButton.setWidth("80px");
        Button registrationButton = new Button("Зарегистрироваться",
                event -> navigator.navigateTo(REGISTRATION_VIEW));
        buttonsLayout.addComponents(enterButton, registrationButton);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(loginLayout, passwordLayout, buttonsLayout);
        verticalLayout.setSizeFull();

        HorizontalLayout welcomeLayout = new HorizontalLayout();

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        Image image = new Image("", new FileResource(new File(basepath + "/WEB-INF/classes/alfa.png")));
        image.setHeight(100, PIXELS);

        Label welcomeLabel = new Label("Добро пожаловать в Альфа-Страхование!");
        welcomeLabel.addStyleName("big-text");

        welcomeLayout.addComponents(image);

        VerticalLayout bottomLayout = new VerticalLayout();

        addComponents(welcomeLayout, verticalLayout, bottomLayout);

        setComponentAlignment(welcomeLayout, MIDDLE_CENTER);
        setComponentAlignment(verticalLayout, MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(loginLayout, BOTTOM_CENTER);
        verticalLayout.setComponentAlignment(passwordLayout, MIDDLE_CENTER);
        verticalLayout.setComponentAlignment(buttonsLayout, TOP_CENTER);

//        setComponentAlignment(goButton, TOP_CENTER);
//        setComponentAlignment(image, MIDDLE_CENTER);
//        setExpandRatio(welcomeLabel, 0.4f);
        setExpandRatio(welcomeLayout, 0.2f);
        setExpandRatio(verticalLayout, 0.4f);
        setExpandRatio(bottomLayout, 0.4f);
    }

    private HorizontalLayout createField(String fieldName) {

        HorizontalLayout layout = new HorizontalLayout();
        Label loginLabel = new Label(fieldName);
        loginLabel.setWidth("80px");
        TextField loginField = new TextField();
        layout.addComponents(loginLabel, loginField);

        return layout;
    }

}
