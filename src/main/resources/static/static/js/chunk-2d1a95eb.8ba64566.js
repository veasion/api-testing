(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d1a95eb"],{"09f4":function(t,e,n){"use strict";n.d(e,"a",(function(){return s})),Math.easeInOutQuad=function(t,e,n,r){return t/=r/2,t<1?n/2*t*t+e:(t--,-n/2*(t*(t-2)-1)+e)};var r=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function i(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function a(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function s(t,e,n){var s=a(),u=t-s,l=20,o=0;e="undefined"===typeof e?500:e;var c=function t(){o+=l;var a=Math.easeInOutQuad(o,s,u,e);i(a),o<e?r(t):n&&"function"===typeof n&&n()};c()}},"24d2":function(t,e,n){"use strict";n.r(e),n.d(e,"listPage",(function(){return i})),n.d(e,"list",(function(){return a})),n.d(e,"getById",(function(){return s})),n.d(e,"update",(function(){return u})),n.d(e,"add",(function(){return l})),n.d(e,"deleted",(function(){return o}));var r=n("b775");function i(t){return Object(r["a"])({url:"/api/project/listPage",method:"get",params:t})}function a(t){return Object(r["a"])({url:"/api/project/list",method:"get",params:t})}function s(t){return Object(r["a"])({url:"/api/project/getById?id="+t,method:"get"})}function u(t){return Object(r["a"])({url:"/api/project/update",method:"post",data:t})}function l(t){return Object(r["a"])({url:"/api/project/add",method:"post",data:t})}function o(t){return Object(r["a"])({url:"/api/project/delete",method:"post",headers:{"Content-Type":"application/json"},data:t})}},"5b39":function(t,e,n){"use strict";n.d(e,"a",(function(){return i})),n.d(e,"b",(function(){return d}));n("8dee"),n("fc02"),n("5199"),n("d31c"),n("1bc7"),n("32ea");var r={if:"if (%code) {\r\n\r\n}",null:"if (%code != null) {\r\n\r\n}",var:"let %var = %code\r\n",for:"for (const i in %code) {\r\n\r\n}",fori:"for (let i = 0; i < %code.length; i++) {\r\n\r\n}",try:"try{\r\n\t%code\r\n} catch (e) {\r\n\tlog.error('发生错误')\r\n}"};function i(t,e,n,r,i){if(t&&e&&((null==n||n<0)&&(n=e.length),0!==n)){for(var l=e.substring(0,n),d=e.substring(n),p=!1,f=0,h=n;h>=0;h--){if(" "===l[h]||";"===l[h]){f=h+1;break}if("{"===l[h]&&h>0&&"$"===l[h-1]){p=!0,f=h+1;break}if(("'"===l[h]||'"'===l[h])&&t.apiNameMap){var m=l[h-1];if(" "===m||"\n"===m||","===m||"{"===m){var v=l.substring(h+1,n),b=c(i,r);if(b&&t.apiNameMap[b]){for(var g=Object.keys(t.apiNameMap[b])||[],y=[],w=0;w<g.length;w++){var j=g[w]+"";j.startsWith(v)&&j!==v&&y.push({displayText:j,text:j.substring(v.length)})}return y}}}}var x=l.substring(f,n);if(x.startsWith("http.request('")){var _="";return""===d.trim()?_="')":")"===d.trim()&&(_="'"),s(a(t.apiNames,x.substring("http.request('".length),_),x,f)}if(x.startsWith('http.request("')){var O="";return""===d.trim()?O='")':")"===d.trim()&&(O='"'),s(a(t.apiNames,x.substring('http.request("'.length),O),x,f)}for(var k=x.endsWith("."),T=x.split("."),C=p?t.globalMap:t.varCodes,S=0;S<T.length;S++){var N=null;if((S<T.length-1||k)&&(N=C[T[S]]),!N){var Q=Object.keys(C)||[];C=[];for(var q=0;q<Q.length;q++){var I="",$=Q[q]+"";p&&""===d.trim()&&(I="}"),$.startsWith(T[S])&&$!==T[S]&&C.push({displayText:$,text:$.substring(T[S].length)+I})}return 0===S&&0===C.length&&(C=o(x,k,t.tempVar,r)),u(C,x,f)}C=N}return u(Object.keys(C),x,f)}}function a(t,e,n){if(n=n||"",null==t||0===t.length)return null;var r=[];for(var i in t){var a=t[i];t[i].startsWith(e)&&r.push({displayText:a,text:a.substring(e.length)+n})}return r}function s(t,e,n){return null==t||0===t.length?u(t,e,n):t}function u(t,e,n){t=t||[];var i=e.lastIndexOf(".");if(i<1)return t;var a=e.substring(0,i),s=e.substring(i+1),u=Object.keys(r);for(var o in u){var c=u[o];if(c.startsWith(s)){var d=r[c].replace("%code",a);d.indexOf("%var")>-1&&(d=d.replace("%var","v"+l(3))),t.push({displayText:c,text:d,replace:!0,startIndex:n})}}return t}function l(t){for(var e="",n=0;n<t;n++)e+=Math.floor(10*Math.random());return e}function o(t,e,n,r){for(var i=n,a=t.replaceAll("[",".[").split("."),s=0;s<a.length;s++){var u=null;if(s<a.length-1||e){var l=a[s].trim();if(Array.isArray(i)){if(!l.startsWith("[")||!l.endsWith("]")){if(""===l){var o=[];for(var c in i){if(c>10)break;var d=e?Object.assign({},r,{ch:r.ch-1}):null,p=e?Object.assign({},r,{ch:r.ch}):null;o.push({displayText:"["+c+"]",text:"["+c+"]",from:d,to:p})}return o}return[]}u=i[parseInt(l.substring(1,l.length-1))]}else u=i[l]}if(!u){if("string"===typeof i)return[];var f=Object.keys(i)||[];i=[];for(var h=0;h<f.length;h++){var m=f[h]+"";m.startsWith(a[s])&&m!==a[s]&&i.push({displayText:m,text:m.substring(a[s].length)})}return i}i=u}return i}function c(t,e){for(var n=t.split("\n"),r=-1,i=e.line;i>=0;i--)if(r=n[i].lastIndexOf("http.request("),-1!==r){if(i===e.line&&n[i].substring(0,e.ch).indexOf(")")>0)return;if(i<e.line&&n[i].substring(r).indexOf(")")>0)return;t=n[i];break}if(!(r<0)){var a=t.substring(r+"http.request(".length).trim();if(("'"===a[0]||'"'===a[0])&&(a=p(a,a[0]),a&&a.trim()))return a.trim()}}function d(t){var e=-1,n=0,r={},i="http.request(";while((e=t.indexOf(i,e+1))>-1){var a=t.substring(n,e).trim();if(a.endsWith("=")){a=a.substring(0,a.length-1).trim(),a=f(a);var s=t.substring(e+i.length).trim();"'"!==s[0]&&'"'!==s[0]||(s=p(s,s[0]),s&&s.trim()&&(r[a]=s.trim(),n=e+i.length))}}return r}function p(t,e,n){null==n&&(n=0);var r=t.indexOf(e,n);if(r<0)return null;var i=t.indexOf(e,r+1);return i<0?null:(t=t.substring(r+1,i),t.indexOf("\n")>0?null:t)}function f(t,e){var n=-1;null==e&&(e=t.length-1);for(var r=e;r>=0;r--)if(" "===t[r]||"\n"===t[r]){n=r+1;break}return n>0?t.substring(n,e+1).trim():t.substring(0,e+1).trim()}},6724:function(t,e,n){"use strict";n("8d41");var r="@@wavesContext";function i(t,e){function n(n){var r=Object.assign({},e.value),i=Object.assign({ele:t,type:"hit",color:"rgba(0, 0, 0, 0.15)"},r),a=i.ele;if(a){a.style.position="relative",a.style.overflow="hidden";var s=a.getBoundingClientRect(),u=a.querySelector(".waves-ripple");switch(u?u.className="waves-ripple":(u=document.createElement("span"),u.className="waves-ripple",u.style.height=u.style.width=Math.max(s.width,s.height)+"px",a.appendChild(u)),i.type){case"center":u.style.top=s.height/2-u.offsetHeight/2+"px",u.style.left=s.width/2-u.offsetWidth/2+"px";break;default:u.style.top=(n.pageY-s.top-u.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",u.style.left=(n.pageX-s.left-u.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return u.style.backgroundColor=i.color,u.className="waves-ripple z-active",!1}}return t[r]?t[r].removeHandle=n:t[r]={removeHandle:n},n}var a={bind:function(t,e){t.addEventListener("click",i(t,e),!1)},update:function(t,e){t.removeEventListener("click",t[r].removeHandle,!1),t.addEventListener("click",i(t,e),!1)},unbind:function(t){t.removeEventListener("click",t[r].removeHandle,!1),t[r]=null,delete t[r]}},s=function(t){t.directive("waves",a)};window.Vue&&(window.waves=a,Vue.use(s)),a.install=s;e["a"]=a},7309:function(t,e,n){"use strict";n.d(e,"d",(function(){return i})),n.d(e,"e",(function(){return a})),n.d(e,"a",(function(){return s})),n.d(e,"c",(function(){return u})),n.d(e,"b",(function(){return l}));var r=n("b775");function i(t){return Object(r["a"])({url:"/api/script/runScript",method:"post",data:t})}function a(t){return Object(r["a"])({url:"/api/script/toScript",method:"get",params:t})}function s(t){return Object(r["a"])({url:"/api/script/apiNameTips",method:"get",params:t})}function u(){return Object(r["a"])({url:"/api/script/codeTips",method:"get"})}function l(t,e){return Object(r["a"])({url:"/api/script/apiResponseTips/"+(t||""),method:"post",data:e})}},"8d41":function(t,e,n){},b36c:function(t,e,n){"use strict";n.r(e),n.d(e,"listPage",(function(){return i})),n.d(e,"list",(function(){return a})),n.d(e,"getById",(function(){return s})),n.d(e,"update",(function(){return u})),n.d(e,"add",(function(){return l})),n.d(e,"deleted",(function(){return o}));var r=n("b775");function i(t){return Object(r["a"])({url:"/api/apiTestCase/listPage",method:"get",params:t})}function a(t){return Object(r["a"])({url:"/api/apiTestCase/list",method:"get",params:t})}function s(t){return Object(r["a"])({url:"/api/apiTestCase/getById?id="+t,method:"get"})}function u(t){return Object(r["a"])({url:"/api/apiTestCase/update",method:"post",data:t})}function l(t){return Object(r["a"])({url:"/api/apiTestCase/add",method:"post",data:t})}function o(t){return Object(r["a"])({url:"/api/apiTestCase/delete",method:"post",headers:{"Content-Type":"application/json"},data:t})}},ccfd:function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("el-input",{staticClass:"filter-item",staticStyle:{width:"150px"},attrs:{placeholder:"用例名称"},model:{value:t.listQuery.caseName,callback:function(e){t.$set(t.listQuery,"caseName",e)},expression:"listQuery.caseName"}}),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"150px"},attrs:{placeholder:"用例描述"},model:{value:t.listQuery.caseDesc,callback:function(e){t.$set(t.listQuery,"caseDesc",e)},expression:"listQuery.caseDesc"}}),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"120px"},attrs:{placeholder:"模块"},model:{value:t.listQuery.module,callback:function(e){t.$set(t.listQuery,"module",e)},expression:"listQuery.module"}}),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"120px"},attrs:{placeholder:"作者"},model:{value:t.listQuery.author,callback:function(e){t.$set(t.listQuery,"author",e)},expression:"listQuery.author"}}),t._v(" "),n("el-select",{staticClass:"filter-item",attrs:{clearable:"",filterable:"",placeholder:"所属项目"},model:{value:t.listQuery.projectId,callback:function(e){t.$set(t.listQuery,"projectId",e)},expression:"listQuery.projectId"}},t._l(t.projectList,(function(t){return n("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})})),1),t._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:t.fetchData}},[t._v("\n      搜索\n    ")]),t._v(" "),n("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"10px"},attrs:{type:"primary",icon:"el-icon-edit"},on:{click:t.handleCreate}},[t._v("\n      添加\n    ")])],1),t._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],attrs:{data:t.list,"element-loading-text":"Loading",border:"",fit:"","highlight-current-row":""}},[n("el-table-column",{attrs:{label:"项目名称",align:"center",width:"120px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.projectName))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"用例名称",align:"center",width:"150px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.caseName))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"用例描述",align:"center",width:"150px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.caseDesc))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"模块",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.module))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"作者",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.author))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"是否启用",align:"center",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-switch",{attrs:{"active-color":"#00A854","active-text":"启用","active-value":1,"inactive-color":"#F04134","inactive-text":"停用","inactive-value":0},on:{change:function(n){return t.changeSwitch(e.row)}},model:{value:e.row.isAvailable,callback:function(n){t.$set(e.row,"isAvailable",n)},expression:"scope.row.isAvailable"}})]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"创建用户",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.createUsername)+"\n      ")]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"创建时间",width:"200",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.createTime))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"操作",align:"center",width:"230","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){var r=e.row;return[n("el-button",{attrs:{size:"mini"},on:{click:function(e){return t.runScript(r)}}},[t._v("\n          测试\n        ")]),t._v(" "),n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(e){return t.handleUpdate(r)}}},[t._v("\n          编辑\n        ")]),t._v(" "),n("el-button",{attrs:{slot:"reference",size:"mini",type:"danger"},on:{click:function(e){return t.handleDelete(r)}},slot:"reference"},[t._v("\n          删除\n        ")])]}}])})],1),t._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.listQuery.pageNo,limit:t.listQuery.pageSize},on:{"update:page":function(e){return t.$set(t.listQuery,"pageNo",e)},"update:limit":function(e){return t.$set(t.listQuery,"pageSize",e)},pagination:t.fetchData}}),t._v(" "),n("script-execute",{ref:"scriptExecute"})],1)},i=[],a=n("24d2"),s=n("b36c"),u=n("333d"),l=n("45d1"),o=n("6724"),c={name:"TestCase",components:{Pagination:u["a"],ScriptExecute:l["a"]},directives:{waves:o["a"]},filters:{},data:function(){return{list:null,listLoading:!0,total:0,listQuery:{pageNo:1,pageSize:10,projectId:null,caseName:"",caseDesc:"",module:"",author:""},projectList:[]}},created:function(){this.init()},methods:{init:function(){var t=this;this.fetchData(),Object(a["list"])({isAvailable:1}).then((function(e){t.projectList=e.data||[]}))},fetchData:function(){var t=this;this.listLoading=!0,s["listPage"](this.listQuery).then((function(e){t.total=e.total,t.list=e.data,t.listLoading=!1}))},handleCreate:function(){this.$router.push({name:"TestCaseConfig",params:{id:0}})},handleUpdate:function(t){this.$router.push({name:"TestCaseConfig",params:{id:t.id}})},changeSwitch:function(t){var e=this;s["update"]({id:t.id,isAvailable:t.isAvailable}).then((function(){e.fetchData(),e.$notify({title:"Success",message:"操作成功",type:"success",duration:2e3})}))},updateData:function(){var t=Object.assign({},this.temp);this.saveOrUpdate(t,!0)},handleDelete:function(t){var e=this;this.$confirm("确定删除吗？").then((function(){s["deleted"](t.id).then((function(t){e.fetchData(),e.$notify({title:"Success",message:"删除成功",type:"success",duration:2e3})}))}))},runScript:function(t){this.$refs.scriptExecute.show(t.projectId,t.script||"")}}},d=c,p=n("cba8"),f=Object(p["a"])(d,r,i,!1,null,null,null);e["default"]=f.exports},ceb9:function(t,e,n){"use strict";n.d(e,"d",(function(){return i})),n.d(e,"c",(function(){return a})),n.d(e,"e",(function(){return s})),n.d(e,"f",(function(){return u})),n.d(e,"a",(function(){return l})),n.d(e,"b",(function(){return o}));var r=n("b775");function i(t){return Object(r["a"])({url:"/api/apiRequest/listPage",method:"get",params:t})}function a(t){return Object(r["a"])({url:"/api/apiRequest/list",method:"get",params:t})}function s(t,e){return Object(r["a"])({url:"/api/apiRequest/queryByApiName?apiName="+t+"&projectId="+(e||""),method:"get"})}function u(t){return Object(r["a"])({url:"/api/apiRequest/update",method:"post",data:t})}function l(t){return Object(r["a"])({url:"/api/apiRequest/add",method:"post",data:t})}function o(t){return Object(r["a"])({url:"/api/apiRequest/delete",method:"post",headers:{"Content-Type":"application/json"},data:t})}}}]);