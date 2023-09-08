package com.zclcs.test.test.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.EasyExcel;
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

import java.util.List;
import java.util.Map;

import static com.zclcs.test.test.api.bean.entity.table.ProjectTableDef.PROJECT;

/**
 * 项目信息 Service实现
 *
 * @author zclcs
 * @since 2023-09-04 20:16:40.541
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
                return null;
            }

            @Override
            public Project toBean(ProjectExcelVo excelVo) {
                return null;
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
