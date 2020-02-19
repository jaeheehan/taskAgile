package com.taskagile.app.web.results;

import com.taskagile.app.domain.model.team.Team;
import org.springframework.http.ResponseEntity;

public class CreateTeamResult {

    public static ResponseEntity<ApiResult> build(Team team) {
        ApiResult apiResult = ApiResult.blank()
                .add("id", team.getTeamId().value())
                .add("name", team.getName());
        return Result.ok(apiResult);
    }
}

