__CreateJSPath = function (js) {
    var scripts = document.getElementsByTagName("script");
    var path = "";
    for (var i = 0, l = scripts.length; i < l; i++) {
        var src = scripts[i].src;
        if (src.indexOf(js) != -1) {
            var ss = src.split(js);
            path = ss[0];
            break;
        }
    }
    var href = location.href;
    href = href.split("#")[0];
    href = href.split("?")[0];
    var ss = href.split("/");
    ss.length = ss.length - 1;
    href = ss.join("/");
    if (path.indexOf("https:") == -1 && path.indexOf("http:") == -1 && path.indexOf("file:") == -1 && path.indexOf("\/") != 0) {
        path = href + "/" + path;
    }
    return path;
}

var bootPATH = __CreateJSPath("boot.js");

//debugger,此变量用来区别ajax请求是否弹出alert来提示交互错误
mini_debugger = true;

//miniui
//document.write('<script src="' + bootPATH + '../lib/nui/jquery/jquery-1.6.2.min.js" type="text/javascript"></sc' + 'ript>');
//document.write('<script src="' + bootPATH + '../lib/nui/nui-min.js" type="text/javascript" ></sc' + 'ript>');
//document.write('<script src="' + bootPATH + '../lib/nui/locale/zh_CN.js" type="text/javascript" ></sc' + 'ript>');
//document.write('<link href="' + bootPATH + '../lib/nui/themes/default/miniui.css" rel="stylesheet" type="text/css" />');
//document.write('<link href="' + bootPATH + '../lib/nui/themes/default/nui-ext.css" rel="stylesheet" type="text/css" />');
//document.write('<link href="' + bootPATH + '../lib/nui/themes/icons.css" rel="stylesheet" type="text/css" />');

//skin
var skin = getCookie("miniuiSkin");
if (!skin) {
    skin = 'jqueryui-cupertino';
}
document.write('<link href="' + bootPATH + '../lib/nui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');
//document.write('<link href="http://service.chinasws.com/nui/themes/' + skin + '/skin.css" rel="stylesheet" type="text/css" />');


//会话失效，未登陆等，则跳转到登陆界面
$.ajaxSetup({
    complete: ajaxCompleteIsGoToLogin
});

function ajaxCompleteIsGoToLogin(XMLHttpRequest, textStatus) {
    var urlCallback = "";
    var index = window.location.href.indexOf("/bpm/");
    if (index >= 0) {
        urlCallback = "&urlCallback=" + encodeURIComponent(window.location.href);
    }

    var forceLogout = XMLHttpRequest.getResponseHeader("ForceLogout");
    if ("true" == forceLogout) {
        goToLogin("?errorCode=1" + urlCallback);
        return;
    }

    var userStatus = XMLHttpRequest.getResponseHeader("userStatus");
    if ("NoUserInfo" == userStatus) {
        goToLogin("?errorCode=2" + urlCallback);
        return;
    }
}

function goToLogin(params) {
    if (window.top != null) {
        if (params) {
            window.top.location.replace(bootPATH + "../../login" + params);
        } else {
            window.top.location.replace(bootPATH + "../../login");
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////
function getCookie(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}

function loading(text) {
    if (!text) {
        text = '提交中...';
    }
    mini.mask({
        el: document.body,
        cls: 'mini-mask-loading',
        html: text
    });
    //setTimeout(function () {
    //    mini.unmask(document.body);
    //}, 2000);
}

function loadComplete() {
    mini.unmask(document.body);
}

/******************************************
 * 自定义验证
 * ************************************/
//////////////////////////////////////
//


function onEnglishValidation(e) {
    if (e.isValid) {
        if (isEnglish(e.value) == false) {
            e.errorText = "必须输入英文";
            e.isValid = false;
        }
    }
}
function onEnglishAndNumberValidation(e) {
    if (e.isValid) {
        if (isEnglishAndNumber(e.value) == false) {
            e.errorText = "必须输入英文+数字";
            e.isValid = false;
        }
    }
}
function onChineseValidation(e) {
    if (e.isValid) {
        if (isChinese(e.value) == false) {
            e.errorText = "必须输入中文";
            e.isValid = false;
        }
    }
}
function onIDCardsValidation(e) {
    if (e.isValid) {
        var pattern = /\d*/;
        if (e.value.length < 15 || e.value.length > 18 || pattern.test(e.value) == false) {
            e.errorText = "必须输入15~18位数字";
            e.isValid = false;
        }
    }
}

////////////////////////////////////
/* 是否英文 */
function isEnglish(v) {
    var re = new RegExp("^[a-zA-Z\_]+$");
    if (re.test(v)) return true;
    return false;
}

/* 是否英文+数字 */
function isEnglishAndNumber(v) {

    var re = new RegExp("^[0-9a-zA-Z\_]+$");
    if (re.test(v)) return true;
    return false;
}

/* 是否汉字 */
function isChinese(v) {
    var re = new RegExp("^[\u4e00-\u9fa5]+$");
    if (re.test(v)) return true;
    return false;
}

/*自定义vtype*/
mini.VTypes["englishErrorText"] = "请输入英文";
mini.VTypes["english"] = function (v) {
    var re = new RegExp("^[a-zA-Z\_]+$");
    if (re.test(v)) return true;
    return false;
}

function getCurrentYear() {
    var d = new Date();
    var currentYear = d.getFullYear().toString();
    return currentYear;
}

//标识一个全局唯一标识
function Guid(g) {
    var arr = new Array();//存放32为数值的数组

    //如果构造函数的参数为字符串
    if (typeof(g) == "string") {
        initByString(arr, g);
    }
    else {
        initOther(arr);
    }

    //返回一个值，该值标识Guid的两个实例是否表示同一个值
    this.Equals = function (o) {
        if (o && o.isGuid) {
            return this.ToString() == o.ToString();
        }
        else {
            return false;
        }
    }
    //Guid对象标记
    this.isGuid = function () {
    }

    //返回Guid类的此实例值的String表示形式
    this.ToString = function (format) {
        if (typeof (format) == "string") {
            if (format == "N" || format == "D" || format == "B" || format == "P") {
                return ToStringWithFormat(arr, format);
            }
            else {
                return ToStringWithFormat(arr, "D");
            }
        } else {
            return ToStringWithFormat(arr, "D");
        }
    }

}

//由字符串加载
function initByString(arr, g) {
    g = g.replace(/\{|\(|\)|\}|-/g, "");
    g = g.toLowerCase();
    if (g.length != 32 || g.search(/[^0-9,a-f]/i) != -1) {
        initOther(arr);
    }
    else {
        for (var i = 0; i < g.length; i++) {
            arr.push(g[i]);
        }
    }
}

//其他类型加载
function initOther(arr) {
    var i = 32;
    while (i--) {
        arr.push("0");
    }
}


function ToStringWithFormat(arr, format) {
    switch (format) {
        case "N":
            return arr.toString().replace(/,/g, "");
        case "D":
            var str = arr.slice(0, 8) + "-" + arr.slice(8, 12) + "-" +
                arr.slice(12, 16) + "-" + arr.slice(16, 20) + "-" + arr.slice(20, 32);
            str = str.replace(/,/g, "");
            return str;
        case "B":
            var str = ToStringWithFormat(arr, "D");
            str = "{" + str + "}";
            return str;
        case "P":
            var str = ToStringWithFormat(arr, "D");
            str = "(" + str + ")";
            return str;
        default :
            return new Guid();
    }
}

Guid.Empty = new Guid();
Guid.NewGuid = function () {
    var g = "";
    var i = 32;
    while (i--) {
        g += Math.floor(Math.random() * 16.0).toString(16);
    }
    return new Guid(g);
}

function formatFloat(src, pos) {
    if (isNaN(src))
        return src;
    return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
}

//Created by gln on 2016/12/16.
function ajax_download(url, queryData, quertName,resultData,resultName) {
    var $iframe,
        iframe_doc,
        iframe_html;
    if (($iframe = $('#download_iframe')).length === 0) {
        $iframe = $("<iframe id='download_iframe'" +
            " style='display: none' src='about:blank'></iframe>"
        ).appendTo("body");
    }
    iframe_doc = $iframe[0].contentWindow || $iframe[0].contentDocument;
    if (iframe_doc.document) {
        iframe_doc = iframe_doc.document;
    }
    iframe_html = "<html><head></head><body><form id='myForm' method='POST' action='" +
        url + "'>" +
        "<input type=hidden name='" + quertName + "' value='" +JSON.stringify(queryData) + "'/>" +
        "<input type=hidden name='" + resultName + "' value='" +JSON.stringify(resultData) + "'/>" +
        "</form></body></html>";
    iframe_doc.open();
    iframe_doc.write(iframe_html);
    $(iframe_doc).find('#myForm').submit();
    var download_iframe = document.getElementById("download_iframe");
    if (download_iframe.attachEvent) {// 仅限IE下弹出遮罩
        var messageid = mini.loading("正在下载, 请稍候...", "文件下载");
        download_iframe.onload = download_iframe.onreadystatechange = function (){
           // console.log(this.readyState);
            if (this.readyState == 'interactive'  || this.readyState == 'complete') {
                mini.hideMessageBox(messageid);
            }
        }
    }
}

