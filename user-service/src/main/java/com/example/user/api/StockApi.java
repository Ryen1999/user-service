package com.example.user.api;

import com.example.user.api.dto.StockDeductRequest;
import com.example.user.api.dto.StockResponse;
import com.example.user.api.dto.UserInfoResDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface StockApi {
	
	@GetMapping("/api/stocks/{skuCode}")
	StockResponse getStock(@PathVariable("skuCode") String skuCode);
	
	@PostMapping("/api/stocks/deduct")
	String deduct(@RequestBody StockDeductRequest request);
	
	@GetMapping("/api/user/list")
	List<UserInfoResDTO> list();
}