package com.gdsc.wero.domain.reply.api;

import com.gdsc.wero.domain.reply.api.dto.request.ReplyReqDto;
import com.gdsc.wero.domain.reply.api.dto.response.ReplyResListDto;
import com.gdsc.wero.domain.reply.application.ReplyService;
import com.gdsc.wero.global.resolver.UserInfoFromHeader;
import com.gdsc.wero.global.resolver.UserInfoFromHeaderDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reply")
public class ReplyController {

    private final ReplyService replyService;
    @ApiOperation(value = "댓글 페이지 진입 api", notes = "댓글 페이지 진입 시 댓글 리스트를 리턴한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 리스트 리턴 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/{boardId}")
    public ResponseEntity<ReplyResListDto> toToBoard(@PathVariable(value = "boardId") Long boardId) {

        ReplyResListDto replyList = replyService.getReplyList(boardId);

        return new ResponseEntity<>(replyList, HttpStatus.OK);
    }

    @ApiOperation(value = "댓글 저장 api", notes = "댓글 작성 후 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/{boardId}")
    public String saveBoard(@PathVariable(value = "boardId") Long boardId, @RequestBody ReplyReqDto replyReqDto, @UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto) {

        String email = userInfoFromHeaderDto.getEmail();
        String provider = userInfoFromHeaderDto.getProvider();

        replyService.saveReply(replyReqDto, boardId, email, provider);

        log.info("================ REPLY HAS BEEN SAVED ==================");

        return "success";
    }

    @ApiOperation(value = "댓글 수정 api", notes = "댓글 수정 후 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PutMapping("/{replyId}")
    public String updateBoard(@PathVariable(value = "replyId") Long replyId, @RequestBody ReplyReqDto replyReqDto) {

        replyService.updateReply(replyReqDto, replyId);

        log.info("================ REPLY HAS BEEN UPDATED ==================");

        return "success";
    }

    @ApiOperation(value = "댓글 삭제 api", notes = "댓글 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping("/{replyId}")
    public String deleteBoard(@PathVariable(value = "replyId") Long replyId) {

        replyService.deleteReply(replyId);

        log.info("================ REPLY HAS BEEN DELETED ==================");

        return "success";
    }
}
