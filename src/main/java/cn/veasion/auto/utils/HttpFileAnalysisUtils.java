package cn.veasion.auto.utils;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HttpFileAnalysisUtils
 *
 * @author luozhuowei
 * @date 2021/9/9
 */
public class HttpFileAnalysisUtils {

    private static final String SPLIT = "###";
    private static final String SHARD_SPLIT = "\n###";
    private static final String SCRIPT_START = "> {%";
    private static final String SCRIPT_END = "%}";
    private static final String LINE = "\r\n";
    private static final Pattern EVAL_PATTERN = Pattern.compile("\\{\\{([a-zA-Z0-9_.\\-]+)}}");

    /**
     * 解析http文件
     */
    public static HttpFileModel parse(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
        HttpFileModel result = parse(new String(bytes, StandardCharsets.UTF_8));
        result.setFileName(file.getName());
        return result;
    }

    /**
     * 解析http文件内容
     */
    public static HttpFileModel parse(String text) {
        int startIndex = 0;
        int index = -1;
        List<String> shards = new ArrayList<>();
        while ((index = text.indexOf(SHARD_SPLIT, index + 1)) > -1) {
            String shard = text.substring(startIndex, index).trim();
            shards.add(shard);
            startIndex = index;
        }
        if (startIndex < text.length() - 1) {
            shards.add(text.substring(startIndex).trim());
        }
        HttpFileModel result = new HttpFileModel();
        result.setRequestModels(new ArrayList<>(shards.size()));
        for (String shard : shards) {
            if ("".equals(shard) || SPLIT.equals(shard)) {
                continue;
            }
            RequestModel requestModel = parseRequest(shard);
            if (requestModel != null) {
                result.getRequestModels().add(requestModel);
            }
        }
        return result;
    }

    /**
     * 替换变量
     */
    public static void eval(RequestModel model, Object object) {
        model.setUrl(eval(model.getUrl(), object));
        model.setBody(eval(model.getBody(), object));
        if (model.getHeaders() != null) {
            for (Map.Entry<String, String> entry : model.getHeaders().entrySet()) {
                entry.setValue(eval(entry.getValue(), object));
            }
        }
    }

    /**
     * 替换变量
     */
    public static String eval(String text, Object object) {
        if (text == null || "".equals(text) || !text.contains("{{")) {
            return text;
        }
        StringBuffer sb = new StringBuffer();
        Matcher matcher = EVAL_PATTERN.matcher(text);
        while (matcher.find()) {
            String var = matcher.group(1).trim();
            Object value = EvalAnalysisUtils.parse(var, object);
            matcher.appendReplacement(sb, value == null ? "" : value.toString());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String toHttpCode(HttpFileModel model) {
        StringBuilder sb = new StringBuilder();
        for (RequestModel requestModel : model.requestModels) {
            toHttpCode(sb, requestModel);
        }
        return sb.toString();
    }

    public static void toHttpCode(StringBuilder sb, RequestModel model) {
        sb.append(SHARD_SPLIT);
        if (StringUtils.hasText(model.getDesc())) {
            sb.append(" ").append(model.getDesc());
        }
        sb.append(LINE);
        if (StringUtils.hasText(model.getMethod())) {
            sb.append(model.getMethod()).append(" ");
        }
        sb.append(model.getUrl()).append(LINE);
        if (model.getHeaders() != null) {
            for (Map.Entry<String, String> entry : model.getHeaders().entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue());
            }
        }
        sb.append(LINE);
        if (StringUtils.hasText(model.getBody())) {
            sb.append(model.getBody()).append(LINE);
        }
        if (StringUtils.hasText(model.getScript())) {
            sb.append(SCRIPT_START).append(LINE);
            sb.append(model.getScript()).append(LINE);
            sb.append(SCRIPT_END);
        }
    }

    private static RequestModel parseRequest(String text) {
        RequestModel model = new RequestModel();
        if (text.startsWith(SPLIT)) {
            int idx = text.indexOf("\n");
            if (idx == -1) {
                return null;
            }
            model.setDesc(text.substring(SPLIT.length(), idx).trim());
            text = text.substring(idx + 1);
        }
        text += "\n";
        int count = 0;
        int sIndex = 0;
        int index = -1;
        while ((index = text.indexOf("\n", index + 1)) > -1) {
            String line = text.substring(sIndex, index).trim();
            if (line.startsWith("#") || line.startsWith("//")) {
                sIndex = index;
                continue;
            }
            if (count == 0) {
                int idx = line.indexOf(" ");
                if (idx > -1) {
                    model.setMethod(line.substring(0, idx).trim());
                } else {
                    idx = 0;
                }
                model.setUrl(line.substring(idx).trim());
                count++;
            } else if (count == 1) {
                if ("".equals(line)) {
                    count++;
                } else {
                    int idx = line.indexOf(":");
                    if (idx == -1) {
                        throw new RuntimeException("脚本异常，header缺少分号：" + line);
                    }
                    model.getHeaders().put(line.substring(0, idx).trim(), line.substring(idx + 1).trim());
                }
            }
            sIndex = index;
            if (count > 1) {
                break;
            }
        }

        if (sIndex < text.length() - 1) {
            text = text.substring(sIndex).trim();
            int sIdx = text.indexOf(SCRIPT_START);
            if (sIdx > -1) {
                int eIdx = text.indexOf(SCRIPT_END, sIdx);
                if (eIdx > -1) {
                    model.setScript(text.substring(sIdx + SCRIPT_START.length(), eIdx).trim());
                } else {
                    throw new RuntimeException("脚本错误，缺少: " + SCRIPT_END);
                }
                model.setBody(text.substring(0, sIdx).trim());
            } else {
                model.setBody(text);
            }
        }
        return model;
    }


    @Data
    public static class HttpFileModel {
        private String fileName;
        private List<RequestModel> requestModels = new ArrayList<>();
    }

    @Data
    public static class RequestModel {
        private String desc;
        private String method;
        private String url;
        private Map<String, String> headers = new HashMap<>();
        private String body;
        private String script;
    }
}
