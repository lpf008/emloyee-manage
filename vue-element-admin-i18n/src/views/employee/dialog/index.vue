<template>
  <div>
    <el-form ref="ruleForm" :rules="rules" :model="ruleForm" label-width="80px">
      <el-form-item label="职工姓名" prop="empName">
        <el-col :span="4">
          <el-input v-model="ruleForm.empName" placeholder="请选择姓名" />
        </el-col>
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="ruleForm.sex" placeholder="请选择性别">
          <el-option label="男" value="男" />
          <el-option label="女" value="女" />
        </el-select>
      </el-form-item>

      <el-form-item label="年龄" prop="age" placeholder="请选择年龄">
        <el-col :span="4">
          <el-input v-model.number="ruleForm.age" />
        </el-col>
      </el-form-item>
      <el-form-item label="部门" prop="deptName">
        <el-select v-model="ruleForm.deptName" placeholder="请选择部门">
          <el-option label="业务部" value="业务部" />
          <el-option label="后勤部" value="后勤部" />
          <el-option label="人事部" value="人事部" />
        </el-select>
      </el-form-item>
      <el-form-item label="学历" prop="empDegreeName">
        <el-select v-model="ruleForm.empDegreeName" placeholder="请选择学历">
          <el-option label="研究生" value="研究生" />
          <el-option label="本科" value="本科" />
          <el-option label="大专" value="大专" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit('ruleForm')"><span>{{ id?"修改":"添加" }}</span></el-button>
        <el-button @click="handleClose">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  props: {
    id: {
      type: Number,
      default: 0
    }
  },
  data() {
    var checkAge = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('年龄不能为空'))
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值'))
        } else {
          if (value < 18 || value > 55) {
            callback(new Error('必须年满18岁且小于55岁'))
          } else {
            callback()
          }
        }
      }, 1000)
    }
    return {
      ruleForm: {
        empName: '',
        sex: '',
        age: 18,
        deptName: '',
        empDegreeName: ''
      },
      rules: {
        empName: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        age: [
          { validator: checkAge, trigger: 'blur' }
        ],
        deptName: [
          { required: true, message: '请选择部门', trigger: 'change' }
        ],
        empDegreeName: [
          { required: true, message: '请选择学历', trigger: 'change' }
        ]

      }
    }
  },
  mounted() {
    if (this.id) {
      axios.get('http://localhost:7777/employee/searchEmployeeById/' + this.id)
        .then((res) => {
          if (res.data.success) {
            this.ruleForm = res.data.data
          } else {
            this.$message({ message: '初始化出错', type: 'fail' })
          }
        })
    }
  },
  methods: {
    onSubmit(ruleForm) {
      this.$refs[ruleForm].validate((valid) => {
        console.log(valid)
        if (valid) {
          axios.post(this.id ? 'http://localhost:7777/employee/updateEmployee' : 'http://localhost:7777/employee/addEmployee', this.ruleForm)
            .then((res) => {
              if (res.data.success) {
                this.$message.success('提交成功')
                this.handleClose()
              } else {
                this.$message({ message: '提交出错', type: 'fail' })
              }
            })
            .catch((e) => {
              console.log('提交出错：' + e)
            })
        }
      })
    },
    handleClose() {
      this.ruleForm = {}
      this.$emit('handleClose')
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
