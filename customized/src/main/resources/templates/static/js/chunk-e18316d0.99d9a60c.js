(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e18316d0"],{"0466":function(t,e,i){},"0666":function(t,e,i){"use strict";i.r(e);var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"p-board-page"},[i("LayoutContent",{attrs:{menuActive:t.menuActive,menuList:t.menuList},on:{menuTap:t.menuTap}},[i("div",{staticClass:"bus-content"},[i("div",{staticClass:"borad-content"},[i("div",{ref:"profit",staticClass:"power-box"},[i("div",{staticClass:"borad-title"},[t._v("盈利能力")]),i("profit",{attrs:{roe:t.roeData,roic:t.roicData,chartData:t.profitChartData,ske:t.showSke,options:t.lineOptions}})],1),i("div",{ref:"operation",staticClass:"power-box"},[i("operation",{attrs:{operationData:t.operationData,debtChartData:t.debtChartData,ske:t.showSke,options:t.lineOptions}})],1),i("div",{ref:"harmonize",staticClass:"power-box"},[i("div",{staticClass:"borad-title"},[t._v("协调能力")]),i("harmonize",{attrs:{harmonizeData:t.harmonizeData,ske:t.showSke}})],1),i("div",{ref:"develop",staticClass:"power-box last"},[i("div",{staticClass:"borad-title develop-title"},[i("div",[t._v("发展能力")]),i("div",{staticClass:"sub"},[t._v(" 备注： ( 留存收益 / 销售收入 ) / [ 收入增长率 × ( 流动资产 / 销售收入 ) ] = 均衡增长值 ")])]),i("develop",{attrs:{developData:t.developData,ske:t.showSke}})],1)])])])],1)},n=[],r=(i("96cf"),i("1da1")),o=i("5530"),s=i("7c9f"),c=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("el-row",{attrs:{gutter:20}},[i("el-col",{attrs:{span:9}},[i("app-board-box",{attrs:{height:"220",data:t.roe,ske:t.ske,showImage:""}})],1),i("el-col",{attrs:{span:6}},[i("div",{staticClass:"col-box"},[i("gauge",{attrs:{height:"220",ske:t.ske,title:t.roic.pct,data:t.roic.data}})],1)]),i("el-col",{attrs:{span:9}},[i("div",{staticClass:"col-box"},[i("echart-line",{attrs:{height:"220",ske:t.ske,data:t.chartData.data,legdata:t.chartData.legdata,xdata:t.chartData.xdata,options:t.options}})],1)])],1)],1)},l=[],u=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{style:t.boxStyle},[i("div",{directives:[{name:"show",rawName:"v-show",value:t.ske,expression:"ske"}],staticClass:"ske-gauge"},[i("app-ske-pie",{attrs:{gauge:""}})],1),i("div",{directives:[{name:"show",rawName:"v-show",value:!t.ske,expression:"!ske"}]},[i("div",{ref:"chart",style:t.boxStyle})])])},h=[],d=i("164e"),f={props:{height:{type:String,default:"220"},data:{type:Array,default:function(){return[]}},title:{type:String,default:""},ske:{type:Boolean,default:!0}},data:function(){return{}},computed:{boxStyle:function(){return{height:this.height+"px",width:this.height+"px",margin:"0 auto"}}},methods:{_initCanvas:function(){var t=this;this.$nextTick((function(){t._initChart()}))},_initChart:function(){if(!this.chart){var t=this.$refs.chart;if(!t)return;this.chart=d["init"](t)}var e=this.echartsGaugeOptions(this.data,this.title);this.chart.setOption(e)},echartsGaugeOptions:function(t,e){var i=15;return{title:{text:e||"",textStyle:{fontSize:11,color:"#999999"},left:"center",bottom:"23%"},series:[{type:"gauge",radius:"88%",startAngle:240,endAngle:-60,data:t,title:{color:"#666666",fontWeight:"bold",fontSize:16,offsetCenter:[0,"-30%"]},axisLabel:{color:"#989898",fontSize:10,distance:i+4,formatter:function(t){return t+"%"}},axisLine:{lineStyle:{width:i,color:[[.2,"#59D9B1"],[.8,"#83B1FF"],[1,"#E8AD8B"]]}},splitLine:{show:!0,distance:-i,length:i,lineStyle:{color:"#fff",width:2}},axisTick:{distance:-i,length:8,lineStyle:{color:"#fff",width:1}},pointer:{length:"50%",width:5},detail:{color:"#333333",fontWeight:"bold",fontSize:20,offsetCenter:[0,"35%"],formatter:function(t){return t+"%"}}}]}}},watch:{ske:function(t){t||this._initCanvas()}}},p=f,m=(i("14f1"),i("2877")),v=Object(m["a"])(p,u,h,!1,null,"6942e94e",null),x=v.exports,y=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("app-ske-list",{directives:[{name:"show",rawName:"v-show",value:t.ske,expression:"ske"}],staticClass:"g-padding-16",attrs:{vertical:!1,height:t.height,nums:t.skemuns}}),i("div",{directives:[{name:"show",rawName:"v-show",value:!t.ske,expression:"!ske"}],ref:"chart",staticClass:"box-line",style:t.boxStyle})],1)},b=[],C=i("33c3"),w={mounted:function(){var t=this;this.$nextTick((function(){window.addEventListener("resize",t.mixinResize)}))},methods:{mixinResize:function(){var t=document.body.clientWidth;t>=1300&&this.chart&&this.chart.resize()}},beforeDestroy:function(){window.removeEventListener("resize",this.mixinResize)}},k={mixins:[w],props:{height:{type:String,default:"220"},data:{type:Array,default:function(){return[]}},xdata:{type:Array,default:function(){return[]}},legdata:{type:Array,default:function(){return[]}},options:{type:Object,default:function(){return null}},ske:{type:Boolean,default:!0}},data:function(){return{}},computed:{boxStyle:function(){return{height:this.height+"px"}},skemuns:function(){return(+this.height-48)/32|0}},methods:{_initCanvas:function(){var t=this;this.$nextTick((function(){t._initChart()}))},_initChart:function(){if(!this.chart){var t=this.$refs.chart;if(!t)return;this.chart=d["init"](t)}var e=Object(C["e"])(this.data,this.legdata,this.xdata);this.options&&(e=Object(C["m"])(this.options,e)),this.chart.setOption(e)}},watch:{ske:function(t){t||this._initCanvas()}}},g=k,D=(i("2d32"),Object(m["a"])(g,y,b,!1,null,"be814676",null)),_=D.exports,O={components:{Gauge:x,EchartLine:_},props:{ske:{type:Boolean,default:!0},roe:{type:Object,default:function(){return{}}},roic:{type:Object,default:function(){return{}}},chartData:{type:Object,default:function(){return{}}},options:{type:Object,default:function(){return{}}}},data:function(){return{}}},S=O,I=(i("d2dd"),Object(m["a"])(S,c,l,!1,null,"6c7b4984",null)),A=I.exports,F=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"operation"},[i("el-row",{attrs:{gutter:20}},[i("el-col",{attrs:{span:15}},[i("div",{staticClass:"operation-title"},[t._v("运营能力")]),i("div",{staticClass:"col-box liquidfill-box"},t._l(t.operationData,(function(e,a){return i("div",{key:a,staticClass:"liquidfill-item"},[i("echart-liquidfill",{attrs:{unit:"天",ske:t.ske,size:125,label:e.title,color:t.COLORS[a],data:e.value}}),i("div",{directives:[{name:"show",rawName:"v-show",value:!t.ske,expression:"!ske"}]},[i("div",{staticClass:"liquidfill-title"},[t._v(t._s(e.name))]),i("div",{staticClass:"liquidfill-sub"},[t._v(t._s(e.pct))])]),i("div",{directives:[{name:"show",rawName:"v-show",value:t.ske,expression:"ske"}]},[i("app-ske-line",{staticClass:"liquidfill-title",attrs:{width:"80%"}}),i("app-ske-line",{staticClass:"liquidfill-sub",attrs:{width:"60%"}})],1)],1)})),0)]),i("el-col",{attrs:{span:9}},[i("div",{staticClass:"operation-title"},[t._v("偿债能力")]),i("div",{staticClass:"col-box"},[i("echart-line",{attrs:{height:"220",ske:t.ske,data:t.debtChartData.data,legdata:t.debtChartData.legdata,xdata:t.debtChartData.xdata,options:t.options}})],1)])],1)],1)},z=[],L=i("8a60"),j=["#45C19A","#AAC4FF","#F3C924","#E8AD8B"],E={components:{EchartLine:_,EchartLiquidfill:L["a"]},props:{ske:{type:Boolean,default:!0},operationData:{type:Array,default:function(){return[]}},debtChartData:{type:Object,default:function(){return{}}},options:{type:Object,default:function(){return{}}}},data:function(){return{COLORS:j}}},R=E,T=(i("dc51"),Object(m["a"])(R,F,z,!1,null,"7f0809d4",null)),B=T.exports,H=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("el-row",{attrs:{gutter:20}},t._l(t.harmonizeData,(function(e){return i("el-col",{key:e.title,attrs:{span:8}},[i("div",{staticClass:"col-box"},[i("app-board-box",{attrs:{showImage:"",height:"220",data:e,ske:t.ske}})],1)])})),1)],1)},$=[],M={props:{ske:{type:Boolean,default:!0},harmonizeData:{type:Array,default:function(){return[]}}},data:function(){return{}}},q=M,N=(i("f8fb"),Object(m["a"])(q,H,$,!1,null,"0c2ee547",null)),G=N.exports,P=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("div",{directives:[{name:"show",rawName:"v-show",value:t.ske,expression:"ske"}],staticClass:"board-develop"},t._l(5,(function(t,e){return i("div",{key:"ske"+e,staticClass:"board-develop-item"},[i("div",{staticStyle:{width:"80%",margin:"0 auto"}},[i("app-ske-line",{staticClass:"develop-item-title",attrs:{width:"80%"}}),i("app-ske-line",{attrs:{width:"60%"}})],1)])})),0),i("div",{directives:[{name:"show",rawName:"v-show",value:!t.ske,expression:"!ske"}],staticClass:"board-develop"},t._l(t.developData,(function(e,a){return i("div",{key:a,staticClass:"board-develop-item"},[i("div",[i("div",{staticClass:"develop-item-title"},[i("span",[t._v(t._s(e.percent))]),i("span",{staticClass:"unit"},[t._v(t._s(e.unit||""))])]),i("div",{staticClass:"develop-item-info"},[i("div",{staticClass:"name"},[t._v(t._s(e.title))]),i("div",{staticClass:"pct"},[t._v(t._s(e.pct))])])])])})),0)])},W=[],Y={props:{ske:{type:Boolean,default:!0},developData:{type:Array,default:function(){return[]}}},data:function(){return{}}},U=Y,V=(i("a807"),Object(m["a"])(U,P,W,!1,null,"3d993cac",null)),J=V.exports,Q=(i("99af"),i("4160"),i("b0c0"),i("ac1f"),i("466d"),i("5319"),i("159b"),i("b85c")),X=i("b7d4"),Z=i("f348");function K(t){return X["a"].Mul(t,100)+"%"}function tt(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:100,i=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"pct";return"同比 ".concat(X["a"].Mul(t,e)).concat(i)}function et(t,e,i,a,n){return{title:e,percent:a?a(t[i]):K(t[i]),pct:tt(t["".concat(i,"Yoy")],100,n)}}function it(t,e,i){var a={legdata:i,xdata:[],data:[]};t.sort((function(t,e){return t.date.localeCompare(e.date)}));var n,r=Object(Q["a"])(t);try{var o=function(){var t=n.value,i=t.date.replace(/_/g,"Q");a.xdata.push(i),e.forEach((function(e,i){a.data[i]||(a.data[i]=[]),a.data[i].push(t[e])}))};for(r.s();!(n=r.n()).done;)o()}catch(s){r.e(s)}finally{r.f()}return a}var at={methods:{mixinFormatROE:function(t){return Object(o["a"])(Object(o["a"])({},et(t,"ROE","roe")),{},{children:[et(t,"净利润率","netProfitRatio"),et(t,"总资产周转率","totalAssetsTurnover"),et(t,"权益乘数","totalAssetsMultiplier")]})},mixinFormatROIC:function(t){return{pct:tt(t.roicYoy),data:[{name:"ROIC",value:X["a"].Mul(t.roic,100)}]}},mixinProfixChartdata:function(t){var e=["operatingProfitRatio","revenueRatio","grossProfitRatio"],i=["营业利润率","收入增长率","毛利率"];return it(t.chart1,e,i)}}},nt={methods:{mixinCreateOperationItem:function(t,e,i,a){return{title:"".concat(t[a]),value:X["a"].Div(t[a],e,2),name:i,pct:tt(t["".concat(a,"Yoy")],1,"天")}},mixinFormatOperation:function(t){var e=Math.max.apply(null,[+t.capitalTurnoverDays,+t.receivableDays,+t.inventoriesDays,+t.payableDays]);return[this.mixinCreateOperationItem(t,e,"运营资金周转天数","capitalTurnoverDays"),this.mixinCreateOperationItem(t,e,"应收账款周转天数","receivableDays"),this.mixinCreateOperationItem(t,e,"存货周转天数","inventoriesDays"),this.mixinCreateOperationItem(t,e,"应付账款周转天数","payableDays")]},mixinFormatDebtChartData:function(t){var e=["liabilityRatio","currentRatio","cashRatio"],i=["资产负债率","流动比率","现金比率"];return it(t.chart2,e,i)}}},rt={methods:{mixinFormatHarmonizeDataChildItem:function(t,e,i){return et(t,e,i,Z["f"],"%")},mixinFormatHarmonizeData:function(t){return[this.mixinFormatOSR(t),this.mixinsFormatFS(t),this.mixinsFormatIS(t)]},mixinFormatOSR:function(t){return Object(o["a"])(Object(o["a"])({},et(t,"经营活动支撑度","netOperateSupportRatio")),{},{children:[this.mixinFormatHarmonizeDataChildItem(t,"经营活动现金流入","subtotalOperateCashInflow"),this.mixinFormatHarmonizeDataChildItem(t,"营运资本","operateCapital")]})},mixinsFormatFS:function(t){return Object(o["a"])(Object(o["a"])({},et(t,"融资活动支撑度","financeSupport")),{},{children:[this.mixinFormatHarmonizeDataChildItem(t,"融资活动现金流入","subtotalFinanceCashInflow"),this.mixinFormatHarmonizeDataChildItem(t,"流动资产","totalCurrentAssets")]})},mixinsFormatIS:function(t){return Object(o["a"])(Object(o["a"])({},et(t,"投资活动支撑度","investSupport")),{},{children:[this.mixinFormatHarmonizeDataChildItem(t,"经营活动净流入","netOperateCashFlow"),this.mixinFormatHarmonizeDataChildItem(t,"融资活动净流入","netFinanceCashFlow"),this.mixinFormatHarmonizeDataChildItem(t,"CAPEX","capex")]})}}},ot={methods:{stringToValueAndUnit:function(t){return{percent:t.match(/-?[\d.]+/)[0],unit:t.replace(/[\d.-]/g,"")}},mixinDevelopItem:function(t){var e=t.data,i=t.key,a=t.title,n=t.format,r=t.unit,s=n?n(e[i]):K(e[i]),c=n?this.stringToValueAndUnit(s):{percent:s};return Object(o["a"])({title:a,pct:tt(e["".concat(i,"Yoy")],100,r)},c)},mixinFormatDevelopData:function(t){return[this.mixinDevelopItem({data:t,title:"留存收益",key:"retainedProfit",format:Z["f"],unit:"%"}),this.mixinDevelopItem({data:t,title:"销售收入",key:"totalOperatingRevenue",format:Z["f"],unit:"%"}),this.mixinDevelopItem({data:t,title:"流动资产",key:"totalCurrentAssets",format:Z["f"],unit:"%"}),this.mixinDevelopItem({data:t,title:"收入增长率",key:"revenueRatio"}),this.mixinDevelopItem({data:t,title:"均衡增长值",key:"balanceRatio"})]}}},st={mixins:[at,nt,rt,ot],data:function(){return{roeData:{},roicData:{},profitChartData:{},operationData:[{title:"",value:0,name:"",pct:""},{title:"",value:0,name:"",pct:""},{title:"",value:0,name:"",pct:""},{title:"",value:0,name:"",pct:""}],debtChartData:{},harmonizeData:[{},{},{}],developData:[],lineOptions:{}}},created:function(){this._initLineOptions()},methods:{formatBoardData:function(t){this.roeData=this.mixinFormatROE(t),this.roicData=this.mixinFormatROIC(t),this.profitChartData=this.mixinProfixChartdata(t),this.operationData=this.mixinFormatOperation(t),this.debtChartData=this.mixinFormatDebtChartData(t),this.harmonizeData=this.mixinFormatHarmonizeData(t),this.developData=this.mixinFormatDevelopData(t)},_initLineOptions:function(){this.lineOptions={dataZoom:C["d"],legend:{top:0,right:0,padding:[16,16,0,0],itemGap:6,itemWidth:8,itemHeight:8,textStyle:{fontSize:12,color:"#999999"}},xAxis:{splitLine:{show:!1}},yAxis:{splitLine:{show:!0,lineStyle:{type:"dashed"}}},tooltip:{position:Object(C["i"])(0,["left","top"]),formatter:function(t){var e=t[0].name,i=Object(C["k"])(e);return Object(C["j"])(t,"",(function(t){return K(t)}),i,!0)}}}}}},ct=i("2f62"),lt=i("bc3a"),ut=i.n(lt);function ht(t){var e="/dataModel/getCompanyBoard";return ut.a.post(e,t)}function dt(t){var e="/dataModel/getIpoCompanyBoard";return ut.a.post(e,t)}var ft=i("60c0"),pt=400,mt={mixins:[ft["a"],st],components:{LayoutContent:s["a"],Profit:A,Operation:B,Harmonize:G,Develop:J},data:function(){return{menuActive:"",defaultCompanyId:0,companyInfo:{},showSke:!0,noData:!1,errorText:""}},computed:Object(o["a"])({menuList:function(){return this._initMenuList()}},Object(ct["c"])(["yanshiInfo"])),created:function(){this._initUIView()},mounted:function(){this.mixinInitElement(this.menuList),this.menuTap(this.menuList[0],0)},methods:{scrollActiveIndex:function(t){var e=this.menuList[t];e.key!==this.menuActive&&(this.menuActive=e.key)},menuTap:function(t,e){this.menuActive=t.key,this.mixinScrollToIndex(e)},hideSke:function(){var t=this;this.$nextTick((function(){t.showSke=!1}))},showNoData:function(){var t=this;this.timer&&clearTimeout(this.timer),this.timer=setTimeout((function(){t.noData=!0}),pt)},queryTap:function(){var t=this.yanshiInfo.seasonDataFlag?"season":"year";this.yanshiInfo.unipo?this._apiGetBoard({myCompanyId:this.yanshiInfo.companyId,type:t}):this._apiGetBoardIpo({companyId:this.yanshiInfo.companyId,type:t})},_initUIView:function(){this.queryTap()},_initMenuList:function(){return[{title:"盈利能力",key:"profit"},{title:"运营能力",key:"operation"},{title:"偿债能力",key:"operation"},{title:"协调能力",key:"harmonize"},{title:"发展能力",key:"develop"}]},_apiGetBoard:function(t){var e=this;return Object(r["a"])(regeneratorRuntime.mark((function i(){var a;return regeneratorRuntime.wrap((function(i){while(1)switch(i.prev=i.next){case 0:return i.prev=0,e.showSke=!0,i.next=4,ht(t);case 4:a=i.sent,e.formatBoardData(a),e.hideSke(),i.next=11;break;case 9:i.prev=9,i.t0=i["catch"](0);case 11:case"end":return i.stop()}}),i,null,[[0,9]])})))()},_apiGetBoardIpo:function(t){var e=this;return Object(r["a"])(regeneratorRuntime.mark((function i(){var a;return regeneratorRuntime.wrap((function(i){while(1)switch(i.prev=i.next){case 0:return i.prev=0,e.showSke=!0,i.next=4,dt(t);case 4:a=i.sent,e.formatBoardData(a),e.hideSke(),i.next=11;break;case 9:i.prev=9,i.t0=i["catch"](0);case 11:case"end":return i.stop()}}),i,null,[[0,9]])})))()}},beforeDestroy:function(){this.timer&&clearTimeout(this.timer)}},vt=mt,xt=(i("dbd81"),Object(m["a"])(vt,a,n,!1,null,"85beaa82",null));e["default"]=xt.exports},"07ad":function(t,e,i){"use strict";i("9fc3")},"14f1":function(t,e,i){"use strict";i("da8b")},"2d32":function(t,e,i){"use strict";i("e86e")},"2ff5":function(t,e,i){},"60c0":function(t,e,i){"use strict";var a=i("b85c"),n=126;e["a"]={mounted:function(){this.mixinListener()},methods:{mixinListener:function(){window.addEventListener("scroll",this.mixinScroll)},mixinScroll:function(){if(this._noListening)this._noListening=!1;else if(this._topList){for(var t=window.scrollY,e=1;e<this._topList.length;e++)if(t<this._topList[e])return this.mixinScrollActive(e-1);return this.mixinScrollActive(this._topList.length-1)}},mixinIsBottom:function(){var t=Math.max(document.documentElement.scrollHeight,document.body.scrollHeight),e=window.pageYOffset||document.documentElement.scrollTop||document.body.scrollTop,i=window.innerHeight||Math.min(document.documentElement.clientHeight,document.body.clientHeight);return i+e>=t},mixinInitElement:function(t){var e,i=[],r=Object(a["a"])(t);try{for(r.s();!(e=r.n()).done;){var o=e.value,s=this.$refs[o.key];if(s){var c=s.offsetTop-n;i.push(c)}}}catch(l){r.e(l)}finally{r.f()}this._topList=i},mixinScrollActive:function(t){this.scrollActiveIndex&&this.scrollActiveIndex(t)},mixinScrollToIndex:function(t){var e=this._topList[t]||0;this._noListening=!0,window.scrollTo(0,e)}},beforeDestroy:function(){window.removeEventListener("scroll",this.mixinScroll)}}},"8a60":function(t,e,i){"use strict";var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{style:t.cStyle},[i("div",{staticClass:"chart-box",style:t.boxStyle},[i("svg",{attrs:{viewBox:"0 0 402 402"}},[i("path",{style:{stroke:t.color,strokeWidth:2,fill:"none"},attrs:{d:"M101,26.8 A200,200 0 1,0 200,0 M101,22.8 A4,4 0 0,0 100,30.8 M101,22.8 A4,4 0 0,1 101,30.8z"}})]),i("div",{ref:"chart",staticClass:"liquidfill-chart",style:t.cStyle})])])},n=[],r=(i("99af"),i("a9e3"),i("164e")),o=[3100,2200],s={props:{size:{type:Number,default:90},color:{type:String,default:"#AAC2FF"},data:{type:[Array,Number],default:function(){return[]}},label:{type:[String,Number],default:""},unit:{type:String,default:"%"}},data:function(){return{}},computed:{cStyle:function(){return{width:"".concat(this.size,"px"),height:"".concat(this.size,"px"),margin:"0 auto"}},boxStyle:function(){return{width:"".concat(this.size+8,"px"),height:"".concat(this.size+8,"px"),margin:"0 auto"}},tip:function(){var t=Array.isArray(this.data)?this.data[0]:this.data,e=100*t|0,i=this.label?"".concat(this.label).concat(this.unit):"".concat(e).concat(this.unit);return i}},methods:{_initCanvas:function(){var t=this;this.$nextTick((function(){t._initChart()}))},_initChart:function(){if(!this.chart){var t=this.$refs.chart;this.chart=r["init"](t)}var e=this.data;Array.isArray(e)||(e=[e]);var i=this;this.chart.setOption({series:[{type:"liquidFill",data:e,amplitude:8,period:function(t,e){return o[e]},radius:this.size-2,color:["".concat(this.color,"CC"),this.color],waveLength:"120%",waveAnimation:!1,itemStyle:{shadowBlur:5,opacity:1},backgroundStyle:{color:"#F7F7F7",borderColor:"#E5E5E5"},outline:{show:!1,borderDistance:5,itemStyle:{borderWidth:1,borderColor:this.color,shadowBlur:20}},label:{fontSize:30,fontWeight:"normal",color:"#333333",offset:[0,40],formatter:function(t){var e=100*t.data|0,a=""===i.label?e:i.label;return"".concat(a,"{a|").concat(i.unit,"}")},rich:{a:{fontSize:"14",verticalAlign:"bottom"}}}}]})}},watch:{data:{deep:!0,immediate:!0,handler:function(){this._renderIndex||(this._renderIndex=0),this._renderIndex++,this._initCanvas()}}}},c=s,l=(i("07ad"),i("2877")),u=Object(l["a"])(c,a,n,!1,null,"14c0a841",null);e["a"]=u.exports},"934b":function(t,e,i){},"9fc3":function(t,e,i){},a807:function(t,e,i){"use strict";i("2ff5")},c2a2:function(t,e,i){},d2dd:function(t,e,i){"use strict";i("c2a2")},da8b:function(t,e,i){},dbd81:function(t,e,i){"use strict";i("f736")},dc51:function(t,e,i){"use strict";i("934b")},e86e:function(t,e,i){},f736:function(t,e,i){},f8fb:function(t,e,i){"use strict";i("0466")}}]);