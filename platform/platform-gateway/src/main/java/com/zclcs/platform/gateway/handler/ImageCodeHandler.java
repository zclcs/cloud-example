package com.zclcs.platform.gateway.handler;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.zclcs.common.core.constant.ImageTypeConstant;
import com.zclcs.common.core.constant.RedisCachePrefixConstant;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.properties.MyValidateCodeProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author zclcs
 * 验证码生成逻辑处理类
 */
@Slf4j
@RequiredArgsConstructor
public class ImageCodeHandler implements HandlerFunction<ServerResponse> {

    private final RedisService redisService;

    private final MyValidateCodeProperties myValidateCodeProperties;

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        Captcha captcha = createCaptcha(myValidateCodeProperties);

        String result = captcha.text();

        // 保存验证码信息
        Optional<String> randomStr = serverRequest.queryParam("randomStr");
        randomStr.ifPresent(s -> redisService.set(RedisCachePrefixConstant.CODE_PREFIX + s, result,
                myValidateCodeProperties.getTime()));

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.out(os);

        return ServerResponse.status(HttpStatus.OK).contentType(MediaType.valueOf(StrUtil.equalsIgnoreCase(myValidateCodeProperties.getType(), ImageTypeConstant.GIF) ? MediaType.IMAGE_GIF_VALUE : MediaType.IMAGE_PNG_VALUE))
                .body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
    }

    private Captcha createCaptcha(MyValidateCodeProperties code) {
        Captcha captcha;
        if (StrUtil.equalsIgnoreCase(code.getType(), ImageTypeConstant.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

}
