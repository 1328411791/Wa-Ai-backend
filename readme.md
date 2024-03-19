# WA AI生成平台 后端

目的是想打造一个类似吐司的AI生成平台，提供多种AI绘画生成服务，包括但不限于：

- 图像生成
- 模型社区讨论
- 模型训练
- 模型在线部署运行
- 模型在线运行调参

以上需求有部分已经实现，有部分还在开发中。

## 已经实现

- 图像生成
- 简易的模型讨论社区
- 模型在线调参

## 如何加入，贡献代码

在此界面加入交流群讨论
https://wa.glcn.top/portal/about

# 目前线上访问

https://wa.glcn.top/

# 使用说明

## 使用架构

- 语言：Java
- 框架：SpringBoot
- 数据库：MySQL
- 缓存：Redis
- 消息队列：RabbitMQ
- 搜索引擎：ElasticSearch （待添加）
- 文件存储：七牛云OSS（可替换为Local）

## 部署方法

- script.sql 是数据库初始化脚本
- application-github.yml 是配置文件,可根据自己的情况修改
- Deployment.yaml es.yaml是K8s相关的部署文件，可根据自己的情况修改
- 中间件的部署需要自行安装配置

如果真的有人用这玩意，可能会提供打包的docker-compose文档（或k8s）和详细的部署文档（逃

如果有问题，欢迎提issue（借楼，有没有大佬能解决SDWebUi在K8s中的内存泄漏问题）