package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;

import java.io.File;

import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.Alignment.MIDDLE_CENTER;
import static com.vaadin.ui.Alignment.TOP_CENTER;
import static ru.alfastrah.prototype.MyUI.REGISTRATION_VIEW;

class StartView extends VerticalLayout implements View {
    StartView(Navigator navigator) {
        buildLayout(navigator);
    }

    private void buildLayout(Navigator navigator) {
        setSizeFull();

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeFull();

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        Image image = new Image("", new FileResource(new File(basepath + "/WEB-INF/classes/alfa.png")));
        image.setHeight(100, PIXELS);

        Label welcomeLabel = new Label("Добро пожаловать в Альфа-Страхование!");
        welcomeLabel.addStyleName("big-text");

        Button goButton = new Button("Начать работу",
                event -> navigator.navigateTo(REGISTRATION_VIEW));

        addComponents(welcomeLabel, goButton, image);
        setComponentAlignment(welcomeLabel, MIDDLE_CENTER);
        setComponentAlignment(goButton, TOP_CENTER);
        setComponentAlignment(image, MIDDLE_CENTER);
        setExpandRatio(welcomeLabel, 0.4f);
        setExpandRatio(goButton, 0.4f);
        setExpandRatio(image, 0.2f);

        addComponent(mainLayout);
    }
}
