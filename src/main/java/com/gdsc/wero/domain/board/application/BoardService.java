package com.gdsc.wero.domain.board.application;

import com.gdsc.wero.domain.board.api.dto.request.BoardReqDto;
import com.gdsc.wero.domain.board.api.dto.response.BoardInfoResDto;
import com.gdsc.wero.domain.board.api.dto.response.BoardResListDto;
import com.gdsc.wero.domain.board.domain.Board;
import com.gdsc.wero.domain.board.domain.repository.BoardRepository;
import com.gdsc.wero.domain.board.exception.BoardNotExistException;
import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import com.gdsc.wero.global.exception.errortype.GcsUploadFailException;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import com.gdsc.wero.global.util.gcs.GcsUtils;
import com.google.cloud.storage.BlobInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final GcsUtils gcsService;


    /**
     * 게시물 조회
     */
    public Board getBoard(Long boardId){

        return boardRepository.findById(boardId).orElseThrow(() -> new BoardNotExistException("This Post does not exist | boardId : " + boardId));
    }


    /**
     * 게시물들 조회
     */
    public BoardResListDto getBoardList(){
        List<BoardInfoResDto> collect = boardRepository.getBoardList()
                .stream()
                .map(BoardInfoResDto::new)
                .collect(Collectors.toList());

        return new BoardResListDto(collect);

    }


    /**
     * 게시물 저장
     */
    public void saveBoard(MultipartFile imgFile, BoardReqDto boardReqDto, String email, String provider) {

        // GCS Image Upload
        String imgLink = "empty";
        String imgName = "empty";
        String[] temp = verifyAndSaveImg(imgFile, imgLink, imgName).split(",");
        imgLink = temp[0];
        imgName = temp[1];

        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist | email : " + email));

        Board board = Board.createBoard(boardReqDto, imgLink, imgName, user);

        // 게시물 저장
        boardRepository.save(board);

        log.info("================ POST IS BEING SAVED ==================");

    }

    /**
     * 게시물 수정
     */
    public void updateBoard(MultipartFile imgFile, BoardReqDto boardReqDto, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotExistException("This Post does not exist | boardId : " + boardId));

        // 이미지 삭제
        gcsService.deleteFileFromGCS(board.getImgName());

        // GCS Image Upload
        String imgLink = "empty";
        String imgName = "empty";
        String[] temp = verifyAndSaveImg(imgFile, imgLink, imgName).split(",");
        imgLink = temp[0];
        imgName = temp[1];

        // 게시물 수정
        board.updateBoard(boardReqDto, imgLink, imgName);

        log.info("================ POST IS BEING UPDATED ==================");

    }

    /**
     * 게시물 삭제
     */
    public void deleteBoard(Long boardId){

        //조회
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotExistException("This Post does not exist | boardId : " + boardId));

        // 이미지 삭제
        gcsService.deleteFileFromGCS(board.getImgName());

        // 게시물 삭제
        boardRepository.delete(board);

        log.info("================ POST IS BEING DELETED ==================");

    }


    /**
     * Image 확인 및 저장
     */
    private String verifyAndSaveImg(MultipartFile imgFile, String imgLink, String imgName) {

        if(!(imgFile == null)){
            try {
                BlobInfo blobInfo = gcsService.uploadFileToGCS(imgFile);
                imgLink = blobInfo.getMediaLink();
                imgName = blobInfo.getName();

                log.info("============ IMAGE HAS BEEN SAVED =============");
                log.info("============ IMAGE LINK : " + blobInfo.getMediaLink() + " ============");
                log.info("============ IMAGE NAME : " + blobInfo.getName() + "=============");

            } catch (IOException e) {
                log.error(e.getMessage());
                throw new GcsUploadFailException(e.getMessage());
            }
        }

        return imgLink +","+imgName;
    }
}
