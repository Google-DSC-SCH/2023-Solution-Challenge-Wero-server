package com.gdsc.wero.domain.reply.application;

import com.gdsc.wero.domain.board.domain.Board;
import com.gdsc.wero.domain.board.domain.repository.BoardRepository;
import com.gdsc.wero.domain.board.exception.BoardNotExistException;
import com.gdsc.wero.domain.reply.api.dto.request.ReplyReqDto;
import com.gdsc.wero.domain.reply.api.dto.response.ReplyInfoResDto;
import com.gdsc.wero.domain.reply.api.dto.response.ReplyResListDto;
import com.gdsc.wero.domain.reply.domain.Reply;
import com.gdsc.wero.domain.reply.domain.repository.ReplyRepository;
import com.gdsc.wero.domain.reply.exception.ReplyNotExistException;
import com.gdsc.wero.domain.user.domain.User;
import com.gdsc.wero.domain.user.domain.repository.UserRepository;
import com.gdsc.wero.global.exception.errortype.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * Reply List 조회
     */
    public ReplyResListDto getReplyList(Long boardId){

        List<ReplyInfoResDto> collect = replyRepository.getReplyList(boardId)
                .stream()
                .map(ReplyInfoResDto::new)
                .collect(Collectors.toList());


        return new ReplyResListDto(collect);
    }


    /**
     * Reply 저장
     */
    public void saveReply(ReplyReqDto replyReqDto, Long boardId, String email, String provider){

        // 유저 조회
        User user = userRepository.findUserByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException("User does not exist | email : " + email));

        // 게시물 조회
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BoardNotExistException("This Post does not exist | boardId : " + boardId));

        Reply reply = Reply.createReply(replyReqDto, user, board);

        replyRepository.save(reply);

        log.info("================ REPLY IS BEING SAVED=================");

    }

    /**
     * Reply 수정
     */
    public void updateReply(ReplyReqDto replyReqDto, Long replyId) {

        Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new ReplyNotExistException("This reply does not exist | replyId : " + replyId));

        reply.updateReply(replyReqDto);

        log.info("================ REPLY IS BEING UPDATED==================");

    }

    /**
     * Reply 삭제
     */
    public void deleteReply(Long replyId){

        replyRepository.deleteById(replyId);

        log.info("================ REPLY IS BEING DELETED ==================");
    }


}
