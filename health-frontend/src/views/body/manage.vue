<template>
  <div class="body-manage-container">
    <el-card class="table-card">
      <div slot="header" class="card-header">
        <span>我的健康记录</span>
        <el-tag type="info" size="small">共 {{ total }} 条记录</el-tag>
      </div>

      <el-table v-loading="loading" :data="bodyList" stripe style="width: 100%" size="small">
        <el-table-column type="index" label="#" width="50" />
        <el-table-column prop="date" label="记录时间" width="160" :formatter="formatDate" />
        <el-table-column prop="name" label="姓名" width="80" />
        <el-table-column prop="age" label="年龄" width="60" />
        <el-table-column prop="gender" label="性别" width="60" />
        <el-table-column prop="height" label="身高/cm" width="80" />
        <el-table-column prop="weight" label="体重/kg" width="80" />
        <el-table-column prop="bloodSugar" label="血糖" width="70" />
        <el-table-column prop="bloodPressure" label="血压" width="80" />
        <el-table-column prop="bloodLipid" label="血脂" width="70" />
        <el-table-column prop="heartRate" label="心率" width="60" />
        <el-table-column label="操作" width="120" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewDetail(scope.row)">详情</el-button>
            <el-button type="text" size="small" style="color:#F56C6C" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-if="total > 0"
        :current-page="searchModel.pageNo"
        :page-size="searchModel.pageSize"
        :total="total"
        layout="total, prev, pager, next, jumper"
        style="margin-top: 15px; text-align: right"
        @current-change="handlePageChange"
      />
    </el-card>

    <el-dialog title="健康记录详情" :visible.sync="detailDialogVisible" width="700px" top="5vh">
      <div v-if="currentRecord" class="detail-content">
        <div class="detail-section">
          <div class="section-title"><i class="el-icon-user" /> 基本信息</div>
          <el-descriptions :column="3" border size="small">
            <el-descriptions-item label="姓名">{{ currentRecord.name }}</el-descriptions-item>
            <el-descriptions-item label="年龄">{{ currentRecord.age }} 岁</el-descriptions-item>
            <el-descriptions-item label="性别">{{ currentRecord.gender }}</el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="detail-section">
          <div class="section-title"><i class="el-icon-data-line" /> 身体指标</div>
          <el-descriptions :column="3" border size="small">
            <el-descriptions-item label="身高">{{ currentRecord.height }} cm</el-descriptions-item>
            <el-descriptions-item label="体重">{{ currentRecord.weight }} kg</el-descriptions-item>
            <el-descriptions-item label="BMI">{{ calcBMI(currentRecord) }}</el-descriptions-item>
            <el-descriptions-item label="血糖">{{ currentRecord.bloodSugar }} mmol/L</el-descriptions-item>
            <el-descriptions-item label="血压">{{ currentRecord.bloodPressure }} mmHg</el-descriptions-item>
            <el-descriptions-item label="血脂">{{ currentRecord.bloodLipid }} mmol/L</el-descriptions-item>
            <el-descriptions-item label="心率">{{ currentRecord.heartRate }} 次/分钟</el-descriptions-item>
            <el-descriptions-item label="视力">{{ currentRecord.vision }}</el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="detail-section">
          <div class="section-title"><i class="el-icon-moon-night" /> 生活习惯</div>
          <el-descriptions :column="3" border size="small">
            <el-descriptions-item label="睡眠时长">{{ currentRecord.sleepDuration }} 小时</el-descriptions-item>
            <el-descriptions-item label="睡眠质量">
              <el-tag :type="sleepQualityTag(currentRecord.sleepQuality)" size="mini">{{ currentRecord.sleepQuality }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="饮水量">{{ currentRecord.waterConsumption }} ml/天</el-descriptions-item>
            <el-descriptions-item label="是否吸烟">
              <el-tag :type="currentRecord.smoking ? 'danger' : 'success'" size="mini">{{ currentRecord.smoking ? '是' : '否' }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="是否喝酒">
              <el-tag :type="currentRecord.drinking ? 'danger' : 'success'" size="mini">{{ currentRecord.drinking ? '是' : '否' }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="是否运动">
              <el-tag :type="currentRecord.exercise ? 'success' : 'warning'" size="mini">{{ currentRecord.exercise ? '是' : '否' }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="喜好食物" :span="3">{{ currentRecord.foodTypes }}</el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="detail-section">
          <div class="section-title"><i class="el-icon-time" /> 记录时间</div>
          <el-descriptions :column="1" border size="small">
            <el-descriptions-item label="录入时间">{{ formatDateRow(currentRecord.date) }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </div>
      <span slot="footer">
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import userApi from '@/api/userManage'

export default {
  data() {
    return {
      bodyList: [],
      total: 0,
      loading: false,
      searchModel: {
        pageNo: 1,
        pageSize: 10,
        id: null,
        name: null
      },
      detailDialogVisible: false,
      currentRecord: null
    }
  },
  created() {
    this.loadBodyList()
  },
  methods: {
    async loadBodyList() {
      this.loading = true
      try {
        const { data: { total, rows } } = await userApi.getUserBodyList(this.searchModel)
        if (rows) {
          this.bodyList = rows
          this.total = total
        }
      } catch (error) {
        this.$message.error('加载健康记录失败')
      } finally {
        this.loading = false
      }
    },
    handlePageChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.loadBodyList()
    },
    viewDetail(row) {
      this.currentRecord = row
      this.detailDialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该条健康记录？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        try {
          await userApi.deleteUserBodyById(row.notesid)
          this.$message.success('删除成功')
          this.loadBodyList()
        } catch (error) {
          this.$message.error('删除失败')
        }
      }).catch(() => {})
    },
    calcBMI(row) {
      if (!row || !row.height || !row.weight) return '-'
      const h = row.height / 100
      const bmi = (row.weight / (h * h)).toFixed(1)
      return bmi
    },
    sleepQualityTag(quality) {
      if (quality === '好') return 'success'
      if (quality === '一般') return 'warning'
      if (quality === '差') return 'danger'
      return 'info'
    },
    formatDate(row, column, cellValue) {
      return this.formatDateRow(cellValue)
    },
    formatDateRow(cellValue) {
      if (!cellValue) return ''
      const date = new Date(cellValue)
      const y = date.getFullYear()
      const m = String(date.getMonth() + 1).padStart(2, '0')
      const d = String(date.getDate()).padStart(2, '0')
      const h = String(date.getHours()).padStart(2, '0')
      const min = String(date.getMinutes()).padStart(2, '0')
      return `${y}-${m}-${d} ${h}:${min}`
    }
  }
}
</script>

<style scoped>
.body-manage-container {
  padding: 20px;
}
.table-card {
  border-radius: 8px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: bold;
}
.detail-content {
  max-height: 65vh;
  overflow-y: auto;
}
.detail-section {
  margin-bottom: 20px;
}
.detail-section:last-child {
  margin-bottom: 0;
}
.section-title {
  font-size: 15px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 10px;
  padding-left: 4px;
}
.section-title i {
  margin-right: 6px;
  color: #409EFF;
}
</style>
