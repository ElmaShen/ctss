function arrayToDroplist(AD) {
    var target = AD.label_array;
    var labelAttribute = AD.labelAttribute;
    var valueAttribute = AD.valueAttribute;
    if (_.isUndefined(target._label)) {
        var arr = [];
        var obj;
        var target = $.itri_district.json;
        for (var i = 0; i < target.length; i++) {
            obj = {
                label: target[i][labelAttribute],
                value: target[i][valueAttribute]
            };
            arr.push(obj);
        };
        target._label = arr;
    }
    return target._label;
}

function pad(n, width, z) {
    z = z || '0';
    n = n + '';
    return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}


function ArrayToMap(array, idAttribute) {
    var map = {};
    for (var i = 0; i < array.length; i++) {
        map[array[i][idAttribute]] = array;
    };
}

function getBBModel(AD) {
    return Backbone.Model.extend({
        urlRoot: AD.url,
        idAttribute: AD.idAttribute
    });
}

function getBBCollection(AD) {
    return Backbone.Collection.extend({
        url: AD.url,
        model: getBBModel(AD.url, AD.idAttribute)
    });
}

function prototype(o) {
    var f = function() {};
    f.prototype = o;
    return new f();
}

function getTplBin(target) {
    if (_.isUndefined(target["_tpl"]))
        target["_tpl"] = Handlebars.compile(target["tpl"]);
    return target["_tpl"];
}

function SHA1(msg) {

    function rotate_left(n, s) {
        var t4 = (n << s) | (n >>> (32 - s));
        return t4;
    };

    function lsb_hex(val) {
        var str = "";
        var i;
        var vh;
        var vl;

        for (i = 0; i <= 6; i += 2) {
            vh = (val >>> (i * 4 + 4)) & 0x0f;
            vl = (val >>> (i * 4)) & 0x0f;
            str += vh.toString(16) + vl.toString(16);
        }
        return str;
    };

    function cvt_hex(val) {
        var str = "";
        var i;
        var v;

        for (i = 7; i >= 0; i--) {
            v = (val >>> (i * 4)) & 0x0f;
            str += v.toString(16);
        }
        return str;
    };


    function Utf8Encode(string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";

        for (var n = 0; n < string.length; n++) {

            var c = string.charCodeAt(n);

            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }

        return utftext;
    };

    var blockstart;
    var i, j;
    var W = new Array(80);
    var H0 = 0x67452301;
    var H1 = 0xEFCDAB89;
    var H2 = 0x98BADCFE;
    var H3 = 0x10325476;
    var H4 = 0xC3D2E1F0;
    var A, B, C, D, E;
    var temp;

    msg = Utf8Encode(msg);

    var msg_len = msg.length;

    var word_array = new Array();
    for (i = 0; i < msg_len - 3; i += 4) {
        j = msg.charCodeAt(i) << 24 | msg.charCodeAt(i + 1) << 16 |
            msg.charCodeAt(i + 2) << 8 | msg.charCodeAt(i + 3);
        word_array.push(j);
    }

    switch (msg_len % 4) {
        case 0:
            i = 0x080000000;
            break;
        case 1:
            i = msg.charCodeAt(msg_len - 1) << 24 | 0x0800000;
            break;

        case 2:
            i = msg.charCodeAt(msg_len - 2) << 24 | msg.charCodeAt(msg_len - 1) << 16 | 0x08000;
            break;

        case 3:
            i = msg.charCodeAt(msg_len - 3) << 24 | msg.charCodeAt(msg_len - 2) << 16 | msg.charCodeAt(msg_len - 1) << 8 | 0x80;
            break;
    }

    word_array.push(i);

    while ((word_array.length % 16) != 14) word_array.push(0);

    word_array.push(msg_len >>> 29);
    word_array.push((msg_len << 3) & 0x0ffffffff);


    for (blockstart = 0; blockstart < word_array.length; blockstart += 16) {

        for (i = 0; i < 16; i++) W[i] = word_array[blockstart + i];
        for (i = 16; i <= 79; i++) W[i] = rotate_left(W[i - 3] ^ W[i - 8] ^ W[i - 14] ^ W[i - 16], 1);

        A = H0;
        B = H1;
        C = H2;
        D = H3;
        E = H4;

        for (i = 0; i <= 19; i++) {
            temp = (rotate_left(A, 5) + ((B & C) | (~B & D)) + E + W[i] + 0x5A827999) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B, 30);
            B = A;
            A = temp;
        }

        for (i = 20; i <= 39; i++) {
            temp = (rotate_left(A, 5) + (B ^ C ^ D) + E + W[i] + 0x6ED9EBA1) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B, 30);
            B = A;
            A = temp;
        }

        for (i = 40; i <= 59; i++) {
            temp = (rotate_left(A, 5) + ((B & C) | (B & D) | (C & D)) + E + W[i] + 0x8F1BBCDC) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B, 30);
            B = A;
            A = temp;
        }

        for (i = 60; i <= 79; i++) {
            temp = (rotate_left(A, 5) + (B ^ C ^ D) + E + W[i] + 0xCA62C1D6) & 0x0ffffffff;
            E = D;
            D = C;
            C = rotate_left(B, 30);
            B = A;
            A = temp;
        }

        H0 = (H0 + A) & 0x0ffffffff;
        H1 = (H1 + B) & 0x0ffffffff;
        H2 = (H2 + C) & 0x0ffffffff;
        H3 = (H3 + D) & 0x0ffffffff;
        H4 = (H4 + E) & 0x0ffffffff;

    }

    var temp = cvt_hex(H0) + cvt_hex(H1) + cvt_hex(H2) + cvt_hex(H3) + cvt_hex(H4);

    return temp.toLowerCase();

}

var defaultErrorHandler = function(model, xhr, options) {
    alert("網路連線有問題，請稍後再試。");
};

var total_page = [
    'accounts_balance.aspx',
    'accounts_check_by_superior.aspx',
    'accounts_customer.aspx',
    'accounts_debatable.aspx',
    'accounts_detail.aspx',
    'accounts_fee.aspx',
    'accounts_money_keep.aspx',
    'batch_query.aspx',
    'customer.aspx',
    'customer_modify.aspx',
    'customer_query.aspx',
    'file_upload.aspx',
    'login.aspx',
    'rights_checked_by_superior.aspx',
    'rights_check_by_superior.aspx',
    'rights_group.aspx',
    'rights_notification.aspx',
    'rights_parameter.aspx',
    'rights_user.aspx',
    'risk.aspx',
    'risk_add.aspx',
    'risk_blacklist.aspx',
    'risk_check_by_superior.aspx',
    'risk_customer.aspx',
    'risk_day.aspx',
    'risk_IP.aspx',
    'risk_like.aspx',
    'risk_query.aspx',
    'risk_warning.aspx',
    'scheduling.aspx',
    'service_add.aspx',
    'service_check_by_superior.aspx',
    'service_count.aspx',
    'service_list.aspx',
    'service_query.aspx',
    'statistic_account.aspx',
    'statistic_agent.aspx',
    'statistic_argue.aspx',
    'statistic_ATM.aspx',
    'statistic_IP.aspx',
    'statistic_msg.aspx',
    'statistic_refunds.aspx',
    'systematic.aspx',
    'systematic_deal.aspx',
    'systematic_internal_operate.aspx',
    'systematic_operate.aspx',
    'systematic_web.aspx',
    'todo.aspx',
    'transaction_all.aspx',
    'transaction_customer.aspx',
    'transaction_query.aspx',
    'web_ads.aspx',
    'web_brand.aspx',
    'web_check_by_superior.aspx',
    'web_contract.aspx',
    'web_faq.aspx',
    'web_info.aspx',
    'web_promotion.aspx'
]

function MyEncodeURI(URI) {

    for (var url in total_page) {
        if (URI.indexOf(total_page[url]) > -1) {
            return encodeURI(URI);
        }
    }
    return "index.aspx";
    // return URI;
}

//modified by tom 20150608
//修正同時有兩個XHR同時進行會提早讓loading消失的bug
$(document).ajaxComplete(function(event, request, settings) {
    if ($('body').attr('data-count') == 1) {
        $('body').attr('data-count', '0');
        $('body').removeClass('loading');
        if($.reset_countdown)
            $.reset_countdown();
    } else {
        var count = parseInt($('body').attr('data-count')) - 1;
        $('body').attr('data-count', count);
    }
}).ajaxSend(function() {
    if ($('body').hasClass('loading')) {
        var count = 1 + parseInt($('body').attr('data-count'));
        $('body').attr('data-count', count);
    } else {
        $('body').addClass('loading');
        $('body').attr('data-count', '1');
    }
})

isBuyer = function(type) {
    try {
        return parseInt(type) <= 3;
    } catch (e) {
        return false;
    }
}
isSeller = function(type) {
    try {
        return parseInt(type) >= 4;
    } catch (e) {
        return false;
    }
}

var reg = /^([a-zA-Z]+\d+|\d+[a-zA-Z]+)[a-zA-Z0-9]*$/;
var regexPhone = /^[0][9][0-9]{2,10}$/;
var regexEmail = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
var regexID = /^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$/;
var regexcorp_id = /^[0-9]{8}$/;
var regInt = /^\d+$/;

function msieversion() {

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./)) // If Internet Explorer, return version number
        return (parseInt(ua.substring(msie + 5, ua.indexOf(".", msie))));
    else // If another browser, return 0
        return 0;
}

function replaceNonNumber(event) {

    if ($(this).val().length == 0) return;
    if (!regInt.test($(this).val())) {
        $(this).val($(this).val().replace(/\D/g, ""));
    }
}
//**************************************
// 台灣身分證檢查簡短版 for Javascript
//**************************************
function checkPersonID(id) {
    //建立字母分數陣列(A~Z)
    var city = new Array(
        1, 10, 19, 28, 37, 46, 55, 64, 39, 73, 82, 2, 11,
        20, 48, 29, 38, 47, 56, 65, 74, 83, 21, 3, 12, 30
    )
    id = id.toUpperCase();
    // 使用「正規表達式」檢驗格式
    if (id.search(/^[A-Z](1|2)\d{8}$/i) == -1) {
        alert('基本格式錯誤');
        return false;
    } else {
        //將字串分割為陣列(IE必需這麼做才不會出錯)
        id = id.split('');
        //計算總分
        var total = city[id[0].charCodeAt(0) - 65];
        for (var i = 1; i <= 8; i++) {
            total += parseInt(id[i]) * (9 - i);
        }
        //補上檢查碼(最後一碼)
        total += parseInt(id[9]);
        //檢查比對碼(餘數應為0);
        return ((total % 10 == 0));
    }
}

function checkLoginID(login_id) {

    if (!login_id || login_id.length == 0)
        return "不得為空。";
    if (login_id.length < 8 || login_id.length > 16)
        return "長度有誤。";
    if (!reg.test(login_id))
        return "須為英數字混合。";

    var checkStrArr = [
        '01234567890',
        '09876543210',
        'abcdefghijklmnopqrstuvwxyz',
        'zyxwvutsrqponmlkjihgfedcba'
    ]
    for (var i = 0; i < checkStrArr.length; i++) {

        for (var j = 0; j < checkStrArr[i].length - 2; j++) {

            var seq = checkStrArr[i].substring(j, j + 3);
            var same = checkStrArr[i].charAt(j) + checkStrArr[i].charAt(j) + checkStrArr[i].charAt(j);
            if (login_id.indexOf(seq) !== -1) {
                return "含有不符合規範的字串" + seq;
            } else if (login_id.indexOf(same) !== -1) {
                return "含有不符合規範的字串" + same;
            }
        }
    }
    return null;
}

function sqlToJsDate(sqlDate) {
    //sqlDate in SQL DATETIME format ("yyyy-mm-ddThh:mm:ss.ms")
    // console.log(sqlDate);
    if (!_.isString(sqlDate)) return sqlDate;
    var sqlDateArr1 = sqlDate.split("-");
    // console.log(sqlDateArr1)
    //format of sqlDateArr1[] = ['yyyy','mm','ddThh:mm:ms']
    var sYear = sqlDateArr1[0];
    var sMonth = (Number(sqlDateArr1[1]) - 1).toString();
    var sqlDateArr2 = sqlDateArr1[2].split("T");
    // console.log(sqlDateArr2)
    //format of sqlDateArr2[] = ['dd', 'hh:mm:ss.ms']
    var sDay = sqlDateArr2[0];
    var sqlDateArr3 = sqlDateArr2[1].split(":");
    //format of sqlDateArr3[] = ['hh','mm','ss.ms']
    var sHour = sqlDateArr3[0];
    var sMinute = sqlDateArr3[1];
    var sqlDateArr4 = sqlDateArr3[2].split(".");
    //format of sqlDateArr4[] = ['ss','ms']
    var sSecond = sqlDateArr4[0];
    var sMillisecond = sqlDateArr4[1];


    return new Date(sYear, sMonth, sDay, sHour, sMinute, sSecond, sMillisecond || 0);
}

function sqlToDateStr(sqlDate) {
    //use when only date
    //sqlDate in SQL DATETIME format ("yyyy-mm-dd")
    if (!sqlDate) return;
    var sqlDateArr1 = sqlDate.split("T");
    return sqlDateArr1[0];
}

function sqlToStr(sqlDate) {
    // yyyy-mm-ddThh:mm:ss.ms -> yyyy-mm-dd hh:mm:ss
    // console.log(sqlDate);
    if (!_.isString(sqlDate)) return sqlDate;
    var sqlDateArr = sqlDate.split("T");
    var sqlDateArr2 = sqlDateArr[1].split(".")

    return sqlDateArr[0] + " " + sqlDateArr2[0];
}

function dateFormCheck(inStr) {
    try {
        var resultArr = inStr.split('-');
        if (resultArr.length != 3) return '';
        var date = new Date(resultArr[0], parseInt(resultArr[1]) - 1, resultArr[2]);
        if ((date.getFullYear() == resultArr[0]) && (date.getMonth() + 1 == resultArr[1]) && (date.getDate() == resultArr[2]))
            return inStr;
        else {
            alert('時間格式不正確');
            return '';
        }
    } catch (e) {
        alert('時間格式不正確');
        return '';
    }
}

function isEmptyString(inStr) {
    if (!inStr) return true;
    var rg = /^\s+$/;
    return rg.test(inStr);
}

// dateRange
$(document).on('change', 'input[name="beginDate"]', function(event) {
    var that = this;
    try {
        var startDate = new Date($(that).val());
        var endDateStr = $(that).parent().find('input[name=endDate]').val();
        var endDate = new Date(endDateStr);
        // if (isEmptyString($(that).val())) {
        //     alert('該日期不可使用');
        //     $(that).datepicker('update', endDate);
        //     $(that).val(endDateStr);
        //     $(that).parent().find('input[name=endDate]').datepicker('setStartDate', endDate);
        // }
        $(that).parent().find('input[name=endDate]').datepicker('setStartDate', startDate);

    } catch (e) {
        alert('時間格式不正確');
        return;
    }
})

//dateRange
$(document).on('change', 'input[name="endDate"]', function(event) {
    try {
        var endDate = new Date($(this).val());
        var startDateStr = $(this).parent().find('input[name=beginDate]').val();
        var startDate = new Date(startDateStr);
        // if (isEmptyString($(this).val())) {
        //     alert('該日期不可使用');
        //     $(this).datepicker('update', startDate);
        //     $(this).val(startDateStr);
        //     $(this).parent().find('input[name=beginDate]').datepicker('setEndDate', startDate);
        // }
        $(this).parent().find('input[name=beginDate]').datepicker('setEndDate', endDate);
    } catch (e) {
        alert('時間格式不正確');
        return;
    }
})


//monthRange
$(document).on('change', 'input[name="beginMonth"]', function(event) {
    try {
        var startDate = new Date($(this).val());
        var endDateStr = $(this).parent().find('input[name=endMonth]').val();
        var endDate = new Date(endDateStr);
        if (isEmptyString($(this).val())) {
            alert('該日期不可使用');
            $(this).datepicker('update', endDate);
            $(this).val(endDateStr);
            $(this).parent().find('input[name=endMonth]').datepicker('setStartDate', endDate);
        }
        $(this).parent().find('input[name=endMonth]').datepicker('setStartDate', startDate);
    } catch (e) {
        alert('時間格式不正確');
        return;
    }
})

//monthRange
$(document).on('change', 'input[name="endMonth"]', function(event) {
    try {
        var endDate = new Date($(this).val());
        var startDateStr = $(this).parent().find('input[name=beginMonth]').val();
        var startDate = new Date(startDateStr);
        if (isEmptyString($(this).val())) {
            alert('該日期不可使用');
            $(this).datepicker('update', startDate);
            $(this).val(startDateStr);
            $(this).parent().find('input[name=beginDate]').datepicker('setEndDate', startDate);
        }
        $(this).parent().find('input[name=beginDate]').datepicker('setEndDate', endDate);
    } catch (e) {
        alert('時間格式不正確');
        return;
    }
})

if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function(elt /*, from*/ ) {
        var len = this.length >>> 0;
        var from = Number(arguments[1]) || 0;
        from = (from < 0) ? Math.ceil(from) : Math.floor(from);
        if (from < 0) from += len;

        for (; from < len; from++) {
            if (from in this && this[from] === elt) return from;
        }
        return -1;
    };
}
if (!console) {
    var console = {
        log: function() {}
    }
}
if (!console.log) {
    console.log = function() {}
}


Date.prototype.ToString = function(str) {

    // console.log(str);
    return str
        .replace('yyyy', this.getFullYear())
        .replace('MM', ('0' + (this.getMonth() + 1)).slice(-2))
        .replace('dd', ('0' + this.getDate()).slice(-2))
        .replace('hh', ('0' + this.getHours()).slice(-2))
        .replace('mm', ('0' + this.getMinutes()).slice(-2))
        .replace('ss', ('0' + this.getSeconds()).slice(-2))
}

function GET (url, data, successCallback, errorCallback) {
    $.ajax({
        type: "GET",
        url: url,
        data: data,
        dataType: "json",
        contentType: "application/json",
        timeout: 600000,
        success: successCallback,
        error: errorCallback
    })
}

function POST (url, data, successCallback, errorCallback) {
    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json",
        timeout: 600000,
        success: successCallback,
        error: errorCallback
    })
}

function POSTFILE (url, form, successCallback, errorCallback) {
    data = new FormData(form);
    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: url,
        data: data,
        processData: false, //important
        contentType: false,
        cache: false,
        timeout: 600000,
        success: successCallback,
        error: errorCallback
    })
}