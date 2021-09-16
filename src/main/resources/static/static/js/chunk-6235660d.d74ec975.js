(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6235660d"],{"1bff":function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"form-container"},[n("el-form",{ref:"dataForm",attrs:{rules:t.rules,model:t.project,"label-position":"left","label-width":"120px"}},[n("el-form-item",{staticStyle:{width:"400px"},attrs:{label:"项目名称",prop:"name"}},[n("el-input",{attrs:{placeholder:"项目名称"},model:{value:t.project.name,callback:function(e){t.$set(t.project,"name",e)},expression:"project.name"}})],1),t._v(" "),n("el-form-item",{attrs:{label:"项目描述",prop:"description"}},[n("el-input",{staticStyle:{width:"400px"},attrs:{placeholder:"项目描述"},model:{value:t.project.description,callback:function(e){t.$set(t.project,"description",e)},expression:"project.description"}})],1),t._v(" "),n("el-form-item",{attrs:{label:"是否启用配置",prop:"isAvailable"}},[n("el-switch",{attrs:{"active-color":"#00A854","active-text":"启用","active-value":1,"inactive-color":"#F04134","inactive-text":"停用","inactive-value":0},model:{value:t.project.projectConfig.isAvailable,callback:function(e){t.$set(t.project.projectConfig,"isAvailable",e)},expression:"project.projectConfig.isAvailable"}})],1),t._v(" "),n("el-form-item",{attrs:{label:"接口请求日志",prop:"openReqLog"}},[n("el-switch",{attrs:{"active-color":"#00A854","active-text":"开启","active-value":1,"inactive-color":"#F04134","inactive-text":"不开启","inactive-value":0},model:{value:t.project.projectConfig.openReqLog,callback:function(e){t.$set(t.project.projectConfig,"openReqLog",e)},expression:"project.projectConfig.openReqLog"}})],1),t._v(" "),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:22}},[n("el-form-item",{attrs:{label:"全局参数",prop:"globalVarJson"}},[n("span",{attrs:{slot:"label"},slot:"label"},[n("span",[t._v("全局参数")]),t._v(" "),n("i",{staticClass:"el-icon-question",staticStyle:{cursor:"pointer"},attrs:{title:"查看示例"},on:{click:function(e){return t.showExample("json","globalVarJson")}}})]),t._v(" "),n("json-editor",{ref:"globalVarJsonEditor",model:{value:t.project.projectConfig.globalVarJson,callback:function(e){t.$set(t.project.projectConfig,"globalVarJson",e)},expression:"project.projectConfig.globalVarJson"}})],1)],1)],1),t._v(" "),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:22}},[n("el-form-item",{attrs:{label:"前置脚本",prop:"beforeScript"}},[n("span",{attrs:{slot:"label"},slot:"label"},[n("span",[t._v("前置脚本")]),t._v(" "),n("i",{staticClass:"el-icon-question",staticStyle:{cursor:"pointer"},attrs:{title:"查看示例"},on:{click:function(e){return t.showExample("js","beforeScript")}}})]),t._v(" "),n("javascript-editor",{ref:"beforeScriptEditor",model:{value:t.project.projectConfig.beforeScript,callback:function(e){t.$set(t.project.projectConfig,"beforeScript",e)},expression:"project.projectConfig.beforeScript"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:2}},[n("span",{attrs:{title:"调试脚本"}},[n("svg-icon",{staticClass:"run-but",attrs:{"icon-class":"run"},nativeOn:{click:function(e){return t.runScript("beforeScript")}}})],1)])],1),t._v(" "),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:22}},[n("el-form-item",{attrs:{label:"后置脚本",prop:"afterScript"}},[n("span",{attrs:{slot:"label"},slot:"label"},[n("span",[t._v("后置脚本")]),t._v(" "),n("i",{staticClass:"el-icon-question",staticStyle:{cursor:"pointer"},attrs:{title:"查看示例"},on:{click:function(e){return t.showExample("js","afterScript")}}})]),t._v(" "),n("javascript-editor",{ref:"afterScriptEditor",model:{value:t.project.projectConfig.afterScript,callback:function(e){t.$set(t.project.projectConfig,"afterScript",e)},expression:"project.projectConfig.afterScript"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:2}},[n("span",{attrs:{title:"调试脚本"}},[n("svg-icon",{staticClass:"run-but",attrs:{"icon-class":"run"},nativeOn:{click:function(e){return t.runScript("afterScript")}}})],1)])],1),t._v(" "),n("el-row",{attrs:{gutter:20}},[n("el-col",{attrs:{span:22}},[n("el-form-item",{attrs:{label:"异常监听脚本",prop:"exceptionScript"}},[n("span",{attrs:{slot:"label"},slot:"label"},[n("span",[t._v("异常监听脚本")]),t._v(" "),n("i",{staticClass:"el-icon-question",staticStyle:{cursor:"pointer"},attrs:{title:"查看示例"},on:{click:function(e){return t.showExample("js","exceptionScript")}}})]),t._v(" "),n("javascript-editor",{ref:"exceptionScriptEditor",model:{value:t.project.projectConfig.exceptionScript,callback:function(e){t.$set(t.project.projectConfig,"exceptionScript",e)},expression:"project.projectConfig.exceptionScript"}})],1)],1),t._v(" "),n("el-col",{attrs:{span:2}},[n("span",{attrs:{title:"调试脚本"}},[n("svg-icon",{staticClass:"run-but",attrs:{"icon-class":"run"},nativeOn:{click:function(e){return t.runScript("exceptionScript")}}})],1)])],1)],1)],1),t._v(" "),n("div",{staticStyle:{"text-align":"center","margin-top":"10px","margin-bottom":"10px"}},[n("el-button",{attrs:{type:"primary"},on:{click:t.update}},[t._v("更新配置")])],1),t._v(" "),n("example",{ref:"example"}),t._v(" "),n("script-execute",{ref:"scriptExecute",attrs:{"before-script":!1,"after-script":!1}})],1)},s=[],i=(n("7f7f"),n("5530")),o=(n("96cf"),n("1da1")),a=n("24d2"),c=n("fa7e"),l=n("f6c2"),p=n("45d1"),u=n("2e50"),h={name:"ProjectConfig",components:{JsonEditor:c["a"],JavascriptEditor:l["a"],ScriptExecute:p["a"],Example:u["a"]},data:function(){return{id:null,project:{id:void 0,name:"",description:"",projectConfig:{openReqLog:1,isAvailable:1,globalVarJson:'{"baseUrl": "http://127.0.0.1:8080"}',beforeScript:"",afterScript:"",exceptionScript:""}},rules:{name:[{required:!0,message:"项目名称不能为空",trigger:"blur"}],description:[{required:!0,message:"项目描述不能为空",trigger:"blur"}]}}},mounted:function(){this.init()},methods:{init:function(){var t=Object(o["a"])(regeneratorRuntime.mark((function t(){var e,n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return this.id=this.$route.params.id||this.$route.query.id,t.next=3,a["getById"](this.id);case 3:if(e=t.sent,n=e.data,n.projectConfig=n.projectConfig||Object(i["a"])({},this.project.projectConfig),n.projectConfig.globalVarJson)try{n.projectConfig.globalVarJson=JSON.parse(n.projectConfig.globalVarJson)}catch(r){console.log(r),this.$message.error("全局参数错误，非JSON格式")}else n.projectConfig.globalVarJson={};this.project=n,this.$refs.beforeScriptEditor&&this.$refs.beforeScriptEditor.reloadTips(this.id),this.$refs.afterScriptEditor&&this.$refs.afterScriptEditor.reloadTips(this.id),this.$refs.exceptionScriptEditor&&this.$refs.exceptionScriptEditor.reloadTips(this.id);case 11:case"end":return t.stop()}}),t,this)})));function e(){return t.apply(this,arguments)}return e}(),update:function(){var t=this;if(this.project.projectConfig.globalVarJson)try{JSON.parse(this.project.projectConfig.globalVarJson)}catch(e){return void this.$message.error("全局参数错误，非JSON格式")}this.$confirm("确定更新项目配置吗？").then((function(){a["update"](t.project).then((function(){t.$notify({title:"Success",message:"更新成功",type:"success",duration:2e3}),t.init()}))}))},runScript:function(t){var e=this.project.projectConfig[t];if(this.project.projectConfig.globalVarJson)try{this.$refs.scriptExecute.setEnvMap(JSON.parse(this.project.projectConfig.globalVarJson))}catch(n){console.error("设置envMap错误",n)}this.$refs.scriptExecute.show(this.project.id,e)},showExample:function(t,e){var n="",r="";"globalVarJson"===e?(n="项目全局参数",r={baseUrl:"http://127.0.0.1:8080",username:"admin",password:123456,desc:"",desc1:"这是一个全局参数配置示例",desc2:"请求参数可以通过${xxx}方式替换变量",desc3:"js中可以通过env.getGlobal('xxx')获取值",desc4:"http.postJson('${baseUrl}', { username: '${username}' })"}):"beforeScript"===e?(n="前置脚本示例",r='// 该脚本是在所有脚本执行前触发，策略开始前执行\n// 脚本适用于提前登录，请求/响应拦截处理\n\n// 登录示例\nlet result = http.postJson("${baseUrl}/api/auth/login", {\n  "username": "${username}",\n  "password": "${password}"\n});\n\nlet token = result.data.token;\nlog.info(\'token: \' + token);\nenv.setGlobal("token", token);\n\n// 请求拦截处理，设置授权headers\nscriptContext.addRequestProcessor(function(request) {\n  request.addHeaders("Authorization", token);\n});\n\n// 响应拦截处理\nscriptContext.addResponseProcessor(function(response, status, log) {\n  // 这里判断请求是否成功（这里是接口失败，并不是脚本执行异常）\n  if (status == 200 && response && response.code && response.code != \'200\') {\n    // code不等于200时请求失败，修改日志状态: 2成功 3 失败\n    log.status = 3;\n\t// 记录失败日志\n    log.appendLog(response.message || "");\n  }\n});'):"afterScript"===e?(n="后置脚本示例",r="// 该脚本是在所有脚本执行后触发，策略正常执行完成后执行\n// 脚本适用于用来监听脚本执行情况\n\n// 发送钉钉群通知示例\nlet projectName = scriptContext.project.name;\nlet strategyName = scriptContext.strategy != null ? scriptContext.strategy.name : null\nif (scriptContext.strategy && scriptContext.strategy.strategy == 2) {\n\t// TODO 压测不处理\n} else {\n\t// 定时任务\n\tif (scriptContext.refLog == 2) {\n\t\t// 全部执行成功\n\t\tsendNotice(projectName + ', 策略' + strategyName + '执行通过');\n\t} else {\n\t\t// 策略执行不通过\n\t\tfor (let i in scriptContext.apiLogList) {\n\t\t\tlet apiLog = scriptContext.apiLogList[i];\n\t\t\tif (apiLog.status == 3) {\n\t\t\t\t// 执行不通过的请求\n\t\t\t\tsendNotice(projectName + ', 接口: ' + apiLog.url + ' 执行失败');\n\t\t\t}\n\t\t}\n\t}\n}\n\nfunction sendNotice(msg) {\n\t// 发送钉钉群通知\n\tlet access_token = '你的群机器人access_token';\n\thttp.postJson('https://oapi.dingtalk.com/robot/send?access_token=' + access_token, {\n\t\tmsgtype: 'text', \n\t\ttext: {\n\t\t\tcontent: '接口自动化测试：' + msg\n\t\t}\n\t});\n}"):"exceptionScript"===e&&(n="异常监听脚本示例",r="// 该脚本是在策略执行异常时触发\n// 脚本适用于异常监听通知\n\n// 发送钉钉群通知示例\nlet projectName = scriptContext.project.name;\nlet strategyName = scriptContext.strategy != null ? scriptContext.strategy.name : null\n// 发送钉钉群通知\nlet access_token = '你的群机器人access_token';\nhttp.postJson('https://oapi.dingtalk.com/robot/send?access_token=' + access_token, {\n\tmsgtype: 'text', \n\ttext: {\n\t\tcontent: '接口自动化测试：' + projectName + ', 策略' + strategyName + '执行异常'\n\t}\n});"),this.$refs.example.show(n,t,r)}}},f=h,d=(n("f478"),n("2877")),y=Object(d["a"])(f,r,s,!1,null,"722e82e3",null);e["default"]=y.exports},"24d2":function(t,e,n){"use strict";n.r(e),n.d(e,"listPage",(function(){return s})),n.d(e,"list",(function(){return i})),n.d(e,"getById",(function(){return o})),n.d(e,"update",(function(){return a})),n.d(e,"add",(function(){return c})),n.d(e,"deleted",(function(){return l}));var r=n("b775");function s(t){return Object(r["a"])({url:"/api/project/listPage",method:"get",params:t})}function i(t){return Object(r["a"])({url:"/api/project/list",method:"get",params:t})}function o(t){return Object(r["a"])({url:"/api/project/getById?id="+t,method:"get"})}function a(t){return Object(r["a"])({url:"/api/project/update",method:"post",data:t})}function c(t){return Object(r["a"])({url:"/api/project/add",method:"post",data:t})}function l(t){return Object(r["a"])({url:"/api/project/delete",method:"post",headers:{"Content-Type":"application/json"},data:t})}},"2e50":function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-dialog",{attrs:{title:t.title,visible:t.visible,width:"800px","before-close":t.handleClose},on:{"update:visible":function(e){t.visible=e}}},[n("div",["json"===t.type?n("json-editor",{model:{value:t.text,callback:function(e){t.text=e},expression:"text"}}):t._e(),t._v(" "),"js"===t.type?n("javascript-editor",{model:{value:t.text,callback:function(e){t.text=e},expression:"text"}}):t._e(),t._v(" "),"text"===t.type?n("text-editor",{model:{value:t.text,callback:function(e){t.text=e},expression:"text"}}):t._e()],1)])},s=[],i=n("fa7e"),o=n("f6c2"),a=n("c422"),c={name:"Example",components:{JsonEditor:i["a"],JavascriptEditor:o["a"],TextEditor:a["a"]},props:{},data:function(){return{text:"",type:"text",title:"示例",visible:!1}},methods:{show:function(t,e,n){if(t&&(this.title=t),"json"===e&&"string"===typeof n)try{n=JSON.parse(n)}catch(r){console.error("json错误: "+n,r)}this.text=n,this.type=e||"text",this.visible=!0},close:function(){this.visible=!1},handleClose:function(t){t()}}},l=c,p=n("2877"),u=Object(p["a"])(l,r,s,!1,null,"232a091a",null);e["a"]=u.exports},"3f23":function(t,e){t.exports='/* Jison generated parser */\nvar jsonlint = (function(){\nvar parser = {trace: function trace() { },\nyy: {},\nsymbols_: {"error":2,"JSONString":3,"STRING":4,"JSONNumber":5,"NUMBER":6,"JSONNullLiteral":7,"NULL":8,"JSONBooleanLiteral":9,"TRUE":10,"FALSE":11,"JSONText":12,"JSONValue":13,"EOF":14,"JSONObject":15,"JSONArray":16,"{":17,"}":18,"JSONMemberList":19,"JSONMember":20,":":21,",":22,"[":23,"]":24,"JSONElementList":25,"$accept":0,"$end":1},\nterminals_: {2:"error",4:"STRING",6:"NUMBER",8:"NULL",10:"TRUE",11:"FALSE",14:"EOF",17:"{",18:"}",21:":",22:",",23:"[",24:"]"},\nproductions_: [0,[3,1],[5,1],[7,1],[9,1],[9,1],[12,2],[13,1],[13,1],[13,1],[13,1],[13,1],[13,1],[15,2],[15,3],[20,3],[19,1],[19,3],[16,2],[16,3],[25,1],[25,3]],\nperformAction: function anonymous(yytext,yyleng,yylineno,yy,yystate,$$,_$) {\n\nvar $0 = $$.length - 1;\nswitch (yystate) {\ncase 1: // replace escaped characters with actual character\n          this.$ = yytext.replace(/\\\\(\\\\|")/g, "$"+"1")\n                     .replace(/\\\\n/g,\'\\n\')\n                     .replace(/\\\\r/g,\'\\r\')\n                     .replace(/\\\\t/g,\'\\t\')\n                     .replace(/\\\\v/g,\'\\v\')\n                     .replace(/\\\\f/g,\'\\f\')\n                     .replace(/\\\\b/g,\'\\b\');\n        \nbreak;\ncase 2:this.$ = Number(yytext);\nbreak;\ncase 3:this.$ = null;\nbreak;\ncase 4:this.$ = true;\nbreak;\ncase 5:this.$ = false;\nbreak;\ncase 6:return this.$ = $$[$0-1];\nbreak;\ncase 13:this.$ = {};\nbreak;\ncase 14:this.$ = $$[$0-1];\nbreak;\ncase 15:this.$ = [$$[$0-2], $$[$0]];\nbreak;\ncase 16:this.$ = {}; this.$[$$[$0][0]] = $$[$0][1];\nbreak;\ncase 17:this.$ = $$[$0-2]; $$[$0-2][$$[$0][0]] = $$[$0][1];\nbreak;\ncase 18:this.$ = [];\nbreak;\ncase 19:this.$ = $$[$0-1];\nbreak;\ncase 20:this.$ = [$$[$0]];\nbreak;\ncase 21:this.$ = $$[$0-2]; $$[$0-2].push($$[$0]);\nbreak;\n}\n},\ntable: [{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],12:1,13:2,15:7,16:8,17:[1,14],23:[1,15]},{1:[3]},{14:[1,16]},{14:[2,7],18:[2,7],22:[2,7],24:[2,7]},{14:[2,8],18:[2,8],22:[2,8],24:[2,8]},{14:[2,9],18:[2,9],22:[2,9],24:[2,9]},{14:[2,10],18:[2,10],22:[2,10],24:[2,10]},{14:[2,11],18:[2,11],22:[2,11],24:[2,11]},{14:[2,12],18:[2,12],22:[2,12],24:[2,12]},{14:[2,3],18:[2,3],22:[2,3],24:[2,3]},{14:[2,4],18:[2,4],22:[2,4],24:[2,4]},{14:[2,5],18:[2,5],22:[2,5],24:[2,5]},{14:[2,1],18:[2,1],21:[2,1],22:[2,1],24:[2,1]},{14:[2,2],18:[2,2],22:[2,2],24:[2,2]},{3:20,4:[1,12],18:[1,17],19:18,20:19},{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],13:23,15:7,16:8,17:[1,14],23:[1,15],24:[1,21],25:22},{1:[2,6]},{14:[2,13],18:[2,13],22:[2,13],24:[2,13]},{18:[1,24],22:[1,25]},{18:[2,16],22:[2,16]},{21:[1,26]},{14:[2,18],18:[2,18],22:[2,18],24:[2,18]},{22:[1,28],24:[1,27]},{22:[2,20],24:[2,20]},{14:[2,14],18:[2,14],22:[2,14],24:[2,14]},{3:20,4:[1,12],20:29},{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],13:30,15:7,16:8,17:[1,14],23:[1,15]},{14:[2,19],18:[2,19],22:[2,19],24:[2,19]},{3:5,4:[1,12],5:6,6:[1,13],7:3,8:[1,9],9:4,10:[1,10],11:[1,11],13:31,15:7,16:8,17:[1,14],23:[1,15]},{18:[2,17],22:[2,17]},{18:[2,15],22:[2,15]},{22:[2,21],24:[2,21]}],\ndefaultActions: {16:[2,6]},\nparseError: function parseError(str, hash) {\n    throw new Error(str);\n},\nparse: function parse(input) {\n    var self = this,\n        stack = [0],\n        vstack = [null], // semantic value stack\n        lstack = [], // location stack\n        table = this.table,\n        yytext = \'\',\n        yylineno = 0,\n        yyleng = 0,\n        recovering = 0,\n        TERROR = 2,\n        EOF = 1;\n\n    //this.reductionCount = this.shiftCount = 0;\n\n    this.lexer.setInput(input);\n    this.lexer.yy = this.yy;\n    this.yy.lexer = this.lexer;\n    if (typeof this.lexer.yylloc == \'undefined\')\n        this.lexer.yylloc = {};\n    var yyloc = this.lexer.yylloc;\n    lstack.push(yyloc);\n\n    if (typeof this.yy.parseError === \'function\')\n        this.parseError = this.yy.parseError;\n\n    function popStack (n) {\n        stack.length = stack.length - 2*n;\n        vstack.length = vstack.length - n;\n        lstack.length = lstack.length - n;\n    }\n\n    function lex() {\n        var token;\n        token = self.lexer.lex() || 1; // $end = 1\n        // if token isn\'t its numeric value, convert\n        if (typeof token !== \'number\') {\n            token = self.symbols_[token] || token;\n        }\n        return token;\n    }\n\n    var symbol, preErrorSymbol, state, action, a, r, yyval={},p,len,newState, expected;\n    while (true) {\n        // retreive state number from top of stack\n        state = stack[stack.length-1];\n\n        // use default actions if available\n        if (this.defaultActions[state]) {\n            action = this.defaultActions[state];\n        } else {\n            if (symbol == null)\n                symbol = lex();\n            // read action for current state and first input\n            action = table[state] && table[state][symbol];\n        }\n\n        // handle parse error\n        _handle_error:\n        if (typeof action === \'undefined\' || !action.length || !action[0]) {\n\n            if (!recovering) {\n                // Report error\n                expected = [];\n                for (p in table[state]) if (this.terminals_[p] && p > 2) {\n                    expected.push("\'"+this.terminals_[p]+"\'");\n                }\n                var errStr = \'\';\n                if (this.lexer.showPosition) {\n                    errStr = \'Parse error on line \'+(yylineno+1)+":\\n"+this.lexer.showPosition()+"\\nExpecting "+expected.join(\', \') + ", got \'" + this.terminals_[symbol]+ "\'";\n                } else {\n                    errStr = \'Parse error on line \'+(yylineno+1)+": Unexpected " +\n                                  (symbol == 1 /*EOF*/ ? "end of input" :\n                                              ("\'"+(this.terminals_[symbol] || symbol)+"\'"));\n                }\n                this.parseError(errStr,\n                    {text: this.lexer.match, token: this.terminals_[symbol] || symbol, line: this.lexer.yylineno, loc: yyloc, expected: expected});\n            }\n\n            // just recovered from another error\n            if (recovering == 3) {\n                if (symbol == EOF) {\n                    throw new Error(errStr || \'Parsing halted.\');\n                }\n\n                // discard current lookahead and grab another\n                yyleng = this.lexer.yyleng;\n                yytext = this.lexer.yytext;\n                yylineno = this.lexer.yylineno;\n                yyloc = this.lexer.yylloc;\n                symbol = lex();\n            }\n\n            // try to recover from error\n            while (1) {\n                // check for error recovery rule in this state\n                if ((TERROR.toString()) in table[state]) {\n                    break;\n                }\n                if (state == 0) {\n                    throw new Error(errStr || \'Parsing halted.\');\n                }\n                popStack(1);\n                state = stack[stack.length-1];\n            }\n\n            preErrorSymbol = symbol; // save the lookahead token\n            symbol = TERROR;         // insert generic error symbol as new lookahead\n            state = stack[stack.length-1];\n            action = table[state] && table[state][TERROR];\n            recovering = 3; // allow 3 real symbols to be shifted before reporting a new error\n        }\n\n        // this shouldn\'t happen, unless resolve defaults are off\n        if (action[0] instanceof Array && action.length > 1) {\n            throw new Error(\'Parse Error: multiple actions possible at state: \'+state+\', token: \'+symbol);\n        }\n\n        switch (action[0]) {\n\n            case 1: // shift\n                //this.shiftCount++;\n\n                stack.push(symbol);\n                vstack.push(this.lexer.yytext);\n                lstack.push(this.lexer.yylloc);\n                stack.push(action[1]); // push state\n                symbol = null;\n                if (!preErrorSymbol) { // normal execution/no error\n                    yyleng = this.lexer.yyleng;\n                    yytext = this.lexer.yytext;\n                    yylineno = this.lexer.yylineno;\n                    yyloc = this.lexer.yylloc;\n                    if (recovering > 0)\n                        recovering--;\n                } else { // error just occurred, resume old lookahead f/ before error\n                    symbol = preErrorSymbol;\n                    preErrorSymbol = null;\n                }\n                break;\n\n            case 2: // reduce\n                //this.reductionCount++;\n\n                len = this.productions_[action[1]][1];\n\n                // perform semantic action\n                yyval.$ = vstack[vstack.length-len]; // default to $$ = $1\n                // default location, uses first token for firsts, last for lasts\n                yyval._$ = {\n                    first_line: lstack[lstack.length-(len||1)].first_line,\n                    last_line: lstack[lstack.length-1].last_line,\n                    first_column: lstack[lstack.length-(len||1)].first_column,\n                    last_column: lstack[lstack.length-1].last_column\n                };\n                r = this.performAction.call(yyval, yytext, yyleng, yylineno, this.yy, action[1], vstack, lstack);\n\n                if (typeof r !== \'undefined\') {\n                    return r;\n                }\n\n                // pop off stack\n                if (len) {\n                    stack = stack.slice(0,-1*len*2);\n                    vstack = vstack.slice(0, -1*len);\n                    lstack = lstack.slice(0, -1*len);\n                }\n\n                stack.push(this.productions_[action[1]][0]);    // push nonterminal (reduce)\n                vstack.push(yyval.$);\n                lstack.push(yyval._$);\n                // goto new state = table[STATE][NONTERMINAL]\n                newState = table[stack[stack.length-2]][stack[stack.length-1]];\n                stack.push(newState);\n                break;\n\n            case 3: // accept\n                return true;\n        }\n\n    }\n\n    return true;\n}};\n/* Jison generated lexer */\nvar lexer = (function(){\nvar lexer = ({EOF:1,\nparseError:function parseError(str, hash) {\n        if (this.yy.parseError) {\n            this.yy.parseError(str, hash);\n        } else {\n            throw new Error(str);\n        }\n    },\nsetInput:function (input) {\n        this._input = input;\n        this._more = this._less = this.done = false;\n        this.yylineno = this.yyleng = 0;\n        this.yytext = this.matched = this.match = \'\';\n        this.conditionStack = [\'INITIAL\'];\n        this.yylloc = {first_line:1,first_column:0,last_line:1,last_column:0};\n        return this;\n    },\ninput:function () {\n        var ch = this._input[0];\n        this.yytext+=ch;\n        this.yyleng++;\n        this.match+=ch;\n        this.matched+=ch;\n        var lines = ch.match(/\\n/);\n        if (lines) this.yylineno++;\n        this._input = this._input.slice(1);\n        return ch;\n    },\nunput:function (ch) {\n        this._input = ch + this._input;\n        return this;\n    },\nmore:function () {\n        this._more = true;\n        return this;\n    },\nless:function (n) {\n        this._input = this.match.slice(n) + this._input;\n    },\npastInput:function () {\n        var past = this.matched.substr(0, this.matched.length - this.match.length);\n        return (past.length > 20 ? \'...\':\'\') + past.substr(-20).replace(/\\n/g, "");\n    },\nupcomingInput:function () {\n        var next = this.match;\n        if (next.length < 20) {\n            next += this._input.substr(0, 20-next.length);\n        }\n        return (next.substr(0,20)+(next.length > 20 ? \'...\':\'\')).replace(/\\n/g, "");\n    },\nshowPosition:function () {\n        var pre = this.pastInput();\n        var c = new Array(pre.length + 1).join("-");\n        return pre + this.upcomingInput() + "\\n" + c+"^";\n    },\nnext:function () {\n        if (this.done) {\n            return this.EOF;\n        }\n        if (!this._input) this.done = true;\n\n        var token,\n            match,\n            tempMatch,\n            index,\n            col,\n            lines;\n        if (!this._more) {\n            this.yytext = \'\';\n            this.match = \'\';\n        }\n        var rules = this._currentRules();\n        for (var i=0;i < rules.length; i++) {\n            tempMatch = this._input.match(this.rules[rules[i]]);\n            if (tempMatch && (!match || tempMatch[0].length > match[0].length)) {\n                match = tempMatch;\n                index = i;\n                if (!this.options.flex) break;\n            }\n        }\n        if (match) {\n            lines = match[0].match(/\\n.*/g);\n            if (lines) this.yylineno += lines.length;\n            this.yylloc = {first_line: this.yylloc.last_line,\n                           last_line: this.yylineno+1,\n                           first_column: this.yylloc.last_column,\n                           last_column: lines ? lines[lines.length-1].length-1 : this.yylloc.last_column + match[0].length}\n            this.yytext += match[0];\n            this.match += match[0];\n            this.yyleng = this.yytext.length;\n            this._more = false;\n            this._input = this._input.slice(match[0].length);\n            this.matched += match[0];\n            token = this.performAction.call(this, this.yy, this, rules[index],this.conditionStack[this.conditionStack.length-1]);\n            if (this.done && this._input) this.done = false;\n            if (token) return token;\n            else return;\n        }\n        if (this._input === "") {\n            return this.EOF;\n        } else {\n            this.parseError(\'Lexical error on line \'+(this.yylineno+1)+\'. Unrecognized text.\\n\'+this.showPosition(), \n                    {text: "", token: null, line: this.yylineno});\n        }\n    },\nlex:function lex() {\n        var r = this.next();\n        if (typeof r !== \'undefined\') {\n            return r;\n        } else {\n            return this.lex();\n        }\n    },\nbegin:function begin(condition) {\n        this.conditionStack.push(condition);\n    },\npopState:function popState() {\n        return this.conditionStack.pop();\n    },\n_currentRules:function _currentRules() {\n        return this.conditions[this.conditionStack[this.conditionStack.length-1]].rules;\n    },\ntopState:function () {\n        return this.conditionStack[this.conditionStack.length-2];\n    },\npushState:function begin(condition) {\n        this.begin(condition);\n    }});\nlexer.options = {};\nlexer.performAction = function anonymous(yy,yy_,$avoiding_name_collisions,YY_START) {\n\nvar YYSTATE=YY_START\nswitch($avoiding_name_collisions) {\ncase 0:/* skip whitespace */\nbreak;\ncase 1:return 6\nbreak;\ncase 2:yy_.yytext = yy_.yytext.substr(1,yy_.yyleng-2); return 4\nbreak;\ncase 3:return 17\nbreak;\ncase 4:return 18\nbreak;\ncase 5:return 23\nbreak;\ncase 6:return 24\nbreak;\ncase 7:return 22\nbreak;\ncase 8:return 21\nbreak;\ncase 9:return 10\nbreak;\ncase 10:return 11\nbreak;\ncase 11:return 8\nbreak;\ncase 12:return 14\nbreak;\ncase 13:return \'INVALID\'\nbreak;\n}\n};\nlexer.rules = [/^(?:\\s+)/,/^(?:(-?([0-9]|[1-9][0-9]+))(\\.[0-9]+)?([eE][-+]?[0-9]+)?\\b)/,/^(?:"(?:\\\\[\\\\"bfnrt/]|\\\\u[a-fA-F0-9]{4}|[^\\\\\\0-\\x09\\x0a-\\x1f"])*")/,/^(?:\\{)/,/^(?:\\})/,/^(?:\\[)/,/^(?:\\])/,/^(?:,)/,/^(?::)/,/^(?:true\\b)/,/^(?:false\\b)/,/^(?:null\\b)/,/^(?:$)/,/^(?:.)/];\nlexer.conditions = {"INITIAL":{"rules":[0,1,2,3,4,5,6,7,8,9,10,11,12,13],"inclusive":true}};\n\n\n;\nreturn lexer;})()\nparser.lexer = lexer;\nreturn parser;\n})();\nif (typeof require !== \'undefined\' && typeof exports !== \'undefined\') {\nexports.parser = jsonlint;\nexports.parse = function () { return jsonlint.parse.apply(jsonlint, arguments); }\nexports.main = function commonjsMain(args) {\n    if (!args[1])\n        throw new Error(\'Usage: \'+args[0]+\' FILE\');\n    if (typeof process !== \'undefined\') {\n        var source = require(\'fs\').readFileSync(require(\'path\').join(process.cwd(), args[1]), "utf8");\n    } else {\n        var cwd = require("file").path(require("file").cwd());\n        var source = cwd.join(args[1]).read({charset: "utf-8"});\n    }\n    return exports.parser.parse(source);\n}\nif (typeof module !== \'undefined\' && require.main === module) {\n  exports.main(typeof process !== \'undefined\' ? process.argv.slice(1) : require("system").args);\n}\n}'},"5b39":function(t,e,n){"use strict";n.d(e,"a",(function(){return r}));n("ac6a"),n("456d"),n("28a5"),n("aef6"),n("f559");function r(t,e,n){if(t&&e&&((null==n||n<0)&&(n=e.length),0!==n)){for(var r=e.substring(0,n),i=e.substring(n),o=!1,a=0,c=n;c>=0;c--){if(" "===r[c]){a=c+1;break}if("{"===r[c]&&c>0&&"$"===r[c-1]){o=!0,a=c+1;break}}var l=r.substring(a,n);if(l.startsWith("http.request('")){var p="";return""===i.trim()?p="')":")"===i.trim()&&(p="'"),s(t.apiNames,l.substring("http.request('".length),p)}if(l.startsWith('http.request("')){var u="";return""===i.trim()?u='")':")"===i.trim()&&(u='"'),s(t.apiNames,l.substring('http.request("'.length),u)}for(var h=l.endsWith("."),f=l.split("."),d=o?t.globalMap:t.varCodes,y=0;y<f.length;y++){var m=null;if((y<f.length-1||h)&&(m=d[f[y]]),!m){var g=Object.keys(d)||[];d=[];for(var b=0;b<g.length;b++){var x="",v=g[b]+"";o&&""===i.trim()&&(x="}"),v.startsWith(f[y])&&v!==f[y]&&d.push({displayText:v,text:v.substring(f[y].length)+x})}return d}d=m}return Object.keys(d)}}function s(t,e,n){if(n=n||"",null==t||0===t.length)return null;var r=[];for(var s in t){var i=t[s];t[s].startsWith(e)&&r.push({displayText:i,text:i.substring(e.length)+n})}return r}},"705e":function(t,e,n){},7309:function(t,e,n){"use strict";n.d(e,"c",(function(){return s})),n.d(e,"d",(function(){return i})),n.d(e,"a",(function(){return o})),n.d(e,"b",(function(){return a}));var r=n("b775");function s(t){return Object(r["a"])({url:"/api/script/runScript",method:"post",data:t})}function i(t){return Object(r["a"])({url:"/api/script/toScript",method:"get",params:t})}function o(t){return Object(r["a"])({url:"/api/script/apiNameTips",method:"get",params:t})}function a(t){return Object(r["a"])({url:"/api/script/codeTips",method:"get",params:t})}},ae67:function(t,e,n){n("f2b5")(n("3f23"))},d2de:function(t,e,n){(function(t){t(n("56b3"))})((function(t){"use strict";t.registerHelper("lint","json",(function(e){var n=[];if(!window.jsonlint)return window.console&&window.console.error("Error: window.jsonlint not defined, CodeMirror JSON linting cannot run."),n;var r=window.jsonlint.parser||window.jsonlint;r.parseError=function(e,r){var s=r.loc;n.push({from:t.Pos(s.first_line-1,s.first_column),to:t.Pos(s.last_line-1,s.last_column),message:e})};try{r.parse(e)}catch(s){}return n}))}))},f2b5:function(t,e){t.exports=function(t){function e(t){"undefined"!==typeof console&&(console.error||console.log)("[Script Loader]",t)}function n(){return"undefined"!==typeof attachEvent&&"undefined"===typeof addEventListener}try{"undefined"!==typeof execScript&&n()?execScript(t):"undefined"!==typeof eval?eval.call(null,t):e("EvalError: No eval function available")}catch(r){e(r)}}},f478:function(t,e,n){"use strict";n("705e")}}]);