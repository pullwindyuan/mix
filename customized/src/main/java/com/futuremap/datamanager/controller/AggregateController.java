package com.futuremap.datamanager.controller;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.annotation.CustomResponse;
import com.futuremap.datamanager.dataProcess.Aggregate;
import com.futuremap.datamanager.bean.DTO.QueryDTO;
import com.futuremap.datamanager.dataProcess.TestSortForest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Api(value = "数据提取", tags = {"数据提取"})
@RestController
@RequestMapping("/aggregate")
public class AggregateController {
	@Autowired
	private Aggregate aggregate;

	@PostMapping("/sum")
	@ApiOperation(value = "求和统计", notes = "求和统计")
	@CustomResponse
	public JSONObject independentAnalyze(@RequestBody QueryDTO queryDTO) {
		return aggregate.independentAnalyze(queryDTO);
	}

	@PostMapping("/unionAnalyze")
	@ApiOperation(value = "联合多维统计分析", notes = "联合多维统计分析")
	@CustomResponse
	public JSONObject unionAnalyze(@RequestBody QueryDTO queryDTO) {
		return aggregate.unionAnalyze(queryDTO);
	}

	@PostMapping("/timeSeriesTendency")
	@ApiOperation(value = "时间序列趋势分析", notes = "时间序列趋势分析")
	@CustomResponse
	public JSONObject timeSeriesTendency(@RequestBody QueryDTO queryDTO) {
		return aggregate.timeSeriesTendency(queryDTO);
	}


//	@RequestMapping("/sum")
//	@ApiOperation(value = "获取从数据中提取的分类筛选目录树", notes = "templateId为和导入的数据配套的表头配置模板ID")
//	@CustomResponse
//	public JSONObject getDictionaryForest() {
//		return extract.getForestByTemplateId("617fba4abc59492bd781eff2");
//	}

	@PostMapping("/sum/msg")
	@ApiOperation(value = "测试通过消息队列流式计算求和统计", notes = "测试通过消息队列流式计算求和统计")
	@CustomResponse
	public JSONObject processData() {
		return null;
	}
}
