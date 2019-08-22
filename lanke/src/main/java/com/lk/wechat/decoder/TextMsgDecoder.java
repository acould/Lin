package com.lk.wechat.decoder;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lk.wechat.request.DefaultTextMessage;
import com.lk.wechat.request.TextMessage;

@Component
@Service
public class TextMsgDecoder {
	public TextMessage decode(String content) {
		return new DefaultTextMessage(content);
	}
}
