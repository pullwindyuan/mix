(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-44927646"],{"1afe":function(n,e,t){"use strict";t("2a81")},"1c31":function(n,e,t){"use strict";t.d(e,"p",(function(){return a})),t.d(e,"y",(function(){return d})),t.d(e,"q",(function(){return o})),t.d(e,"r",(function(){return u})),t.d(e,"x",(function(){return s})),t.d(e,"n",(function(){return c})),t.d(e,"e",(function(){return p})),t.d(e,"l",(function(){return m})),t.d(e,"a",(function(){return f})),t.d(e,"d",(function(){return x})),t.d(e,"g",(function(){return I})),t.d(e,"h",(function(){return l})),t.d(e,"j",(function(){return v})),t.d(e,"k",(function(){return y})),t.d(e,"i",(function(){return h})),t.d(e,"s",(function(){return T})),t.d(e,"z",(function(){return g})),t.d(e,"t",(function(){return w})),t.d(e,"w",(function(){return b})),t.d(e,"u",(function(){return L})),t.d(e,"f",(function(){return N})),t.d(e,"c",(function(){return k})),t.d(e,"b",(function(){return C})),t.d(e,"m",(function(){return _})),t.d(e,"o",(function(){return M})),t.d(e,"v",(function(){return B}));t("99af");var r=t("bc3a"),i=t.n(r);function a(n){var e="/case/risk/construction";return i.a.post(e,n)}function d(n){var e="/case/risk/single/construction";return i.a.post(e,n)}function o(n){var e="/case/risk/industry/dis";return i.a.post(e,n)}function u(n){var e="/case/risk/industry/quarter";return i.a.post(e,n)}function s(n){var e="/case/risk/industry/range/"+n;return i.a.post(e)}function c(n){var e="/case/bi/factor/history";return i.a.post(e,n)}function p(n){var e="/case/finance/bench";return i.a.post(e,n)}function m(n){var e="/dataModel/getDuPoint";return i.a.post(e,n)}function f(n){var e="/case/competitive/radar";return i.a.post(e,n)}function x(){var n="/case/competitive/industry";return i.a.get(n)}function I(n){var e="/case/riskvalue";return i.a.post(e,n)}function l(n){var e="/case/riskvalue/input";return i.a.post(e,n)}function v(n){var e="/case/value/eight";return i.a.post(e,n)}function y(n){var e="/case/value/industry/".concat(n.industryCode);return i.a.post(e,n)}function h(n){var e="/case/value/dis/".concat(n.industryCode);return i.a.post(e,n)}function T(n){var e=n.type,t=n.date,r=n.companyId,a="/scoreModel/portrait/".concat(e,"/").concat(t,"/").concat(r);return i.a.get(a)}function g(){var n="/scoreModel/index/subscore";return i.a.get(n)}function w(n){var e="/scoreModel/industry/order";return i.a.post(e,n)}function b(n){var e="/scoreModel/company/scoreTop";return i.a.post(e,n)}function L(n){var e="/scoreModel/company/scoreList";return i.a.post(e,n)}function N(n){var e="/case/risk/rank";return i.a.post(e,n)}function k(n){var e="/dataModel/getBiReportByQuarter";return i.a.post(e,n)}function C(n){var e="/dataModel/getBiIndexDataByQuarter";return i.a.post(e,n)}function _(n){var e="/case/bi/factor/history";return i.a.post(e,n)}function M(n){var e="/case/industry/location";return i.a.post(e,n)}function B(n){var e="/scoreModel/rank";return i.a.post(e,n)}},"2a81":function(n,e,t){},"35d2":function(n,e,t){},"391a":function(n,e,t){"use strict";t.r(e);var r=function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("div",{staticClass:"bi-page g-bg-white"},[t("BiHeader",{attrs:{companyInfo:n.companyInfo,infoTitle:n.companyInfo.companyName+"发展能力分析",imgUrl:"/images/3-7-2.png",showTitle:!n.$slots.title,subTitle:"发展能力是指，企业在确保基本生存的基础上，有能力扩大规模、提升效率、增强竞争优势的能力。特别是AIBI洞察8个相关财务指标表现，能折射企业发展能力的强弱。例如，",textList:["1）营业收入增长率；","2）净利润增长率；","3）指标保值增长率；","4）资本积累率；","5）总资产增长率；","6）技术研发投入比；","7）营业收入三年平均增长率；","8）资本三年平均增长率。","请查看以下具体的分析："]}},[t("div",{staticClass:"img-info-slot",attrs:{slot:"imgInfo"},slot:"imgInfo"},[n._v(" 可查看该企业 "),t("span",{directives:[{name:"theme-color-text",rawName:"v-theme-color-text"}],staticClass:"g-span-primary-line",on:{click:function(e){return n.openTap("IRR")}}},[n._v("【内部收益率IRR】")]),n._v(" 分析 ")])]),n._l(n.dataList,(function(e){return t("div",{key:e.indexId},[t("BiBoxElement",{attrs:{queryData:e,companyInfo:n.companyInfo}})],1)}))],2)},i=[],a=t("de03"),d=t("fbcb"),o=t("8da4"),u=t("7044"),s={mixins:[a["a"]],components:{BiHeader:d["a"],BiBoxElement:o["a"]},data:function(){return{}},created:function(){this._initUIView()},methods:{openTap:function(n){window.$IM.openReport({path:"/home/main/risk-map",query:{component:n}})},_initUIView:function(){this.mixinGetBiIndexList(u["i"])}}},c=s,p=(t("90e4"),t("2877")),m=Object(p["a"])(c,r,i,!1,null,"15c314a6",null);e["default"]=m.exports},7044:function(n,e,t){"use strict";t.d(e,"w",(function(){return r})),t.d(e,"r",(function(){return i})),t.d(e,"v",(function(){return a})),t.d(e,"m",(function(){return d})),t.d(e,"n",(function(){return o})),t.d(e,"h",(function(){return u})),t.d(e,"i",(function(){return s})),t.d(e,"u",(function(){return c})),t.d(e,"f",(function(){return p})),t.d(e,"d",(function(){return m})),t.d(e,"z",(function(){return f})),t.d(e,"a",(function(){return x})),t.d(e,"c",(function(){return I})),t.d(e,"b",(function(){return l})),t.d(e,"g",(function(){return v})),t.d(e,"y",(function(){return y})),t.d(e,"o",(function(){return h})),t.d(e,"s",(function(){return T})),t.d(e,"t",(function(){return g})),t.d(e,"e",(function(){return w})),t.d(e,"k",(function(){return b})),t.d(e,"l",(function(){return L})),t.d(e,"j",(function(){return N})),t.d(e,"A",(function(){return k})),t.d(e,"p",(function(){return C})),t.d(e,"x",(function(){return _})),t.d(e,"q",(function(){return M}));var r={indexList:[1,2,3,4,5,6],reportType:1,repName:"应对战略风险能力"},i={indexList:[7,6,9,3,1],reportType:2,repName:"应对市场风险能力"},a={indexList:[4,9,10,92,51,13],reportType:3,repName:"应对运营风险能力"},d={indexList:[4,14,3,7,6,15],reportType:4,repName:"应对成长风险能力"},o={indexList:[16,17,5,10],reportType:5,repName:"应对行业风险能力"},u={indexList:[6,9,18,19,20,16,21,22],reportType:6,repName:"业务抗风险能力"},s={indexList:[7,23,92,24,25,26,2,27],reportType:7,repName:"发展能力"},c={indexList:[6,9,28,29,21,30,31,32,33,34],reportType:8,repName:"盈利能力"},p={indexList:[35,17,36,37,16,38,39,40,41,21,22,42,43],reportType:9,repName:"偿债能力"},m={indexList:[44,45,46,47,48,49,50],reportType:10,repName:"成本管控能力"},f={indexList:[51,3,4,52,53,54,55,56,57,58],reportType:11,repName:"营运能力"},x={reportType:13,indexList:[14,71,72,16,17,36],repName:"资金链断裂分析"},I={reportType:14,indexList:[74,75,76,77,78],repName:"现金流健康度分析"},l={reportType:15,indexList:[79,80,81,82,16,83],repName:"资产结构分析"},v={reportType:16,indexList:[16,84,85,86,87,89,90,91,92,93,94],repName:"负债及权益结构分析"},y={reportType:17,indexList:[62],repName:"营运资本分析WC"},h={reportType:18,indexList:[95],repName:"再投资率分析RR"},T={reportType:19,indexList:[96],repName:"非现金营运资本分析"},g={reportType:20,indexList:[97],repName:"非现金运营资本"},w={reportType:21,indexList:[65,5,66,68,9,10],repName:"杜邦分析"},b={reportType:22,indexList:[105],repName:"企业价值/息税前利润分析"},L={reportType:23,indexList:[106],repName:"息税前利润/企业价值比分析EBIT/EV"},N={reportType:24,indexList:[107],repName:"企业价值/息税折旧摊销前利润分析"},k={reportType:25,indexList:[10],repName:"总资产周转率"},C={reportType:25,indexList:[108],repName:"租赁或租赁效应投入资本回报率分析LEROIC"},_={reportType:27,indexList:[109],repName:"可持续增长率分析sgr"},M={reportType:28,indexList:[110],repName:"劳动生产率分析"}},"753d":function(n,e,t){"use strict";e["a"]={1:{name:"市场占有率",indexId:1,hideIndustry:!0},2:{name:"研发费用率",indexId:2,hide:!0},3:{name:"存货周转率",indexId:3,hide:!0},4:{name:"应收账款周转率",indexId:4,hide:!0},5:{name:"总资产回报率",indexId:5},6:{name:"毛利率",indexId:6},7:{name:"营业收入增长率",indexId:7},8:{name:"净利润率",indexId:8},9:{name:"净利润率",indexId:9},10:{name:"总资产周转率",indexId:10},11:{name:"总资产增长率",indexId:11},12:{name:"人均营业收入",indexId:12},13:{name:"人均净利润",indexId:13},14:{name:"销售现金比率",indexId:14},15:{name:"期间费用率",indexId:15},16:{name:"资产负债率",indexId:16},17:{name:"流动比率",indexId:17,hide:!0},18:{name:"经营安全率",indexId:18},19:{name:"经营风险系数",indexId:19},20:{name:"财务风险系数",indexId:20},21:{name:"净资产收益率",indexId:21},22:{name:"实际利息率",indexId:22},23:{name:"净利润增长率",indexId:23},24:{name:"总资产增长率",indexId:24},25:{name:"经营净现金流增长率",indexId:25},26:{name:"可动用资金总额增长率",indexId:26},27:{name:"流动资产周转速度增长率",indexId:27},28:{name:"营业利润率",indexId:28},29:{name:"成本费用利润率",indexId:29},30:{name:"资本利润率",indexId:30},31:{name:"总资产报酬率",indexId:31},32:{name:"盈余现金保障倍数",indexId:32},33:{name:"内部经营资产收益率",indexId:33},34:{name:"对外投资收益率",indexId:34},35:{name:"自由现金流",indexId:35},36:{name:"速动比率",indexId:36,hide:!0},37:{name:"利息保障倍数",indexId:37},38:{name:"现金流动资产比",indexId:38,hide:!0},39:{name:"经营还债能力",indexId:39,hide:!0},40:{name:"债务偿还率",indexId:40,hide:!0},41:{name:"现金流动负债率",indexId:41,hide:!0},42:{name:"产权比率",indexId:42},43:{name:"长期资产适合率",indexId:43},44:{name:"主营业务成本率",indexId:44},45:{name:"主营业务成本利润率",indexId:45},46:{name:"销售费用利润率",indexId:46},47:{name:"管理费用率",indexId:47},48:{name:"财务费用率",indexId:48,hide:!0},49:{name:"管理费用资产比",indexId:49},50:{name:"人均管理资产",indexId:50},51:{name:"人均销售收入",indexId:51},52:{name:"应付账款周转率",indexId:52,hide:!0},53:{name:"现金周期",indexId:53},54:{name:"营业周期",indexId:54},55:{name:"流动资产周转天数",indexId:55,hide:!0},56:{name:"总资产周转天数",indexId:56},57:{name:"固定资产周转天数",indexId:57},58:{name:"资产现金回收率",indexId:58},59:{name:"营运资本收入比率",indexId:59,hide:!0},60:{name:"固定比率",indexId:60},61:{name:"营运资金需求增长率",indexId:61},62:{name:"营运资本",indexId:62,hide:!0},63:{name:"营运资金需求",indexId:63},64:{name:"现金支付能力",indexId:64},65:{name:"净资产收益率（ROE）",indexId:65},66:{name:"权益乘数",indexId:66},67:{name:"期间费用",indexId:67},68:{name:"归属于母公司股东净利润占比",indexId:68},69:{name:"应收账款",indexId:69},71:{name:"应收账款与总资产比重",indexId:71},72:{name:"应收账款与流动资产比重",indexId:72},74:{name:"现金总负债比率",indexId:74},75:{name:"全部资产现金回收率",indexId:75},76:{name:"现金再投资比率",indexId:76},77:{name:"到期债务本息偿付比率",indexId:77},78:{name:"现金购销比率",indexId:78},79:{name:"流动资产总资产比",indexId:79},80:{name:"存货比率",indexId:80},81:{name:"固定资产与股东权益比率",indexId:81},82:{name:"股东权益比率",indexId:82},83:{name:"长期负债比率",indexId:83},84:{name:"自有资金负债率",indexId:84},85:{name:"负债经营率",indexId:85},86:{name:"流动负债比重",indexId:86},87:{name:"长期负债比重",indexId:87},89:{name:"有息负债比重",indexId:89},90:{name:"无息负债比重",indexId:90},91:{name:"积累比率",indexId:91},92:{name:"总资产增长率",indexId:92},93:{name:"投入资本比率",indexId:93},94:{name:"留存收益比率",indexId:94},95:{name:"再投资率分析",indexId:95},96:{name:"非现金营运资本分析",indexId:96},97:{name:"非现金营运资本/销售比（NCWC）",indexId:97},105:{name:"企业价值/息税前利润比分析（EV/EBIT）",indexId:105},106:{name:"息税前利润/企业价值分析（EBIT/EV）",indexId:106},107:{name:"企业价值倍数",indexId:107},109:{name:" 可持续增长率",indexId:109},110:{name:"劳动生产率",indexId:110}}},"7db0":function(n,e,t){"use strict";var r=t("23e7"),i=t("b727").find,a=t("44d2"),d=t("ae40"),o="find",u=!0,s=d(o);o in[]&&Array(1)[o]((function(){u=!1})),r({target:"Array",proto:!0,forced:u||!s},{find:function(n){return i(this,n,arguments.length>1?arguments[1]:void 0)}}),a(o)},"90e4":function(n,e,t){"use strict";t("35d2")},de03:function(n,e,t){"use strict";t("d81d");var r=t("5530"),i=t("753d");e["a"]={props:{companyInfo:{type:Object,default:function(){return{}}}},data:function(){return{dataList:[]}},methods:{mixinGetBiIndexList:function(n){this.dataList=n.indexList.map((function(e){var t=i["a"][e];return Object(r["a"])(Object(r["a"])({},t),{},{reportType:n.reportType,repName:n.repName})}))}}}},f4dd:function(n,e,t){"use strict";t.d(e,"j",(function(){return a})),t.d(e,"i",(function(){return d})),t.d(e,"h",(function(){return o})),t.d(e,"g",(function(){return u})),t.d(e,"a",(function(){return s})),t.d(e,"e",(function(){return c})),t.d(e,"l",(function(){return p})),t.d(e,"k",(function(){return m})),t.d(e,"f",(function(){return f})),t.d(e,"d",(function(){return x})),t.d(e,"c",(function(){return I})),t.d(e,"b",(function(){return l}));var r=t("bc3a"),i=t.n(r);function a(){var n="/case/industry";return i.a.post(n)}function d(n){var e="/finance/getIndex";return i.a.post(e,n)}function o(n,e){var t="/case/list/page";return i.a.post(t,n,{cancelToken:e?new i.a.CancelToken((function(n){e.c=n})):null})}function u(n,e){var t="/case/list/dialog";return i.a.post(t,n,{cancelToken:e?new i.a.CancelToken((function(n){e.c=n})):null})}function s(n){var e="/company/history/batch";return i.a.post(e,n)}function c(n){var e="/case/riskroi/history";return i.a.post(e,n)}function p(n){var e="/case/risk/suggest/".concat(n.companyId);return i.a.get(e)}function m(n){var e="/case/risk/distribution";return i.a.post(e,n,{hideError:!0})}function f(n){var e="/case/risk";return i.a.post(e,n)}function x(n){var e="/case/bi/factor";return i.a.post(e,n)}function I(n){var e="/dataModel/getBiIndexData";return i.a.post(e,n)}function l(n){var e="/dataModel/getBiReport";return i.a.post(e,n)}},fbcb:function(n,e,t){"use strict";var r=function(){var n=this,e=n.$createElement,t=n._self._c||e;return t("div",{staticClass:"bi-header"},[n.showTitle?t("div",{staticClass:"big-title"},[n._v(" "+n._s(n.infoTitle)+" "),t("span",{staticClass:"year"},[n._v("（基于"+n._s(n.$startYear)+" - "+n._s(n.$endYear)+"数据）")])]):n._e(),t("div",{staticClass:"top-banner-wrap",class:n.showMore?"show":""},[t("div",{staticClass:"l-banner-main"},[t("div",{staticClass:"sub-title",class:n.showMore?"":"one-line"},[n._v(" "+n._s(n.subTitle)+" ")]),t("div",{staticClass:"future-risk-content-info",class:n.showMore?"h270":""},[t("br",{directives:[{name:"show",rawName:"v-show",value:n.showMore,expression:"showMore"}]}),n._l(n.textList,(function(e,r){return t("div",{directives:[{name:"show",rawName:"v-show",value:n.showMore,expression:"showMore"}],key:r},[t("div",{staticClass:"text-list"},[t("span",{staticClass:"point"}),t("span",{staticClass:"t"},[n._v(n._s(e))])]),t("br")])})),t("div",{staticClass:"href-a",on:{click:n.handleToggleShow}},[n._v(" "+n._s(n.showMore?"收起详情":"展开该指标内涵")+" ")])],2),t("div",{staticClass:"foot-info"},[t("div",{staticClass:"img-info"},[n._t("imgInfo")],2),t("div",{staticClass:"more-down"},[n._v(n._s(n.moreText))])])]),t("div",{staticClass:"r-img"},[t("div",{staticClass:"img-bg",style:{"background-image":"url("+n.imgUrl+")"}})])])])},i=[],a={props:{infoTitle:{type:String,default:""},imgUrl:{type:String,default:""},subTitle:{type:String,default:""},showTitle:{type:Boolean,default:!0},textList:{type:Array,default:function(){return[]}},moreText:{type:String,default:""}},data:function(){return{showMore:!1}},methods:{handleToggleShow:function(){this.showMore=!this.showMore}}},d=a,o=(t("1afe"),t("2877")),u=Object(o["a"])(d,r,i,!1,null,"540c1f34",null);e["a"]=u.exports}}]);