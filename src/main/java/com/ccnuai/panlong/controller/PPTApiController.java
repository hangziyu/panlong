package com.ccnuai.panlong.controller;

import com.ccnuai.panlong.result.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ppt")
public class PPTApiController {
    /**
     * 创建任务
     * @param text
     * @param templateId
     * @return
     * @throws IOException
     */
    @GetMapping("/create_task")
    public Result createTask(String text, String templateId) throws IOException {
        log.info("创建任务");

        // 1. URL 和参数编码
        //TODO 这里需要替换ngrok的地址
        URL url = new URL("https://54a0-223-76-127-34.ngrok-free.app/api/create_task");
        String params = "text=" + URLEncoder.encode(text, "UTF-8") +
                "&templateId=" + URLEncoder.encode(templateId, "UTF-8");

        // 2. 设置请求属性
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        conn.setDoOutput(true);

        // 3. 发送请求体
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = params.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 4. 读取响应（包括错误响应）
        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ?
                conn.getInputStream() : conn.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        log.info("响应状态码：" + status);
        log.info("响应内容：" + response.toString());

        return Result.ok(response.toString());
    }

    /**
     * 创建大纲
     * @param text
     * @return
     * @throws IOException
     */
    @GetMapping("/create_outline")
    public Result createOutline(String text) throws IOException {
        log.info("创建大纲");

        // 1. 构造 URL 和 JSON 参数
        //TODO 这里需要替换ngrok的地址
        URL url = new URL("https://e6a7-218-199-207-4.ngrok-free.app/api/create_outline");
        String jsonPayload = "{\"text\": \"" + text + "\"}";

        // 2. 建立连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        // 3. 写入 JSON 请求体
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 4. 获取响应流
        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ?
                conn.getInputStream() : conn.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        log.info("响应状态码：" + status);
        log.info("响应内容：" + response.toString());

        return Result.ok(response.toString());
    }

    /**
     * 上传文档
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload_doc")
    public Result uploadDoc(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("上传文档");
        //后端地址
        //TODO 这里需要替换ngrok的地址
        String targetUrl = "https://e6a7-218-199-207-4.ngrok-free.app/api/upload_doc";
        // 创建 URL 对象
        URL url = new URL(targetUrl);
        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置请求方法为 POST
        connection.setRequestMethod("POST");
        // 设置请求头
        connection.setRequestProperty("Content-Type", "application/json");
        // 设置为输出模式
        connection.setDoOutput(true);
        //设置multipart/form-data 边界
        String boundary = "===" + System.currentTimeMillis() + "===";
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

        try (OutputStream os = connection.getOutputStream();DataOutputStream writer = new DataOutputStream(os)){

            //写入文件字段
            String fileName = file.getOriginalFilename();
            String mimeType = file.getContentType();

            writer.writeBytes("--" + boundary + "\r\n");
            writer.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
            writer.writeBytes("Content-Type: " + mimeType + "\r\n\r\n");
            writer.write(file.getBytes());
            writer.writeBytes("\r\n");

            // 结束 multipart/form-data
            writer.writeBytes("--" + boundary + "--\r\n");
            writer.flush();

        }

        // 读取响应
        // 读取响应
        int status = connection.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ?
                connection.getInputStream() : connection.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        log.info("响应状态码：" + status);
        log.info("响应内容：" + response.toString());

        return Result.ok(response.toString());
    }

    /**
     * 生成ppt
     * @param outline
     * @param templateId
     * @param text
     * @return
     * @throws IOException
     */
    @GetMapping("/create_ppt_by_outline")
    public Result createPptByOutline(String outline, String templateId, String text) throws IOException {
        //TODO 这里需要替换ngrok的地址
        URL url = new URL("https://your-ngrok-url/api/create_ppt_by_outline");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);

        // 构造 JSON 请求体
        String jsonInputString = String.format(
                "{\"outline\":\"%s\", \"templateId\":\"%s\", \"text\":\"%s\"}",
                outline, templateId, text
        );

        // 发送请求
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 读取响应
        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        return Result.ok(response.toString());
    }

    /**
     * 检查进度
     * @param taskId
     * @return
     * @throws IOException
     */
    @GetMapping("/check_progress")
    public Result checkProgress(@RequestParam String taskId) throws IOException {
        //TODO 这里需要替换ngrok的地址
        URL url = new URL("https://your-ngrok-url/api/progress/" + taskId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        return Result.ok(response.toString());
    }

    /**
     * 获取结果
     * @param taskId
     * @return
     * @throws IOException
     */
    @GetMapping("/get_result")
    public Result getResult(@RequestParam String taskId) throws IOException {
        //TODO 这里需要替换ngrok的地址
        URL url = new URL("https://your-ngrok-url/api/result/" + taskId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        return Result.ok(response.toString());
    }

    /**
     * 获取模板
     * @return
     * @throws IOException
     */
    @GetMapping("/get_templates")
    public Result getTemplates() throws IOException {
        URL url = new URL("https://your-ngrok-url/api/templates");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int status = conn.getResponseCode();
        InputStream is = (status >= 200 && status < 300) ? conn.getInputStream() : conn.getErrorStream();

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        return Result.ok(response.toString());
    }

    /**
     * 下载文件
     * @param filename
     * @param response
     * @throws IOException
     */
    @GetMapping("/download_file")
    public void downloadFile(@RequestParam String filename, HttpServletResponse response) throws IOException {
        URL url = new URL("http://your-server-address/download/" + URLEncoder.encode(filename, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        response.setContentType(conn.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        try (InputStream in = conn.getInputStream(); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }









}
