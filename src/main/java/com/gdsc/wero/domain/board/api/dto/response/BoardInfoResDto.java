package com.gdsc.wero.domain.board.api.dto.response;

import com.gdsc.wero.domain.board.domain.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfoResDto {
    private String email;
    private String nickname;
    private String mbti;
    private Long boardId;
    private String title;
    private String contents;
    private String img;

    public BoardInfoResDto(Board board) {
        this.email = board.getUser().getEmail();
        this.nickname = board.getUser().getUserInfo().getNickname();
        this.mbti = board.getUser().getMbti().getMbti();
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.img = board.getImg();
    }
}
