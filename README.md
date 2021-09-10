# Api Testing UI

## 说明

[前端地址github](https://github.com/veasion/api_testing_ui)
[后端地址github](https://github.com/veasion/api_testing)

## Build Setup

运行

```
npm install [ 慢的话用  npm install --registry https://registry.npm.taobao.org]
```

修改配置

找到 `vue.config.js` 修改变量即可

```
const apiHost = 'localhost' // 后端接口地址
const apiPort = 8080 // 后端端口
```

启动 

```
 npm run dev
```

打包

```
npm run build:prod
```
