package com.example.userservice.controller;

import com.example.user.api.StockApi;
import com.example.user.api.dto.StockDeductRequest;
import com.example.user.api.dto.StockResponse;
import com.example.user.api.dto.UserInfoResDTO;
import com.example.userservice.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 利用Feign的继承 以接口契约形式让消费者调用(springMvc不介意使用，业内大部分公司使用)
 * @Author 郭旺
 * @Date 16:06 2026.3.5
 * @Param
 * @return
 **/
@RestController
public class StockController implements StockApi {
	
	private final Map<String, Integer> stockMap = new ConcurrentHashMap<>();
	@Autowired
	UserInfoDao userInfoDao;
	
	
	public StockController() {
		stockMap.put("SKU-1", 100);
		stockMap.put("SKU-2", 50);
	}
	
	@Override
	public StockResponse getStock(String skuCode) {
		Integer available = stockMap.getOrDefault(skuCode, 0);
		return new StockResponse(skuCode, available);
	}
	
	@Override
	public String deduct(StockDeductRequest request) {
		String skuCode = request.getSkuCode();
		int count = request.getCount() == null ? 0 : request.getCount();
		stockMap.compute(skuCode, (k, v) -> {
			int current = v == null ? 0 : v;
			return Math.max(current - count, 0);
		});
		return "deducted";
	}
	
	@Override
	public List<UserInfoResDTO> list() {
		List<UserInfoResDTO> users = userInfoDao.queryUserInfoList();
		return users;
	}
}