package com.wzxc.szwenzhou.controller;

import com.wzxc.common.core.domain.KbengineResult;
import com.wzxc.common.utils.security.Base64Utils;
import com.wzxc.szwenzhou.service.IDxQuotaCurrentService;
import com.wzxc.szwenzhou.vo.DxQuotaCurrent;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/dxQuotaCurrent")
@Slf4j
public class DxQuotaController {

    @Autowired
    private IDxQuotaCurrentService dxQuotaCurrentService;

    /**
     * 根据指标名称查询
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键", required = false, paramType = "query", dataType="long")
    })
    @ApiResponses({
            @ApiResponse(code = 13000, message = "OK", response = DxQuotaCurrent.class),
            @ApiResponse(code = 13500, message = "ERROR")
    })
    @PostMapping("/{quotaName}")
    public KbengineResult list(@PathVariable("quotaName") String quotaName) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        List<DxQuotaCurrent> dxQuotaCurrents = dxQuotaCurrentService.selectDxQuotaCurrentByName(new String(Base64Utils.decode(quotaName)));
        resultMap.put("rows", dxQuotaCurrents);
        resultMap.put("total", dxQuotaCurrents.size());
        return KbengineResult.success("查询成功", resultMap);
    }
}
