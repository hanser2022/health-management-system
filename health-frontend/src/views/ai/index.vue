<template>
  <div class="background-view">
    <div class="chat-view">
      <div class="chat-panel">
        <div class="message-panel">
          <!-- 消息列表 -->
          <div ref="messageContainer" class="message-list">
            <div
              v-for="(message, index) in messages"
              :key="index"
              :class="['message-row', message.type]"
            >
              <div v-if="message.type === 'ai'" class="message-avatar">
                <img :src="aiAvatar" alt="AI Avatar" class="avatar">
              </div>
              <div :class="['message', message.type === 'user' ? 'user-message' : 'ai-message']">
                {{ message.content }}
              </div>
              <div v-if="message.type === 'user'" class="message-avatar">
                <img :src="userAvatar" alt="User Avatar" class="avatar">
              </div>
            </div>
          </div>

          <!-- 快捷操作 -->
          <div class="quick-actions">
            <el-button size="small" type="success" :disabled="!isConnected || loading" @click="sendHealthSuggestion">
              <i class="el-icon-first-aid-kit" /> 获取AI健康建议
            </el-button>
            <el-button size="small" type="danger" plain @click="clearChatHistory">
              <i class="el-icon-delete" /> 清空记录
            </el-button>
            <span class="quick-tip">基于您的身体数据生成个性化健康建议</span>
          </div>
          <!-- 输入区域 -->
          <div class="message-input">
            <el-input
              v-model="userInput"
              type="textarea"
              :rows="3"
              placeholder="请输入您的问题..."
              @keyup.enter.native="sendMessage"
            />
            <el-button type="primary" :loading="loading" @click="sendMessage">
              发送
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AiAssistant',
  data() {
    return {
      messages: [
        {
          type: 'ai',
          content: '你好！我是您的AI健康助手，请问有什么可以帮您的吗？（输入“AI健康建议” 会根据用户历史健康数据给出建议！）'
        }
      ],
      userInput: '',
      loading: false,
      socket: null,
      userAvatar: require('@/assets/user.jpg'),
      aiAvatar: require('@/assets/ai.jpg'),
      isConnected: false,
      reconnectAttempts: 0,
      maxReconnectAttempts: 5,
      reconnectInterval: 3000,
      username: localStorage.getItem('username'),
      userId: localStorage.getItem('userId') ? parseInt(localStorage.getItem('userId')) : null,
      chatHistoryKey: 'ai_chat_history_' + localStorage.getItem('userId')
    }
  },
  mounted() {
    this.loadChatHistory()
    this.setupWebSocket()
  },
  beforeDestroy() {
    this.closeWebSocket()
  },
  methods: {
    setupWebSocket() {
      try {
        // 关闭已有连接
        if (this.socket) {
          this.socket.close()
          this.socket = null
        }

        // 构建WebSocket URL
        const wsProtocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:'
        const wsHost = window.location.host
        const wsUrl = `${wsProtocol}//${wsHost}/ws/chat`
        this.socket = new WebSocket(wsUrl)
        // 连接超时处理
        const connectionTimeout = setTimeout(() => {
          if (!this.isConnected) {
            console.error('WebSocket连接超时')
            this.closeWebSocket()
            this.handleReconnect()
          }
        }, 5000)

        // 连接成功
        this.socket.onopen = (event) => {
          clearTimeout(connectionTimeout)
          this.isConnected = true
          this.reconnectAttempts = 0
          this.$message.success('已连接到AI助手服务')
        }

        // 接收消息
        this.socket.onmessage = (event) => {
          try {
            let message = event.data
            if (message.startsWith('data:')) {
              message = message.substring(5)
              // 检查结束标记
              if (message.includes('[DONE]')) {
                this.loading = false
                this.saveChatHistory()
                return
              }
              // 更新最后一条AI消息
              const lastMessage = this.messages[this.messages.length - 1]
              if (lastMessage && lastMessage.type === 'ai') {
                lastMessage.content += message
                this.scrollToBottom()
              }
            }
          } catch (error) {
            console.error('处理WebSocket消息时出错:', error)
            this.$message.error('消息处理出错')
          }
        }

        // 错误处理
        this.socket.onerror = (error) => {
          console.error('WebSocket错误:', error)
          this.isConnected = false
          this.handleReconnect()
        }

        // 连接关闭
        this.socket.onclose = (event) => {
          this.isConnected = false
          if (!event.wasClean) {
            this.handleReconnect()
          }
        }
      } catch (error) {
        console.error('WebSocket创建失败:', error)
        this.$message.error(`连接失败: ${error.message}`)
        this.handleReconnect()
      }
    },
    handleReconnect() {
      if (this.reconnectAttempts >= this.maxReconnectAttempts) {
        this.$message.error('无法连接到AI助手服务，请稍后再试')
        this.isConnected = false
        return
      }

      this.reconnectAttempts++
      const nextAttemptIn = this.reconnectInterval / 1000
      this.$message.warning(`连接失败，${nextAttemptIn}秒后尝试第${this.reconnectAttempts}次重连...`)

      setTimeout(() => {
        this.setupWebSocket()
      }, this.reconnectInterval)
    },
    closeWebSocket() {
      if (this.socket) {
        this.socket.close()
        this.socket = null
        this.isConnected = false
      }
    },
    async sendMessage() {
      if (!this.userInput.trim()) return

      // 检查连接状态
      if (!this.isConnected) {
        this.$message.warning('正在连接到AI助手服务...')
        this.setupWebSocket()
        return
      }

      // 添加用户消息
      this.messages.push({
        type: 'user',
        content: this.userInput
      })
      // 准备发送内容
      const userMessage = this.userInput
      this.userInput = ''

      // 添加空的AI消息占位
      this.messages.push({
        type: 'ai',
        content: ''
      })

      this.loading = true
      this.scrollToBottom()

      try {
        // 构建消息对象，包含用户信息
        const currentUserId = localStorage.getItem('userId')
        if (!currentUserId && userMessage === 'AI健康建议') {
          this.$message.warning('请先重新登录后再使用AI健康建议功能')
          this.loading = false
          this.messages.pop()
          return
        }
        const message = {
          type: 'message',
          text: userMessage,
          userId: currentUserId ? parseInt(currentUserId) : null
        }
        // 发送消息
        if (this.socket.readyState === WebSocket.OPEN) {
          this.socket.send(JSON.stringify(message))
        } else {
          throw new Error('WebSocket连接未就绪')
        }
      } catch (error) {
        console.error('发送消息失败:', error)
        this.$message.error('发送失败，请重试')
        this.loading = false
        // 移除空的AI消息
        this.messages.pop()
        // 尝试重连
        this.handleReconnect()
      }
    },
    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messageContainer
        if (container) {
          container.scrollTop = container.scrollHeight
        }
      })
    },
    sendHealthSuggestion() {
      if (!this.isConnected) {
        this.$message.warning('正在连接到AI助手服务...')
        this.setupWebSocket()
        return
      }
      this.userInput = 'AI健康建议'
      this.sendMessage()
    },
    saveChatHistory() {
      try {
        const historyToSave = this.messages.filter(m => m.content && m.content.trim())
        localStorage.setItem(this.chatHistoryKey, JSON.stringify(historyToSave))
      } catch (e) {
        console.warn('保存聊天记录失败', e)
      }
    },
    loadChatHistory() {
      try {
        const saved = localStorage.getItem(this.chatHistoryKey)
        if (saved) {
          const history = JSON.parse(saved)
          if (history && history.length > 0) {
            this.messages = history
            return
          }
        }
      } catch (e) {
        console.warn('加载聊天记录失败', e)
      }
      this.messages = [
        {
          type: 'ai',
          content: '你好！我是您的AI健康助手，请问有什么可以帮您的吗？（输入"AI健康建议" 会根据用户历史健康数据给出建议！）'
        }
      ]
    },
    clearChatHistory() {
      this.messages = [
        {
          type: 'ai',
          content: '你好！我是您的AI健康助手，请问有什么可以帮您的吗？'
        }
      ]
      localStorage.removeItem(this.chatHistoryKey)
      this.$message.success('聊天记录已清空')
    }
  }
}
</script>

<style scoped>
.background-view {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.chat-view {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 20px;
}

.chat-panel {
  width: 90%;
  max-width: 800px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.message-panel {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 100px);
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message-row {
  display: flex;
  align-items: flex-start;
  margin-bottom: 15px;
}

.message-row.user {
  justify-content: flex-end;
}

.message-row.ai {
  justify-content: flex-start;
}

.message-avatar {
  margin: 0 10px;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}

.message {
  max-width: 70%;
  padding: 12px 16px;
  border-radius: 12px;
  font-size: 14px;
  line-height: 1.5;
}

.user-message {
  background-color: #409EFF;
  color: white;
}

.ai-message {
  background-color: #f4f4f5;
  color: #333;
}

.message-input {
  padding: 20px;
  border-top: 1px solid #eee;
  display: flex;
  gap: 10px;
}

.message-input .el-textarea {
  flex: 1;
}

.message-input .el-button {
  align-self: flex-end;
}

.quick-actions {
  padding: 10px 20px;
  border-top: 1px solid #eee;
  display: flex;
  align-items: center;
  gap: 10px;
}

.quick-tip {
  font-size: 12px;
  color: #999;
}
</style>

