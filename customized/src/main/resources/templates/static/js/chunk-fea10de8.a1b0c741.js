(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-fea10de8"],{"0605":function(t,e,i){"use strict";i("c0eb")},"104c":function(t,e,i){},"1dc6":function(t,e,i){"use strict";i("eadd")},"1e8f":function(t,e,i){},2532:function(t,e,i){"use strict";var n=i("23e7"),a=i("5a34"),o=i("1d80"),r=i("ab13");n({target:"String",proto:!0,forced:!r("includes")},{includes:function(t){return!!~String(o(this)).indexOf(a(t),arguments.length>1?arguments[1]:void 0)}})},"2cb9":function(t,e,i){"use strict";i("34e8")},"31a2":function(t,e,i){"use strict";i("1e8f")},"34e8":function(t,e,i){},5801:function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return t.loadingBox?i("g-echart-box",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],attrs:{bgcolor:t.bgcolor,"g-loading-background":t.bgcolor,"g-loading-text-color":t.textColor,txtColor:t.txtColor,imgWidth:t.imgWidth,empty:0===t.xdata.length}},[i("div",{ref:"chart",style:t.boxStyle})]):i("div",{ref:"chart",style:t.boxStyle})},a=[],o=(i("4160"),i("a9e3"),i("159b"),i("33c3")),r=i("164e"),s={props:{title:{type:String,default:""},imgWidth:{type:Number,default:200},txtColor:{type:String,default:"white"},data:{type:Array,default:function(){return[]}},xdata:{type:Array,default:function(){return[]}},height:{type:[Number,String],default:320},bgcolor:{type:String,default:"white"},textColor:{type:String,default:"#606266"},colors:{type:Array,default:function(){return[]}},options:{type:Object,default:function(){return null}},xTitle:{type:String,default:""},yTitle:{type:String,default:""},emptyText:String,loadingScale:{type:Number,default:1},smooth:{type:Boolean,default:!1},loadingBox:{type:Boolean,default:!0}},data:function(){return{loading:!0}},computed:{boxStyle:function(){return{height:this.height+"px",margin:"0 auto"}}},mounted:function(){window.addEventListener("resize",this.resize)},methods:{resize:function(){this.chart&&this.chart.resize()},_initCanvas:function(){var t=this;this._renderIndex>1&&this.loading&&(this.loading=!1),this.loading&&this.xdata.length>0&&(this.loading=!1),this.$nextTick((function(){t.loading||t._initChart()}))},_initChart:function(){var t=this;if(!this.chart){var e=this.$refs.chart;if(!e)return;this.chart=r["init"](e)}this.data.length>0&&this.data.forEach((function(e,i){e.type="bar"===e.type?"bar":"line",e.symbolSize=e.symbolSize||0,e.smooth=t.smooth,t.colors.length&&t.colors.length>0&&t.colors.forEach((function(t,n){i===n&&(e.color=t)}))}));var i=Object(o["l"])(this.data,this.xdata,this.title,this.xTitle,this.yTitle);Object(o["c"])(i,this.options),this.options&&(i=Object(o["m"])(this.options,i)),this.chart.clear(),this.chart.setOption(i)}},watch:{data:{deep:!0,immediate:!0,handler:function(){this._renderIndex||(this._renderIndex=0),this._renderIndex++,this._initCanvas()}}},beforeDestroy:function(){window.removeEventListener("resize",this.resize)}},c=s,l=(i("31a2"),i("2877")),u=Object(l["a"])(c,n,a,!1,null,"6cbf7eb0",null);e["a"]=u.exports},"5a34":function(t,e,i){var n=i("44e7");t.exports=function(t){if(n(t))throw TypeError("The method doesn't accept regular expressions");return t}},"5e8c":function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"business-forecast"},[i("LayoutContent",{attrs:{menuActive:t.menuActive,menuList:t.menuList,showAllChild:""},on:{menuTap:t.menuTap}},[i("div",{staticClass:"bus-content"},[t.$role("sellForecast")?i("div",{ref:"forecast",staticClass:"bus-item-box"},[i("Forecast",{attrs:{dateText:t.dateText,forecast:t.forecast,forecastRate:t.forecastRate}})],1):t._e(),t.$role("sellContrast")?i("div",{ref:"compare",staticClass:"bus-item-box"},[i("Compare",{attrs:{loading:t.loading,dateText:t.dateText,trend:t.trend}})],1):t._e(),t.$role("financeForecast")?i("div",{ref:"finance",staticClass:"bus-item-box"},[i("Finance",{ref:"profit",attrs:{loading:t.loading,dateText:t.dateText,extendColumn:t.extendColumn,pdata:t.pdata,goalData:t.goalData,year:t.year},on:{queryGoalTap:t.queryGoalTap}})],1):t._e()])])],1)},a=[],o=(i("99af"),i("4de4"),i("c740"),i("4160"),i("b680"),i("ac1f"),i("5319"),i("1276"),i("96cf"),i("1da1")),r=i("b85c"),s=i("5530"),c=i("7c9f"),l=i("b86a"),u=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"compare"},[i("TempHeader",{attrs:{dateText:t.dateText,title:"阿尔法财AI分析：过去28天日销售表现走势与不同时期销售表现走势对比"}}),i("div",{staticClass:"charts"},[i("div",{directives:[{name:"g-loading",rawName:"v-g-loading",value:t.loading,expression:"loading"}],staticClass:"chart-wrap g-relative"},[i("EchartsColumn",{attrs:{txtColor:"#999",height:"296",xdata:t.xdata,data:t.data,options:t.options}})],1)])],1)},d=[],h=(i("caad"),i("a15b"),i("d81d"),i("fb6a"),i("b0c0"),i("2532"),i("159b"),i("2909")),f=i("f0aa"),p=i("164e"),m=i("8943"),g=i("f348"),v=i("33c3"),x={props:{trend:{type:Array,default:function(){return[]}},loading:Boolean,dateText:String},components:{TempHeader:f["a"],EchartsColumn:m["a"]},data:function(){return{data:[],options:{},xdata:[]}},created:function(){this._initUIView()},methods:{_initCavans:function(){var t=this;this.$nextTick((function(){t.handleResult()}))},handleResult:function(){var t=this,e=this.trend.slice();e.sort((function(t,e){return t.name.localeCompare(e.name)}));var i=e.map((function(t){return t.name})),n=[],a=[],o=[],r=[],s=this.$day.format(new Date);if(i.includes(s)){var c=i.findIndex((function(t){return t===s}));i[c]="今天"}e.forEach((function(e){var i=t.getTooltipText(e),s=i.p1,c=i.p2,l=i.p3;n.push(+e.actualSales),a.push({value:+e.microTrend,text:s}),o.push({value:+e.quartersTrend,text:c}),r.push({value:+e.allQuartersTrend,text:l})})),this.xdata=i,this.data=[{name:"日销量",type:"bar",data:n,barWidth:10,itemStyle:{color:"#69DC22",borderRadius:[4,4,0,0]}},{name:"28天销售表现趋势",type:"line",symbolSize:6,symbol:"circle",smooth:!0,showSymbol:!1,data:a,itemStyle:{color:"#F59A66"},lineStyle:{width:1.6,color:"#F59A66"}},{name:"4个季度销售表现趋势",type:"line",symbolSize:6,symbol:"circle",data:o,smooth:!0,showSymbol:!1,itemStyle:{color:"rgba(4, 160, 225, 1)"},lineStyle:{width:1.6}},{name:"全部历史销售表现趋势",type:"line",symbolSize:6,symbol:"circle",data:r,smooth:!0,showSymbol:!1,itemStyle:{color:"#B723BF"},lineStyle:{width:1.6,opacity:.7}}]},getTooltipText:function(t){var e=this.getPctText(+t.actualSales,+t.microTrend),i="与过去28天的销售表现趋势（".concat(Object(g["f"])(+t.microTrend),"）对比，").concat(e),n=this.getPctText(+t.actualSales,+t.quartersTrend),a="与过去四个季度销售表现趋势（".concat(Object(g["f"])(+t.quartersTrend),"）对比，").concat(n),o=this.getPctText(+t.actualSales,+t.allQuartersTrend),r="与全部历史销售表现趋势（".concat(Object(g["f"])(+t.allQuartersTrend),"）对比，").concat(o);return{p1:i,p2:a,p3:r}},getPctText:function(t,e){if(0===+t)return t>e?"表现较好":t===e?"基本持平":"表现不及预期";var i=(t-e)/t,n=(100*i).toFixed(2)+"%";return i<-.05?"低".concat(n,"，表现不及预期"):i>.05?"超过了".concat(n,"，表现较好"):"基本持平"},_initUIView:function(){this._initColors(),this._initOptions()},_initColors:function(){this.color1=new p["graphic"].LinearGradient(0,0,0,1,[{offset:0,color:"#3EDDFF"},{offset:1,color:"#3F60F2"}]),this.color2=new p["graphic"].LinearGradient(1,0,0,0,[{offset:0,color:"#F59A66"},{offset:1,color:"#F53B70"}]),this.color3=new p["graphic"].LinearGradient(1,0,0,0,[{offset:0,color:"#69DC22"},{offset:1,color:"#108400"}]),this.color4=new p["graphic"].LinearGradient(1,0,0,0,[{offset:0,color:"#B723BF"},{offset:1,color:"#4945DC"}])},_initOptions:function(){this.options={grid:{top:40,bottom:50,right:40},dataZoom:v["d"],tooltip:{appendToBody:!0,formatter:function(t){var e=t[0].name,i="".concat(e," 日销量：").concat(Object(g["f"])(t[0].value)),n=t.slice(1).map((function(t){return t.data.text}));return[i].concat(Object(h["a"])(n)).join("<br/>")}},yAxis:[{type:"value",name:"销售量",nameTextStyle:{color:"#303133"},axisLabel:{formatter:function(t){return Object(g["f"])(t)},textStyle:{color:"#303133"}},splitLine:{show:!0},axisLine:{show:!0,lineStyle:{color:"rgba(204,204,204,1)",width:1,type:"solid"}}}],xAxis:{show:!0,axisLine:{show:!0,lineStyle:{color:"rgba(204,204,204,.4)",width:1,type:"solid"}},splitLine:{show:!1},axisLabel:{show:!0,textStyle:{color:function(t,e){var i=["#303133","rgba(254, 119, 0, 1)"];return"今天"===t?i[1]:i[0]}}}},legend:{show:!0,bottom:18,textStyle:{color:"#303133"},data:[{name:"日销量",icon:this.$echartsLegendIcon.roundRect},{name:"28天销售表现趋势",icon:this.$echartsLegendIcon.line},{name:"4个季度销售表现趋势",icon:this.$echartsLegendIcon.line},{name:"全部历史销售表现趋势",icon:this.$echartsLegendIcon.line,itemHeight:4}]}}}},watch:{trend:{handler:function(){this._initCavans()},deep:!0,immediate:!0}}},y=x,b=(i("b731"),i("2877")),w=Object(b["a"])(y,u,d,!1,null,"02e7999e",null),_=w.exports,T=i("a4d3d"),C=i("35b8"),S=i("2f62"),L=i("60c0"),I={mixins:[L["a"]],components:{LayoutContent:c["a"],Forecast:l["a"],Compare:_,Finance:T["a"]},data:function(){return{unManageKeys:["forecast","compare","finance"],menuActive:"",forecast:[],trend:[],dateText:"",goalData:{},extendColumn:[],pdata:[],year:"",loading:!0,forecastRate:{}}},computed:Object(s["a"])({menuList:function(){return this._initMenuList()}},Object(S["c"])(["yanshiInfo"])),mounted:function(){this._initUIView()},methods:{scrollActiveIndex:function(t){this.menuActive=this.unManageKeys[t]},menuTap:function(t,e){var i=this,n=t.key;this.menuActive=n,this.$nextTick((function(){i.mixinInitElement();var t=i.getScrollIndex(n,e);i.mixinScrollToIndex(t)}))},getScrollIndex:function(t){var e=this.menuList.filter((function(t){return!t.hide})),i=e.findIndex((function(e){return e.key===t}));return e.length===this.menuList.length&&(i-=1),i},mixinInitElement:function(){var t=this.getUnManageTopList();this._topList=t},getUnManageTopList:function(){if(this.unManageTopList)return this.unManageTopList;var t,e=[],i=Object(r["a"])(this.unManageKeys);try{for(i.s();!(t=i.n()).done;){var n=t.value,a=this.$refs[n];a&&e.push(a)}}catch(o){i.e(o)}finally{i.f()}return this.unManageTopList=this.getTopList(e),this.unManageTopList},getTopList:function(t){var e,i=126,n=[],a=Object(r["a"])(t);try{for(a.s();!(e=a.n()).done;){var o=e.value,s=o.offsetTop-i;n.push(s)}}catch(c){a.e(c)}finally{a.f()}return n},refreshTap:function(){var t=this;this.$nextTick((function(){["profit"].forEach((function(e){var i=t.$refs[e];i&&i.formatData()}))}))},queryGoalTap:function(){this._apiGetSellingExpectData()},querySellForecast:function(){this._apiSellForecast({companyId:this.yanshiInfo.companyId})},queryTap:function(){this.queryGoalTap(),this.querySellForecast()},handleResult:function(t){var e=t.dateList;this.dateText="（基于".concat(e[0],"至").concat(e[1],"数据分析）"),this.forecast=t.forecast,this.trend=t.variance,this.forecastRate={data:t.data,high:(100*t.high).toFixed(2)+"%",per95:(100*t.per95).toFixed(2)+"%"},this.year=t.pl.year.replace(/_/,"年")+"季度",this.pdata=[t.pl];for(var i=t.pl.year.split("_"),n=+i[0],a=+i[1],o=0;o<4;o++){var r=a+o;this.extendColumn.push({title:r>4?"".concat(n+1,"年").concat(r-4,"季度"):"".concat(n,"年").concat(r,"季度"),key:"uuid"+o})}this.refreshTap()},initData:function(){this.forecast=[]},_initUIView:function(){this.queryTap(),this._initActive()},_initEventBus:function(){},_initActive:function(){var t=this.menuList.findIndex((function(t){return!t.hide}));this.menuTap(this.menuList[t],t)},_initMenuList:function(){return[{title:"AI销售预测",key:"forecast",hide:!this.roleData.sellForecast},{title:"AI销售对比",key:"compare",hide:!this.roleData.sellContrast},{title:"AI财报预测",key:"finance",hide:!this.roleData.financeForecast}]},_apiGetSellingExpectData:function(){var t=this;return Object(o["a"])(regeneratorRuntime.mark((function e(){var i;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,e.next=3,Object(C["e"])(t.yanshiInfo.companyId);case 3:i=e.sent,t.goalData=i,t.refreshTap(),e.next=10;break;case 8:e.prev=8,e.t0=e["catch"](0);case 10:case"end":return e.stop()}}),e,null,[[0,8]])})))()},_apiSellForecast:function(t){var e=this;return Object(o["a"])(regeneratorRuntime.mark((function i(){var n;return regeneratorRuntime.wrap((function(i){while(1)switch(i.prev=i.next){case 0:return i.prev=0,e.loading=!0,i.next=4,Object(C["g"])(t);case 4:n=i.sent,e.handleResult(n),i.next=11;break;case 8:i.prev=8,i.t0=i["catch"](0),e.initData();case 11:e.loading=!1;case 12:case"end":return i.stop()}}),i,null,[[0,8]])})))()}},beforeDestroy:function(){}},A=I,O=(i("0605"),Object(b["a"])(A,n,a,!1,null,"0841b384",null));e["default"]=O.exports},"60c0":function(t,e,i){"use strict";var n=i("b85c"),a=126;e["a"]={mounted:function(){this.mixinListener()},methods:{mixinListener:function(){window.addEventListener("scroll",this.mixinScroll)},mixinScroll:function(){if(this._noListening)this._noListening=!1;else if(this._topList){for(var t=window.scrollY,e=1;e<this._topList.length;e++)if(t<this._topList[e])return this.mixinScrollActive(e-1);return this.mixinScrollActive(this._topList.length-1)}},mixinIsBottom:function(){var t=Math.max(document.documentElement.scrollHeight,document.body.scrollHeight),e=window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop,i=window.innerHeight||Math.min(document.documentElement.clientHeight,document.body.clientHeight);return i+e>=t},mixinInitElement:function(t){var e,i=[],o=Object(n["a"])(t);try{for(o.s();!(e=o.n()).done;){var r=e.value,s=this.$refs[r.key];if(s){var c=s.offsetTop-a;i.push(c)}}}catch(l){o.e(l)}finally{o.f()}this._topList=i},mixinScrollActive:function(t){this.scrollActiveIndex&&this.scrollActiveIndex(t)},mixinScrollToIndex:function(t){var e=this._topList[t]||0;this._noListening=!0,window.scrollTo(0,e)}},beforeDestroy:function(){window.removeEventListener("scroll",this.mixinScroll)}}},8943:function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("g-echart-box",{attrs:{bgcolor:t.bgcolor,empty:0===t.xdata.length,txtColor:t.txtColor,imgWidth:t.imgWidth}},[i("div",{ref:"chart",staticClass:"echart-box",style:t.boxStyle})])},a=[],o=(i("4160"),i("a9e3"),i("159b"),i("33c3")),r=i("164e"),s={props:{title:{type:String,default:""},imgWidth:{type:Number,default:200},txtColor:{type:String,default:"#606266"},data:{type:Array,default:function(){return[]}},bgcolor:{type:String,default:"white"},xdata:{type:Array,default:function(){return[]}},height:{type:[String,Number],default:"320"},labelColor:{type:String,default:"#FF413D"},showLabel:{type:Boolean,default:!1},options:{type:Object,default:function(){return null}}},data:function(){return{}},computed:{boxStyle:function(){return{height:this.height+"px",margin:"0 auto"}}},mounted:function(){var t=this;window.addEventListener("resize",(function(){t.chart.resize()}),!1)},methods:{_initCanvas:function(){var t=this;this.$nextTick((function(){t._initChart()}))},resize:function(){this.chart&&this.chart.resize()},_initChart:function(){if(!this.chart){var t=this.$refs.chart;if(!t)return;this.chart=r["init"](t)}this.data.length>0&&this.data.forEach((function(t){t.type=t.type||"bar",t.barWidth=t.barWidth||16}));var e=Object(o["b"])(this.data,this.xdata,this.title);Object(o["c"])(e,this.options),this.options&&(e=Object(o["m"])(this.options,e)),this.chart.clear(),this.chart.setOption(e)}},watch:{data:{deep:!0,immediate:!0,handler:function(){this._initCanvas()}}}},c=s,l=i("2877"),u=Object(l["a"])(c,n,a,!1,null,"35e3f8c6",null);e["a"]=u.exports},ab13:function(t,e,i){var n=i("b622"),a=n("match");t.exports=function(t){var e=/./;try{"/./"[t](e)}catch(i){try{return e[a]=!1,"/./"[t](e)}catch(n){}}return!1}},b731:function(t,e,i){"use strict";i("104c")},b86a:function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"forecast"},[i("TempHeader",{attrs:{dateText:t.dateText,title:"阿尔法财AI预测：公司未来28天销售量",showQuestion:!0}},[i("CustomSave",{attrs:{like:t.mixinForecastLike},on:{saveTap:t.mixinAddForecastMaterial}})],1),i("div",{staticClass:"charts"},[i("div",{staticClass:"chart-wrap g-relative"},[i("EchartsLine",{attrs:{smooth:"",height:"296",txtColor:"#999",xdata:t.xdata,data:t.data,options:t.options}})],1)]),i("div",{staticClass:"report-information"},[i("el-row",{attrs:{type:"flex",align:"middle",gutter:20}},[i("el-col",{attrs:{span:12}},[i("div",{staticClass:"l-side"},[i("div",{staticClass:"h-title"},[t._v("AI预测的报告解读")]),i("div",{staticClass:"main"},[i("img",{staticClass:"img",attrs:{src:"/images/book.png",alt:""}}),i("div",{staticClass:"info-box"},[i("div",{staticClass:"info-t"},[t._v(" 当前AI预测的准确率在"+t._s(t.forecastRate.high)+"左右，误差约"+t._s(t.forecastRate.per95)+"； ")]),i("div",{staticClass:"info-t"},[t._v(" 若要达到95%的准确率，需要提供"+t._s(t.forecastRate.data)+"天的历史订单数据。 ")]),i("div",{staticClass:"info-t"},[t._v(" 说明：AI预测精准度，是指该预测值落在所允许的科学间隔误差范围内的概率 ")])])])])]),i("el-col",{attrs:{span:12}},[i("div",{staticClass:"r-side"},[i("div",{staticClass:"h-title"},[t._v("如何更好的使用AI预测做决策参考")]),i("div",{staticClass:"h-sub-t"},[t._v(" AI预测销售，数据时间越长、数据量越大，预测的精准度就越高。有这几种情况： ")]),i("div",{staticClass:"info-t"},[t._v(" 1、如果只有10天销售数据，AI虽然可以预测了，但是精准度非常差； ")]),i("div",{staticClass:"info-t"},[t._v(" 2、如果有连续一个月每天的销售数据，AI预测未来一个月每天的销售精准度可提升，但对决策参考微不足道； ")]),i("div",{staticClass:"info-t"},[t._v(" 3、如果有连续六个月每天的销售数据，AI预测未来一个月每天的销售精准度可提升，开始为决策做一些参考； ")]),i("div",{staticClass:"info-t"},[t._v(" 4、如果有连续十二个月每天的销售数据，AI预测未来一个月每天的销售精准度远远高于顶尖专家，为决策管理提供不错的参考； ")]),i("div",{staticClass:"info-t"},[t._v(" 5、如果有连续四年每天的销售数据，AI预测未来一个月每天的销售精准度非常好，可成为管理者先知、先觉、先行的AI决策助理。 ")])])])],1)],1)],1)},a=[],o=(i("99af"),i("d81d"),i("fb6a"),i("b0c0"),i("f0aa")),r=i("164e"),s=i("5801"),c=i("f348"),l=i("33c3"),u=i("a8c8"),d=i("6612"),h={name:"forecast",mixins:[d["a"]],props:{forecast:{type:Array,default:function(){return[]}},dateText:String,forecastRate:Object},components:{TempHeader:o["a"],EchartsLine:s["a"],CustomSave:u["a"]},data:function(){return{data:[],options:{},xdata:[]}},created:function(){this._initUIView()},methods:{_initCavans:function(){var t=this;this.$nextTick((function(){t.handleResult()}))},handleResult:function(){var t=this.forecast.slice();t.sort((function(t,e){return t.name.localeCompare(e.name)}));var e=t.map((function(t){return t.name})),i=t.map((function(t){return+t.value}));this.xdata=e,this.data=[{name:"实际填报的销售额",type:"line",symbolSize:6,symbol:"circle",data:i,areaStyle:{color:this.color1,opacity:.4},lineStyle:{width:1.6}}]},_initUIView:function(){this._initColors(),this._initOptions()},_initColors:function(){this.color1=new r["graphic"].LinearGradient(0,0,0,1,[{offset:0,color:"rgba(4, 160, 225, 1)"},{offset:1,color:"rgba(11, 162, 225, 0)"}])},_initOptions:function(){this.options={grid:{top:40,bottom:30,right:40},dataZoom:l["d"],tooltip:{formatter:function(t){return Object(l["q"])({params:t,format:function(t){var e=t.marker,i="AI预测的销售额";return{text:"".concat(e).concat(i),value:Object(c["f"])(t.value)}}})},extraCssText:"width: 250px"},yAxis:{type:"value",name:"销售量",nameTextStyle:{color:"#303133"},axisLabel:{formatter:function(t){return Object(c["f"])(t)},textStyle:{color:"#303133"}},splitLine:{show:!0},axisLine:{show:!0,lineStyle:{color:"rgba(204,204,204,1)",width:1,type:"solid"}}},xAxis:{show:!0,boundaryGap:!0,axisLine:{show:!0,lineStyle:{color:"rgba(204,204,204,.4)",width:1,type:"solid"}},splitLine:{show:!1},axisLabel:{show:!0,textStyle:{color:"#303133"}}},legend:{show:!1}}}},watch:{forecast:{immediate:!0,deep:!0,handler:function(t){this._initCavans()}}}},f=h,p=(i("1dc6"),i("2877")),m=Object(p["a"])(f,n,a,!1,null,"69ef8c8c",null);e["a"]=m.exports},c0eb:function(t,e,i){},c740:function(t,e,i){"use strict";var n=i("23e7"),a=i("b727").findIndex,o=i("44d2"),r=i("ae40"),s="findIndex",c=!0,l=r(s);s in[]&&Array(1)[s]((function(){c=!1})),n({target:"Array",proto:!0,forced:c||!l},{findIndex:function(t){return a(this,t,arguments.length>1?arguments[1]:void 0)}}),o(s)},eadd:function(t,e,i){},f0aa:function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"b-header"},[i("div",{staticClass:"l"},[i("div",{staticClass:"g-app-title-1"},[t._v(t._s(t.title))]),i("div",{staticClass:"date-text"},[t._v(t._s(t.dateText))])]),i("span",{staticClass:"opers"},[t._t("default")],2)])},a=[],o={props:{title:String,dateText:String,showQuestion:{type:Boolean,default:!1}},data:function(){return{}}},r=o,s=(i("2cb9"),i("2877")),c=Object(s["a"])(r,n,a,!1,null,"0ca8d4d7",null);e["a"]=c.exports}}]);