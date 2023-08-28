package com.zclcs.cloud.lib.core.properties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * @author zclcs
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@RefreshScope
@ConfigurationProperties(prefix = "my")
public class GlobalProperties {

    /**
     * 服务缓存前缀
     */
    private String redisCachePrefix;

    /**
     * 默认密码
     */
    private String defaultPassword;

    /**
     * 允许的文件类型
     * application/msword	word(.doc)
     * application/vnd.ms-powerpoint	powerpoint(.ppt)
     * application/vnd.ms-excel	excel(.xls)
     * application/vnd.openxmlformats-officedocument.wordprocessingml.document	word(.docx)
     * application/vnd.openxmlformats-officedocument.presentationml.presentation	powerpoint(.pptx)
     * application/vnd.openxmlformats-officedocument.spreadsheetml.sheet	excel(.xlsx)
     * application/x-rar-compressed	rar
     * application/zip	zip
     * application/pdf	pdf
     * video/*	视频文件
     * image/*	图片文件
     * text/plain	纯文本
     * text/css	css文件
     * text/html	html文件
     * text/x-java-source	java源代码
     * text/x-csrc	c源代码
     * text/x-c++src	c++源代码
     */
    private List<String> allowFileType;

}
