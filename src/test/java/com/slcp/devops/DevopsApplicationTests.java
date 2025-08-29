package com.slcp.devops;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.util.Proxys;
import com.slcp.devops.service.QqUserService;

import com.slcp.devops.utils.OpenAiUtils;
import com.theokanning.openai.completion.CompletionChoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DevopsApplicationTests {
    @Autowired
    private QqUserService QqUserService;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${openai.token}")
    private String OPENAPI_TOKEN;

    /**
     * 测试问答
     */
    @Test
    public void testQA(){
        List<CompletionChoice> questionAnswer = OpenAiUtils.getQuestionAnswer("长沙有哪些好玩的地方？");
        for (CompletionChoice completionChoice : questionAnswer) {
            System.out.println(completionChoice.getText());
        }
    }

    /**
     * 测试面试题生成
     */
    @Test
    public void testInterview(){
        /*//国内访问需要做代理，国外服务器不需要
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
        //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .proxy(proxy)//自定义代理
                .addInterceptor(httpLoggingInterceptor)//自定义日志输出
                .addInterceptor(new OpenAiResponseInterceptor())//自定义返回值拦截
                .connectTimeout(15, TimeUnit.SECONDS)//自定义超时时间
                .writeTimeout(1200, TimeUnit.SECONDS)//自定义超时时间
                .readTimeout(1200, TimeUnit.SECONDS)//自定义超时时间
                .build();
        //构建客户端
        OpenAiClient openAiClient = OpenAiClient.builder()
                .apiKey(OPENAPI_TOKEN)
                .okHttpClient(okHttpClient)
                .build();
        //简单模型
        //CompletionResponse completions = //openAiClient.completions("我想申请转专业，从计算机专业转到会计学专业，帮我完成一份两百字左右的申请书");
        //最新GPT-3.5-Turbo模型
        Message message = Message.builder().role(Message.Role.USER).content("java怎么学呀").build();
        ChatCompletion chatCompletion = ChatCompletion.builder().messages(Arrays.asList(message)).build();
        ChatCompletionResponse chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
        StringBuilder st = new StringBuilder();
        chatCompletionResponse.getChoices().forEach(e -> {
            st.append(e.getMessage().getContent());
        });
        System.out.println(st);*/
        //国内需要代理 国外不需要
        Proxy proxy = Proxys.http("127.0.0.1", 7890);
        ChatGPT chatGPT = ChatGPT.builder()
                .apiKey(OPENAPI_TOKEN)
                .proxy(proxy)
                .timeout(900)
                .apiHost("https://api.openai.com/") //反向代理地址
                .build()
                .init();

        Message system = Message.ofSystem("你现在是一个博主，专门回复留言");
        Message message = Message.of("java怎么学呀");

        ChatCompletion chatCompletion = ChatCompletion.builder()
                .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
                .messages(Arrays.asList(system, message))
                .maxTokens(3000)
                .temperature(0.9)
                .build();
        ChatCompletionResponse response = chatGPT.chatCompletion(chatCompletion);
        Message res = response.getChoices().get(0).getMessage();
        System.out.println(res);
    }

    @Test
    public void test() {
        List<CompletionChoice> questionAnswer = OpenAiUtils.getQuestionAnswer("使用SpringBoot框架进行Http请求的详细代码");
        for (CompletionChoice completionChoice : questionAnswer) {
            System.out.println(completionChoice.getText());
        }
        List<CompletionChoice> openAiApi = OpenAiUtils.getOpenAiApi("使用SpringBoot框架进行Http请求");
        for (CompletionChoice completionChoice : openAiApi) {
            System.out.println(completionChoice.getText());
        }
    }

    @Test
    void contextLoads() throws MessagingException {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("1305366530@qq.com");
            helper.setTo("1305366530@qq.com");
            helper.setSubject("Slcpの时光屋");
            String date = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
            helper.setText("<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Document</title>\n" +
                    "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                    "    <base target=\"_blank\"/>\n" +
                    "    <style id=\"scrollbar\" type=\"text/css\">::-webkit-scrollbar {\n" +
                    "        width: 0 !important\n" +
                    "    }\n" +
                    "\n" +
                    "    pre {\n" +
                    "        white-space: pre-wrap !important;\n" +
                    "        word-wrap: break-word !important;\n" +
                    "        *white-space: normal !important\n" +
                    "    }\n" +
                    "\n" +
                    "    pre {\n" +
                    "        white-space: pre-wrap !important;\n" +
                    "        word-wrap: break-word !important;\n" +
                    "        *white-space: normal !important\n" +
                    "    }\n" +
                    "\n" +
                    "    #letter img {\n" +
                    "        max-width: 300px\n" +
                    "    }</style>\n" +
                    "    <style id=\"from-wrapstyle\" type=\"text/css\">#form-wrap {\n" +
                    "        overflow: hidden;\n" +
                    "        height: 447px;\n" +
                    "        position: relative;\n" +
                    "        top: 0px;\n" +
                    "        transition: all 1s ease-in-out .3s;\n" +
                    "        z-index: 0\n" +
                    "    }</style>\n" +
                    "    <style id=\"from-wraphoverstyle\" type=\"text/css\">#form-wrap:hover {\n" +
                    "        height: 1400px;\n" +
                    "        top: -100px\n" +
                    "    }</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div style=\"width: 530px;margin: 20px auto 0;height: 1000px;\">\n" +
                    "    <div id=\"form-wrap\">\n" +
                    "        <img src=\"https://cdn.jsdelivr.net/gh/Akilarlxh/Valine-Admin@v1.0/source/img/before.png\"\n" +
                    "             alt=\"before\"\n" +
                    "             style=\"position: absolute;bottom: 126px;left: 0px;background-repeat: no-repeat;width: 530px;height: 317px;z-index:-100\">\n" +
                    "        <div style=\"position: relative;overflow: visible;height: 1500px;width: 500px;margin: 0px auto;transition: all 1s ease-in-out .3s;padding-top:200px;\"\n" +
                    "        <form>\n" +
                    "            <div style=\"background: white;width: 95%;max-width: 800px;margin: auto auto;border-radius: 5px;border: 1px solid;overflow: hidden;-webkit-box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.12);box-shadow: 0px 0px 20px 0px rgba(0, 0, 0, 0.18);\">\n" +
                    "                <img style=\"width:100%;overflow: hidden;\"\n" +
                    "                     src=\"https://ae01.alicdn.com/kf/U5bb04af32be544c4b41206d9a42fcacfd.jpg\"/>\n" +
                    "                <div style=\"padding: 5px 20px;\"><br>\n" +
                    "                    <div>\n" +
                    "                        <h3 style=\"text-decoration: none; color: rgb(246, 214, 175);\">\n" +
                    "                            某某某，见信安：</h3>\n" +
                    "                    </div>\n" +
                    "                    <div id=\"letter\"\n" +
                    "                         style=\"overflow:auto;height:400px;width:100%;display:block;word-break: break-all;word-wrap: break-word;\">\n" +
                    "                        <p style=\"display: inline-block;\">\n" +
                    "                            您在<a style=\"text-decoration: none;color: rgb(246, 214, 175)\">\n" +
                    "                            Slcpの时光屋</a>上发表的评论:\n" +
                    "                        </p>\n" +
                    "                        <div style=\"border-bottom: #ddd 1px solid;border-left: #ddd 1px solid;padding-bottom: 20px;background-color: #eee;margin: 15px 0px;padding-left: 20px;padding-right: 20px;border-top: #ddd 1px solid;border-right: #ddd 1px solid;padding-top: 20px;font-family: \"\n" +
                    "                             Arial\n" +
                    "                        \", \"Microsoft YaHei\" , \"黑体\" , \"宋体\" , sans-serif;\">\n" +
                    "                        邮件内容测试专用-4341341----1233443-------12232312\n" +
                    "                    </div>\n" +
                    "                    <p>收到了来自<span style=\"color:rgb(246, 214, 175) \">Slcp</span>的回复：</p>\n" +
                    "                    <div  style=\"width:390px;position:absolute;z-index:9999;opacity: 0.95; height:150px;border-bottom: #ddd 1px solid;\n" +
                    "                    border-left: #ddd 1px solid;padding-bottom: 20px;background-color: #eee;margin: 15px 0px;padding-left: 20px;padding-right: 20px;border-top: #ddd 1px solid;border-right: #ddd 1px solid;padding-top: 20px;font-family: \"\n" +
                    "                         Arial\", \"Microsoft YaHei\" , \"黑体\" , \"宋体\" , sans-serif;\">\n" +
                    "                    回复信息，测试专用-----13-231----122113-2-313123\n" +
                    "                </div>\n" +
                    "            </div>\n" +
                    "            <div style=\"text-align: center;margin-top: 40px;\"><img\n" +
                    "                    src=\"https://ae01.alicdn.com/kf/U0968ee80fd5c4f05a02bdda9709b041eE.png\" alt=\"hr\"\n" +
                    "                    style=\"width:100%; margin:5px auto 5px auto; display: block;\"/><a\n" +
                    "                    style=\"text-transform: uppercase;text-decoration: none;font-size: 17px;border: 2px solid #6c7575;color: #2f3333;padding: 10px;display: inline-block;margin: 10px auto 0;background-color: rgb(246, 214, 175);\"\n" +
                    "                    target=\"_blank\" href=\"#\">2020年5月20号</a>\n" +
                    "            </div>\n" +
                    "            <p style=\"font-size: 12px;text-align: center;color: #999;\">你若盛开,Slcp自来！<br><a\n" +
                    "                    style=\"text-decoration:none; color:rgb(30,171,234)\" href=\"http://www.qingfenginn.top\">@\n" +
                    "                Slcp小栈</a>\n" +
                    "            </p>\n" +
                    "\n" +
                    "        </form>\n" +
                    "    </div>\n" +
                    "\n" +
                    "</div>\n" +
                    "\n" +
                    "</div>\n" +
                    "    <img src=\"https://cdn.jsdelivr.net/gh/Akilarlxh/Valine-Admin@v1.0/source/img/after.png\" alt=\"after\"\n" +
                    "     style=\"      position: absolute;bottom: -2px;left: 0;background-repeat: no-repeat;width: 530px;height: 259px;z-index:100\"></div></div>\n" +
                    "</body>\n",true);
            javaMailSender.send(message);
        }

    }


