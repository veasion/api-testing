(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-9f00bb52"],{"09f4":function(t,e,n){"use strict";n.d(e,"a",(function(){return l})),Math.easeInOutQuad=function(t,e,n,a){return t/=a/2,t<1?n/2*t*t+e:(t--,-n/2*(t*(t-2)-1)+e)};var a=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(t){window.setTimeout(t,1e3/60)}}();function i(t){document.documentElement.scrollTop=t,document.body.parentNode.scrollTop=t,document.body.scrollTop=t}function r(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function l(t,e,n){var l=r(),o=t-l,s=20,c=0;e="undefined"===typeof e?500:e;var u=function t(){c+=s;var r=Math.easeInOutQuad(c,l,o,e);i(r),c<e?a(t):n&&"function"===typeof n&&n()};u()}},"24d2":function(t,e,n){"use strict";n.r(e),n.d(e,"listPage",(function(){return i})),n.d(e,"list",(function(){return r})),n.d(e,"getById",(function(){return l})),n.d(e,"update",(function(){return o})),n.d(e,"add",(function(){return s})),n.d(e,"deleted",(function(){return c}));var a=n("b775");function i(t){return Object(a["a"])({url:"/api/project/listPage",method:"get",params:t})}function r(t){return Object(a["a"])({url:"/api/project/list",method:"get",params:t})}function l(t){return Object(a["a"])({url:"/api/project/getById?id="+t,method:"get"})}function o(t){return Object(a["a"])({url:"/api/project/update",method:"post",data:t})}function s(t){return Object(a["a"])({url:"/api/project/add",method:"post",data:t})}function c(t){return Object(a["a"])({url:"/api/project/delete",method:"post",headers:{"Content-Type":"application/json"},data:t})}},6724:function(t,e,n){"use strict";n("8d41");var a="@@wavesContext";function i(t,e){function n(n){var a=Object.assign({},e.value),i=Object.assign({ele:t,type:"hit",color:"rgba(0, 0, 0, 0.15)"},a),r=i.ele;if(r){r.style.position="relative",r.style.overflow="hidden";var l=r.getBoundingClientRect(),o=r.querySelector(".waves-ripple");switch(o?o.className="waves-ripple":(o=document.createElement("span"),o.className="waves-ripple",o.style.height=o.style.width=Math.max(l.width,l.height)+"px",r.appendChild(o)),i.type){case"center":o.style.top=l.height/2-o.offsetHeight/2+"px",o.style.left=l.width/2-o.offsetWidth/2+"px";break;default:o.style.top=(n.pageY-l.top-o.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",o.style.left=(n.pageX-l.left-o.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return o.style.backgroundColor=i.color,o.className="waves-ripple z-active",!1}}return t[a]?t[a].removeHandle=n:t[a]={removeHandle:n},n}var r={bind:function(t,e){t.addEventListener("click",i(t,e),!1)},update:function(t,e){t.removeEventListener("click",t[a].removeHandle,!1),t.addEventListener("click",i(t,e),!1)},unbind:function(t){t.removeEventListener("click",t[a].removeHandle,!1),t[a]=null,delete t[a]}},l=function(t){t.directive("waves",r)};window.Vue&&(window.waves=r,Vue.use(l)),r.install=l;e["a"]=r},7309:function(t,e,n){"use strict";n.d(e,"a",(function(){return i})),n.d(e,"b",(function(){return r}));var a=n("b775");function i(t){return Object(a["a"])({url:"/api/script/runScript",method:"post",data:t})}function r(t){return Object(a["a"])({url:"/api/script/toScript",method:"get",params:t})}},"8d41":function(t,e,n){},b36c:function(t,e,n){"use strict";n.r(e),n.d(e,"listPage",(function(){return i})),n.d(e,"list",(function(){return r})),n.d(e,"getById",(function(){return l})),n.d(e,"update",(function(){return o})),n.d(e,"add",(function(){return s})),n.d(e,"deleted",(function(){return c}));var a=n("b775");function i(t){return Object(a["a"])({url:"/api/apiTestCase/listPage",method:"get",params:t})}function r(t){return Object(a["a"])({url:"/api/apiTestCase/list",method:"get",params:t})}function l(t){return Object(a["a"])({url:"/api/apiTestCase/getById?id="+t,method:"get"})}function o(t){return Object(a["a"])({url:"/api/apiTestCase/update",method:"post",data:t})}function s(t){return Object(a["a"])({url:"/api/apiTestCase/add",method:"post",data:t})}function c(t){return Object(a["a"])({url:"/api/apiTestCase/delete",method:"post",headers:{"Content-Type":"application/json"},data:t})}},ccfd:function(t,e,n){"use strict";n.r(e);var a=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("el-input",{staticClass:"filter-item",staticStyle:{width:"150px"},attrs:{placeholder:"用例名称"},model:{value:t.listQuery.caseName,callback:function(e){t.$set(t.listQuery,"caseName",e)},expression:"listQuery.caseName"}}),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"150px"},attrs:{placeholder:"用例描述"},model:{value:t.listQuery.caseDesc,callback:function(e){t.$set(t.listQuery,"caseDesc",e)},expression:"listQuery.caseDesc"}}),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"120px"},attrs:{placeholder:"模块"},model:{value:t.listQuery.module,callback:function(e){t.$set(t.listQuery,"module",e)},expression:"listQuery.module"}}),t._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"120px"},attrs:{placeholder:"作者"},model:{value:t.listQuery.author,callback:function(e){t.$set(t.listQuery,"author",e)},expression:"listQuery.author"}}),t._v(" "),n("el-select",{staticClass:"filter-item",attrs:{clearable:"",filterable:"",placeholder:"所属项目"},model:{value:t.listQuery.projectId,callback:function(e){t.$set(t.listQuery,"projectId",e)},expression:"listQuery.projectId"}},t._l(t.projectList,(function(t){return n("el-option",{key:t.id,attrs:{label:t.name,value:t.id}})})),1),t._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:t.fetchData}},[t._v("\n      搜索\n    ")]),t._v(" "),n("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"10px"},attrs:{type:"primary",icon:"el-icon-edit"},on:{click:t.handleCreate}},[t._v("\n      添加\n    ")])],1),t._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.listLoading,expression:"listLoading"}],attrs:{data:t.list,"element-loading-text":"Loading",border:"",fit:"","highlight-current-row":""}},[n("el-table-column",{attrs:{label:"项目名称",align:"center",width:"120px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.projectName))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"用例名称",align:"center",width:"150px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.caseName))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"用例描述",align:"center",width:"150px"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.caseDesc))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"模块",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.module))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"作者",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.author))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"是否启用",align:"center",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-switch",{attrs:{"active-color":"#00A854","active-text":"启用","active-value":1,"inactive-color":"#F04134","inactive-text":"停用","inactive-value":0},on:{change:function(n){return t.changeSwitch(e.row)}},model:{value:e.row.isAvailable,callback:function(n){t.$set(e.row,"isAvailable",n)},expression:"scope.row.isAvailable"}})]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"创建用户",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.createUsername)+"\n      ")]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"创建时间",width:"200",align:"center"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.createTime))]}}])}),t._v(" "),n("el-table-column",{attrs:{label:"操作",align:"center",width:"230","class-name":"small-padding fixed-width"},scopedSlots:t._u([{key:"default",fn:function(e){var a=e.row;return[n("el-button",{attrs:{size:"mini"},on:{click:function(e){return t.runScript(a)}}},[t._v("\n          测试\n        ")]),t._v(" "),n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(e){return t.handleUpdate(a)}}},[t._v("\n          编辑\n        ")]),t._v(" "),n("el-button",{attrs:{slot:"reference",size:"mini",type:"danger"},on:{click:function(e){return t.handleDelete(a)}},slot:"reference"},[t._v("\n          删除\n        ")])]}}])})],1),t._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:t.total>0,expression:"total>0"}],attrs:{total:t.total,page:t.listQuery.pageNo,limit:t.listQuery.pageSize},on:{"update:page":function(e){return t.$set(t.listQuery,"pageNo",e)},"update:limit":function(e){return t.$set(t.listQuery,"pageSize",e)},pagination:t.fetchData}}),t._v(" "),n("script-execute",{ref:"scriptExecute"})],1)},i=[],r=n("24d2"),l=n("b36c"),o=n("333d"),s=n("45d1"),c=n("6724"),u={name:"TestCase",components:{Pagination:o["a"],ScriptExecute:s["a"]},directives:{waves:c["a"]},filters:{},data:function(){return{list:null,listLoading:!0,total:0,listQuery:{pageNo:1,pageSize:10,projectId:null,caseName:"",caseDesc:"",module:"",author:""},projectList:[]}},created:function(){this.init()},methods:{init:function(){var t=this;this.fetchData(),Object(r["list"])({isAvailable:1}).then((function(e){t.projectList=e.data||[]}))},fetchData:function(){var t=this;this.listLoading=!0,l["listPage"](this.listQuery).then((function(e){t.total=e.total,t.list=e.data,t.listLoading=!1}))},handleCreate:function(){this.$router.push({name:"TestCaseConfig",params:{id:""}})},handleUpdate:function(t){this.$router.push({name:"TestCaseConfig",params:{id:t.id}})},changeSwitch:function(t){var e=this;l["update"]({id:t.id,isAvailable:t.isAvailable}).then((function(){e.fetchData(),e.$notify({title:"Success",message:"操作成功",type:"success",duration:2e3})}))},updateData:function(){var t=Object.assign({},this.temp);this.saveOrUpdate(t,!0)},handleDelete:function(t){var e=this;this.$confirm("确定删除吗？").then((function(){l["deleted"](t.id).then((function(t){e.fetchData(),e.$notify({title:"Success",message:"删除成功",type:"success",duration:2e3})}))}))},runScript:function(t){this.$refs.scriptExecute.show(t.projectId,t.script||"")}}},d=u,p=n("2877"),f=Object(p["a"])(d,a,i,!1,null,null,null);e["default"]=f.exports}}]);