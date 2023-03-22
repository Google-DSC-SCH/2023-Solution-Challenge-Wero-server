package com.gdsc.wero.domain.board.api;

import com.gdsc.wero.domain.board.api.dto.request.BoardReqDto;
import com.gdsc.wero.domain.board.api.dto.response.BoardResListDto;
import com.gdsc.wero.domain.board.application.BoardService;
import com.gdsc.wero.global.auth.jwt.JwtUtils;
import com.gdsc.wero.global.exception.errortype.GcsUploadFailException;
import com.gdsc.wero.global.util.gcs.GcsInfoProperties;
import com.gdsc.wero.global.util.gcs.GcsService;
import com.google.cloud.storage.BlobInfo;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final GcsService gcsService;
    private final GcsInfoProperties gcsInfoProperties;
    private final JwtUtils jwtUtils;

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
    public String saveBoard(@RequestPart BoardReqDto boardReqDto, @RequestPart(required = false) MultipartFile imgFile, HttpServletRequest request) {

        // 유저 조회
        String jwtFromHeader = jwtUtils.getJwtFromHeader(request);
        Map<String, Object> userEmailAndProviderFromJwtToken = jwtUtils.getUserEmailAndProviderFromJwtToken(jwtFromHeader);
        String email = (String)userEmailAndProviderFromJwtToken.get("email");
        String provider = (String)userEmailAndProviderFromJwtToken.get("provider");
        // GCS
        String img = "empty";

        if(!(imgFile == null)){
            try {
                BlobInfo blobInfo = gcsService.uploadFileToGCS(imgFile);
                log.info("========== link : " + blobInfo.getMediaLink());
                log.info("========== link : " + blobInfo.getSelfLink());
                img = blobInfo.getMediaLink();

                log.info("============ IMAGE HAS BEEN SAVED =============");

            } catch (IOException e) {
                log.error(e.getMessage());
                throw new GcsUploadFailException(e.getMessage());
            }
        }

        // 저장
        boardService.saveBoard(boardReqDto, img, email, provider);

        log.info("================ POST HAS BEEN SAVED =================");

        return "success";
    }

    @ApiOperation(value = "게시물 수정 api", notes = "게시물 수정 후 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시물 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PutMapping("/{boardId}")
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String updateBoard(@PathVariable(value = "boardId") Long boardId, @RequestPart BoardReqDto boardReqDto, @RequestPart(required = false) MultipartFile imgFile) {

        // GCS
        String img = "empty";

        if(!(imgFile == null)){
            try {
                BlobInfo blobInfo = gcsService.uploadFileToGCS(imgFile);
                log.info("========== link : " + blobInfo.getMediaLink());
                log.info("========== link : " + blobInfo.getSelfLink());
                img = blobInfo.getMediaLink();

                log.info("============ IMAGE HAS BEEN SAVED =============");

            } catch (IOException e) {
                // 예외 던지기
                log.error(e.getMessage());
                throw new GcsUploadFailException(e.getMessage());
            }
        }

        boardService.updateBoard(boardReqDto, img, boardId);

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

        boardService.deleteBoard(boardId);

        log.info("=============== POST HAS BEEN DELETED =================");

        return "success";
    }

}
