package com.yixiang.api.brand.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.redis.Redis;
import com.yixiang.api.brand.service.BrandCarComponent;
import com.yixiang.api.util.Constants;
import com.yixiang.api.util.DataUtil;
import com.yixiang.api.util.Result;

@RestController
@RequestMapping("/brand/car")
@CrossOrigin(methods=RequestMethod.POST,origins=Constants.TRUST_CROSS_ORIGINS)
public class BrandCarController {

	@Autowired
	private BrandCarComponent brandCarComponent;
	
	//下发精选车型
	@RequestMapping("/special")
	public Result querySpecialCars(@RequestParam(required=false)Integer source){
		List<Map<Object,Object>> result=brandCarComponent.querySpecialCars(source);
		if(Result.noError()){
			Result.putValue(result);
		}
		return Result.getThreadObject();
	}
	
	//下发所有品牌车型
	@RequestMapping("/list")
	public Result queryAllCars(@RequestParam Map<String,Object> param){
		List<Map<Object,Object>> result=brandCarComponent.queryAllCars(param);
		if(Result.noError()){
			Result.putValue(result);
		}
		return Result.getThreadObject();
	}
	
	//车型热门搜索关键字
	@RequestMapping("/keys")
	public Result hotKeys(@RequestParam(required=false) String uuid){
		String keys=Redis.use().get("car_hot_keys");
		if(StringUtils.isNotEmpty(keys)){
			Result.putValue(Arrays.asList(keys.split(",")).stream().map(i->DataUtil.mapOf("key",i)).collect(Collectors.toList()));
		}
		return Result.getThreadObject();
	}
	
	//获取所有车型并按照首字母品牌分组排序
	@RequestMapping("")
	public Result queryAllBrandCars(){
		List<Map<Object,Object>> result=brandCarComponent.queryAllBrandCars();
		if(Result.noError()){
			Result.putValue(result);
		}
		return Result.getThreadObject();
	}
	
	//下发品牌车型检索条件
	@RequestMapping("/items")
	public Result getBrandCarQueryItems(){
		JSONObject result=brandCarComponent.getBrandCarQueryItems();
		if(Result.noError()){
			Result.putValue(result);
		}
		return Result.getThreadObject();
	}
	
	//检索符合查询条件的品牌车型
	@RequestMapping("/query")
	public Result queryBrandCars(@RequestParam(required=false)String price,@RequestParam(required=false)String car
			,@RequestParam(required=false)String batteryLife,@RequestParam(defaultValue="1")Integer source
			,@RequestParam(defaultValue="1")Integer page,@RequestParam(required=false)Integer brandId){
		List<Map<Object,Object>> result=brandCarComponent.queryBrandCars(price, car, batteryLife, source, brandId, page);
		if(Result.noError()){
			Result.putValue(result);
		}
		return Result.getThreadObject();
	}
	
	//获取品牌车型详情信息
	@RequestMapping("/detail")
	public Result getBrandCarDetail(@RequestParam Integer id){
		Map<Object,Object> result=brandCarComponent.getBrandCarDetail(id);
		if(Result.noError()){
			Result.putValue(result);
		}
		return Result.getThreadObject();
	}
	
	//车型分享成功回调
	@RequestMapping("/share/callback")
	public Result shareCallback(@RequestParam(required=false) Integer id){
		brandCarComponent.shareSuccessCallback(id);
		return Result.getThreadObject();
	}
	
}
