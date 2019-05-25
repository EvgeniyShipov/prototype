package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import javax.mail.MessagingException;

import java.io.File;
import java.util.List;

import static com.vaadin.ui.Alignment.MIDDLE_LEFT;
import static ru.alfastrah.prototype.MyUI.CURATOR_VIEW;

class NewAgentView extends VerticalLayout implements View {
    private Agent agent;
    private EmailSender emailSender;

    NewAgentView(Navigator navigator, Agent agent, List<Agent> newAgents, List<Agent> inProgressAgents) {
        this.agent = agent;
        emailSender = new EmailSender();
        buildLayout(navigator, newAgents, inProgressAgents);
    }

    private void buildLayout(Navigator navigator, List<Agent> newAgents, List<Agent> inProgressAgents) {
        setSpacing(true);

        Label labelFIO = new Label("<b>" + agent.getFIO() + "</b>");
        labelFIO.setContentMode(ContentMode.HTML);

        Label labelPhone = new Label("Телефон агента: " + agent.getPhone());
        Label labelPasport = new Label("Паспорт, серия 4554, номер 454545");
        Label labelValue = new Label("Портфель: " + agent.getValue().toString() + " руб.");
        Label labelStatus = new Label("Статус заявки: " + agent.getStatusOk());
        Label labelDate = new Label("Дата начала страховой деятельности: " + agent.getStringStartDate());
        Label labelOther = new Label("Прочие данные: в разработке... ");

        Button downloadDocs = new Button("Скачать документы");
        FileDownloader fileDownloader;
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
        fileDownloader = new FileDownloader(new FileResource(new File(basepath + "/WEB-INF/classes/Pasport_RF.jpg")));
        fileDownloader.extend(downloadDocs);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button confirmButtom = new Button("Утвердить",
                event -> {
                    try {
                        newAgents.remove(agent);
                        inProgressAgents.add(agent);
                        emailSender.sendEmail("Заявка на рассмотрение",
                                "Please check - http://localhost:8080/prototype/#!newAgent");
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    Notification.show("Заявка подтверждена!");
                });

        Button rejectButtom = new Button("Отклонить",
                event -> {
                    newAgents.remove(agent);
                    Notification.show("Заявка отклонена =(");
                } );
        buttonsLayout.addComponents(rejectButtom, confirmButtom);

        Button backButton = new Button("Назад",
                event -> navigator.navigateTo(CURATOR_VIEW));

        addComponents(labelFIO, labelPhone, labelPasport, labelValue, labelStatus, labelDate,
                labelOther, downloadDocs, buttonsLayout, backButton);

        setComponentAlignment(backButton, Alignment.BOTTOM_LEFT);

        setComponentAlignment(labelFIO, MIDDLE_LEFT);
    }
}
