package com.futuremap.mqService;

/**
 * java类简单作用描述
 *
 * @ProjectName: newcard
 * @Package: com.hl.card.common.mq
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2018/11/21 14:53
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018/11/21 14:53
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>Copyright: Copyright (c) 2018</p>
 */
public interface QmCallback {
    public void OnPickup(String queue_name, String msg);
}
