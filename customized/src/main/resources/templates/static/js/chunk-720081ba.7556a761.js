(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-720081ba"],{"0a81":function(t,e,a){},"1e8f":function(t,e,a){},"2fc1":function(t,e,a){"use strict";a("39340")},"31a2":function(t,e,a){"use strict";a("1e8f")},39340:function(t,e,a){},"3b42":function(t,e,a){},"3d4e":function(t,e,a){"use strict";a("4fbd")},"3e58":function(t,e,a){"use strict";a("3b42")},"47a4":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"portrait-item"},[a("div",{staticClass:"portrait-item-title"},[t._v("阿尔法财AI风险预警")]),a("div",{staticClass:"p-content"},[a("div",{staticClass:"sub-title"},[t._v("AI分析结论")]),a("div",{staticClass:"p-content-line"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12,offset:0}},[a("StarBox",{attrs:{industryRank:t.scoreData.industryRank,score:t.scoreData.scoreTotal,companyInfo:t.companyInfo,levelList:t.scoreData.riskList,sort:t.scoreData.sort,scoreMarket:t.scoreData.marketScore,industryRankMarket:t.scoreData.marketRank,data:t.scoreData,showMore:!0,rankTxt:t.scoreData.rankTxt}})],1),a("el-col",{attrs:{span:12,offset:0}},[a("div",{staticClass:"radar"},[a("FourDimensions",{attrs:{companyInfo:t.companyInfo}})],1)])],1)],1),a("div",{staticClass:"p-content-line"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12,offset:0}},[a("HistoryTrend",{attrs:{companyInfo:t.companyInfo}})],1),a("el-col",{attrs:{span:12,offset:0}},[a("FourDimensionsCompetitor",{attrs:{soreTypeId:t.scoreData.scoreTypeId,companyInfo:t.companyInfo}})],1)],1)],1),a("div",{staticClass:"p-content-line"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12,offset:0}},[a("AllIndustryRank",{attrs:{companyInfo:t.companyInfo,scoreTypeId:t.scoreData.scoreTypeId,infoTitle:t.scoreData.industryTxt}})],1),a("el-col",{attrs:{span:12,offset:0}},[a("AllMarketRank",{attrs:{companyInfo:t.companyInfo,scoreTypeId:t.scoreData.scoreTypeId,infoTitle:t.scoreData.marketTxt}})],1)],1)],1)])])},i=[],r=a("e7de"),o=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:"四个维度1~5年风险概率"},on:{moreTap:t.mixinMoreTap}},[a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("EchartRadar2",{attrs:{layer:"",layerData:t.layerData,options:t.options,xdata:t.xdata,data:t.data,height:265}}),a("LegendBox",{attrs:{name3:"市场平均"}})],1)])],1)},s=[],c=(a("99af"),a("7db0"),a("a15b"),a("d81d"),a("b0c0"),a("a9e3"),a("b680"),a("96cf"),a("1da1")),l=a("2909"),u=a("7f89"),d=a("2abf"),p=a("f4dd"),m=a("3501"),f=a("f870"),h=a("33c3"),y={mixins:[m["a"]],components:{ChartBox:d["a"],EchartRadar2:f["a"],LegendBox:u["a"]},data:function(){return{xdata:[],data:[],options:{},loading:!1,reportData:{title:"AI预测未来风险概率",component:"FutureRisk",feedback:!0},layerData:{"健康病变":"该维度是指，企业因可能被隐藏的不健康因素（如财务指标折射的战略、业务、模式、组织等因素）及其勾稽关系，而导致发展受阻的风险概率。","生存危机":"该维度是指，企业因现金量及其相关因素的勾稽关系，而导致短期内无法履行财务义务的风险概率。","错失客户":"该维度是指，公司产品或解决方案因不匹配客户需求，而导致业务和销售落后于市场需求的风险概率。","成本失控":"该维度是指，公司运营成本或结构因有缺陷，而导致企业效率降低或经营失控的风险概率。"}}},created:function(){this._initUIView()},methods:{queryTap:function(){this._apiGetRiskSuggest({companyId:this.companyInfo.companyId})},formatResdata:function(t){var e=this,a=[],n=[],i=[],r=this.xdata.map((function(r,o){var s=t.industryAvg.find((function(t){return t.name===r.key}))||{},c=s.value||null,l=t.marketAvg.find((function(t){return t.name===r.key}))||{},u=l.value||null;n.push(c),i.push(u);var d=t.latitudeSummaries.find((function(t){return t.name===r.key}))||{},p=Number((100*d.value).toFixed(2))||null,m=e.xdata.map((function(){return 1e3}));return m[o]=p,a.push({name:e.companyInfo.companyName,type:"radar",data:[m],itemStyle:{color:"white"},lineStyle:{color:"white",opacity:0}}),p}));this.data=[{type:"radar",data:[{value:r,v:r,avg:n,avg2:i}],lineStyle:{color:"rgb(254, 224, 94)"},areaStyle:{color:"white",opacity:.2},symbolSize:2},{type:"radar",data:[{value:n,avg:n,v:r,avg2:i}],z:10,lineStyle:{color:"#00FF00"},itemStyle:{color:"rgba(0, 0, 0, 0.5)"},areaStyle:{color:"white",opacity:.2},symbolSize:2},{type:"radar",data:[{value:i,avg:n,v:r,avg2:i}],z:10,lineStyle:{color:"#FF00BA"},areaStyle:{color:"white",opacity:.2},itemStyle:{color:"rgba(0, 0, 0, 0.5)"},symbolSize:2}].concat(a)},_initUIView:function(){this._initXdata(),this._initOptions()},_initOptions:function(){var t=this;this.options={tooltip:{position:this.$tipPosition(0,["left","top"],[100,0]),formatter:function(e){if(e.seriesIndex>1)return"";var a="<strong>".concat(t.companyInfo.companyName," / 行业平均 / 市场平均</strong>"),n=e.data,i=n.v,r=n.avg,o=n.avg2,s=t.xdata.map((function(t,e){var a=[{text:"".concat(t.name,"风险概率"),value:"".concat(i[e],"%")},{text:"行业",value:"".concat(r[e],"%"),flex:"0 0 100px"},{text:"市场",value:"".concat(o[e],"%"),flex:"0 0 100px"}],n=Object(h["p"])(a);return n}));return[a].concat(Object(l["a"])(s)).join("")},extraCssText:"width: 440px;"}}},_initXdata:function(){this.xdata=[{name:"健康病变",key:"health",max:100,_color:"#7195F2"},{name:"生存危机",key:"life",max:100,_color:"#5FC054"},{name:"错失客户",key:"demand",max:100,_color:"#F8CB22"},{name:"成本失控",key:"costs",max:100,_color:"#ED5C4E"}]},_apiGetRiskSuggest:function(t){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(p["l"])(t);case 4:n=a.sent,e.formatResdata(n),a.next=11;break;case 8:a.prev=8,a.t0=a["catch"](0),e.data=[];case 11:e.loading=!1;case 12:case"end":return a.stop()}}),a,null,[[0,8]])})))()}}},g=y,v=a("2877"),x=Object(v["a"])(g,o,s,!1,null,"7d14dde7",null),b=x.exports,k=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:"历史风险走势与"+t.indexName+"对标"},on:{moreTap:t.mixinMoreTap}},[a("div",[a("div",{staticClass:"select"},[a("BenchmarkingIndex",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}],attrs:{size:"small",theme:"light",multiple:!1,defaultData:t.defaultIndexList},on:{change:t.indexChange}})],1),a("EchartLine",{attrs:{title:"",bgcolor:"white",options:t.options,xdata:t.xdata,data:t.data,textColor:"#303133",colors:t.colors,height:265}})],1)])],1)},T=[],w=(a("b64b"),a("ac1f"),a("5319"),a("1276"),a("5530")),C=a("ac8b"),I=a("5801"),S=a("f348"),_=a("a741"),R={mixins:[m["a"],_["a"]],components:{ChartBox:d["a"],BenchmarkingIndex:C["a"],EchartLine:I["a"]},data:function(){return{xdata:[],data:[],options:{},indexList:[],defaultIndexList:[119],colors:["rgba(0, 0, 0, 0.65)","#7195F2","#5FC054","#F8CB21","#EB4A64"],reportData:{title:"AI预警历史风险",component:"HistoryRisk"}}},computed:{indexName:function(){var t=this.indexList[0];return t?t.name:""}},created:function(){this._initUIView()},methods:{indexChange:function(t){this.indexList=t,this.queryTap()},queryTap:function(){if(!(this.indexList.length<=0)&&this.companyInfo.companyId){var t=this.mixinReqOneTime();this._apiGetCaseHistoryQuarter(Object(w["a"])({companyList:[this.companyInfo],indexList:this.indexList},t))}},formateResQuarter:function(t){if(t){var e=Object.keys(t.indexData[0].data);e.sort((function(t,e){return t.localeCompare(e)}));var a=[{name:"健康病变",key:"health"},{name:"生存危机",key:"default"},{name:"错失客户",key:"demand"},{name:"成本失控",key:"costs"}],n=a.map((function(a){var n=t.result[a.key],i=e.map((function(t){return n[t]}));return{data:i,name:a.name,lineStyle:{width:1.6}}})),i=t.indexData[0].data,r={data:e.map((function(t){return i[t]?Number((100*i[t]).toFixed(2)):i[t]})),name:"ROI",yAxisIndex:1,lineStyle:{width:1.6}};this._initOptions(this.indexList[0]),this.xdata=e.map((function(t){return t.replace(/_/g,"Q")})),this.data=[r].concat(Object(l["a"])(n))}},_initUIView:function(){},_initOptions:function(t){this.options={backgroundColor:"transparent",tooltip:{formatter:function(t){var e=t[0].name.split("Q").join("年")+"季度",a=t.map((function(t){var e=t.seriesName,a=0===t.seriesIndex?"":"风险概率",n=t.value?"".concat(t.value,"%"):"";return"".concat(e).concat(a," ").concat(n)}));return[e].concat(Object(l["a"])(a)).join("<br/>")}},legend:{textStyle:{color:"rgba(0, 0, 0, 0.65)"},bottom:20},grid:{bottom:50},dataZoom:h["d"],yAxis:[{splitLine:{show:!1},axisLine:{lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},type:"value",name:"风险概率",min:0,max:100,nameTextStyle:{color:"rgba(0, 0, 0, .65)"},axisLabel:{color:"rgba(0, 0, 0, 0.65)",formatter:"{value} %"},axisTick:{show:!1}},{axisLine:{lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisTick:{show:!1},nameTextStyle:{color:"rgba(0, 0, 0, .65)"},splitLine:{show:!1},type:"value",name:this.indexName,axisLabel:{color:"rgba(0, 0, 0, 0.65)",formatter:function(e){return"百分比"===t.unit?"".concat(e,"%"):"元"===t.unit?Object(S["f"])(e):"".concat(e).concat(t.unit)}}}],xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},splitLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, 0.65)"}}}},_apiGetCaseHistoryQuarter:function(t){var e=this;return Object(c["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,a.next=3,Object(p["e"])(t);case 3:n=a.sent,e.formateResQuarter(n[0]),a.next=9;break;case 7:a.prev=7,a.t0=a["catch"](0);case 9:case"end":return a.stop()}}),a,null,[[0,7]])})))()}}},O=R,L=(a("5bba"),Object(v["a"])(O,k,T,!1,null,"09c03c70",null)),j=L.exports,D=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:"风险概率与行业TOP3对比"},on:{moreTap:t.mixinMoreTap}},[a("div",[a("div",{staticClass:"select"},[a("BenchmarkingCompany",{ref:"mark",attrs:{size:"mini",theme:"light",hideFirst:""},on:{change:t.companyChange}})],1),a("EchartColumn",{ref:"columnCharts",attrs:{height:265,title:"",bgcolor:"white",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},N=[],B=(a("4160"),a("159b"),a("623e")),M=a("c9a5"),A={mixins:[B["a"],m["a"],M["a"]],components:{ChartBox:d["a"]},data:function(){var t=this.$colors.map((function(t){return"".concat(t,"F2")}));return{colors:t,reportData:{title:"企业风险在市场位置",component:"EnterpriseRiskPosition"}}},created:function(){this._initUIView()},methods:{companyChange:function(t){this.queryData.companyList=t,this.queryRiskTap()},queryRiskTap:function(){if(0!==this.queryData.companyList.length){var t=this.queryData.companyList.map((function(t){return{companyId:t.id,companyName:t.companyName,code:t.code}}));this._apiGetCaseRisk({companyList:[this.companyInfo].concat(Object(l["a"])(t)),yearRangeStart:this.$startTime,yearRangeEnd:this.$endTime})}},_initUIView:function(){this._initOptions()},_initOptions:function(){this.options={backgroundColor:"transparent",legend:{textStyle:{color:"rgba(0,0,0,0.65)"}},yAxis:{nameTextStyle:{color:"rgba(0, 0, 0, .65)"},name:"风险概率/%",axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0,0,0,0.65)"}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0,0,0,0.65)"}},grid:{right:0},tooltip:{formatter:function(t){return t.forEach((function(t){t.seriesName+="风险概率"})),Object(h["j"])(t,"%")},extraCssText:"width: 300px;"}}}}},E=A,F=(a("2fc1"),Object(v["a"])(E,D,N,!1,null,"0a49366a",null)),$=F.exports,q=a("3ca5"),z=a("20b9"),U=a("600e"),V={mixins:[q["a"]],components:{StarBox:r["a"],FourDimensions:b,HistoryTrend:j,FourDimensionsCompetitor:$,AllIndustryRank:z["a"],AllMarketRank:U["a"]},data:function(){return{}},created:function(){},methods:{}},G=V,P=(a("c111"),Object(v["a"])(G,n,i,!1,null,"46f125ae",null));e["default"]=P.exports},"4fbd":function(t,e,a){},5801:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return t.loadingBox?a("g-echart-box",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],attrs:{bgcolor:t.bgcolor,"g-loading-background":t.bgcolor,"g-loading-text-color":t.textColor,txtColor:t.txtColor,imgWidth:t.imgWidth,empty:0===t.xdata.length}},[a("div",{ref:"chart",style:t.boxStyle})]):a("div",{ref:"chart",style:t.boxStyle})},i=[],r=(a("4160"),a("a9e3"),a("159b"),a("33c3")),o=a("164e"),s={props:{title:{type:String,default:""},imgWidth:{type:Number,default:200},txtColor:{type:String,default:"white"},data:{type:Array,default:function(){return[]}},xdata:{type:Array,default:function(){return[]}},height:{type:[Number,String],default:320},bgcolor:{type:String,default:"white"},textColor:{type:String,default:"#606266"},colors:{type:Array,default:function(){return[]}},options:{type:Object,default:function(){return null}},xTitle:{type:String,default:""},yTitle:{type:String,default:""},emptyText:String,loadingScale:{type:Number,default:1},smooth:{type:Boolean,default:!1},loadingBox:{type:Boolean,default:!0}},data:function(){return{loading:!0}},computed:{boxStyle:function(){return{height:this.height+"px",margin:"0 auto"}}},mounted:function(){window.addEventListener("resize",this.resize)},methods:{resize:function(){this.chart&&this.chart.resize()},_initCanvas:function(){var t=this;this._renderIndex>1&&this.loading&&(this.loading=!1),this.loading&&this.xdata.length>0&&(this.loading=!1),this.$nextTick((function(){t.loading||t._initChart()}))},_initChart:function(){var t=this;if(!this.chart){var e=this.$refs.chart;if(!e)return;this.chart=o["init"](e)}this.data.length>0&&this.data.forEach((function(e,a){e.type="bar"===e.type?"bar":"line",e.symbolSize=e.symbolSize||0,e.smooth=t.smooth,t.colors.length&&t.colors.length>0&&t.colors.forEach((function(t,n){a===n&&(e.color=t)}))}));var a=Object(r["l"])(this.data,this.xdata,this.title,this.xTitle,this.yTitle);Object(r["c"])(a,this.options),this.options&&(a=Object(r["m"])(this.options,a)),this.chart.clear(),this.chart.setOption(a)}},watch:{data:{deep:!0,immediate:!0,handler:function(){this._renderIndex||(this._renderIndex=0),this._renderIndex++,this._initCanvas()}}},beforeDestroy:function(){window.removeEventListener("resize",this.resize)}},c=s,l=(a("31a2"),a("2877")),u=Object(l["a"])(c,n,i,!1,null,"6cbf7eb0",null);e["a"]=u.exports},"5bba":function(t,e,a){"use strict";a("847d")},"600e":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:t.infoTitle,moreFlag:!1,rank:t.rank,color:"#11FDDA"}},[a("div",{attrs:{slot:"rank"},slot:"rank"}),a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("EchartColumn",{ref:"columnCharts",attrs:{height:320,title:"",bgcolor:"white",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},i=[],r=(a("99af"),a("c740"),a("d81d"),a("a9e3"),a("96cf"),a("1da1")),o=a("5530"),s=a("2abf"),c=a("33c3"),l=a("8943"),u=a("1c31"),d=a("a741"),p={mixins:[d["a"]],props:{infoTitle:{type:String},companyInfo:{type:Object},scoreTypeId:{type:Number}},components:{ChartBox:s["a"],EchartColumn:l["a"]},data:function(){return{loading:!1,title:"",xdata:[],data:[],options:{},rank:1}},created:function(){this._initUIView()},methods:{queryTap:function(){var t={type:"market",unipo:this.companyInfo.unipo,scoreType:this.scoreTypeId,companyId:this.companyInfo.companyId,industryCode:this.companyInfo.industryCode};this._params=t;var e=this.mixinReqOneTime(),a=this.mixinReqBiOneTime(),n=Object(o["a"])(Object(o["a"])(Object(o["a"])({},a),t),{},{companyId:t.unipo?t.companyId:null,industryCode:null,dateType:e.dateType});this._apiGetScoreModelRank(n)},formatResdata:function(t){var e=this._params.companyId;9===this._params.scoreType&&(e=this._params.industryCode);var a=t.findIndex((function(t){return t.companyId===Number(e)})),n=t.map((function(t){return t.companyName})),i=t.map((function(t){var a=t.score;return{value:a,item:t,itemStyle:{color:t.companyId===e?"#F9D22B":"#409EFF"}}})),r=t[a];this.rank=r.marketRank,this.xdata=n;var o=parseInt(180/t.length)>=20?20:parseInt(180/t.length),s=o<3?3:o;this.data=[{name:"",type:"bar",data:i,barWidth:s,color:"#0F9FFD",itemStyle:{normal:{}},markPoint:Object(c["f"])({name:"本行业",value:"".concat(n[a],"：第").concat(this.rank,"名"),index:a,yAxis:r.score})}]},_initUIView:function(){this._initOptions(),this.queryTap()},_initOptions:function(){var t=JSON.parse(JSON.stringify(c["d"]));t[0].left=20,t[0].right=20,this.options={grid:{left:30,right:40},backgroundColor:"transparent",dataZoom:t,yAxis:{name:"分数",nameTextStyle:{color:"rgba(0, 0, 0, .65)"},min:0,max:100,axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)"}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)",formatter:function(t){return t.length>4?"".concat(t.substring(0,4),"..."):t}}}}},_apiGetScoreModelRank:function(t){var e=this;return Object(r["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(u["v"])(t);case 4:n=a.sent,e.formatResdata(n),a.next=10;break;case 8:a.prev=8,a.t0=a["catch"](0);case 10:e.loading=!1;case 11:case"end":return a.stop()}}),a,null,[[0,8]])})))()}}},m=p,f=(a("3d4e"),a("2877")),h=Object(f["a"])(m,n,i,!1,null,"0a56188f",null);e["a"]=h.exports},"623e":function(t,e,a){"use strict";a("99af"),a("d81d"),a("a9e3"),a("b680"),a("96cf");var n=a("1da1"),i=a("5530"),r=a("2909"),o=a("8943"),s=a("f36c"),c=a("f4dd"),l=a("a741");e["a"]={mixins:[l["a"]],components:{EchartColumn:o["a"],BenchmarkingCompany:s["a"]},data:function(){return{queryData:{yearRangeStart:"",yearRangeEnd:"",companyList:[]},colors:["#0F9FFD","#444347","#808080","#D8D7DE"],xdata:[],data:[],options:{},vLoading:!1}},methods:{companyChange:function(t){this.queryData.companyList=t,this.queryTap()},queryTap:function(){var t=this.queryData.companyList.map((function(t){return{companyId:t.id,companyName:t.companyName,code:t.code}})),e=this.mixinReqTime([this.companyInfo].concat(Object(r["a"])(this.queryData.companyList)));this._apiGetCaseRisk(Object(i["a"])({companyList:[this.companyInfo].concat(Object(r["a"])(t))},e))},formatResdata:function(t){var e=this,a=[],n=t.map((function(t,n){return{name:t.companyName,data:t.latitudeSummaries.map((function(t){return 0===n&&a.push(t.subject),Number((100*t.value).toFixed(2))})),color:e.colors[n],barWidth:10,itemStyle:{normal:{}}}}));this.data=n,this.xdata=a},_apiGetCaseRisk:function(t){var e=this;return Object(n["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.vLoading=!0,a.next=4,Object(c["f"])(t);case 4:n=a.sent,e.formatResdata(n),a.next=10;break;case 8:a.prev=8,a.t0=a["catch"](0);case 10:e.vLoading=!1;case 11:case"end":return a.stop()}}),a,null,[[0,8]])})))()}}}},"847d":function(t,e,a){},8844:function(t,e,a){"use strict";a("0a81")},"9a34":function(t,e,a){"use strict";a.r(e);a("96cf");var n=a("1da1"),i=a("d4ec"),r=a("bee2"),o=a("262e"),s=a("2caf"),c=a("9383"),l=a("f4dd"),u=function(t){Object(o["a"])(a,t);var e=Object(s["a"])(a);function a(){return Object(i["a"])(this,a),e.call(this)}return Object(r["a"])(a,[{key:"getValues",value:function(){var t=Object(n["a"])(regeneratorRuntime.mark((function t(){var e;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,this.getOptions({params:{current:1,size:999},api:l["i"],valueKey:"id",labelKey:"name",others:["unit","columnName"]});case 2:return e=t.sent,t.abrupt("return",e);case 4:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}()}]),a}(c["default"]);e["default"]=new u},"9e24":function(t,e,a){"use strict";a("fa1f")},ac8b:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"benchmarking-index",class:[t.size,t.theme,"light"===t.theme?"g-change-el-input-bg-transpart":""]},[a("div",{staticClass:"benchmarking-label",style:t.labelStyle},[t._v("对标指标")]),a("div",{staticClass:"benchmarking-value",on:{mouseenter:t.mouseenter,mouseleave:t.mouseleave}},[a("el-select",{ref:"select",staticStyle:{width:"100%"},attrs:{size:t.size,placeholder:"请选择对标指标",multiple:t.multiple,"multiple-limit":t.limit,"collapse-tags":!0},on:{change:t.change},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}},t._l(t.options,(function(e){return a("el-option",{key:e.value,attrs:{disabled:t.getItemDisabled(e),label:e.label,value:e}},[t._v(" "+t._s(e.label)+" ")])})),1),a("div",{staticClass:"benchmarking-value-text",on:{click:t.selectTextTap}},[t._v(" "+t._s(t.valueText)+" ")]),a("div",{directives:[{name:"show",rawName:"v-show",value:t.showClear&&t.multiple,expression:"showClear && multiple"}],staticClass:"benchmarking-value-clear",on:{click:function(e){return e.stopPropagation(),t.clearTap(e)}}},[a("i",{staticClass:"el-icon-error"})])],1)])},i=[],r=(a("7db0"),a("a15b"),a("d81d"),a("a9e3"),a("96cf"),a("1da1")),o=a("9a34"),s={props:{labelRight:{type:Number,default:35},multiple:{type:Boolean,default:!0},limit:{type:Number,default:5},defaultData:{type:Array,default:null},size:{type:String,default:"default"},theme:{type:String,default:"dark"}},data:function(){return{options:[],value:"",selectUnit:"",showClear:!1}},computed:{labelStyle:function(){return{marginRight:this.labelRight+"px"}},valueText:function(){return Array.isArray(this.value)?this.value.map((function(t){return t.label})).join("/"):this.value.label}},created:function(){this._initUIView()},methods:{mouseenter:function(){this.showClear=!0},mouseleave:function(){this.showClear=!1},clearTap:function(){this.value=[],this.selectUnit="",this.selectTextTap()},change:function(t){Array.isArray(t)||(t=[t]);var e=t.map((function(t){return{id:t.value,name:t.label,unit:t.unit,columnName:t.columnName}}));if(0===t.length)return this.selectUnit="";this.selectUnit=t[0].unit,this.$emit("change",e)},getItemDisabled:function(t){return""!==this.selectUnit&&t.unit!==this.selectUnit&&this.multiple},selectTextTap:function(){var t=this.$refs.select;t&&t.toggleMenu()},setDefaultValue:function(t){if(!this.multiple)return this.setSingleSelectDefault(t);if(this.defaultData){var e=this.defaultData.map((function(e){return t.find((function(t){return t.value===e}))}));return this.value=e,void this.change(e)}for(var a=Math.min(this.limit,3),n=[],i=0;i<a;i++)n.push(t[i]);this.value=n,this.change(n)},setSingleSelectDefault:function(t){var e=t[0];if(this.defaultData){var a=this.defaultData[0],n=t.find((function(t){return t.value===a}));n&&(e=n)}this.change(e),this.value=e},_initUIView:function(){this._initOptions()},_initOptions:function(){var t=this;return Object(r["a"])(regeneratorRuntime.mark((function e(){var a,n,i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,o["default"].init();case 2:a=e.sent,n=a.options,i=a.optionsData,t.options=n,t.optionsData=i,t.setDefaultValue(n),t.$emit("options",i);case 9:case"end":return e.stop()}}),e)})))()}}},c=s,l=(a("9e24"),a("2877")),u=Object(l["a"])(c,n,i,!1,null,"2478d8a8",null);e["a"]=u.exports},c111:function(t,e,a){"use strict";a("d8d6")},c9a5:function(t,e,a){"use strict";a("d81d"),a("a9e3"),a("96cf");var n=a("1da1"),i=a("5530"),r=a("1c31"),o=a("a741");e["a"]={mixins:[o["a"]],props:{soreTypeId:{type:[Number,String],default:""}},methods:{changeSelect:function(t){var e=t.map((function(t){var e=t.caseCompanyScoreList[0];return{id:e.companyId,code:e.code,companyName:e.companyName,industryCode:e.industryCode,selected:!0}})),a=this.$refs.mark;a&&a.change(e),this.companyChange(e)},queryTap:function(){var t=this.mixinReqBiOneTime();this._apiGetScoreTopThree(Object(i["a"])({companyId:this.companyInfo.companyId,soreTypeId:this.soreTypeId},t))},_apiGetScoreTopThree:function(t){var e=this;return Object(n["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(r["w"])(t);case 4:n=a.sent,e.resList=n,e.changeSelect(n),a.next=12;break;case 9:a.prev=9,a.t0=a["catch"](0),e.loading=!1;case 12:case"end":return a.stop()}}),a,null,[[0,9]])})))()}}}},d8d6:function(t,e,a){},e7de:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"star-box g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("Card",{attrs:{score:t.score,level:t.scoreLevel,levelList:t.levelList,sort:t.sort,showMore:t.showMore,industryRank:t.industryRank,rankTxt:t.rankTxt,industryRankMarket:t.industryRankMarket,levelMarket:t.levelMarket,scoreMarket:t.scoreMarket,data:t.data}})],1)},i=[],r=(a("a9e3"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"card-page"},[a("CardItem",{attrs:{typeText:"对标行业",rankTxt:"在本行业"+t.rankTxt,industryRank:t.industryRank,score:t.score,sort:t.sort,level:t.level,levelList:t.levelList},on:{rankTap:t.industryRankTap}}),a("CardItem",{attrs:{typeText:"对标市场",rankTxt:"在整个市场"+t.rankTxt,industryRank:t.industryRankMarket,score:t.scoreMarket,sort:t.sort,level:t.levelMarket,levelList:t.levelList},on:{rankTap:t.marketRankTap}}),t.showMore?a("div",{staticClass:"parent-foot"},[t._m(0),a("moreBtn",{staticClass:"foot-more",attrs:{txt:"一键获取优化策略"},on:{moreTap:t.moreTap}})],1):t._e()],1)}),o=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"warning-box"},[a("i",{staticClass:"el-icon-info icon"}),a("span",{staticClass:"t"},[t._v("提示：风险概率越高评分越高，排名越靠后")])])}],s=(a("99af"),a("ac1f"),a("5319"),a("5530")),c=a("9d27"),l=a("c598"),u=a("8905"),d={components:{moreBtn:c["a"],CardItem:u["a"]},props:{data:{type:Object,default:function(){return{}}},rankTxt:{type:String,default:""},industryRank:{type:[Number,String],default:null},industryRankMarket:{type:[Number,String],default:null},showMore:{type:Boolean,default:!1},score:{type:Number,default:20},scoreMarket:{type:Number,default:20},sort:{type:Boolean,default:!1},level:{type:String,default:"1"},levelMarket:{type:String,default:"1"},levelList:{type:Array,default:function(){return["a1","a2","a3","a4","a5"]}}},data:function(){return{}},computed:{progressStyle:function(){var t=Math.min(this.score,100);return{width:t+"%"}},tagStyle:function(){var t=Math.min(this.score,100);return{left:t+"%"}},trueLevel:function(){return this.sort?7-this.level:this.level}},methods:{moreTap:function(){l["a"].$emit("showReport",{component:"ImproveSuggest"})},marketRankTap:function(){var t=this.getCommonParams("整个市场");l["a"].$emit("portraitRankTap",Object(s["a"])(Object(s["a"])({},t),{},{type:"market"}))},industryRankTap:function(){var t=this.getCommonParams("本行业");l["a"].$emit("portraitRankTap",Object(s["a"])(Object(s["a"])({},t),{},{type:"industry"}))},getCommonParams:function(t){var e=this.data.rankTxt.replace(/第$/,"");return{scoreType:this.data.scoreTypeId,title:"".concat(t).concat(e)}}}},p=d,m=(a("3e58"),a("2877")),f=Object(m["a"])(p,r,o,!1,null,"51906320",null),h=f.exports,y={props:{data:{type:Object,default:function(){return{}}},scoreMarket:{type:Number,default:20},industryRankMarket:{type:[Number,String],default:null},rankTxt:{type:String,default:""},industryRank:{type:[Number,String],default:null},showMore:{type:Boolean,default:!1},score:{type:Number,default:9},sort:{type:Boolean,default:!1},levelList:{type:Array,default:function(){return["a1","a2","a3","a4","a5"]}},loading:{type:Boolean,default:!1}},components:{Card:h},data:function(){return{}},computed:{scoreLevel:function(){return this.getScoreLevel(this.score)},levelMarket:function(){return this.getScoreLevel(this.scoreMarket)}},methods:{getScoreLevel:function(t){for(var e=[20,30,50,70,89,100],a=1,n=0,i=e;n<i.length;n++){var r=i[n];if(t<=r)return"".concat(a);a++}return"5"}}},g=y,v=(a("8844"),Object(m["a"])(g,n,i,!1,null,"4cf94d1e",null));e["a"]=v.exports},fa1f:function(t,e,a){}}]);