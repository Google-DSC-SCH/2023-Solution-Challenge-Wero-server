package com.gdsc.wero.domain.mbti.api;

import com.gdsc.wero.domain.mbti.api.dto.request.MbtiReqDto;
import com.gdsc.wero.domain.mbti.api.dto.response.MbtiInfoResDto;
import com.gdsc.wero.domain.mbti.application.MbtiService;
import com.gdsc.wero.global.auth.jwt.JwtUtils;
import com.gdsc.wero.global.exception.errortype.SaveFailException;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mbti")
public class MbtiController {

    private final MbtiService mbtiService;
    private final JwtUtils jwtUtils;

    @ApiOperation(value = "mbti 페이지 진입 api", notes = "mbti 페이지 진입 시 개인의 mbti를 리턴한다. 만약 없을 경우 null을 리턴한다")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "mbti 혹은 null 리턴 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("")
    public MbtiInfoResDto goToMbti(HttpServletRequest request) {

        String jwtFromHeader = jwtUtils.getJwtFromHeader(request);
        Map<String, Object> userEmailAndProviderFromJwtToken = jwtUtils.getUserEmailAndProviderFromJwtToken(jwtFromHeader);
        String email = (String)userEmailAndProviderFromJwtToken.get("email");
        String provider = (String)userEmailAndProviderFromJwtToken.get("provider");

        MbtiInfoResDto mbti = mbtiService.getMbti(email, provider);

        log.info("================= mbti 리턴 ==================");

        return mbti;
    }

    @ApiOperation(value = "mbti 테스트 저장 및 수정 api", notes = "mbti 설문완료 후 저장 및 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "저장 및 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PutMapping("/test")
    public String testMbti(@RequestBody MbtiReqDto mbtiReqDto, HttpServletRequest request) {


        String jwtFromHeader = jwtUtils.getJwtFromHeader(request);
        Map<String, Object> userEmailAndProviderFromJwtToken = jwtUtils.getUserEmailAndProviderFromJwtToken(jwtFromHeader);
        String email = (String)userEmailAndProviderFromJwtToken.get("email");
        String provider = (String)userEmailAndProviderFromJwtToken.get("provider");

        mbtiService.saveOrUpdateMbti(mbtiReqDto, email, provider);

        log.info("================== MBTI TEST HAS BEEN SAVED ===================");

        return "success";
    }

}
