package ru.alfastrah.prototype;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.File;

import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.Alignment.MIDDLE_CENTER;
import static com.vaadin.ui.Alignment.MIDDLE_RIGHT;
import static ru.alfastrah.prototype.MyUI.PRIVATE_OFFICE_VIEW;

public class PrivateOfficeView extends VerticalLayout implements View {

    private EmailSender emailSender;
    private Label label;

    private Image image = new Image("", new FileResource(
            new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() +
                    "/WEB-INF/classes/application_status_bar_1.png")));

    PrivateOfficeView(Navigator navigator, boolean isMiddleStatus, String name) {
        buildLayout(navigator, isMiddleStatus, name);
        emailSender = new EmailSender();
    }

    private void buildLayout(Navigator navigator, boolean isMiddleStatus, String name) {

        setSpacing(true);

        Label pageTitle = new Label(name);

        VerticalLayout layout = new VerticalLayout();

        if (isMiddleStatus) {
            image.setSource(new FileResource(
                    new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() +
                            "/WEB-INF/classes/application_status_bar_3.png")));
        }
        String firstLabel = "<b>Документы загружены, ожидайте результатов проверки (около 1-2 дней)</b>";
        String secondLabel = "<b>Ожидайте результатов проверки (около 12 дней)</b>";

        if (isMiddleStatus) {
            label = new Label(secondLabel, ContentMode.HTML);
            label.setVisible(true);
        } else {
            label = new Label(firstLabel, ContentMode.HTML);
            label.setVisible(false);
        }

        layout.addComponents(
                label,
                createEditFormLayout(navigator),
                createFileUpload(),
                createLink("Изучите продукты АльфаСтрахования"),
                createLink("Научитесь работать с Альфа Полисом"),
                createLink("Получить ЭЦП для подписания договора"),
                createLink("Скачать мобильные приложения для агентов"),
                createLink("Агентский договор"),
                createLink("Пройти обучение на AlfaEducation"),
                createTestCalculator(),
                createCuratorConnectionBlock(name));
        layout.setWidth(50, Unit.PERCENTAGE);

        HorizontalLayout statusBar = createStatusBar();
        addComponents(
                pageTitle,
                statusBar,
                layout);

        setComponentAlignment(pageTitle, Alignment.MIDDLE_RIGHT);
        setComponentAlignment(statusBar, MIDDLE_CENTER);
        setComponentAlignment(layout, MIDDLE_CENTER);
    }

    private HorizontalLayout createStatusBar() {

        HorizontalLayout layout = new HorizontalLayout();

        image.setHeight(100, PIXELS);
        layout.addComponent(image);

        return layout;
    }

    private HorizontalLayout createFileUpload() {

        HorizontalLayout layout = new HorizontalLayout();

        Label labelDoc = new Label("паспорт");
        Label labelIcon = new Label("");
        labelIcon.setIcon(VaadinIcons.CHECK);
        labelDoc.setVisible(false);
        labelIcon.setVisible(false);

        Upload personalPageUpload = new Upload();
        personalPageUpload.setButtonCaption("Загрузить документы");
        personalPageUpload.setReceiver(
                (String fileName, String mimeType) -> new ByteArrayOutputStream());
        personalPageUpload.addFinishedListener(event -> {
            image.setSource(new FileResource(
                new File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() +
                        "/WEB-INF/classes/application_status_bar_2.png")));
            labelDoc.setVisible(true);
            labelIcon.setVisible(true);
            label.setVisible(true);
        });
        personalPageUpload.setWidth("350px");

        layout.addComponents(personalPageUpload, labelDoc, labelIcon);

        return layout;
    }

    private HorizontalLayout createEditFormLayout(Navigator navigator) {

        HorizontalLayout layout = new HorizontalLayout();

        Button button = new Button("Заполнить анкету",
                event -> navigator.navigateTo(PRIVATE_OFFICE_VIEW));
        button.setWidth("350px");
        button.addClickListener(event -> {
            button.setCaption("Анкета заполнена");
            button.setEnabled(false);
        });
        layout.addComponent(button);

        return layout;
    }

    private HorizontalLayout createLink(String title) {

        HorizontalLayout layout = new HorizontalLayout();

        Link link = new Link(title, new ExternalResource("https://ya.ru"));
        layout.addComponent(link);

        return layout;
    }

    private HorizontalLayout createTestCalculator() {

        HorizontalLayout layout = new HorizontalLayout();

        Button button = new Button("Запросить доступ к расчету премии");
        button.setWidth("350px");
        button.addListener(event -> {
            button.setCaption("Доступ предоставлен");
            button.setEnabled(false);
        });
        layout.addComponent(button);

        return layout;
    }

    private HorizontalLayout createCuratorConnectionBlock(String name) {

        HorizontalLayout layout = new HorizontalLayout();

        Window window = new Window("Отправка сообщения куратору");
        VerticalLayout windowLayout = new VerticalLayout();

        Label textCaption = new Label("Введите текст сообщения");
        TextArea textArea = new TextArea();
        textArea.setWidth("500px");
        Button sendButton = new Button("Отправить", event -> {
            try {
                emailSender.sendEmail("Сообщение от " + name, textArea.getValue());
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            window.close();
        } );
        windowLayout.addComponents(
                textCaption,
                textArea,
                sendButton);
        windowLayout.setComponentAlignment(sendButton, MIDDLE_RIGHT);
        window.setContent(windowLayout);
        window.center();

        Button button = new Button("Связаться с куратором",
                event -> UI.getCurrent().addWindow(window));
        button.setWidth("350px");
        layout.addComponent(button);

        return layout;
    }

}
