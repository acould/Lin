'use strict'

import {
  remote,
  app,
  BrowserWindow,
  Menu,
  ipcMain,
  dialog,
  session
} from 'electron'

/**
 * Set `__static` path to static files in production
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-static-assets.html
 */
if (process.env.NODE_ENV !== 'development') {
  global.__static = require('path')
    .join(__dirname, '/static')
    .replace(/\\/g, '\\\\')
}

const url = 'http://localhost:9080'
const winURL =
  process.env.NODE_ENV === 'development'
    ? `${url}`
    : `file://${__dirname}/index.html`
let mainWindow, loginScreen, loadingScreen
let ses

let LoginParams = {
  width: 460,
  height: 520,
  useContentSize: true,
  minWidth: 460,
  minHeight: 520,
  maxWidth: 460,
  maxHeight: 520,
  frame: false,
  webPreferences: { webSecurity: false },
  show: false
}

let mainParams = {
  height: 700,
  useContentSize: true,
  width: 1027,
  minWidth: 1027, // 设置最小宽度
  minHeight: 700, // 设置最小高度
  webPreferences: { webSecurity: false },
  show: false
}

global.sharedObject = {
  versionCode: "16",
  isInit: true // 初始化页面
}

// 主窗口
function createWindow() {
  mainWindow = new BrowserWindow(mainParams)
  Menu.setApplicationMenu(null) // 隐藏菜单栏
  mainWindow.loadURL(winURL)
  mainWindow.webContents.on('did-finish-load', () => {
    if (!loginScreen) {     
      mainWindow.show()
    }
  })
}


function createLoginScreen() {
  loginScreen = new BrowserWindow(LoginParams)
  loginScreen.loadURL(winURL)
  loginScreen.on('closed', () => {
    loginScreen = null
  })
  loginScreen.webContents.on('did-finish-load', () => {     
    if (!mainWindow) {
      createWindow()
    } 
  })
}

app.on('ready', () => {
  createLoginScreen()
})


app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  } 
})

app.on('activate', () => {
  if (mainWindow === null) {
    createLoginScreen()
  }
})

const shouldQuit = app.makeSingleInstance((commandLine, workingDirectory) => {
  if (mainWindow) {
    if (mainWindow.isMinimized()) mainWindow.restore()
    mainWindow.focus()
  }
})
if (shouldQuit) {
  app.quit()
}


ipcMain.on('validToken', (event, hasToken) => {    
  if (hasToken) {
    if (mainWindow) {
      mainWindow.show()
    } else {
      createWindow()
    } 
    loginScreen.close()
  } else {
    loginScreen.show()
  }
  global.sharedObject.isInit = false
})

// 关闭登录框
ipcMain.on('closeWindow', () => {
  if (loginScreen) {
    loginScreen.close()
    app.quit()
  }
})


ipcMain.on('loginSuccess', (event, arg) => {
  if (loginScreen) {
    loginScreen.close()
  }
  mainWindow.loadURL(winURL)
})


ipcMain.on('logout', () => {
  mainWindow.hide()
  if (!loginScreen) {
    createLoginScreen()
    loginScreen.show()
  }
})

ipcMain.on('closeAllWin', () => {
  mainWindow.close()
  app.quit()
})

/**
 * Auto Updater
 *
 * Uncomment the following code below and install `electron-updater` to
 * support auto updating. Code Signing with a valid certificate is required.
 * https://simulatedgreg.gitbooks.io/electron-vue/content/en/using-electron-builder.html#auto-updating
 */

// import { autoUpdater } from 'electron-updater'

// autoUpdater.on('update-downloaded', () => {
//   autoUpdater.quitAndInstall()
// })

// app.on('ready', () => {
//   if (process.env.NODE_ENV === 'production') autoUpdater.checkForUpdates()
// })
// yuhuhjh
