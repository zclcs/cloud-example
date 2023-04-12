package com.zclcs.platform.system.controller;

import com.zclcs.common.core.base.BasePage;
import com.zclcs.common.core.base.BasePageAo;
import com.zclcs.common.core.base.BaseRsp;
import com.zclcs.common.core.constant.StringConstant;
import com.zclcs.common.core.exception.MyException;
import com.zclcs.common.core.utils.RspUtil;
import com.zclcs.platform.system.api.entity.MinioFile;
import com.zclcs.platform.system.api.entity.vo.MinioFileVo;
import com.zclcs.platform.system.service.MinioFileService;
import com.zclcs.platform.system.utils.MinioUtil;
import io.minio.StatObjectResponse;
import io.minio.errors.MinioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件 Controller
 *
 * @author zclcs
 * @date 2021-10-18 10:37:21.262
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
@Tag(name = "文件")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MinioFileController {

    private final MinioFileService minioFileService;

    private final MinioUtil minioUtil;

    @GetMapping
    @Operation(summary = "文件查询（分页）")
    @PreAuthorize("hasAuthority('file:view')")
    public BaseRsp<BasePage<MinioFileVo>> findMinioFilePage(@Valid BasePageAo basePageAo, MinioFileVo minioFileVo) {
        BasePage<MinioFileVo> page = this.minioFileService.findMinioFilePage(basePageAo, minioFileVo);
        return RspUtil.data(page);
    }

    @GetMapping("list")
    @Operation(summary = "文件查询（集合）")
    @PreAuthorize("hasAuthority('file:view')")
    public BaseRsp<List<MinioFileVo>> findMinioFileList(MinioFileVo minioFileVo) {
        List<MinioFileVo> list = this.minioFileService.findMinioFileList(minioFileVo);
        return RspUtil.data(list);
    }

    @GetMapping("one")
    @Operation(summary = "文件查询（单个）")
    @PreAuthorize("hasAuthority('file:view')")
    public BaseRsp<MinioFileVo> findMinioFile(MinioFileVo minioFileVo) {
        MinioFileVo minioFile = this.minioFileService.findMinioFile(minioFileVo);
        return RspUtil.data(minioFile);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //@PreAuthorize("hasAuthority('file:add')")
    @Operation(summary = "新增文件")
    public BaseRsp<MinioFile> addMinioFile(@Parameter(name = "file", description = "文件", required = true) @RequestPart(value = "file") MultipartFile file,
                                           @Parameter(name = "bucketName", description = "桶（相当于文件夹）") String bucketName) {
        return RspUtil.data(this.minioFileService.createMinioFile(file, bucketName));
    }

    @DeleteMapping("/{fileIds}")
    @PreAuthorize("hasAuthority('file:delete')")
    @Operation(summary = "删除文件")
    @Parameters({
            @Parameter(name = "fileIds", description = "文件id集合(,分隔)", required = true, in = ParameterIn.PATH)
    })
    public void deleteMinioFile(@NotBlank(message = "{required}") @PathVariable String fileIds) {
        List<String> ids = Arrays.stream(fileIds.split(StringConstant.COMMA)).collect(Collectors.toList());
        this.minioFileService.deleteMinioFile(ids);
    }

    @GetMapping("/preViewPicture/{fileId}")
    @Operation(summary = "浏览图片或下载文件", description = "用于权限不是读/写的桶")
    //@PreAuthorize("hasAuthority('file:view')")
    @Parameters({
            @Parameter(name = "fileId", description = "文件id", required = true, in = ParameterIn.PATH)
    })
    public void preViewPicture(@NotBlank(message = "{required}") @PathVariable("fileId") String fileId, HttpServletResponse response) throws Exception {
        MinioFileVo minioFile = minioFileService.findMinioFile(MinioFileVo.builder().id(fileId).build());
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

    @GetMapping("/download/{fileId}")
    @Operation(summary = "下载文件", description = "用于权限不是读/写的桶")
    //@PreAuthorize("hasAuthority('file:download')")
    @Parameters({
            @Parameter(name = "fileId", description = "文件id", required = true, in = ParameterIn.PATH)
    })
    public ResponseEntity<byte[]> download(@NotBlank(message = "{required}") @PathVariable("fileId") String fileId) throws Exception {
        MinioFileVo minioFile = minioFileService.findMinioFile(MinioFileVo.builder().id(fileId).build());
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
                throw new RuntimeException("文件不存在");
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
            httpHeaders.add("Content-Length", bytes.length + "");
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