(function(h){var a=window.AmazonUIPageJS||window.P,m=a._namespace||a.attributeErrors,n=m?m("AmazonStoresCnubaAssets",""):a;n.guardFatal?n.guardFatal(h)(n,window):n.execute(function(){h(n,window)})})(function(h,a,m){a.P&&a.P.AUI_BUILD_DATE&&h.when("A","jQuery").register("cnuba-lazy-load",function(a,f){var d=[],e=void 0,b=void 0,g=void 0,c=void 0,k=function(){0<d.length?h():(g.remove(),uet("x3"))},p=function(a){var c=e;if("/pages"===b||"/proxy/pages"===b)c.slotId=a;return c},h=function(){var e=d[0],
g;g="/pages"===b?"/pages/nextslot":"/proxy/pages"===b?"/proxy/pages/nextslot":"/stores/slot/"+e;if(g===m||null===g||""!==g)e=p(e),a.ajax(g,{method:"post"===c||"get"===c?c:"post",dataType:"html",params:e,success:function(e){var a=e;e=d.shift();var c=/[\u2028\u2029]/gi;a.match(c)&&(a=a.replace(c,""));try{f("#"+e).append(a)}catch(g){"function"===typeof ueLogError&&ueLogError(g,{message:"Unable to append widget response into page on lazyload",attribution:"AmazonStoresCnubaAssets:lazyload:insertContent",
logLevel:"ERROR"})}f("#"+e).css("opacity",1);k()},error:function(e,a,c){d.shift();k()}})};return{triggerLazyLoad:function(a,p,l,h){0<a.length&&(d=a,e=p,b=l,c=h,g=f(".stores-loading-indicator"),k())}}});"use strict";a.P&&a.P.AUI_BUILD_DATE&&h.when("A","jQuery","ready").register("engagement-event-logger",function(a,f){var d=function(e,b,g,c,k,d){e={widgetId:e,widgetType:b,elementLocator:g,eventType:c.toUpperCase(),eventData:k||{},eventCategory:d||null};if(b=a.state("visitInfo"))e=f.extend(e,b),e.schemaId=
"stores.StoreEngagementEvent.4",e.producerId="stores",b={n:1},ue&&ue.event&&ue.event(e,e.producerId,e.schemaId,b)};return{clickEvent:function(a,b,g,c,k){d(a,b,g,"CLICK",c,k)},renderEvent:function(a,b,g,c){g=g||{};g.eventSubType="RENDER";d(a,b,null,"IMPRESSION",g,c)},simpleEvent:function(a,b,g){d(null,null,null,a,b,g)},visibilityChangeEvent:function(a){d(null,null,null,"VISIBILITY_CHANGE",a,"PAGE")},event:d}});"use strict";a.P&&a.P.AUI_BUILD_DATE&&h.when("jQuery","engagement-event-logger","ready").execute(function(l,
f){l(".stores-page").click(function(a){var b=l(a.target).closest(".stores-column");a=b.data("widgetid");b=b.data("widgettype");f.clickEvent(a,b,null,{},"PAGE")});document.addEventListener("visibilitychange",function(){f.visibilityChangeEvent({visibilityState:document.visibilityState})});var d={scrollend:"SCROLL"};l(a).bind("scrollend focus blur beforeunload unload pageshow pagehide",function(a){(a=a.type&&(d[a.type]||a.type))&&f.simpleEvent(a,{},"PAGE")})});"use strict";a.P&&a.P.AUI_BUILD_DATE&&h.when("jQuery",
"ready").execute("scroll-end-event",function(l){var f=null;l(a).scroll(function(d){a.clearTimeout(f);f=setTimeout(function(){l(a).trigger("scrollend")},300)})});"use strict";var n=function(){return function(a,f){if(Array.isArray(a))return a;if(Symbol.iterator in Object(a)){var d=[],e=!0,b=!1,g=m;try{for(var c=a[Symbol.iterator](),k;!(e=(k=c.next()).done)&&(d.push(k.value),!f||d.length!==f);e=!0);}catch(p){b=!0,g=p}finally{try{if(!e&&c["return"])c["return"]()}finally{if(b)throw g;}}return d}throw new TypeError("Invalid attempt to destructure non-iterable instance");
}}();"scrollRestoration"in a.history&&(a.history.scrollRestoration="manual");a.performance&&a.performance.navigation&&2===a.performance.navigation.type&&a.P&&a.P.AUI_BUILD_DATE&&h.when("A","StoresUiToolkit","ready").execute(function(h,f){var d=function(a){if(a=sessionStorage.getItem("scrollPosition-"+a)){a=a.split(",");var c=n(a,2);a=c[0];c=c[1];return{scrollX:a===m?0:a,scrollY:c===m?0:c}}return null};a.addEventListener("beforeunload",function(){var e=a.location.pathname+a.location.search,c=a.scrollX,
b=a.scrollY,d=f&&f.BodyScroll?f.BodyScroll.getInstance():null;d&&d.isDisabled()&&(b=d.scrollPosition);c=[c,b].join();sessionStorage.setItem("scrollPosition-"+e,c)});var e=null,b=function c(b){clearTimeout(e);var d=document.body,f=document.documentElement,h=Math.max(d.scrollWidth,d.offsetWidth,f.clientWidth,f.scrollWidth,f.offsetWidth),d=Math.max(d.scrollHeight,d.offsetHeight,f.clientHeight,f.scrollHeight,f.offsetHeight);h-a.innerWidth>=b.x&&d-a.innerHeight>=b.y||Date.now()>b.latestTimeToTry?parseInt(b.y)<
d&&parseInt(b.x)<h&&a.scrollTo(b.x,b.y):e=setTimeout(function(){return c(b)},50)};(function(){var c=d(a.location.pathname+a.location.search);c&&c.scrollX&&c.scrollY&&setTimeout(function(){return b({x:c.scrollX,y:c.scrollY,latestTimeToTry:Date.now()+3E3})})})()});"use strict";a.P&&a.P.AUI_BUILD_DATE&&h.when("A").register("interaction-event",function(h){h.on("packard:glow:destinationChangeNavAck",function(){a.location.reload()})})});