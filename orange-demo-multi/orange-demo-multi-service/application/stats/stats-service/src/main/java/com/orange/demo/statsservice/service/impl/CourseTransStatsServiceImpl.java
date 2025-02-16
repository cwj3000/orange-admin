package com.orange.demo.statsservice.service.impl;

import com.orange.demo.statsservice.service.*;
import com.orange.demo.statsservice.dao.*;
import com.orange.demo.statsservice.model.*;
import com.orange.demo.common.core.util.*;
import com.orange.demo.common.core.object.MyRelationParam;
import com.orange.demo.common.core.base.dao.BaseDaoMapper;
import com.orange.demo.common.core.base.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 课程统计数据操作服务类。
 *
 * @author Jerry
 * @date 2020-08-08
 */
@Slf4j
@Service("courseTransStatsService")
public class CourseTransStatsServiceImpl extends BaseService<CourseTransStats, Long> implements CourseTransStatsService {

    @Autowired
    private CourseTransStatsMapper courseTransStatsMapper;

    /**
     * 返回当前Service的主表Mapper对象。
     *
     * @return 主表Mapper对象。
     */
    @Override
    protected BaseDaoMapper<CourseTransStats> mapper() {
        return courseTransStatsMapper;
    }

    /**
     * 获取单表查询结果。由于没有关联数据查询，因此在仅仅获取单表数据的场景下，效率更高。
     * 如果需要同时获取关联数据，请移步(getCourseTransStatsListWithRelation)方法。
     *
     * @param filter  过滤对象。
     * @param orderBy 排序参数。
     * @return 查询结果集。
     */
    @Override
    public List<CourseTransStats> getCourseTransStatsList(CourseTransStats filter, String orderBy) {
        return courseTransStatsMapper.getCourseTransStatsList(null, null, filter, orderBy);
    }

    /**
     * 获取主表的查询结果，查询条件中包括主表过滤对象和指定字段的(in list)过滤。
     * 由于没有关联数据查询，因此在仅仅获取单表数据的场景下，效率更高。
     * 如果需要同时获取关联数据，请移步(getCourseTransStatsListWithRelation)方法。
     *
     * @param inFilterField (In-list)指定的字段(Java成员字段，而非数据列名)。
     * @param inFilterValues inFilterField指定字段的(In-list)数据列表。
     * @param filter 过滤对象。
     * @param orderBy 排序参数。
     * @return 查询结果集。
     */
    @Override
    public <M> List<CourseTransStats> getCourseTransStatsList(
            String inFilterField, Set<M> inFilterValues, CourseTransStats filter, String orderBy) {
        String inFilterColumn = MyModelUtil.mapToColumnName(inFilterField, CourseTransStats.class);
        return courseTransStatsMapper.getCourseTransStatsList(inFilterColumn, inFilterValues, filter, orderBy);
    }

    /**
     * 获取主表的查询结果，以及主表关联的字典数据和一对一从表数据，以及一对一从表的字典数据。
     * 该查询会涉及到一对一从表的关联过滤，或一对多从表的嵌套关联过滤，因此性能不如单表过滤。
     * 如果仅仅需要获取主表数据，请移步(getCourseTransStatsList)，以便获取更好的查询性能。
     *
     * @param filter 主表过滤对象。
     * @param orderBy 排序对象。
     * @return 查询结果集。
     */
    @Override
    public List<CourseTransStats> getCourseTransStatsListWithRelation(CourseTransStats filter, String orderBy) {
        List<CourseTransStats> resultList = courseTransStatsMapper.getCourseTransStatsList(null, null, filter, orderBy);
        // 在缺省生成的代码中，如果查询结果resultList不是Page对象，说明没有分页，那么就很可能是数据导出接口调用了当前方法。
        // 为了避免一次性的大量数据关联，规避因此而造成的系统运行性能冲击，这里手动进行了分批次读取，开发者可按需修改该值。
        int batchSize = resultList instanceof Page ? 0 : 1000;
        this.buildRelationForDataList(resultList, MyRelationParam.normal(), batchSize);
        return resultList;
    }

    /**
     * 获取主表的查询结果，查询条件中包括主表过滤对象和指定字段的(in list)过滤。
     * 同时还包含主表关联的字典数据和一对一从表数据，以及一对一从表的字典数据。
     * 如果仅仅需要获取主表数据，请移步(getCourseTransStatsList)，以便获取更好的查询性能。
     *
     * @param inFilterField (In-list)指定的字段(Java成员字段，而非数据列名)。
     * @param inFilterValues inFilterField指定字段的(In-list)数据列表。
     * @param filter 主表过滤对象。
     * @param orderBy 排序对象。
     * @return 查询结果集。
     */
    @Override
    public <M> List<CourseTransStats> getCourseTransStatsListWithRelation(
            String inFilterField, Set<M> inFilterValues, CourseTransStats filter, String orderBy) {
        String inFilterColumn = MyModelUtil.mapToColumnName(inFilterField, CourseTransStats.class);
        List<CourseTransStats> resultList =
                courseTransStatsMapper.getCourseTransStatsList(inFilterColumn, inFilterValues, filter, orderBy);
        // 在缺省生成的代码中，如果查询结果resultList不是Page对象，说明没有分页，那么就很可能是数据导出接口调用了当前方法。
        // 为了避免一次性的大量数据关联，规避因此而造成的系统运行性能冲击，这里手动进行了分批次读取，开发者可按需修改该值。
        int batchSize = resultList instanceof Page ? 0 : 1000;
        this.buildRelationForDataList(resultList, MyRelationParam.dictOnly(), batchSize);
        return resultList;
    }

    /**
     * 获取分组过滤后的数据查询结果，以及关联的字典数据和一对一从表数据，以及一对一从表的字典数据。
     *
     * @param filter         过滤对象。
     * @param groupSelect    分组显示列表参数。位于SQL语句SELECT的后面。
     * @param groupBy        分组参数。位于SQL语句的GROUP BY后面。
     * @param orderBy        排序字符串，ORDER BY从句的参数。
     * @return 分组过滤结果集。
     */
    @Override
    public List<CourseTransStats> getGroupedCourseTransStatsListWithRelation(
            CourseTransStats filter, String groupSelect, String groupBy, String orderBy) {
        List<CourseTransStats> resultList =
                courseTransStatsMapper.getGroupedCourseTransStatsList(filter, groupSelect, groupBy, orderBy);
        // 在缺省生成的代码中，如果查询结果resultList不是Page对象，说明没有分页，那么就很可能是数据导出接口调用了当前方法。
        // 为了避免一次性的大量数据关联，规避因此而造成的系统运行性能冲击，这里手动进行了分批次读取，开发者可按需修改该值。
        int batchSize = resultList instanceof Page ? 0 : 1000;
        // NOTE: 这里只是包含了本地关联数据和远程关联数据，本地聚合计算数据和远程聚合计算数据没有包含。
        // 主要原因是，由于聚合字段通常被视为普通字段使用，不会在group by的从句中出现，语义上也不会在此关联。
        this.buildRelationForDataList(resultList, MyRelationParam.normal(), batchSize);
        return resultList;
    }
}
