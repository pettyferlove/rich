package com.github.rich.message.stream;

import com.github.rich.message.stream.sink.UserMessageSink;
import com.github.rich.message.stream.source.UserMessageSource;

/**
 * @author Petty
 */
public interface UserMessageProcessor extends UserMessageSink, UserMessageSource {
}
