package com.taskagile.app.web.apis;

import com.taskagile.app.domain.application.BoardService;
import com.taskagile.app.domain.application.TeamService;
import com.taskagile.app.domain.common.mail.DefaultMailManager;
import com.taskagile.app.domain.common.security.CurrentUser;
import com.taskagile.app.domain.model.board.Board;
import com.taskagile.app.domain.model.team.Team;
import com.taskagile.app.domain.model.user.SimpleUser;
import com.taskagile.app.domain.model.user.UserId;
import com.taskagile.app.web.results.ApiResult;
import com.taskagile.app.web.results.MyDataResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MeApiController {

    private TeamService teamService;
    private BoardService boardService;

    public MeApiController(TeamService teamService, BoardService boardService) {
        this.teamService = teamService;
        this.boardService = boardService;
    }

    @GetMapping("/api/me")
    public ResponseEntity<ApiResult> getMyData(@CurrentUser SimpleUser currentUser) {
        List<Team> teams = teamService.findTeamsByUserId(currentUser.getUserId());
        List<Board> boards = boardService.findBoardsByMembership(currentUser.getUserId());
        return MyDataResult.build(currentUser, teams, boards);
    }
}
