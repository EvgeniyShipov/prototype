package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import static com.vaadin.ui.Alignment.MIDDLE_LEFT;

public class NewAgentView extends VerticalLayout implements View {
    private Agent agent;

    NewAgentView(Navigator navigator, Agent agent) {
        this.agent = agent;
        buildLayout(navigator);
    }

    private void buildLayout(Navigator navigator) {
        setSpacing(true);

        Label labelFIO = new Label("<b>" + agent.getFIO() + "</b>");
        labelFIO.setContentMode(ContentMode.HTML);

        Label labelPhone = new Label("Телефон агента: "+ agent.getPhone());
        Label labelPasport= new Label("Паспорт, серия 4554, номер 454545");
        Label labelValue = new Label("Портфель: " + agent.getValue().toString() + " руб.");
        Label labelStatus = new Label("Статус заявки: " + agent.getStatusOk());
        Label labelDate = new Label("Дата начала страховой деятельности: " + agent.getStringStartDate());
        Label labelOther = new Label("Прочие данные: в разработке... ");

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button confirmButtom = new Button("Утвердить",
                event -> Notification.show("Заявка подтверждена!"));

        Button rejectButtom = new Button("Отклонить",
                event -> Notification.show("Заявка отклонена =("));
        buttonsLayout.addComponents(rejectButtom, confirmButtom);

        addComponents(labelFIO, labelPhone, labelPasport, labelValue, labelStatus, labelDate,
                labelOther, buttonsLayout);

        setComponentAlignment(labelFIO, MIDDLE_LEFT);
    }
}
