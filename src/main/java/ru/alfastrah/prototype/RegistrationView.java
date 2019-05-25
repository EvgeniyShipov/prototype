package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vaadin.ui.Alignment.*;
import static ru.alfastrah.prototype.MyUI.PRIVATE_OFFICE_VIEW;

class RegistrationView extends VerticalLayout implements View {

    private final Map<String, List<String>> regions = new HashMap<>();
    private ComboBox<String> filialSelector = new ComboBox<>();
    private Navigator navigator;
    private EmailSender emailSender;

    RegistrationView(Navigator navigator) {
        this.navigator = navigator;
        emailSender = new EmailSender();
        buildLayout(navigator);
        regions.put("Московский", Arrays.asList("Москва", "Руза", "Сергиев-Посад"));
        regions.put("Северо-Западный", Arrays.asList("Санкт-Петерург", "Выборг"));
    }

    private void buildLayout(Navigator navigator) {

        setSpacing(true);

        Label label = new Label("<b> " +
                "Заявка на заключение агентского договора </b>", ContentMode.HTML);

        TextField nameField = new TextField();
        nameField.setWidth("300px");

        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button sendButton = new Button("Отправить заявку", event -> {
            try {
                DataBase.name = nameField.getValue();
                emailSender.sendEmail("Ваша заявка принята", "Welcome to Alfa! \n " +
                        "Your curator - Alexander Ivanov. \n " +
                        "Your private office - http://localhost:8080/prototype/#!privateOffice");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            navigator.addView(PRIVATE_OFFICE_VIEW, new PrivateOfficeView(navigator, false, nameField.getValue()));
            navigator.navigateTo(PRIVATE_OFFICE_VIEW);
        });
        buttonLayout.addComponents(sendButton);

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.addComponents(label,
                new Label("Как к Вам обращаться?"),
                nameField,
                new Label("Как с Вами связаться?"),
                createRow("Телефон"),
                createRow("Email"),
                new Label("Объем Вашего портфеля, руб."),
                createSelector("КАСКО"),
                createSelector("ОСАГО"),
                createSelector("ИФЛ"),
                createSelector("Прочее"),
                createAgentForm(),
                new Label("Где вы находитесь?"),
                createRegionSelector(),
                createFilialSelector(),
                createNotificationChannel(),
                createAgreementSelector(),
                buttonLayout);
        mainLayout.setWidth(50, Unit.PERCENTAGE);
        addComponent(mainLayout);

        setComponentAlignment(mainLayout, MIDDLE_CENTER);
    }

    private HorizontalLayout createSelector(String text) {

        HorizontalLayout layout = new HorizontalLayout();

        Label nameLabel = new Label(text);
        nameLabel.setWidth("70px");

        RadioButtonGroup<String> selector = new RadioButtonGroup<>();
        selector.setItems("< 1 млн", "1 - 10 млн", "> 10 млн ");
        selector.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        layout.addComponents(nameLabel, selector);

        layout.setComponentAlignment(nameLabel, MIDDLE_LEFT);

        return layout;
    }

    private HorizontalLayout createAgentForm() {

        HorizontalLayout layout = new HorizontalLayout();

        RadioButtonGroup<String> selector = new RadioButtonGroup<>("Вы работаете в качестве");
        selector.setItems("ФЛ", "ИП", "ЮЛ");
        selector.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        layout.addComponent(selector);

        return layout;
    }

    private HorizontalLayout createAgreementSelector() {

        HorizontalLayout layout = new HorizontalLayout();

        CheckBox checkBox = new CheckBox("Согласие на обработку персональных данных");
        layout.addComponent(checkBox);

        return layout;
    }

    private HorizontalLayout createNotificationChannel() {

        HorizontalLayout layout = new HorizontalLayout();

        RadioButtonGroup<String> selector = new RadioButtonGroup<>("Выберите удобный способ для коммуникации");
        selector.setItems("Телефон", "Мессенджер", "E-mail", "Нет");
        selector.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        layout.addComponent(selector);

        return layout;
    }

    private HorizontalLayout createRegionSelector() {
        HorizontalLayout layout = new HorizontalLayout();

        Label nameLabel = new Label("Регион");
        nameLabel.setWidth("70px");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(regions.keySet());
        comboBox.addValueChangeListener(event -> {

            String oldValue = event.getOldValue();
            String newValue = event.getValue();

            if (newValue == null) {
                filialSelector.setSelectedItem(null);
                return;
            }

            if (oldValue == null || !oldValue.equals(newValue)) {
                filialSelector.setSelectedItem(null);
                filialSelector.setItems(regions.get(newValue));
            }
        });

        layout.addComponents(nameLabel, comboBox);

        return layout;
    }

    private HorizontalLayout createFilialSelector() {

        HorizontalLayout layout = new HorizontalLayout();

        Label nameLabel = new Label("Филиал");
        nameLabel.setWidth("70px");

        layout.addComponents(nameLabel, filialSelector);

        return layout;
    }

    private HorizontalLayout createRow(String fieldName) {

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);

        Label nameLabel = new Label(fieldName);
        nameLabel.setWidth("90px");
        TextField nameField = new TextField();

        layout.addComponents(nameLabel, nameField);

        return layout;
    }
}
