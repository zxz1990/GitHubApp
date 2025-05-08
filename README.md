# GitHubApp

Android版GitHub客户端，提供仓库浏览、代码查看、问题提交等功能

## 功能特性
- 仓库列表展示（星标/作者/语言）
- 仓库详情查看（README/文件结构）
- 问题反馈功能（登录用户）
- 多语言搜索过滤
- 单元测试覆盖

## 使用介绍
1. apk在根目录的apk目录下
2. 安装[app-debug.apk](apk/app-debug.apk)即可执行主程序
3. 单元测试:[app-debug-androidTest.apk](apk/app-debug-androidTest.apk)，安装主程序apk和单元测试apk后，再执行如下命令即可（目前还需要点一下主程序才可执行）：
   adb shell am instrument -w com.mcdull.githubapp.test/androidx.test.runner.AndroidJUnitRunner

## 截图展示
![主页](screenshots/home.png)
![搜索页](screenshots/search.png)
![仓库详情](screenshots/repo_detail.png)
![问题提交](screenshots/create_issue.png)

## 技术栈
- Kotlin + Jetpack
- MVVM 架构
- Retrofit + OkHttp
- Espresso 单元测试
- GitHub REST API v3
