(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-272fc6fa"],{"118f":function(t,a,e){"use strict";e("576b")},1411:function(t,a,e){"use strict";e("bd825")},2390:function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"enterprise-risk-position-report g-bg-white"},[t._t("title"),t.$slots.title?t._e():e("div",{staticClass:"history-risk-tilte"},[t._v(" 阿尔法财AI分析："+t._s(t.companyInfo.companyName)+"未来风险概率在市场位置 "),e("span",{staticClass:"g-head-year"},[t._v("（基于"+t._s(t.$startYear)+" - "+t._s(t.$endYear)+"数据）")])]),e("riskPosition",{attrs:{health:t.health,life:t.life,demand:t.demand,costs:t.costs,companyInfo:t.companyInfo},on:{itemTap:t.itemTap}}),e("div",{staticClass:"main"},[e("healthyRiskPosition",{ref:"risk",attrs:{contrastType:t.contrastType,companyInfo:t.companyInfo}}),e("fourDimensionAndCompetitor",{attrs:{companyInfo:t.companyInfo}})],1)],2)},i=[],s=(e("4160"),e("b0c0"),e("159b"),function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"risk-pos"},[e("div",{staticClass:"top-banner"},[t._m(0),e("div",{staticClass:"risk-wrap"},[e("div",{staticClass:"risk risk1"},[e("div",{staticClass:"info1"},[e("span",[t._v("健康病变风险")]),e("span",[t._v(t._s(t.health))])]),e("div",{staticClass:"info2"},[t._v("在市场排"+t._s(t.healthRank)+"名")])]),e("div",{staticClass:"risk risk2"},[e("div",{staticClass:"info1"},[e("span",[t._v("生存危机风险")]),e("span",[t._v(t._s(t.life))])]),e("div",{staticClass:"info2"},[t._v("在市场排"+t._s(t.lifeRank)+"名")])]),e("div",{staticClass:"risk risk3"},[e("div",{staticClass:"info1"},[e("span",[t._v("错失客户风险")]),e("span",[t._v(t._s(t.demand))])]),e("div",{staticClass:"info2"},[t._v("在市场排"+t._s(t.demandRank)+"名")])]),e("div",{staticClass:"risk risk4"},[e("div",{staticClass:"info1"},[e("span",[t._v("成本失控风险")]),e("span",[t._v(t._s(t.costs))])]),e("div",{staticClass:"info2"},[t._v("在市场排"+t._s(t.costsRank)+"名")])])])]),e("div",{staticClass:"foot"},[e("span",{staticClass:"label-btn",class:[1===t.active?"active":""],on:{click:function(a){return t.itemTap(1)}}},[t._v("对比行业")]),e("span",{staticClass:"label-btn",class:[2===t.active?"active":""],on:{click:function(a){return t.itemTap(2)}}},[t._v("对比全部市场")])])])}),r=[function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"img"},[e("img",{attrs:{src:"/images/2-8-2.png",alt:""}})])}],o=(e("b64b"),e("96cf"),e("1da1")),c=e("1c31"),u={props:{companyInfo:Object,health:String,life:String,demand:String,costs:String},data:function(){return{active:1,healthRank:1,costsRank:1,demandRank:1,lifeRank:1,riskArr:["health","costs","demand","life"]}},created:function(){this.queryTap()},methods:{itemTap:function(t){this.active=t,this.$emit("itemTap",t)},queryTap:function(){var t={companyId:this.companyInfo.companyId,industryCode:this.companyInfo.industryCode};this._apiGetRankData(t)},_initUIView:function(){this._apiGetRankData()},formatResData:function(t){if(t&&t.length>0){var a={};t.forEach((function(t){Object.keys(t).forEach((function(e){"name"===e&&(a[t[e]]=t.rank)}))})),this.healthRank=a["health"],this.lifeRank=a["life"],this.demandRank=a["demand"],this.costsRank=a["costs"]}},_apiGetRankData:function(t){var a=this;return Object(o["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,Object(c["f"])(t);case 3:n=e.sent,a.formatResData(n),e.next=9;break;case 7:e.prev=7,e.t0=e["catch"](0);case 9:case"end":return e.stop()}}),e,null,[[0,7]])})))()}}},l=u,d=(e("118f"),e("2877")),f=Object(d["a"])(l,s,r,!1,null,"002c6d5f",null),h=f.exports,p=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"healthy-risk-position"},[e("div",{staticClass:"main-t"},[e("span",[t._v(t._s(t.companyInfo.companyName))]),e("span",[t._v("未来"+t._s(t.riskTitle))]),e("span",[t._v("维度风险概率在"+t._s(t.contrastTypeText)+"位置")])]),e("div",{staticClass:"wrap"},[e("el-row",{attrs:{gutter:40}},[e("el-col",{attrs:{span:12,offset:0}},[e("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative"},[e("EchartLine",{ref:"chart",attrs:{title:t.companyInfo.companyTxt,yTitle:"企业数量",smooth:"",xdata:t.xdata,data:t.data,options:t.options}})],1)]),e("el-col",{attrs:{span:12,offset:0}},[e("div",{staticClass:"charts-form"},[e("div",{staticClass:"form-box"},[e("div",{staticClass:"form-group"},[e("BenchmarkingCompany",{attrs:{companyInfo:t.companyInfo,onlyIndustry:1===t.contrastType},on:{change:t.companyChange}})],1),e("yearRange",{attrs:{disabled:!0},on:{changeDate:t.changeDate}}),e("riskTab",{on:{riskChange:t.riskChange}}),e("div",{staticClass:"foot-info"},[e("p",{staticClass:"foot-info-t"}),e("p",{staticClass:"foot-info-main"},[t._v(" 对标"+t._s(t.contrastTypeText)+"位置，才知风险大小。 "),e("span",[t._v(" "+t._s(t.companyInfo.companyName)+"未来"+t._s(this.riskTitle)+"风险概率是"+t._s(t.resultData.x)+"%，")]),e("span",[t._v("超过"+t._s(t.contrastTypeText)+"中 "+t._s(t.resultData.y)+"%的公司。")])])])],1)])])],1)],1)])},m=[],v=(e("99af"),e("a15b"),e("d81d"),e("fb6a"),e("a9e3"),e("b680"),e("2909")),y=e("a5a5"),g=e("46da"),b=e("f36c"),k=e("5801"),x=e("2592"),_=e("f4dd"),C=e("c5b0"),R=e("f348"),T=e("a741"),w={mixins:[x["a"],T["a"]],props:{contrastType:{type:Number,default:1}},components:{yearRange:y["a"],riskTab:g["a"],BenchmarkingCompany:b["a"],EchartLine:k["a"]},data:function(){var t=this.$colors;return{enumRiskObject:C["d"],xdata:[],data:[],benchYear:0,queryData:{yearRangeStart:"",yearRangeEnd:"",riskList:["health"],companyList:[]},options:{},riskInfo:{},resultData:{x:"",y:""},loading:!1,colors:t}},computed:{contrastTypeText:function(){var t=["","本行业","市场"];return t[this.contrastType]},riskTitle:function(){return this.enumRiskObject[this.queryData.riskList[0]]}},created:function(){this._initUIView()},methods:{openTap:function(){},companyChange:function(t){this.queryData.companyList=t,this.queryTap()},riskChange:function(t){this.queryData.riskList=[t.val],this.queryTap()},changeDate:function(t,a){1===t?this.queryData.yearRangeStart=a:this.queryData.yearRangeEnd=a,this.queryTap()},queryTap:function(){this._reqCompanyList=[this.companyInfo].concat(Object(v["a"])(this.queryData.companyList)),this.benchYear=Object(R["i"])(this._reqCompanyList,this.$endTime);var t=this.mixinReqOneTime();this._apiGetRiskDistribution({companyList:this._reqCompanyList,contrastType:this.contrastType,benchYear:this.benchYear,industryCode:this.companyInfo.industryCode,dateType:t.dateType})},updateChart:function(){var t=this.$refs.chart;t&&t._initCanvas()},formatResdata:function(t){var a=this,e={},n=[this.companyInfo].concat(Object(v["a"])(this.queryData.companyList));n.forEach((function(t){e[t.code]=t}));var i=this.getXdata(),s=[],r=[],o=i.map((function(){return null})),c=null;for(var u in t)if(Object.hasOwnProperty.call(t,u)){var l=this.queryData.riskList[0];l="life"===l?"default":l,c||(c=Object.keys(t[u][l])[0]);var d=t[u][l][c],f=Math.round(+d.val),h=+d.pos;if(s.push({x:f,y:h}),e[u]){var p=o.slice();p[f]=h;var m={name:e[u].companyName,_data:h,_x:+d.val,data:p,symbolSize:12,symbol:"circle",itemStyle:{color:"#5FC054"}},y=u===this.companyInfo.code;y?r.unshift(m):r.push(m),y&&(this.resultData={x:+d.val,y:h})}}r.forEach((function(t,e){t.itemStyle.color=a.colors[e]}));var g=this.getLineValues(s);this.data=[{name:"",data:g,showSymbol:!1}].concat(r),this.xdata=i},getLineValues:function(t){var a={x:0,y:0},e=[];t.sort((function(t,a){return t.x-a.x}));var n=0;return t.forEach((function(t){var i=(t.y-a.y)/(t.x-a.x);while(n<=t.x){var s=(n-a.x)*i+a.y;s=Number(s.toFixed(2)),e.push(s),n++}a=t})),e},getXdata:function(){for(var t=[],a=20,e=0;e<=100;e++)t.push({value:e,textStyle:{color:e%a===0?"":"transparent"}});return t},_initUIView:function(){this._initOptions()},_initOptions:function(){var t=this;this.options={grid:{top:60,right:110},legend:{itemHeight:14,icon:this.$echartsLegendIcon.circle},tooltip:{position:this.$tipPosition(45,["right","top"],[-80,0]),trigger:"item",borderColor:"white",formatter:function(a){var e=t.data[a.seriesIndex],n=["".concat(e.name),"".concat(t.riskTitle,"风险概率 ").concat(e._x,"%"),"超过".concat(t.contrastTypeText,"中 ").concat(e._data,"% 的公司")];return n.join("<br/>")}},xAxis:{type:"category",name:"企业风险概率%",nameStyle:{align:"left"},axisLabel:{interval:0},min:0,boundaryGap:!0,axisLine:{lineStyle:{color:"#999999"},show:!0},axisTick:{show:!1},splitLine:{show:!1}},yAxis:{type:"value",name:"超过".concat(this.contrastTypeText,"公司%"),nameLocation:"end",nameTextStyle:{padding:[0,0,0,10]},axisLine:{lineStyle:{color:"#999999"},show:!0},splitLine:{show:!1}}}},_apiGetRiskDistribution:function(t){var a=this;return Object(o["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,a.loading=!0,e.next=4,Object(_["k"])(t);case 4:n=e.sent,a.formatResdata(n.data),a._initOptions(),e.next=13;break;case 9:e.prev=9,e.t0=e["catch"](0),a.xdata=[],a.data=[];case 13:a.loading=!1;case 14:case"end":return e.stop()}}),e,null,[[0,9]])})))()}}},I=w,D=(e("5dfc"),Object(d["a"])(I,p,m,!1,null,"f9123a66",null)),O=D.exports,j=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"four-dimension-and-competitor"},[e("div",{staticClass:"chart-title"},[t._v(" "+t._s(t.companyInfo.companyName)+"四个维度风险概率与竞争者对比 ")]),e("div",{staticClass:"flex-wrap"},[e("el-row",{attrs:{gutter:40}},[e("el-col",{attrs:{span:12,offset:0}},[e("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.vLoading,expression:"vLoading"}],staticClass:"charts-l"},[e("EchartColumn",{ref:"columnCharts",attrs:{height:350,title:t.companyInfo.companyTxt,xdata:t.xdata,data:t.data,options:t.options}})],1)]),e("el-col",{attrs:{span:12,offset:0}},[e("div",{staticClass:"charts-form"},[e("div",{staticClass:"form-box"},[e("div",{staticClass:"form-group"},[e("BenchmarkingCompany",{on:{change:t.companyChange}})],1),e("div",{staticClass:"form-group"},[e("yearRange",{attrs:{disabled:!0},on:{changeDate:t.changeDate}})],1),e("div",{staticClass:"foot-txt"},[t._v(" AI赋能知己知彼、聚焦超越。挑选相同时间段、任意多个行业龙头或竞争对手，对标他们未来1~5年的风险概率。 ")])])])])],1)],1)])},L=[],q=e("33c3"),E=e("623e"),S={mixins:[x["a"],E["a"]],components:{YearRange:y["a"]},data:function(){var t=this.$colors;return{colors:t}},created:function(){this._initUIView()},methods:{openTap:function(){},changeDate:function(t,a){1===t?this.queryData.yearRangeStart=a:this.queryData.yearRangeEnd=a,this.queryTap()},_initUIView:function(){this._initOptions()},_initOptions:function(){this.options={yAxis:{name:"风险概率/%",axisLine:{show:!0}},grid:{right:100},tooltip:{position:this.$tipPosition(45,["right","top"],[-80,0]),formatter:function(t){return Object(q["j"])(t,"%")},extraCssText:"width: 260px;"}}}}},$=S,F=(e("1411"),Object(d["a"])($,j,L,!1,null,"140c6d22",null)),N=F.exports,z=e("6e92"),G={mixins:[z["a"]],components:{riskPosition:h,healthyRiskPosition:O,fourDimensionAndCompetitor:N},data:function(){return{health:"",life:"",demand:"",costs:"",contrastType:1}},methods:{handleResult:function(){var t=this;this.data.forEach((function(a){t[a.name]=Math.round(100*a.value)+"%"}))},itemTap:function(t){var a=this;this.contrastType=t,this.$nextTick((function(){var t=a.$refs.risk;t&&t.queryTap()}))}}},A=G,V=(e("adb3"),Object(d["a"])(A,n,i,!1,null,"aabf4a54",null));a["default"]=V.exports},"46da":function(t,a,e){"use strict";var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"risk-tab mt20"},[e("div",{staticClass:"form-group"},[e("div",{staticClass:"label-t"},[t._v("风险维度")]),e("div",{staticClass:"form-cont"},t._l(t.riskList,(function(a,n){return e("a",{key:n,staticClass:"risk-btn",class:t.curRisk.name==a.name?"on":"",attrs:{href:"javascript:void(0);"},on:{click:function(e){return t.handleClick(a)}}},[t._v(t._s(a.name))])})),0)])])},i=[],s={data:function(){return{curRisk:{name:"健康病变",val:"health"},riskList:[{name:"健康病变",val:"health"},{name:"生存危机",val:"life"},{name:"错失客户",val:"demand"},{name:"成本失控",val:"costs"}]}},methods:{handleClick:function(t){this.curRisk=t,this.$emit("riskChange",this.curRisk)}}},r=s,o=(e("bb82"),e("2877")),c=Object(o["a"])(r,n,i,!1,null,"3a40a027",null);a["a"]=c.exports},"4ec9":function(t,a,e){"use strict";var n=e("6d61"),i=e("6566");t.exports=n("Map",(function(t){return function(){return t(this,arguments.length?arguments[0]:void 0)}}),i)},"576b":function(t,a,e){},"5dfc":function(t,a,e){"use strict";e("71d8")},"623e":function(t,a,e){"use strict";e("99af"),e("d81d"),e("a9e3"),e("b680"),e("96cf");var n=e("1da1"),i=e("5530"),s=e("2909"),r=e("8943"),o=e("f36c"),c=e("f4dd"),u=e("a741");a["a"]={mixins:[u["a"]],components:{EchartColumn:r["a"],BenchmarkingCompany:o["a"]},data:function(){return{queryData:{yearRangeStart:"",yearRangeEnd:"",companyList:[]},colors:["#0F9FFD","#444347","#808080","#D8D7DE"],xdata:[],data:[],options:{},vLoading:!1}},methods:{companyChange:function(t){this.queryData.companyList=t,this.queryTap()},queryTap:function(){var t=this.queryData.companyList.map((function(t){return{companyId:t.id,companyName:t.companyName,code:t.code}})),a=this.mixinReqTime([this.companyInfo].concat(Object(s["a"])(this.queryData.companyList)));this._apiGetCaseRisk(Object(i["a"])({companyList:[this.companyInfo].concat(Object(s["a"])(t))},a))},formatResdata:function(t){var a=this,e=[],n=t.map((function(t,n){return{name:t.companyName,data:t.latitudeSummaries.map((function(t){return 0===n&&e.push(t.subject),Number((100*t.value).toFixed(2))})),color:a.colors[n],barWidth:10,itemStyle:{normal:{}}}}));this.data=n,this.xdata=e},_apiGetCaseRisk:function(t){var a=this;return Object(n["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,a.vLoading=!0,e.next=4,Object(c["f"])(t);case 4:n=e.sent,a.formatResdata(n),e.next=10;break;case 8:e.prev=8,e.t0=e["catch"](0);case 10:a.vLoading=!1;case 11:case"end":return e.stop()}}),e,null,[[0,8]])})))()}}}},6566:function(t,a,e){"use strict";var n=e("9bf2").f,i=e("7c73"),s=e("e2cc"),r=e("0366"),o=e("19aa"),c=e("2266"),u=e("7dd0"),l=e("2626"),d=e("83ab"),f=e("f183").fastKey,h=e("69f3"),p=h.set,m=h.getterFor;t.exports={getConstructor:function(t,a,e,u){var l=t((function(t,n){o(t,l,a),p(t,{type:a,index:i(null),first:void 0,last:void 0,size:0}),d||(t.size=0),void 0!=n&&c(n,t[u],{that:t,AS_ENTRIES:e})})),h=m(a),v=function(t,a,e){var n,i,s=h(t),r=y(t,a);return r?r.value=e:(s.last=r={index:i=f(a,!0),key:a,value:e,previous:n=s.last,next:void 0,removed:!1},s.first||(s.first=r),n&&(n.next=r),d?s.size++:t.size++,"F"!==i&&(s.index[i]=r)),t},y=function(t,a){var e,n=h(t),i=f(a);if("F"!==i)return n.index[i];for(e=n.first;e;e=e.next)if(e.key==a)return e};return s(l.prototype,{clear:function(){var t=this,a=h(t),e=a.index,n=a.first;while(n)n.removed=!0,n.previous&&(n.previous=n.previous.next=void 0),delete e[n.index],n=n.next;a.first=a.last=void 0,d?a.size=0:t.size=0},delete:function(t){var a=this,e=h(a),n=y(a,t);if(n){var i=n.next,s=n.previous;delete e.index[n.index],n.removed=!0,s&&(s.next=i),i&&(i.previous=s),e.first==n&&(e.first=i),e.last==n&&(e.last=s),d?e.size--:a.size--}return!!n},forEach:function(t){var a,e=h(this),n=r(t,arguments.length>1?arguments[1]:void 0,3);while(a=a?a.next:e.first){n(a.value,a.key,this);while(a&&a.removed)a=a.previous}},has:function(t){return!!y(this,t)}}),s(l.prototype,e?{get:function(t){var a=y(this,t);return a&&a.value},set:function(t,a){return v(this,0===t?0:t,a)}}:{add:function(t){return v(this,t=0===t?0:t,t)}}),d&&n(l.prototype,"size",{get:function(){return h(this).size}}),l},setStrong:function(t,a,e){var n=a+" Iterator",i=m(a),s=m(n);u(t,a,(function(t,a){p(this,{type:n,target:t,state:i(t),kind:a,last:void 0})}),(function(){var t=s(this),a=t.kind,e=t.last;while(e&&e.removed)e=e.previous;return t.target&&(t.last=e=e?e.next:t.state.first)?"keys"==a?{value:e.key,done:!1}:"values"==a?{value:e.value,done:!1}:{value:[e.key,e.value],done:!1}:(t.target=void 0,{value:void 0,done:!0})}),e?"entries":"values",!e,!0),l(a)}}},"6e92":function(t,a,e){"use strict";e("99af"),e("d81d"),e("b0c0"),e("ac1f"),e("1276"),e("96cf");var n=e("1da1"),i=e("b85c"),s=e("5530"),r=e("f4dd"),o=e("44b3");a["a"]={mixins:[o["a"]],data:function(){return{data:[],loading:!1}},created:function(){this.queryTap()},methods:{queryTap:function(){this._apiGetRiskSuggest({companyId:this.companyInfo.companyId})},formatValues:function(t){var a={health:{color:"#7195F2"},life:{color:"#17CC37"},demand:{color:"#FBC800"},costs:{color:"#FF0000"}},e=t.map((function(t){return Object(s["a"])(Object(s["a"])(Object(s["a"])({},t),a[t.name]),{},{label:Math.round(100*t.value)})}));this.data=e,this.formatResdata()},formatResdata:function(){var t,a=0,e=Object(i["a"])(this.data);try{for(e.s();!(t=e.n()).done;){var n=t.value;a++;var s,r=0,o=Object(i["a"])(n.factorySummries);try{for(o.s();!(s=o.n()).done;){var c=s.value;r++;var u=c.subject.split("，");c.title=u[0],c.subTitle=u[1]||"",c.supperTilte=n.subject,c.id="".concat(a,"-").concat(r)}}catch(l){o.e(l)}finally{o.f()}}}catch(l){e.e(l)}finally{e.f()}},_apiGetRiskSuggest:function(t){var a=this;return Object(n["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,a.loading=!0,e.next=4,Object(r["l"])(t);case 4:n=e.sent,a.formatValues(n.latitudeSummaries),a.handleResult&&a.handleResult(),e.next=11;break;case 9:e.prev=9,e.t0=e["catch"](0);case 11:a.loading=!1;case 12:case"end":return e.stop()}}),e,null,[[0,9]])})))()}}}},"71d8":function(t,a,e){},"96fb":function(t,a,e){},adb3:function(t,a,e){"use strict";e("d882")},bb82:function(t,a,e){"use strict";e("96fb")},bd825:function(t,a,e){},c5b0:function(t,a,e){"use strict";e.d(a,"d",(function(){return n})),e.d(a,"b",(function(){return i})),e.d(a,"c",(function(){return s})),e.d(a,"g",(function(){return r})),e.d(a,"e",(function(){return o})),e.d(a,"f",(function(){return c})),e.d(a,"h",(function(){return u})),e.d(a,"a",(function(){return l}));e("4ec9"),e("d3b7"),e("3ca3"),e("ddb0");var n={health:"健康病变",life:"生存危机",demand:"错失客户",costs:"成本失控"},i=[{name:"健康病变",key:"health"},{name:"生存危机",key:"default"},{name:"错失客户",key:"demand"},{name:"成本失控",key:"costs"}],s={health:"“健康病变”：该维度用来评估行业某阶段，企业平均可持续发展被隐藏的不健康因素破坏的风险概率。",life:"“生存危机”：该维度用来评估行业在短期内,企业平均出现无法履行短期财务义务的风险概率。",demand:"“错失客户”：该维度用来评估行业内企业产品或解决方案因不匹配客户需而阻碍发展的风险概率。",costs:"“成本失控”：该维度用来评估行业内企业平均运营成本及结构因缺陷而影响成长的风险概率。"},r=function(t){var a=(new Map).set("health","健康病变").set("life","生存危机").set("demand","错失客户").set("costs","成本失控");return a.get(t)},o={1:"20%以下",2:"20% ~ 40%",3:"40% ~ 60%",4:"60% ~ 80%",5:"80%以上"},c={1:"风险概率<20%",2:"20%≤风险概率<40%",3:"40%≤风险概率<60%",4:"60%≤风险概率<80%",5:"80%≤风险概率"},u=function(t){var a=(new Map).set("1","风险概率<20%").set("2","20%≤风险概率<40%").set("3","40%≤风险概率<60%").set("4","60%≤风险概率<80%").set("5","80%≤风险概率");return a.get(t)},l=["#7195F2","#5FC054","#F8CB22","#ED5C4E"]},d882:function(t,a,e){}}]);