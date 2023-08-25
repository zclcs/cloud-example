package com.zclcs.common.export.excel.starter.utils;

import com.alibaba.excel.support.ExcelTypeEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author zclcs
 */
@UtilityClass
public class ExcelUtil {


    public void setResponse(HttpServletResponse response, String fileName, ExcelTypeEnum excelTypeEnum) {
        fileName = String.format("%s%s", URLEncoder.encode(fileName, StandardCharsets.UTF_8), excelTypeEnum.getValue());
        String contentType = MediaTypeFactory.getMediaType(fileName)
                .map(MediaType::toString)
                .orElse("application/vnd.ms-excel");
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.setCharacterEncoding("utf-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename*=utf-8''" + fileName);
    }

}
