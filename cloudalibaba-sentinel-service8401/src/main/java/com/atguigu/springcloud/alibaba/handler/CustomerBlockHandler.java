package com.atguigu.springcloud.alibaba.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public CommonResult handlerException(BlockException exception){
        return new CommonResult(4444,"按客户自定义,global,handlerException------1");
    }

    public CommonResult handlerException2(BlockException exception){
        return new CommonResult(4444,"按客户自定义,global,handlerException-------2");
    }
}
