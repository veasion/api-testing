(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-fc56e352"],{"0092":function(e,t,s){},"133c":function(e,t,s){"use strict";s("66bf")},"304e":function(e,t,s){},"66bf":function(e,t,s){},"6e50":function(e,t,s){"use strict";s("304e")},bb70:function(e,t,s){"use strict";s("0092")},ecac:function(e,t,s){"use strict";s.r(t);var a=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"app-container"},[e.user?s("div",[s("el-row",{attrs:{gutter:20}},[s("el-col",{attrs:{span:6,xs:24}},[s("user-card",{attrs:{user:e.user}})],1),e._v(" "),s("el-col",{attrs:{span:18,xs:24}},[s("el-card",[s("el-tabs",{model:{value:e.activeTab,callback:function(t){e.activeTab=t},expression:"activeTab"}},[s("el-tab-pane",{attrs:{label:"Activity",name:"activity"}},[s("activity")],1),e._v(" "),s("el-tab-pane",{attrs:{label:"Timeline",name:"timeline"}},[s("timeline")],1),e._v(" "),s("el-tab-pane",{attrs:{label:"Account",name:"account"}},[s("account",{attrs:{user:e.user}})],1)],1)],1)],1)],1)],1):e._e()])},i=[],n=(s("ac67"),s("1bc7"),s("32ea"),s("a450"),s("8955")),r=s("52c1"),c=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("el-card",{staticStyle:{"margin-bottom":"20px"}},[s("div",{staticClass:"clearfix",attrs:{slot:"header"},slot:"header"},[s("span",[e._v("About me")])]),e._v(" "),s("div",{staticClass:"user-profile"},[s("div",{staticClass:"box-center"},[s("pan-thumb",{attrs:{image:e.user.avatar,height:"100px",width:"100px",hoverable:!1}},[s("div",[e._v("Hello")]),e._v("\n        "+e._s(e.user.role)+"\n      ")])],1),e._v(" "),s("div",{staticClass:"box-center"},[s("div",{staticClass:"user-name text-center"},[e._v(e._s(e.user.name))]),e._v(" "),s("div",{staticClass:"user-role text-center text-muted"},[e._v(e._s(e._f("uppercaseFirst")(e.user.role)))])])]),e._v(" "),s("div",{staticClass:"user-bio"},[s("div",{staticClass:"user-education user-bio-section"},[s("div",{staticClass:"user-bio-section-header"},[s("svg-icon",{attrs:{"icon-class":"education"}}),s("span",[e._v("Education")])],1),e._v(" "),s("div",{staticClass:"user-bio-section-body"},[s("div",{staticClass:"text-muted"},[e._v("\n          JS in Computer Science from the University of Technology\n        ")])])]),e._v(" "),s("div",{staticClass:"user-skills user-bio-section"},[s("div",{staticClass:"user-bio-section-header"},[s("svg-icon",{attrs:{"icon-class":"skill"}}),s("span",[e._v("Skills")])],1),e._v(" "),s("div",{staticClass:"user-bio-section-body"},[s("div",{staticClass:"progress-item"},[s("span",[e._v("Vue")]),e._v(" "),s("el-progress",{attrs:{percentage:70}})],1),e._v(" "),s("div",{staticClass:"progress-item"},[s("span",[e._v("JavaScript")]),e._v(" "),s("el-progress",{attrs:{percentage:18}})],1),e._v(" "),s("div",{staticClass:"progress-item"},[s("span",[e._v("Css")]),e._v(" "),s("el-progress",{attrs:{percentage:12}})],1),e._v(" "),s("div",{staticClass:"progress-item"},[s("span",[e._v("ESLint")]),e._v(" "),s("el-progress",{attrs:{percentage:100,status:"success"}})],1)])])])])},l=[],o=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"pan-item",style:{zIndex:e.zIndex,height:e.height,width:e.width}},[s("div",{staticClass:"pan-info"},[s("div",{staticClass:"pan-info-roles-container"},[e._t("default")],2)]),e._v(" "),s("div",{staticClass:"pan-thumb",style:{backgroundImage:"url("+e.image+")"}})])},u=[],m=(s("e680"),{name:"PanThumb",props:{image:{type:String,required:!0},zIndex:{type:Number,default:1},width:{type:String,default:"150px"},height:{type:String,default:"150px"}}}),p=m,v=(s("133c"),s("cba8")),d=Object(v["a"])(p,o,u,!1,null,"799537af",null),f=d.exports,b={components:{PanThumb:f},props:{user:{type:Object,default:function(){return{name:"",email:"",avatar:"",roles:""}}}}},_=b,h=(s("bb70"),Object(v["a"])(_,c,l,!1,null,"72e10cd6",null)),g=h.exports,C=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"user-activity"},[s("div",{staticClass:"post"},[s("div",{staticClass:"user-block"},[s("img",{staticClass:"img-circle",attrs:{src:"https://wpimg.wallstcn.com/57ed425a-c71e-4201-9428-68760c0537c4.jpg"+e.avatarPrefix}}),e._v(" "),s("span",{staticClass:"username text-muted"},[e._v("Iron Man")]),e._v(" "),s("span",{staticClass:"description"},[e._v("Shared publicly - 7:30 PM today")])]),e._v(" "),s("p",[e._v("\n      Lorem ipsum represents a long-held tradition for designers,\n      typographers and the like. Some people hate it and argue for\n      its demise, but others ignore the hate as they create awesome\n      tools to help create filler text for everyone from bacon lovers\n      to Charlie Sheen fans.\n    ")]),e._v(" "),s("ul",{staticClass:"list-inline"},[e._m(0),e._v(" "),s("li",[s("span",{staticClass:"link-black text-sm"},[s("svg-icon",{attrs:{"icon-class":"like"}}),e._v("\n          Like\n        ")],1)])])]),e._v(" "),s("div",{staticClass:"post"},[s("div",{staticClass:"user-block"},[s("img",{staticClass:"img-circle",attrs:{src:"https://wpimg.wallstcn.com/9e2a5d0a-bd5b-457f-ac8e-86554616c87b.jpg"+e.avatarPrefix}}),e._v(" "),s("span",{staticClass:"username text-muted"},[e._v("Captain American")]),e._v(" "),s("span",{staticClass:"description"},[e._v("Sent you a message - yesterday")])]),e._v(" "),s("p",[e._v("\n      Lorem ipsum represents a long-held tradition for designers,\n      typographers and the like. Some people hate it and argue for\n      its demise, but others ignore the hate as they create awesome\n      tools to help create filler text for everyone from bacon lovers\n      to Charlie Sheen fans.\n    ")]),e._v(" "),s("ul",{staticClass:"list-inline"},[e._m(1),e._v(" "),s("li",[s("span",{staticClass:"link-black text-sm"},[s("svg-icon",{attrs:{"icon-class":"like"}}),e._v("\n          Like\n        ")],1)])])]),e._v(" "),s("div",{staticClass:"post"},[s("div",{staticClass:"user-block"},[s("img",{staticClass:"img-circle",attrs:{src:"https://wpimg.wallstcn.com/fb57f689-e1ab-443c-af12-8d4066e202e2.jpg"+e.avatarPrefix}}),e._v(" "),s("span",{staticClass:"username"},[e._v("Spider Man")]),e._v(" "),s("span",{staticClass:"description"},[e._v("Posted 4 photos - 2 days ago")])]),e._v(" "),s("div",{staticClass:"user-images"},[s("el-carousel",{attrs:{interval:6e3,type:"card",height:"220px"}},e._l(e.carouselImages,(function(t){return s("el-carousel-item",{key:t},[s("img",{staticClass:"image",attrs:{src:t+e.carouselPrefix}})])})),1)],1),e._v(" "),s("ul",{staticClass:"list-inline"},[e._m(2),e._v(" "),s("li",[s("span",{staticClass:"link-black text-sm"},[s("svg-icon",{attrs:{"icon-class":"like"}}),e._v(" Like")],1)])])])])},y=[function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("li",[s("span",{staticClass:"link-black text-sm"},[s("i",{staticClass:"el-icon-share"}),e._v("\n          Share\n        ")])])},function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("li",[s("span",{staticClass:"link-black text-sm"},[s("i",{staticClass:"el-icon-share"}),e._v("\n          Share\n        ")])])},function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("li",[s("span",{staticClass:"link-black text-sm"},[s("i",{staticClass:"el-icon-share"}),e._v(" Share")])])}],x="?imageView2/1/w/80/h/80",k="?imageView2/2/h/440",w={data:function(){return{carouselImages:["https://wpimg.wallstcn.com/9679ffb0-9e0b-4451-9916-e21992218054.jpg","https://wpimg.wallstcn.com/bcce3734-0837-4b9f-9261-351ef384f75a.jpg","https://wpimg.wallstcn.com/d1d7b033-d75e-4cd6-ae39-fcd5f1c0a7c5.jpg","https://wpimg.wallstcn.com/50530061-851b-4ca5-9dc5-2fead928a939.jpg"],avatarPrefix:x,carouselPrefix:k}}},j=w,O=(s("6e50"),Object(v["a"])(j,C,y,!1,null,"1066d76c",null)),P=O.exports,S=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"block"},[s("el-timeline",e._l(e.timeline,(function(t,a){return s("el-timeline-item",{key:a,attrs:{timestamp:t.timestamp,placement:"top"}},[s("el-card",[s("h4",[e._v(e._s(t.title))]),e._v(" "),s("p",[e._v(e._s(t.content))])])],1)})),1)],1)},E=[],$={data:function(){return{timeline:[{timestamp:"2019/4/20",title:"Update Github template",content:"PanJiaChen committed 2019/4/20 20:46"},{timestamp:"2019/4/21",title:"Update Github template",content:"PanJiaChen committed 2019/4/21 20:46"},{timestamp:"2019/4/22",title:"Build Template",content:"PanJiaChen committed 2019/4/22 20:46"},{timestamp:"2019/4/23",title:"Release New Version",content:"PanJiaChen committed 2019/4/23 20:46"}]}}},T=$,J=Object(v["a"])(T,S,E,!1,null,null,null),U=J.exports,I=function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("el-form",[s("el-form-item",{attrs:{label:"Name"}},[s("el-input",{model:{value:e.user.name,callback:function(t){e.$set(e.user,"name","string"===typeof t?t.trim():t)},expression:"user.name"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"Email"}},[s("el-input",{model:{value:e.user.email,callback:function(t){e.$set(e.user,"email","string"===typeof t?t.trim():t)},expression:"user.email"}})],1),e._v(" "),s("el-form-item",[s("el-button",{attrs:{type:"primary"},on:{click:e.submit}},[e._v("Update")])],1)],1)},A=[],L={props:{user:{type:Object,default:function(){return{name:"",email:""}}}},methods:{submit:function(){this.$message({message:"User information has been updated successfully",type:"success",duration:5e3})}}},D=L,V=Object(v["a"])(D,I,A,!1,null,null,null),z=V.exports;function M(e,t){var s=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),s.push.apply(s,a)}return s}function N(e){for(var t=1;t<arguments.length;t++){var s=null!=arguments[t]?arguments[t]:{};t%2?M(Object(s),!0).forEach((function(t){Object(n["a"])(e,t,s[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(s)):M(Object(s)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(s,t))}))}return e}var G={name:"Profile",components:{UserCard:g,Activity:P,Timeline:U,Account:z},data:function(){return{user:{},activeTab:"activity"}},computed:N({},Object(r["b"])(["name","avatar","roles"])),created:function(){this.getUser()},methods:{getUser:function(){this.user={name:this.name,role:this.roles.join(" | "),email:"admin@test.com",avatar:this.avatar}}}},q=G,B=Object(v["a"])(q,a,i,!1,null,null,null);t["default"]=B.exports}}]);