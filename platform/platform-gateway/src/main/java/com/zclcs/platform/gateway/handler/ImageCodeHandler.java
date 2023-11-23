package com.zclcs.platform.gateway.handler;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.zclcs.cloud.lib.core.constant.ImageType;
import com.zclcs.cloud.lib.core.constant.RedisCachePrefix;
import com.zclcs.cloud.lib.core.exception.ValidateCodeException;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.redis.starter.bean.CacheKey;
import com.zclcs.common.redis.starter.rate.limiter.impl.DefaultRateLimiterClient;
import com.zclcs.common.redis.starter.service.RedisService;
import com.zclcs.platform.gateway.properties.MyValidateCodeProperties;
import com.zclcs.platform.gateway.utils.GatewayUtil;
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

import java.time.Duration;
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
    private final DefaultRateLimiterClient defaultRateLimiterClient;
    private static final int RANDOM_STR_LENGTH = 32;

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        String serverHttpRequestIpAddress = GatewayUtil.getServerHttpRequestIpAddress(serverRequest.exchange().getRequest());
        boolean allowed = defaultRateLimiterClient.isAllowed(
                String.format(RedisCachePrefix.IMAGE_CODE_RATE_LIMIT_KEY_PREFIX, serverHttpRequestIpAddress),
                100L, 1L);
        if (!allowed) {
            return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                    .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                    .body(BodyInserters.fromValue(RspUtil.message("访问频率超限")));
        }
        Captcha captcha = createCaptcha(myValidateCodeProperties);

        String result = captcha.text();

        // 保存验证码信息
        Optional<String> randomStr = serverRequest.queryParam("randomStr");

        randomStr.filter(StrUtil::isNotBlank).orElseThrow(() -> new ValidateCodeException("随机值不能为空"));

        randomStr.filter(s -> s.length() <= RANDOM_STR_LENGTH).orElseThrow(() -> new ValidateCodeException("随机值超长"));

        randomStr.ifPresent(s -> redisService.set(new CacheKey(RedisCachePrefix.CODE_PREFIX + s, Duration.ofSeconds(myValidateCodeProperties.getTime())), result));


        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        captcha.out(os);

        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(StrUtil.equalsIgnoreCase(myValidateCodeProperties.getType(), ImageType.GIF)
                        ? MediaType.IMAGE_GIF_VALUE : MediaType.IMAGE_PNG_VALUE))
                .body(BodyInserters.fromResource(new ByteArrayResource(os.toByteArray())));
    }

    private Captcha createCaptcha(MyValidateCodeProperties code) {
        Captcha captcha;
        if (StrUtil.equalsIgnoreCase(code.getType(), ImageType.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

}
