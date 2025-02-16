package com.orange.demo.webadmin.app.model;

import com.baomidou.mybatisplus.annotation.*;
import com.orange.demo.webadmin.app.model.constant.CourseDifficult;
import com.orange.demo.application.common.constant.Subject;
import com.orange.demo.common.core.upload.UploadStoreTypeEnum;
import com.orange.demo.common.core.annotation.UploadFlagColumn;
import com.orange.demo.common.core.annotation.RelationDict;
import com.orange.demo.common.core.annotation.RelationConstDict;
import com.orange.demo.common.core.base.mapper.BaseModelMapper;
import com.orange.demo.webadmin.app.vo.CourseVo;
import lombok.Data;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Course实体对象。
 *
 * @author Jerry
 * @date 2020-09-24
 */
@Data
@TableName(value = "zz_course")
public class Course {

    /**
     * 主键Id。
     */
    @TableId(value = "course_id")
    private Long courseId;

    /**
     * 课程名称。
     */
    @TableField(value = "course_name")
    private String courseName;

    /**
     * 课程价格。
     */
    private BigDecimal price;

    /**
     * 课程描述。
     */
    private String description;

    /**
     * 课程难度(0: 容易 1: 普通 2: 很难)。
     */
    private Integer difficulty;

    /**
     * 年级Id。
     */
    @TableField(value = "grade_id")
    private Integer gradeId;

    /**
     * 学科Id。
     */
    @TableField(value = "subject_id")
    private Integer subjectId;

    /**
     * 课时数量。
     */
    @TableField(value = "class_hour")
    private Integer classHour;

    /**
     * 多张课程图片地址。
     */
    @UploadFlagColumn(storeType = UploadStoreTypeEnum.LOCAL_SYSTEM)
    @TableField(value = "picture_url")
    private String pictureUrl;

    /**
     * 创建用户Id。
     */
    @TableField(value = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间。
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 最后修改时间。
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * price 范围过滤起始值(>=)。
     */
    @TableField(exist = false)
    private BigDecimal priceStart;

    /**
     * price 范围过滤结束值(<=)。
     */
    @TableField(exist = false)
    private BigDecimal priceEnd;

    /**
     * classHour 范围过滤起始值(>=)。
     */
    @TableField(exist = false)
    private Integer classHourStart;

    /**
     * classHour 范围过滤结束值(<=)。
     */
    @TableField(exist = false)
    private Integer classHourEnd;

    /**
     * createTime 范围过滤起始值(>=)。
     */
    @TableField(exist = false)
    private String createTimeStart;

    /**
     * createTime 范围过滤结束值(<=)。
     */
    @TableField(exist = false)
    private String createTimeEnd;

    /**
     * courseId 的多对多关联表数据对象。
     */
    @TableField(exist = false)
    private ClassCourse classCourse;

    @RelationDict(
            masterIdField = "gradeId",
            slaveServiceName = "gradeService",
            slaveModelClass = Grade.class,
            slaveIdField = "gradeId",
            slaveNameField = "gradeName")
    @TableField(exist = false)
    private Map<String, Object> gradeIdDictMap;

    @RelationConstDict(
            masterIdField = "difficulty",
            constantDictClass = CourseDifficult.class)
    @TableField(exist = false)
    private Map<String, Object> difficultyDictMap;

    @RelationConstDict(
            masterIdField = "subjectId",
            constantDictClass = Subject.class)
    @TableField(exist = false)
    private Map<String, Object> subjectIdDictMap;

    @Mapper
    public interface CourseModelMapper extends BaseModelMapper<CourseVo, Course> {
        /**
         * 转换Vo对象到实体对象。
         *
         * @param courseVo 域对象。
         * @return 实体对象。
         */
        @Mapping(target = "classCourse", expression = "java(mapToBean(courseVo.getClassCourse(), com.orange.demo.webadmin.app.model.ClassCourse.class))")
        @Override
        Course toModel(CourseVo courseVo);
        /**
         * 转换实体对象到VO对象。
         *
         * @param course 实体对象。
         * @return 域对象。
         */
        @Mapping(target = "classCourse", expression = "java(beanToMap(course.getClassCourse(), false))")
        @Override
        CourseVo fromModel(Course course);
    }
    public static final CourseModelMapper INSTANCE = Mappers.getMapper(CourseModelMapper.class);
}
