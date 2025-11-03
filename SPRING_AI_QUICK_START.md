# Spring AI 快速上手指南 ⚡

> 5 分钟快速集成 AI 到你的 Spring Boot 项目

## 🎯 核心改动总结

### ✅ 已完成的升级

1. **JDK**: 8 → 17
2. **Spring Boot**: 2.5.3 → 3.2.1
3. **包名**: javax → jakarta
4. **AI 框架**: OpenAI SDK → Spring AI
5. **模型支持**: OpenAI + Ollama (本地)

### 📦 关键依赖

```xml
<!-- Spring AI (已添加到 pom.xml) -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
</dependency>
```

---

## 🚀 立即开始

### 步骤 1: 安装 Ollama

```bash
# macOS
brew install ollama

# Linux
curl -fsSL https://ollama.com/install.sh | sh

# Windows
# 访问 https://ollama.com/download
```

### 步骤 2: 启动 Ollama 并下载模型

```bash
# 启动服务
ollama serve

# 新开一个终端，下载模型
ollama pull qwen3:0.6b  # 轻量级，600MB，适合开发
```

### 步骤 3: 配置已就绪

配置文件 `application-dev.yml` 已自动配置好：

```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: qwen3:0.6b
          temperature: 0.7
```

### 步骤 4: 启动项目

```bash
mvn clean install
mvn spring-boot:run
```

### 步骤 5: 测试 AI 功能

访问 Swagger UI：
```
http://localhost:81/swagger-ui.html
```

或使用 curl：
```bash
curl -X POST "http://localhost:81/api/ai/chat" \
  -d "message=你好，介绍一下自己"
```

---

## 💡 代码示例

### 1. Controller 中使用 (最简单)

```java
@RestController
public class MyController {
    
    @Resource
    private AiChatService aiChatService;
    
    @GetMapping("/ai/ask")
    public String ask(@RequestParam String question) {
        return aiChatService.chat(question);
    }
}
```

### 2. Service 中使用 (推荐)

```java
@Service
public class ArticleService {
    
    @Resource
    private AiChatService aiChatService;
    
    // 生成文章摘要
    public String generateSummary(String content) {
        return aiChatService.summarize(content);
    }
    
    // 提取关键字
    public String extractKeywords(String content) {
        return aiChatService.extractKeywords(content);
    }
    
    // 翻译
    public String translate(String text, String lang) {
        return aiChatService.translate(text, lang);
    }
}
```

### 3. 异步使用 (高性能)

```java
@Service
public class CommentService {
    
    @Resource
    private AiChatService aiChatService;
    
    @Async
    public void autoReply(Comment comment) {
        String reply = aiChatService.chat(comment.getContent());
        saveReply(comment.getId(), reply);
    }
}
```

### 4. 流式响应 (最佳体验)

```java
@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> streamChat(@RequestParam String message) {
    return aiChatService.chatStream(message);
}
```

---

## 🎨 常用场景速查

| 场景 | 方法 | 示例 |
|------|------|------|
| 简单对话 | `chat(message)` | `aiChatService.chat("你好")` |
| 代码解释 | `explainCode(code)` | `aiChatService.explainCode(code)` |
| Bug 修复 | `fixBug(lang, code)` | `aiChatService.fixBug("Java", code)` |
| 翻译 | `translate(text, lang)` | `aiChatService.translate("Hello", "中文")` |
| 摘要 | `summarize(text)` | `aiChatService.summarize(article)` |
| 关键字 | `extractKeywords(text)` | `aiChatService.extractKeywords(content)` |
| SQL 生成 | `generateSql(desc, table)` | `aiChatService.generateSql(desc, table)` |
| 故事创作 | `createStory(topic)` | `aiChatService.createStory("科幻")` |
| 面试题 | `generateInterviewQuestions(topic)` | `aiChatService.generateInterviewQuestions("Java")` |

---

## 🔥 实战案例

### 案例 1: 智能留言墙回复

**原代码**（已迁移）：
```java
// 使用旧的 ChatGPT SDK（复杂、需要代理）
ChatGPTStream chatgptStream = ChatGPTStream.builder()
    .timeout(50)
    .apiKeyList(OPENAPI_TOKEN)
    .proxy(proxy)
    .build()
    .init();
```

**新代码**（简单、直接）：
```java
// 使用新的 Spring AI（简单、无需代理）
String reply = aiChatService.wallMessageBot(message);
```

### 案例 2: 文章辅助系统

```java
@Service
public class ArticleAssistant {
    
    @Resource
    private AiChatService aiChatService;
    
    public ArticleDTO enhanceArticle(ArticleDTO article) {
        // 1. 生成摘要
        article.setSummary(
            aiChatService.summarize(article.getContent())
        );
        
        // 2. 提取标签
        article.setTags(
            aiChatService.extractKeywords(article.getContent())
        );
        
        // 3. 生成 SEO 描述
        article.setSeoDescription(
            aiChatService.chat("为这篇文章生成 SEO 描述：" + article.getTitle())
        );
        
        return article;
    }
}
```

### 案例 3: 代码助手

```java
@RestController
@RequestMapping("/code-helper")
public class CodeHelperController {
    
    @Resource
    private AiChatService aiChatService;
    
    // 解释代码
    @PostMapping("/explain")
    public String explain(@RequestBody String code) {
        return aiChatService.explainCode(code);
    }
    
    // 修复 Bug
    @PostMapping("/fix")
    public String fix(@RequestParam String language, 
                     @RequestBody String code) {
        return aiChatService.fixBug(language, code);
    }
    
    // 生成文档
    @PostMapping("/document")
    public String document(@RequestParam String language,
                          @RequestBody String code) {
        return aiChatService.generateDocumentation(language, code);
    }
}
```

---

## ⚙️ 配置选项

### 基础配置（默认已配置）

```yaml
spring:
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: qwen3:0.6b
          temperature: 0.7
```

### 高级配置

```yaml
spring:
  ai:
    ollama:
      chat:
        options:
          model: qwen2.5:7b        # 更强大的模型
          temperature: 0.9         # 更有创造性
          top-p: 0.9
          num-predict: 2000        # 最大输出长度
```

### OpenAI 配置（可选）

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-3.5-turbo
          temperature: 0.7
          max-tokens: 2000
```

---

## 🔍 API 测试

### Swagger UI

```
http://localhost:81/swagger-ui.html
http://localhost:81/doc.html  (Knife4j 增强版)
```

### cURL 测试

```bash
# 1. 简单对话
curl -X POST "http://localhost:81/api/ai/chat" \
  -d "message=你好"

# 2. 代码解释
curl -X POST "http://localhost:81/api/ai/code/explain" \
  -d "code=System.out.println(\"Hello\");"

# 3. 翻译
curl -X POST "http://localhost:81/api/ai/translate" \
  -d "text=Hello World" \
  -d "targetLanguage=中文"

# 4. 流式响应
curl "http://localhost:81/api/ai/chat/stream?message=讲个故事"

# 5. 健康检查
curl "http://localhost:81/api/ai/health"
```

---

## 🎯 常见问题

### Q: Ollama 连接失败？
```bash
# 确认服务运行
curl http://localhost:11434/api/tags

# 如果没运行，启动它
ollama serve
```

### Q: 模型下载慢？
```bash
# 选择更小的模型
ollama pull qwen3:0.6b  # 只需 600MB

# 或者使用已下载的其他模型
ollama list  # 查看已有模型
```

### Q: 想使用 OpenAI？
```yaml
# 添加环境变量
export OPENAI_API_KEY=sk-your-key-here

# 或在配置文件中设置
spring:
  ai:
    openai:
      api-key: sk-your-key-here
```

### Q: 回复太慢？
- 使用更小的模型：`qwen3:0.6b` 代替 `qwen2.5:7b`
- 使用流式响应：`chatStream()` 代替 `chat()`
- 使用缓存：添加 `@Cacheable` 注解

---

## 📚 推荐模型

| 模型 | 大小 | 速度 | 质量 | 适用场景 |
|------|------|------|------|----------|
| qwen3:0.6b | 600MB | ⚡⚡⚡ | ⭐⭐ | 开发测试 |
| qwen3:1.5b | 1.5GB | ⚡⚡ | ⭐⭐⭐ | 日常使用 |
| codellama | 3.8GB | ⚡ | ⭐⭐⭐⭐ | 代码辅助 |
| qwen2.5:7b | 7GB | ⚡ | ⭐⭐⭐⭐⭐ | 生产环境 |

```bash
# 下载其他模型
ollama pull codellama     # 代码专用
ollama pull llama2        # 英文优秀
ollama pull qwen2.5:7b    # 高质量
```

---

## 🎉 迁移完成检查清单

- [✅] JDK 升级到 17
- [✅] Spring Boot 升级到 3.2.1
- [✅] javax → jakarta 包名迁移
- [✅] Spring AI 依赖集成
- [✅] Ollama 配置完成
- [✅] AiChatService 服务创建
- [✅] 旧代码迁移（MomentTimeServiceImpl）
- [✅] API 控制器创建
- [✅] 配置文件更新
- [✅] 文档编写完成

---

## 📖 更多文档

- **详细升级指南**: `UPGRADE_GUIDE.md`
- **完整 API 说明**: `AI_USAGE.md`
- **Swagger UI**: http://localhost:81/swagger-ui.html

---

## 🤝 需要帮助？

- **QQ 交流群**: 648742271
- **邮箱**: 2890046448@qq.com
- **GitHub Issues**: https://github.com/sunlicp/SpringBootBlog/issues

---

**享受 AI 的力量！🚀**


