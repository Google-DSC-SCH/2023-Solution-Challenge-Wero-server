package com.gdsc.wero.domain.user.api;

import com.gdsc.wero.domain.user.api.dto.request.UserInfoReqDto;
import com.gdsc.wero.domain.user.application.UserService;
import com.gdsc.wero.global.auth.jwt.JwtUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserInfoController {

    private final UserService userInfoService;
    private final JwtUtils jwtUtils;

    @ApiOperation(value = "유저 정보 저장 api", notes = "유저 정보를 입력 후 저장한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 정보 저장 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/info")
    public String saveUserInfo(@RequestBody UserInfoReqDto userInfoReqDto, HttpServletRequest request){

        String jwtFromHeader = jwtUtils.getJwtFromHeader(request);
        Map<String, Object> userEmailAndProviderFromJwtToken = jwtUtils.getUserEmailAndProviderFromJwtToken(jwtFromHeader);
        String email = (String)userEmailAndProviderFromJwtToken.get("email");
        String provider = (String)userEmailAndProviderFromJwtToken.get("provider");

        userInfoService.saveOrUpdateUserInfo(userInfoReqDto, email, provider);

        log.info("================== USER INFO HAS BEEN SAVED OR UPDATED ===================");

        return "success";
    }
}
