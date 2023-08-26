<template>
  <div>
    <div>
      <el-main style="padding-left:0;height:100%">
        <el-col :span="2">
          <el-input v-model="searchData.empName" autofocus placeholder="姓名" />
        </el-col>

        <el-select v-model="searchData.deptName" placeholder="部门">
          <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-select v-model="searchData.empDegreeName" placeholder="学历">
          <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-button type="primary" icon="el-icon-search" @click="searchEmployee()">搜索</el-button>
        <el-button type="primary" icon="el-icon-search" @click="updateEmployee()">添加</el-button>
        <el-button type="primary" :loading="downloadLoading" icon="el-icon-search" @click="handleExcel()">导出</el-button>
      </el-main>

    </div>
    <div>
      <el-container style="height: 680px;">
        <el-main style="padding-left:0;height:100%">
          <el-table ref="table" v-loading="loading" element-loading-text="加载中，请稍后..." :data="tableData" height="100%" border style="width: 100% ">
            <el-table-column header-align="center" align="center" prop="id" label="序号" min-width="2%" />
            <el-table-column header-align="center" align="center" prop="empName" label="职工姓名" min-width="6%" />
            <el-table-column header-align="center" align="center" prop="sex" label="性别" min-width="6%" />
            <el-table-column header-align="center" align="center" prop="age" label="年龄" min-width="6%" />
            <el-table-column header-align="center" align="center" prop="deptName" label="部门名称" min-width="6%" />
            <el-table-column header-align="center" align="center" prop="empDegreeName" label="学历" min-width="6%" />
            <el-table-column header-align="center" align="center" label="操作" min-width="6%">
              <template slot-scope="scope">
                <el-button type="primary" plain icon="el-icon-edit" @click="updateEmployee(scope.row.id)">编辑</el-button>
                <el-button type="warning" plain icon="el-icon-delete" @click="deleteEmployee(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>

        </el-main>

      </el-container>
      <div>
        <el-pagination
          background
          layout="prev, pager, next"
          :total="total"
          @prev-click="handlePrev"
          @next-click="handleNext"
          @current-change="handleCurrent"
        />
      </div>
    </div>
    <el-dialog :title="title" :visible.sync="employeeDialog" width="50%" top="200px" :close-on-click-modal="false" :close-on-press-escape="false" custom-class="editDialog">
      <add-or-edit v-if="employeeDialog" :id="id" style="min-height:300px" @handleClose="handleClose" />
    </el-dialog>
  </div>
</template>

<script>
import { parseTime } from '@/utils'
import axios from 'axios'
export default {
  components: {
    'addOrEdit': () => import('./dialog/index')
  },
  data() {
    return {
      employeeDialog: false,
      loading: false,
      downloadLoading: false,
      total: 1, // 数据总数
      options1: [{
        value: '业务部',
        label: '业务部'
      }, {
        value: '后勤部',
        label: '后勤部'
      }, {
        value: '人事部',
        label: '人事部'
      }],
      options2: [{
        value: '大专',
        label: '大专'
      }, {
        value: '本科',
        label: '本科'
      }, {
        value: '研究生',
        label: '研究生'
      }],
      searchData: {
        empName: '',
        deptName: '',
        empDegreeName: '',
        currentPage: 1, // 当前页
        size: 10 // 每页显示数据条数
      },
      tableData: [{
        id: 1,
        empName: '卢鹏飞',
        sex: '男',
        age: 23,
        deptName: '业务部',
        empDegreeName: '本科'
      }],
      id: null,
      title: '',
      filename: '',
      autoWidth: true,
      bookType: 'xlsx'

    }
  },
  mounted() {
    this.employeeDataInit()
  },
  methods: {
    searchEmployee() {
      this.employeeDataInit()
    },
    employeeDataInit(val) {
      if (val) {
        this.searchData.currentPage = val
      }
      axios.post('http://localhost:7777/employee/searchEmployeeByPage', this.searchData)
        .then((res) => {
          if (res) {
            this.total = res.data.total
            this.tableData = res.data.data
          }
        })
    },
    updateEmployee(id) {
      this.title = '员工信息添加'
      if (id) {
        this.id = id
        this.title = '员工信息编辑'
      }
      this.employeeDialog = true
    },
    deleteEmployee(id) {
      console.log(id)
      this.$confirm('确定删除选中的数据?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        axios.get('http://localhost:7777/employee/deleteEmployee', { params: { id: id }})
          .then((res) => {
            if (res.data.success) {
              this.$message.success('删除成功')
              this.employeeDataInit()
            } else {
              this.$message({ message: '删除出错', type: 'fail' })
            }
          })
          .catch((e) => {
            console.log('删除出错：' + e)
          })
      })
        .catch(() => {
          this.$message({ type: 'info', message: '已取消操作' })
        })
    },
    handleClose() {
      this.employeeDialog = false
      this.id = null
      this.title = ''
      this.employeeDataInit()
    },
    handlePrev(val) {
      console.log(val)
      this.employeeDataInit(val)
    },
    handleNext(val) {
      console.log(val)
      this.employeeDataInit(val)
    },
    handleCurrent(val) {
      console.log(val)
      this.employeeDataInit(val)
    },
    handleExcel() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['id', '职工姓名', '性别', '年龄', '部门名称', '学历']
        const filterVal = ['id', 'empName', 'sex', 'age', 'deptName', 'empDegreeName']
        const list = this.tableData
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.filename,
          autoWidth: this.autoWidth,
          bookType: this.bookType
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
