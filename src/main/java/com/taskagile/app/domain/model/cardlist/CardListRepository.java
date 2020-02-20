package com.taskagile.app.domain.model.cardlist;

import com.taskagile.app.domain.model.board.BoardId;
import com.taskagile.app.domain.model.card.Card;
import com.taskagile.app.domain.model.card.CardPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardListRepository {

    /**
     * Find card lists of a board
     *
     * @param boardId the id of the board
     * @return a list of card lists of a board or an empty list if none found
     */
    List<CardList> findByBoardId(BoardId boardId);

    /**
     * Save card list
     *
     * @param cardList cardList to save
     */
    void save(CardList cardList);

    /**
     * Change card list positions
     *
     * @param cardListPositions the positions of card lists
     */
    void changePositions(List<CardListPosition> cardListPositions);


}
