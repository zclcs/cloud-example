package com.zclcs.server.dict.service.impl;

import cn.hutool.json.JSONUtil;
import com.houkunlin.system.dict.starter.bean.DictValueVo;
import com.rabbitmq.client.Channel;
import com.zclcs.common.core.constant.MyConstant;
import com.zclcs.common.core.entity.CanalBinLogInfo;
import com.zclcs.common.core.entity.dict.DictTable;
import com.zclcs.common.core.entity.dict.DictTableLevel;
import com.zclcs.common.core.service.HandleCacheService;
import com.zclcs.server.dict.provider.DictProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zclcs
 */
@Service(value = "handleServerDictCacheService")
@RequiredArgsConstructor
@Slf4j
public class HandleServerDictCacheServiceImpl implements HandleCacheService {

    private static final String DICT_TABLE = "dict_table";
    private static final String DICT_TABLE_LEVEL = "dict_table_level";
    private final DictProvider dictProvider;

    @Override
    public void handleCache(CanalBinLogInfo canalBinLogInfo, long deliveryTag, Channel channel) throws Exception {
        // ddl语句直接break
        if (!canalBinLogInfo.getIsDdl()) {
            switch (canalBinLogInfo.getTable()) {
                case DICT_TABLE:
                    List<DictTable> dictTables = JSONUtil.toList(canalBinLogInfo.getData(), DictTable.class);
                    for (DictTable dictTable : dictTables) {
                        refreshCache(canalBinLogInfo, dictTable.getDictName(), dictTable.getCode(), dictTable.getTitle());
                    }
                    break;
                case DICT_TABLE_LEVEL:
                    List<DictTableLevel> dictTableLevels = JSONUtil.toList(canalBinLogInfo.getData(), DictTableLevel.class);
                    for (DictTableLevel dictTableLevel : dictTableLevels) {
                        refreshCache(canalBinLogInfo, dictTableLevel.getDictName(), dictTableLevel.getCode(), dictTableLevel.getTitle());
                        refreshCache(canalBinLogInfo, dictTableLevel.getDictName(), dictTableLevel.getParentCode(), dictTableLevel.getCode(), dictTableLevel.getTitle());
                    }
                    break;
                default:
                    break;
            }
        }
        channel.basicAck(deliveryTag, false);
    }

    private void refreshCache(CanalBinLogInfo canalBinLogInfo, String dictName, String code, String title) {
        switch (canalBinLogInfo.getType()) {
            case MyConstant.INSERT:
            case MyConstant.UPDATE:
                refreshDictCache(dictName, code, title);
                break;
            case MyConstant.DELETE:
                deleteDictCache(dictName, code);
                break;
            default:
                break;
        }
    }

    private void refreshCache(CanalBinLogInfo canalBinLogInfo, String dictName, String parentCode, String code, String title) {
        switch (canalBinLogInfo.getType()) {
            case MyConstant.INSERT:
            case MyConstant.UPDATE:
                if (parentCode.equals(MyConstant.TOP_PARENT_CODE)) {
                    parentCode = "";
                }
                refreshDictCache(dictName, parentCode, code, title);
                break;
            case MyConstant.DELETE:
                deleteDictCache(dictName, code);
                break;
            default:
                break;
        }
    }

    private void refreshDictCache(String dictName, String code, String title) {
        dictProvider.refreshDictCache(DictValueVo.builder()
                .dictType(dictName)
                .value(code)
                .title(title)
                .build());
    }

    private void refreshDictCache(String dictName, String parentCode, String code, String title) {
        dictProvider.refreshDictCache(DictValueVo.builder()
                .dictType(dictName)
                .parentValue(parentCode)
                .value(code)
                .title(title)
                .build());
    }

    private void deleteDictCache(String dictName, String code) {
        dictProvider.refreshDictCache(DictValueVo.builder()
                .dictType(dictName)
                .value(code)
                .title(null)
                .build());
    }

}
