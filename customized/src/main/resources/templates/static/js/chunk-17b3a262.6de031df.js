(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-17b3a262"],{"1c31":function(e,n,t){"use strict";t.d(n,"p",(function(){return d})),t.d(n,"y",(function(){return a})),t.d(n,"q",(function(){return u})),t.d(n,"r",(function(){return o})),t.d(n,"x",(function(){return c})),t.d(n,"n",(function(){return s})),t.d(n,"e",(function(){return m})),t.d(n,"l",(function(){return p})),t.d(n,"a",(function(){return x})),t.d(n,"d",(function(){return I})),t.d(n,"g",(function(){return f})),t.d(n,"h",(function(){return v})),t.d(n,"j",(function(){return y})),t.d(n,"k",(function(){return l})),t.d(n,"i",(function(){return h})),t.d(n,"s",(function(){return L})),t.d(n,"z",(function(){return T})),t.d(n,"t",(function(){return N})),t.d(n,"w",(function(){return b})),t.d(n,"u",(function(){return C})),t.d(n,"f",(function(){return g})),t.d(n,"c",(function(){return _})),t.d(n,"b",(function(){return k})),t.d(n,"m",(function(){return B})),t.d(n,"o",(function(){return w})),t.d(n,"v",(function(){return E}));t("99af");var i=t("bc3a"),r=t.n(i);function d(e){var n="/case/risk/construction";return r.a.post(n,e)}function a(e){var n="/case/risk/single/construction";return r.a.post(n,e)}function u(e){var n="/case/risk/industry/dis";return r.a.post(n,e)}function o(e){var n="/case/risk/industry/quarter";return r.a.post(n,e)}function c(e){var n="/case/risk/industry/range/"+e;return r.a.post(n)}function s(e){var n="/case/bi/factor/history";return r.a.post(n,e)}function m(e){var n="/case/finance/bench";return r.a.post(n,e)}function p(e){var n="/dataModel/getDuPoint";return r.a.post(n,e)}function x(e){var n="/case/competitive/radar";return r.a.post(n,e)}function I(){var e="/case/competitive/industry";return r.a.get(e)}function f(e){var n="/case/riskvalue";return r.a.post(n,e)}function v(e){var n="/case/riskvalue/input";return r.a.post(n,e)}function y(e){var n="/case/value/eight";return r.a.post(n,e)}function l(e){var n="/case/value/industry/".concat(e.industryCode);return r.a.post(n,e)}function h(e){var n="/case/value/dis/".concat(e.industryCode);return r.a.post(n,e)}function L(e){var n=e.type,t=e.date,i=e.companyId,d="/scoreModel/portrait/".concat(n,"/").concat(t,"/").concat(i);return r.a.get(d)}function T(){var e="/scoreModel/index/subscore";return r.a.get(e)}function N(e){var n="/scoreModel/industry/order";return r.a.post(n,e)}function b(e){var n="/scoreModel/company/scoreTop";return r.a.post(n,e)}function C(e){var n="/scoreModel/company/scoreList";return r.a.post(n,e)}function g(e){var n="/case/risk/rank";return r.a.post(n,e)}function _(e){var n="/dataModel/getBiReportByQuarter";return r.a.post(n,e)}function k(e){var n="/dataModel/getBiIndexDataByQuarter";return r.a.post(n,e)}function B(e){var n="/case/bi/factor/history";return r.a.post(n,e)}function w(e){var n="/case/industry/location";return r.a.post(n,e)}function E(e){var n="/scoreModel/rank";return r.a.post(n,e)}},"4e21":function(e,n,t){"use strict";t.r(n);var i=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("div",[t("HeaderBox",{attrs:{companyInfo:e.companyInfo,infoTitle:"可持续增长率分析SGR",imgUrl:"/images/financial/i-3-9A.png",subTitle:"SGR可持续增长率是指，在不发行新股、不改变经营效率和财务政策时，企业销售所能达到的增长率。被专家们广泛用来评估企业当前经营效率和财务政策决定的内在增长能力，这也意味着，该指标可折射企业一种适宜的发展速度。"}},[e._t("title",null,{slot:"title"}),t("div",[t("div",{staticClass:"h-r-title"},[t("br"),e._v(" 当实际增长率大于可持续增长率，意味着企业现金短缺，决策者可将下面几种办法综合运用，谋求平衡： ")]),t("div",{staticClass:"ul"},[t("div",{staticClass:"h-r-item"},[e._v("1）增加权益资本；")]),t("div",{staticClass:"h-r-item"},[e._v("2）提高财务杠杆；")]),t("div",{staticClass:"h-r-item"},[e._v("3）降低股利支付率；")]),t("div",{staticClass:"h-r-item"},[e._v("4）非核心业务剥离；")]),t("div",{staticClass:"h-r-item"},[e._v("5）寻求外购（自制改外购）；")]),t("div",{staticClass:"h-r-item"},[e._v("6）兼并购。")])]),t("div",{staticClass:"h-r-title"},[t("br"),e._v(" 当实际增长率小于可持续增长率： "),t("br")]),t("div",{staticClass:"ul"},[t("div",{staticClass:"h-r-item"},[e._v("1）忽视问题的存在； ")]),t("div",{staticClass:"h-r-item"},[e._v("2）还给股东；")]),t("div",{staticClass:"h-r-item"},[e._v("3）购买增长（开辟多元化经营）")])])])],2),t("div",{staticClass:"g-bg-gray gray-wrap"},e._l(e.dataList,(function(n){return t("div",{key:n.indexId},[t("BiBoxElement",{attrs:{queryData:n,companyInfo:e.companyInfo}})],1)})),0)],1)},r=[],d=t("a1d2"),a=t("de03"),u=t("8da4"),o=t("7044"),c={mixins:[a["a"]],components:{HeaderBox:d["a"],BiBoxElement:u["a"]},props:{companyInfo:{type:Object,default:function(){return{}}}},data:function(){return{}},created:function(){this._initUIView()},methods:{_initUIView:function(){this.mixinGetBiIndexList(o["x"])}}},s=c,m=(t("cd7a"),t("2877")),p=Object(m["a"])(s,i,r,!1,null,"49ffdbea",null);n["default"]=p.exports},7044:function(e,n,t){"use strict";t.d(n,"w",(function(){return i})),t.d(n,"r",(function(){return r})),t.d(n,"v",(function(){return d})),t.d(n,"m",(function(){return a})),t.d(n,"n",(function(){return u})),t.d(n,"h",(function(){return o})),t.d(n,"i",(function(){return c})),t.d(n,"u",(function(){return s})),t.d(n,"f",(function(){return m})),t.d(n,"d",(function(){return p})),t.d(n,"z",(function(){return x})),t.d(n,"a",(function(){return I})),t.d(n,"c",(function(){return f})),t.d(n,"b",(function(){return v})),t.d(n,"g",(function(){return y})),t.d(n,"y",(function(){return l})),t.d(n,"o",(function(){return h})),t.d(n,"s",(function(){return L})),t.d(n,"t",(function(){return T})),t.d(n,"e",(function(){return N})),t.d(n,"k",(function(){return b})),t.d(n,"l",(function(){return C})),t.d(n,"j",(function(){return g})),t.d(n,"A",(function(){return _})),t.d(n,"p",(function(){return k})),t.d(n,"x",(function(){return B})),t.d(n,"q",(function(){return w}));var i={indexList:[1,2,3,4,5,6],reportType:1,repName:"应对战略风险能力"},r={indexList:[7,6,9,3,1],reportType:2,repName:"应对市场风险能力"},d={indexList:[4,9,10,92,51,13],reportType:3,repName:"应对运营风险能力"},a={indexList:[4,14,3,7,6,15],reportType:4,repName:"应对成长风险能力"},u={indexList:[16,17,5,10],reportType:5,repName:"应对行业风险能力"},o={indexList:[6,9,18,19,20,16,21,22],reportType:6,repName:"业务抗风险能力"},c={indexList:[7,23,92,24,25,26,2,27],reportType:7,repName:"发展能力"},s={indexList:[6,9,28,29,21,30,31,32,33,34],reportType:8,repName:"盈利能力"},m={indexList:[35,17,36,37,16,38,39,40,41,21,22,42,43],reportType:9,repName:"偿债能力"},p={indexList:[44,45,46,47,48,49,50],reportType:10,repName:"成本管控能力"},x={indexList:[51,3,4,52,53,54,55,56,57,58],reportType:11,repName:"营运能力"},I={reportType:13,indexList:[14,71,72,16,17,36],repName:"资金链断裂分析"},f={reportType:14,indexList:[74,75,76,77,78],repName:"现金流健康度分析"},v={reportType:15,indexList:[79,80,81,82,16,83],repName:"资产结构分析"},y={reportType:16,indexList:[16,84,85,86,87,89,90,91,92,93,94],repName:"负债及权益结构分析"},l={reportType:17,indexList:[62],repName:"营运资本分析WC"},h={reportType:18,indexList:[95],repName:"再投资率分析RR"},L={reportType:19,indexList:[96],repName:"非现金营运资本分析"},T={reportType:20,indexList:[97],repName:"非现金运营资本"},N={reportType:21,indexList:[65,5,66,68,9,10],repName:"杜邦分析"},b={reportType:22,indexList:[105],repName:"企业价值/息税前利润分析"},C={reportType:23,indexList:[106],repName:"息税前利润/企业价值比分析EBIT/EV"},g={reportType:24,indexList:[107],repName:"企业价值/息税折旧摊销前利润分析"},_={reportType:25,indexList:[10],repName:"总资产周转率"},k={reportType:25,indexList:[108],repName:"租赁或租赁效应投入资本回报率分析LEROIC"},B={reportType:27,indexList:[109],repName:"可持续增长率分析sgr"},w={reportType:28,indexList:[110],repName:"劳动生产率分析"}},"753d":function(e,n,t){"use strict";n["a"]={1:{name:"市场占有率",indexId:1,hideIndustry:!0},2:{name:"研发费用率",indexId:2,hide:!0},3:{name:"存货周转率",indexId:3,hide:!0},4:{name:"应收账款周转率",indexId:4,hide:!0},5:{name:"总资产回报率",indexId:5},6:{name:"毛利率",indexId:6},7:{name:"营业收入增长率",indexId:7},8:{name:"净利润率",indexId:8},9:{name:"净利润率",indexId:9},10:{name:"总资产周转率",indexId:10},11:{name:"总资产增长率",indexId:11},12:{name:"人均营业收入",indexId:12},13:{name:"人均净利润",indexId:13},14:{name:"销售现金比率",indexId:14},15:{name:"期间费用率",indexId:15},16:{name:"资产负债率",indexId:16},17:{name:"流动比率",indexId:17,hide:!0},18:{name:"经营安全率",indexId:18},19:{name:"经营风险系数",indexId:19},20:{name:"财务风险系数",indexId:20},21:{name:"净资产收益率",indexId:21},22:{name:"实际利息率",indexId:22},23:{name:"净利润增长率",indexId:23},24:{name:"总资产增长率",indexId:24},25:{name:"经营净现金流增长率",indexId:25},26:{name:"可动用资金总额增长率",indexId:26},27:{name:"流动资产周转速度增长率",indexId:27},28:{name:"营业利润率",indexId:28},29:{name:"成本费用利润率",indexId:29},30:{name:"资本利润率",indexId:30},31:{name:"总资产报酬率",indexId:31},32:{name:"盈余现金保障倍数",indexId:32},33:{name:"内部经营资产收益率",indexId:33},34:{name:"对外投资收益率",indexId:34},35:{name:"自由现金流",indexId:35},36:{name:"速动比率",indexId:36,hide:!0},37:{name:"利息保障倍数",indexId:37},38:{name:"现金流动资产比",indexId:38,hide:!0},39:{name:"经营还债能力",indexId:39,hide:!0},40:{name:"债务偿还率",indexId:40,hide:!0},41:{name:"现金流动负债率",indexId:41,hide:!0},42:{name:"产权比率",indexId:42},43:{name:"长期资产适合率",indexId:43},44:{name:"主营业务成本率",indexId:44},45:{name:"主营业务成本利润率",indexId:45},46:{name:"销售费用利润率",indexId:46},47:{name:"管理费用率",indexId:47},48:{name:"财务费用率",indexId:48,hide:!0},49:{name:"管理费用资产比",indexId:49},50:{name:"人均管理资产",indexId:50},51:{name:"人均销售收入",indexId:51},52:{name:"应付账款周转率",indexId:52,hide:!0},53:{name:"现金周期",indexId:53},54:{name:"营业周期",indexId:54},55:{name:"流动资产周转天数",indexId:55,hide:!0},56:{name:"总资产周转天数",indexId:56},57:{name:"固定资产周转天数",indexId:57},58:{name:"资产现金回收率",indexId:58},59:{name:"营运资本收入比率",indexId:59,hide:!0},60:{name:"固定比率",indexId:60},61:{name:"营运资金需求增长率",indexId:61},62:{name:"营运资本",indexId:62,hide:!0},63:{name:"营运资金需求",indexId:63},64:{name:"现金支付能力",indexId:64},65:{name:"净资产收益率（ROE）",indexId:65},66:{name:"权益乘数",indexId:66},67:{name:"期间费用",indexId:67},68:{name:"归属于母公司股东净利润占比",indexId:68},69:{name:"应收账款",indexId:69},71:{name:"应收账款与总资产比重",indexId:71},72:{name:"应收账款与流动资产比重",indexId:72},74:{name:"现金总负债比率",indexId:74},75:{name:"全部资产现金回收率",indexId:75},76:{name:"现金再投资比率",indexId:76},77:{name:"到期债务本息偿付比率",indexId:77},78:{name:"现金购销比率",indexId:78},79:{name:"流动资产总资产比",indexId:79},80:{name:"存货比率",indexId:80},81:{name:"固定资产与股东权益比率",indexId:81},82:{name:"股东权益比率",indexId:82},83:{name:"长期负债比率",indexId:83},84:{name:"自有资金负债率",indexId:84},85:{name:"负债经营率",indexId:85},86:{name:"流动负债比重",indexId:86},87:{name:"长期负债比重",indexId:87},89:{name:"有息负债比重",indexId:89},90:{name:"无息负债比重",indexId:90},91:{name:"积累比率",indexId:91},92:{name:"总资产增长率",indexId:92},93:{name:"投入资本比率",indexId:93},94:{name:"留存收益比率",indexId:94},95:{name:"再投资率分析",indexId:95},96:{name:"非现金营运资本分析",indexId:96},97:{name:"非现金营运资本/销售比（NCWC）",indexId:97},105:{name:"企业价值/息税前利润比分析（EV/EBIT）",indexId:105},106:{name:"息税前利润/企业价值分析（EBIT/EV）",indexId:106},107:{name:"企业价值倍数",indexId:107},109:{name:" 可持续增长率",indexId:109},110:{name:"劳动生产率",indexId:110}}},cd7a:function(e,n,t){"use strict";t("d9e9")},d9e9:function(e,n,t){},de03:function(e,n,t){"use strict";t("d81d");var i=t("5530"),r=t("753d");n["a"]={props:{companyInfo:{type:Object,default:function(){return{}}}},data:function(){return{dataList:[]}},methods:{mixinGetBiIndexList:function(e){this.dataList=e.indexList.map((function(n){var t=r["a"][n];return Object(i["a"])(Object(i["a"])({},t),{},{reportType:e.reportType,repName:e.repName})}))}}}}}]);