(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e3dcc702"],{"0a81":function(t,e,a){},"1c81":function(t,e,a){"use strict";a("8bc8")},3473:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"year-season-info",style:t.boxStyle},[t._v(" 提示：上传当前公司连续"+t._s(t.isYear?"4年以上的年报":"4个季度上的季报")+"才可以生成该评估， "),a("span",{staticClass:"line",style:t.lineStyle,on:{click:t.itemTap}},[t._v("去上传 >>")])])},r=[],i={props:{isYear:{type:Boolean,default:!0},color:{type:String,default:"#F8CB21"},lineColor:{type:String,default:"#F8CB21"}},data:function(){return{}},computed:{boxStyle:function(){return{color:this.color}},lineStyle:function(){return{color:this.lineColor}}},methods:{itemTap:function(){this.$router.push("/home/data-manage/finance-report")}}},o=i,s=(a("6ace"),a("2877")),c=Object(s["a"])(o,n,r,!1,null,"ff7b8ddc",null);e["a"]=c.exports},"3b42":function(t,e,a){},"3d4e":function(t,e,a){"use strict";a("4fbd")},"3e58":function(t,e,a){"use strict";a("3b42")},4365:function(t,e,a){"use strict";a("75e7")},"4fbd":function(t,e,a){},"600e":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:t.infoTitle,moreFlag:!1,rank:t.rank,color:"#11FDDA"}},[a("div",{attrs:{slot:"rank"},slot:"rank"}),a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("EchartColumn",{ref:"columnCharts",attrs:{height:320,title:"",bgcolor:"white",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},r=[],i=(a("99af"),a("c740"),a("d81d"),a("a9e3"),a("96cf"),a("1da1")),o=a("5530"),s=a("2abf"),c=a("33c3"),l=a("8943"),u=a("1c31"),d=a("a741"),p={mixins:[d["a"]],props:{infoTitle:{type:String},companyInfo:{type:Object},scoreTypeId:{type:Number}},components:{ChartBox:s["a"],EchartColumn:l["a"]},data:function(){return{loading:!1,title:"",xdata:[],data:[],options:{},rank:1}},created:function(){this._initUIView()},methods:{queryTap:function(){var t={type:"market",unipo:this.companyInfo.unipo,scoreType:this.scoreTypeId,companyId:this.companyInfo.companyId,industryCode:this.companyInfo.industryCode};this._params=t;var e=this.mixinReqOneTime(),a=this.mixinReqBiOneTime(),n=Object(o["a"])(Object(o["a"])(Object(o["a"])({},a),t),{},{companyId:t.unipo?t.companyId:null,industryCode:null,dateType:e.dateType});this._apiGetScoreModelRank(n)},formatResdata:function(t){var e=this._params.companyId;9===this._params.scoreType&&(e=this._params.industryCode);var a=t.findIndex((function(t){return t.companyId===Number(e)})),n=t.map((function(t){return t.companyName})),r=t.map((function(t){var a=t.score;return{value:a,item:t,itemStyle:{color:t.companyId===e?"#F9D22B":"#409EFF"}}})),i=t[a];this.rank=i.marketRank,this.xdata=n;var o=parseInt(180/t.length)>=20?20:parseInt(180/t.length),s=o<3?3:o;this.data=[{name:"",type:"bar",data:r,barWidth:s,color:"#0F9FFD",itemStyle:{normal:{}},markPoint:Object(c["f"])({name:"本行业",value:"".concat(n[a],"：第").concat(this.rank,"名"),index:a,yAxis:i.score})}]},_initUIView:function(){this._initOptions(),this.queryTap()},_initOptions:function(){var t=JSON.parse(JSON.stringify(c["d"]));t[0].left=20,t[0].right=20,this.options={grid:{left:30,right:40},backgroundColor:"transparent",dataZoom:t,yAxis:{name:"分数",nameTextStyle:{color:"rgba(0, 0, 0, .65)"},min:0,max:100,axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)"}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)",formatter:function(t){return t.length>4?"".concat(t.substring(0,4),"..."):t}}}}},_apiGetScoreModelRank:function(t){var e=this;return Object(i["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(u["v"])(t);case 4:n=a.sent,e.formatResdata(n),a.next=10;break;case 8:a.prev=8,a.t0=a["catch"](0);case 10:e.loading=!1;case 11:case"end":return a.stop()}}),a,null,[[0,8]])})))()}}},f=p,m=(a("3d4e"),a("2877")),h=Object(m["a"])(f,n,r,!1,null,"0a56188f",null);e["a"]=h.exports},"6ace":function(t,e,a){"use strict";a("d6ed")},"6e87":function(t,e,a){"use strict";a.d(e,"a",(function(){return n}));var n=[{title:"市场价值",industryTitle:"行业市场价值平均值",value:"one",infoTitle:"市场价值估值方式，通常是基于近期投资者对贵公司股票或价值的估值。",infoText:"与投资基金不同，股票投资者很多时候会被媒体报道和分析师的意见影响，会因兴奋或激动而投资，导致他们对企业估值比较主观。这种现象还被诺贝尔经济学奖获得者称之为“动物精神”。",textIntro:"该估值模型是基于近期市场投资者对该公司股票市值或价值的估值。有时该估值方法会受市场情绪因素影响，这种现象被诺贝尔经济学奖获得者称之为“动物精神”。"},{title:"风险/投资基金价值",industryTitle:"行业风险/基金/投资价值",value:"four",infoTitle:"风险投资基金价值模型，是基于风险投资和私募股权基金对贵公司价值的估值。",infoText:"这些基金通常要求在5~7年内，能获得至少25%至45%的投资回报。通常，他们会将价值压低而导致人为的公司价值缩减，除非公司有非常强劲的增长。",textIntro:"该估值模型是基于风险投资和私募股权基金对贵公司价值的估值。通常在5~7年内，投资要求获得至少25%至45%的投资回报。 "},{title:"银行信贷安全价值",industryTitle:"行业银行信贷安全价值",value:"five",infoTitle:"这种估值模型是基于考虑了公司被拆分、并按资产出售的情况下计算的贵公司估值",infoText:"比如，银行贷款金额（是一种估值）通常会不超过每种资产类别的一个百分比，减去公司的现有债务。",textIntro:"该估值模型是基于考虑了公司被拆分、并按资产出售的情况下计算该公司估值。比如，银行贷款金额就是一种估值。"},{title:"财务报表价值",industryTitle:"行业财务报表价值",value:"two",infoTitle:"该种估值模型是基于标准会计准则下，提供一种基于资产和负债的价值估值。",infoText:"现实中，资产可能会被高估或低估，估值结果可能会与公司创造利润和业绩增长的能力几乎无关。",textIntro:"该估值模型是基于标准会计准则下，提供一种基于资产和负债的价值估值，有时，估值结果可能与公司利润和业绩增长能力几乎无关。"},{title:"重置/替换价值",industryTitle:"行业重置/替换价值",value:"eight",infoTitle:"重置价值的估值模型，是基于假设一个竞争对手以你公司今天的成本复制你的业务，而计算的一种估价",infoText:"这种成本不仅包含了房屋、设备，而且还包含了所有的无形资产如专利、技术诀窍、有才华的员工、客户和供应商关系、地理位置等等。现实中，这种估值方法可以基于复制公司所需的实际资产，也可以基于负责公司在一段时间内的资本成本。",textIntro:"该估值模型是基于假设一个竞争对手按照贵公司今天成本、或实际资产、或一段时间的资本成本，复制公司业务而计算的一种估价。"},{title:"永续价值",industryTitle:"行业永续价值",value:"six",infoTitle:"永续价值估值模型，是基于现金流量的统计趋势，而转换算成的价值",infoText:"永续增长模型假设企业未来长期稳定、可持续的增长。在永续增长的情况下，企业价值是下期现金流量的函数，其一般表达式如下：股权价值=下期股权现金流量/（股权资本成本－永续增长率）。永续增长模型的特例是永续增长率等于零，即零增长模型。",textIntro:"该估值模型是基于现金流量的统计趋势，而转换计算的价值。该模型假设了企业未来长期稳定、增长可持续。"},{title:"不断增长的永续价值",industryTitle:"行业不断增长的永续价值",value:"seven",infoTitle:"这种估值方式，是假设现金流是永恒的，而且会增加",infoText:"在古典金融中，它是现金流除以折现率和增长率之差，其趋势被转换成价值。",textIntro:"该估值模型假设现金流是永恒的，而且会增加的条件下，用现金流除以折现率和增长率之差，再将其趋势转换成价值。"},{title:"现金流量价值",industryTitle:"行业现金流量价值",value:"three",infoTitle:"现金流量价值（也是财务价值）是一个估值的数学和统计模型，通常被用于将过去的收入现金流转化为价值。",infoText:"该估值模型与投资者购买股票或银行贷款的价值模型截然不同，它意味着企业可以通过增加收入、降低风险、提升成长来大大提高财务价值。",textIntro:"该估值模型是一个数学和统计模型，通常被用于将过去的收入现金流转化为价值。它意味着企业可通过增加收入、降低风险、提升成长来提高财务价值。"}]},"75e7":function(t,e,a){},"7e54":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"portrait-item"},[a("div",{staticClass:"portrait-item-title"},[a("span",[t._v("阿尔法财AI价值评估")]),t.yanshiInfo.seasonDataFlag?t._e():a("YearSeasonInfo",{attrs:{color:"",isYear:!1}})],1),a("div",{staticClass:"p-content"},[a("div",{staticClass:"sub-title"},[t._v("AI分析结论")]),a("div",{staticClass:"p-content-line"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12,offset:0}},[a("StartBox",{attrs:{industryRank:t.scoreData.industryRank,score:t.scoreData.scoreTotal,companyInfo:t.companyInfo,levelList:t.scoreData.riskList,scoreMarket:t.scoreData.marketScore,industryRankMarket:t.scoreData.marketRank,rankTxt:t.scoreData.rankTxt,data:t.scoreData}})],1),a("el-col",{attrs:{span:12,offset:0}},[a("div",{staticClass:"radar"},[a("Radar",{attrs:{companyInfo:t.companyInfo}})],1)])],1)],1),a("div",{staticClass:"p-content-line"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12,offset:0}},[a("TopThree",{attrs:{soreTypeId:t.scoreData.scoreTypeId,companyInfo:t.companyInfo}})],1),a("el-col",{attrs:{span:12,offset:0}},[a("IndustryRank",{attrs:{companyInfo:t.companyInfo}})],1)],1)],1),a("div",{staticClass:"p-content-line"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:12,offset:0}},[a("AllIndustryRank",{attrs:{companyInfo:t.companyInfo,scoreTypeId:t.scoreData.scoreTypeId,infoTitle:t.scoreData.industryTxt}})],1),a("el-col",{attrs:{span:12,offset:0}},[a("AllMarketRank",{attrs:{companyInfo:t.companyInfo,scoreTypeId:t.scoreData.scoreTypeId,infoTitle:t.scoreData.marketTxt}})],1)],1)],1)])])},r=[],i=a("5530"),o=a("e7de"),s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:"八个维度价值估值"},on:{moreTap:t.mixinMoreTap}},[a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#606266"}},[a("EchartRadar2",{attrs:{layer:"",layerData:t.layerData,options:t.options,xdata:t.xdata,data:t.data,height:265}}),a("LegendBox",{attrs:{name1:"八个维度价值",name2:"行业平均",name3:"市场平均"}})],1)])],1)},c=[],l=(a("99af"),a("4160"),a("a15b"),a("d81d"),a("b0c0"),a("a9e3"),a("b680"),a("159b"),a("96cf"),a("1da1")),u=a("2909"),d=a("b85c"),p=a("7f89"),f=a("2abf"),m=a("3501"),h=a("f870"),y=a("1c31"),v=a("6e87"),g=a("f348"),x=a("33c3"),b={mixins:[m["a"]],components:{ChartBox:f["a"],EchartRadar2:h["a"],LegendBox:p["a"]},data:function(){return{xdata:[],layerData:{},data:[],loading:!1,options:{},companyList:[],reportData:{title:"价值评估",component:"ValueAssess"}}},created:function(){this._initUIView()},methods:{queryTap:function(){this.companyInfo.companyId&&(this.companyList=[this.companyInfo],this._apiGetCaseValueEight({companyList:this.companyList}))},filterMaxData:function(t,e){var a=e>0?e:0,n=a/t*100*.9;return n},formatResdata:function(t){var e=this,a=[],n=[],r=[],i=[],o=[],s=[];this.xdata.forEach((function(c){var l=[],u=t[c.key],d=Number(t.industryAvg[c.key].toFixed(2))||null,p=Number(t.marketAvg[c.key].toFixed(2))||null,f=Math.max(u,d,p);l.push(f),a.push(u),n.push(d),r.push(p);var m=e.filterMaxData(f,u),h=e.filterMaxData(f,d),y=e.filterMaxData(f,p);i.push(m),o.push(h),s.push(y)})),this._initXdata(100,0),this.data=[{type:"radar",data:[{value:i,v:a,avg:n,avg2:r}],name:this.companyInfo.companyName,lineStyle:{color:"#fee05e"},areaStyle:{color:"white",opacity:.2},itemStyle:{color:"rgba(0, 0, 0, 0.5)"},symbolSize:2},{type:"radar",data:[{value:o,v:a,avg:n,avg2:r}],name:"行业平均",z:10,lineStyle:{color:"#00ff00"},areaStyle:{color:"white",opacity:.2},itemStyle:{color:"rgba(0, 0, 0, 0.5)"},symbolSize:2},{type:"radar",data:[{value:s,v:a,avg:n,avg2:r}],name:"市场平均",z:10,lineStyle:{color:"#FF00BA"},areaStyle:{color:"white",opacity:.2},itemStyle:{color:"rgba(0, 0, 0, 0.5)"},symbolSize:2}]},_initUIView:function(){this._initText(),this._initXdata(),this._initOptions()},_initText:function(){var t,e={},a=Object(d["a"])(v["a"]);try{for(a.s();!(t=a.n()).done;){var n=t.value;e[n.title]="".concat(n.textIntro)}}catch(r){a.e(r)}finally{a.f()}this.layerData=e},_initOptions:function(){var t=this;this.options={tooltip:{formatter:function(e){var a="<strong>".concat(t.companyInfo.companyName,"八个维度价值估值 / 行业平均 / 市场平均</strong>"),n=e.data,r=n.v,i=n.avg,o=n.avg2,s=t.xdata.map((function(t,e){var a=[{text:"".concat(t.name),value:"".concat(Object(g["f"])(r[e]))},{text:"行业",value:"".concat(Object(g["f"])(i[e])),flex:"0 0 120px"},{text:"市场",value:"".concat(Object(g["f"])(o[e])),flex:"0 0 120px"}],n=Object(x["p"])(a);return n}));return[a].concat(Object(u["a"])(s)).join("")},extraCssText:"width: 500px;"}}},_initXdata:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:0;this.xdata=v["a"].map((function(a){return{name:a.title,key:a.value,max:t,min:e}}))},_apiGetCaseValueEight:function(t){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(y["j"])(t);case 4:n=a.sent,e.formatResdata(n[0]),a.next=10;break;case 8:a.prev=8,a.t0=a["catch"](0);case 10:e.loading=!1;case 11:case"end":return a.stop()}}),a,null,[[0,8]])})))()}}},k=b,T=a("2877"),I=Object(T["a"])(k,s,c,!1,null,"117236ec",null),C=I.exports,_=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:"公司价值与行业TOP3对比"},on:{moreTap:t.mixinMoreTap}},[a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("div",{staticClass:"select"},[a("BenchmarkingCompany",{ref:"mark",attrs:{hideFirst:"",size:"mini",theme:"light"},on:{change:t.companyChange}})],1),a("EchartColumn",{ref:"columnCharts",attrs:{height:265,title:"",bgcolor:"#0c368a",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},S=[],w=(a("7db0"),a("f36c")),O=a("c9a5"),R=a("8943"),L=8,j=11,N={mixins:[m["a"],O["a"]],components:{ChartBox:f["a"],EchartColumn:R["a"],BenchmarkingCompany:w["a"]},data:function(){var t=this.$colors.map((function(t){return"".concat(t,"F2")}));return{activeId:"one",radioList:[],companyList:[],active:{},options:{},xdata:[],data:[],loading:!1,colors:t,reportData:{title:"价值评估",component:"ValueAssess"}}},created:function(){this._initUIView()},methods:{companyChange:function(t){var e=t.map((function(t){return{companyId:t.id,code:t.code,companyName:t.companyName,industryCode:t.industryCode}}));this.companyList=[this.companyInfo].concat(Object(u["a"])(e)),this.queryValuesTap()},queryValuesTap:function(){this.companyInfo.companyId&&0!==this.companyList.length&&this._apiGetCaseValueEight({companyList:this.companyList})},formatResdata:function(t){var e=this,a=this.radioList.map((function(t){return t.title})),n=this.companyList.map((function(a,n){var r=t.find((function(t){return t.code===a.code})),i=e.radioList.map((function(t){return{value:r[t.value]}}));return{name:a.companyName,barWidth:L,data:i,itemStyle:{color:e.colors[n]}}}));this.xdata=a,this.data=n},_initUIView:function(){this._initOptions(),this._initRadioList(),this._initActive()},_initActive:function(){var t=this;this.active=this.radioList.find((function(e){return e.value===t.activeId}))},_initOptions:function(){this.options={backgroundColor:"transparent",yAxis:{name:"",axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)",formatter:function(t){return Object(g["f"])(t)}}},legend:{textStyle:{color:"rgba(0, 0, 0, .65)"}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)",interval:0,fontSize:j,formatter:function(t){return t.length>4?"".concat(t.substring(0,4),"..."):t}}},grid:{right:0},tooltip:{formatter:function(t){var e=t[0].name,a=t.map((function(t){return"".concat(t.seriesName," ").concat(Object(g["f"])(t.value))}));return[e].concat(Object(u["a"])(a)).join("<br/>")}}}},_initRadioList:function(){this.radioList=JSON.parse(JSON.stringify(v["a"]))},_apiGetCaseValueEight:function(t){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(y["j"])(t);case 4:n=a.sent,e.formatResdata(n),a.next=10;break;case 8:a.prev=8,a.t0=a["catch"](0);case 10:e.loading=!1;case 11:case"end":return a.stop()}}),a,null,[[0,8]])})))()}}},M=N,D=(a("4365"),Object(T["a"])(M,_,S,!1,null,"75860769",null)),B=D.exports,A=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:"行业间八个维度价值估值排名"},on:{moreTap:t.mixinMoreTap}},[a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative c-box g-change-el-input-bg-transpart",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("div",{staticClass:"select-box"},[a("div",{staticClass:"label"},[t._v("选择指标")]),a("div",{staticClass:"value"},[a("el-select",{staticStyle:{width:"100%"},attrs:{"value-key":"key",size:"small"},on:{change:t.change},model:{value:t.active,callback:function(e){t.active=e},expression:"active"}},t._l(t.radioList,(function(t){return a("el-option",{key:t.key,attrs:{label:t.name,value:t.key}})})),1)],1)]),a("EchartColumn",{ref:"columnCharts",attrs:{height:265,title:"",bgcolor:"#0c368a",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},E=[],F=(a("c740"),12),V={mixins:[m["a"]],components:{ChartBox:f["a"],EchartColumn:R["a"]},props:{companyInfo:{type:Object,default:function(){}}},data:function(){return{radioList:[],active:"one",activeName:"市场价值",dataList:[],xdata:[],data:[],options:{},loading:!1,reportData:{title:"价值评估",component:"ValueAssess"}}},created:function(){this._initUIView()},methods:{change:function(t){if(0!==this.dataList.length){var e=this.radioList.find((function(e){return e.key===t}));this.activeName=e.name,this.formatResData()}},queryTap:function(){this._apiGetCaseCompetitivIndustry()},formatResData:function(){var t=this,e=JSON.parse(JSON.stringify(this.dataList));e.sort((function(e,a){return a[t.active]-e[t.active]}));var a=e.map((function(t){return t.industryName})),n=e.map((function(e){var a=e[t.active];return{value:a,itemStyle:{color:e.industryCode===t.companyInfo.industryCode?"#F9D22B":"#409EFF"}}})),r=e.find((function(e){return e.industryCode===t.companyInfo.industryCode})),i=e.findIndex((function(e){return e.industryCode===t.companyInfo.industryCode}));this.xdata=a,this.data=[{name:"",type:"bar",barWidth:2,data:n,color:"#0F9FFD",markPoint:Object(x["f"])({name:"本行业",value:r.industryName,index:i,yAxis:r[this.active]})}]},_initUIView:function(){this._initXdata(),this._initOptions()},_initOptions:function(){var t=this;this.options={backgroundColor:"transparent",dataZoom:x["d"],yAxis:{name:"",axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, .25)"}},axisLabel:{color:"rgba(0, 0, 0, 0.5)",formatter:function(t){return Object(g["f"])(t)}}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, .25)"}},axisLabel:{color:"rgba(0, 0, 0, 0.5)",fontSize:F,formatter:function(t){return t.length>4?"".concat(t.substring(0,4),"..."):t}}},grid:{right:0},tooltip:{formatter:function(e){var a=e[0],n="行业：".concat(a.name),r="".concat(t.activeName,"：").concat(Object(g["f"])(a.value));return[n,r].join("<br/>")}}}},_initXdata:function(){this.radioList=v["a"].map((function(t){return{name:t.title,key:t.value}}))},_apiGetCaseCompetitivIndustry:function(){var t=this;return Object(l["a"])(regeneratorRuntime.mark((function e(){var a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,t.loading=!0,e.next=4,Object(y["d"])();case 4:a=e.sent,t.dataList=a,t.formatResData(),e.next=11;break;case 9:e.prev=9,e.t0=e["catch"](0);case 11:t.loading=!1;case 12:case"end":return e.stop()}}),e,null,[[0,9]])})))()}}},$=V,q=(a("1c81"),Object(T["a"])($,A,E,!1,null,"767d184c",null)),G=q.exports,z=a("3473"),J=a("3ca5"),U=a("20b9"),P=a("600e"),X=a("2f62"),Y={mixins:[J["a"]],components:{StartBox:o["a"],Radar:C,TopThree:B,IndustryRank:G,YearSeasonInfo:z["a"],AllIndustryRank:U["a"],AllMarketRank:P["a"]},data:function(){return{}},computed:Object(i["a"])({},Object(X["c"])(["yanshiInfo"]))},W=Y,Z=(a("cd97"),Object(T["a"])(W,n,r,!1,null,"20e93e57",null));e["default"]=Z.exports},8844:function(t,e,a){"use strict";a("0a81")},"8bc8":function(t,e,a){},c9a5:function(t,e,a){"use strict";a("d81d"),a("a9e3"),a("96cf");var n=a("1da1"),r=a("5530"),i=a("1c31"),o=a("a741");e["a"]={mixins:[o["a"]],props:{soreTypeId:{type:[Number,String],default:""}},methods:{changeSelect:function(t){var e=t.map((function(t){var e=t.caseCompanyScoreList[0];return{id:e.companyId,code:e.code,companyName:e.companyName,industryCode:e.industryCode,selected:!0}})),a=this.$refs.mark;a&&a.change(e),this.companyChange(e)},queryTap:function(){var t=this.mixinReqBiOneTime();this._apiGetScoreTopThree(Object(r["a"])({companyId:this.companyInfo.companyId,soreTypeId:this.soreTypeId},t))},_apiGetScoreTopThree:function(t){var e=this;return Object(n["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(i["w"])(t);case 4:n=a.sent,e.resList=n,e.changeSelect(n),a.next=12;break;case 9:a.prev=9,a.t0=a["catch"](0),e.loading=!1;case 12:case"end":return a.stop()}}),a,null,[[0,9]])})))()}}}},cd97:function(t,e,a){"use strict";a("ec93")},d6ed:function(t,e,a){},e7de:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"star-box g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("Card",{attrs:{score:t.score,level:t.scoreLevel,levelList:t.levelList,sort:t.sort,showMore:t.showMore,industryRank:t.industryRank,rankTxt:t.rankTxt,industryRankMarket:t.industryRankMarket,levelMarket:t.levelMarket,scoreMarket:t.scoreMarket,data:t.data}})],1)},r=[],i=(a("a9e3"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"card-page"},[a("CardItem",{attrs:{typeText:"对标行业",rankTxt:"在本行业"+t.rankTxt,industryRank:t.industryRank,score:t.score,sort:t.sort,level:t.level,levelList:t.levelList},on:{rankTap:t.industryRankTap}}),a("CardItem",{attrs:{typeText:"对标市场",rankTxt:"在整个市场"+t.rankTxt,industryRank:t.industryRankMarket,score:t.scoreMarket,sort:t.sort,level:t.levelMarket,levelList:t.levelList},on:{rankTap:t.marketRankTap}}),t.showMore?a("div",{staticClass:"parent-foot"},[t._m(0),a("moreBtn",{staticClass:"foot-more",attrs:{txt:"一键获取优化策略"},on:{moreTap:t.moreTap}})],1):t._e()],1)}),o=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"warning-box"},[a("i",{staticClass:"el-icon-info icon"}),a("span",{staticClass:"t"},[t._v("提示：风险概率越高评分越高，排名越靠后")])])}],s=(a("99af"),a("ac1f"),a("5319"),a("5530")),c=a("9d27"),l=a("c598"),u=a("8905"),d={components:{moreBtn:c["a"],CardItem:u["a"]},props:{data:{type:Object,default:function(){return{}}},rankTxt:{type:String,default:""},industryRank:{type:[Number,String],default:null},industryRankMarket:{type:[Number,String],default:null},showMore:{type:Boolean,default:!1},score:{type:Number,default:20},scoreMarket:{type:Number,default:20},sort:{type:Boolean,default:!1},level:{type:String,default:"1"},levelMarket:{type:String,default:"1"},levelList:{type:Array,default:function(){return["a1","a2","a3","a4","a5"]}}},data:function(){return{}},computed:{progressStyle:function(){var t=Math.min(this.score,100);return{width:t+"%"}},tagStyle:function(){var t=Math.min(this.score,100);return{left:t+"%"}},trueLevel:function(){return this.sort?7-this.level:this.level}},methods:{moreTap:function(){l["a"].$emit("showReport",{component:"ImproveSuggest"})},marketRankTap:function(){var t=this.getCommonParams("整个市场");l["a"].$emit("portraitRankTap",Object(s["a"])(Object(s["a"])({},t),{},{type:"market"}))},industryRankTap:function(){var t=this.getCommonParams("本行业");l["a"].$emit("portraitRankTap",Object(s["a"])(Object(s["a"])({},t),{},{type:"industry"}))},getCommonParams:function(t){var e=this.data.rankTxt.replace(/第$/,"");return{scoreType:this.data.scoreTypeId,title:"".concat(t).concat(e)}}}},p=d,f=(a("3e58"),a("2877")),m=Object(f["a"])(p,i,o,!1,null,"51906320",null),h=m.exports,y={props:{data:{type:Object,default:function(){return{}}},scoreMarket:{type:Number,default:20},industryRankMarket:{type:[Number,String],default:null},rankTxt:{type:String,default:""},industryRank:{type:[Number,String],default:null},showMore:{type:Boolean,default:!1},score:{type:Number,default:9},sort:{type:Boolean,default:!1},levelList:{type:Array,default:function(){return["a1","a2","a3","a4","a5"]}},loading:{type:Boolean,default:!1}},components:{Card:h},data:function(){return{}},computed:{scoreLevel:function(){return this.getScoreLevel(this.score)},levelMarket:function(){return this.getScoreLevel(this.scoreMarket)}},methods:{getScoreLevel:function(t){for(var e=[20,30,50,70,89,100],a=1,n=0,r=e;n<r.length;n++){var i=r[n];if(t<=i)return"".concat(a);a++}return"5"}}},v=y,g=(a("8844"),Object(f["a"])(v,n,r,!1,null,"4cf94d1e",null));e["a"]=g.exports},ec93:function(t,e,a){}}]);