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
import com.zclcs.test.test.api.bean.ao.ProjectAo;
import com.zclcs.test.test.api.bean.entity.Project;
import com.zclcs.test.test.api.bean.excel.ProjectExcelVo;
import com.zclcs.test.test.api.bean.vo.ProjectVo;
import com.zclcs.test.test.mapper.ProjectMapper;
import com.zclcs.test.test.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zclcs.test.test.api.bean.entity.table.ProjectTableDef.PROJECT;

/**
 * 项目信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-08 16:48:48.873
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Override
    public BasePage<ProjectVo> findProjectPage(BasePageAo basePageAo, ProjectVo projectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        Page<ProjectVo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ProjectVo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<ProjectVo> findProjectList(ProjectVo projectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        return this.mapper.selectListByQueryAs(queryWrapper, ProjectVo.class);
    }

    @Override
    public ProjectVo findProject(ProjectVo projectVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ProjectVo.class);
    }

    @Override
    public Long countProject(ProjectVo projectVo) {
    QueryWrapper queryWrapper = getQueryWrapper(projectVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(ProjectVo projectVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                PROJECT.PROJECT_ID,
                PROJECT.PROJECT_CODE,
                PROJECT.PROJECT_NAME,
                PROJECT.DESCRIPTION,
                PROJECT.CATEGORY,
                PROJECT.BUILD_PLAN_NUM,
                PROJECT.CHILD_PROJECT_PLAN_NUM,
                PROJECT.AREA_CODE,
                PROJECT.IS_LEAD_BY_CITY,
                PROJECT.INVEST,
                PROJECT.BUILDING_AREA,
                PROJECT.BUILDING_LENGTH,
                PROJECT.START_DATE,
                PROJECT.COMPLETE_DATE,
                PROJECT.PLANNED_START_DATE,
                PROJECT.PLANNED_COMPLETE_DATE,
                PROJECT.LINK_MAN,
                PROJECT.LINK_PHONE,
                PROJECT.PROJECT_STATUS,
                PROJECT.LNG,
                PROJECT.LAT,
                PROJECT.ADDRESS,
                PROJECT.APPROVAL_NUM,
                PROJECT.APPROVAL_LEVEL_NUM,
                PROJECT.PROJECT_SIZE,
                PROJECT.PROPERTY_NUM,
                PROJECT.FUNCTION_NUM,
                PROJECT.FUNCTIONAL_UNIT,
                PROJECT.MAJOR_PROJECT,
                PROJECT.LAST_ATTEND_TIME
        );
        // TODO 设置公共查询条件
        return queryWrapper;
   }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Project createProject(ProjectAo projectAo) {
        Project project = new Project();
        BeanUtil.copyProperties(projectAo, project);
        this.save(project);
        return project;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Project updateProject(ProjectAo projectAo) {
        Project project = new Project();
        BeanUtil.copyProperties(projectAo, project);
        this.updateById(project);
        return project;
    }

    @Override
    public Project createOrUpdateProject(ProjectAo projectAo) {
        Project project = new Project();
        BeanUtil.copyProperties(projectAo, project);
        this.saveOrUpdate(project);
        return project;
    }

    @Override
    public List<Project> createProjectBatch(List<ProjectAo> projectAos) {
        List<Project> projectList = new ArrayList<>();
        for (ProjectAo projectAo : projectAos) {
            Project project = new Project();
            BeanUtil.copyProperties(projectAo, project);
            projectList.add(project);
        }
        saveBatch(projectList);
        return projectList;
    }

    @Override
    public List<Project> updateProjectBatch(List<ProjectAo> projectAos) {
        List<Project> projectList = new ArrayList<>();
        for (ProjectAo projectAo : projectAos) {
            Project project = new Project();
            BeanUtil.copyProperties(projectAo, project);
            projectList.add(project);
        }
        updateBatch(projectList);
        return projectList;
    }

    @Override
    public List<Project> createOrUpdateProjectBatch(List<ProjectAo> projectAos) {
        List<Project> projectList = new ArrayList<>();
        for (ProjectAo projectAo : projectAos) {
            Project project = new Project();
            BeanUtil.copyProperties(projectAo, project);
            projectList.add(project);
        }
        saveOrUpdateBatch(projectList);
        return projectList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProject(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void exportExcel(ProjectVo projectVo) {
        SimpleExportListener<ProjectVo, ProjectExcelVo> simpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(ProjectVo projectVo) {
                return countProject(projectVo);
            }

            @Override
            public List<ProjectExcelVo> getDataPaginateAs(ProjectVo projectVo, Long pageNum, Long pageSize, Long totalRows) {
                QueryWrapper queryWrapper = getQueryWrapper(projectVo);
                Page<ProjectExcelVo> excelVoPage = mapper.paginateAs(pageNum, pageSize, totalRows, queryWrapper, ProjectExcelVo.class);
                return excelVoPage.getRecords();
            }
        }, ProjectExcelVo.class.getDeclaredFields());
        simpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "项目信息", ProjectExcelVo.class, projectVo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<Project, ProjectExcelVo> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public ProjectExcelVo toExcelVo(Map<String, String> cellData) {
                ProjectExcelVo projectExcelVo = new ProjectExcelVo();
                projectExcelVo.setProjectId(cellData.get("projectId") != null ? Long.valueOf(cellData.get("projectId")) : null);
                projectExcelVo.setProjectCode(cellData.get("projectCode"));
                projectExcelVo.setProjectName(cellData.get("projectName"));
                projectExcelVo.setDescription(cellData.get("description"));
                projectExcelVo.setCategory(cellData.get("category"));
                projectExcelVo.setBuildPlanNum(cellData.get("buildPlanNum"));
                projectExcelVo.setChildProjectPlanNum(cellData.get("childProjectPlanNum"));
                String areaCodeDictName = "area_code";
                String provinceCode = DictCacheUtil.getDictValue(areaCodeDictName, cellData.get("province"));
                DictItemCacheVo province = DictCacheUtil.getDict(areaCodeDictName, provinceCode);
                if (province == null) {
                    throw new FieldException("省输入非法值");
                }
                String cityCode = DictCacheUtil.getDictValue("area_code", province.getValue(), cellData.get("city"));
                DictItemCacheVo city = DictCacheUtil.getDict(areaCodeDictName, cityCode);
                if (city == null) {
                    throw new FieldException("市输入非法值");
                }
                String area = DictCacheUtil.getDictValue("area_code", city.getValue(), cellData.get("areaCode"));
                projectExcelVo.setAreaCode(area);
                projectExcelVo.setIsLeadByCity(cellData.get("isLeadByCity"));
                projectExcelVo.setInvest(cellData.get("invest"));
                projectExcelVo.setBuildingArea(cellData.get("buildingArea"));
                projectExcelVo.setBuildingLength(cellData.get("buildingLength"));
                projectExcelVo.setStartDate(cellData.get("startDate") != null ? LocalDate.parse(cellData.get("startDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                projectExcelVo.setCompleteDate(cellData.get("completeDate") != null ? LocalDate.parse(cellData.get("completeDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                projectExcelVo.setPlannedStartDate(cellData.get("plannedStartDate") != null ? LocalDate.parse(cellData.get("plannedStartDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                projectExcelVo.setPlannedCompleteDate(cellData.get("plannedCompleteDate") != null ? LocalDate.parse(cellData.get("plannedCompleteDate"), DatePattern.NORM_DATE_FORMATTER) : null);
                projectExcelVo.setLinkMan(cellData.get("linkMan"));
                projectExcelVo.setLinkPhone(cellData.get("linkPhone"));
                projectExcelVo.setProjectStatus(cellData.get("projectStatus"));
                projectExcelVo.setLng(cellData.get("lng") != null ? new BigDecimal(cellData.get("lng")) : null);
                projectExcelVo.setLat(cellData.get("lat") != null ? new BigDecimal(cellData.get("lat")) : null);
                projectExcelVo.setAddress(cellData.get("address"));
                projectExcelVo.setApprovalNum(cellData.get("approvalNum"));
                projectExcelVo.setApprovalLevelNum(cellData.get("approvalLevelNum"));
                projectExcelVo.setProjectSize(cellData.get("projectSize"));
                projectExcelVo.setPropertyNum(cellData.get("propertyNum"));
                projectExcelVo.setFunctionNum(cellData.get("functionNum"));
                projectExcelVo.setFunctionalUnit(cellData.get("functionalUnit"));
                projectExcelVo.setMajorProject(cellData.get("majorProject"));
                projectExcelVo.setLastAttendTime(cellData.get("lastAttendTime") != null ? LocalDateTime.parse(cellData.get("lastAttendTime"), DatePattern.NORM_DATETIME_FORMATTER) : null);
                return projectExcelVo;
            }

            @Override
            public Project toBean(ProjectExcelVo excelVo) {
                Project project = new Project();
                BeanUtil.copyProperties(excelVo, project);
                return project;
            }

            @Override
            public void saveBeans(List<Project> t) {
                saveBatch(t);
            }
        }, ProjectExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, String> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
