(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-bb2ccc16"],{"0538":function(t,e,n){"use strict";var r=n("1c0b"),a=n("861d"),o=[].slice,c={},i=function(t,e,n){if(!(e in c)){for(var r=[],a=0;a<e;a++)r[a]="a["+a+"]";c[e]=Function("C,a","return new C("+r.join(",")+")")}return c[e](t,n)};t.exports=Function.bind||function(t){var e=r(this),n=o.call(arguments,1),c=function(){var r=n.concat(o.call(arguments));return this instanceof c?i(e,r.length,r):e.apply(t,r)};return a(e.prototype)&&(c.prototype=e.prototype),c}},1412:function(t,e,n){"use strict";n("96cf");var r=n("1da1"),a=n("39e6");e["a"]={data:function(){return{companyOptions:[]}},created:function(){this._initCompanyOptions()},methods:{_initCompanyOptions:function(){var t=this;return Object(r["a"])(regeneratorRuntime.mark((function e(){var n,r;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["default"].init();case 2:n=e.sent,r=n.options,t.companyOptions=r,t._initItemList();case 6:case"end":return e.stop()}}),e)})))()}}}},"262e":function(t,e,n){"use strict";function r(t,e){return r=Object.setPrototypeOf||function(t,e){return t.__proto__=e,t},r(t,e)}function a(t,e){if("function"!==typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function");t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,writable:!0,configurable:!0}}),e&&r(t,e)}n.d(e,"a",(function(){return a}))},2909:function(t,e,n){"use strict";n.d(e,"a",(function(){return u}));var r=n("6b75");function a(t){if(Array.isArray(t))return Object(r["a"])(t)}n("a4d3"),n("e01a"),n("d28b"),n("a630"),n("d3b7"),n("3ca3"),n("ddb0");function o(t){if("undefined"!==typeof Symbol&&Symbol.iterator in Object(t))return Array.from(t)}var c=n("06c5");function i(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function u(t){return a(t)||o(t)||Object(c["a"])(t)||i()}},"2caf":function(t,e,n){"use strict";n.d(e,"a",(function(){return u}));n("4ae1"),n("3410");function r(t){return r=Object.setPrototypeOf?Object.getPrototypeOf:function(t){return t.__proto__||Object.getPrototypeOf(t)},r(t)}n("d3b7"),n("25f0");function a(){if("undefined"===typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"===typeof Proxy)return!0;try{return Date.prototype.toString.call(Reflect.construct(Date,[],(function(){}))),!0}catch(t){return!1}}var o=n("53ca");function c(t){if(void 0===t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return t}function i(t,e){return!e||"object"!==Object(o["a"])(e)&&"function"!==typeof e?c(t):e}function u(t){var e=a();return function(){var n,a=r(t);if(e){var o=r(this).constructor;n=Reflect.construct(a,arguments,o)}else n=a.apply(this,arguments);return i(this,n)}}},3410:function(t,e,n){var r=n("23e7"),a=n("d039"),o=n("7b0b"),c=n("e163"),i=n("e177"),u=a((function(){c(1)}));r({target:"Object",stat:!0,forced:u,sham:!i},{getPrototypeOf:function(t){return c(o(t))}})},"39e6":function(t,e,n){"use strict";n.r(e);n("96cf");var r=n("1da1"),a=n("d4ec"),o=n("bee2"),c=n("262e"),i=n("2caf"),u=n("9383"),s=n("f8b7"),l=function(t){Object(c["a"])(n,t);var e=Object(i["a"])(n);function n(){return Object(a["a"])(this,n),e.call(this)}return Object(o["a"])(n,[{key:"getValues",value:function(){var t=Object(r["a"])(regeneratorRuntime.mark((function t(){var e;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,this.getOptions({api:s["m"],valueKey:"companyCode",labelKey:"companyName",others:["datasource"]});case 2:return e=t.sent,t.abrupt("return",e);case 4:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}()}]),n}(u["default"]);e["default"]=new l},4024:function(t,e,n){"use strict";n("99af");e["a"]={methods:{mixinExportFormat:function(t,e){var n=!1;if(Array.isArray(t)){if(t[1]&&t[0]){var r=this.$day.valueOf(t[1])-this.$day.valueOf(t[0]);n=r<=26784e5}}else n=!!t;return n||this.mixinOpenModal(e,Array.isArray(t)),n},mixinOpenModal:function(t,e){this.$alert("最多可以导出1个月的数据，请调整 <strong>".concat(t,"</strong> 的时间").concat(e?"段":""),"温馨提示",{center:!0,dangerouslyUseHTMLString:!0})}}}},"4ae1":function(t,e,n){var r=n("23e7"),a=n("d066"),o=n("1c0b"),c=n("825a"),i=n("861d"),u=n("7c73"),s=n("0538"),l=n("d039"),f=a("Reflect","construct"),p=l((function(){function t(){}return!(f((function(){}),[],t)instanceof t)})),d=!l((function(){f((function(){}))})),b=p||d;r({target:"Reflect",stat:!0,forced:b,sham:b},{construct:function(t,e){o(t),c(e);var n=arguments.length<3?t:o(arguments[2]);if(d&&!p)return f(t,e,n);if(t==n){switch(e.length){case 0:return new t;case 1:return new t(e[0]);case 2:return new t(e[0],e[1]);case 3:return new t(e[0],e[1],e[2]);case 4:return new t(e[0],e[1],e[2],e[3])}var r=[null];return r.push.apply(r,e),new(s.apply(t,r))}var a=n.prototype,l=u(i(a)?a:Object.prototype),b=Function.apply.call(t,l,e);return i(b)?b:l}})},"4c82":function(t,e,n){},"53ca":function(t,e,n){"use strict";n.d(e,"a",(function(){return r}));n("a4d3"),n("e01a"),n("d28b"),n("d3b7"),n("3ca3"),n("ddb0");function r(t){return r="function"===typeof Symbol&&"symbol"===typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"===typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t},r(t)}},"53eb":function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"header-control"},[n("el-button",{staticClass:"g-form-submit",attrs:{type:"primary",size:"small"},on:{click:t.queryTap}},[t._v("查询")]),n("el-button",{staticClass:"g-form-reset",attrs:{type:"text",size:"small"},on:{click:t.resetTap}},[t._v("重置")]),n("div",{directives:[{name:"show",rawName:"v-show",value:t.showMore,expression:"showMore"}],staticClass:"g-form-more",on:{click:t.moreTap}},[n("span",[t._v("更多条件")]),n("i",{staticClass:"el-icon-arrow-down"})])],1)},a=[],o=n("f348"),c={props:{showMore:{type:Boolean,default:!0}},data:function(){return{}},methods:{moreTap:function(){this.$emit("moreTap")},queryTap:function(){this.$emit("queryTap")},resetTap:function(){this.$emit("resetTap")},reset:function(t){Object(o["f"])(t)}}},i=c,u=(n("b9db"),n("2877")),s=Object(u["a"])(i,r,a,!1,null,"7f45ee62",null);e["a"]=s.exports},"5f1a":function(t,e,n){},9383:function(t,e,n){"use strict";n.r(e),n.d(e,"default",(function(){return l}));n("96cf");var r=n("1da1"),a=n("d4ec"),o=n("bee2"),c=n("262e"),i=n("2caf"),u=n("c824"),s=n("f348"),l=function(t){Object(c["a"])(n,t);var e=Object(i["a"])(n);function n(){return Object(a["a"])(this,n),e.call(this)}return Object(o["a"])(n,[{key:"getOptions",value:function(){var t=Object(r["a"])(regeneratorRuntime.mark((function t(e){var n,r,a,o,c,i,u,l;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return n=e.api,r=e.valueKey,a=e.labelKey,o=e.others,c=e.params,t.prev=1,t.next=4,n(c);case 4:return i=t.sent,u=Object(s["b"])(i,r,a,o),l=Object(s["a"])(i,r),t.abrupt("return",{options:u,optionsData:l});case 10:return t.prev=10,t.t0=t["catch"](1),t.abrupt("return",{options:[],optionsData:{}});case 13:case"end":return t.stop()}}),t,null,[[1,10]])})));function e(e){return t.apply(this,arguments)}return e}()},{key:"beforeUpdate",value:function(){this.__cacheData&&0!==this.__cacheData.options.length||this.update()}}]),n}(u["default"])},"9840e":function(t,e,n){"use strict";n("99af"),n("d81d");var r=n("5530"),a=n("2909"),o=n("53ca"),c=n("b85c");n("4339");function i(t,e,n){return{disabledDate:function(r){return!!t[e]&&(n?r.getTime()>t[e]:r.getTime()<t[e])}}}function u(t,e,n){var r,a=Object(c["a"])(e);try{for(a.s();!(r=a.n()).done;){var o=r.value;t[o.key]=i(n,o.ckey,o.isLess)}}catch(u){a.e(u)}finally{a.f()}}var s,l,f=n("5a0c"),p=n.n(f),d={props:{data:{type:Array,default:function(){return[]}},bindData:{type:Object,default:function(){return{}}}},data:function(){return{}},render:function(t){return this.init(t)},methods:{init:function(t){var e,n={"input-number-between":this.createBetweenNumber,"date-picker-between":this.createBetweenDate,"input-text":this.createTextInput,split:this.createSplit,"date-picker":this.createDatePicker,input:this.createInput,select:this.createSelect,switch:this.createSwitch},r=[],i=Object(c["a"])(this.data);try{for(i.s();!(e=i.n()).done;){var u=e.value,s=u.title?[this.createLabel(t,u)]:[];if("object"===Object(o["a"])(u.type)){var l=this.createComponent(t,u),f=[].concat(s,[l]);r=r.concat(f)}else{var p=n[u.type],d=p(t,u);d=Array.isArray(d)?[].concat(s,Object(a["a"])(d)):[].concat(s,[d]),r=r.concat(d)}}}catch(b){i.e(b)}finally{i.f()}return this.createLineBox(t,r)},createLineBox:function(t,e){return t("div",{},e)},createComponent:function(t,e){return t(e.type,e)},createLabel:function(t,e){return t("div",{class:{"g-form-label":!0}},e.title)},createBetweenNumber:function(t,e){function n(t,e,n){return Object(r["a"])(Object(r["a"])({},t),{},{bindKey:e,class:Object(r["a"])({"g-form-item-right":n,"g-form-price":!0},t.class),props:Object(r["a"])({type:"number","suffix-icon":t.pct?" font_family icon-baifenbi g-font-24":""},t.props)})}var a=this.createInput(t,n(e,e.start)),o=this.createSplit(t),c=this.createInput(t,n(e,e.end,!0));return[a,o,c]},createBetweenDate:function(t,e){var n=e.bindData||this.bindData,a={},o=[{key:e.start,ckey:e.end,isLess:!0},{key:e.end,ckey:e.start,isLess:!1}];u(a,o,n);var c=this.createDatePicker(t,Object(r["a"])(Object(r["a"])({},e),{},{props:Object(r["a"])({"picker-options":a[e.start]},e.props),class:Object(r["a"])({"g-form-date":!0},e.class),bindKey:e.start})),i=this.createSplit(t),s=this.createDatePicker(t,Object(r["a"])(Object(r["a"])({},e),{},{props:Object(r["a"])({"picker-options":a[e.end]},e.props),class:Object(r["a"])({"g-form-date":!0,"g-form-item-right":e.endRight},e.class),bindKey:e.end,input:function(t,e,n){if(n){var r=n.getFullYear(),a=n.getMonth(),o=n.getDate();t[e]=p()().year(r).month(a).date(o).hour(23).minute(59).second(59).format("YYYY-MM-DD HH:mm:ss")}else t[e]=null}}));return[c,i,s]},createTextInput:function(t,e){return e.class=Object(r["a"])({"g-form-input":!0},e.class),[this.createInput(t,e)]},createSplit:function(t){return t("div",{class:{"g-form-split":!0}},"—")},createDatePicker:function(t,e){var n=e.bindData||this.bindData;return t("el-date-picker",{class:e.class,style:e.style,props:Object(r["a"])({value:n[e.bindKey],size:"small"},e.props),on:Object(r["a"])({input:function(t){if(e.input)e.input(n,e.bindKey,t);else if(t){var r=t.getFullYear(),a=t.getMonth(),o=t.getDate();n[e.bindKey]=p()().year(r).month(a).date(o).hour(0).minute(0).second(0).format("YYYY-MM-DD HH:mm:ss")}else n[e.bindKey]=null}},e.on)})},createSwitch:function(t,e){var n=e.bindData||this.bindData;return t("el-switch",{class:e.class,style:e.style,props:Object(r["a"])({value:n[e.bindKey]},e.props),on:Object(r["a"])({change:function(t){n[e.bindKey]=t}},e.on)})},createInput:function(t,e){var n=e.bindData||this.bindData;return t("el-input",{class:e.class,style:e.style,props:Object(r["a"])({value:n[e.bindKey],size:"small"},e.props),on:Object(r["a"])({input:function(t){n[e.bindKey]=t}},e.on)})},createSelect:function(t,e){var n=e.bindData||this.bindData,a=e.options.map((function(n){return t("el-option",{props:Object(r["a"])({value:n.value,label:n.label},e.props)})})),o=t("el-select",{class:e.class,style:e.style,props:Object(r["a"])({value:n[e.bindKey],size:"small"},e.props),on:Object(r["a"])({change:function(t){n[e.bindKey]=t}},e.on)},[a]);return o}}},b=d,m=n("2877"),h=Object(m["a"])(b,s,l,!1,null,"66ab11b4",null);e["a"]=h.exports},a416:function(t,e,n){"use strict";n("4c82")},b9db:function(t,e,n){"use strict";n("5f1a")},edef:function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"c-header-box"},[n("div",{staticClass:"header-content-box"},[n("div",{staticClass:"header-content"},[t._t("default")],2),n("div",{staticClass:"header-user"},[n("el-dropdown",{on:{command:t.dropItemTap}},[n("div",{staticClass:"user-dropdown-link"},[n("span",{staticClass:"name",attrs:{title:t.userInfo.name}},[t._v(t._s(t.userInfo.name))]),n("i",{staticClass:"el-icon-arrow-down"})]),n("el-dropdown-menu",{attrs:{slot:"dropdown"},slot:"dropdown"},[n("el-dropdown-item",{attrs:{command:"setting"}},[n("div",{staticClass:"g-pos-center"},[t._v("系统设置")])]),n("el-dropdown-item",{attrs:{command:"outTap"}},[n("div",{staticClass:"g-pos-center"},[t._v("退出登录")])])],1)],1)],1)]),n("div",[t._t("more")],2),n("OutLoginModal",{ref:"modal",on:{submitTap:t.outTap}})],1)},a=[],o=n("5530"),c=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("g-modal",{ref:"modal",attrs:{width:"496px",title:"温馨提示",iconColor:"#FF9931",icon:"el-icon-warning"}},[n("div",{staticClass:"g-pos-center"},[n("span",[t._v("请确认是否退出登录")])]),n("div",{attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{size:"small"},on:{click:t.hide}},[t._v("取消")]),n("el-button",{attrs:{loading:t.submitLoading,size:"small",type:"primary"},on:{click:t.submitTap}},[t._v("确认")])],1)])},i=[],u={data:function(){return{submitLoading:!1}},methods:{hide:function(){this.$refs.modal.hide()},show:function(){this.$refs.modal.show()},submitTap:function(){this.$emit("submitTap"),this.hide()}}},s=u,l=n("2877"),f=Object(l["a"])(s,c,i,!1,null,"62d54f6e",null),p=f.exports,d=n("ae6f"),b=n("2f62"),m={components:{OutLoginModal:p},data:function(){return{avatar:"/images/user.jpg"}},computed:Object(o["a"])({},Object(b["b"])(["userInfo"])),methods:{dropItemTap:function(t){if("outTap"===t)return this.show();this.$router.push("/home/".concat(t))},outTap:function(){d["default"].backLoginPage()},show:function(){this.$refs.modal.show()}}},h=m,v=(n("a416"),Object(l["a"])(h,r,a,!1,null,"6d6af18e",null));e["a"]=v.exports},f8b7:function(t,e,n){"use strict";n.d(e,"C",(function(){return c})),n.d(e,"x",(function(){return i})),n.d(e,"y",(function(){return u})),n.d(e,"z",(function(){return s})),n.d(e,"A",(function(){return l})),n.d(e,"w",(function(){return f})),n.d(e,"s",(function(){return p})),n.d(e,"d",(function(){return d})),n.d(e,"u",(function(){return b})),n.d(e,"E",(function(){return m})),n.d(e,"D",(function(){return h})),n.d(e,"v",(function(){return v})),n.d(e,"B",(function(){return y})),n.d(e,"t",(function(){return g})),n.d(e,"I",(function(){return O})),n.d(e,"H",(function(){return w})),n.d(e,"G",(function(){return j})),n.d(e,"a",(function(){return _})),n.d(e,"e",(function(){return T})),n.d(e,"b",(function(){return C})),n.d(e,"m",(function(){return k})),n.d(e,"F",(function(){return D})),n.d(e,"h",(function(){return x})),n.d(e,"i",(function(){return P})),n.d(e,"g",(function(){return S})),n.d(e,"f",(function(){return z})),n.d(e,"l",(function(){return K})),n.d(e,"j",(function(){return M})),n.d(e,"k",(function(){return I})),n.d(e,"r",(function(){return L})),n.d(e,"c",(function(){return R})),n.d(e,"J",(function(){return A})),n.d(e,"p",(function(){return $})),n.d(e,"n",(function(){return B})),n.d(e,"q",(function(){return Y})),n.d(e,"o",(function(){return F}));n("99af"),n("b0c0");var r=n("bc3a"),a=n.n(r),o=n("f121");function c(t){var e="/saleorder/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function i(t){var e="/orderprocess/purchOrder/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function u(t){var e="/orderprocess/recordin/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function s(t){var e="/orderprocess/recordout/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function l(t){var e="/orderprocess/saleout/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function f(t){var e="/orderprocess/saleBill/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function p(t){var e="/operation/operatingStatement/list";return a.a.post(e,t)}function d(t){var e="/operation/operatingStatement/update";return a.a.post(e,t)}function b(t){var e="/orderprocess/purchSaleBill/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function m(t){var e="/quotation/total/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function h(){var t=o["a"].base_url+"/quotation/fileUpload";return t}function v(t){var e="/orderCost/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function y(t){var e="/saleKpi/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function g(t){var e="/crmMgr/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function O(t){var e="/switchOrder/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function w(){var t=o["a"].base_url+"/switchOrder/fileUpload";return t}function j(t){var e="/switchOrder/download",n=t.name;return a()({method:"get",url:e,responseType:"blob",name:n})}function _(t){var e="/switchOrder/add";return a.a.post(e,t)}function T(t){var e="/switchOrder/edit";return a.a.post(e,t)}function C(t){var e="/switchOrder/del";return a.a.post(e,t)}function k(){var t="/comp/companyInfo/list";return a.a.post(t,{})}function D(t){var e="/saleorder/update";return a.a.post(e,t)}function x(t){var e="/saleorder/download/".concat(t.current,"/").concat(t.size),n=t.name;return a()({method:"post",url:e,data:t,responseType:"blob",name:n})}function P(t){var e="/orderprocess/purchSaleBill/download/".concat(t.current,"/").concat(t.size),n=t.name;return a()({method:"post",url:e,data:t,responseType:"blob",name:n})}function S(t){var e="/orderCost/download",n=t.name;return a()({method:"post",url:e,data:t,responseType:"blob",name:n})}function z(t){var e="/crmMgr/download",n=t.name;return a()({method:"post",url:e,data:t,responseType:"blob",name:n})}function K(t){var e="/switchOrder/download",n=t.name;return a()({method:"post",url:e,data:t,responseType:"blob",name:n})}function M(t){var e="/quotation/total/download",n=t.name;return a()({method:"post",url:e,data:t,responseType:"blob",name:n})}function I(t){var e="/saleKpi/download",n=t.name;return a()({method:"post",url:e,data:t,responseType:"blob",name:n})}function L(t){var e="/orderCost/listPage/getCven";return a.a.post(e,t)}function R(t){var e="/orderAmountMgr/download",n=t.name;return a()({method:"get",url:e,responseType:"blob",name:n})}function A(){var t=o["a"].base_url+"/orderAmountMgr/fileUpload";return t}function $(t){var e="/orderAmountMgr/listPage/".concat(t.current,"/").concat(t.size);return a.a.post(e,t)}function B(t){var e="/orderCost/listPage/getCCus";return a.a.post(e,t)}function Y(t){var e="/orderCost/listPage/getCperson";return a.a.post(e,t)}function F(t){var e="/operation/operatingStatement/subList";return a.a.post(e,t)}}}]);