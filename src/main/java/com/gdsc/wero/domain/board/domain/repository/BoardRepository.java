package com.gdsc.wero.domain.board.domain.repository;

import com.gdsc.wero.domain.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * 최신 게시물 순으로 정렬 후 조회
     */
    @Query("select b " +
            "from Board b " +
            "join fetch b.user u " +
            "join fetch u.mbti m " +
            "order by b.createdAt DESC ")
    List<Board> getBoardList();
}
