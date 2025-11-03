package com.slcp.devops.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * AI 聊天服务
 * 基于 Spring AI 0.8.1 稳定版本
 * 支持 Ollama 模型
 * 使用 Spring AI 自动配置的 ChatClient
 * 
 * @author slcp
 * @date 2025-10-27
 */
@Slf4j
@Service
public class AiChatService {

    @Resource
    private ChatClient ollamaChatClient;

    /**
     * 简单聊天（使用默认 Ollama）
     */
    public String chat(String message) {
        try {
            UserMessage userMessage = new UserMessage(message);
            Prompt prompt = new Prompt(List.of(userMessage));
            ChatResponse response = ollamaChatClient.call(prompt);
            return response.getResult().getOutput().getContent();
        } catch (Exception e) {
            log.error("聊天失败: {}", e.getMessage(), e);
            return "抱歉，AI 服务暂时不可用：" + e.getMessage();
        }
    }

    /**
     * 流式聊天（Spring AI 0.8.1 不支持流式，返回完整响应）
     */
    public Flux<String> chatStream(String message) {
        try {
            String response = chat(message);
            return Flux.just(response);
        } catch (Exception e) {
            log.error("流式聊天失败: {}", e.getMessage(), e);
            return Flux.just("抱歉，AI 服务暂时不可用");
        }
    }

    /**
     * AI 问答
     */
    public String questionAnswer(String question) {
        String promptText = """
                请作为一个知识渊博的助手回答以下问题。
                如果你不确定答案，请如实说明，不要编造信息。
                
                问题：{question}
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("question", question));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 留言墙智能回复机器人
     */
    public String wallMessageBot(String message) {
        String promptText = """
                你的名字是小c，你是(Slcpの童话镇 🏰)网站的机器人。
                然后你是一个友好的留言墙机器人。请对用户的留言做出温暖、积极的回复。
                保持回复简短，富有情感和人性化。
                
                用户留言：{message}
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("message", message));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 代码解释
     */
    public String explainCode(String code) {
        String promptText = """
                请详细解释以下代码的功能、逻辑和关键点：
                
                ```
                {code}
                ```
                
                请用简洁明了的中文解释，适合初学者理解。
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("code", code));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 文本翻译
     */
    public String translate(String text, String targetLanguage) {
        String promptText = """
                请将以下文本翻译成{targetLanguage}，保持原意和语气：
                
                {text}
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of(
                "text", text,
                "targetLanguage", targetLanguage
        ));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * Bug 修复建议
     */
    public String fixBug(String language, String code) {
        String promptText = """
                请分析以下 {language} 代码中可能存在的问题，并提供修复建议：
                
                ```{language}
                {code}
                ```
                
                请提供：
                1. 问题分析
                2. 修复后的代码
                3. 改进建议
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of(
                "language", language,
                "code", code
        ));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 内容摘要
     */
    public String summarize(String text) {
        String promptText = """
                请对以下内容生成一个简洁的摘要（不超过200字）：
                
                {text}
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("text", text));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 关键字提取
     */
    public String extractKeywords(String text) {
        String promptText = """
                请从以下文本中提取5-10个关键词，用逗号分隔：
                
                {text}
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("text", text));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 情感分析
     */
    public String sentimentAnalysis(String text) {
        String promptText = """
                请分析以下文本的情感倾向（积极/中性/消极），并简要说明理由：
                
                {text}
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("text", text));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * SQL 生成
     */
    public String generateSql(String description, String tableInfo) {
        String promptText = """
                根据以下表结构和需求描述，生成相应的 SQL 语句：
                
                表结构：
                {tableInfo}
                
                需求描述：
                {description}
                
                请只输出 SQL 语句，并添加必要的注释。
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of(
                "description", description,
                "tableInfo", tableInfo
        ));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 故事创作
     */
    public String createStory(String topic) {
        String promptText = """
                请以"{topic}"为主题，创作一个有趣的短故事（300-500字）。
                故事要有开头、发展、高潮和结局。
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("topic", topic));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 头脑风暴
     */
    public String brainstorm(String topic) {
        String promptText = """
                请针对"{topic}"这个主题进行头脑风暴，提供5-10个创意想法或解决方案。
                每个想法用一行表示，简洁明了。
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("topic", topic));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 面试题生成
     */
    public String generateInterviewQuestions(String topic) {
        String promptText = """
                请针对"{topic}"这个技术主题，生成5-10个高质量的面试题。
                包含不同难度级别（初级、中级、高级）。
                每道题目后面简要说明考察点。
                """;
        
        PromptTemplate promptTemplate = new PromptTemplate(promptText);
        Prompt prompt = promptTemplate.create(Map.of("topic", topic));
        
        ChatResponse response = ollamaChatClient.call(prompt);
        return response.getResult().getOutput().getContent();
    }

    /**
     * 使用 Ollama 聊天
     */
    public String chatWithOllama(String message) {
        return chat(message);
    }

    /**
     * 使用 OpenAI 聊天（未配置）
     */
    public String chatWithOpenAi(String message) {
        return "OpenAI 功能暂未配置，当前版本(0.8.1)仅支持 Ollama。如需 OpenAI 支持，请升级到 Spring AI 1.0+";
    }
}
