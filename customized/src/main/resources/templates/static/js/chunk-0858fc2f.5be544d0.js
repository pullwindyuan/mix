(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0858fc2f"],{"54be":function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"portrait-item"},[e("div",{staticClass:"portrait-item-title"},[t._v(t._s(t.scoreData.title))]),e("div",{staticClass:"p-content"},[e("div",{staticClass:"sub-title"},[t._v("AI分析结论")]),e("div",{staticClass:"p-content-line"},[e("el-row",{attrs:{gutter:20}},[e("el-col",{attrs:{span:12,offset:0}},[e("StartBox",{attrs:{industryRank:t.scoreData.industryRank,score:t.scoreData.scoreTotal,companyInfo:t.companyInfo,levelList:t.scoreData.riskList,sort:t.scoreData.sort,scoreMarket:t.scoreData.marketScore,industryRankMarket:t.scoreData.marketRank,rankTxt:t.scoreData.rankTxt,data:t.scoreData}})],1),e("el-col",{attrs:{span:12,offset:0}},[e("BiIndexRadar",{attrs:{companyInfo:t.companyInfo,soreTypeId:t.scoreData.scoreTypeId,reportData:t.scoreData.radarData.reportData,title:t.scoreData.radarData.title,radarData:t.scoreData.caseCompanyScoreList}})],1)],1)],1),e("div",{staticClass:"p-content-line"},[e("el-row",{attrs:{gutter:20}},[e("el-col",{attrs:{span:12,offset:0}},[e("BiIndexCompare",{attrs:{companyInfo:t.companyInfo,soreTypeId:t.scoreData.scoreTypeId,reportData:t.scoreData.compareData.reportData,title:t.scoreData.compareData.title,scoreData:t.scoreData}})],1),e("el-col",{attrs:{span:12,offset:0}},[e("BiIndexIndustryRank",{attrs:{companyInfo:t.companyInfo,soreTypeId:t.scoreData.scoreTypeId,reportData:t.scoreData.industryData.reportData,title:t.scoreData.industryData.title}})],1)],1)],1),e("div",{staticClass:"p-content-line"},[e("el-row",{attrs:{gutter:20}},[e("el-col",{attrs:{span:12,offset:0}},[e("AllIndustryRank",{attrs:{companyInfo:t.companyInfo,scoreTypeId:t.scoreData.scoreTypeId,infoTitle:t.scoreData.industryTxt}})],1),e("el-col",{attrs:{span:12,offset:0}},[e("AllMarketRank",{attrs:{companyInfo:t.companyInfo,scoreTypeId:t.scoreData.scoreTypeId,infoTitle:t.scoreData.marketTxt}})],1)],1)],1)])])},r=[],i=e("e7de"),o=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"radar-chart-container"},[e("ChartBox",{attrs:{title:t.title},on:{moreTap:t.mixinMoreTap}},[t.radarData&&t.radarData.length>0?e("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[e("EchartRadar2",{attrs:{layer:"",layerData:t.layerData,options:t.options,xdata:t.xdata,data:t.data,height:265}}),e("LegendBox",{attrs:{name1:"在本行业"+t.title,name2:"",name3:"在市场"+t.title}})],1):e("div",{staticClass:"empty-box"},[e("img",{staticClass:"img",attrs:{src:"/images/empty.png",alt:""}}),e("div",{staticClass:"info"},[t._v("没有数据哦")])])])],1)},s=[],c=(e("99af"),e("4de4"),e("4160"),e("a15b"),e("d81d"),e("fb6a"),e("b0c0"),e("a9e3"),e("159b"),e("2909")),l=e("2abf"),d=e("7f89"),u=e("a6e6"),p=e("3501"),m=e("f870"),f=e("33c3"),y={mixins:[p["a"]],props:{title:{type:String,default:""},soreTypeId:{type:Number,default:4},reportData:{type:Object,default:function(){return{}}},radarData:{type:Array,default:function(){return[]}}},components:{ChartBox:l["a"],EchartRadar2:m["a"],LegendBox:d["a"]},data:function(){return{xdata:[],data:[],options:{},loading:!1,layerData:{}}},created:function(){this._initUIView()},methods:{formatResdata:function(t){var a=this;if(!Array.isArray(t)||0===t.length)return this.xdata=[],void(this.data=[]);var e=t.filter((function(t){return t.scoreSubType}));e.sort((function(t,a){return t.scoreSubType-a.scoreSubType}));var n=100,r=0,i=[],o=[],s=[];e.forEach((function(t){o.push(t.score),s.push(t.marketScore),i.push({name:t.scoreSubName})})),i=i.map((function(t){var e=a.formatStr(t.name);return{name:e,_name:t.name,max:n,min:r}})),this.xdata=i,this.data=[{type:"radar",data:[{value:o,v:o,avg:s}],name:this.companyInfo.companyName,lineStyle:{color:"#fee05e"},areaStyle:{color:"white",opacity:.2},itemStyle:{color:"rgba(0, 0, 0, 0.5)"},symbolSize:2},{type:"radar",data:[{value:s,v:o,avg:s}],name:"市场平均",z:10,lineStyle:{color:"#FF00BA"},areaStyle:{color:"white",opacity:.2},itemStyle:{color:"rgba(0, 0, 0, 0.5)"},symbolSize:2}]},_initUIView:function(){this._initOptions(),this._initLayerData()},formatStr:function(t){var a=t&&t.length>=13?t.slice(0,8)+"\n"+t.slice(8):t;return a},_initLayerData:function(){var t={};for(var a in u["a"])if(Object.hasOwnProperty.call(u["a"],a)){var e=u["a"][a],n=this.formatStr(a);t[n]=e}this.layerData=t},_initOptions:function(){var t=this;this.options={tooltip:{formatter:function(a){var e="<strong>".concat(a.seriesName,"在本行业").concat(t.title,"</strong> / <strong>在市场").concat(t.title,"</strong>"),n=a.data,r=n.v,i=n.avg,o=t.xdata.map((function(t,a){var e=[{text:"".concat(t._name),value:"".concat(r[a])},{text:"市场",value:"".concat(i[a]),flex:"0 0 100px"}],n=Object(f["p"])(e);return n}));return[e].concat(Object(c["a"])(o)).join("")},extraCssText:"width: 420px;"}}}},watch:{radarData:{immediate:!0,handler:function(t){this.formatResdata(JSON.parse(JSON.stringify(t)))}}}},h=y,g=(e("7807"),e("2877")),v=Object(g["a"])(h,o,s,!1,null,"3b4dc4e4",null),x=v.exports,b=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("ChartBox",{attrs:{title:t.title},on:{moreTap:t.mixinMoreTap}},[e("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[e("div",{staticClass:"select"},[e("BenchmarkingCompany",{ref:"mark",attrs:{size:"mini",theme:"light",hideFirst:""},on:{change:t.companyChange}})],1),e("EchartColumn",{ref:"columnCharts",attrs:{height:265,title:"",bgcolor:"white",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},I=[],D=(e("7db0"),e("96cf"),e("1da1")),k=e("5530"),C=e("f36c"),S=e("8943"),L=e("1c31"),T=e("c9a5"),w=e("a741"),_=8,O=11,R={mixins:[p["a"],T["a"],w["a"]],props:{title:{type:String,default:""},soreTypeId:{type:Number,default:4},reportData:{type:Object,default:function(){return{}}},scoreData:{type:Object,default:function(){return{}}}},components:{ChartBox:l["a"],EchartColumn:S["a"],BenchmarkingCompany:C["a"]},data:function(){var t=this.$colors.map((function(t){return"".concat(t,"F2")}));return{radioList:[],companyList:[],active:{},options:{},xdata:[],data:[],loading:!1,resList:[],colors:t}},created:function(){this._initUIView()},methods:{companyChange:function(t){var a=t.map((function(t){return{companyId:t.id,code:t.code,companyName:t.companyName}}));this.companyList=[this.companyInfo].concat(Object(c["a"])(a)),this.queryValuesTap()},queryValuesTap:function(){var t=this.mixinReqBiOneTime();this._apiGetScoreList(Object(k["a"])({companys:this.companyList.map((function(t){return t.companyId})),soreTypeId:this.soreTypeId},t))},formatResdata:function(t){var a=this;if(this.scoreData.code&&0!==this.resList.length){this._initRadioList(this.scoreData.caseCompanyScoreList);var e=this.radioList.map((function(t){return t.name})),n=t.map((function(t,e){var n="",r=a.radioList.map((function(a){var e=t.caseCompanyScoreList.find((function(t){return t.scoreSubType===a.key}));n||(n=e.companyName);var r=e?e.score:"";return{value:r,itemStyle:{}}}));return{name:n,barWidth:_,data:r,itemStyle:{color:a.colors[e]}}}));this.xdata=e,this.data=n}},_initUIView:function(){this._initOptions()},_initOptions:function(){this.options={backgroundColor:"transparent",legend:{textStyle:{color:"rgba(0,0,0,0.65)"}},yAxis:{name:"",axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0,0,0,0.65)"}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0,0,0,0.65)",interval:0,fontSize:O,formatter:function(t){return t.length>4?"".concat(t.substring(0,4),"..."):t}}},grid:{right:0},tooltip:{formatter:function(t){var a=t[0].name,e=t.map((function(t){return"".concat(t.seriesName," ").concat(t.value,"分")}));return[a].concat(Object(c["a"])(e)).join("<br/>")}}}},_initRadioList:function(t){var a=[];t.forEach((function(t){t.scoreSubType&&a.push({name:t.scoreSubName,key:t.scoreSubType})})),a.sort((function(t,a){return t.key-a.key})),this.radioList=a},_apiGetScoreList:function(t){var a=this;return Object(D["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,a.loading=!0,e.next=4,Object(L["u"])(t);case 4:n=e.sent,a.formatResdata(n),e.next=10;break;case 8:e.prev=8,e.t0=e["catch"](0);case 10:a.loading=!1;case 11:case"end":return e.stop()}}),e,null,[[0,8]])})))()}}},N=R,B=(e("db42"),Object(g["a"])(N,b,I,!1,null,"ae96bc3e",null)),j=B.exports,A=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("ChartBox",{attrs:{title:t.title},on:{moreTap:t.mixinMoreTap}},[e("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"g-relative c-box g-change-el-input-bg-transpart",attrs:{"g-loading-background":"white","g-loading-text-color":"#303133"}},[e("div",{staticClass:"select-box"},[e("div",{staticClass:"label"},[t._v("选择指标")]),e("div",{staticClass:"value"},[e("el-select",{staticStyle:{width:"100%"},attrs:{"value-key":"key",size:"small"},on:{change:t.change},model:{value:t.active,callback:function(a){t.active=a},expression:"active"}},t._l(t.radioList,(function(t){return e("el-option",{key:t.key,attrs:{label:t.name,value:t.key}})})),1)],1)]),e("EchartColumn",{ref:"columnCharts",attrs:{height:265,title:"",bgcolor:"#0c368a",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},E=[],F=(e("c740"),e("b680"),12),V={mixins:[p["a"],w["a"]],props:{title:{type:String,default:""},soreTypeId:{type:Number,default:4},reportData:{type:Object,default:function(){return{}}}},components:{ChartBox:l["a"],EchartColumn:S["a"]},data:function(){return{radioList:[],active:"",activeName:"",dataList:[],xdata:[],data:[],options:{},loading:!1}},created:function(){this._initUIView()},methods:{change:function(t){if(0!==this.dataList.length){var a=this.radioList.find((function(a){return a.key===t}));this.activeName=a.name,this.formatResData()}},queryTap:function(){var t=this.mixinReqBiOneTime();this._apiGetScoreBiIndustry(Object(k["a"])({companyId:this.companyInfo.companyId,soreTypeId:this.soreTypeId},t))},formatResData:function(){var t=this,a=this.dataList.find((function(a){return a.indexAddr===t.active}));a=JSON.parse(JSON.stringify(a.indexIndustryValList)),a.sort((function(t,a){return a.avg-t.avg}));var e=a.map((function(t){return t.industryName})),n=a.map((function(a){var e=a.avg;return{value:e,itemStyle:{color:a.industryCode===t.companyInfo.industryCode?"#F9D22B":"#409EFF"}}})),r=a.find((function(a){return a.industryCode===t.companyInfo.industryCode})),i=a.findIndex((function(a){return a.industryCode===t.companyInfo.industryCode}));this.xdata=e,this.data=[{name:"",type:"bar",barWidth:2,data:n,color:"#0F9FFD",markPoint:Object(f["f"])({name:"本行业",value:r.industryName,index:i,yAxis:r.avg})}]},_initUIView:function(){this._initOptions()},_initOptions:function(){var t=this;this.options={backgroundColor:"transparent",dataZoom:f["d"],yAxis:{name:"",axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0,0,0,0.65)",formatter:function(t){return Number((100*t).toFixed(2))+"%"}}},xAxis:{axisLine:{show:!0,lineStyle:{color:"rgba(0, 0, 0, 0.25)"}},axisLabel:{color:"rgba(0,0,0,0.65)",fontSize:F,formatter:function(t){return t.length>4?"".concat(t.substring(0,4),"..."):t}}},grid:{right:0},tooltip:{formatter:function(a){var e=a[0],n=Number((100*e.value).toFixed(2)),r="行业：".concat(e.name),i="".concat(t.activeName,"：").concat(n,"%");return[r,i].join("<br/>")}}}},_initXdata:function(t){this.radioList=t.map((function(t){return{name:t.indexName,key:t.indexAddr}})),!this.active&&this.radioList.length>0&&(this.active=this.radioList[0].key,this.activeName=this.radioList[0].name)},_apiGetScoreBiIndustry:function(t){var a=this;return Object(D["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,a.loading=!0,e.next=4,Object(L["t"])(t);case 4:n=e.sent,a.dataList=n,a._initXdata(n),a.formatResData(),e.next=12;break;case 10:e.prev=10,e.t0=e["catch"](0);case 12:a.loading=!1;case 13:case"end":return e.stop()}}),e,null,[[0,10]])})))()}}},z=V,M=(e("f04a"),Object(g["a"])(z,A,E,!1,null,"2a5760bc",null)),J=M.exports,U=e("3ca5"),q=e("20b9"),$=e("600e"),G={mixins:[U["a"]],components:{StartBox:i["a"],BiIndexRadar:x,BiIndexCompare:j,BiIndexIndustryRank:J,AllIndustryRank:q["a"],AllMarketRank:$["a"]},data:function(){return{}}},P=G,W=(e("d0ce"),Object(g["a"])(P,n,r,!1,null,"f087c4e0",null));a["default"]=W.exports},7807:function(t,a,e){"use strict";e("8241")},8241:function(t,a,e){},a283:function(t,a,e){},d0ce:function(t,a,e){"use strict";e("edcf")},db42:function(t,a,e){"use strict";e("a283")},edcf:function(t,a,e){},f04a:function(t,a,e){"use strict";e("f3e1")},f3e1:function(t,a,e){}}]);