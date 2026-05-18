import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import getPageTitle from '@/utils/get-page-title'

import Layout from '@/layout'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/register', '/change_Password']

let hasAddRoutes = false

router.beforeEach(async(to, from, next) => {
  NProgress.start()
  document.title = getPageTitle(to.meta.title)
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      if (hasAddRoutes) {
        next()
      } else {
        try {
          await store.dispatch('user/getInfo')
          const myRoutes = generateRoutes(store.getters.menuList)
          router.addRoutes(myRoutes)
          global.myRoutes = myRoutes
          hasAddRoutes = true
          next({ ...to, replace: true })
        } catch (error) {
          hasAddRoutes = false
          await store.dispatch('user/resetToken')
          const errorMsg = error && error.message ? error.message : (typeof error === 'string' ? error : '获取用户信息失败')
          Message.error(errorMsg)
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})

function buildRoutes(menuList) {
  const routes = []

  menuList.forEach(menu => {
    const route = {
      path: menu.path,
      name: menu.name,
      meta: menu.meta,
      hidden: menu.hidden
    }

    if (menu.component === 'Layout') {
      route.component = Layout
    } else {
      route.component = require(`@/views/${menu.component}.vue`).default
    }

    if (menu.redirect) {
      route.redirect = menu.redirect
    }

    if (menu.children && menu.children.length) {
      route.children = buildRoutes(menu.children)
    }

    routes.push(route)
  })

  return routes
}

function generateRoutes(menuList) {
  const routes = buildRoutes(menuList)

  if (routes.length > 0) {
    const firstMenu = routes[0]
    let redirectPath = firstMenu.path
    if (firstMenu.children && firstMenu.children.length > 0) {
      redirectPath = firstMenu.path + '/' + firstMenu.children[0].path
    }
    routes.unshift({
      path: '/',
      redirect: redirectPath,
      hidden: true
    })
  }

  routes.push({ path: '*', redirect: '/404', hidden: true })

  return routes
}
