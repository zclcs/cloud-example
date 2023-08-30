package com.zclcs.platform.system.api.bean.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.zclcs.cloud.lib.core.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件 Entity
 *
 * @author zclcs
 * @since 2021-10-18 10:37:21.262
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table("system_minio_file")
public class MinioFile extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件id
     */
    @Id(value = "id", keyType = KeyType.Generator)
    private String id;

    /**
     * 桶id
     */
    @Column("bucket_id")
    private Long bucketId;

    /**
     * 文件名称
     */
    @Column("file_name")
    private String fileName;

    /**
     * 原文件名称
     */
    @Column("original_file_name")
    private String originalFileName;

    /**
     * 文件路径
     */
    @Column("file_path")
    private String filePath;

    /**
     * 内容类型
     */
    @Column("content_type")
    private String contentType;

}