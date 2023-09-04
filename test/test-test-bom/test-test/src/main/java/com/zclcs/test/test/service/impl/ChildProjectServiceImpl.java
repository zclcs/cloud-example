package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.metadata.data.CellData;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
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
import java.util.List;
import java.util.Map;

import static com.zclcs.test.test.api.bean.entity.table.ChildProjectTableDef.CHILD_PROJECT;

/**
 * 工程信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-04 20:16:49.255
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
        });
        simpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "工程信息", ChildProjectExcelVo.class, childProjectVo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<ChildProject> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public ChildProject toBean(Map<String, String> cellData) {
                ChildProject childProject = new ChildProject();
                childProject.setChildProjectId(Long.valueOf(cellData.get("childProjectId")));
                childProject.setChildProjectName(cellData.get("childProjectName"));
                childProject.setProjectId(Long.valueOf(cellData.get("projectId")));
                childProject.setLocation(cellData.get("location"));
                childProject.setChildProjectSize(cellData.get("childProjectSize"));
                childProject.setPrice(cellData.get("price"));
                childProject.setContractStartDate(LocalDate.parse(cellData.get("contractStartDate"), DatePattern.NORM_DATE_FORMATTER));
                childProject.setContractEndDate(LocalDate.parse(cellData.get("contractEndDate"), DatePattern.NORM_DATE_FORMATTER));
                childProject.setConstructionPermit(cellData.get("constructionPermit"));
                childProject.setPermitGrantOrg(cellData.get("permitGrantOrg"));
                childProject.setPermitGrantDate(LocalDate.parse(cellData.get("permitGrantDate"), DatePattern.NORM_DATE_FORMATTER));
                childProject.setPermitStatus(cellData.get("permitStatus"));
                childProject.setPermitAttachment(cellData.get("permitAttachment"));
                childProject.setPermitRemark(cellData.get("permitRemark"));
                childProject.setChildProjectStatus(cellData.get("childProjectStatus"));
                childProject.setChildProjectType(cellData.get("childProjectType"));
                childProject.setStructureType(cellData.get("structureType"));
                childProject.setFoundationType(cellData.get("foundationType"));
                childProject.setBaseType(cellData.get("baseType"));
                childProject.setPrjStartDate(LocalDate.parse(cellData.get("prjStartDate"), DatePattern.NORM_DATE_FORMATTER));
                childProject.setPrjCompleteDate(LocalDate.parse(cellData.get("prjCompleteDate"), DatePattern.NORM_DATE_FORMATTER));
                childProject.setPrjLength(cellData.get("prjLength"));
                childProject.setPrjSpan(cellData.get("prjSpan"));
                childProject.setOverGroundFloor(cellData.get("overGroundFloor"));
                childProject.setUnderGroundFloor(cellData.get("underGroundFloor"));
                childProject.setUsefulLife(cellData.get("usefulLife"));
                childProject.setSeismicPrecaution(cellData.get("seismicPrecaution"));
                childProject.setPrjArea(cellData.get("prjArea"));
                return childProject;
            }

            @Override
            public void saveBeans(List<ChildProject> t) {
                saveBatch(t);
            }
        }, ChildProjectExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, CellData<?>> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
