package com.ld.live;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

import org.springframework.web.socket.server.standard.SpringConfigurator;

public class GetHttpSessionConfigurator extends SpringConfigurator {
	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		super.modifyHandshake(sec, request, response);
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		if(httpSession!=null)
		{
		sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
		sec.getUserProperties().put(HttpServletRequest.class.getName(), request);
		}
	}
}
