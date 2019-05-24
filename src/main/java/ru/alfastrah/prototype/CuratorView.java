package ru.alfastrah.prototype;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.GridSelectionModel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.vaadin.ui.Alignment.MIDDLE_CENTER;
import static ru.alfastrah.prototype.Agent.Status.NEW;
import static ru.alfastrah.prototype.MyUI.AGENT_CURATOR_VIEW;
import static ru.alfastrah.prototype.MyUI.NEW_AGENT_VIEW;

class CuratorView extends VerticalLayout implements View {
    CuratorView(Navigator navigator) {
        buildLayout(navigator);
    }

    private Navigator navigator;

    private void buildLayout(Navigator navigator) {
        setSpacing(true);
        this.navigator = navigator;

        Label curatorLabel = new Label("Кабинет куратора");

        List<Agent> inProgressAgents = Arrays.asList(getInProgressGoodAgent(), getInProgressBadAgent());

        Grid<Agent> newAgentsGrid = getNewAgnetsGrid(Collections.singletonList(getNewAgent()));
        Grid<Agent> inProgressAgentsGrid = getInProgressAgentsGrid(inProgressAgents);
        Grid<Agent> stateAgents = getStateAgents(Collections.singletonList(getStateAgent()));
        Label newApplicationLabel = new Label("Новые заявки");
        Label inProgressApplicationLabel = new Label("Заявки в работе");
        Label stateApplicationLabel = new Label("Мои агенты");

        addComponents(curatorLabel, newApplicationLabel, newAgentsGrid,
                inProgressApplicationLabel, inProgressAgentsGrid, stateApplicationLabel, stateAgents);
        setComponentAlignment(curatorLabel, MIDDLE_CENTER);

        GridSelectionModel<Agent> selectionModel = inProgressAgentsGrid.getSelectionModel();
        inProgressAgentsGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        inProgressAgents.stream()
                .filter(agent -> !"Все хорошо".equals(agent.getStatusOk()))
                .forEach(inProgressAgentsGrid::select);

    }


    private Grid<Agent> getStateAgents(List<Agent> agents) {
        Grid<Agent> grid = new Grid<>();
        //grid.addColumn("#").setCaption("№");
        grid.setItems(agents);

        grid.addColumn(Agent::getFIO).setCaption("ФИО");
        grid.addColumn(Agent::getSumValue).setCaption("Текущий портфель");
        grid.addColumn(Agent::getLoanKV).setCaption("Дебиторка");

        grid.setWidth(1200, Unit.PIXELS);
        grid.setHeight(150, Unit.PIXELS);

        return grid;
    }

    private Grid<Agent> getNewAgnetsGrid(List<Agent> agents) {
        Grid<Agent> grid = new Grid<>();
        //grid.addColumn("#").setCaption("№");
        grid.setItems(agents);

        grid.addColumn(Agent::getStringStartDate).setCaption("Дата стажа агента");
        grid.addColumn(Agent::getFIO).setCaption("ФИО");
        grid.addColumn(Agent::getStatus).setCaption("Статус");
        grid.addColumn(Agent::getValue).setCaption("Объем портфеля");
        grid.addColumn(Agent::getPhone).setCaption("Контакты");

        grid.setWidth(1200, Unit.PIXELS);
        grid.setHeight(150, Unit.PIXELS);

        grid.addItemClickListener(event -> {
            navigator.addView(NEW_AGENT_VIEW, new NewAgentView(navigator, event.getItem()));
            navigator.navigateTo(NEW_AGENT_VIEW);
        });

        return grid;
    }

    private Grid<Agent> getInProgressAgentsGrid(List<Agent> agents) {
        Grid<Agent> inProgressAgents = getNewAgnetsGrid(agents);
        inProgressAgents.addColumn(Agent::getStatusOk).setCaption("Проблемы?");

        inProgressAgents.addItemClickListener(event -> {
            navigator.addView(AGENT_CURATOR_VIEW, new AgentCuratorView(navigator, event.getItem()));
            navigator.navigateTo(AGENT_CURATOR_VIEW);
        });

        return inProgressAgents;
    }

    private Agent getNewAgent() {
        Agent agent = new Agent();
        agent.setFIO("Агентов Агент Агентович");
        agent.setPhone("+7(495)123-45-67");
        agent.setStartDate(new Date());
        agent.setStatus(NEW);
        agent.setValue(new BigDecimal(1_500_000L));
        return agent;
    }

    private Agent getInProgressGoodAgent() {
        Agent agent = new Agent();
        agent.setFIO("Мега Крутой Агент");
        agent.setPhone("+7(495)555-45-67");
        agent.setStartDate(new Date());
        agent.setStatus(Agent.Status.DOC_COMPLETE);
        agent.setStatusOk("Все хорошо");
        agent.setValue(new BigDecimal(1_500_000L));
        return agent;
    }

    private Agent getInProgressBadAgent() {
        Agent agent = new Agent();
        agent.setFIO("Хреновый Агент");
        agent.setPhone("+7(495)555-45-67");
        agent.setStartDate(new Date());
        agent.setStatus(Agent.Status.DOC_COMPLETE);
        agent.setStatusOk("Обратить внимание");
        agent.setValue(new BigDecimal(1_500_000L));
        return agent;
    }

    private Agent getStateAgent() {
        Agent agent = new Agent();
        agent.setFIO("Наш Агент Альфастрахович");
        agent.setPhone("+7(495)987-54-32");
        agent.setStartDate(new Date());
        agent.setStatus(Agent.Status.COMPLETE);
        agent.setValue(new BigDecimal(1_500_000L));
        return agent;
    }
}
