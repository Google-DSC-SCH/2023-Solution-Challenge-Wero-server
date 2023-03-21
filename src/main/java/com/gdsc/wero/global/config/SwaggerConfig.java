package com.gdsc.wero.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트 추출
                .paths(PathSelectors.ant("/api/**")) // 그 중 /api로 시작하는 URL들만 필터릉
                .build();
    }

    // swagger-ui/index.html 페이지 커스텀
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("GDSC - wero")
                .version("1.0.0")
                .description("wero api spec")
                .build();
    }

}
