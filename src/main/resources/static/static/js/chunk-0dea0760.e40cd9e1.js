(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0dea0760"],{"09f4":function(e,t,n){"use strict";n.d(t,"a",(function(){return s})),Math.easeInOutQuad=function(e,t,n,r){return e/=r/2,e<1?n/2*e*e+t:(e--,-n/2*(e*(e-2)-1)+t)};var r=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function i(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function a(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function s(e,t,n){var s=a(),o=e-s,l=20,c=0;t="undefined"===typeof t?500:t;var u=function e(){c+=l;var a=Math.easeInOutQuad(c,s,o,t);i(a),c<t?r(e):n&&"function"===typeof n&&n()};u()}},1622:function(e,t,n){(function(e){e(n("012b"))})((function(e){"use strict";e.registerHelper("lint","json",(function(t){var n=[];if(!window.jsonlint)return window.console&&window.console.error("Error: window.jsonlint not defined, CodeMirror JSON linting cannot run."),n;var r=window.jsonlint.parser||window.jsonlint;r.parseError=function(t,r){var i=r.loc;n.push({from:e.Pos(i.first_line-1,i.first_column),to:e.Pos(i.last_line-1,i.last_column),message:t})};try{r.parse(t)}catch(i){}return n}))}))},"24d2":function(e,t,n){"use strict";n.r(t),n.d(t,"listPage",(function(){return i})),n.d(t,"list",(function(){return a})),n.d(t,"getById",(function(){return s})),n.d(t,"update",(function(){return o})),n.d(t,"add",(function(){return l})),n.d(t,"deleted",(function(){return c}));var r=n("b775");function i(e){return Object(r["a"])({url:"/api/project/listPage",method:"get",params:e})}function a(e){return Object(r["a"])({url:"/api/project/list",method:"get",params:e})}function s(e){return Object(r["a"])({url:"/api/project/getById?id="+e,method:"get"})}function o(e){return Object(r["a"])({url:"/api/project/update",method:"post",data:e})}function l(e){return Object(r["a"])({url:"/api/project/add",method:"post",data:e})}function c(e){return Object(r["a"])({url:"/api/project/delete",method:"post",headers:{"Content-Type":"application/json"},data:e})}},"436f":function(e,t){e.exports=function(e){function t(e){"undefined"!==typeof console&&(console.error||console.log)("[Script Loader]",e)}function n(){return"undefined"!==typeof attachEvent&&"undefined"===typeof addEventListener}try{"undefined"!==typeof execScript&&n()?execScript(e):"undefined"!==typeof eval?eval.call(null,e):t("EvalError: No eval function available")}catch(r){t(r)}}},"548c":function(e,t,n){n("436f")(n("f7f4"))},6515:function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("el-input",{staticClass:"filter-item",staticStyle:{width:"150px"},attrs:{placeholder:"URL"},model:{value:e.listQuery.url,callback:function(t){e.$set(e.listQuery,"url",t)},expression:"listQuery.url"}}),e._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"150px"},attrs:{placeholder:"api命名"},model:{value:e.listQuery.apiName,callback:function(t){e.$set(e.listQuery,"apiName",t)},expression:"listQuery.apiName"}}),e._v(" "),n("el-input",{staticClass:"filter-item",staticStyle:{width:"150px"},attrs:{placeholder:"请求描述"},model:{value:e.listQuery.apiDesc,callback:function(t){e.$set(e.listQuery,"apiDesc",t)},expression:"listQuery.apiDesc"}}),e._v(" "),n("el-select",{staticClass:"filter-item",attrs:{clearable:"",filterable:"",placeholder:"所属项目"},model:{value:e.listQuery.projectId,callback:function(t){e.$set(e.listQuery,"projectId",t)},expression:"listQuery.projectId"}},e._l(e.projectList,(function(e){return n("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1),e._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.fetchData}},[e._v("\n      搜索\n    ")]),e._v(" "),n("el-button",{staticClass:"filter-item",staticStyle:{"margin-left":"10px"},attrs:{type:"primary",icon:"el-icon-edit"},on:{click:e.handleCreate}},[e._v("\n      添加\n    ")])],1),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],attrs:{data:e.list,"element-loading-text":"Loading",border:"",fit:"","highlight-current-row":""}},[n("el-table-column",{attrs:{label:"项目名称",align:"center",width:"120px"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.projectName))]}}])}),e._v(" "),n("el-table-column",{attrs:{"show-overflow-tooltip":!0,label:"URL",align:"center",width:"300px"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.url))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"api命名",align:"center",width:"150px"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.apiName))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"请求描述",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.apiDesc))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"请求方法",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.method))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"创建用户",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.createUsername)+"\n      ")]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"创建时间",width:"200",align:"center"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.createTime))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作",align:"center",width:"230","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){var r=t.row;return[n("el-button",{attrs:{size:"mini"},on:{click:function(t){return e.runScript(r)}}},[e._v("\n          测试\n        ")]),e._v(" "),n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(t){return e.handleUpdate(r)}}},[e._v("\n          编辑\n        ")]),e._v(" "),n("el-button",{attrs:{slot:"reference",size:"mini",type:"danger"},on:{click:function(t){return e.handleDelete(r)}},slot:"reference"},[e._v("\n          删除\n        ")])]}}])})],1),e._v(" "),n("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.listQuery.pageNo,limit:e.listQuery.pageSize},on:{"update:page":function(t){return e.$set(e.listQuery,"pageNo",t)},"update:limit":function(t){return e.$set(e.listQuery,"pageSize",t)},pagination:e.fetchData}}),e._v(" "),n("el-dialog",{attrs:{title:e.textMap[e.dialogStatus],visible:e.dialogFormVisible,width:"1000px","before-close":e.handleClose},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[n("el-form",{ref:"dataForm",attrs:{rules:e.rules,model:e.temp,"label-position":"left","label-width":"110px"}},[n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:8}},[n("el-form-item",{attrs:{label:"请求描述",prop:"apiDesc"}},[n("el-input",{attrs:{size:"medium",placeholder:"请输入描述"},model:{value:e.temp.apiDesc,callback:function(t){e.$set(e.temp,"apiDesc",t)},expression:"temp.apiDesc"}})],1)],1),e._v(" "),n("el-col",{attrs:{span:8}},[n("el-form-item",{attrs:{label:"请求方法",prop:"method"}},[n("el-select",{attrs:{placeholder:"请选择"},on:{change:e.changeMethod},model:{value:e.temp.method,callback:function(t){e.$set(e.temp,"method",t)},expression:"temp.method"}},e._l(e.methodList,(function(e){return n("el-option",{key:e.method,attrs:{label:e.method,value:e.method}})})),1)],1)],1),e._v(" "),n("el-col",{attrs:{span:8}},[n("el-form-item",{attrs:{label:"所属项目",prop:"projectId"}},[n("el-select",{staticClass:"filter-item",attrs:{filterable:"",placeholder:"请选择"},model:{value:e.temp.projectId,callback:function(t){e.$set(e.temp,"projectId",t)},expression:"temp.projectId"}},e._l(e.projectList,(function(e){return n("el-option",{key:e.id,attrs:{label:e.name,value:e.id}})})),1)],1)],1)],1),e._v(" "),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:24}},[n("el-form-item",{attrs:{label:"请求url",prop:"url"}},[n("el-input",{attrs:{placeholder:"${baseUrl}/user/getById?id=${userId}"},model:{value:e.temp.url,callback:function(t){e.$set(e.temp,"url",t)},expression:"temp.url"}})],1)],1)],1),e._v(" "),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:12}},[n("el-form-item",{attrs:{prop:"apiName"}},[n("span",{attrs:{slot:"label"},slot:"label"},[e._v("\n              api 命名\n              "),n("el-tooltip",{staticClass:"item",attrs:{effect:"dark",placement:"top-start",content:"命名确保在项目中唯一，脚本中通过 http.request('"+(e.temp.apiName||"xxx")+"'); 请求当前接口"}},[n("i",{staticClass:"el-icon-question"})])],1),e._v(" "),n("el-input",{attrs:{placeholder:"请输入api命名，唯一，如: /user/getById"},model:{value:e.temp.apiName,callback:function(t){e.$set(e.temp,"apiName",t)},expression:"temp.apiName"}})],1)],1),e._v(" "),e.hasBodyMethod(e.temp.method)?n("el-col",{attrs:{span:12,prop:"contentType"}},[n("el-form-item",{attrs:{label:"Content-Type",prop:"contentType"}},[n("el-select",{attrs:{placeholder:"请选择"},model:{value:e.temp.contentType,callback:function(t){e.$set(e.temp,"contentType",t)},expression:"temp.contentType"}},e._l(e.contentTypeList,(function(e){return n("el-option",{key:e.contentType,attrs:{label:e.contentType,value:e.contentType}})})),1)],1)],1):e._e()],1),e._v(" "),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:24}},[n("el-form-item",{attrs:{label:"请求头",prop:"headersJson"}},[n("el-input",{attrs:{placeholder:e.headersPlaceholder},model:{value:e.temp.headersJson,callback:function(t){e.$set(e.temp,"headersJson",t)},expression:"temp.headersJson"}})],1)],1)],1)],1),e._v(" "),e.hasBodyMethod(e.temp.method)?n("div",{staticStyle:{color:"#606266","font-size":"14px","margin-bottom":"15px","font-weight":"bold"}},[e._v("请求 Body：")]):e._e(),e._v(" "),e.hasBodyMethod(e.temp.method)&&"application/json"===e.temp.contentType?n("json-editor",{ref:"jsonEditor",model:{value:e.temp.body,callback:function(t){e.$set(e.temp,"body",t)},expression:"temp.body"}}):e.hasBodyMethod(e.temp.method)?n("text-editor",{ref:"textEditor",model:{value:e.temp.body,callback:function(t){e.$set(e.temp,"body",t)},expression:"temp.body"}}):e._e(),e._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("\n        取消\n      ")]),e._v(" "),n("el-button",{on:{click:e.formTestApi}},[e._v("\n        测试\n      ")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(t){"create"===e.dialogStatus?e.createData():e.updateData()}}},[e._v("\n        确认\n      ")])],1)],1),e._v(" "),n("script-execute",{ref:"scriptExecute"})],1)},i=[],a=(n("8dee"),n("ceb9")),s=n("24d2"),o=n("333d"),l=n("fa7e"),c=n("c4224"),u=n("45d1"),p=n("6724"),d={name:"Api",components:{Pagination:o["a"],JsonEditor:l["a"],TextEditor:c["a"],ScriptExecute:u["a"]},directives:{waves:p["a"]},filters:{},data:function(){return{list:null,canUpdate:!0,listLoading:!0,total:0,listQuery:{pageNo:1,pageSize:10,projectId:null,url:"",apiName:"",apiDesc:""},projectList:[],dialogFormVisible:!1,dialogStatus:"",textMap:{update:"编辑API请求",create:"新增API请求"},methodList:[{method:"GET"},{method:"POST"},{method:"PUT"},{method:"PATCH"},{method:"DELETE"}],contentTypeList:[{contentType:"application/json"},{contentType:"application/x-www-form-urlencoded"},{contentType:"text/plain"}],rules:{projectId:[{required:!0,message:"请选择项目",trigger:"change"}],apiName:[{required:!0,message:"命名不能为空",trigger:"blur"}],apiDesc:[{required:!0,message:"接口描述不能为空",trigger:"blur"}],method:[{required:!0,message:"请求方法不能为空",trigger:"blur"}],url:[{required:!0,message:"请求url不能为空",trigger:"blur"}]},temp:{id:void 0,projectId:null,apiName:"",apiDesc:"",method:null,url:"",contentType:null,headersJson:"",body:"",script:void 0},visible:!0,headersPlaceholder:'{"Content-Type": "application/json;charset=UTF-8", "Authorization": "${token}"}'}},watch:{"temp.contentType":{handler:function(e){this.handleHeader(this.temp,e)},deep:!0,immediate:!0}},created:function(){this.init()},methods:{init:function(){var e=this;this.fetchData(),Object(s["list"])({isAvailable:1}).then((function(t){e.projectList=t.data||[]}))},fetchData:function(){var e=this;this.canUpdate=!0,this.listLoading=!0,a["d"](this.listQuery).then((function(t){e.total=t.total,e.list=t.data,e.listLoading=!1}))},resetTemp:function(){this.temp={id:void 0,projectId:null,apiName:"",apiDesc:"",method:null,url:"",contentType:null,headersJson:"",body:"",script:void 0}},handleCreate:function(){var e=this;this.resetTemp(),this.dialogStatus="create",this.dialogFormVisible=!0,this.$nextTick((function(){e.$refs["dataForm"].clearValidate()}))},createData:function(){var e=this;this.canUpdate&&(this.handleHeader(this.temp,this.temp.contentType),this.$refs["dataForm"].validate((function(t){t&&(e.canUpdate=!1,a["a"](e.temp).then((function(){e.fetchData(),e.dialogFormVisible=!1,e.$notify({title:"Success",message:"创建成功",type:"success",duration:2e3})})).catch((function(){e.canUpdate=!0})))})))},handleUpdate:function(e){var t=this,n=Object.assign({},e);if(n.headersJson)try{n.contentType=JSON.parse(n.headersJson)["Content-Type"]||null,n.contentType&&n.contentType.indexOf(";")>0&&(n.contentType=n.contentType.substring(0,n.contentType.indexOf(";")))}catch(r){console.error(r)}if(n.body&&"application/json"===n.contentType)try{n.body=JSON.parse(n.body)}catch(r){console.log(r)}this.temp=n,this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick((function(){t.$refs["dataForm"].clearValidate()}))},updateData:function(){var e=this;this.canUpdate&&(this.handleHeader(this.temp,this.temp.contentType),this.$refs["dataForm"].validate((function(t){if(t){var n=Object.assign({},e.temp);e.canUpdate=!1,a["e"](n).then((function(){e.fetchData(),e.dialogFormVisible=!1,e.$notify({title:"Success",message:"修改成功",type:"success",duration:2e3})})).catch((function(){e.canUpdate=!0}))}})))},handleDelete:function(e){var t=this;this.$confirm("确定删除吗？").then((function(){a["b"](e.id).then((function(e){t.fetchData(),t.$notify({title:"Success",message:"删除成功",type:"success",duration:2e3})}))}))},handleHeader:function(e,t){try{if(t&&e.headersJson){var n=JSON.parse(e.headersJson);n["Content-Type"]=t,e.headersJson=JSON.stringify(n)}else t&&(e.headersJson=JSON.stringify({"Content-Type":t}));e.headersJson&&JSON.parse(e.headersJson)}catch(r){throw this.$message.error("请求头数据格式错误，必须为json格式"),r}},handleClose:function(e){this.temp.id?e():this.$confirm("确认关闭？").then((function(t){e()})).catch((function(e){}))},formTestApi:function(){var e=this;this.$refs["dataForm"].validate((function(t){t&&e.runScript(e.temp)}))},hasBodyMethod:function(e){return"POST"===e||"PUT"===e||"PATCH"===e},changeMethod:function(){this.temp.contentType=null,"GET"===this.temp.method&&(this.temp.body=null)},runScript:function(e){var t="";if(e.id)t+="// 如果请求中有${name}参数则可以通过如下方式传参\r\n",t+="// http.request('"+e.apiName+"', { name: 'veasion' })\r\n\r\n",t+="http.request('"+e.apiName+"')";else{e.apiName&&(t+="// 保存后可以通过apiName请求\r\n",t+="// http.request('"+e.apiName+"')\r\n\r\n"),this.handleHeader(e,e.contextType);var n="";n=e.body&&e.headersJson&&e.headersJson.indexOf("application/json")?"http.request('%url', '%method', %body, %headers)":e.headersJson?"http.request('%url', '%method', '%body', %headers)":"http.request('%url', '%method', '%body', null)",t+=n.replace("%url",e.url||"").replace("%method",e.method||"GET").replace("%body",e.body||"").replace("%headers",e.headersJson)}this.$refs.scriptExecute.show(e.projectId,t)}}},h=d,f=n("cba8"),m=Object(f["a"])(h,r,i,!1,null,null,null);t["default"]=m.exports},6724:function(e,t,n){"use strict";n("8d41");var r="@@wavesContext";function i(e,t){function n(n){var r=Object.assign({},t.value),i=Object.assign({ele:e,type:"hit",color:"rgba(0, 0, 0, 0.15)"},r),a=i.ele;if(a){a.style.position="relative",a.style.overflow="hidden";var s=a.getBoundingClientRect(),o=a.querySelector(".waves-ripple");switch(o?o.className="waves-ripple":(o=document.createElement("span"),o.className="waves-ripple",o.style.height=o.style.width=Math.max(s.width,s.height)+"px",a.appendChild(o)),i.type){case"center":o.style.top=s.height/2-o.offsetHeight/2+"px",o.style.left=s.width/2-o.offsetWidth/2+"px";break;default:o.style.top=(n.pageY-s.top-o.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",o.style.left=(n.pageX-s.left-o.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return o.style.backgroundColor=i.color,o.className="waves-ripple z-active",!1}}return e[r]?e[r].removeHandle=n:e[r]={removeHandle:n},n}var a={bind:function(e,t){e.addEventListener("click",i(e,t),!1)},update:function(e,t){e.removeEventListener("click",e[r].removeHandle,!1),e.addEventListener("click",i(e,t),!1)},unbind:function(e){e.removeEventListener("click",e[r].removeHandle,!1),e[r]=null,delete e[r]}},s=function(e){e.directive("waves",a)};window.Vue&&(window.waves=a,Vue.use(s)),a.install=s;t["a"]=a},7309:function(e,t,n){"use strict";n.d(t,"a",(function(){return i})),n.d(t,"b",(function(){return a}));var r=n("b775");function i(e){return Object(r["a"])({url:"/api/script/runScript",method:"post",data:e})}function a(e){return Object(r["a"])({url:"/api/script/toScript",method:"get",params:e})}},"8d41":function(e,t,n){},ceb9:function(e,t,n){"use strict";n.d(t,"d",(function(){return i})),n.d(t,"c",(function(){return a})),n.d(t,"e",(function(){return s})),n.d(t,"a",(function(){return o})),n.d(t,"b",(function(){return l}));var r=n("b775");function i(e){return Object(r["a"])({url:"/api/apiRequest/listPage",method:"get",params:e})}function a(e){return Object(r["a"])({url:"/api/apiRequest/list",method:"get",params:e})}function s(e){return Object(r["a"])({url:"/api/apiRequest/update",method:"post",data:e})}function o(e){return Object(r["a"])({url:"/api/apiRequest/add",method:"post",data:e})}function l(e){return Object(r["a"])({url:"/api/apiRequest/delete",method:"post",headers:{"Content-Type":"application/json"},data:e})}},f7f4:function(e,t){e.exports='/* Jison generated parser */\nvar jsonlint = (function(){\nvar parser = {trace: function trace() { },\nyy: {},\nsymbols_: {"error":2,"JSONString":3,"STRING":4,"JSONNumber":5,"NUMBER":6,"JSONNullLiteral":7,"NULL":8,"JSONBooleanLiteral":9,"TRUE":10,"FALSE":11,"JSONText":12,"JSONValue":13,"EOF":14,"JSONObject":15,"JSONArray":16,"{":17,"}":18,"JSONMemberList":19,"JSONMember":20,":":21,",":22,"[":23,"]":24,"JSONElementList":25,"$accept":0,"$end":1},\nterminals_: {2:"error",4:"STRING",6:"NUMBER",8:"NULL",10:"TRUE",11:"FALSE",14:"EOF",17:"{",18:"}",21:":",22:",",23:"[",24:"]"},\nproductions_: [0,[3,1],[5,1],[7,1],[9,1],[9,1],[12,2],[13,1],[13,1],[13,1],[13,1],[13,1],[13,1],[15,2],[15,3],[20,3],[19,1],[19,3],[16,2],[16,3],[25,1],[25,3]],\nperformAction: function anonymous(yytext,yyleng,yylineno,yy,yystate,$$,_$) {\n\nvar $0 = $$.length - 1;\nswitch (yystate) {\ncase 1: // replace escaped characters with actual character\n          this.$ = yytext.replace(/\\\\(\\\\|")/g, "$"+"1")\n                     .replace(/\\\\n/g,\'\\n\')\n                     .replace(/\\\\r/g,\'\\r\')\n                     .replace(/\\\\t/g,\'\\t\')\n                     .replace(/\\\\v/g,\'\\v\')\n                     .replace(/\\\\f/g,\'\\f\')\n                     .replace(/\\\\b/g,\'\\b\');\n        \nbreak;\ncase 2:this.$ = Number(yytext);\nbreak;\ncase 3:this.$ = null;\nbreak;\ncase 4:this.$ = true;\nbreak;\ncase 5:this.$ = false;\nbreak;\ncase 6:return this.$ = $$[$0-1];\nbreak;\ncase 13:this.$ = {};\nbreak;\ncase 14:this.$ = $$[$0-1];\nbreak;\ncase 15:this.$ = [$$[$0-2], $$[$0]];\nbreak;\ncase 16:this.$ = {}; this.$[$$[$0][0]] = $$[$0][1];\nbreak;\ncase 17:this.$ = $$[$0-2]; $$[$0-2][$$[$0][0]] = $$[$0][1];\nbreak;\ncase 18:this.$ = [];\nbreak;\ncase 19:this.$ = $$[$0-1];\nbreak;\ncase 20:this.$ = [$$[$0]];\nbreak;\ncase 21:this.$ = $$[$0-2]; $$[$0-2].push($$[$0]);\nbreak;\n}\n},\ntable: [{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],12:1,13:2,15:7,16:8,17:[1,14],23:[1,15]},{1:[3]},{14:[1,16]},{14:[2,7],18:[2,7],22:[2,7],24:[2,7]},{14:[2,8],18:[2,8],22:[2,8],24:[2,8]},{14:[2,9],18:[2,9],22:[2,9],24:[2,9]},{14:[2,10],18:[2,10],22:[2,10],24:[2,10]},{14:[2,11],18:[2,11],22:[2,11],24:[2,11]},{14:[2,12],18:[2,12],22:[2,12],24:[2,12]},{14:[2,3],18:[2,3],22:[2,3],24:[2,3]},{14:[2,4],18:[2,4],22:[2,4],24:[2,4]},{14:[2,5],18:[2,5],22:[2,5],24:[2,5]},{14:[2,1],18:[2,1],21:[2,1],22:[2,1],24:[2,1]},{14:[2,2],18:[2,2],22:[2,2],24:[2,2]},{3:20,4:[1,12],18:[1,17],19:18,20:19},{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],13:23,15:7,16:8,17:[1,14],23:[1,15],24:[1,21],25:22},{1:[2,6]},{14:[2,13],18:[2,13],22:[2,13],24:[2,13]},{18:[1,24],22:[1,25]},{18:[2,16],22:[2,16]},{21:[1,26]},{14:[2,18],18:[2,18],22:[2,18],24:[2,18]},{22:[1,28],24:[1,27]},{22:[2,20],24:[2,20]},{14:[2,14],18:[2,14],22:[2,14],24:[2,14]},{3:20,4:[1,12],20:29},{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],13:30,15:7,16:8,17:[1,14],23:[1,15]},{14:[2,19],18:[2,19],22:[2,19],24:[2,19]},{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],13:31,15:7,16:8,17:[1,14],23:[1,15]},{18:[2,17],22:[2,17]},{18:[2,15],22:[2,15]},{22:[2,21],24:[2,21]}],\ndefaultActions: {16:[2,6]},\nparseError: function parseError(str, hash) {\n    throw new Error(str);\n},\nparse: function parse(input) {\n    var self = this,\n        stack = [0],\n        vstack = [null], // semantic value stack\n        lstack = [], // location stack\n        table = this.table,\n        yytext = \'\',\n        yylineno = 0,\n        yyleng = 0,\n        recovering = 0,\n        TERROR = 2,\n        EOF = 1;\n\n    //this.reductionCount = this.shiftCount = 0;\n\n    this.lexer.setInput(input);\n    this.lexer.yy = this.yy;\n    this.yy.lexer = this.lexer;\n    if (typeof this.lexer.yylloc == \'undefined\')\n        this.lexer.yylloc = {};\n    var yyloc = this.lexer.yylloc;\n    lstack.push(yyloc);\n\n    if (typeof this.yy.parseError === \'function\')\n        this.parseError = this.yy.parseError;\n\n    function popStack (n) {\n        stack.length = stack.length - 2*n;\n        vstack.length = vstack.length - n;\n        lstack.length = lstack.length - n;\n    }\n\n    function lex() {\n        var token;\n        token = self.lexer.lex() || 1; // $end = 1\n        // if token isn\'t its numeric value, convert\n        if (typeof token !== \'number\') {\n            token = self.symbols_[token] || token;\n        }\n        return token;\n    }\n\n    var symbol, preErrorSymbol, state, action, a, r, yyval={},p,len,newState, expected;\n    while (true) {\n        // retreive state number from top of stack\n        state = stack[stack.length-1];\n\n        // use default actions if available\n        if (this.defaultActions[state]) {\n            action = this.defaultActions[state];\n        } else {\n            if (symbol == null)\n                symbol = lex();\n            // read action for current state and first input\n            action = table[state] && table[state][symbol];\n        }\n\n        // handle parse error\n        _handle_error:\n        if (typeof action === \'undefined\' || !action.length || !action[0]) {\n\n            if (!recovering) {\n                // Report error\n                expected = [];\n                for (p in table[state]) if (this.terminals_[p] && p > 2) {\n                    expected.push("\'"+this.terminals_[p]+"\'");\n                }\n                var errStr = \'\';\n                if (this.lexer.showPosition) {\n                    errStr = \'Parse error on line \'+(yylineno+1)+":\\n"+this.lexer.showPosition()+"\\nExpecting "+expected.join(\', \') + ", got \'" + this.terminals_[symbol]+ "\'";\n                } else {\n                    errStr = \'Parse error on line \'+(yylineno+1)+": Unexpected " +\n                                  (symbol == 1 /*EOF*/ ? "end of input" :\n                                              ("\'"+(this.terminals_[symbol] || symbol)+"\'"));\n                }\n                this.parseError(errStr,\n                    {text: this.lexer.match, token: this.terminals_[symbol] || symbol, line: this.lexer.yylineno, loc: yyloc, expected: expected});\n            }\n\n            // just recovered from another error\n            if (recovering == 3) {\n                if (symbol == EOF) {\n                    throw new Error(errStr || \'Parsing halted.\');\n                }\n\n                // discard current lookahead and grab another\n                yyleng = this.lexer.yyleng;\n                yytext = this.lexer.yytext;\n                yylineno = this.lexer.yylineno;\n                yyloc = this.lexer.yylloc;\n                symbol = lex();\n            }\n\n            // try to recover from error\n            while (1) {\n                // check for error recovery rule in this state\n                if ((TERROR.toString()) in table[state]) {\n                    break;\n                }\n                if (state == 0) {\n                    throw new Error(errStr || \'Parsing halted.\');\n                }\n                popStack(1);\n                state = stack[stack.length-1];\n            }\n\n            preErrorSymbol = symbol; // save the lookahead token\n            symbol = TERROR;         // insert generic error symbol as new lookahead\n            state = stack[stack.length-1];\n            action = table[state] && table[state][TERROR];\n            recovering = 3; // allow 3 real symbols to be shifted before reporting a new error\n        }\n\n        // this shouldn\'t happen, unless resolve defaults are off\n        if (action[0] instanceof Array && action.length > 1) {\n            throw new Error(\'Parse Error: multiple actions possible at state: \'+state+\', token: \'+symbol);\n        }\n\n        switch (action[0]) {\n\n            case 1: // shift\n                //this.shiftCount++;\n\n                stack.push(symbol);\n                vstack.push(this.lexer.yytext);\n                lstack.push(this.lexer.yylloc);\n                stack.push(action[1]); // push state\n                symbol = null;\n                if (!preErrorSymbol) { // normal execution/no error\n                    yyleng = this.lexer.yyleng;\n                    yytext = this.lexer.yytext;\n                    yylineno = this.lexer.yylineno;\n                    yyloc = this.lexer.yylloc;\n                    if (recovering > 0)\n                        recovering--;\n                } else { // error just occurred, resume old lookahead f/ before error\n                    symbol = preErrorSymbol;\n                    preErrorSymbol = null;\n                }\n                break;\n\n            case 2: // reduce\n                //this.reductionCount++;\n\n                len = this.productions_[action[1]][1];\n\n                // perform semantic action\n                yyval.$ = vstack[vstack.length-len]; // default to $$ = $1\n                // default location, uses first token for firsts, last for lasts\n                yyval._$ = {\n                    first_line: lstack[lstack.length-(len||1)].first_line,\n                    last_line: lstack[lstack.length-1].last_line,\n                    first_column: lstack[lstack.length-(len||1)].first_column,\n                    last_column: lstack[lstack.length-1].last_column\n                };\n                r = this.performAction.call(yyval, yytext, yyleng, yylineno, this.yy, action[1], vstack, lstack);\n\n                if (typeof r !== \'undefined\') {\n                    return r;\n                }\n\n                // pop off stack\n                if (len) {\n                    stack = stack.slice(0,-1*len*2);\n                    vstack = vstack.slice(0, -1*len);\n                    lstack = lstack.slice(0, -1*len);\n                }\n\n                stack.push(this.productions_[action[1]][0]);    // push nonterminal (reduce)\n                vstack.push(yyval.$);\n                lstack.push(yyval._$);\n                // goto new state = table[STATE][NONTERMINAL]\n                newState = table[stack[stack.length-2]][stack[stack.length-1]];\n                stack.push(newState);\n                break;\n\n            case 3: // accept\n                return true;\n        }\n\n    }\n\n    return true;\n}};\n/* Jison generated lexer */\nvar lexer = (function(){\nvar lexer = ({EOF:1,\nparseError:function parseError(str, hash) {\n        if (this.yy.parseError) {\n            this.yy.parseError(str, hash);\n        } else {\n            throw new Error(str);\n        }\n    },\nsetInput:function (input) {\n        this._input = input;\n        this._more = this._less = this.done = false;\n        this.yylineno = this.yyleng = 0;\n        this.yytext = this.matched = this.match = \'\';\n        this.conditionStack = [\'INITIAL\'];\n        this.yylloc = {first_line:1,first_column:0,last_line:1,last_column:0};\n        return this;\n    },\ninput:function () {\n        var ch = this._input[0];\n        this.yytext+=ch;\n        this.yyleng++;\n        this.match+=ch;\n        this.matched+=ch;\n        var lines = ch.match(/\\n/);\n        if (lines) this.yylineno++;\n        this._input = this._input.slice(1);\n        return ch;\n    },\nunput:function (ch) {\n        this._input = ch + this._input;\n        return this;\n    },\nmore:function () {\n        this._more = true;\n        return this;\n    },\nless:function (n) {\n        this._input = this.match.slice(n) + this._input;\n    },\npastInput:function () {\n        var past = this.matched.substr(0, this.matched.length - this.match.length);\n        return (past.length > 20 ? \'...\':\'\') + past.substr(-20).replace(/\\n/g, "");\n    },\nupcomingInput:function () {\n        var next = this.match;\n        if (next.length < 20) {\n            next += this._input.substr(0, 20-next.length);\n        }\n        return (next.substr(0,20)+(next.length > 20 ? \'...\':\'\')).replace(/\\n/g, "");\n    },\nshowPosition:function () {\n        var pre = this.pastInput();\n        var c = new Array(pre.length + 1).join("-");\n        return pre + this.upcomingInput() + "\\n" + c+"^";\n    },\nnext:function () {\n        if (this.done) {\n            return this.EOF;\n        }\n        if (!this._input) this.done = true;\n\n        var token,\n            match,\n            tempMatch,\n            index,\n            col,\n            lines;\n        if (!this._more) {\n            this.yytext = \'\';\n            this.match = \'\';\n        }\n        var rules = this._currentRules();\n        for (var i=0;i < rules.length; i++) {\n            tempMatch = this._input.match(this.rules[rules[i]]);\n            if (tempMatch && (!match || tempMatch[0].length > match[0].length)) {\n                match = tempMatch;\n                index = i;\n                if (!this.options.flex) break;\n            }\n        }\n        if (match) {\n            lines = match[0].match(/\\n.*/g);\n            if (lines) this.yylineno += lines.length;\n            this.yylloc = {first_line: this.yylloc.last_line,\n                           last_line: this.yylineno+1,\n                           first_column: this.yylloc.last_column,\n                           last_column: lines ? lines[lines.length-1].length-1 : this.yylloc.last_column + match[0].length}\n            this.yytext += match[0];\n            this.match += match[0];\n            this.yyleng = this.yytext.length;\n            this._more = false;\n            this._input = this._input.slice(match[0].length);\n            this.matched += match[0];\n            token = this.performAction.call(this, this.yy, this, rules[index],this.conditionStack[this.conditionStack.length-1]);\n            if (this.done && this._input) this.done = false;\n            if (token) return token;\n            else return;\n        }\n        if (this._input === "") {\n            return this.EOF;\n        } else {\n            this.parseError(\'Lexical error on line \'+(this.yylineno+1)+\'. Unrecognized text.\\n\'+this.showPosition(), \n                    {text: "", token: null, line: this.yylineno});\n        }\n    },\nlex:function lex() {\n        var r = this.next();\n        if (typeof r !== \'undefined\') {\n            return r;\n        } else {\n            return this.lex();\n        }\n    },\nbegin:function begin(condition) {\n        this.conditionStack.push(condition);\n    },\npopState:function popState() {\n        return this.conditionStack.pop();\n    },\n_currentRules:function _currentRules() {\n        return this.conditions[this.conditionStack[this.conditionStack.length-1]].rules;\n    },\ntopState:function () {\n        return this.conditionStack[this.conditionStack.length-2];\n    },\npushState:function begin(condition) {\n        this.begin(condition);\n    }});\nlexer.options = {};\nlexer.performAction = function anonymous(yy,yy_,$avoiding_name_collisions,YY_START) {\n\nvar YYSTATE=YY_START\nswitch($avoiding_name_collisions) {\ncase 0:/* skip whitespace */\nbreak;\ncase 1:return 6\nbreak;\ncase 2:yy_.yytext = yy_.yytext.substr(1,yy_.yyleng-2); return 4\nbreak;\ncase 3:return 17\nbreak;\ncase 4:return 18\nbreak;\ncase 5:return 23\nbreak;\ncase 6:return 24\nbreak;\ncase 7:return 22\nbreak;\ncase 8:return 21\nbreak;\ncase 9:return 10\nbreak;\ncase 10:return 11\nbreak;\ncase 11:return 8\nbreak;\ncase 12:return 14\nbreak;\ncase 13:return \'INVALID\'\nbreak;\n}\n};\nlexer.rules = [/^(?:\\s+)/,/^(?:(-?([0-9]|[1-9][0-9]+))(\\.[0-9]+)?([eE][-+]?[0-9]+)?\\b)/,/^(?:"(?:\\\\[\\\\"bfnrt/]|\\\\u[a-fA-F0-9]{4}|[^\\\\\\0-\\x09\\x0a-\\x1f"])*")/,/^(?:\\{)/,/^(?:\\})/,/^(?:\\[)/,/^(?:\\])/,/^(?:,)/,/^(?::)/,/^(?:true\\b)/,/^(?:false\\b)/,/^(?:null\\b)/,/^(?:$)/,/^(?:.)/];\nlexer.conditions = {"INITIAL":{"rules":[0,1,2,3,4,5,6,7,8,9,10,11,12,13],"inclusive":true}};\n\n\n;\nreturn lexer;})()\nparser.lexer = lexer;\nreturn parser;\n})();\nif (typeof require !== \'undefined\' && typeof exports !== \'undefined\') {\nexports.parser = jsonlint;\nexports.parse = function () { return jsonlint.parse.apply(jsonlint, arguments); }\nexports.main = function commonjsMain(args) {\n    if (!args[1])\n        throw new Error(\'Usage: \'+args[0]+\' FILE\');\n    if (typeof process !== \'undefined\') {\n        var source = require(\'fs\').readFileSync(require(\'path\').join(process.cwd(), args[1]), "utf8");\n    } else {\n        var cwd = require("file").path(require("file").cwd());\n        var source = cwd.join(args[1]).read({charset: "utf-8"});\n    }\n    return exports.parser.parse(source);\n}\nif (typeof module !== \'undefined\' && require.main === module) {\n  exports.main(typeof process !== \'undefined\' ? process.argv.slice(1) : require("system").args);\n}\n}'}}]);