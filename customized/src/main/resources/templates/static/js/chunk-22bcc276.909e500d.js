(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-22bcc276"],{"1c31":function(t,e,n){"use strict";n.d(e,"p",(function(){return r})),n.d(e,"y",(function(){return o})),n.d(e,"q",(function(){return c})),n.d(e,"r",(function(){return s})),n.d(e,"x",(function(){return u})),n.d(e,"n",(function(){return l})),n.d(e,"e",(function(){return d})),n.d(e,"l",(function(){return f})),n.d(e,"a",(function(){return p})),n.d(e,"d",(function(){return h})),n.d(e,"g",(function(){return y})),n.d(e,"h",(function(){return m})),n.d(e,"j",(function(){return v})),n.d(e,"k",(function(){return g})),n.d(e,"i",(function(){return b})),n.d(e,"s",(function(){return x})),n.d(e,"z",(function(){return C})),n.d(e,"t",(function(){return _})),n.d(e,"w",(function(){return k})),n.d(e,"u",(function(){return B})),n.d(e,"f",(function(){return w})),n.d(e,"c",(function(){return T})),n.d(e,"b",(function(){return I})),n.d(e,"m",(function(){return L})),n.d(e,"o",(function(){return S})),n.d(e,"v",(function(){return O}));n("99af");var a=n("bc3a"),i=n.n(a);function r(t){var e="/case/risk/construction";return i.a.post(e,t)}function o(t){var e="/case/risk/single/construction";return i.a.post(e,t)}function c(t){var e="/case/risk/industry/dis";return i.a.post(e,t)}function s(t){var e="/case/risk/industry/quarter";return i.a.post(e,t)}function u(t){var e="/case/risk/industry/range/"+t;return i.a.post(e)}function l(t){var e="/case/bi/factor/history";return i.a.post(e,t)}function d(t){var e="/case/finance/bench";return i.a.post(e,t)}function f(t){var e="/dataModel/getDuPoint";return i.a.post(e,t)}function p(t){var e="/case/competitive/radar";return i.a.post(e,t)}function h(){var t="/case/competitive/industry";return i.a.get(t)}function y(t){var e="/case/riskvalue";return i.a.post(e,t)}function m(t){var e="/case/riskvalue/input";return i.a.post(e,t)}function v(t){var e="/case/value/eight";return i.a.post(e,t)}function g(t){var e="/case/value/industry/".concat(t.industryCode);return i.a.post(e,t)}function b(t){var e="/case/value/dis/".concat(t.industryCode);return i.a.post(e,t)}function x(t){var e=t.type,n=t.date,a=t.companyId,r="/scoreModel/portrait/".concat(e,"/").concat(n,"/").concat(a);return i.a.get(r)}function C(){var t="/scoreModel/index/subscore";return i.a.get(t)}function _(t){var e="/scoreModel/industry/order";return i.a.post(e,t)}function k(t){var e="/scoreModel/company/scoreTop";return i.a.post(e,t)}function B(t){var e="/scoreModel/company/scoreList";return i.a.post(e,t)}function w(t){var e="/case/risk/rank";return i.a.post(e,t)}function T(t){var e="/dataModel/getBiReportByQuarter";return i.a.post(e,t)}function I(t){var e="/dataModel/getBiIndexDataByQuarter";return i.a.post(e,t)}function L(t){var e="/case/bi/factor/history";return i.a.post(e,t)}function S(t){var e="/case/industry/location";return i.a.post(e,t)}function O(t){var e="/scoreModel/rank";return i.a.post(e,t)}},"1cdf":function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"g-relative"},[n("HeaderBox",{attrs:{companyInfo:t.companyInfo,infoTitle:"贝塔系数分析Beta",subTitle:"Beta贝塔系数是针对市场的企业风险系数。通常被专家用来度量一种证券投资或一个投资组合相对总体市场的波动性或系统风险水平，以预测并选择不同Beta系数，获取额外收益。",imgUrl:"/images/financial/i-2-5A.png"}},[t._t("title",null,{slot:"title"})],2),n("div",{staticClass:"g-bg-gray gray-wrap"},[n("RiskValueBoxElement",{attrs:{type:"beta",companyInfo:t.companyInfo}})],1)],1)},i=[],r=n("a1d2"),o=n("fcc7"),c={props:{companyInfo:{type:Object,default:function(){return{}}}},components:{HeaderBox:r["a"],RiskValueBoxElement:o["a"]},data:function(){return{}},created:function(){this._initUIView()},methods:{_initUIView:function(){}}},s=c,u=(n("458c"),n("2877")),l=Object(u["a"])(s,a,i,!1,null,"fae6d170",null);e["default"]=l.exports},3473:function(t,e,n){"use strict";var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"year-season-info",style:t.boxStyle},[t._v(" 提示：上传当前公司连续"+t._s(t.isYear?"4年以上的年报":"4个季度上的季报")+"才可以生成该评估， "),n("span",{staticClass:"line",style:t.lineStyle,on:{click:t.itemTap}},[t._v("去上传 >>")])])},i=[],r={props:{isYear:{type:Boolean,default:!0},color:{type:String,default:"#F8CB21"},lineColor:{type:String,default:"#F8CB21"}},data:function(){return{}},computed:{boxStyle:function(){return{color:this.color}},lineStyle:function(){return{color:this.lineColor}}},methods:{itemTap:function(){this.$router.push("/home/data-manage/finance-report")}}},o=r,c=(n("6ace"),n("2877")),s=Object(c["a"])(o,a,i,!1,null,"ff7b8ddc",null);e["a"]=s.exports},"35a7":function(t,e,n){},"458c":function(t,e,n){"use strict";n("4744")},4744:function(t,e,n){},"6ace":function(t,e,n){"use strict";n("d6ed")},8943:function(t,e,n){"use strict";var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("g-echart-box",{attrs:{bgcolor:t.bgcolor,empty:0===t.xdata.length,txtColor:t.txtColor,imgWidth:t.imgWidth}},[n("div",{ref:"chart",staticClass:"echart-box",style:t.boxStyle})])},i=[],r=(n("4160"),n("a9e3"),n("159b"),n("33c3")),o=n("164e"),c={props:{title:{type:String,default:""},imgWidth:{type:Number,default:200},txtColor:{type:String,default:"#606266"},data:{type:Array,default:function(){return[]}},bgcolor:{type:String,default:"white"},xdata:{type:Array,default:function(){return[]}},height:{type:[String,Number],default:"320"},labelColor:{type:String,default:"#FF413D"},showLabel:{type:Boolean,default:!1},options:{type:Object,default:function(){return null}}},data:function(){return{}},computed:{boxStyle:function(){return{height:this.height+"px",margin:"0 auto"}}},mounted:function(){var t=this;window.addEventListener("resize",(function(){t.chart.resize()}),!1)},methods:{_initCanvas:function(){var t=this;this.$nextTick((function(){t._initChart()}))},resize:function(){this.chart&&this.chart.resize()},_initChart:function(){if(!this.chart){var t=this.$refs.chart;if(!t)return;this.chart=o["init"](t)}this.data.length>0&&this.data.forEach((function(t){t.type=t.type||"bar",t.barWidth=t.barWidth||16}));var e=Object(r["b"])(this.data,this.xdata,this.title);Object(r["c"])(e,this.options),this.options&&(e=Object(r["m"])(this.options,e)),this.chart.clear(),this.chart.setOption(e)}},watch:{data:{deep:!0,immediate:!0,handler:function(){this._initCanvas()}}}},s=c,u=n("2877"),l=Object(u["a"])(s,a,i,!1,null,"35e3f8c6",null);e["a"]=l.exports},c740:function(t,e,n){"use strict";var a=n("23e7"),i=n("b727").findIndex,r=n("44d2"),o=n("ae40"),c="findIndex",s=!0,u=o(c);c in[]&&Array(1)[c]((function(){s=!1})),a({target:"Array",proto:!0,forced:s||!u},{findIndex:function(t){return i(this,t,arguments.length>1?arguments[1]:void 0)}}),r(c)},d6ed:function(t,e,n){},ed2c:function(t,e,n){"use strict";n("35a7")},fcc7:function(t,e,n){"use strict";var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",[n("div",{staticClass:"risk-value-box"},[t.showHeader?n("div",{staticClass:"header-title"},[n("span",[t._v(t._s(t.textData.bigTitle))])]):t._e(),n("div",{staticClass:"main"},[n("div",{staticClass:"opers"},[n("CustomSave",{attrs:{like:t.mixinFinanceLike},on:{saveTap:t.mixinAddFinanceMaterial}})],1),n("div",{staticClass:"risk-value-left"},[n("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative chart"},[n("ChartColumn",{attrs:{xdata:t.xdata,data:t.data,options:t.options}})],1),t.mindInfo.yearDataFlag?t._e():n("YearSeasonInfo",{staticClass:"year-info-box",attrs:{color:"",lineColor:"#1989FA"}})],1),n("div",{staticClass:"risk-value-right"},[n("div",{staticClass:"test-pop",on:{click:function(t){t.stopPropagation()}}},[n("BenchmarkingCompany",{on:{change:t.companyChange}})],1),n("div",{staticClass:"r-title",class:[t.textData.smallTitle?"small-title":""]},[t._v(" "+t._s(t.textData.title)+" ")]),t._l(t.textData.textList,(function(e,a){return n("div",{key:a,staticClass:"r-text"},[t._v(" "+t._s(e)+" ")])}))],2)])])])},i=[],r=(n("99af"),n("7db0"),n("4160"),n("a15b"),n("d81d"),n("b0c0"),n("a9e3"),n("b680"),n("159b"),n("96cf"),n("1da1")),o=n("2909"),c=n("5530"),s=n("f36c"),u=n("8943"),l=n("3473"),d=n("1c31"),f=n("2f62"),p=n("a8c8"),h=n("6612"),y={wacc:100,capm:100,erp:100},m={name:"risk-value",mixins:[h["a"]],props:{showHeader:{type:Boolean,default:!1},companyInfo:{type:Object,default:function(){return{}}},type:{type:String,default:"wacc"}},components:{ChartColumn:u["a"],BenchmarkingCompany:s["a"],YearSeasonInfo:l["a"],CustomSave:p["a"]},data:function(){var t=this.$colors;return{loading:!1,xdata:[],data:[],options:{},companyList:[],colorArray:t,textData:{title:"",yTitle:"",textList:[]}}},created:function(){this._initUIView()},computed:Object(c["a"])({},Object(f["c"])(["mindInfo"])),methods:{companyChange:function(t){var e=t.map((function(t){return{companyId:t.id,code:t.code,companyName:t.companyName,industryCode:t.industryCode,industryName:t.industryName}}));this.companyList=[this.companyInfo].concat(Object(o["a"])(e)),this.queryTap()},queryTap:function(){this.mindInfo.yearDataFlag&&this._apiGetCaseRiskvalue({companyList:this.companyList.map((function(t){return{companyId:t.companyId,code:t.code,companyName:t.companyName}}))})},formatResdata:function(t){var e=this,n=y[this.type],a=[],i=[];this.companyList.forEach((function(r,o){var c=t.find((function(t){return t.code===r.code})),s=Number(n?(+c[e.type]*n).toFixed(2):(+c[e.type]).toFixed(4));a.push(r.companyName),i.push({value:s,itemStyle:{color:e.colorArray[o]}})})),this.xdata=a,this.data=[{name:"",data:i,barWidth:36}]},_initUIView:function(){this._initTextList(),this._initOptions()},_initTextList:function(){var t={wacc:{bigTitle:"加权资本成本分析WACC",title:"WACC应用示例：",yTitle:"加权资本成本率",textList:["若公司的收益率为20％，WACC为11％。说明公司每投资1元，就会获得9％的回报，即公司每花费1元，就能创造9分的价值。","若公司的收益小于WACC，则公司正在失去价值。","若公司的回报率为11％，WACC为17％，则该公司每花费1元，就会损失6分，这表明潜在投资者最好将其钱花在其他地方。"]},capm:{bigTitle:"资本资产定价分析CAPM",title:"专家们通常用CAPM模型，将风险和货币时间价值与预期收益进行比较，评估股票估值是否合理。当然，该指标需要结合其他指标一起综合分析判断。",smallTitle:!0,yTitle:"预期报酬率",textList:["比如：","净资产收益率（ROE）-必要回报率=留存收益率","留存收益率 > 0，则公司值得投资，否者撤资","必要回报率 = 无风险利率 +（市场回报率 - 无风险利率）* 贝塔系数"]},beta:{bigTitle:"贝塔系数分析Beta",title:"",yTitle:"贝塔系数",textList:["1. 当Beta值=1，表明公司股价与大盘波动性一致，股票具有系统风险。","2. 当Beta值<1，表明公司股价波动性小于大盘波动性。","3. 当Beta值>1，表明公司股价波动性大于大盘波动性。如，股票的beta为1.2，则大盘涨1%，该股涨1.2%，科技股和中小盘股的贝塔值往往高于大盘。","4. 当Beta值<0，表明公司股价波动性与大盘成反比。如，个股Beta系数为-1.3时，说明当大盘涨1%，它可能跌1.3%，同理，如果大盘跌1%，它可能涨1.3%。"]},unleveredBeta:{bigTitle:"剔除财务杠杆风险系数分析UBeta",title:"通常，无杠杆贝塔值越大，企业经营风险越大，投资者要求的投资回报率越大，市盈率越低。而且，市盈率与无杠杆的贝塔值之间的关系是反向的。",yTitle:"无杠杆贝塔系数",smallTitle:!0,textList:["无杠杆UBeta一般小于等于杠杆Beta，如果公司杠杆率较低，那么无杠杆Beta会很接近杠杆Beta；可与Beta分析同步使用。"]},erp:{bigTitle:"股票风险溢价分析ERP",title:"通常，被专家们用于：",yTitle:"股票风险溢价",textList:["1. 进行投资决策。例如，美国1926-1997年间，普通股平均收益比国库券平均收益高9.2个百分点，国内1992-2004上证股指平均收益率19.68%，深证股指平均收益率24.59%；","2. 进行宏观经济预测。例如，当ERP下降，引起股价上升。股市是宏观经济运行的先行指标，通过ERP和股价的变动趋势，可预测宏观经济趋势；","3. 当该指标越高，股票就越被低估，越具有投资价值。"]}};this.textData=t[this.type]},_initOptions:function(){var t=this;this.options={tooltip:{formatter:function(e){var n=y[t.type]?"%":"",a=e[0].name,i="".concat(t.textData.yTitle," ").concat(e[0].value).concat(n);return[a,i].join("<br/>")}},xAxis:{axisLine:{show:!0,lineStyle:{color:"#ccc"}},axisLabel:{color:"#909399",formatter:function(t){return t.length<6?t:"".concat(t.substr(0,5),"...")}}},yAxis:{name:this.textData.yTitle,axisLine:{show:!0,lineStyle:{color:"#ccc"}},axisLabel:{color:"#909399"},splitLine:{show:!0,lineStyle:{color:"#EBEEF5"}}},grid:{right:100,left:30},legend:{icon:"line",itemWidth:18,itemHeight:15,top:5,right:5,orient:"vertical"}}},_apiGetCaseRiskvalue:function(t){var e=this;return Object(r["a"])(regeneratorRuntime.mark((function n(){var a;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.prev=0,e.loading=!0,n.next=4,Object(d["g"])(t);case 4:a=n.sent,e.formatResdata(a),n.next=12;break;case 8:n.prev=8,n.t0=n["catch"](0),e.xdata=[],e.data=[];case 12:e.loading=!1;case 13:case"end":return n.stop()}}),n,null,[[0,8]])})))()}}},v=m,g=(n("ed2c"),n("2877")),b=Object(g["a"])(v,a,i,!1,null,"4ef1be0d",null);e["a"]=b.exports}}]);