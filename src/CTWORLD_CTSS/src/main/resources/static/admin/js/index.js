(function($) {

    function breadcrumb_init(breadcrumb) {
        var html = "";
        if (breadcrumb!=null) {
            if(breadcrumb.length == 2 ) {
                html += '<li><i class="ace-icon fa fa-home home-icon"></i><a>'+breadcrumb[0].am_name+'</a></li>';
                html += '<li class="active">'+breadcrumb[1].am_name+'</li>';
                $(".breadcrumb").html(html);
            }
            else if (breadcrumb.length == 1) {
                html +='<li><i class="ace-icon fa fa-home home-icon"></i><a>'+breadcrumb[0].am_name+'</a></li>';
                $(".breadcrumb").html(html);
            }
        }
        
    }

    function check(arr) {
        var flag = true;
        var path = window.location.pathname;
        //match menu
        for (var i = 0; i < arr.length; i++) {
            if (path.indexOf(arr[i].am_url, path.length - arr[i].am_url.length) !== -1) {
                return;
            }
        }
        //match todo.aspx
        var str = "todo.aspx"
        if (path.indexOf(str, path.length - str.length) !== -1) {
            return;
        }
        alert("您無權瀏覽此頁面");
        window.location.href = $.config.after_login_url;
    }

    function generateMenu(arr) {
        var path = window.location.pathname;

        //set array
        for (var i = 0; i < arr.length; i++) {
            if (path.indexOf(arr[i].am_url, path.length - arr[i].am_url.length) !== -1)
            {
                var index = i;
                var level = arr[i].am_level;
                while(true)
                {
                    if (arr[index].am_level == level)
                    {
                        arr[i].active = true;
                        level--;
                    }
                    i--;
                    if (i < 0) break;
                }
                break;
            }
            if (arr[i].am_level == '1')
                arr[i].hasSub = i != arr.length - 1 && arr[i + 1].am_level == '2';

        }

        var _html = '<ul>';

        for (var i = 0; i < arr.length; i++) {
            if (arr[i].am_level == '1') {

                _html += '<li'+(arr[i].active?(arr[i].hasSub?' class="open"':' class="active"'):'')+'><a href="'+arr[i].am_url+'"><i class="'+arr[i].am_icon+' txt-color-pink"></i> <span class="menu-item-parent">'+arr[i].am_name+'</span></a>';

                if (arr[i].hasSub)
                {
                    _html += '<ul>';

                    i++;
                    for(;i<arr.length && arr[i].am_level == 2; i++) {
                        _html += '<li'+(arr[i].active?' class="active"':'')+'><a href="'+arr[i].am_url+'"><i class="fa fa-arrow-circle-right"></i><span class="menu-item-parent">'+arr[i].am_name+'</span></a></li>';
                    }

                    _html += '</ul>';
                }

                _html += '</li>';
                
            }
        }

        _html += '</ul>';


        $('#left-panel nav').html(_html);
    }

    function page_init() {

        //nav-buttons
        $('.navbar-buttons .user-info').html('<small>Auto logout：<span id="expireTime"></span></small>' + $.login.admin_emp.au_name);

        //check auth of this page
        // check($.login.total_page);

        //sidebar
        generateMenu($.login.total_page);

        //breadcrumb
        breadcrumb_init($.active_breadcrumb);

        //倒數計時
        // countdownfunc();
    }

    function auth_logout() {
        var modelConfig = {
            url: $.login.url+"/logout",
            idAttribute: $.login.idAttribute
        }

        var Model = getBBModel(modelConfig);
        var user = new Model({});
        user.save({},{
            success: function(model, response, options) {
                if (response.rtnCode < 0) {
                    if (response.url)
                        window.location.href = response.url;
                } else {
                    alert("System already logout!");
                    window.location.href = $.config.login_url;
                }
            },
            error: function(model, response, options) {
                // alert('登出失敗');
                window.location.href = $.config.login_url;
            }
        })
    }

    function auth() {
        $.login.UserCollection = getBBCollection($.login);
        $.login.UserModel = getBBModel($.login);
        var user = new $.login.UserModel();

        user.fetch({
            async:false,
            success: function(collction, response, options) {
                if (response.rtnCode < 0) {
                    alert(response.rtnMsg);

                    if (response.url)
                        window.location.href = response.url;
                    else
                        window.location.href = $.config.login_url;
                } else {

                    $.Session = response;

                    $.login.total_page = response.menu;
                    $.login.admin_emp = response.admin_user;

                    page_init(response);
                }
            },
            error: function(model, response, options) {
                // alert("page not found");
                // window.location.href = $.config.home_url;
            }
        });
    }

    // Backbone.emulateHTTP = true;
    
    auth();

    $(document).on('click', '#logout', function() {
        if (!confirm('Are you sure to logout?')) return;
        auth_logout();
    })

    // var expireTime = idle_time * 60;
    // var countdownnumber = expireTime; //count down 10mins in seconds
    // var countdownid = setInterval(countdownfunc, 1000);

    // function countdownfunc() {
    //     if (countdownnumber <= 0) {
    //         //alert time's up
    //         clearTimeout(countdownid);

    //         auth_logout();

    //     } else if (countdownnumber == 60) {

    //         if (countdownid) {
    //             clearInterval(countdownid);
    //         }

    //         var ret = confirm("時間即將到，是否繼續使用?");
    //         if (ret)
    //             countdownnumber = expireTime;
    //         else
    //             countdownnumber--;
    //         var uc = new $.login.UserCollection();

    //         uc.fetch({
    //             async: false,
    //             success: function(collection, response, options) {

    //                 if (response.rtnCode < 0) {
    //                     alert(response.rtnMsg);
    //                     if (response.url)
    //                         window.location.href = response.url;
    //                     else
    //                         window.location.href = $.config.login_url;
    //                 }
    //             },
    //             error: defaultErrorHandler
    //         });

    //         countdownid = setInterval(countdownfunc, 1000);
    //     } else {

    //         out = '';
    //         mins = Math.floor(countdownnumber / 60); //minutes
    //         secs = Math.floor(countdownnumber % 60); //seconds

    //         if (mins < 10)
    //             mins = "0" + mins.toString();
    //         if (secs < 10)
    //             secs = "0" + secs.toString();
    //         out += mins + ":" + secs;

    //         $("#expireTime").html(out);

    //         countdownnumber--;
    //     }
    //     // return out;
    // }

    // function reset_countdown() {
    //     countdownnumber = expireTime;
    // }

    // $("#reset_expire_time").click(function(event) {
    //     countdownnumber = expireTime;
    //     var uc = new $.login.UserCollection();
    //     uc.fetch({
    //         async: false,
    //         success: function(collection, response, options) {

    //             if (response.rtnCode < 0) {
    //                 alert(response.rtnMsg);
    //                 window.location.href = $.config.login_url;
    //             }
    //         },
    //         error: defaultErrorHandler
    //     });
    // })

    // $.reset_countdown = reset_countdown;
})(jQuery);
