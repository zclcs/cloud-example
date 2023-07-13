package com.zclcs.common.minio.starter.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author zclcs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileUploadVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 生成的文件名称
     */
    private String genFileName;

    /**
     * 原文件名称
     */
    private String originalFileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * http 地址
     */
    private String urlHttp;

    /**
     * 域名 地址
     */
    private String urlDomain;
}
