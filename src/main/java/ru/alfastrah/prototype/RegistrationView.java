package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.*;

import static com.vaadin.ui.Alignment.*;

class RegistrationView extends VerticalLayout implements View {
    RegistrationView(Navigator navigator) {
        buildLayout(navigator);
    }

    private void buildLayout(Navigator navigator) {
        setSpacing(true);

        Label label = new Label("Введите данные");

        Button confirmButtom = new Button("Подтвердить", event -> {
            // добавить логику
        });

        addComponents(label,
                createRow("Имя"),
                createRow("Фамилия"),
                createRow("Телефон"),
                createRow("Много бабла хочешь?"),
                confirmButtom);

        setComponentAlignment(label, MIDDLE_CENTER);
        setComponentAlignment(confirmButtom, MIDDLE_CENTER);
    }

    private HorizontalLayout createRow(String fieldName) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);

        Label nameLabel = new Label(fieldName);
        TextField nameField = new TextField();

        layout.addComponents(nameLabel, nameField);
        layout.setComponentAlignment(nameLabel, MIDDLE_RIGHT);
        layout.setComponentAlignment(nameField, MIDDLE_LEFT);

        return layout;
    }
}
