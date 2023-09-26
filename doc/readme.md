## 安装 
1. 通过顶级项目：apm-sniffer install 所有的模块
2. 安装 apm-agent：apm agent 模块 执行 package，会在该模块的所在磁盘目录下的 target 目录下创建 apm-dist 目录（参照 apm-agent pom 文件中的 antrun 
   插件配置）： 
   1. 生成 agent 包： apm-agent-1.0-SNAPSHOT.jar 
   2. plugins 子目录：存放需要应用的所有插件 jar 包

## 使用
与一般的 agent 一样，通过 -javaagent 参数指定生成的：apm-agent-1.0-SNAPSHOT.jar。例：`java -javaagent:.\apm-agent-1.0-SNAPSHOT.jar -jar .\apm-app-1.0-SNAPSHOT.jar`