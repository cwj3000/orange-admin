package com.orange.demo.webadmin.app.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * ClassCourse实体对象。
 *
 * @author Jerry
 * @date 2020-09-24
 */
@Data
@TableName(value = "zz_class_course")
public class ClassCourse {

    /**
     * 班级Id。
     */
    @TableField(value = "class_id")
    private Long classId;

    /**
     * 课程Id。
     */
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 课程顺序(数值越小越靠前)。
     */
    @TableField(value = "course_order")
    private Integer courseOrder;
}
