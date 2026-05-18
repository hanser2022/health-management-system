<template>
  <div class="sport-container">
    <el-tabs v-model="activeTab" @tab-click="handleTabChange">
      <el-tab-pane label="运动知识" name="list">
        <el-card class="search-card">
          <el-row>
            <el-col :span="16">
              <el-input
                v-model="searchModel.sportType"
                placeholder="搜索运动类型，如：跑步、瑜伽、游泳"
                clearable
                @keyup.enter.native="getSportList"
              >
                <el-button slot="append" icon="el-icon-search" @click="getSportList" />
              </el-input>
            </el-col>
          </el-row>
        </el-card>

        <el-row :gutter="20" class="sport-list">
          <el-col v-for="sport in sportList" :key="sport.id" :span="8">
            <el-card class="sport-card" shadow="hover" @click.native="viewDetail(sport)">
              <div class="sport-type">{{ sport.sportType }}</div>
              <div class="sport-info">
                <p><i class="el-icon-time" /> 适宜时间：{{ sport.suitableTime || '暂无' }}</p>
                <p><i class="el-icon-star-off" /> 适宜频率：{{ sport.suitableFrequency || '暂无' }}</p>
                <p><i class="el-icon-data-line" /> 推荐速度：{{ sport.recommendedSpeed || '暂无' }}</p>
                <p><i class="el-icon-s-custom" /> 运动心率：{{ sport.suitableHeartRate || '暂无' }}</p>
              </div>
              <el-button type="text" class="detail-btn">查看详情 →</el-button>
            </el-card>
          </el-col>
        </el-row>

        <el-pagination
          :current-page="searchModel.pageNo"
          :page-sizes="[6, 9, 12, 18]"
          :page-size="searchModel.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </el-tab-pane>

      <el-tab-pane label="智能推荐" name="recommend">
        <div v-if="recommendList.length === 0 && !recommendLoading" class="no-recommend">
          <i class="el-icon-warning-outline" style="font-size: 48px; color: #E6A23C;" />
          <p style="font-size: 16px; color: #666; margin-top: 16px;">暂无推荐数据，请先在"身体信息"页面录入您的健康数据</p>
          <el-button type="primary" style="margin-top: 16px;" @click="$router.push('/health/body')">去录入身体信息</el-button>
        </div>
        <div v-else>
          <el-alert
            title="以下运动根据您的身体数据智能推荐，评分越高越适合您"
            type="success"
            :closable="false"
            style="margin-bottom: 20px;"
          />
          <el-row :gutter="20" class="recommend-list">
            <el-col v-for="(item, index) in recommendList" :key="index" :span="8">
              <el-card class="recommend-card" shadow="hover">
                <div class="recommend-rank">
                  <span class="rank-badge" :class="'rank-' + (index + 1)">TOP {{ index + 1 }}</span>
                  <span class="recommend-score">匹配度：{{ item.score }}分</span>
                </div>
                <div class="sport-type">{{ item.sport.sportType }}</div>
                <div class="sport-info">
                  <p><i class="el-icon-time" /> 适宜时间：{{ item.sport.suitableTime || '暂无' }}</p>
                  <p><i class="el-icon-star-off" /> 适宜频率：{{ item.sport.suitableFrequency || '暂无' }}</p>
                  <p><i class="el-icon-data-line" /> 推荐速度：{{ item.sport.recommendedSpeed || '暂无' }}</p>
                  <p><i class="el-icon-s-custom" /> 运动心率：{{ item.sport.suitableHeartRate || '暂无' }}</p>
                </div>
                <div v-if="item.reason" class="recommend-reason">
                  <i class="el-icon-info" /> {{ item.reason }}
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :title="currentSport.sportType + ' - 运动详情'" :visible.sync="detailVisible" width="60%">
      <div v-if="detailInfo" class="detail-content">
        <el-descriptions :column="2" border size="medium">
          <el-descriptions-item label="运动类型">{{ detailInfo.sportType }}</el-descriptions-item>
          <el-descriptions-item label="适宜时间">{{ detailInfo.suitableTime }}</el-descriptions-item>
          <el-descriptions-item label="适宜频率">{{ detailInfo.suitableFrequency }}</el-descriptions-item>
          <el-descriptions-item label="推荐速度">{{ detailInfo.recommendedSpeed }}</el-descriptions-item>
          <el-descriptions-item label="运动心率">{{ detailInfo.suitableHeartRate }}</el-descriptions-item>
        </el-descriptions>
        <div v-if="detailInfo.disease" class="detail-section">
          <h4>禁忌疾病</h4>
          <p>{{ detailInfo.disease }}</p>
        </div>
        <div v-if="detailInfo.method" class="detail-section">
          <h4>方法介绍</h4>
          <p>{{ detailInfo.method }}</p>
        </div>
        <div v-if="detailInfo.notes" class="detail-section">
          <h4>注意事项</h4>
          <p>{{ detailInfo.notes }}</p>
        </div>
      </div>
      <div v-else class="no-detail">
        <p>暂无详细信息</p>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import sportApi from '@/api/Function_Menu'

export default {
  data() {
    return {
      activeTab: 'list',
      sportList: [],
      total: 0,
      searchModel: {
        pageNo: 1,
        pageSize: 9,
        sportType: ''
      },
      detailVisible: false,
      currentSport: {},
      detailInfo: null,
      recommendList: [],
      recommendLoading: false
    }
  },
  created() {
    this.getSportList()
  },
  methods: {
    handleTabChange(tab) {
      if (tab.name === 'recommend') {
        this.loadRecommendations()
      }
    },
    loadRecommendations() {
      this.recommendLoading = true
      sportApi.recommendSports().then((response) => {
        this.recommendList = response.data || []
        this.recommendLoading = false
      }).catch(() => {
        this.recommendList = []
        this.recommendLoading = false
      })
    },
    getSportList() {
      sportApi.getSportList(this.searchModel).then((response) => {
        this.sportList = response.data.rows
        this.total = response.data.total
      })
    },
    async viewDetail(sport) {
      this.currentSport = sport
      this.detailVisible = true
      this.detailInfo = null
      try {
        const response = await sportApi.DetailInfo(sport.sportType)
        this.detailInfo = response.data
      } catch (error) {
        this.detailInfo = null
      }
    },
    handleSizeChange(pageSize) {
      this.searchModel.pageSize = pageSize
      this.getSportList()
    },
    handleCurrentChange(pageNo) {
      this.searchModel.pageNo = pageNo
      this.getSportList()
    }
  }
}
</script>

<style scoped>
.sport-container {
  padding: 20px;
}
.search-card {
  margin-bottom: 20px;
  border-radius: 8px;
}
.sport-list {
  min-height: 400px;
}
.sport-card {
  margin-bottom: 20px;
  border-radius: 8px;
  cursor: pointer;
  transition: transform 0.2s;
}
.sport-card:hover {
  transform: translateY(-4px);
}
.sport-type {
  font-size: 20px;
  font-weight: bold;
  color: #3472a6;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 2px solid #3472a6;
}
.sport-info p {
  font-size: 13px;
  color: #666;
  margin: 6px 0;
}
.sport-info i {
  color: #3472a6;
  margin-right: 4px;
}
.detail-btn {
  margin-top: 10px;
  font-size: 14px;
}
.detail-content {
  padding: 10px;
}
.detail-section {
  margin-top: 20px;
}
.detail-section h4 {
  color: #3472a6;
  margin-bottom: 8px;
  padding-left: 10px;
  border-left: 3px solid #3472a6;
}
.detail-section p {
  line-height: 1.8;
  color: #333;
  text-indent: 2em;
}
.no-detail {
  text-align: center;
  color: #999;
  padding: 40px;
}
.el-pagination {
  text-align: center;
  margin: 20px 0;
}
.no-recommend {
  text-align: center;
  padding: 60px 0;
}
.recommend-card {
  margin-bottom: 20px;
  border-radius: 8px;
  border-top: 3px solid #67C23A;
}
.recommend-rank {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}
.rank-badge {
  display: inline-block;
  padding: 2px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: bold;
  color: #fff;
  background: #909399;
}
.rank-1 { background: linear-gradient(135deg, #FFD700, #FFA500); }
.rank-2 { background: linear-gradient(135deg, #C0C0C0, #A0A0A0); }
.rank-3 { background: linear-gradient(135deg, #CD7F32, #B8860B); }
.recommend-score {
  font-size: 14px;
  font-weight: bold;
  color: #67C23A;
}
.recommend-reason {
  margin-top: 12px;
  padding: 8px 12px;
  background: #f0f9eb;
  border-radius: 4px;
  font-size: 12px;
  color: #67C23A;
  line-height: 1.6;
}
.recommend-reason i {
  margin-right: 4px;
}
</style>
