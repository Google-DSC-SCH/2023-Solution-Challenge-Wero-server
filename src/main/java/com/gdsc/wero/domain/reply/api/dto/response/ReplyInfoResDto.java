package com.gdsc.wero.domain.reply.api.dto.response;

import com.gdsc.wero.domain.reply.domain.Reply;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyInfoResDto {

    private String email;
    private String nickname;
    private String mbti;
    private Long replyId;
    private String contents;

    public ReplyInfoResDto(Reply reply) {
        this.email = reply.getUser().getEmail();
        this.nickname = reply.getUser().getUserInfo().getNickname();
        this.mbti = reply.getUser().getMbti().getMbti();
        this.replyId = reply.getReplyId();
        this.contents = reply.getContents();
    }
}
