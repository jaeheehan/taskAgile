package com.taskagile.app.domain.model.board;

import com.taskagile.app.domain.model.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query(value = "select b.* from board b left outer join board_member bm on b.id = bm.board_id where bm.user_id = :user_id"
            , nativeQuery = true)
    List<Board> findBoardsByMembership(@Param("user_id") Long userId);
}
