package com.taskagile.app.web.apis;

import com.taskagile.app.domain.application.BoardService;
import com.taskagile.app.domain.application.impl.UserServiceImpl;
import com.taskagile.app.domain.common.security.CurrentUser;
import com.taskagile.app.domain.model.board.Board;
import com.taskagile.app.domain.model.user.SimpleUser;
import com.taskagile.app.domain.model.user.UserId;
import com.taskagile.app.web.payload.CreateBoardPayload;
import com.taskagile.app.web.results.ApiResult;
import com.taskagile.app.web.results.CreateBoardResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BoardApiController {

    private static final Logger log = LoggerFactory.getLogger(BoardApiController.class);

    private BoardService boardService;

    public BoardApiController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/api/boards")
    public ResponseEntity<ApiResult> createBoard(
            @RequestBody CreateBoardPayload payload,
            @CurrentUser SimpleUser currentUser) {

        log.info(currentUser.toString());

        Board board = boardService.createBoard(
                payload.toCommand(new UserId(currentUser.getUserId()))
        );
        return CreateBoardResult.build(board);
    }
}
