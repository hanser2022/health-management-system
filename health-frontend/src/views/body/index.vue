<template>
  <div class="body-info-container">
    <el-card class="form-card">
      <div slot="header" class="card-header">
        <span>身体信息录入</span>
        <el-tag v-if="hasBodyInfo" type="success" size="small">已有记录，更新将覆盖</el-tag>
      </div>
      <el-form ref="bodyFormRef" :model="bodyForm" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="bodyForm.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input v-model.number="bodyForm.age" placeholder="请输入年龄" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="bodyForm.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="身高/cm" prop="height">
              <el-input v-model.number="bodyForm.height" placeholder="如170" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="体重/kg" prop="weight">
              <el-input v-model.number="bodyForm.weight" placeholder="如65" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血糖/mmol/L" prop="bloodSugar">
              <el-input v-model="bodyForm.bloodSugar" placeholder="如5.6" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="血压/mmHg" prop="bloodPressure">
              <el-input v-model="bodyForm.bloodPressure" placeholder="如120/80" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="血脂/mmol/L" prop="bloodLipid">
              <el-input v-model="bodyForm.bloodLipid" placeholder="如4.5" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="心率/次/分钟" prop="heartRate">
              <el-input v-model.number="bodyForm.heartRate" placeholder="如72" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="视力" prop="vision">
              <el-input v-model="bodyForm.vision" placeholder="如5.0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="睡眠时长/h" prop="sleepDuration">
              <el-input v-model.number="bodyForm.sleepDuration" placeholder="如7.5" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="睡眠质量" prop="sleepQuality">
              <el-radio-group v-model="bodyForm.sleepQuality">
                <el-radio label="好">好</el-radio>
                <el-radio label="一般">一般</el-radio>
                <el-radio label="差">差</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="是否吸烟">
              <el-switch v-model="bodyForm.smoking" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否喝酒">
              <el-switch v-model="bodyForm.drinking" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否运动">
              <el-switch v-model="bodyForm.exercise" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="喜好食物" prop="foodTypes">
              <el-select v-model="bodyForm.foodTypes" placeholder="请选择摄入较多的食物种类" style="width:100%">
                <el-option label="蔬菜" value="蔬菜" />
                <el-option label="水果" value="水果" />
                <el-option label="肉类" value="肉类" />
                <el-option label="鱼类" value="鱼类" />
                <el-option label="豆类" value="豆类" />
                <el-option label="谷物" value="谷物" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="饮水量/ml" prop="waterConsumption">
              <el-input v-model="bodyForm.waterConsumption" placeholder="如2000" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="submitForm">
            {{ hasBodyInfo ? '更新信息' : '保存信息' }}
          </el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import FunctionApi from '@/api/Function_Menu'
import userApi from '@/api/userManage'

export default {
  data() {
    return {
      bodyForm: {
        name: '',
        age: null,
        gender: '男',
        height: null,
        weight: null,
        bloodSugar: '',
        bloodPressure: '',
        bloodLipid: '',
        heartRate: null,
        vision: '',
        sleepDuration: null,
        sleepQuality: '一般',
        smoking: false,
        drinking: false,
        exercise: false,
        foodTypes: '',
        waterConsumption: ''
      },
      hasBodyInfo: false,
      loading: false,
      rules: {
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
        gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
        height: [{ required: true, message: '请输入身高', trigger: 'blur' }],
        weight: [{ required: true, message: '请输入体重', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.loadBodyInfo()
  },
  methods: {
    async loadBodyInfo() {
      try {
        const {
          data: { bodyList }
        } = await userApi.getBodyInfo()
        if (bodyList && bodyList.length > 0) {
          this.hasBodyInfo = true
          const info = bodyList[0]
          this.bodyForm = { ...this.bodyForm, ...info }
        }
      } catch (error) {
        // 暂无身体信息
      }
    },
    submitForm() {
      this.$refs.bodyFormRef.validate(async(valid) => {
        if (!valid) return false
        this.loading = true
        try {
          const bodyRes = await FunctionApi.BodyInformation(this.bodyForm)
          if (bodyRes.data && bodyRes.data.id) {
            this.bodyForm.id = bodyRes.data.id
          }
          this.$message.success(this.hasBodyInfo ? '更新成功' : '保存成功')
          this.hasBodyInfo = true
          this.loadBodyInfo()
        } catch (error) {
          this.$message.error('保存失败，请重试')
        } finally {
          this.loading = false
        }
      })
    },
    resetForm() {
      this.$refs.bodyFormRef.resetFields()
    }
  }
}
</script>

<style scoped>
.body-info-container {
  padding: 20px;
}
.form-card {
  max-width: 900px;
  margin: 0 auto;
  border-radius: 8px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}
</style>
