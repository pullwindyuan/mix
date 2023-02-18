package org.hlpay.base.bo;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;









public class PayChannelAuditBo
  implements Serializable
{
  @ApiModelProperty(value = "渠道主键ID：该值为空表示新增，有值表示修改", required = true)
  private Integer id;
  @ApiModelProperty(value = "审核状态：0-不通过,1-通过", required = true)
  private Integer audit;
  private static final long serialVersionUID = 1L;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getAudit() {
    return this.audit;
  }

  public void setAudit(Integer audit) {
    this.audit = audit;
  }



  public static long getSerialVersionUID() {
    return 1L;
  }
}
