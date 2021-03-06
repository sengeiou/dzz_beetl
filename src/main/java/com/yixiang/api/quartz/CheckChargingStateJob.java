package com.yixiang.api.quartz;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yixiang.api.order.service.OrderInfoComponent;

public class CheckChargingStateJob implements Job {
	
	@Autowired
	private OrderInfoComponent orderInfoComponent;
	
	Logger log=LoggerFactory.getLogger(getClass());
	
	public CheckChargingStateJob(){}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		String orderId=context.getJobDetail().getJobDataMap().getString("orderId");
		if(StringUtils.isNotEmpty(orderId)){
			log.info("调度任务:"+context.getJobDetail().getKey().getName()+"开始执行充电状态检查任务:orderId="+orderId);
			orderInfoComponent.queryChargingState(Integer.valueOf(orderId), false);
		}else{
			log.info("调度任务:"+context.getJobDetail().getKey().getName()+"未获取到必要参数:orderId="+orderId);
		}
	}

}
