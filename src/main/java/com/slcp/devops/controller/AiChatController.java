package com.slcp.devops.controller;

import com.slcp.devops.service.AiChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * AI 聊天控制器
 * 使用新的 Spring AI + Ollama/OpenAI 统一接口
 * 
 * @author slcp
 * @date 2025-10-27
 */
@Tag(name = "AI 聊天", description = "AI 对话和智能辅助接口")
@RestController
@RequestMapping("/api/ai")
@CrossOrigin
public class AiChatController {

    @Resource
    private AiChatService aiChatService;

    /**
     * 简单聊天（默认使用 Ollama）
     */
    @Operation(summary = "简单聊天", description = "与 AI 进行简单对话，默认使用本地 Ollama 模型")
    @PostMapping("/chat")
    public Map<String, Object> chat(
            @Parameter(description = "用户消息", required = true)
            @RequestParam String message) {
        try {
            String response = aiChatService.chat(message);
            return success(response);
        } catch (Exception e) {
            return error("AI 服务暂时不可用：" + e.getMessage());
        }
    }

    /**
     * 流式聊天（SSE）
     */
    @Operation(summary = "流式聊天", description = "以流式方式接收 AI 回复（Server-Sent Events）")
    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatStream(
            @Parameter(description = "用户消息", required = true)
            @RequestParam String message) {
        return aiChatService.chatStream(message);
    }

    /**
     * AI 问答
     */
    @Operation(summary = "AI 问答", description = "使用问答模式与 AI 交互")
    @PostMapping("/question")
    public Map<String, Object> questionAnswer(
            @Parameter(description = "问题", required = true)
            @RequestParam String question) {
        try {
            String answer = aiChatService.questionAnswer(question);
            return success(answer);
        } catch (Exception e) {
            return error("问答服务异常：" + e.getMessage());
        }
    }

    /**
     * 代码解释
     */
    @Operation(summary = "代码解释", description = "让 AI 解释代码的功能和逻辑")
    @PostMapping("/code/explain")
    public Map<String, Object> explainCode(
            @Parameter(description = "代码内容", required = true)
            @RequestParam String code) {
        try {
            String explanation = aiChatService.explainCode(code);
            return success(explanation);
        } catch (Exception e) {
            return error("代码解释失败：" + e.getMessage());
        }
    }

    /**
     * Bug 修复
     */
    @Operation(summary = "Bug 修复", description = "让 AI 帮助修复代码中的 Bug")
    @PostMapping("/code/fix")
    public Map<String, Object> fixBug(
            @Parameter(description = "编程语言", required = true)
            @RequestParam String language,
            @Parameter(description = "代码内容", required = true)
            @RequestParam String code) {
        try {
            String fixedCode = aiChatService.fixBug(language, code);
            return success(fixedCode);
        } catch (Exception e) {
            return error("Bug 修复失败：" + e.getMessage());
        }
    }

    /**
     * 翻译
     */
    @Operation(summary = "文本翻译", description = "将文本翻译成指定语言")
    @PostMapping("/translate")
    public Map<String, Object> translate(
            @Parameter(description = "原文", required = true)
            @RequestParam String text,
            @Parameter(description = "目标语言", required = true)
            @RequestParam String targetLanguage) {
        try {
            String translation = aiChatService.translate(text, targetLanguage);
            return success(translation);
        } catch (Exception e) {
            return error("翻译失败：" + e.getMessage());
        }
    }

    /**
     * 内容摘要
     */
    @Operation(summary = "内容摘要", description = "生成文本内容的摘要")
    @PostMapping("/summarize")
    public Map<String, Object> summarize(
            @Parameter(description = "文本内容", required = true)
            @RequestParam String text) {
        try {
            String summary = aiChatService.summarize(text);
            return success(summary);
        } catch (Exception e) {
            return error("摘要生成失败：" + e.getMessage());
        }
    }

    /**
     * 关键字提取
     */
    @Operation(summary = "关键字提取", description = "从文本中提取关键字")
    @PostMapping("/keywords")
    public Map<String, Object> extractKeywords(
            @Parameter(description = "文本内容", required = true)
            @RequestParam String text) {
        try {
            String keywords = aiChatService.extractKeywords(text);
            return success(keywords);
        } catch (Exception e) {
            return error("关键字提取失败：" + e.getMessage());
        }
    }

    /**
     * 情感分析
     */
    @Operation(summary = "情感分析", description = "分析文本的情感倾向")
    @PostMapping("/sentiment")
    public Map<String, Object> sentimentAnalysis(
            @Parameter(description = "文本内容", required = true)
            @RequestParam String text) {
        try {
            String sentiment = aiChatService.sentimentAnalysis(text);
            return success(sentiment);
        } catch (Exception e) {
            return error("情感分析失败：" + e.getMessage());
        }
    }

    /**
     * SQL 生成
     */
    @Operation(summary = "SQL 生成", description = "根据需求描述生成 SQL 语句")
    @PostMapping("/sql/generate")
    public Map<String, Object> generateSql(
            @Parameter(description = "需求描述", required = true)
            @RequestParam String description,
            @Parameter(description = "表结构信息", required = true)
            @RequestParam String tableInfo) {
        try {
            String sql = aiChatService.generateSql(description, tableInfo);
            return success(sql);
        } catch (Exception e) {
            return error("SQL 生成失败：" + e.getMessage());
        }
    }

    /**
     * 故事创作
     */
    @Operation(summary = "故事创作", description = "根据主题创作故事")
    @PostMapping("/create/story")
    public Map<String, Object> createStory(
            @Parameter(description = "故事主题", required = true)
            @RequestParam String topic) {
        try {
            String story = aiChatService.createStory(topic);
            return success(story);
        } catch (Exception e) {
            return error("故事创作失败：" + e.getMessage());
        }
    }

    /**
     * 头脑风暴
     */
    @Operation(summary = "头脑风暴", description = "针对主题进行头脑风暴")
    @PostMapping("/brainstorm")
    public Map<String, Object> brainstorm(
            @Parameter(description = "讨论主题", required = true)
            @RequestParam String topic) {
        try {
            String ideas = aiChatService.brainstorm(topic);
            return success(ideas);
        } catch (Exception e) {
            return error("头脑风暴失败：" + e.getMessage());
        }
    }

    /**
     * 面试题生成
     */
    @Operation(summary = "面试题生成", description = "生成指定主题的面试题")
    @PostMapping("/interview")
    public Map<String, Object> generateInterviewQuestions(
            @Parameter(description = "技术主题", required = true)
            @RequestParam String topic) {
        try {
            String questions = aiChatService.generateInterviewQuestions(topic);
            return success(questions);
        } catch (Exception e) {
            return error("面试题生成失败：" + e.getMessage());
        }
    }

    /**
     * 使用 Ollama 聊天
     */
    @Operation(summary = "Ollama 聊天", description = "显式使用 Ollama 模型进行对话")
    @PostMapping("/chat/ollama")
    public Map<String, Object> chatWithOllama(
            @Parameter(description = "用户消息", required = true)
            @RequestParam String message) {
        try {
            String response = aiChatService.chatWithOllama(message);
            return success(response);
        } catch (Exception e) {
            return error("Ollama 服务不可用：" + e.getMessage());
        }
    }

    /**
     * 使用 OpenAI 聊天
     */
    @Operation(summary = "OpenAI 聊天", description = "显式使用 OpenAI 模型进行对话")
    @PostMapping("/chat/openai")
    public Map<String, Object> chatWithOpenAi(
            @Parameter(description = "用户消息", required = true)
            @RequestParam String message) {
        try {
            String response = aiChatService.chatWithOpenAi(message);
            return success(response);
        } catch (Exception e) {
            return error("OpenAI 服务不可用：" + e.getMessage());
        }
    }

    /**
     * 健康检查
     */
    @Operation(summary = "健康检查", description = "检查 AI 服务是否可用")
    @GetMapping("/health")
    public Map<String, Object> health() {
        try {
            String response = aiChatService.chat("ping");
            Map<String, Object> result = new HashMap<>();
            result.put("status", "healthy");
            result.put("message", "AI 服务运行正常");
            result.put("test_response", response);
            return result;
        } catch (Exception e) {
            return error("AI 服务异常：" + e.getMessage());
        }
    }

    // ========== 辅助方法 ==========

    private Map<String, Object> success(String data) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", data);
        return result;
    }

    private Map<String, Object> error(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 500);
        result.put("message", message);
        result.put("data", null);
        return result;
    }
}

