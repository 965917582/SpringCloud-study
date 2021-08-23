package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {

    //收集eureka上的所有服务
    ServiceInstance instances(List<ServiceInstance> serviceInstances);



}
