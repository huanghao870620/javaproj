package com.xa.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xa.entity.ShoppingCart;
import com.xa.entity.ShoppingCartGoods;
import com.xa.service.ShoppingCartGoodsService;
import com.xa.service.ShoppingCartService;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController extends BaseController {

	@Autowired
	private ShoppingCartGoodsService<ShoppingCartGoods> shoppingCartGoodsService;
	
	@Autowired
	private ShoppingCartService<ShoppingCart> shoppingCartService;
	
	/**
	 * 购物车添加商品
	 * @param goodId
	 * @param cartId
	 * @param sign
	 */
	@RequestMapping("addGoodsToCart")
	public void addGoodsToCart(ShoppingCartGoods scg,Long dgId, String sign){
		  try {
			this.sendAjaxMsg(this.shoppingCartGoodsService.addGoodsToCart(scg, dgId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 获取购物车通过买家id
	 * @param buyerId
	 * @param sign
	 */
	@RequestMapping("getCartByBuyerId")
	public void getCartByBuyerId(Long buyerId, String sign) {
		try {
			this.sendAjaxMsg(this.shoppingCartService.getCartByBuyerId(buyerId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取购物车中的信息
	 * @param random
	 * @param sign
	 */
	@RequestMapping("getAllCartGoods")
	public void getAllCartGoods(Long cartId,Integer pageNum,Integer pageSize, String sign){
		try {
			this.sendAjaxMsg(this.shoppingCartGoodsService.getAllCartGoods(cartId,pageNum, pageSize, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除购物车中的商品
	 */
	@RequestMapping("removeSCG")
	public void removeSCG(Long scgId, String sign) {
		try {
			this.sendAjaxMsg(this.shoppingCartGoodsService.removeSCG(scgId, sign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
