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
import com.zclcs.test.test.api.bean.ao.ProjectCompanyAo;
import com.zclcs.test.test.api.bean.entity.ProjectCompany;
import com.zclcs.test.test.api.bean.excel.ProjectCompanyExcelVo;
import com.zclcs.test.test.api.bean.vo.ProjectCompanyVo;
import com.zclcs.test.test.mapper.ProjectCompanyMapper;
import com.zclcs.test.test.service.ProjectCompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.zclcs.test.test.api.bean.entity.table.ProjectCompanyTableDef.PROJECT_COMPANY;

/**
 * 项目参建单位信息数据 Service实现
 *
 * @author zclcs
 * @since 2023-11-09 16:48:38.746
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectCompanyServiceImpl extends ServiceImpl<ProjectCompanyMapper, ProjectCompany> implements ProjectCompanyService {

    @Override
    public BasePage<ProjectCompanyVo> findProjectCompanyPage(BasePageAo basePageAo, ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        Page<ProjectCompanyVo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ProjectCompanyVo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<ProjectCompanyVo> findProjectCompanyList(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.selectListByQueryAs(queryWrapper, ProjectCompanyVo.class);
    }

    @Override
    public ProjectCompanyVo findProjectCompany(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ProjectCompanyVo.class);
    }

    @Override
    public Long countProjectCompany(ProjectCompanyVo projectCompanyVo) {
    QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(ProjectCompanyVo projectCompanyVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select(
                PROJECT_COMPANY.PROJECT_COMPANY_ID,
                PROJECT_COMPANY.PROJECT_ID,
                PROJECT_COMPANY.COMPANY_ID,
                PROJECT_COMPANY.COMPANY_ROLE,
                PROJECT_COMPANY.MANAGER_NAME,
                PROJECT_COMPANY.MANAGER_ID_CARD_TYPE,
                PROJECT_COMPANY.MANAGER_ID_CARD_NUMBER,
                PROJECT_COMPANY.MANAGER_PHONE
        );
        // TODO 设置公共查询条件
        return queryWrapper;
   }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectCompany createProjectCompany(ProjectCompanyAo projectCompanyAo) {
        ProjectCompany projectCompany = new ProjectCompany();
        BeanUtil.copyProperties(projectCompanyAo, projectCompany);
        this.save(projectCompany);
        return projectCompany;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProjectCompany updateProjectCompany(ProjectCompanyAo projectCompanyAo) {
        ProjectCompany projectCompany = new ProjectCompany();
        BeanUtil.copyProperties(projectCompanyAo, projectCompany);
        this.updateById(projectCompany);
        return projectCompany;
    }

    @Override
    public ProjectCompany createOrUpdateProjectCompany(ProjectCompanyAo projectCompanyAo) {
        ProjectCompany projectCompany = new ProjectCompany();
        BeanUtil.copyProperties(projectCompanyAo, projectCompany);
        this.saveOrUpdate(projectCompany);
        return projectCompany;
    }

    @Override
    public List<ProjectCompany> createProjectCompanyBatch(List<ProjectCompanyAo> projectCompanyAos) {
        List<ProjectCompany> projectCompanyList = new ArrayList<>();
        for (ProjectCompanyAo projectCompanyAo : projectCompanyAos) {
            ProjectCompany projectCompany = new ProjectCompany();
            BeanUtil.copyProperties(projectCompanyAo, projectCompany);
            projectCompanyList.add(projectCompany);
        }
        saveBatch(projectCompanyList);
        return projectCompanyList;
    }

    @Override
    public List<ProjectCompany> updateProjectCompanyBatch(List<ProjectCompanyAo> projectCompanyAos) {
        List<ProjectCompany> projectCompanyList = new ArrayList<>();
        for (ProjectCompanyAo projectCompanyAo : projectCompanyAos) {
            ProjectCompany projectCompany = new ProjectCompany();
            BeanUtil.copyProperties(projectCompanyAo, projectCompany);
            projectCompanyList.add(projectCompany);
        }
        updateBatch(projectCompanyList);
        return projectCompanyList;
    }

    @Override
    public List<ProjectCompany> createOrUpdateProjectCompanyBatch(List<ProjectCompanyAo> projectCompanyAos) {
        List<ProjectCompany> projectCompanyList = new ArrayList<>();
        for (ProjectCompanyAo projectCompanyAo : projectCompanyAos) {
            ProjectCompany projectCompany = new ProjectCompany();
            BeanUtil.copyProperties(projectCompanyAo, projectCompany);
            projectCompanyList.add(projectCompany);
        }
        saveOrUpdateBatch(projectCompanyList);
        return projectCompanyList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProjectCompany(List<Long> ids) {
        this.removeByIds(ids);
    }

    @Override
    public void exportExcel(ProjectCompanyVo projectCompanyVo) {
        SimpleExportListener<ProjectCompanyVo, ProjectCompanyExcelVo> simpleExportListener = new SimpleExportListener<>(new ExportExcelService<>() {
            @Override
            public Long count(ProjectCompanyVo projectCompanyVo) {
                return countProjectCompany(projectCompanyVo);
            }

            @Override
            public List<ProjectCompanyExcelVo> getDataPaginateAs(ProjectCompanyVo projectCompanyVo, Long pageNum, Long pageSize, Long totalRows) {
                QueryWrapper queryWrapper = getQueryWrapper(projectCompanyVo);
                Page<ProjectCompanyExcelVo> excelVoPage = mapper.paginateAs(pageNum, pageSize, totalRows, queryWrapper, ProjectCompanyExcelVo.class);
                return excelVoPage.getRecords();
            }
        }, ProjectCompanyExcelVo.class.getDeclaredFields());
        simpleExportListener.exportWithEntity(WebUtil.getHttpServletResponse(), "项目参建单位信息数据", ProjectCompanyExcelVo.class, projectCompanyVo);
    }

    @SneakyThrows
    @Override
    public void importExcel(MultipartFile multipartFile) {
        SimpleImportListener<ProjectCompany, ProjectCompanyExcelVo> simpleImportListener = new SimpleImportListener<>(new ImportExcelService<>() {

            @Override
            public ProjectCompanyExcelVo toExcelVo(Map<String, String> cellData) {
                ProjectCompanyExcelVo projectCompanyExcelVo = new ProjectCompanyExcelVo();
                projectCompanyExcelVo.setProjectCompanyId(parseLong(cellData.get("projectCompanyId")));
                projectCompanyExcelVo.setProjectId(parseLong(cellData.get("projectId")));
                projectCompanyExcelVo.setCompanyId(parseLong(cellData.get("companyId")));
                projectCompanyExcelVo.setCompanyRole(cellData.get("companyRole"));
                projectCompanyExcelVo.setManagerName(cellData.get("managerName"));
                projectCompanyExcelVo.setManagerIdCardType(cellData.get("managerIdCardType"));
                projectCompanyExcelVo.setManagerIdCardNumber(cellData.get("managerIdCardNumber"));
                projectCompanyExcelVo.setManagerPhone(cellData.get("managerPhone"));
                return projectCompanyExcelVo;
            }

            @Override
            public ProjectCompany toBean(ProjectCompanyExcelVo excelVo) {
                ProjectCompany projectCompany = new ProjectCompany();
                BeanUtil.copyProperties(excelVo, projectCompany);
                return projectCompany;
            }

            @Override
            public void saveBeans(List<ProjectCompany> t) {
                saveBatch(t);
            }
        }, ProjectCompanyExcelVo.class.getDeclaredFields());
        EasyExcel.read(multipartFile.getInputStream(), simpleImportListener).sheet().doRead();
        Map<Integer, String> error = simpleImportListener.getError();
        if (CollectionUtil.isNotEmpty(error)) {
            throw new ExcelReadException("导入发生错误", error);
        }
    }
}
