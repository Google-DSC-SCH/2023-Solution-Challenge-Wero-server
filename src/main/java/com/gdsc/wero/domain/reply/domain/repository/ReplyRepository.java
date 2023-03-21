package com.gdsc.wero.domain.reply.domain.repository;

import com.gdsc.wero.domain.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    /**
     * 게시물 별 댓글 리스트 조회
     */
    @Query("select r " +
            "from Reply r " +
            "join fetch r.user u " +
            "join fetch u.mbti m " +
            "where r.board.boardId = :boardId")
     List<Reply> getReplyList(@Param("boardId") Long boardId);
}
