<div style="margin: 15px;">
	<form class="layui-form">
		<#if item.id?exists>
            <input type="text" name="id" hidden="hidden" value="${item.id?if_exists }">
		</#if>
		<div class="layui-form-item">
			<label class="layui-form-label">商户ID</label>
			<div class="layui-input-block">
				<input type="text" name="mchId" lay-verify="required" placeholder="请输入商户ID" autocomplete="off" class="layui-input" value="${item.mchId?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">渠道ID</label>
			<div class="layui-input-block">
				<select name="channelId" lay-verify="required">
					<option value=""></option>
                    <option value="ALIPAY_MOBILE" <#if (item.channelId!"") == "ALIPAY_MOBILE">selected="selected"</#if>>ALIPAY_MOBILE</option>
                    <option value="ALIPAY_PC" <#if (item.channelId!"") == "ALIPAY_PC">selected="selected"</#if>>ALIPAY_PC</option>
                    <option value="ALIPAY_WAP" <#if (item.channelId!"") == "ALIPAY_WAP">selected="selected"</#if>>ALIPAY_WAP</option>
                    <option value="ALIPAY_QR" <#if (item.channelId!"") == "ALIPAY_QR">selected="selected"</#if>>ALIPAY_QR</option>
                    <option value="WX_APP" <#if (item.channelId!"") == "WX_APP">selected="selected"</#if>>WX_APP</option>
                    <option value="WX_JSAPI" <#if (item.channelId!"") == "WX_JSAPI">selected="selected"</#if>>WX_JSAPI</option>
                    <option value="WX_NATIVE" <#if (item.channelId!"") == "WX_NATIVE">selected="selected"</#if>>WX_NATIVE</option>
                    <option value="WX_MWEB" <#if (item.channelId!"") == "WX_MWEB">selected="selected"</#if>>WX_MWEB</option>
                    <option value="HL_BIDOU_EXCHANGE_HTL" <#if (item.channelId!"") == "HL_BIDOU_EXCHANGE_HTL">selected="selected"</#if>>比豆兑换HTL</option>
                    <option value="HL_AWARD_HTL" <#if (item.channelId!"") == "HL_AWARD_HTL">selected="selected"</#if>>交易奖励HTL</option>
                    <option value="HL_AWARD" <#if (item.channelId!"") == "HL_AWARD">selected="selected"</#if>>比豆奖励</option>
                    <option value="HL_HTL_RECHARGE" <#if (item.channelId!"") == "HL_HTL_RECHARGE">selected="selected"</#if>>HTL充值</option>
                    <option value="HL_ETH_RECHARGE" <#if (item.channelId!"") == "HL_ETH_RECHARGE">selected="selected"</#if>>ETH充值</option>
                    <option value="HL_GUSD_RECHARGE" <#if (item.channelId!"") == "HL_GUSD_RECHARGE">selected="selected"</#if>>GUSD充值</option>
                    <option value="HL_HTL_WITHDRAW" <#if (item.channelId!"") == "HL_HTL_WITHDRAW">selected="selected"</#if>>HTL提取</option>
                    <option value="HL_ETH_WITHDRAW" <#if (item.channelId!"") == "HL_ETH_WITHDRAW">selected="selected"</#if>>ETH提取</option>
                    <option value="HL_GUSD_WITHDRAW" <#if (item.channelId!"") == "HL_GUSD_WITHDRAW">selected="selected"</#if>>GUSD提取</option>
                    <option value="HL_TRANS" <#if (item.channelId!"") == "HL_TRANS">selected="selected"</#if>>通证转账</option>
                    <option value="HL_RED_ENVELOPE" <#if (item.channelId!"") == "HL_RED_ENVELOPE">selected="selected"</#if>>通证红包</option>
                    <option value="HL_HTL_PAY" <#if (item.channelId!"") == "HL_HTL_PAY">selected="selected"</#if>>HTL支付</option>
                    <option value="HL_ETH_PAY" <#if (item.channelId!"") == "HL_ETH_PAY">selected="selected"</#if>>ETH支付</option>
                    <option value="HL_GUSD_PAY" <#if (item.channelId!"") == "HL_GUSD_PAY">selected="selected"</#if>>GUSD支付</option>
                    <option value="HL_HTL_ASYN_PAY" <#if (item.channelId!"") == "HL_HTL_ASYN_PAY">selected="selected"</#if>>HTL异步分账</option>
                    <option value="HL_ETH_ASYN_PAY" <#if (item.channelId!"") == "HL_ETH_ASYN_PAY">selected="selected"</#if>>ETH异步分账</option>
                    <option value="HL_GUSD_ASYN_PAY" <#if (item.channelId!"") == "HL_GUSD_ASYN_PAY">selected="selected"</#if>>GUSD异步分账</option>
                    <option value="UNION_PC" <#if (item.channelId!"") == "UNION_PC">selected="selected"</#if>>UNION_PC</option>
                    <option value="UNION_WAP" <#if (item.channelId!"") == "UNION_WAP">selected="selected"</#if>>UNION_WAP</option>
                    <option value="HUICHAO_WAP" <#if (item.channelId!"") == "YMD_WAP">selected="selected"</#if>>HUICHAO_WAP</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">渠道主体名称</label>
			<div class="layui-input-block">
				<select name="channelName" lay-verify="required">
					<option value=""></option>
					<option value="ALIPAY" <#if (item.channelName!"") == "ALIPAY">selected="selected"</#if>>支付宝</option>
					<option value="WX" <#if (item.channelName!"") == "WX">selected="selected"</#if>>微信支付</option>
                    <option value="KB" <#if (item.channelName!"") == "KB">selected="selected"</#if>>卡包</option>
                    <option value="HL" <#if (item.channelName!"") == "HL">selected="selected"</#if>>深圳航旅</option>
                    <option value="BBG" <#if (item.channelName!"") == "BBG">selected="selected"</#if>>比比网络科技</option>
                    <option value="UNION" <#if (item.channelName!"") == "UNION">selected="selected"</#if>>银联</option>
                    <option value="HUICHAO" <#if (item.channelName!"") == "YMD">selected="selected"</#if>>汇潮</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">渠道商户ID</label>
			<div class="layui-input-block">
				<input type="text" name="channelMchId" lay-verify="required" placeholder="请输入渠道商户ID" autocomplete="off" class="layui-input" value="${item.channelMchId?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">渠道收款账号</label>
			<div class="layui-input-block">
				<input type="text" name="channelAccount" lay-verify="required" placeholder="请输入渠道收款账号" autocomplete="off" class="layui-input" value="${item.channelAccount?if_exists }">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否启用</label>
			<div class="layui-input-block">
				<input type="checkbox" name="state" lay-skin="switch" <#if (item.state!1) == 1>checked="checked"</#if> >
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">参数</label>
			<div class="layui-input-block">
				<textarea name="param" placeholder="请输入参数" lay-verify="required" class="layui-textarea">${item.param?if_exists }</textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">备注</label>
			<div class="layui-input-block">
				<input type="text" name="remark" placeholder="请输入备注" autocomplete="off" class="layui-input" value="${item.remark?if_exists }">
			</div>
		</div>
		<button lay-filter="edit" lay-submit style="display: none;"></button>
	</form>
</div>