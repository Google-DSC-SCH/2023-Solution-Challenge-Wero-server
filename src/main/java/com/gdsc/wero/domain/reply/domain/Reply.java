package com.gdsc.wero.domain.reply.domain;

import com.gdsc.wero.domain.board.domain.Board;
import com.gdsc.wero.domain.common.BaseTimeEntity;
import com.gdsc.wero.domain.reply.api.dto.request.ReplyReqDto;
import com.gdsc.wero.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long replyId;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private Reply(String contents, User user, Board board) {
        this.contents = contents;
        this.user = user;
        this.board = board;
    }

    /**
     * Reply 생성 메서드
     */
    public static Reply createReply(ReplyReqDto replyReqDto, User user, Board board){
        return Reply.builder()
                .contents(replyReqDto.getContents())
                .user(user)
                .board(board)
                .build();

    }

    /**
     * Reply 수정 메서드
     */
    public void updateReply(ReplyReqDto replyReqDto) {
        this.contents = replyReqDto.getContents();
    }
}
