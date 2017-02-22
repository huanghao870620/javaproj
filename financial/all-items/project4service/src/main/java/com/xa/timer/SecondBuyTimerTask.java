package com.xa.timer;

import java.util.TimerTask;

import com.xa.entity.DsGood;
import com.xa.entity.Orders;
import com.xa.enumeration.OrdersState;
import com.xa.mapper.DsGoodMapper;
import com.xa.mapper.OrdersMapper;
/**
 * 
 * @author burgess
 *
 */
public class SecondBuyTimerTask extends TimerTask {
	
	private OrdersMapper ordersMapper;
	
	private Long orderId;
	
	private DsGoodMapper dsGoodMapper;
	
	private Long dgId;
	
	public SecondBuyTimerTask(OrdersMapper ordersMapper, Long orderId,
			DsGoodMapper dsGoodMapper, Long dgId){
		this.ordersMapper = ordersMapper;
		this.orderId=orderId;
		this.dsGoodMapper = dsGoodMapper;
		this.dgId=dgId;
	}

	@Override
	public void run() {
		Orders order= ordersMapper.selectByPrimaryKey(orderId);
		Integer state= order.getState();
		if(state.intValue() == OrdersState.FOR_PAYMENT.getValue()){
			order.setState(OrdersState.HAS_BEEN_CANCELLED.getValue());//取消订单
			ordersMapper.updateByPrimaryKeySelective(order);
			
			DsGood dsGood= this.dsGoodMapper.selectByPrimaryKey(dgId);
			 Integer inventory= dsGood.getInventory();
			 dsGood.setInventory(inventory+1);
			 this.dsGoodMapper.updateByPrimaryKeySelective(dsGood);
		}
	}

}
