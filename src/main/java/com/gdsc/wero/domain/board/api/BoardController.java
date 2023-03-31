package com.gdsc.wero.domain.board.api;

import com.gdsc.wero.domain.board.api.dto.request.BoardReqDto;
import com.gdsc.wero.domain.board.api.dto.response.BoardResListDto;
import com.gdsc.wero.domain.board.application.BoardService;
import com.gdsc.wero.global.resolver.UserInfoFromHeader;
import com.gdsc.wero.global.resolver.UserInfoFromHeaderDto;
import com.gdsc.wero.global.util.gcs.GcsUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 이미지 저장 및 수정 트랜잭션 영역 합치던가 에러 후 어떻게 할지 정의하기
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final GcsUtils gcsService;

    @ApiOperation(value = "게시물 페이지 진입 api", notes = "게시물 페이지 진입 시 게시물 리스트를 최신 게시물 순으로 리턴한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 리스트 리턴 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("")
    public ResponseEntity<BoardResListDto> goToBoard() {

        BoardResListDto boardList = boardService.getBoardList();

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

    @ApiOperation(value = "게시물 저장 api", notes = "게시물 작성 후 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String saveBoard(@RequestPart BoardReqDto boardReqDto, @RequestPart(required = false) MultipartFile imgFile, @UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto) {

        String email = userInfoFromHeaderDto.getEmail();
        String provider = userInfoFromHeaderDto.getProvider();

        // 게시물 저장 및 이미지 업로드
        boardService.saveBoard(imgFile, boardReqDto, email, provider); // imgName 추가, img -> imgLink로 수정

        log.info("================ POST HAS BEEN SAVED =================");

        return "success";
    }



    @ApiOperation(value = "게시물 수정 api", notes = "게시물 수정 후 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PutMapping(value ="/{boardId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String updateBoard(@PathVariable(value = "boardId") Long boardId, @RequestPart BoardReqDto boardReqDto, @RequestPart(required = false) MultipartFile imgFile) {

        // 게시물 수정
        boardService.updateBoard(imgFile, boardReqDto, boardId);

        log.info("=============== POST HAS BEEN UPDATED =================");

        return "success";
    }


    @ApiOperation(value = "게시물 삭제 api", notes = "게시물을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable(value = "boardId") Long boardId) {

        // 게시물 및 이미지 삭제
        boardService.deleteBoard(boardId);

        log.info("=============== POST HAS BEEN DELETED =================");

        return "success";
    }






}
