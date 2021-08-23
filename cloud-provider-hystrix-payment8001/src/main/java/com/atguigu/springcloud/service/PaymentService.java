package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常访问，肯定OK
     */
    public String paymentInfo_OK(Integer id){
        return "线程池：  "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id ;
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="10000")
    })
    public String paymentInfo_Timeout(Integer id){
        //int a=1/0;
        int timeNumber = 3;
        try{ TimeUnit.SECONDS.sleep(timeNumber); }catch (InterruptedException e){ e.printStackTrace(); }
        return "线程池：  "+Thread.currentThread().getName()+"  paymentInfo_TimeOut,id:  "+id +"  耗时："+timeNumber+"秒"+"  O(∩_∩)O";
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "线程池：  "+Thread.currentThread().getName()+"  8001系统繁忙或运行异常 请稍后再试,id:  "+id+"  ┭┮﹏┭┮";

    }

    //--------------------以上是服务降级，以下是服务熔断-------------------
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id<0){
            throw new RuntimeException("****id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id){
        return "id 不能负数，请稍后再试，id:"+id;
    }
}