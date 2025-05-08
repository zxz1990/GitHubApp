# GitHubApp

Android版GitHub客户端，提供仓库浏览、代码查看、问题提交等功能

## 功能特性

- 最近流行仓库展示（星标/作者/语言）
- 仓库详情查看（README/文件结构）
- 多语言搜索过滤
- 个人信息展示（需要通过OAuth认证）
- 问题反馈功能（登录用户）
- 单元测试覆盖

## 已知问题

- 功能粗糙，界面不够美观
- Trending Topics还未做
- raise issue接口还未调通
- 单元测试只写了2个简单的搜索页面的UI测试case

## 使用介绍

- apk在根目录的apk目录下
- 安装[app-debug.apk](apk/app-debug.apk)即可执行主程序
- 单元测试:[app-debug-androidTest.apk](apk/app-debug-androidTest.apk)
  ，安装主程序apk和单元测试apk后，再执行如下命令即可（目前还需要点一下主程序才可执行）：
  adb shell am instrument -w com.mcdull.githubapp.test/androidx.test.runner.AndroidJUnitRunner
- 单元测试结果
  ![单元测试](img/android_test.png)

## 技术栈

- Kotlin + Jetpack
- MVVM 架构
- Hilt 框架
- Retrofit + OkHttp
- Espresso 单元测试
- GitHub REST API v3

## 部分UML图及数据流图

- 搜索页UML
- 
  ![搜索](img/search_uml.png)
- 
- 搜索页数据流
- 
  ![搜索](img/search_flow.png)
- 个人页UML
- 
  ![个人页](img/profile_uml.png)
- 
- 个人页数据流
- 
  ![个人页](img/profile_flow.png)

## 截图展示

- 主页
  ![主页](img/home.png)
- 搜索页
  ![搜索页](img/search.png)
- 详情页首页
  ![仓库详情](img/repo_detail_home.png)
- 详情页子目录页
  ![仓库详情](img/repo_detail_sub.png)
- 个人页已登录
  ![个人页](img/profile_login.png)
- 个人页未登录
  ![个人页](img/profile_unlogin.png)
- OAuth授权界面
  ![OAuth](img/oauth.png)
- 提交问题页
  ![问题提交](img/raise_issue.png)
- 横屏展示
  ![横屏](img/landscape.png)
