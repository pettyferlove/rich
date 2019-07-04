package com.github.rich.message.stream;

import com.github.rich.message.stream.sink.LoginCaptchaSmsSink;
import com.github.rich.message.stream.source.LoginCaptchaSmsSource;

/**
 * @author Petty
 */
public interface LoginCaptchaSmsProcessor extends LoginCaptchaSmsSink, LoginCaptchaSmsSource {
}
