package com.github.rich.message.stream;

import com.github.rich.message.stream.sink.UserMessageBroadcastSink;
import com.github.rich.message.stream.source.UserMessageBroadcastSource;

/**
 * @author Petty
 */
public interface UserMessageBroadcastProcessor extends UserMessageBroadcastSink, UserMessageBroadcastSource {
}
