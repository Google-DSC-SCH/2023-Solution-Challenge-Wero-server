package com.gdsc.wero.domain.board.api.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResListDto {
    private List<BoardInfoResDto> boardInfoList;

    @JsonCreator
    public BoardResListDto(List<BoardInfoResDto> boardInfoList) {
        this.boardInfoList = boardInfoList;
    }
}
