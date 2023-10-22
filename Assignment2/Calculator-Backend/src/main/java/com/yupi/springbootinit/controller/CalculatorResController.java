package com.yupi.springbootinit.controller;

import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.DeleteRequest;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.entity.CalculatorRes;
import com.yupi.springbootinit.model.entity.User;
import com.yupi.springbootinit.service.CalculatorResService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/calculatorRes")
@Slf4j
public class CalculatorResController {

    @Resource
    private CalculatorResService calculatorResService;

    // region 增删改查

    /**
     * 创建
     */
    @PostMapping("/add")
    public BaseResponse<Long> addCalculatorRes(@RequestBody CalculatorRes calculatorResAddRequest, HttpServletRequest request) {
        if (calculatorResAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CalculatorRes calculatorRes = new CalculatorRes();
        BeanUtils.copyProperties(calculatorResAddRequest, calculatorRes);
        boolean result = calculatorResService.save(calculatorRes);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newCalculatorResId = calculatorRes.getId();
        return ResultUtils.success(newCalculatorResId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCalculatorRes(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        // 判断是否存在
        CalculatorRes oldCalculatorRes = calculatorResService.getById(id);
        ThrowUtils.throwIf(oldCalculatorRes == null, ErrorCode.NOT_FOUND_ERROR);
        boolean b = calculatorResService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<CalculatorRes> getCalculatorResVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CalculatorRes calculatorRes = calculatorResService.getById(id);
        if (calculatorRes == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(calculatorRes);
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param calculatorResQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/vo")
    public BaseResponse<List<CalculatorRes>> listCalculatorResVOByPage(@RequestBody CalculatorRes calculatorResQueryRequest,
            HttpServletRequest request) {
        // 限制爬虫
        List<CalculatorRes> calculatorResList = calculatorResService.list();
        return ResultUtils.success(calculatorResList);
    }

    // endregion

}
