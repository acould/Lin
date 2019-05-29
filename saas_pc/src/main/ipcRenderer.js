import { ipcRenderer } from 'electron'

class WindowUtil {
  static closeWindow () {
    ipcRenderer.send('closeWindow')
  }
  static loginSuccess () {  
    ipcRenderer.send('loginSuccess')
  }
  static validToken (hasToken) {
    ipcRenderer.send('validToken', hasToken)
  }
  static logout () {
    ipcRenderer.send('logout')
  }  
  static closeAllWin () {
    ipcRenderer.send('closeAllWin')
  }
}

export default WindowUtil