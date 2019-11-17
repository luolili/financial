1.gradle构建 项目

2.新建 模块后生成的 build.gradle 里面的内容删掉

3. mysql:主从复制，otter

4.升级boot2:

---
gradle 基于maven 构建，用 groovy :领域驱动语言。

新建一个 gradle 的项目。Tool-->Groovy Console.

groovy兼容 java 语法。

build -->jar:在build -->libs

运行jar:
F:\githubpro\financial\groovy>
java -classpath build/libs/groovy-1.0-SNAPSHOT.jar com.luo.gradle.todo.App

project+task:一个 project 包含多个 task。

project 的attr:

group,name,version:坐标.

project method:

apply,dependencies,task,repositories.

task:dependsOn,doFirst/Last



