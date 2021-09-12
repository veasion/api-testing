package cn.veasion.auto;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * BaseTest
 *
 * @author luozhuowei
 * @date 2021/9/12
 */
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @Autowired
    protected MockMvc mockMvc;
    protected String token;

    static final String JSON_DATA_KEY = "content";
    static final String HEADER_TOKEN = "Authorization";

    @BeforeEach
    @DisplayName("登录")
    public void checkLogin() throws Exception {
        if (!StringUtils.hasText(token)) {
            String json = "{\"username\": \"admin\",\"password\": \"123456\"}";
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                    .post("/api/auth/login")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(json)
            ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            String content = mvcResult.getResponse().getContentAsString();
            Assertions.assertTrue(StringUtils.hasText(content));
            JSONObject data = JSONObject.parseObject(content);
            token = data.getJSONObject(JSON_DATA_KEY).getString("data");
            System.out.println(HEADER_TOKEN + ": " + token);
        }
    }

    protected void assertResponse(JSONObject data) {
        Assertions.assertEquals(data.getInteger("code"), 200);
    }

    protected JSONObject postRequest(String url, Object data) throws Exception {
        if (!(data instanceof String)) {
            data = JSONObject.toJSONString(data);
        }
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post(url)
                .header(HEADER_TOKEN, token)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content((String) data)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        return JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
    }

    protected JSONObject getRequest(String url, Map<String, Object> params) throws Exception {
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder(url);
            sb.append(url.contains("?") ? "?" : "&");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.setLength(sb.length() - 1);
            url = sb.toString();
        }
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get(url)
                .header(HEADER_TOKEN, token)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        return JSONObject.parseObject(mvcResult.getResponse().getContentAsString());
    }

    protected String randCode() {
        return String.valueOf(System.currentTimeMillis()).substring(8);
    }

}
