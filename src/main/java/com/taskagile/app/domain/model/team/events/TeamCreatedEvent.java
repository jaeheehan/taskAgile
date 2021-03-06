package com.taskagile.app.domain.model.team.events;

import com.taskagile.app.domain.common.event.DomainEvent;
import com.taskagile.app.domain.model.team.Team;

public class TeamCreatedEvent extends DomainEvent {

    private static final long serialVersionUID = 2714833255396717504L;

    private Team team;

    public TeamCreatedEvent(Object source, Team team) {
        super(source);
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }
}

