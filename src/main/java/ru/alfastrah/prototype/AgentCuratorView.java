package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;

import static com.vaadin.ui.Alignment.*;

public class AgentCuratorView extends VerticalLayout implements View {
    private Agent agent;

    AgentCuratorView(Navigator navigator, Agent agent) {
        this.agent = agent;
        buildLayout(navigator);
    }

    private void buildLayout(Navigator navigator) {
        setSpacing(true);

        Label labelFIO = new Label("<b>" + agent.getFIO() + "</b>");
        labelFIO.setContentMode(ContentMode.HTML);

        Label labelStatus = new Label("Статус заявки: " + agent.getStatusOk());
        Label labelPhone = new Label("Телефон агента: " + agent.getPhone());
        Label labelSumValue = new Label("Текущий портфель: " + agent.getSumValue().toString() + " руб.");


        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button accessButtom = new Button("Дать тестовый доступ в АП",
                event -> Notification.show("Доступ предоставлен!"));
        buttonsLayout.addComponent(accessButtom);

        if (!"Все хорошо".equals(agent.getStatusOk())) {
            Button buttom2 = new Button("Пнуть процесс",
                    event -> Notification.show("Все необходимые люди уведомлены!"));

            Button buttom3 = new Button("Эскалация",
                    event -> Notification.show("Все необходимые люди наказаны!"));
            buttonsLayout.addComponents(buttom2, buttom3);
        }
        addComponents(labelFIO, labelStatus, labelPhone, labelSumValue, buttonsLayout);

        setComponentAlignment(labelFIO, MIDDLE_LEFT);
    }
}
