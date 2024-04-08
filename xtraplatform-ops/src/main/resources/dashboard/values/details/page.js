(()=>{var e={};e.id=582,e.ids=[582],e.modules={7849:e=>{"use strict";e.exports=require("next/dist/client/components/action-async-storage.external")},2934:e=>{"use strict";e.exports=require("next/dist/client/components/action-async-storage.external.js")},5403:e=>{"use strict";e.exports=require("next/dist/client/components/request-async-storage.external")},4580:e=>{"use strict";e.exports=require("next/dist/client/components/request-async-storage.external.js")},4749:e=>{"use strict";e.exports=require("next/dist/client/components/static-generation-async-storage.external")},5869:e=>{"use strict";e.exports=require("next/dist/client/components/static-generation-async-storage.external.js")},399:e=>{"use strict";e.exports=require("next/dist/compiled/next-server/app-page.runtime.prod.js")},1017:e=>{"use strict";e.exports=require("path")},7310:e=>{"use strict";e.exports=require("url")},447:(e,t,r)=>{"use strict";r.r(t),r.d(t,{GlobalError:()=>i.a,__next_app__:()=>p,originalPathname:()=>u,pages:()=>d,routeModule:()=>h,tree:()=>c});var s=r(482),a=r(9108),n=r(2563),i=r.n(n),l=r(8300),o={};for(let e in l)0>["default","tree","pages","GlobalError","originalPathname","__next_app__","routeModule"].indexOf(e)&&(o[e]=()=>l[e]);r.d(t,o);let c=["",{children:["values",{children:["details",{children:["__PAGE__",{},{page:[()=>Promise.resolve().then(r.bind(r,5774)),"/Users/az/development/ws-ldproxy-4/dashboard/src/app/values/details/page.tsx"]}]},{}]},{metadata:{icon:[async e=>(await Promise.resolve().then(r.bind(r,3881))).default(e)],apple:[],openGraph:[],twitter:[],manifest:void 0}}]},{layout:[()=>Promise.resolve().then(r.bind(r,1342)),"/Users/az/development/ws-ldproxy-4/dashboard/src/app/layout.tsx"],"not-found":[()=>Promise.resolve().then(r.t.bind(r,9361,23)),"next/dist/client/components/not-found-error"],metadata:{icon:[async e=>(await Promise.resolve().then(r.bind(r,3881))).default(e)],apple:[],openGraph:[],twitter:[],manifest:void 0}}],d=["/Users/az/development/ws-ldproxy-4/dashboard/src/app/values/details/page.tsx"],u="/values/details/page",p={require:r,loadChunk:()=>Promise.resolve()},h=new s.AppPageRouteModule({definition:{kind:a.x.APP_PAGE,page:"/values/details/page",pathname:"/values/details",bundlePath:"",filename:"",appPaths:[]},userland:{loaderTree:c}})},614:(e,t,r)=>{Promise.resolve().then(r.t.bind(r,2583,23)),Promise.resolve().then(r.t.bind(r,6840,23)),Promise.resolve().then(r.t.bind(r,8771,23)),Promise.resolve().then(r.t.bind(r,3225,23)),Promise.resolve().then(r.t.bind(r,9295,23)),Promise.resolve().then(r.t.bind(r,3982,23))},245:(e,t,r)=>{Promise.resolve().then(r.bind(r,8473))},8992:(e,t,r)=>{Promise.resolve().then(r.bind(r,7114))},8473:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>g});var s=r(5344),a=r(7019),n=r.n(a),i=r(6506),l=r(1453);r(3729);var o=r(6256);function c({children:e,...t}){return s.jsx(o.f,{...t,children:e})}var d=r(3174),u=r(8428),p=r(3412);function h({title:e,route:t,icon:r}){let a=(0,u.usePathname)(),n=r?(0,p.q)(r):null;return s.jsx(d.z,{variant:a&&a.startsWith(t)?"secondary":"ghost",className:"w-full justify-start",asChild:!0,children:(0,s.jsxs)(i.default,{href:t,children:[n?s.jsx(n,{className:"mr-2 h-4 w-4"}):null,e]})},e)}function m({title:e,entries:t}){return(0,s.jsxs)("div",{className:"px-3 py-2",children:[s.jsx("h2",{className:"mb-2 px-4 text-lg font-semibold tracking-tight",children:e}),s.jsx("div",{className:"space-y-1",children:t.map(({title:e,selected:t,route:r,icon:a})=>r?s.jsx(h,{title:e,route:r,icon:a},e):(0,s.jsxs)(d.z,{variant:t?"secondary":"ghost",className:"w-full justify-start",children:[(()=>{let e=a?(0,p.q)(a):null;return e?s.jsx(e,{className:"mr-2 h-4 w-4"}):null})(),e]},e))})]})}function x({className:e,sections:t}){return s.jsx("div",{className:(0,l.cn)("pb-12",e),children:s.jsx("div",{className:"space-y-4 py-4",children:t.map(({title:e,entries:t})=>s.jsx(m,{title:e,entries:t},e))})})}var f=r(4664);function g({children:e}){return s.jsx("html",{lang:"en",children:s.jsx("body",{className:(0,l.cn)("min-h-screen bg-background font-sans antialiased",n().variable),children:s.jsx(c,{attribute:"class",storageKey:"ldproxy-portal",enableSystem:!0,disableTransitionOnChange:!0,children:(0,s.jsxs)("div",{className:"hidden h-full flex-col md:flex",children:[(0,s.jsxs)("div",{className:"flex flex-col items-start justify-between space-y-2 py-4 px-4 lg:px-8 sm:flex-row sm:items-center sm:space-y-0 md:h-16",children:[s.jsx("h2",{className:"text-lg font-semibold whitespace-nowrap",children:(0,s.jsxs)(i.default,{href:"/",children:[s.jsx(f.QGC,{className:"mr-2 h-4 w-4 inline"}),s.jsx("span",{children:"dashboard"})]})}),s.jsx("div",{className:"ml-auto flex w-full space-x-2 sm:justify-end"})]}),s.jsx("div",{className:"border-t",children:s.jsx("div",{className:"bg-background",children:(0,s.jsxs)("div",{className:"grid lg:grid-cols-5",children:[s.jsx(x,{sections:[{title:"demo.ldproxy.net",entries:[{title:"Deployment",icon:p.c.Play,route:"/deployment"},{title:"Entities",icon:p.c.Id,route:"/entities"},{title:"Values",icon:p.c.Code,route:"/values"}]}],className:"hidden lg:block"}),s.jsx("div",{className:"col-span-3 lg:col-span-4 lg:border-l",children:s.jsx("div",{className:"h-full px-4 py-6 lg:px-8",children:e})})]})})})]})})})})}r(3824)},7114:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>p});var s=r(5344),a=r(3174),n=r(1453),i=r(4664),l=r(3729),o=r(8428),c=r(8211),d=r.n(c);r(3889),r(6850);var u=r(9426);let p=()=>s.jsx(l.Suspense,{fallback:s.jsx("div",{children:"Loading..."}),children:s.jsx(h,{})});function h(){let[e,t]=(0,l.useState)({}),[r,c]=(0,l.useState)(!1),p=(0,o.useRouter)(),h="",m=(0,o.useSearchParams)();null!==m&&(h=m.get("id"));let x=async()=>{try{if(null!==h){let e=await (0,n.AR)(h);"Method not allowed"===e.message?c(!0):t(e)}}catch(e){c(!0),console.error("Error loading cfg:",e)}};return(0,l.useEffect)(()=>{x()},[]),(0,s.jsxs)(s.Fragment,{children:[(0,s.jsxs)("div",{className:"flex justify-between items-center",children:[(0,s.jsxs)("div",{className:"flex items-center",children:[s.jsx("a",{onClick:()=>p.back(),className:"font-bold flex items-center cursor-pointer text-blue-500 hover:text-blue-400",children:s.jsx(i.wyc,{className:"mr-[-1px] h-6 w-6"})}),s.jsx("h2",{className:"text-2xl font-semibold tracking-tight ml-2",children:h?h.split("_")[1]:"Not Found..."})]}),s.jsx("div",{className:"p-8 pt-6",children:(0,s.jsxs)(a.z,{onClick:()=>{x()},className:"font-bold",children:[s.jsx(i.BGW,{className:"mr-2 h-4 w-4"}),"Reload"]})})]}),s.jsx("div",{className:"p-8 pt-6",children:s.jsx("div",{style:{backgroundColor:"#f5f5f5",borderRadius:"8px",padding:"16px",border:"1px solid lightgray"},children:r?"No results.":0===Object.keys(e).length?(0,s.jsxs)("div",{className:"flex items-center",children:[s.jsx(u.Z,{color:"#123abc",loading:!0,size:20}),s.jsx("span",{style:{marginLeft:"5px"},children:"Loading..."})]}):Object.entries(e).map(([e,t])=>{let r=JSON.stringify(t,null,2),a=d().highlight(r,d().languages.json,"json");return(0,s.jsxs)("div",{style:{display:"flex"},children:[(0,s.jsxs)("span",{children:[e,":"]}),s.jsx("pre",{dangerouslySetInnerHTML:{__html:a},style:{margin:"0 0 0 10px"}})]},e)})})})]})}},3174:(e,t,r)=>{"use strict";r.d(t,{z:()=>c});var s=r(5344),a=r(3729),n=r(2751),i=r(8720),l=r(1453);let o=(0,i.j)("inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50",{variants:{variant:{default:"bg-primary text-primary-foreground hover:bg-primary/90",destructive:"bg-destructive text-destructive-foreground hover:bg-destructive/90",outline:"border border-input bg-background hover:bg-accent hover:text-accent-foreground",secondary:"bg-secondary text-secondary-foreground hover:bg-secondary/80",ghost:"hover:bg-accent hover:text-accent-foreground",link:"text-primary underline-offset-4 hover:underline"},size:{default:"h-10 px-4 py-2",sm:"h-9 rounded-md px-3",lg:"h-11 rounded-md px-8",icon:"h-10 w-10"}},defaultVariants:{variant:"default",size:"default"}}),c=a.forwardRef(({className:e,variant:t,size:r,asChild:a=!1,...i},c)=>{let d=a?n.g7:"button";return s.jsx(d,{className:(0,l.cn)(o({variant:t,size:r,className:e})),ref:c,...i})});c.displayName="Button"},3412:(e,t,r)=>{"use strict";r.d(t,{c:()=>a,q:()=>n});var s=r(4664);let a={Play:"Play",Id:"Id",Code:"Code",ListBullet:"ListBullet",Upload:"Upload",Clock:"Clock",Desktop:"Desktop"},n=e=>{switch(e){case a.Play:return s.o1U;case a.Id:return s.Xwj;case a.Code:return s.dNJ;case a.ListBullet:return s.jVc;case a.Upload:return s.rG2;case a.Clock:return s.T39;case a.Desktop:return s.ugZ;default:return}}},1453:(e,t,r)=>{"use strict";r.d(t,{AR:()=>x,C5:()=>d,KX:()=>p,Kt:()=>u,ZH:()=>h,cn:()=>n,fL:()=>c,fi:()=>o,hs:()=>m});var s=r(6815),a=r(9377);function n(...e){return(0,a.m6)((0,s.W)(e))}let i="/api",l="/api",o=async()=>{try{let e=await fetch(i+"/entities"),t=await e.json();return Object.keys(t).flatMap(e=>t[e].map(t=>({type:e,uid:`${e}_${t.id}`,...t})))}catch(e){throw console.error("Error:",e),e}},c=async()=>{try{let e=await fetch(i+"/health"),t=await e.json();return Object.keys(t).map(e=>({name:e,...t[e],capabilities:t[e].capabilities?Object.keys(t[e].capabilities).map(r=>({name:r,...t[e].capabilities[r]})):void 0,components:t[e].components?Object.keys(t[e].components).map(r=>({name:r,...t[e].components[r]})):void 0}))}catch(e){throw console.error("Error:",e),e}},d=async()=>{try{let e=await fetch(i+"/info");return await e.json()}catch(e){throw console.error("Error:",e),e}},u=async()=>{try{let e=await fetch(i+"/metrics"),t=await e.json();return{uptime:t.gauges["jvm.attribute.uptime"].value,memory:t.gauges["jvm.memory.total.used"].value}}catch(e){throw console.error("Error:",e),e}},p=async()=>{try{let e=await fetch(l+"/values");return await e.json()}catch(e){throw console.error("Error:",e),e}},h=async e=>{try{let t=e.replace(/_/g,"/"),r=await fetch(`${l}/cfg/entities/${t}`);if(!r.ok)throw Error(`HTTP error! status: ${r.status}`);return await r.json()}catch(e){throw console.error("Error:",e),e}},m=async()=>{try{let e=await fetch(l+"/cfg/global/deployment");if(!e.ok)throw Error(`HTTP error! status: ${e.status}`);return await e.json()}catch(e){throw console.error("Error:",e),e}},x=async e=>{try{let t=e.replace(/_/g,"/"),r=await fetch(`${l}/cfg/values/${t}`);if(!r.ok)throw Error(`HTTP error! status: ${r.status}`);return await r.json()}catch(e){throw console.error("Error:",e),e}}},1342:(e,t,r)=>{"use strict";r.r(t),r.d(t,{$$typeof:()=>n,__esModule:()=>a,default:()=>i});let s=(0,r(6843).createProxy)(String.raw`/Users/az/development/ws-ldproxy-4/dashboard/src/app/layout.tsx`),{__esModule:a,$$typeof:n}=s,i=s.default},5774:(e,t,r)=>{"use strict";r.r(t),r.d(t,{$$typeof:()=>n,__esModule:()=>a,default:()=>i});let s=(0,r(6843).createProxy)(String.raw`/Users/az/development/ws-ldproxy-4/dashboard/src/app/values/details/page.tsx`),{__esModule:a,$$typeof:n}=s,i=s.default},3881:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>a});var s=r(337);let a=e=>[{type:"image/x-icon",sizes:"16x16",url:(0,s.fillMetadataSegment)(".",e.params,"favicon.ico")+""}]},3824:()=>{}};var t=require("../../../webpack-runtime.js");t.C(e);var r=e=>t(t.s=e),s=t.X(0,[638,876,337,101],()=>r(447));module.exports=s})();