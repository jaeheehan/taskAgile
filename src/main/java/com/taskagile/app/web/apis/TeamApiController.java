package com.taskagile.app.web.apis;

import com.taskagile.app.domain.application.TeamService;
import com.taskagile.app.domain.common.security.CurrentUser;
import com.taskagile.app.domain.model.team.Team;
import com.taskagile.app.domain.model.user.SimpleUser;
import com.taskagile.app.domain.model.user.UserId;
import com.taskagile.app.web.payload.CreateTeamPayload;
import com.taskagile.app.web.results.ApiResult;
import com.taskagile.app.web.results.CreateTeamResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TeamApiController {

    private TeamService teamService;

    public TeamApiController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/api/teams")
    public ResponseEntity<ApiResult> createTeam(@RequestBody CreateTeamPayload payload,
                                                @CurrentUser SimpleUser currentUser) {
        Team team = teamService.createTeam(payload.toCommand(new UserId(currentUser.getUserId())));
        return CreateTeamResult.build(team);
    }
}
