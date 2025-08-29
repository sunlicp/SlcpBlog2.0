function fetchCommits() {
    fetch("/json/commits.json").then((e=>e.json().then((e=>{
            for (var t = e, i = 0; i < t.length; i++) {
                t[i];
                var n = i + 1 + "." + t[i].commit.message;
                if (i < 1)
                    var o = n;
                else
                    o = o + "<br>" + n
            }
            SAONotify("Latest Update", o, "location.reload(true);")
        }
    )))).catch(console.error)
}
var t1 = 0
    , t2 = 0
    , timer = null;
function isScrollEnd() {
    (t2 = document.documentElement.scrollTop || document.body.scrollTop) == t1 && document.styleSheets[0].addRule("*::-webkit-scrollbar-thumb", "display:none;")
}
document.styleSheets[0].addRule("*::-webkit-scrollbar-thumb", "display:none;"),
    document.onscroll = function() {
        clearTimeout(timer),
            timer = setTimeout(isScrollEnd, 1e3),
            t1 = document.documentElement.scrollTop || document.body.scrollTop,
            document.styleSheets[0].addRule("*::-webkit-scrollbar-thumb", "display:block;")
    }
;
var titleTime, OriginTitile = document.title;
function ReDirect() {
    var e = window.location.pathname.split("/")[2].split(".");
    "html" === e[1] ? window.location.href = "/posts/" + e[0] + "/" : SAONotify("404", "非常抱歉，文章可能已经被删除了...", "window.location.href='/'")
}
function PublicSacrificeDay() {
    var e = new Array("0403","0404","0405","0508","0512","0707","0814","0909","0918","0930","1025","1213")
        , t = new Date
        , i = ""
        , n = t.getMonth() + 1;
    return t.getMonth() > 9 ? i += n : i += "0" + n,
        t.getDate() > 9 ? i += t.getDate() : i += "0" + t.getDate(),
        e.indexOf(i) > -1 ? 1 : 0
}
function searchSize() {
    if (document.body.clientWidth > 768)
        return;
    let e = document.querySelector("#algolia-hits");
    e.addEventListener("DOMNodeInserted", (()=>{
            e.children[0].style.maxHeight = document.documentElement.clientHeight - 210 + "px"
        }
    ))
}
PublicSacrificeDay() && document.getElementsByTagName("html")[0].setAttribute("style", "filter:gray !important;filter:grayscale(100%);-webkit-filter:grayscale(100%);-moz-filter:grayscale(100%);-ms-filter:grayscale(100%);-o-filter:grayscale(100%);"),
    searchSize(),
    window.addEventListener("resize", searchSize),
    document.addEventListener("scroll", (function() {
            var e = window.scrollY + document.documentElement.clientHeight
                , t = window.scrollY
                , i = document.getElementById("pagination")
                , n = document.getElementsByClassName("post-copyright")[0];
            if (n && i) {
                var o = n.offsetTop + n.offsetHeight / 2;
                o > t && o < e ? i.classList.add("pagination-active") : i.classList.remove("pagination-active")
            }
        }
    ));
