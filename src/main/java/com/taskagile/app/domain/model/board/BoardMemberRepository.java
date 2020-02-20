package com.taskagile.app.domain.model.board;

import com.taskagile.app.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardMemberRepository extends JpaRepository<BoardMember, BoardMember.BoardMemberId> {

    @Query(value = " SELECT u.* FROM user u " +
            " LEFT JOIN board_member bm ON u.id = bm.user_id " +
            " WHERE bm.board_id = :boardId", nativeQuery = true)
    List<User> findMembers(@Param("boardId") BoardId boardId);
}
