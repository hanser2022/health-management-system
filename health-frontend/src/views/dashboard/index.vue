<template>
  <div class="dashboard-container">
    <div v-if="!hasBodyInfo" class="empty-state">
      <i class="el-icon-warning-outline" style="font-size: 60px; color: #E6A23C" />
      <h2>暂无身体信息</h2>
      <p>请先录入身体信息，即可查看健康数据趋势</p>
      <el-button type="primary" @click="$router.push('/health/body')">去录入</el-button>
    </div>
    <div v-else>
      <div v-if="warnings.length > 0" class="warning-section">
        <el-alert
          v-for="(warning, index) in warnings"
          :key="index"
          :title="warning.title"
          :description="warning.desc"
          :type="warning.type"
          show-icon
          :closable="false"
          style="margin-bottom: 10px;"
        />
      </div>

      <el-row :gutter="20">
        <el-col :span="6">
          <div class="grid-content">
            <div class="col1">
              <div class="col6">身高</div>
              <div class="col6zhi">{{ bodyInfo.height }}<span class="unit">cm</span></div>
            </div>
            <div><i class="el-icon-user-solid" style="font-size: 60px; color: #409EFF" /></div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content">
            <div class="col1">
              <div class="col6">体重</div>
              <div class="col6zhi">{{ bodyInfo.weight }}<span class="unit">kg</span></div>
            </div>
            <div><i class="el-icon-odometer" style="font-size: 60px; color: #67C23A" /></div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content" :class="{'warning-border': bmiWarning}">
            <div class="col1">
              <div class="col6">BMI</div>
              <div class="col6zhi" :class="{'warning-text': bmiWarning}">{{ bmi }}<span class="unit">{{ bmiStatus }}</span></div>
            </div>
            <div><i class="el-icon-data-analysis" style="font-size: 60px; color: #E6A23C" /></div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content">
            <div class="col1">
              <div class="col6">年龄</div>
              <div class="col6zhi">{{ bodyInfo.age }}<span class="unit">岁</span></div>
            </div>
            <div><i class="el-icon-s-data" style="font-size: 60px; color: #F56C6C" /></div>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20" style="margin-top: 20px;">
        <el-col :span="6">
          <div class="grid-content indicator-card" :class="{'warning-border': heartRateWarning}">
            <div class="col1">
              <div class="col6">心率</div>
              <div class="col6zhi" :class="{'warning-text': heartRateWarning}">{{ bodyInfo.heartRate }}<span class="unit">bpm</span></div>
            </div>
            <div><i class="el-icon-s-custom" style="font-size: 50px; color: #F56C6C" /></div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content indicator-card" :class="{'warning-border': bloodSugarWarning}">
            <div class="col1">
              <div class="col6">血糖</div>
              <div class="col6zhi" :class="{'warning-text': bloodSugarWarning}">{{ bodyInfo.bloodSugar }}<span class="unit">mmol/L</span></div>
            </div>
            <div><i class="el-icon-sugar" style="font-size: 50px; color: #E6A23C" /></div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content indicator-card" :class="{'warning-border': bloodPressureWarning}">
            <div class="col1">
              <div class="col6">血压</div>
              <div class="col6zhi" :class="{'warning-text': bloodPressureWarning}">{{ bodyInfo.bloodPressure }}<span class="unit">mmHg</span></div>
            </div>
            <div><i class="el-icon-data-board" style="font-size: 50px; color: #409EFF" /></div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content indicator-card" :class="{'warning-border': sleepWarning}">
            <div class="col1">
              <div class="col6">睡眠</div>
              <div class="col6zhi" :class="{'warning-text': sleepWarning}">{{ bodyInfo.sleepDuration }}<span class="unit">小时</span></div>
            </div>
            <div><i class="el-icon-moon" style="font-size: 50px; color: #909399" /></div>
          </div>
        </el-col>
      </el-row>

      <div v-if="hasNotes" class="chart-row">
        <div class="chart-box">
          <div ref="heartRateChart" style="width: 100%; height: 100%" />
        </div>
        <div class="chart-box">
          <div ref="visionChart" style="width: 100%; height: 100%" />
        </div>
        <div class="chart-box">
          <div ref="bpBsChart" style="width: 100%; height: 100%" />
        </div>
      </div>
      <div v-else class="empty-chart">
        <p>暂无历史数据，录入多次身体信息后即可查看趋势图</p>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import userApi from '@/api/userManage'
import FunctionApi from '@/api/Function_Menu'

export default {
  data() {
    return {
      bodyInfo: {},
      hasBodyInfo: false,
      hasNotes: false,
      bmi: null,
      bmiStatus: '',
      bmiWarning: false,
      heartRateWarning: false,
      bloodSugarWarning: false,
      bloodPressureWarning: false,
      sleepWarning: false,
      warnings: [],
      BodyNotesInfo: [],
      vision: [],
      bloodSugar: [],
      bloodPressure: [],
      date: [],
      heartRate: []
    }
  },
  created() {
    this.getBodyInfo()
  },
  methods: {
    checkHealthWarnings() {
      this.warnings = []
      this.bmiWarning = false
      this.heartRateWarning = false
      this.bloodSugarWarning = false
      this.bloodPressureWarning = false
      this.sleepWarning = false

      if (this.bmi) {
        const bmiVal = parseFloat(this.bmi)
        if (bmiVal < 18.5) {
          this.bmiWarning = true
          this.warnings.push({ title: 'BMI偏低 - 体重过轻', desc: '您的BMI为' + this.bmi + '，低于正常范围(18.5-24)，建议适当增加营养摄入，加强力量训练。', type: 'warning' })
        } else if (bmiVal >= 28) {
          this.bmiWarning = true
          this.warnings.push({ title: 'BMI偏高 - 肥胖', desc: '您的BMI为' + this.bmi + '，超过正常范围(18.5-24)，建议控制饮食并增加有氧运动，推荐查看智能运动推荐。', type: 'error' })
        } else if (bmiVal >= 24) {
          this.bmiWarning = true
          this.warnings.push({ title: 'BMI偏高 - 超重', desc: '您的BMI为' + this.bmi + '，略超正常范围(18.5-24)，建议适当控制饮食并增加运动量。', type: 'warning' })
        }
      }

      const hr = Number(this.bodyInfo.heartRate)
      if (hr > 0) {
        if (hr > 100) {
          this.heartRateWarning = true
          this.warnings.push({ title: '心率过快 - 心动过速', desc: '您的心率为' + hr + 'bpm，超过正常范围(60-100bpm)，建议减少咖啡因摄入，避免剧烈运动，必要时就医检查。', type: 'error' })
        } else if (hr < 60) {
          this.heartRateWarning = true
          this.warnings.push({ title: '心率过缓 - 心动过缓', desc: '您的心率为' + hr + 'bpm，低于正常范围(60-100bpm)，如伴有头晕乏力等症状，建议就医检查。', type: 'warning' })
        }
      }

      const bs = Number(this.bodyInfo.bloodSugar)
      if (bs > 0) {
        if (bs > 7.0) {
          this.bloodSugarWarning = true
          this.warnings.push({ title: '血糖偏高', desc: '您的空腹血糖为' + bs + 'mmol/L，超过正常范围(3.9-6.1mmol/L)，建议控制糖分摄入，定期监测血糖。', type: 'error' })
        } else if (bs < 3.9) {
          this.bloodSugarWarning = true
          this.warnings.push({ title: '血糖偏低', desc: '您的血糖为' + bs + 'mmol/L，低于正常范围(3.9-6.1mmol/L)，注意规律饮食，随身携带含糖食品。', type: 'warning' })
        }
      }

      if (this.bodyInfo.bloodPressure) {
        const bpStr = String(this.bodyInfo.bloodPressure)
        const bpMatch = bpStr.match(/(\d+)/g)
        if (bpMatch && bpMatch.length >= 2) {
          const systolic = parseInt(bpMatch[0])
          const diastolic = parseInt(bpMatch[1])
          if (systolic > 140 || diastolic > 90) {
            this.bloodPressureWarning = true
            this.warnings.push({ title: '血压偏高 - 高血压', desc: '您的血压为' + bpStr + 'mmHg，超过正常范围(90-140/60-90mmHg)，建议低盐饮食，规律作息，定期监测血压。', type: 'error' })
          } else if (systolic < 90 || diastolic < 60) {
            this.bloodPressureWarning = true
            this.warnings.push({ title: '血压偏低 - 低血压', desc: '您的血压为' + bpStr + 'mmHg，低于正常范围(90-140/60-90mmHg)，建议适当增加盐分摄入，避免突然站立。', type: 'warning' })
          }
        }
      }

      const sleep = Number(this.bodyInfo.sleepDuration)
      if (sleep > 0) {
        if (sleep < 6) {
          this.sleepWarning = true
          this.warnings.push({ title: '睡眠不足', desc: '您的睡眠时长为' + sleep + '小时，低于建议的7-8小时，长期睡眠不足会影响免疫力和心血管健康。', type: 'warning' })
        } else if (sleep > 10) {
          this.sleepWarning = true
          this.warnings.push({ title: '睡眠过多', desc: '您的睡眠时长为' + sleep + '小时，超过建议的7-8小时，过度睡眠可能是身体疲劳的信号。', type: 'warning' })
        }
      }
    },
    async getBodyInfo() {
      try {
        const {
          data: { bodyList }
        } = await userApi.getBodyInfo()
        if (bodyList && bodyList.length > 0) {
          this.bodyInfo = bodyList[0]
          this.hasBodyInfo = true
          this.bmiM()
          this.checkHealthWarnings()
          await this.getBodyNotes()
        }
      } catch (error) {
        this.hasBodyInfo = false
      }
    },
    async getBodyNotes() {
      try {
        const response = await FunctionApi.getBodyNotes(this.bodyInfo.id)
        this.BodyNotesInfo = response.data || []
        if (this.BodyNotesInfo.length > 0) {
          this.hasNotes = true
          this.BodyNotesInfo.forEach((note) => {
            this.vision.push(note.vision)
            this.bloodSugar.push(note.bloodSugar)
            const bpStr = note.bloodPressure || ''
            const bpMatch = bpStr.match(/(\d+)/g)
            if (bpMatch && bpMatch.length >= 1) {
              this.bloodPressure.push(parseInt(bpMatch[0]))
            } else {
              this.bloodPressure.push(0)
            }
            this.heartRate.push(note.heartRate)
            this.date.push(
              new Date(note.date).toLocaleDateString('zh-CN', {
                month: '2-digit',
                day: '2-digit'
              })
            )
          })
          this.$nextTick(() => {
            this.renderCharts()
          })
        }
      } catch (error) {
        this.hasNotes = false
      }
    },
    bmiM() {
      const weight = Number(this.bodyInfo.weight)
      const heightCm = Number(this.bodyInfo.height)
      if (!weight || !heightCm) return
      const heightM = heightCm / 100
      const bmiValue = weight / (heightM * heightM)
      this.bmi = bmiValue.toFixed(1)
      if (bmiValue < 18.5) this.bmiStatus = '偏瘦'
      else if (bmiValue < 24) this.bmiStatus = '正常'
      else if (bmiValue < 28) this.bmiStatus = '偏胖'
      else this.bmiStatus = '肥胖'
    },
    renderCharts() {
      this.renderHeartRateChart()
      this.renderVisionChart()
      this.renderBpBsChart()
    },
    renderHeartRateChart() {
      const dom = this.$refs.heartRateChart
      if (!dom) return
      const myChart = echarts.init(dom)
      const markAreas = []
      if (this.heartRateWarning) {
        markAreas.push([{ xAxis: this.date[0], itemStyle: { color: 'rgba(245,108,108,0.15)' }}, { xAxis: this.date[this.date.length - 1] }])
      }
      myChart.setOption({
        title: { text: '心率变化趋势', left: 'center', textStyle: { fontSize: 16, fontWeight: 'normal' }},
        tooltip: { trigger: 'axis' },
        xAxis: { type: 'category', data: this.date, axisLabel: { fontSize: 11 }},
        yAxis: { type: 'value', axisLabel: { fontSize: 11 }},
        series: [{
          data: this.heartRate, type: 'line', smooth: true,
          lineStyle: { width: 2, color: '#00bfff' },
          itemStyle: { color: '#00bfff' },
          markLine: {
            data: [
              { type: 'average', name: '平均' },
              { yAxis: 100, lineStyle: { type: 'dashed', color: '#F56C6C' }, label: { formatter: '上限100', color: '#F56C6C' }},
              { yAxis: 60, lineStyle: { type: 'dashed', color: '#E6A23C' }, label: { formatter: '下限60', color: '#E6A23C' }}
            ]
          },
          markArea: { data: markAreas }
        }]
      })
    },
    renderVisionChart() {
      const dom = this.$refs.visionChart
      if (!dom) return
      const myChart = echarts.init(dom)
      myChart.setOption({
        title: { text: '视力变化趋势', left: 'center', textStyle: { fontSize: 16, fontWeight: 'normal' }},
        tooltip: { trigger: 'axis', formatter: function(params) {
          if (params && params.length > 0) {
            const p = params[0]
            return p.name + '<br/>视力: <b>' + p.value + '</b>'
          }
          return ''
        }},
        grid: { top: 60, right: 20, bottom: 40, left: 50 },
        xAxis: { type: 'category', data: this.date, axisLabel: { fontSize: 11, rotate: 30 }, axisTick: { alignWithLabel: true }},
        yAxis: {
          type: 'value',
          min: 4.0,
          max: 5.5,
          interval: 0.5,
          axisLabel: {
            fontSize: 11,
            formatter: function(val) { return val.toFixed(1) }
          }
        },
        dataZoom: [{
          type: 'inside',
          start: 0,
          end: 100
        }],
        series: [{
          name: '视力',
          type: 'bar',
          data: this.vision,
          barMaxWidth: 50,
          barGap: '30%',
          label: {
            show: true,
            position: 'top',
            fontSize: 12,
            fontWeight: 'bold',
            color: '#333',
            formatter: function(params) { return String(params.value) }
          },
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#3398DB' },
              { offset: 1, color: '#66B3E8' }
            ]),
            borderRadius: [4, 4, 0, 0]
          },
          emphasis: {
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#1a7bb9' },
                { offset: 1, color: '#3398DB' }
              ])
            }
          }
        }]
      })
    },
    renderBpBsChart() {
      const dom = this.$refs.bpBsChart
      if (!dom) return
      const myChart = echarts.init(dom)
      myChart.setOption({
        title: { text: '血压血糖变化趋势', left: 'center', textStyle: { fontSize: 16, fontWeight: 'normal' }},
        tooltip: { trigger: 'axis' },
        legend: { data: ['血压', '血糖'], bottom: 0 },
        xAxis: { type: 'category', data: this.date, axisLabel: { fontSize: 11 }},
        yAxis: { type: 'value' },
        series: [
          { name: '血压', data: this.bloodPressure, type: 'line', smooth: true },
          { name: '血糖', data: this.bloodSugar, type: 'line', smooth: true, markLine: { data: [{ yAxis: 7.0, lineStyle: { type: 'dashed', color: '#F56C6C' }, label: { formatter: '血糖上限', color: '#F56C6C' }}] }}
        ]
      })
    }
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}
.grid-content {
  background: #ffffff;
  border-radius: 8px;
  min-height: 120px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s;
}
.warning-border {
  border: 2px solid #F56C6C;
  box-shadow: 0 2px 12px rgba(245, 108, 108, 0.3);
}
.warning-text {
  color: #F56C6C !important;
}
.unit {
  font-size: 14px;
  font-weight: normal;
  margin-left: 4px;
  color: #999;
}
.col6 {
  font-size: 22px;
  font-weight: bold;
  color: #313131;
  text-align: right;
  padding-right: 10px;
  margin-top: -20px;
}
.col6zhi {
  font-size: 24px;
  font-weight: bold;
  color: #3472a6;
  padding-top: 20px;
}
.col1 {
  margin-right: -60%;
  padding-top: 15px;
}
.chart-row {
  display: flex;
  margin-top: 30px;
  gap: 16px;
}
.chart-box {
  flex: 1;
  height: 380px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  padding: 10px;
}
.empty-state {
  text-align: center;
  padding: 100px 0;
}
.empty-state h2 {
  margin: 20px 0 10px;
  color: #333;
}
.empty-state p {
  color: #999;
  margin-bottom: 20px;
}
.empty-chart {
  text-align: center;
  padding: 60px 0;
  color: #999;
}
.warning-section {
  margin-bottom: 10px;
}
.indicator-card {
  min-height: 100px;
}
</style>
