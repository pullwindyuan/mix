(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1249c9f1"],{"59d4":function(t,a,e){},"87f8":function(t,a,e){"use strict";e("c837")},c837:function(t,a,e){},cf55:function(t,a,e){"use strict";e("59d4")},dc38:function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"business-forecast-page"},[e("div",{staticClass:"business-forecast-content"},[e("TitleColorLine",{attrs:{title:"阿尔法财AI预警近期销售表现"}}),e("div",{staticClass:"performance-top-box"},[e("div",{staticClass:"p-top-item"},[e("SalesPerformance",{attrs:{dateText:t.dateText,data:t.sales}})],1)]),e("div",{staticClass:"b-card-box"},[e("el-row",{attrs:{gutter:20}},[e("el-col",{attrs:{span:12,offset:0}},[e("LeftCardBox")],1),e("el-col",{attrs:{span:12,offset:0}},[e("RightCardBox")],1)],1),e("div",{staticClass:"footer-text"})],1)],1)])},s=[],i=(e("99af"),e("7db0"),e("96cf"),e("1da1")),r=e("b85c"),c=e("5530"),o=e("2f62"),l=e("35b8"),d=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",{staticClass:"sales-performance"},[e("PerformanceBox",{attrs:{title:"阿尔法财AI分析：对比不同趋势，销售表现如何？",dateText:t.dateText}},[e("transition",{attrs:{name:"el-opacity-2"}},[e("div",{directives:[{name:"show",rawName:"v-show",value:t.xdata.length>0,expression:"xdata.length > 0"}],staticClass:"sales-content"},[e("div",{staticClass:"sales-content-chart-box"},[e("div",{staticClass:"s-right"},[e("div",{staticClass:"s-right-item-box"},[e("div",{staticClass:"s-right-item"},[t._v("对标短期走势")])]),e("div",{staticClass:"s-right-item-box"},[e("div",{staticClass:"s-right-item"},[t._v("对标中期走势")])]),e("div",{staticClass:"s-right-item-box"},[e("div",{staticClass:"s-right-item"},[t._v("对标长期走势")])])]),e("EchartHotmap",{attrs:{options:t.options,height:105,size:35,pointList:t.pointList,hotList:t.hotList,xdata:t.xdata,ydata:t.ydata}})],1),e("div",{staticClass:"s-label-box"},[e("div",{staticClass:"s-label-item"},[t._v(t._s(t.xYear)+"年")]),t._l(t.xdata,(function(a){return e("div",{key:a.value,staticClass:"s-label-item"},[t._v(" "+t._s(a.name)+" ")])}))],2)])]),e("div",{directives:[{name:"show",rawName:"v-show",value:0===t.xdata.length,expression:"xdata.length === 0"}],staticStyle:{height:"126px"}})],1)],1)},f=[],u=(e("d81d"),e("ac1f"),e("1276"),e("b3cb")),h=e("b578"),m=e("ca79"),v={mixins:[m["a"]],props:{dateText:String},components:{PerformanceBox:u["a"],EchartHotmap:h["a"]},data:function(){return{options:{},pointList:[],hotList:[],xdata:[],ydata:[],xYear:"",title:"销售表现"}},methods:{beforeFormat:function(t){var a,e=Object(r["a"])(t);try{for(e.s();!(a=e.n()).done;){var n=a.value;n.children.sort((function(t,a){return t.nameEn.localeCompare(a.nameEn)}))}}catch(s){e.e(s)}finally{e.f()}},getXdata:function(){var t=null,a=this.data[0].children.map((function(a){var e=a.nameEn.split("-");return t||(t=e[0]),{value:a.nameEn,name:"".concat(e[1],".").concat(e[2]),en:a.nameEn}}));return this.xYear=t,a}}},p=v,x=(e("cf55"),e("2877")),b=Object(x["a"])(p,d,f,!1,null,"6bc150c0",null),C=b.exports,g=e("1da18"),_=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("CardBoxGif",{attrs:{title:"AI知识：销售表现的好与坏如何得来？",data:t.dataList}})},w=[],y=e("4f4d"),L={components:{CardBoxGif:y["a"]},data:function(){return{dataList:["AI对日常销售额进行持续跟踪后，洞察出不同期间的销售业绩表现规律或模式。AI将这种规律或模式用科学的算法模型在对应的时间序，计算出一个区间值 {很坏：-1 ， 0没有变化， 很好：+1} 的分布。","然后，AI将当天的销售业绩与短期、中期、长期销售业绩表现的规律对比，计算出销售员或团队当天的销售业绩是好、是坏、或没有变化。"]}}},I=L,E=Object(x["a"])(I,_,w,!1,null,"75507e16",null),T=E.exports,j=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("CardBoxPrint",{attrs:{title:"管理者如何使用AI分析？",data:t.dataList}})},O=[],B=e("d9a3"),S={components:{CardBoxPrint:B["a"]},data:function(){return{dataList:[]}},created:function(){this._initUIView()},methods:{_initUIView:function(){this._initDataList()},_initDataList:function(){this.dataList=[" “销售表现”不是销售额，它在数据勾稽关系的背后，隐射了人、团队在不同时间的行为模式或规律。因此，崇尚员工创新的管理者，无论是通过自管理、或上级对下属的指导，可根据AI分析的销售表现的好与坏，对销售员或团队及时采取提升措施。 ","比如，表现值在零以下的负数，要进行驱动力和技能培训，或对客户需求和市场环节进行诊断，查看是内在问题或是外部问题。如果表现值趋于正数1，管理者对个人或团队嘉奖鼓励，也可考虑增加业务目标（有可能目标设置过低），或改为更激进的销售策略。","总之，有了AI就不用等到月底财报结果、或周会才解决问题。"]}}},A=S,D=Object(x["a"])(A,j,O,!1,null,"16ff9290",null),k=D.exports,P={components:{SalesPerformance:C,TitleColorLine:g["a"],LeftCardBox:T,RightCardBox:k},data:function(){return{sales:[],dateText:"",goalData:{}}},computed:Object(c["a"])({},Object(o["c"])(["yanshiInfo"])),created:function(){this._initUIView()},methods:{querySellForecast:function(){this._apiSellForecast({companyId:this.yanshiInfo.companyId})},queryTap:function(){this.querySellForecast()},handleResult:function(t){var a=t.dateList;this.dateText="（基于".concat(a[0],"至").concat(a[1],"数据分析）"),this.sales=this.formatHotdata(t.heatMap),this.filterSalesData(a[0],this.sales)},formatHotdata:function(t){var a=t.find((function(t){return"sales"===t.nameEn}));return a.children},filterSalesData:function(t,a){if(a.length>0){var e,n=Object(r["a"])(a);try{for(n.s();!(e=n.n()).done;){var s,i=e.value,c=Object(r["a"])(i.children);try{for(c.s();!(s=c.n()).done;){var o=s.value;new Date(o["nameEn"]).getTime()<new Date(t).getTime()&&(o.gray=!0)}}catch(l){c.e(l)}finally{c.f()}}}catch(l){n.e(l)}finally{n.f()}}},_initUIView:function(){this.queryTap()},_apiSellForecast:function(t){var a=this;return Object(i["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.prev=0,a.loading=!0,e.next=4,Object(l["g"])(t);case 4:n=e.sent,a.handleResult(n),e.next=10;break;case 8:e.prev=8,e.t0=e["catch"](0);case 10:a.loading=!1;case 11:case"end":return e.stop()}}),e,null,[[0,8]])})))()}}},R=P,F=(e("87f8"),Object(x["a"])(R,n,s,!1,null,"0f8e2bb3",null));a["default"]=F.exports}}]);