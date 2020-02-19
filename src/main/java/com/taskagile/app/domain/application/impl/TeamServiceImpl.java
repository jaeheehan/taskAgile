package com.taskagile.app.domain.application.impl;

import com.taskagile.app.domain.application.TeamService;
import com.taskagile.app.domain.application.commands.CreateTeamCommand;
import com.taskagile.app.domain.common.event.DomainEventPublisher;
import com.taskagile.app.domain.model.team.Team;
import com.taskagile.app.domain.model.team.TeamRepository;
import com.taskagile.app.domain.model.team.events.TeamCreatedEvent;
import com.taskagile.app.domain.model.user.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private DomainEventPublisher domainEventPublisher;

    public TeamServiceImpl(TeamRepository teamRepository, DomainEventPublisher domainEventPublisher) {
        this.teamRepository = teamRepository;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public List<Team> findTeamsByUserId(Long userId) {
        return teamRepository.findTeamsByUserId(userId);
    }

    @Override
    public Team createTeam(CreateTeamCommand command) {
        Team team = Team.create(command.getName(), command.getUserId());
        teamRepository.save(team);
        domainEventPublisher.publish(new TeamCreatedEvent(this, team));
        return team;
    }

}
