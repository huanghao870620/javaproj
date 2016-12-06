package com.ld.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ld.util.RandomCreator;
/**
 * 
 * @author huang.hao
 *
 */
public class DrawRandom {
	
	private Graphics g;
	private String rands;
	
	public DrawRandom(Graphics g, String rands){
		  this.g = g;
		  this.rands = rands;
	}

	public void drawBackground() {
		// 画背景
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, RandomCreator.WIDTH, RandomCreator.HEIGHT);

		// 随机产生 120 个干扰点

		for (int i = 0; i < 120; i++) {
			int x = (int) (Math.random() * RandomCreator.WIDTH);
			int y = (int) (Math.random() * RandomCreator.HEIGHT);
			int red = (int) (Math.random() * 255);
			int green = (int) (Math.random() * 255);
			int blue = (int) (Math.random() * 255);
			g.setColor(new Color(red, green, blue));
			g.drawOval(x, y, 1, 0);
		}
	}

	public void drawRands() {
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.ITALIC | Font.BOLD, 18));

		// 在不同的高度上输出验证码的每个字符
		g.drawString("" + rands.charAt(0), 1, 17);
		g.drawString("" + rands.charAt(1), 16, 15);
		g.drawString("" + rands.charAt(2), 31, 18);
		g.drawString("" + rands.charAt(3), 46, 16);
	}
	
}
