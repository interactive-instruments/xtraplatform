(()=>{var e={};e.id=931,e.ids=[931],e.modules={7849:e=>{"use strict";e.exports=require("next/dist/client/components/action-async-storage.external")},2934:e=>{"use strict";e.exports=require("next/dist/client/components/action-async-storage.external.js")},5403:e=>{"use strict";e.exports=require("next/dist/client/components/request-async-storage.external")},4580:e=>{"use strict";e.exports=require("next/dist/client/components/request-async-storage.external.js")},4749:e=>{"use strict";e.exports=require("next/dist/client/components/static-generation-async-storage.external")},5869:e=>{"use strict";e.exports=require("next/dist/client/components/static-generation-async-storage.external.js")},399:e=>{"use strict";e.exports=require("next/dist/compiled/next-server/app-page.runtime.prod.js")},1017:e=>{"use strict";e.exports=require("path")},7310:e=>{"use strict";e.exports=require("url")},6938:(e,t,r)=>{"use strict";r.r(t),r.d(t,{GlobalError:()=>o.a,__next_app__:()=>p,originalPathname:()=>u,pages:()=>d,routeModule:()=>m,tree:()=>c});var s=r(482),a=r(9108),n=r(2563),o=r.n(n),i=r(8300),l={};for(let e in i)0>["default","tree","pages","GlobalError","originalPathname","__next_app__","routeModule"].indexOf(e)&&(l[e]=()=>i[e]);r.d(t,l);let c=["",{children:["__PAGE__",{},{page:[()=>Promise.resolve().then(r.bind(r,1136)),"/Users/az/development/ws-ldproxy-4/dashboard/src/app/page.tsx"],metadata:{icon:[async e=>(await Promise.resolve().then(r.bind(r,3881))).default(e)],apple:[],openGraph:[],twitter:[],manifest:void 0}}]},{layout:[()=>Promise.resolve().then(r.bind(r,1342)),"/Users/az/development/ws-ldproxy-4/dashboard/src/app/layout.tsx"],"not-found":[()=>Promise.resolve().then(r.t.bind(r,9361,23)),"next/dist/client/components/not-found-error"],metadata:{icon:[async e=>(await Promise.resolve().then(r.bind(r,3881))).default(e)],apple:[],openGraph:[],twitter:[],manifest:void 0}}],d=["/Users/az/development/ws-ldproxy-4/dashboard/src/app/page.tsx"],u="/page",p={require:r,loadChunk:()=>Promise.resolve()},m=new s.AppPageRouteModule({definition:{kind:a.x.APP_PAGE,page:"/page",pathname:"/",bundlePath:"",filename:"",appPaths:[]},userland:{loaderTree:c}})},614:(e,t,r)=>{Promise.resolve().then(r.t.bind(r,2583,23)),Promise.resolve().then(r.t.bind(r,6840,23)),Promise.resolve().then(r.t.bind(r,8771,23)),Promise.resolve().then(r.t.bind(r,3225,23)),Promise.resolve().then(r.t.bind(r,9295,23)),Promise.resolve().then(r.t.bind(r,3982,23))},245:(e,t,r)=>{Promise.resolve().then(r.bind(r,8473))},6709:(e,t,r)=>{Promise.resolve().then(r.bind(r,1532))},8473:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>y});var s=r(5344),a=r(7019),n=r.n(a),o=r(6506),i=r(1453);r(3729);var l=r(6256);function c({children:e,...t}){return s.jsx(l.f,{...t,children:e})}var d=r(3174),u=r(8428),p=r(3412);function m({title:e,route:t,icon:r}){let a=(0,u.usePathname)(),n=r?(0,p.q)(r):null;return s.jsx(d.z,{variant:a&&a.startsWith(t)?"secondary":"ghost",className:"w-full justify-start",asChild:!0,children:(0,s.jsxs)(o.default,{href:t,children:[n?s.jsx(n,{className:"mr-2 h-4 w-4"}):null,e]})},e)}function h({title:e,entries:t}){return(0,s.jsxs)("div",{className:"px-3 py-2",children:[s.jsx("h2",{className:"mb-2 px-4 text-lg font-semibold tracking-tight",children:e}),s.jsx("div",{className:"space-y-1",children:t.map(({title:e,selected:t,route:r,icon:a})=>r?s.jsx(m,{title:e,route:r,icon:a},e):(0,s.jsxs)(d.z,{variant:t?"secondary":"ghost",className:"w-full justify-start",children:[(()=>{let e=a?(0,p.q)(a):null;return e?s.jsx(e,{className:"mr-2 h-4 w-4"}):null})(),e]},e))})]})}function x({className:e,sections:t}){return s.jsx("div",{className:(0,i.cn)("pb-12",e),children:s.jsx("div",{className:"space-y-4 py-4",children:t.map(({title:e,entries:t})=>s.jsx(h,{title:e,entries:t},e))})})}var f=r(4664);function y({children:e}){return s.jsx("html",{lang:"en",children:s.jsx("body",{className:(0,i.cn)("min-h-screen bg-background font-sans antialiased",n().variable),children:s.jsx(c,{attribute:"class",storageKey:"ldproxy-portal",enableSystem:!0,disableTransitionOnChange:!0,children:(0,s.jsxs)("div",{className:"hidden h-full flex-col md:flex",children:[(0,s.jsxs)("div",{className:"flex flex-col items-start justify-between space-y-2 py-4 px-4 lg:px-8 sm:flex-row sm:items-center sm:space-y-0 md:h-16",children:[s.jsx("h2",{className:"text-lg font-semibold whitespace-nowrap",children:(0,s.jsxs)(o.default,{href:"/",children:[s.jsx(f.QGC,{className:"mr-2 h-4 w-4 inline"}),s.jsx("span",{children:"dashboard"})]})}),s.jsx("div",{className:"ml-auto flex w-full space-x-2 sm:justify-end"})]}),s.jsx("div",{className:"border-t",children:s.jsx("div",{className:"bg-background",children:(0,s.jsxs)("div",{className:"grid lg:grid-cols-5",children:[s.jsx(x,{sections:[{title:"demo.ldproxy.net",entries:[{title:"Deployment",icon:p.c.Play,route:"/deployment"},{title:"Entities",icon:p.c.Id,route:"/entities"},{title:"Values",icon:p.c.Code,route:"/values"}]}],className:"hidden lg:block"}),s.jsx("div",{className:"col-span-3 lg:col-span-4 lg:border-l",children:s.jsx("div",{className:"h-full px-4 py-6 lg:px-8",children:e})})]})})})]})})})})}r(3824)},1532:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>o});var s=r(5344),a=r(3729),n=r(8428);function o(){let e=(0,n.useRouter)();return(0,a.useEffect)(()=>{e.push("/deployment")},[]),s.jsx("div",{className:"flex-1 space-y-4 p-8 pt-0",children:s.jsx("div",{className:"flex items-center justify-between space-y-2"})})}},3174:(e,t,r)=>{"use strict";r.d(t,{z:()=>c});var s=r(5344),a=r(3729),n=r(2751),o=r(8720),i=r(1453);let l=(0,o.j)("inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50",{variants:{variant:{default:"bg-primary text-primary-foreground hover:bg-primary/90",destructive:"bg-destructive text-destructive-foreground hover:bg-destructive/90",outline:"border border-input bg-background hover:bg-accent hover:text-accent-foreground",secondary:"bg-secondary text-secondary-foreground hover:bg-secondary/80",ghost:"hover:bg-accent hover:text-accent-foreground",link:"text-primary underline-offset-4 hover:underline"},size:{default:"h-10 px-4 py-2",sm:"h-9 rounded-md px-3",lg:"h-11 rounded-md px-8",icon:"h-10 w-10"}},defaultVariants:{variant:"default",size:"default"}}),c=a.forwardRef(({className:e,variant:t,size:r,asChild:a=!1,...o},c)=>{let d=a?n.g7:"button";return s.jsx(d,{className:(0,i.cn)(l({variant:t,size:r,className:e})),ref:c,...o})});c.displayName="Button"},3412:(e,t,r)=>{"use strict";r.d(t,{c:()=>a,q:()=>n});var s=r(4664);let a={Play:"Play",Id:"Id",Code:"Code",ListBullet:"ListBullet",Upload:"Upload",Clock:"Clock",Desktop:"Desktop"},n=e=>{switch(e){case a.Play:return s.o1U;case a.Id:return s.Xwj;case a.Code:return s.dNJ;case a.ListBullet:return s.jVc;case a.Upload:return s.rG2;case a.Clock:return s.T39;case a.Desktop:return s.ugZ;default:return}}},1453:(e,t,r)=>{"use strict";r.d(t,{AR:()=>x,C5:()=>d,KX:()=>p,Kt:()=>u,ZH:()=>m,cn:()=>n,fL:()=>c,fi:()=>l,hs:()=>h});var s=r(6815),a=r(9377);function n(...e){return(0,a.m6)((0,s.W)(e))}let o="/api",i="/api",l=async()=>{try{let e=await fetch(o+"/entities"),t=await e.json();return Object.keys(t).flatMap(e=>t[e].map(t=>({type:e,uid:`${e}_${t.id}`,...t})))}catch(e){throw console.error("Error:",e),e}},c=async()=>{try{let e=await fetch(o+"/health"),t=await e.json();return Object.keys(t).map(e=>({name:e,...t[e],capabilities:t[e].capabilities?Object.keys(t[e].capabilities).map(r=>({name:r,...t[e].capabilities[r]})):void 0,components:t[e].components?Object.keys(t[e].components).map(r=>({name:r,...t[e].components[r]})):void 0}))}catch(e){throw console.error("Error:",e),e}},d=async()=>{try{let e=await fetch(o+"/info");return await e.json()}catch(e){throw console.error("Error:",e),e}},u=async()=>{try{let e=await fetch(o+"/metrics"),t=await e.json();return{uptime:t.gauges["jvm.attribute.uptime"].value,memory:t.gauges["jvm.memory.total.used"].value}}catch(e){throw console.error("Error:",e),e}},p=async()=>{try{let e=await fetch(i+"/values");return await e.json()}catch(e){throw console.error("Error:",e),e}},m=async e=>{try{let t=e.replace(/_/g,"/"),r=await fetch(`${i}/cfg/entities/${t}`);if(!r.ok)throw Error(`HTTP error! status: ${r.status}`);return await r.json()}catch(e){throw console.error("Error:",e),e}},h=async()=>{try{let e=await fetch(i+"/cfg/global/deployment");if(!e.ok)throw Error(`HTTP error! status: ${e.status}`);return await e.json()}catch(e){throw console.error("Error:",e),e}},x=async e=>{try{let t=e.replace(/_/g,"/"),r=await fetch(`${i}/cfg/values/${t}`);if(!r.ok)throw Error(`HTTP error! status: ${r.status}`);return await r.json()}catch(e){throw console.error("Error:",e),e}}},1342:(e,t,r)=>{"use strict";r.r(t),r.d(t,{$$typeof:()=>n,__esModule:()=>a,default:()=>o});let s=(0,r(6843).createProxy)(String.raw`/Users/az/development/ws-ldproxy-4/dashboard/src/app/layout.tsx`),{__esModule:a,$$typeof:n}=s,o=s.default},1136:(e,t,r)=>{"use strict";r.r(t),r.d(t,{$$typeof:()=>n,__esModule:()=>a,default:()=>o});let s=(0,r(6843).createProxy)(String.raw`/Users/az/development/ws-ldproxy-4/dashboard/src/app/page.tsx`),{__esModule:a,$$typeof:n}=s,o=s.default},3881:(e,t,r)=>{"use strict";r.r(t),r.d(t,{default:()=>a});var s=r(337);let a=e=>[{type:"image/x-icon",sizes:"16x16",url:(0,s.fillMetadataSegment)(".",e.params,"favicon.ico")+""}]},3824:()=>{}};var t=require("../webpack-runtime.js");t.C(e);var r=e=>t(t.s=e),s=t.X(0,[638,876,337],()=>r(6938));module.exports=s})();