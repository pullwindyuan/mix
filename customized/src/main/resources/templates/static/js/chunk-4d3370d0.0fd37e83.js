(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4d3370d0"],{"385c":function(t,e,i){"use strict";i("d3b7"),i("96cf");var n=i("1da1"),s=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"ceo-ai-element-box"},[i("div",{staticClass:"ceo-title-box",style:t.boxStyle,on:{click:t.itemTap}},[i("div",{staticClass:"title-text"},[t._v(t._s(t.title))]),i("div",{staticClass:"title-icon"},[i("i",{staticClass:"icon el-icon-arrow-right",class:[t.active?"active":""]})])]),t._t("default")],2)},o=[],r={props:{title:String,active:{type:Boolean,default:!1},bgcolor:{type:String,default:"#f5f7fa"}},data:function(){return{}},computed:{boxStyle:function(){return{background:this.bgcolor}}},methods:{itemTap:function(){this.$emit("itemTap")}}},a=r,l=(i("ec37"),i("2877")),c=Object(l["a"])(a,s,o,!1,null,"e8958676",null),u=c.exports,d=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"ceo-sub-box"},[i("div",{staticClass:"ceo-title-sub-box",style:t.boxStyle},[i("div",{staticClass:"sub-title-index",style:t.signStyle},[t._v(t._s(t.indexTitle))]),i("div",{staticClass:"sub-title-text",style:t.textStyle},[t._v(t._s(t.title))])])])},f=[],h={props:{indexTitle:{type:String,default:""},title:{type:String,default:""},top:{type:String,default:"28"},borderColor:{type:String,defualt:"#c0c4cc"},signBgColor:{type:String,default:"#dddddd"},signColor:{type:String,default:"white"},signBorderColor:{type:String,defualt:"#e0e0e0"},textColor:{type:String,default:"#282828"}},data:function(){return{}},computed:{boxStyle:function(){return{marginTop:"".concat(this.top,"px"),borderColor:this.borderColor}},signStyle:function(){return{color:this.signColor,borderColor:this.signBorderColor,background:this.signBgColor}},textStyle:function(){return{color:this.textColor}}}},p=h,m=(i("be33"),Object(l["a"])(p,d,f,!1,null,"23131b52",null)),b=m.exports;e["a"]={props:{companyInfo:Object},components:{CeoAiTitleBox:u,CeoAiTitleSubBox:b},data:function(){return{mixinSleep:80}},methods:{mixinGetBgcolor:function(t){var e=t%this.$boxColors.length;return this.$boxColors[e]},mixinLinkTap:function(t){if(t===this.showFlag)return this.showFlag="";this.$emit("linkTap",{id:t})},mixinGetLinkElement:function(t){var e=this;return Object(n["a"])(regeneratorRuntime.mark((function i(){var n;return regeneratorRuntime.wrap((function(i){while(1)switch(i.prev=i.next){case 0:return e.mixinLoadElement(t),i.next=3,e.sleep(e.mixinSleep);case 3:if(n=e.$refs[t.id],n){i.next=6;break}return i.abrupt("return",null);case 6:if(Array.isArray(n)&&(n=n[0]),!n||!n.$el){i.next=9;break}return i.abrupt("return",n.$el);case 9:return i.abrupt("return",n);case 10:case"end":return i.stop()}}),i)})))()},mixinLoadElement:function(t){var e=t.pId?t.pId:t.id;this.vIfFlag[e]=!0,this.showFlag=e},sleep:function(t){var e=this;return new Promise((function(i){e.sleepTimer&&clearTimeout(e.sleepTimer),e.timer=setTimeout((function(){i()}),t)}))}},beforeDestroy:function(){this.sleepTimer&&clearTimeout(this.sleepTimer)}}},"3cfe":function(t,e,i){},"7bb8":function(t,e,i){},9470:function(t,e,i){},a1d2:function(t,e,i){"use strict";var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"fin-header"},[t.$slots.title?t._e():i("div",{staticClass:"big-title"},[i("div",{staticClass:"title"},[i("span",[t._v(t._s(t.companyInfo.companyName)+t._s(t.infoTitle))]),i("span",{staticClass:"year"},[t._v("（基于"+t._s(t.$startYear)+" - "+t._s(t.$endYear)+"数据）")])])]),t._t("title"),i("div",{staticClass:"top-banner-wrap",class:t.showMore?"show":""},[i("div",{staticClass:"l-banner-main"},[i("div",{staticClass:"sub-title",class:t.showMore?"":"one-line"},[t._v(" "+t._s(t.subTitle)+" ")]),i("div",{staticClass:"future-risk-content-info",class:t.showMore?"h270":""},[i("br",{directives:[{name:"show",rawName:"v-show",value:t.showMore,expression:"showMore"}]}),t.$slots.default?i("div",[i("div",{directives:[{name:"show",rawName:"v-show",value:t.showMore,expression:"showMore"}]},[t._t("default")],2)]):i("div",t._l(t.textList,(function(e,n){return i("div",{directives:[{name:"show",rawName:"v-show",value:t.showMore,expression:"showMore"}],key:n},[i("div",{staticClass:"text-list"},[i("span",{staticClass:"point"}),i("span",{staticClass:"t"},[t._v(t._s(e))])]),i("br")])})),0),i("div",{staticClass:"href-a",on:{click:t.handleToggleShow}},[t._v(" "+t._s(t.showMore?"收起详情":"展开该指标内涵")+" ")])]),i("div",{staticClass:"foot-info"},[i("div",{staticClass:"img-info"},[t._t("imgInfo")],2),i("div",{staticClass:"more-down"},[t._v(t._s(t.moreText))])])]),i("div",{staticClass:"r-img"},[i("img",{staticClass:"img",attrs:{src:t.imgUrl,alt:""}})])])],2)},s=[],o={props:{infoTitle:{type:String,default:""},subTitle:{type:String,default:""},imgUrl:{type:String,default:"http://placehold.it/300x220"},textList:{type:Array,default:function(){return[]}},companyInfo:{type:Object,default:function(){return{}}},hideRight:{type:Boolean,default:!1},moreText:{type:String,default:""}},data:function(){return{showMore:!1}},methods:{handleToggleShow:function(){this.showMore=!this.showMore}}},r=o,a=(i("b6d9"),i("2877")),l=Object(a["a"])(r,n,s,!1,null,"17ff5a33",null);e["a"]=l.exports},b6d9:function(t,e,i){"use strict";i("3cfe")},be33:function(t,e,i){"use strict";i("9470")},ec37:function(t,e,i){"use strict";i("7bb8")},ec3b:function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("HeaderBox",{attrs:{companyInfo:t.companyInfo,infoTitle:"生存与发展分析",subTitle:"在高水平专家的眼里“生存与发展”，对应的是 “现在与未来”的内涵。",textList:["AI通过对超过10⁸²海量大数据勾稽关系的分析，能在1秒内，分析短期资金链健康状况，也可分析长期投资、回报、权益等状况。","更重要的是，AI以秒级速度分析的深度、广度、精度，是专家们也望尘莫及的。"],imgUrl:"/images/financial/i-1-ba.png"}}),i("div",{staticClass:"report-elements-ceo-ai-title-small g-bg-white"},t._l(t.data.children,(function(e,n){return i("CeoAiTitleBox",{key:e.id,ref:e.id,refInFor:!0,attrs:{active:t.showFlag===e.id,title:n+1+"."+e.title,bgcolor:t.mixinGetBgcolor(n)},on:{itemTap:function(i){return t.mixinLinkTap(e.id)}}},[t.vIfFlag[e.id]?i("div",{directives:[{name:"show",rawName:"v-show",value:t.showFlag===e.id,expression:"showFlag === item.id"}]},[i("g-visible-box",{attrs:{minHeight:0}},[i("Report"+e.component,{tag:"component",attrs:{companyInfo:t.companyInfo}},[i("div",{attrs:{slot:"title"},slot:"title"})])],1)],1):t._e()])})),1)],1)},s=[],o=i("a1d2"),r=i("385c"),a=(i("d3b7"),{components:{ReportCapitalChain:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-92256838")]).then(i.bind(null,"c941"))},ReportCashHealthy:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-700471be")]).then(i.bind(null,"e535"))},ReportNotCashAndSellAnalysis:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-78c2b837")]).then(i.bind(null,"b2d6"))},ReportNotCashAnalysis:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-89f25712")]).then(i.bind(null,"9f17"))},ReportCapitalStruct:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-2332e5b8")]).then(i.bind(null,"ff4a"))},ReportDebtsAndRights:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-e4731382")]).then(i.bind(null,"da8f"))},ReportWorkingCapital:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-6081c11a")]).then(i.bind(null,"9696"))},ReportInvestRR:function(){return Promise.all([i.e("chunk-273e45a4"),i.e("chunk-0dfc38de")]).then(i.bind(null,"9ec0"))}}}),l=i("9be3"),c=i("f8ee"),u={mixins:[a,r["a"],c["a"]],components:{HeaderBox:o["a"]},props:{companyInfo:{type:Object,default:function(){return{}}}},data:function(){return{vIfFlag:{},showFlag:"",data:{}}},created:function(){this._initUIView()},methods:{_initUIView:function(){this._initData()},_initData:function(){this.data=JSON.parse(JSON.stringify(l["a"][0]));var t=this.data.children[0];this.showFlag=t.id,this.vIfFlag[t.id]=!0}}},d=u,f=i("2877"),h=Object(f["a"])(d,n,s,!1,null,"096506bc",null);e["default"]=h.exports},f8ee:function(t,e,i){"use strict";e["a"]={mounted:function(){this.$emit("componentMouted")},methods:{mixinGetLinkElement:function(t){this.mixinLoadElement(t);var e=this.$refs[t.id];return e?(Array.isArray(e)&&(e=e[0]),e&&e.$el?e.$el:e):null}}}}}]);