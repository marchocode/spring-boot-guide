package xyz.chaobei.resttemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class RestRequestClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject get(String url) {
        return get(url, Collections.emptyMap());
    }

    /**
     * 发起一个GET请求
     *
     * @param url    请求URL
     * @param params URL参数
     * @return JSON对象
     */
    public JSONObject get(String url, Map<String, String> params) {

        LinkedMultiValueMap linkedMultiValueMap = new LinkedMultiValueMap();
        for (Map.Entry<String, String> item : params.entrySet()) {
            linkedMultiValueMap.add(item.getKey(), item.getValue());
        }

        log.info("request get url={},params={}", url, params);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(linkedMultiValueMap).build();

        log.info("request get http data={}", uriComponents.toUriString());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uriComponents.toUriString(), String.class);

        log.info("request get http code={}", responseEntity.getStatusCode().value());
        log.info("request get http data={}", responseEntity.getBody());

        return JSON.parseObject(responseEntity.getBody());
    }

    /**
     * 通过POST请求下载一个文件
     *
     * @param url  请求URL
     * @param data 数据对象
     * @return 下载的字节数组
     */
    public byte[] postDownload(String url, Object data) {

        log.info("postDownload url={}", url);
        log.info("postDownload data={}", data);

        ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url, data, byte[].class);
        log.info("postDownload http code={}", responseEntity.getStatusCodeValue());

        return responseEntity.getBody();
    }


}
