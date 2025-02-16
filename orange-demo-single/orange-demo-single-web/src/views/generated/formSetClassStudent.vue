<template>
  <div class="form-single-fragment" style="position: relative;">
    <el-form label-width="100px" size="mini" label-position="right" @submit.native.prevent>
      <filter-box :item-width="350">
        <el-form-item label="所属校区">
          <el-select class="filter-item" v-model="formSetClassStudent.formFilter.schoolId" :clearable="true" filterable
            placeholder="所属校区" :loading="formSetClassStudent.schoolId.impl.loading"
            @visible-change="formSetClassStudent.schoolId.impl.onVisibleChange"
            @change="onSchoolIdValueChange">
            <el-option v-for="item in formSetClassStudent.schoolId.impl.dropdownList" :key="item.id" :value="item.id" :label="item.name" />
          </el-select>
        </el-form-item>
        <el-button slot="operator" type="primary" :plain="true" size="mini" @click="refreshFormSetClassStudent(true)">查询</el-button>
        <el-button slot="operator" type="primary" size="mini" :disabled="tableSelectRowList.length <= 0 || !checkPermCodeExist('formSetClassStudent:formSetClassStudent:addClassStudent')"
          @click="onAddClassStudentClick()">
          添加
        </el-button>
      </filter-box>
    </el-form>
    <el-row>
      <el-col :span="24">
        <el-table :data="formSetClassStudent.Student.impl.dataList" size="mini"
          @sort-change="formSetClassStudent.Student.impl.onSortChange"
          @selection-change="onStudentSelectionChange"
          header-cell-class-name="table-header-gray">
          <el-table-column label="序号" type="index" header-align="center" align="center" width="55px" :index="formSetClassStudent.Student.impl.getTableIndex" />
          <el-table-column type="selection"
            header-align="center" align="center" width="55px"
          />
          <el-table-column label="学生姓名" prop="studentName">
          </el-table-column>
          <el-table-column label="手机号码" prop="loginMobile">
          </el-table-column>
          <el-table-column label="所属年级" prop="gradeIdDictMap.name">
          </el-table-column>
          <el-table-column label="所属校区" prop="schoolIdDictMap.name">
          </el-table-column>
          <el-table-column label="经验等级" prop="experienceLevelDictMap.name">
          </el-table-column>
          <el-table-column label="注册时间" prop="registerTime">
            <template slot-scope="scope">
              <span>{{formatDateByStatsType(scope.row.registerTime, 'day')}}</span>
            </template>
          </el-table-column>
        </el-table>
        <el-col :span="24">
          <el-row type="flex" justify="end" style="margin-top: 10px;">
            <el-pagination
              :total="formSetClassStudent.Student.impl.totalCount"
              :current-page="formSetClassStudent.Student.impl.currentPage"
              :page-size="formSetClassStudent.Student.impl.pageSize"
              :page-sizes="[10, 20, 50, 100]"
              layout="total, prev, pager, next, sizes"
              @current-change="formSetClassStudent.Student.impl.onCurrentPageChange"
              @size-change="formSetClassStudent.Student.impl.onPageSizeChange">
            </el-pagination>
          </el-row>
        </el-col>
      </el-col>
    </el-row>
    <label v-if="closeVisible == '1'" class="page-close-box">
      <el-button type="text" @click="onCancel(true)" icon="el-icon-close" />
    </label>
  </div>
</template>

<script>
/* eslint-disable-next-line */
import rules from '@/utils/validate.js';
/* eslint-disable-next-line */
import { DropdownWidget, TableWidget, UploadWidget, ChartWidget } from '@/utils/widget.js';
/* eslint-disable-next-line */
import { uploadMixin, statsDateRangeMixin, cachePageMixin, cachedPageChildMixin } from '@/core/mixins';
/* eslint-disable-next-line */
import { StudentClassController, DictionaryController } from '@/api';

export default {
  name: 'formSetClassStudent',
  props: {
    classId: {
      default: undefined
    },
    closeVisible: {
      type: [Number, String],
      default: 0
    }
  },
  mixins: [uploadMixin, statsDateRangeMixin, cachePageMixin, cachedPageChildMixin],
  data () {
    return {
      tableSelectRowList: [],
      formSetClassStudent: {
        formFilter: {
          schoolId: undefined
        },
        formFilterCopy: {
          schoolId: undefined
        },
        schoolId: {
          impl: new DropdownWidget(this.loadSchoolIdDropdownList)
        },
        Student: {
          impl: new TableWidget(this.loadStudentWidgetData, this.loadStudentVerify, true, false)
        },
        isInit: false
      }
    }
  },
  methods: {
    onCancel (isSuccess) {
      this.removeCachePage(this.$options.name);
      this.refreshParentCachedPage = isSuccess;
      this.$router.go(-1);
    },
    onStudentSelectionChange (values) {
      this.tableSelectRowList = values;
    },
    /**
     * 班级学生数据获取函数，返回Promise
     */
    loadStudentWidgetData (params) {
      if (
        this.classId == null
      ) {
        this.formSetClassStudent.Student.impl.clearTable();
        return Promise.reject();
      }
      if (params == null) params = {};
      params = {
        ...params,
        studentDtoFilter: {
          schoolId: this.formSetClassStudent.formFilterCopy.schoolId
        },
        classId: this.classId
      }
      return new Promise((resolve, reject) => {
        StudentClassController.listNotInClassStudent(this, params).then(res => {
          resolve({
            dataList: res.data.dataList,
            totalCount: res.data.totalCount
          });
        }).catch(e => {
          reject(e);
        });
      });
    },
    /**
     * 班级学生数据获取检测函数，返回true正常获取数据，返回false停止获取数据
     */
    loadStudentVerify () {
      this.formSetClassStudent.formFilterCopy.schoolId = this.formSetClassStudent.formFilter.schoolId;
      return true;
    },
    /**
     * 所属校区下拉数据获取函数
     */
    loadSchoolIdDropdownList () {
      return new Promise((resolve, reject) => {
        let params = {};
        DictionaryController.dictSysDeptByParentId(this, params).then(res => {
          resolve(res.getList());
        }).catch(e => {
          reject(e);
        });
      });
    },
    /**
     * 所属校区选中值改变
     */
    onSchoolIdValueChange (value) {
    },
    /**
     * 更新设置班级学生
     */
    refreshFormSetClassStudent (reloadData = false) {
      if (reloadData) {
        this.formSetClassStudent.Student.impl.refreshTable(true, 1);
      } else {
        this.formSetClassStudent.Student.impl.refreshTable();
      }
      if (!this.formSetClassStudent.isInit) {
        // 初始化下拉数据
      }
      this.formSetClassStudent.isInit = true;
    },
    /**
     * 添加
     */
    onAddClassStudentClick () {
      if (
        this.classId == null
      ) {
        this.$message.error('请求失败，发现必填参数为空！');
        return;
      }
      let params = {
        classId: this.classId,
        classStudentDtoList: this.tableSelectRowList.map((item) => {
          return {
            studentId: item.studentId
          };
        })
      };

      StudentClassController.addClassStudent(this, params).then(res => {
        this.$message.success('添加成功');
        this.refreshFormSetClassStudent();
      }).catch(e => {});
    },
    onResume () {
      this.refreshFormSetClassStudent(true);
    },
    initFormData () {
    },
    formInit () {
      this.refreshFormSetClassStudent();
    }
  },
  mounted () {
    // 初始化页面数据
    this.formInit();
  },
  watch: {
  }
}
</script>
