package com.ld.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ld.mapper.PrivateMessageMapper;
import com.ld.model.PrivateMessage;
import com.ld.service.PrivateMessageService;

@Service
@Transactional
public class PrivateMessageServiceImpl  extends BaseServiceImpl<PrivateMessage, PrivateMessageMapper>
implements PrivateMessageService<PrivateMessage> {

}
