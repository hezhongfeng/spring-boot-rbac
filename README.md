# 开发环境准备

本小节我们需要准备一套开发环境，包括 Java 的开发套件 jdk，还有构建项目所需的打包工具 Gradle。另外还有一个编写代码的 IDE ，这里我选择的是 vscode。

## 开发套件 jdk

> Java Development Kit

Java 语言编写的程序所需的开发工具包，JDK 包含了 Java 的运行时所需的 JRE，同时还包括 Java 源码的编译器 javac、监控工具 jconsole、分析工具 jvisualvm 等

一般开发和运行服务器都安装 jdk，我们这里安装 jdk11，不算新也不算老。

### OracleJDK 和 OpenJDK

这里说下 JDK 的版本区别：

Oracle JDK 完全由 Oracle 公司开发，而 OpenJDK 由 Oracle、OpenJDK 和 Java 社区开发，目前已经是主流了。以前 Google 和 Oracle 打过旷日持久的官司，最终 Google 胜诉了。

原来 jdk8 的差异还有一点，但是 jdk11 在 OpenJDK 和 Oracle JDK 的代码几乎相同，OpenJDK 已经是主流了，我们这里选择 OpenJDK ，不会有什么授权收费的问题。

### 安装

可以自行安装 jdk11

#### windows

`https://www.cjavapy.com/article/81/`

`http://jdk.java.net/archive/`

成功后可以测试`java -version`

#### mac

`brew install openjdk@11`

配置：

```
sudo ln -sfn /opt/homebrew/opt/openjdk@11/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-11.jdk
```

成功后可以测试`java -version`

```
openjdk version "11.0.14.1" 2022-02-08
OpenJDK Runtime Environment Temurin-11.0.14.1+1 (build 11.0.14.1+1)
OpenJDK 64-Bit Server VM Temurin-11.0.14.1+1 (build 11.0.14.1+1, mixed mode)
```

#### HelloWorld

新建文件 `HelloWorld.java` 并写入如下代码

```
public class HelloWorld {
  public static void main(String[] args) {
    System.out.println("Hello World!");
  }
}
```

控制台运行`javac HelloWorld.java && java HelloWorld`会打印出 `Hello World!`，到这一步我们的 jdk 环境就没啥问题了

## gradle

Gradle 是一个基于 JVM 的构建工具

`https://gradle.org/install/#manually`

安装成功后：

```
gradle --version

------------------------------------------------------------
Gradle 7.4
------------------------------------------------------------

Build time:   2022-02-08 09:58:38 UTC
Revision:     f0d9291c04b90b59445041eaa75b2ee744162586

Kotlin:       1.5.31
Groovy:       3.0.9
Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
JVM:          11.0.14.1 (Eclipse Adoptium 11.0.14.1+1)
OS:           Mac OS X 12.3.1 x86_64
```

### 配置 gradle

咱们国家的网络环境特殊，一般我会配置在国内镜像，在 USER_HOME/.gradle/下创建 init.gradle 文件，这个配置对所有项目生效，这样下载 java 各种包的时候会很快

```
def repoConfig = {
    all { ArtifactRepository repo ->
        if (repo instanceof MavenArtifactRepository) {
            def url = repo.url.toString()
            if (url.contains('repo1.maven.org/maven2') || url.contains('jcenter.bintray.com')) {
                println "gradle init: (${repo.name}: ${repo.url}) removed"
                remove repo
            }
        }
    }
    maven { url 'https://maven.aliyun.com/repository/central' }
    maven { url 'https://maven.aliyun.com/repository/jcenter' }
    maven { url 'https://maven.aliyun.com/repository/google' }
    maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
}

allprojects {
    buildscript {
        repositories repoConfig
    }

    repositories repoConfig
}
```

## 安装 vscode

直接点击[官网](https://code.visualstudio.com/)下载就好了

### 安装插件

列举了我目前安装的

- Java Code Generators
- Language Support for Java(TM) by Red Hat
- Spring Boot Dashboard
- Spring Boot Developer Extension Pack
- Spring Boot Extension Pack
- Spring Boot Snippets
- Spring Boot Tools
- Spring Initializr Java Support
- Test Runner for Java
