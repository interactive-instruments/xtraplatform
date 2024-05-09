"use strict";(self.webpackChunk_N_E=self.webpackChunk_N_E||[]).push([[639],{1639:function(e,t,n){n.d(t,{VY:function(){return X},aV:function(){return Q},fC:function(){return J},xz:function(){return W}});var r=n(2110),o=n(4090),a=n.t(o,2);function u(e,t){let{checkForDefaultPrevented:n=!0}=arguments.length>2&&void 0!==arguments[2]?arguments[2]:{};return function(r){if(null==e||e(r),!1===n||!r.defaultPrevented)return null==t?void 0:t(r)}}function l(e){let t=arguments.length>1&&void 0!==arguments[1]?arguments[1]:[],n=[],r=()=>{let t=n.map(e=>(0,o.createContext)(e));return function(n){let r=(null==n?void 0:n[e])||t;return(0,o.useMemo)(()=>({["__scope".concat(e)]:{...n,[e]:r}}),[n,r])}};return r.scopeName=e,[function(t,r){let a=(0,o.createContext)(r),u=n.length;function l(t){let{scope:n,children:r,...l}=t,i=(null==n?void 0:n[e][u])||a,c=(0,o.useMemo)(()=>l,Object.values(l));return(0,o.createElement)(i.Provider,{value:c},r)}return n=[...n,r],l.displayName=t+"Provider",[l,function(n,l){let i=(null==l?void 0:l[e][u])||a,c=(0,o.useContext)(i);if(c)return c;if(void 0!==r)return r;throw Error("`".concat(n,"` must be used within `").concat(t,"`"))}]},function(){for(var e=arguments.length,t=Array(e),n=0;n<e;n++)t[n]=arguments[n];let r=t[0];if(1===t.length)return r;let a=()=>{let e=t.map(e=>({useScope:e(),scopeName:e.scopeName}));return function(t){let n=e.reduce((e,n)=>{let{useScope:r,scopeName:o}=n,a=r(t)["__scope".concat(o)];return{...e,...a}},{});return(0,o.useMemo)(()=>({["__scope".concat(r.scopeName)]:n}),[n])}};return a.scopeName=r.scopeName,a}(r,...t)]}var i=n(1266),c=n(9143);let s=(null==globalThis?void 0:globalThis.document)?o.useLayoutEffect:()=>{},f=a["useId".toString()]||(()=>void 0),d=0;function m(e){let[t,n]=o.useState(f());return s(()=>{e||n(e=>null!=e?e:String(d++))},[e]),e||(t?"radix-".concat(t):"")}var p=n(9542);let v=["a","button","div","form","h2","h3","img","input","label","li","nav","ol","p","span","svg","ul"].reduce((e,t)=>{let n=(0,o.forwardRef)((e,n)=>{let{asChild:a,...u}=e,l=a?c.g7:t;return(0,o.useEffect)(()=>{window[Symbol.for("radix-ui")]=!0},[]),(0,o.createElement)(l,(0,r.Z)({},u,{ref:n}))});return n.displayName="Primitive.".concat(t),{...e,[t]:n}},{});function b(e){let t=(0,o.useRef)(e);return(0,o.useEffect)(()=>{t.current=e}),(0,o.useMemo)(()=>function(){for(var e,n=arguments.length,r=Array(n),o=0;o<n;o++)r[o]=arguments[o];return null===(e=t.current)||void 0===e?void 0:e.call(t,...r)},[])}function g(e){let{prop:t,defaultProp:n,onChange:r=()=>{}}=e,[a,u]=function(e){let{defaultProp:t,onChange:n}=e,r=(0,o.useState)(t),[a]=r,u=(0,o.useRef)(a),l=b(n);return(0,o.useEffect)(()=>{u.current!==a&&(l(a),u.current=a)},[a,u,l]),r}({defaultProp:n,onChange:r}),l=void 0!==t,i=l?t:a,c=b(r);return[i,(0,o.useCallback)(e=>{if(l){let n="function"==typeof e?e(t):e;n!==t&&c(n)}else u(e)},[l,t,u,c])]}let E=(0,o.createContext)(void 0);function h(e){let t=(0,o.useContext)(E);return e||t||"ltr"}let w="rovingFocusGroup.onEntryFocus",C={bubbles:!1,cancelable:!0},R="RovingFocusGroup",[I,T,N]=function(e){let t=e+"CollectionProvider",[n,r]=l(t),[a,u]=n(t,{collectionRef:{current:null},itemMap:new Map}),s=e+"CollectionSlot",f=o.forwardRef((e,t)=>{let{scope:n,children:r}=e,a=u(s,n),l=(0,i.e)(t,a.collectionRef);return o.createElement(c.g7,{ref:l},r)}),d=e+"CollectionItemSlot",m="data-radix-collection-item";return[{Provider:e=>{let{scope:t,children:n}=e,r=o.useRef(null),u=o.useRef(new Map).current;return o.createElement(a,{scope:t,itemMap:u,collectionRef:r},n)},Slot:f,ItemSlot:o.forwardRef((e,t)=>{let{scope:n,children:r,...a}=e,l=o.useRef(null),s=(0,i.e)(t,l),f=u(d,n);return o.useEffect(()=>(f.itemMap.set(l,{ref:l,...a}),()=>void f.itemMap.delete(l))),o.createElement(c.g7,{[m]:"",ref:s},r)})},function(t){let n=u(e+"CollectionConsumer",t);return o.useCallback(()=>{let e=n.collectionRef.current;if(!e)return[];let t=Array.from(e.querySelectorAll("[".concat(m,"]")));return Array.from(n.itemMap.values()).sort((e,n)=>t.indexOf(e.ref.current)-t.indexOf(n.ref.current))},[n.collectionRef,n.itemMap])},r]}(R),[y,M]=l(R,[N]),[A,_]=y(R),S=(0,o.forwardRef)((e,t)=>(0,o.createElement)(I.Provider,{scope:e.__scopeRovingFocusGroup},(0,o.createElement)(I.Slot,{scope:e.__scopeRovingFocusGroup},(0,o.createElement)(F,(0,r.Z)({},e,{ref:t}))))),F=(0,o.forwardRef)((e,t)=>{let{__scopeRovingFocusGroup:n,orientation:a,loop:l=!1,dir:c,currentTabStopId:s,defaultCurrentTabStopId:f,onCurrentTabStopIdChange:d,onEntryFocus:m,...p}=e,E=(0,o.useRef)(null),R=(0,i.e)(t,E),I=h(c),[N=null,y]=g({prop:s,defaultProp:f,onChange:d}),[M,_]=(0,o.useState)(!1),S=b(m),F=T(n),D=(0,o.useRef)(!1),[P,O]=(0,o.useState)(0);return(0,o.useEffect)(()=>{let e=E.current;if(e)return e.addEventListener(w,S),()=>e.removeEventListener(w,S)},[S]),(0,o.createElement)(A,{scope:n,orientation:a,dir:I,loop:l,currentTabStopId:N,onItemFocus:(0,o.useCallback)(e=>y(e),[y]),onItemShiftTab:(0,o.useCallback)(()=>_(!0),[]),onFocusableItemAdd:(0,o.useCallback)(()=>O(e=>e+1),[]),onFocusableItemRemove:(0,o.useCallback)(()=>O(e=>e-1),[])},(0,o.createElement)(v.div,(0,r.Z)({tabIndex:M||0===P?-1:0,"data-orientation":a},p,{ref:R,style:{outline:"none",...e.style},onMouseDown:u(e.onMouseDown,()=>{D.current=!0}),onFocus:u(e.onFocus,e=>{let t=!D.current;if(e.target===e.currentTarget&&t&&!M){let t=new CustomEvent(w,C);if(e.currentTarget.dispatchEvent(t),!t.defaultPrevented){let e=F().filter(e=>e.focusable);x([e.find(e=>e.active),e.find(e=>e.id===N),...e].filter(Boolean).map(e=>e.ref.current))}}D.current=!1}),onBlur:u(e.onBlur,()=>_(!1))})))}),D=(0,o.forwardRef)((e,t)=>{let{__scopeRovingFocusGroup:n,focusable:a=!0,active:l=!1,tabStopId:i,...c}=e,s=m(),f=i||s,d=_("RovingFocusGroupItem",n),p=d.currentTabStopId===f,b=T(n),{onFocusableItemAdd:g,onFocusableItemRemove:E}=d;return(0,o.useEffect)(()=>{if(a)return g(),()=>E()},[a,g,E]),(0,o.createElement)(I.ItemSlot,{scope:n,id:f,focusable:a,active:l},(0,o.createElement)(v.span,(0,r.Z)({tabIndex:p?0:-1,"data-orientation":d.orientation},c,{ref:t,onMouseDown:u(e.onMouseDown,e=>{a?d.onItemFocus(f):e.preventDefault()}),onFocus:u(e.onFocus,()=>d.onItemFocus(f)),onKeyDown:u(e.onKeyDown,e=>{if("Tab"===e.key&&e.shiftKey){d.onItemShiftTab();return}if(e.target!==e.currentTarget)return;let t=function(e,t,n){var r;let o=(r=e.key,"rtl"!==n?r:"ArrowLeft"===r?"ArrowRight":"ArrowRight"===r?"ArrowLeft":r);if(!("vertical"===t&&["ArrowLeft","ArrowRight"].includes(o))&&!("horizontal"===t&&["ArrowUp","ArrowDown"].includes(o)))return P[o]}(e,d.orientation,d.dir);if(void 0!==t){e.preventDefault();let o=b().filter(e=>e.focusable).map(e=>e.ref.current);if("last"===t)o.reverse();else if("prev"===t||"next"===t){var n,r;"prev"===t&&o.reverse();let a=o.indexOf(e.currentTarget);o=d.loop?(n=o,r=a+1,n.map((e,t)=>n[(r+t)%n.length])):o.slice(a+1)}setTimeout(()=>x(o))}})})))}),P={ArrowLeft:"prev",ArrowUp:"prev",ArrowRight:"next",ArrowDown:"next",PageUp:"first",Home:"first",PageDown:"last",End:"last"};function x(e){let t=document.activeElement;for(let n of e)if(n===t||(n.focus(),document.activeElement!==t))return}let O=e=>{let{present:t,children:n}=e,r=function(e){var t,n;let[r,a]=(0,o.useState)(),u=(0,o.useRef)({}),l=(0,o.useRef)(e),i=(0,o.useRef)("none"),[c,f]=(t=e?"mounted":"unmounted",n={mounted:{UNMOUNT:"unmounted",ANIMATION_OUT:"unmountSuspended"},unmountSuspended:{MOUNT:"mounted",ANIMATION_END:"unmounted"},unmounted:{MOUNT:"mounted"}},(0,o.useReducer)((e,t)=>{let r=n[e][t];return null!=r?r:e},t));return(0,o.useEffect)(()=>{let e=k(u.current);i.current="mounted"===c?e:"none"},[c]),s(()=>{let t=u.current,n=l.current;if(n!==e){let r=i.current,o=k(t);e?f("MOUNT"):"none"===o||(null==t?void 0:t.display)==="none"?f("UNMOUNT"):n&&r!==o?f("ANIMATION_OUT"):f("UNMOUNT"),l.current=e}},[e,f]),s(()=>{if(r){let e=e=>{let t=k(u.current).includes(e.animationName);e.target===r&&t&&(0,p.flushSync)(()=>f("ANIMATION_END"))},t=e=>{e.target===r&&(i.current=k(u.current))};return r.addEventListener("animationstart",t),r.addEventListener("animationcancel",e),r.addEventListener("animationend",e),()=>{r.removeEventListener("animationstart",t),r.removeEventListener("animationcancel",e),r.removeEventListener("animationend",e)}}f("ANIMATION_END")},[r,f]),{isPresent:["mounted","unmountSuspended"].includes(c),ref:(0,o.useCallback)(e=>{e&&(u.current=getComputedStyle(e)),a(e)},[])}}(t),a="function"==typeof n?n({present:r.isPresent}):o.Children.only(n),u=(0,i.e)(r.ref,a.ref);return"function"==typeof n||r.isPresent?(0,o.cloneElement)(a,{ref:u}):null};function k(e){return(null==e?void 0:e.animationName)||"none"}O.displayName="Presence";let L="Tabs",[U,Z]=l(L,[M]),V=M(),[G,K]=U(L),z=(0,o.forwardRef)((e,t)=>{let{__scopeTabs:n,value:a,onValueChange:u,defaultValue:l,orientation:i="horizontal",dir:c,activationMode:s="automatic",...f}=e,d=h(c),[p,b]=g({prop:a,onChange:u,defaultProp:l});return(0,o.createElement)(G,{scope:n,baseId:m(),value:p,onValueChange:b,orientation:i,dir:d,activationMode:s},(0,o.createElement)(v.div,(0,r.Z)({dir:d,"data-orientation":i},f,{ref:t})))}),B=(0,o.forwardRef)((e,t)=>{let{__scopeTabs:n,loop:a=!0,...u}=e,l=K("TabsList",n),i=V(n);return(0,o.createElement)(S,(0,r.Z)({asChild:!0},i,{orientation:l.orientation,dir:l.dir,loop:a}),(0,o.createElement)(v.div,(0,r.Z)({role:"tablist","aria-orientation":l.orientation},u,{ref:t})))}),q=(0,o.forwardRef)((e,t)=>{let{__scopeTabs:n,value:a,disabled:l=!1,...i}=e,c=K("TabsTrigger",n),s=V(n),f=H(c.baseId,a),d=Y(c.baseId,a),m=a===c.value;return(0,o.createElement)(D,(0,r.Z)({asChild:!0},s,{focusable:!l,active:m}),(0,o.createElement)(v.button,(0,r.Z)({type:"button",role:"tab","aria-selected":m,"aria-controls":d,"data-state":m?"active":"inactive","data-disabled":l?"":void 0,disabled:l,id:f},i,{ref:t,onMouseDown:u(e.onMouseDown,e=>{l||0!==e.button||!1!==e.ctrlKey?e.preventDefault():c.onValueChange(a)}),onKeyDown:u(e.onKeyDown,e=>{[" ","Enter"].includes(e.key)&&c.onValueChange(a)}),onFocus:u(e.onFocus,()=>{let e="manual"!==c.activationMode;m||l||!e||c.onValueChange(a)})})))}),j=(0,o.forwardRef)((e,t)=>{let{__scopeTabs:n,value:a,forceMount:u,children:l,...i}=e,c=K("TabsContent",n),s=H(c.baseId,a),f=Y(c.baseId,a),d=a===c.value,m=(0,o.useRef)(d);return(0,o.useEffect)(()=>{let e=requestAnimationFrame(()=>m.current=!1);return()=>cancelAnimationFrame(e)},[]),(0,o.createElement)(O,{present:u||d},n=>{let{present:a}=n;return(0,o.createElement)(v.div,(0,r.Z)({"data-state":d?"active":"inactive","data-orientation":c.orientation,role:"tabpanel","aria-labelledby":s,hidden:!a,id:f,tabIndex:0},i,{ref:t,style:{...e.style,animationDuration:m.current?"0s":void 0}}),a&&l)})});function H(e,t){return"".concat(e,"-trigger-").concat(t)}function Y(e,t){return"".concat(e,"-content-").concat(t)}let J=z,Q=B,W=q,X=j}}]);