(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1f11a88c"],{"0a81":function(t,e,a){},"3b42":function(t,e,a){},"3d4e":function(t,e,a){"use strict";a("4fbd")},"3e58":function(t,e,a){"use strict";a("3b42")},"4fbd":function(t,e,a){},"600e":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",[a("ChartBox",{attrs:{title:t.infoTitle,moreFlag:!1,rank:t.rank,color:"#11FDDA"}},[a("div",{attrs:{slot:"rank"},slot:"rank"}),a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("EchartColumn",{ref:"columnCharts",attrs:{height:320,title:"",bgcolor:"white",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},r=[],o=(a("99af"),a("c740"),a("d81d"),a("a9e3"),a("96cf"),a("1da1")),i=a("5530"),s=a("2abf"),c=a("33c3"),l=a("8943"),u=a("1c31"),d=a("a741"),p={mixins:[d["a"]],props:{infoTitle:{type:String},companyInfo:{type:Object},scoreTypeId:{type:Number}},components:{ChartBox:s["a"],EchartColumn:l["a"]},data:function(){return{loading:!1,title:"",xdata:[],data:[],options:{},rank:1}},created:function(){this._initUIView()},methods:{queryTap:function(){var t={type:"market",unipo:this.companyInfo.unipo,scoreType:this.scoreTypeId,companyId:this.companyInfo.companyId,industryCode:this.companyInfo.industryCode};this._params=t;var e=this.mixinReqOneTime(),a=this.mixinReqBiOneTime(),n=Object(i["a"])(Object(i["a"])(Object(i["a"])({},a),t),{},{companyId:t.unipo?t.companyId:null,industryCode:null,dateType:e.dateType});this._apiGetScoreModelRank(n)},formatResdata:function(t){var e=this._params.companyId;9===this._params.scoreType&&(e=this._params.industryCode);var a=t.findIndex((function(t){return t.companyId===Number(e)})),n=t.map((function(t){return t.companyName})),r=t.map((function(t){var a=t.score;return{value:a,item:t,itemStyle:{color:t.companyId===e?"#F9D22B":"#409EFF"}}})),o=t[a];this.rank=o.marketRank,this.xdata=n;var i=parseInt(180/t.length)>=20?20:parseInt(180/t.length),s=i<3?3:i;this.data=[{name:"",type:"bar",data:r,barWidth:s,color:"#0F9FFD",itemStyle:{normal:{}},markPoint:Object(c["f"])({name:"本行业",value:"".concat(n[a],"：第").concat(this.rank,"名"),index:a,yAxis:o.score})}]},_initUIView:function(){this._initOptions(),this.queryTap()},_initOptions:function(){var t=JSON.parse(JSON.stringify(c["d"]));t[0].left=20,t[0].right=20,this.options={grid:{left:30,right:40},backgroundColor:"transparent",dataZoom:t,yAxis:{name:"分数",nameTextStyle:{color:"rgba(0, 0, 0, .65)"},min:0,max:100,axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)"}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0, 0, 0, .65)",formatter:function(t){return t.length>4?"".concat(t.substring(0,4),"..."):t}}}}},_apiGetScoreModelRank:function(t){var e=this;return Object(o["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(u["v"])(t);case 4:n=a.sent,e.formatResdata(n),a.next=10;break;case 8:a.prev=8,a.t0=a["catch"](0);case 10:e.loading=!1;case 11:case"end":return a.stop()}}),a,null,[[0,8]])})))()}}},m=p,f=(a("3d4e"),a("2877")),y=Object(f["a"])(m,n,r,!1,null,"0a56188f",null);e["a"]=y.exports},8844:function(t,e,a){"use strict";a("0a81")},a6e6:function(t,e,a){"use strict";a.d(e,"a",(function(){return n}));var n={"盈利能力":"该维度主要是指，企业基于主营业务活动的获利能力。","运营能力":"该维度是指，企业受外部环境约束下，通过人、财、物等资源对经营活动的配置组合，产生财务结果的能力。","应对行业风险能力":"行业风险主要指，当行业寿命周期、技术革新、政策等因素变化，将引起企业经营的关联变动，从而折射企业对风险的应对能力","应对运营风险能力":"运营风险是指，公司经营中因各种因素变化而导致经营活动出现不确定性增大、甚至有可能损失，从相关财务指标，能折射企业应对这种风险的能力。","应对成长风险能力":"成长风险主要指，公司成长过程中的相关财务风险，包括主营业务、组织效率等，其表现能折射公司应对成长不确定性的能力。","偿债能力":"偿债能力是指，企业偿还到期债务（本息）的能力。","应对战略风险能力":"战略风险可理解为，企业因各种经营的不确定性会带来的损失，相关指标表现能折射企业应对这种风险的能力。","应对市场风险能力":"市场风险是指，由于业务在市场停滞不前、或失去客户、或被竞争对手打败等，导致企业发展风险的不确定性增加，相关指标表现能折射企业应对该风险的能力。","毛利率":"毛利与营业收入的比值，反映企业整体的盈利能力，包括产品创新、技术研发、市场营销与竞争、品牌效应、成本优势、生命周期等。","净利润率":"它是指扣除所有成本、费用和企业所得税后的利润率，反映企业从销售收入中创造利润的能力","净资产收益率":"评估企业股东权益（净资产）的收益水平，反映了企业自有资本获得净收益的能力","投入资本回报率ROIC":"反映企业已投入资本的获利能力","总资产报酬率ROA":"企业投资报酬与投资总额之间的比率，反映企业总资产获利能力","盈余现金保障倍数":"是指企业报告期经营现金净流量同净利润的比值，是实现利润中现金收益的保障程度，反映企业盈利质量","营业收入现金含量比":"是实现收入中的现金比重，反映企业收入质量","流动资产总资产比":"折射是否有发展机会","积累比率":"反映公司自身造血能力，折射是否有成长性","资本积累率":"反映公司发展潜力，也反映投资者资本是否与保全性和增长性。","资产负债率":"体现企业资本结构是否稳健，也折射企业发展潜力","负债经营率":"衡量企业的独立性和稳定性，也折射出企业发展的潜力","再投资率分析":"评估企业用盈余所得再投资是否有能力支持公司成长","留存收益比率":"评估企业净利润有多少用于保留盈余用于经营的扩张","固定资产与股东权益比率":"反映企业财务结构的稳定性是否支持企业的发展；","市场价值":"该估值模型是基于近期市场投资者对贵公司所在行业所有企业股票市值或价值估值的平均值。","风险/投资基金价值":"该估值模型是基于风险投资、和私募股权基金对贵公司所在行业所有企业价值的估值的平均值。","银行信贷安全价值":"该估值模型是基于考虑了贵公司所在行业企业被拆分、并按资产出售的情况下，计算所有公司估值的平均值。","财务报表价值":"该估值模型是基于标准会计准则下，提供一种基于贵公司所在行业全部企业资产和负债的价值估值的平均值。","重置/替换价值":"该估值模型是基于贵公司所在行业，任何一个企业竞争对手要复杂一个企业业务，而计算的估值的平均值。","永续价值":"该估值模型是基于贵公司所在行业全部企业现金流量的统计趋势，而转换计算的价值估值的平均值。","不断增长的永续价值":"该估值模型假设现金流是永恒的，而且会增加的条件下，用贵公司所在行业全部企业的现金流除以折现率和增长率之差，再将其趋势转换成价值的平均值。","现金流量价值":"该估值模型是一个数学和统计模型，将贵公司所在行业的全部企业过去的收入现金流转化为价值的平均值。","杜邦分析/净资产收益率":"反映股东权益的收益水平，以衡量企业运用自有资本的效率","总资产收益率":"反映企业总资产运用的效率","总资产周转率":"综合反映企业全部资产的经营质量和利用效率","存货周转率":"反映企业采购、生产、销售等内部价值链运转效率","销售净利润率":"综合反映企业业务的经营效率；","应收账款周转率":"年内应收账款转为现金的平均次数，也即是应收账款流动速度，折射资源利用效率；","息税前利润/企业价值分析EBIT/EV":"衡量公司收益率；当公司间效率比较时，该指标比ROE、ROIC更有优势","可持续增长率分析SGR":"反映在不发行新股、不改变财务政策时，企业销售所能达到的增长率。折射企业内在增长力和适宜发展速度等效率","劳动生产率":"企业员工在报告期劳动的投入产出比值。折射企业的组织创新、人才发展能力等经营效率"}},c9a5:function(t,e,a){"use strict";a("d81d"),a("a9e3"),a("96cf");var n=a("1da1"),r=a("5530"),o=a("1c31"),i=a("a741");e["a"]={mixins:[i["a"]],props:{soreTypeId:{type:[Number,String],default:""}},methods:{changeSelect:function(t){var e=t.map((function(t){var e=t.caseCompanyScoreList[0];return{id:e.companyId,code:e.code,companyName:e.companyName,industryCode:e.industryCode,selected:!0}})),a=this.$refs.mark;a&&a.change(e),this.companyChange(e)},queryTap:function(){var t=this.mixinReqBiOneTime();this._apiGetScoreTopThree(Object(r["a"])({companyId:this.companyInfo.companyId,soreTypeId:this.soreTypeId},t))},_apiGetScoreTopThree:function(t){var e=this;return Object(n["a"])(regeneratorRuntime.mark((function a(){var n;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.prev=0,e.loading=!0,a.next=4,Object(o["w"])(t);case 4:n=a.sent,e.resList=n,e.changeSelect(n),a.next=12;break;case 9:a.prev=9,a.t0=a["catch"](0),e.loading=!1;case 12:case"end":return a.stop()}}),a,null,[[0,9]])})))()}}}},e7de:function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"star-box g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[a("Card",{attrs:{score:t.score,level:t.scoreLevel,levelList:t.levelList,sort:t.sort,showMore:t.showMore,industryRank:t.industryRank,rankTxt:t.rankTxt,industryRankMarket:t.industryRankMarket,levelMarket:t.levelMarket,scoreMarket:t.scoreMarket,data:t.data}})],1)},r=[],o=(a("a9e3"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"card-page"},[a("CardItem",{attrs:{typeText:"对标行业",rankTxt:"在本行业"+t.rankTxt,industryRank:t.industryRank,score:t.score,sort:t.sort,level:t.level,levelList:t.levelList},on:{rankTap:t.industryRankTap}}),a("CardItem",{attrs:{typeText:"对标市场",rankTxt:"在整个市场"+t.rankTxt,industryRank:t.industryRankMarket,score:t.scoreMarket,sort:t.sort,level:t.levelMarket,levelList:t.levelList},on:{rankTap:t.marketRankTap}}),t.showMore?a("div",{staticClass:"parent-foot"},[t._m(0),a("moreBtn",{staticClass:"foot-more",attrs:{txt:"一键获取优化策略"},on:{moreTap:t.moreTap}})],1):t._e()],1)}),i=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"warning-box"},[a("i",{staticClass:"el-icon-info icon"}),a("span",{staticClass:"t"},[t._v("提示：风险概率越高评分越高，排名越靠后")])])}],s=(a("99af"),a("ac1f"),a("5319"),a("5530")),c=a("9d27"),l=a("c598"),u=a("8905"),d={components:{moreBtn:c["a"],CardItem:u["a"]},props:{data:{type:Object,default:function(){return{}}},rankTxt:{type:String,default:""},industryRank:{type:[Number,String],default:null},industryRankMarket:{type:[Number,String],default:null},showMore:{type:Boolean,default:!1},score:{type:Number,default:20},scoreMarket:{type:Number,default:20},sort:{type:Boolean,default:!1},level:{type:String,default:"1"},levelMarket:{type:String,default:"1"},levelList:{type:Array,default:function(){return["a1","a2","a3","a4","a5"]}}},data:function(){return{}},computed:{progressStyle:function(){var t=Math.min(this.score,100);return{width:t+"%"}},tagStyle:function(){var t=Math.min(this.score,100);return{left:t+"%"}},trueLevel:function(){return this.sort?7-this.level:this.level}},methods:{moreTap:function(){l["a"].$emit("showReport",{component:"ImproveSuggest"})},marketRankTap:function(){var t=this.getCommonParams("整个市场");l["a"].$emit("portraitRankTap",Object(s["a"])(Object(s["a"])({},t),{},{type:"market"}))},industryRankTap:function(){var t=this.getCommonParams("本行业");l["a"].$emit("portraitRankTap",Object(s["a"])(Object(s["a"])({},t),{},{type:"industry"}))},getCommonParams:function(t){var e=this.data.rankTxt.replace(/第$/,"");return{scoreType:this.data.scoreTypeId,title:"".concat(t).concat(e)}}}},p=d,m=(a("3e58"),a("2877")),f=Object(m["a"])(p,o,i,!1,null,"51906320",null),y=f.exports,h={props:{data:{type:Object,default:function(){return{}}},scoreMarket:{type:Number,default:20},industryRankMarket:{type:[Number,String],default:null},rankTxt:{type:String,default:""},industryRank:{type:[Number,String],default:null},showMore:{type:Boolean,default:!1},score:{type:Number,default:9},sort:{type:Boolean,default:!1},levelList:{type:Array,default:function(){return["a1","a2","a3","a4","a5"]}},loading:{type:Boolean,default:!1}},components:{Card:y},data:function(){return{}},computed:{scoreLevel:function(){return this.getScoreLevel(this.score)},levelMarket:function(){return this.getScoreLevel(this.scoreMarket)}},methods:{getScoreLevel:function(t){for(var e=[20,30,50,70,89,100],a=1,n=0,r=e;n<r.length;n++){var o=r[n];if(t<=o)return"".concat(a);a++}return"5"}}},v=h,g=(a("8844"),Object(m["a"])(v,n,r,!1,null,"4cf94d1e",null));e["a"]=g.exports}}]);