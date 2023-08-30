package com.zclcs.platform.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.base.BaseRsp;
import com.zclcs.cloud.lib.core.constant.Strings;
import com.zclcs.cloud.lib.core.exception.MyException;
import com.zclcs.cloud.lib.core.utils.RspUtil;
import com.zclcs.common.minio.starter.utils.MinioUtil;
import com.zclcs.platform.system.api.bean.entity.MinioFile;
import com.zclcs.platform.system.api.bean.vo.MinioFileVo;
import com.zclcs.platform.system.service.MinioService;
import io.minio.StatObjectResponse;
import io.minio.errors.MinioException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件
 *
 * @author zclcs
 * @since 2021-10-18 10:37:21.262
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class MinioFileController {

    private final MinioService minioService;

    private final MinioUtil minioUtil;

    /**
     * 文件查询（分页）
     * 权限: file:view
     *
     * @see MinioService#findMinioFilePage(BasePageAo, MinioFileVo)
     */
    @GetMapping
    @SaCheckPermission("file:view")
    public BaseRsp<BasePage<MinioFileVo>> findMinioFilePage(@Validated BasePageAo basePageAo, @Validated MinioFileVo minioFileVo) {
        BasePage<MinioFileVo> page = this.minioService.findMinioFilePage(basePageAo, minioFileVo);
        return RspUtil.data(page);
    }

    /**
     * 文件查询（集合）
     * 权限: file:view
     *
     * @see MinioService#findMinioFileList(MinioFileVo)
     */
    @GetMapping("list")
    @SaCheckPermission("file:view")
    public BaseRsp<List<MinioFileVo>> findMinioFileList(@Validated MinioFileVo minioFileVo) {
        List<MinioFileVo> list = this.minioService.findMinioFileList(minioFileVo);
        return RspUtil.data(list);
    }

    /**
     * 文件查询（单个）
     * 权限: file:view
     *
     * @see MinioService#findMinioFile(MinioFileVo)
     */
    @GetMapping("one")
    @SaCheckPermission("file:view")
    public BaseRsp<MinioFileVo> findMinioFile(@Validated MinioFileVo minioFileVo) {
        MinioFileVo minioFile = this.minioService.findMinioFile(minioFileVo);
        return RspUtil.data(minioFile);
    }

    /**
     * 新增文件
     *
     * @param file       文件
     * @param bucketName 桶（相当于文件夹）
     * @return {@link MinioFile}
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@SaCheckPermission("file:add")
    public BaseRsp<MinioFile> addMinioFile(@RequestPart(value = "file") MultipartFile file,
                                           String bucketName) throws IOException {
        return RspUtil.data(this.minioService.createMinioFile(file, bucketName));
    }

    /**
     * 删除文件
     * 权限: file:delete
     *
     * @param fileIds 文件id集合(,分隔)
     */
    @DeleteMapping("/{fileIds}")
    @SaCheckPermission("file:delete")
    public void deleteMinioFile(@NotBlank(message = "{required}") @PathVariable String fileIds) {
        List<String> ids = Arrays.stream(fileIds.split(Strings.COMMA)).collect(Collectors.toList());
        this.minioService.deleteMinioFile(ids);
    }

    /**
     * 浏览图片或下载文件
     * 用于权限不是读/写的桶
     *
     * @param fileId   文件id
     * @param response 返回
     * @throws Exception 文件不存在
     */
    @GetMapping("/preViewPicture/{fileId}")
    //@SaCheckPermission("file:view")
    public void preViewPicture(@NotBlank(message = "{required}") @PathVariable("fileId") String fileId, HttpServletResponse response) throws Exception {
        MinioFileVo minioFile = minioService.findMinioFile(MinioFileVo.builder().id(fileId).build());
        if (minioFile == null) {
            throw new MyException("文件不存在");
        }
        StatObjectResponse statObjectResponse = minioUtil.statObject(minioFile.getBucketName(), minioFile.getFileName());
        response.setContentType(statObjectResponse.contentType());
        try (ServletOutputStream out = response.getOutputStream()) {
            InputStream stream = minioUtil.getObject(minioFile.getBucketName(), minioFile.getFileName());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();
            out.write(bytes);
            out.flush();
        }
    }

    /**
     * 下载文件
     * 用于权限不是读/写的桶
     * 返回的二进制流
     *
     * @param fileId 文件id
     * @return 二进制流
     * @throws Exception 文件不存在
     */
    @GetMapping("/download/{fileId}")
    //@SaCheckPermission("file:download")
    public ResponseEntity<byte[]> download(@NotBlank(message = "{required}") @PathVariable("fileId") String fileId) throws Exception {
        MinioFileVo minioFile = minioService.findMinioFile(MinioFileVo.builder().id(fileId).build());
        if (minioFile == null) {
            throw new MyException("文件不存在");
        }
        ResponseEntity<byte[]> responseEntity = null;
        InputStream stream = null;
        ByteArrayOutputStream output = null;
        try {
            stream = minioUtil.getObject(minioFile.getBucketName(), minioFile.getFileName());
            if (stream == null) {
                log.error("文件不存在");
                throw new MyException("文件不存在");
            }
            //用于转换byte
            output = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = stream.read(buffer))) {
                output.write(buffer, 0, n);
            }
            byte[] bytes = output.toByteArray();

            //设置header
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Accept-Ranges", "bytes");
            httpHeaders.add("Content-Length", String.valueOf(bytes.length));
            httpHeaders.add("Content-disposition", "attachment; filename=" + minioFile.getFileName());
            httpHeaders.add("Content-Type", "text/plain;charset=utf-8");
            responseEntity = new ResponseEntity<>(bytes, httpHeaders, HttpStatus.CREATED);

        } catch (MinioException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (output != null) {
                output.close();
            }
        }
        return responseEntity;
    }
}