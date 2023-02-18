package org.hlpay.base.payFeign;

import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import javax.servlet.ServletException;
import org.hlpay.base.bo.WorkFlowAuditAddBo;
import org.hlpay.common.entity.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("workflow-audit-provider")
public interface WorkflowAudit {
  @ApiOperation(value = "同步审核内容到工作流", notes = "同步审核内容到工作流")
  @RequestMapping({"/workflow/sync/add"})
  @ResponseBody
  CommonResult workflowSyncAdd(WorkFlowAuditAddBo paramWorkFlowAuditAddBo) throws ServletException, IOException;
}
