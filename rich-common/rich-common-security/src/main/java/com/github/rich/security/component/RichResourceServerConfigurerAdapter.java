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

import com.github.rich.common.core.config.FilterIgnorePropertiesConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.client.RestTemplate;


/**
 * @author Petty
 */
@Slf4j
public class RichResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
	@Autowired
	protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;
	@Autowired
	protected RemoteTokenServices remoteTokenServices;
	@Autowired
	private FilterIgnorePropertiesConfig ignorePropertiesConfig;
	@Autowired
	private RestTemplate lbRestTemplate;

	/**
	 * 将不需要鉴权的url加载到忽略链
	 * @param httpSecurity HttpSecurity
	 */
	@Override
	@SneakyThrows
	public void configure(HttpSecurity httpSecurity) {
		//允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
		httpSecurity.headers().frameOptions().disable();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>
			.ExpressionInterceptUrlRegistry registry = httpSecurity
			.authorizeRequests();
		ignorePropertiesConfig.getAnon()
			.forEach(url -> registry.antMatchers(url).permitAll());
		registry.anyRequest().authenticated()
			.and().csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		/*UserAuthenticationConverter userTokenConverter = new PigUserAuthenticationConverter();
		accessTokenConverter.setUserTokenConverter(userTokenConverter);*/

		remoteTokenServices.setRestTemplate(lbRestTemplate);
		remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
		resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
			.tokenServices(remoteTokenServices);
	}
}
