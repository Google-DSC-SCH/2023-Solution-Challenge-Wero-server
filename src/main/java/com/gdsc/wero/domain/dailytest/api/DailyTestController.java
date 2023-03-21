package com.gdsc.wero.domain.dailytest.api;

import com.gdsc.wero.domain.dailytest.api.dto.request.DailyTestSaveReqDto;
import com.gdsc.wero.domain.dailytest.api.dto.response.DailyTestResListDto;
import com.gdsc.wero.domain.dailytest.application.DailyTestService;
import com.gdsc.wero.global.auth.jwt.JwtUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/daily")
public class DailyTestController {

    private final DailyTestService dailyTestService;
    private final JwtUtils jwtUtils;

    @ApiOperation(value = "main 페이지 daily test 페이징 api", notes = "main 페이지 진입 시 및 양 옆 버튼 클릭 시 daily test 7개씩 페이징 처리해 리턴한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "daily test 7일치 리턴 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/paging")
    public ResponseEntity<DailyTestResListDto> getDailyTestByWeek(Pageable pageable, HttpServletRequest request) {

        String jwtFromHeader = jwtUtils.getJwtFromHeader(request);
        Map<String, Object> userEmailAndProviderFromJwtToken = jwtUtils.getUserEmailAndProviderFromJwtToken(jwtFromHeader);
        String email = (String)userEmailAndProviderFromJwtToken.get("email");
        String provider = (String)userEmailAndProviderFromJwtToken.get("provider");

        DailyTestResListDto dailyTestByWeek = dailyTestService.getDailyTestByWeek(pageable, email, provider);

        return new ResponseEntity<>(dailyTestByWeek, HttpStatus.OK);

    }


    @ApiOperation(value = "daily test 저장 api", notes = " daily test 작성 후 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "daily test 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/test")
    public String saveTest(@RequestBody DailyTestSaveReqDto dailyTestSaveReqDto, HttpServletRequest request){

        String jwtFromHeader = jwtUtils.getJwtFromHeader(request);
        Map<String, Object> userEmailAndProviderFromJwtToken = jwtUtils.getUserEmailAndProviderFromJwtToken(jwtFromHeader);
        String email = (String)userEmailAndProviderFromJwtToken.get("email");
        String provider = (String)userEmailAndProviderFromJwtToken.get("provider");

        dailyTestService.saveDailyTest(dailyTestSaveReqDto, email, provider);

        log.info("=============== DAILY TEST HAS BENN SAVED ===============");

        return "success";
    }
}
