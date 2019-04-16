package com.github.rich.common.core.bean.request;

import com.github.rich.common.core.bean.filter.HtmlFilter;
import com.github.rich.common.core.bean.filter.SqlFilter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Petty
 */
public class SecurityHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final HtmlFilter HTML_FILTER = new HtmlFilter();

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request the {@link HttpServletRequest} to be wrapped.
     * @throws IllegalArgumentException if the request is null
     */
    public SecurityHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * The default behavior of this method is to return getInputStream()
     * on the wrapped request object.
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非JSON类型忽略处理
        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return super.getInputStream();
        }

        // JSON数据为空忽略处理
        String json = IOUtils.toString(super.getInputStream(), "UTF-8");
        if (StringUtils.isBlank(json)) {
            return super.getInputStream();
        }

        json = xssEncode(json);
        json = SqlFilter.sqlInject(json);
        ByteArrayInputStream jsonByte = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return jsonByte.read();
            }
        };
    }

    /**
     * 检查请求参数
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (org.apache.commons.lang.StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
            value = SqlFilter.sqlInject(value);
        }
        return value;
    }

    /**
     * 检查请求值
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] parameters = super.getParameterValues(name);
        if (parameters == null || parameters.length == 0) {
            return null;
        }

        for (int i = 0; i < parameters.length; i++) {
            parameters[i] = xssEncode(parameters[i]);
            parameters[i] = SqlFilter.sqlInject(parameters[i]);
        }
        return parameters;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = xssEncode(values[i]);
                values[i] = SqlFilter.sqlInject(values[i]);
            }
            map.put(key, values);
        }
        return map;
    }

    /**
     * 检查请求头
     * @param name
     * @return
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(xssEncode(name));
        if (org.apache.commons.lang.StringUtils.isNotBlank(value)) {
            value = xssEncode(value);
            value = SqlFilter.sqlInject(value);
        }
        return value;
    }

    private String xssEncode(String str) {
        return HTML_FILTER.filter(str);
    }
}
