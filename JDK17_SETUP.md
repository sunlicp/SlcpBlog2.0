# 切换到 JDK 17 指南

你当前使用的是 JDK 25，但项目需要 JDK 17。按照以下步骤切换：

---

## 🔍 方式一：macOS/Linux 使用 SDKMAN（推荐）

### 1. 安装 SDKMAN
```bash
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

### 2. 安装并切换到 JDK 17
```bash
# 列出可用的 JDK 17 版本
sdk list java | grep 17

# 安装 JDK 17 (选择一个稳定版本，比如 Temurin)
sdk install java 17.0.9-tem

# 设置为默认版本
sdk default java 17.0.9-tem

# 验证
java -version  # 应显示 17.0.x
```

---

## 🔍 方式二：macOS 使用 Homebrew

### 1. 安装 JDK 17
```bash
# 安装 OpenJDK 17
brew install openjdk@17
```

### 2. 设置环境变量

#### 临时切换（当前终端有效）
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

# 验证
java -version
```

#### 永久切换（推荐）

编辑 `~/.zshrc` 或 `~/.bash_profile`:
```bash
# 打开配置文件
vim ~/.zshrc

# 添加以下内容
export JAVA_HOME=/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

# 保存后重新加载
source ~/.zshrc
```

### 3. 验证
```bash
java -version
# 输出应该类似：
# openjdk version "17.0.9" 2023-10-17
# OpenJDK Runtime Environment (build 17.0.9+9)
# OpenJDK 64-Bit Server VM (build 17.0.9+9, mixed mode)
```

---

## 🔍 方式三：手动切换多个 JDK 版本

如果你已经安装了多个 JDK 版本，可以快速切换：

### 查看已安装的 JDK
```bash
/usr/libexec/java_home -V
```

输出示例：
```
Matching Java Virtual Machines (3):
    25 (arm64) "Oracle Corporation" - "Java SE 25" /Library/Java/...
    17.0.9 (arm64) "Eclipse Adoptium" - "OpenJDK 17.0.9" /Library/Java/...
    11.0.21 (arm64) "Eclipse Adoptium" - "OpenJDK 11.0.21" /Library/Java/...
```

### 临时切换到 JDK 17
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
java -version
```

### 创建快捷命令

在 `~/.zshrc` 中添加：
```bash
# JDK 切换快捷命令
alias jdk17="export JAVA_HOME=\$(/usr/libexec/java_home -v 17); java -version"
alias jdk25="export JAVA_HOME=\$(/usr/libexec/java_home -v 25); java -version"

# 默认使用 JDK 17
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
```

重新加载配置：
```bash
source ~/.zshrc
```

现在你可以快速切换：
```bash
jdk17  # 切换到 JDK 17
jdk25  # 切换到 JDK 25
```

---

## 🔍 方式四：Windows 系统

### 1. 下载 JDK 17
从以下网站下载：
- [Adoptium (推荐)](https://adoptium.net/temurin/releases/?version=17)
- [Oracle JDK](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

### 2. 设置环境变量

1. 右键"此电脑" → 属性 → 高级系统设置 → 环境变量
2. 新建系统变量：
   - 变量名：`JAVA_HOME`
   - 变量值：`C:\Program Files\Java\jdk-17`（根据实际安装路径）
3. 编辑系统变量 `Path`，添加：
   - `%JAVA_HOME%\bin`
4. 确定保存

### 3. 验证
```cmd
java -version
```

---

## ✅ 切换完成后的步骤

### 1. 清理并重新编译
```bash
cd /Users/slcp/zhongzhi/myblog

# 清理缓存
rm -rf target/
rm -rf ~/.m2/repository/org/projectlombok/

# 重新编译
mvn clean compile -DskipTests
```

### 2. 在 IDE 中配置 JDK 17

#### IntelliJ IDEA
```
File → Project Structure (⌘;)
→ Project Settings → Project
→ SDK: 选择 17
→ Language level: 17
```

#### Eclipse
```
Window → Preferences
→ Java → Installed JREs
→ Add → Standard VM
→ 选择 JDK 17 目录
→ 应用
```

### 3. 启动项目

#### 命令行启动
```bash
mvn spring-boot:run
```

#### IDE 启动
```
右键 DevopsApplication.java → Run 'DevopsApplication'
```

---

## 🎯 验证检查清单

- [ ] `java -version` 显示 17.x
- [ ] `javac -version` 显示 17.x
- [ ] `mvn clean compile` 编译成功
- [ ] 项目能正常启动
- [ ] 访问 http://localhost:8080 能看到响应

---

## 💡 常见问题

### Q: 切换后 IDE 还是使用旧版本？
**A**: 需要在 IDE 的 Project Structure 中重新选择 JDK 17，然后重启 IDE。

### Q: Maven 编译还是报错？
**A**: 确保 Maven 使用的也是 JDK 17：
```bash
# 查看 Maven 使用的 JDK
mvn -version

# 如果不对，确认 JAVA_HOME 环境变量
echo $JAVA_HOME
```

### Q: 我想保留 JDK 25 用于其他项目？
**A**: 可以！使用方式三的快捷命令，或者在不同项目中设置不同的 JDK。

---

## 📚 参考资源

- [Adoptium JDK 下载](https://adoptium.net/)
- [SDKMAN 官网](https://sdkman.io/)
- [Spring Boot 3.2 系统要求](https://docs.spring.io/spring-boot/docs/3.2.1/reference/html/getting-started.html#getting-started.system-requirements)

---

**完成后你的项目应该就能正常启动了！** 🎉

