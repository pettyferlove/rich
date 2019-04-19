/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.rich.security.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


/**
 * @author Petty
 */
@Slf4j
@ComponentScan("com.github.rich.security")
public class RichResourceServerAutoConfiguration {


    /**
     * 自定义RestTemplate 增加错误处理以及日志打印
     * @return RestTemplate
     */
    @Bean
    @Primary
    @LoadBalanced
    public RestTemplate richRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) {
                try {
                    if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
                        super.handleError(response);
                    }
                } catch (Exception e) {
                    log.error("RestTemplate richRestTemplate->Error Response:{}", response);
                }
            }
        });
        return restTemplate;
    }
}
