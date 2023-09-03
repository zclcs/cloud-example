package ${basePackage}.${serviceImplPackage};

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.zclcs.cloud.lib.core.base.BasePage;
import com.zclcs.cloud.lib.core.base.BasePageAo;
import ${basePackage}.${aoPackage}.${className}Ao;
import ${basePackage}.${entityPackage}.${className};
import ${basePackage}.${voPackage}.${className}Vo;
import ${basePackage}.${mapperPackage}.${className}Mapper;
import ${basePackage}.${servicePackage}.${className}Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ${tableComment} Service实现
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl extends ServiceImpl<${className}Mapper, ${className}> implements ${className}Service {

    @Override
    public BasePage<${className}Vo> find${className}Page(BasePageAo basePageAo, ${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        Page<${className}Vo> page = this.mapper.paginateAs(basePageAo.getPageNum(), basePageAo.getPageSize(), queryWrapper, ${className}Vo.class);
        return new BasePage<>(page);
    }

    @Override
    public List<${className}Vo> find${className}List(${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        return this.mapper.selectListByQueryAs(queryWrapper, ${className}Vo.class);
    }

    @Override
    public ${className}Vo find${className}(${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        return this.mapper.selectOneByQueryAs(queryWrapper, ${className}Vo.class);
    }

    @Override
    public Long count${className}(${className}Vo ${className?uncap_first}Vo) {
    QueryWrapper queryWrapper = getQueryWrapper(${className?uncap_first}Vo);
        return this.mapper.selectCountByQuery(queryWrapper);
    }

    private QueryWrapper getQueryWrapper(${className}Vo ${className?uncap_first}Vo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        // TODO 设置公共查询条件
        return queryWrapper;
   }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className} create${className}(${className}Ao ${className?uncap_first}Ao) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
        this.save(${className?uncap_first});
        return ${className?uncap_first};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${className} update${className}(${className}Ao ${className?uncap_first}Ao) {
        ${className} ${className?uncap_first} = new ${className}();
        BeanUtil.copyProperties(${className?uncap_first}Ao, ${className?uncap_first});
        this.updateById(${className?uncap_first});
        return ${className?uncap_first};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete${className}(List<Long> ids) {
        this.removeByIds(ids);
    }
}
