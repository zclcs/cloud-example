package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import com.zclcs.cloud.lib.core.exception.FieldException;
import com.zclcs.cloud.lib.dict.bean.cache.DictItemCacheVo;
import com.zclcs.cloud.lib.dict.utils.DictCacheUtil;
import com.zclcs.common.export.excel.starter.kit.ExcelReadException;
import com.zclcs.common.export.excel.starter.listener.SimpleExportListener;
import com.zclcs.common.export.excel.starter.listener.SimpleImportListener;
import com.zclcs.common.export.excel.starter.service.ExportExcelService;
import com.zclcs.common.export.excel.starter.service.ImportExcelService;
import com.zclcs.common.web.starter.utils.WebUtil;
import com.zclcs.test.test.api.bean.ao.ChildProjectAo;
import com.zclcs.test.test.api.bean.entity.ChildProject;
import com.zclcs.test.test.api.bean.excel.ChildProjectExcelVo;
import com.zclcs.test.test.api.bean.vo.ChildProjectVo;
import com.zclcs.test.test.mapper.ChildProjectMapper;
import com.zclcs.test.test.service.ChildProjectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zclcs.test.test.api.bean.entity.table.ChildProjectTableDef.CHILD_PROJECT;

/**
 * 工程信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-08 16:48:53.770
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChildProjectServiceImpl extends ServiceImpl<ChildProjectMapper, ChildProject> implements ChildProjectService {

    @Override
    public BasePage<ChildProjectVo> findChildProjectPage(BasePageAo basePageAo, ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        Page<ChildProjectVo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ChildProjectVo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<ChildProjectVo> findChildProjectList(ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.selectListByQueryAs(queryWrapper, ChildProjectVo.class);
    }

    @Override
    public ChildProjectVo findChildProject(ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ChildProjectVo.class);
    }

    @Override
    public Long countChildProject(ChildProjectVo childProjectVo) {
    QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(ChildProjectVo childProjectVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                CHILD_PROJECT.CHILD_PROJECT_ID,
                CHILD_PROJECT.CHILD_PROJECT_NAME,
                CHILD_PROJECT.PROJECT_ID,
                CHILD_PROJECT.LOCATION,
                CHILD_PROJECT.CHILD_PROJECT_SIZE,
                CHILD_PROJECT.PRICE,
                CHILD_PROJECT.CONTRACT_START_DATE,
                CHILD_PROJECT.CONTRACT_END_DATE,
                CHILD_PROJECT.CONSTRUCTION_PERMIT,
                CHILD_PROJECT.PERMIT_GRANT_ORG,
                CHILD_PROJECT.PERMIT_GRANT_DATE,
                CHILD_PROJECT.PERMIT_STATUS,
                CHILD_PROJECT.PERMIT_ATTACHMENT,
                CHILD_PROJECT.PERMIT_REMARK,
                CHILD_PROJECT.CHILD_PROJECT_STATUS,
                CHILD_PROJECT.CHILD_PROJECT_TYPE,
                CHILD_PROJECT.STRUCTURE_TYPE,
                CHILD_PROJECT.FOUNDATION_TYPE,
                CHILD_PROJECT.BASE_TYPE,
                CHILD_PROJECT.PRJ_START_DATE,
                CHILD_PROJECT.PRJ_COMPLETE_DATE,
                CHILD_PROJECT.PRJ_LENGTH,
                CHILD_PROJECT.PRJ_SPAN,
                CHILD_PROJECT.OVER_GROUND_FLOOR,
                CHILD_PROJECT.UNDER_GROUND_FLOOR,
                CHILD_PROJECT.USEFUL_LIFE,
                CHILD_PROJECT.SEISMIC_PRECAUTION,
                CHILD_PROJECT.PRJ_AREA
        );
        // TODO 设置公共查询条件
        return queryWrapper;
   }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChildProject createChildProject(ChildProjectAo childProjectAo) {
        ChildProject childProject = new ChildProject();
        BeanUtil.copyProperties(childProjectAo, childProject);
        this.save(childProject);
        return childProject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ChildProject updateChildProject(ChildProjectAo childProjectAo) {
        ChildProject childProject = new ChildProject();
        BeanUtil.copyProperties(childProjectAo, childProject);
        this.updateById(childProject);
        return childProject;
    }

    @Override
    public ChildProject createOrUpdateChildProject(ChildProjectAo childProjectAo) {
        ChildProject childProject = new ChildProject();
        BeanUtil.copyProperties(childProjectAo, childProject);
        this.saveOrUpdate(childProject);
        return childProject;
    }

    @Override
    public List<ChildProject> createChildProjectBatch(List<ChildProjectAo> childProjectAos) {
        List<ChildProject> childProjectList = new ArrayList<>();
        for (ChildProjectAo childProjectAo : childProjectAos) {
            ChildProject childProject = new ChildProject();
            BeanUtil.copyProperties(childProjectAo, childProject);
            childProjectList.add(childProject);
        }
        saveBatch(childProjectList);
        return childProjectList;
    }

    @Override
    public List<ChildProject> updateChildProjectBatch(List<ChildProjectAo> childProjectAos) {
        List<ChildProject> childProjectList = new ArrayList<>();
        for (ChildProjectAo childProjectAo : childProjectAos) {
            ChildProject childProject = new ChildProject();
            BeanUtil.copyProperties(childProjectAo, childProject);
            childProjectList.add(childProject);
        }
        updateBatch(childProjectList);
        return childProjectList;
    }

    @Override
    public List<ChildProject> createOrUpdateChildProjectBatch(List<ChildProjectAo> childProjectAos) {
        List<ChildProject> childProjectList = new ArrayList<>();
        for (ChildProjectAo childProjectAo : childProjectAos) {
            ChildProject childProject = new ChildProject();
            BeanUtil.copyProperties(childProjectAo, childProject);
            childProjectList.add(childProject);
        }
        saveOrUpdateBatch(childProjectList);
        return childProjectList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChildProject(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void exportExcel(ChildProjectVo childProjectVo) {
        SimpleExportListener<ChildProjectVo, ChildProjectExcelVo> simpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(ChildProjectVo childProjectVo) {
                return countChildProject(childProjectVo);
            }

            @Override
            public List<ChildProjectExcelVo> getDataPaginateAs(ChildProjectVo childProjectVo, Long pageNum, Long pageSize, Long totalRows) {
                QueryWrapper queryWrapper = getQueryWrapper(childProjectVo);
                Page<ChildProjectExcelVo> excelVoPage = mapper.paginateAs(pageNum, pageSize, totalRows, queryWrapper, ChildProjectExcelVo.class);
                return excelVoPage.getRecords();
            }
        }, ChildProjectExcelVo.class.getDeclaredFields());
        simpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "工程信息", ChildProjectExcelVo.class, childProjectVo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<ChildProject, ChildProjectExcelVo> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public ChildProjectExcelVo toExcelVo(Map<String, String> cellData) {
                ChildProjectExcelVo childProjectExcelVo = new ChildProjectExcelVo();
                childProjectExcelVo.setChildProjectId(cellData.get("childProjectId") != null ? Long.valueOf(cellData.get("childProjectId")) : null);
                childProjectExcelVo.setChildProjectName(cellData.get("childProjectName"));
                childProjectExcelVo.setProjectId(cellData.get("projectId") != null ? Long.valueOf(cellData.get("projectId")) : null);
                childProjectExcelVo.setLocation(cellData.get("location"));
                childProjectExcelVo.setChildProjectSize(cellData.get("childProjectSize"));
                childProjectExcelVo.setPrice(cellData.get("price"));
                childProjectExcelVo.setContractStartDate(cellData.get("contractStartDate") != null ? LocalDate.parse(cellData.get("contractStartDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                childProjectExcelVo.setContractEndDate(cellData.get("contractEndDate") != null ? LocalDate.parse(cellData.get("contractEndDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                childProjectExcelVo.setConstructionPermit(cellData.get("constructionPermit"));
                childProjectExcelVo.setPermitGrantOrg(cellData.get("permitGrantOrg"));
                childProjectExcelVo.setPermitGrantDate(cellData.get("permitGrantDate") != null ? LocalDate.parse(cellData.get("permitGrantDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                childProjectExcelVo.setPermitStatus(cellData.get("permitStatus"));
                childProjectExcelVo.setPermitAttachment(cellData.get("permitAttachment"));
                childProjectExcelVo.setPermitRemark(cellData.get("permitRemark"));
                childProjectExcelVo.setChildProjectStatus(cellData.get("childProjectStatus"));
                childProjectExcelVo.setChildProjectType(cellData.get("childProjectType"));
                childProjectExcelVo.setStructureType(cellData.get("structureType"));
                childProjectExcelVo.setFoundationType(cellData.get("foundationType"));
                childProjectExcelVo.setBaseType(cellData.get("baseType"));
                childProjectExcelVo.setPrjStartDate(cellData.get("prjStartDate") != null ? LocalDate.parse(cellData.get("prjStartDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                childProjectExcelVo.setPrjCompleteDate(cellData.get("prjCompleteDate") != null ? LocalDate.parse(cellData.get("prjCompleteDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                childProjectExcelVo.setPrjLength(cellData.get("prjLength"));
                childProjectExcelVo.setPrjSpan(cellData.get("prjSpan"));
                childProjectExcelVo.setOverGroundFloor(cellData.get("overGroundFloor"));
                childProjectExcelVo.setUnderGroundFloor(cellData.get("underGroundFloor"));
                childProjectExcelVo.setUsefulLife(cellData.get("usefulLife"));
                childProjectExcelVo.setSeismicPrecaution(cellData.get("seismicPrecaution"));
                childProjectExcelVo.setPrjArea(cellData.get("prjArea"));
                return childProjectExcelVo;
            }

            @Override
            public ChildProject toBean(ChildProjectExcelVo excelVo) {
                ChildProject childProject = new ChildProject();
                BeanUtil.copyProperties(excelVo, childProject);
                return childProject;
            }

            @Override
            public void saveBeans(List<ChildProject> t) {
                saveBatch(t);
            }
        }, ChildProjectExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, String> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
