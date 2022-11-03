package com.cunjun.demo.controller;

import com.cunjun.demo.business.ResultData;
import com.cunjun.demo.service.ComputeTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cunjun Wang (zhixin) on 2022/11/2
 */
@RestController
@RequestMapping(value = "")
@Api(value = "计算路程时间", tags = {"计算路程时间"})
public class ComputeTimeController {

    @Autowired
    private ComputeTimeService computeTimeService;

    @RequestMapping(value = "/compute/time", method = RequestMethod.GET)
    @ApiOperation(value = "计算路程时间", notes = "计算路程时间")
    public ResultData queryUserById(String departTime, String originAddress) {
        computeTimeService.computeTime(departTime, originAddress);
        return ResultData.ok();
    }

}
