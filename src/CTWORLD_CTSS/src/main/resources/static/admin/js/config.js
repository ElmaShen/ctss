(function($) {

//    $.url_prefix = "/ecs"
    $.url_prefix = ".."
    $.config = {
        url_prefix: "",
        // restful server
        server: $.url_prefix + "/api/admin/",
        // fileServer: "../api/fileUpload/",
        // receiptServer: "../api/receipt/",
        // home page
        home_url: $.url_prefix + "/admin/login.html",
        // login page
        login_url: $.url_prefix + "/admin/login.html",
        after_login_url: $.url_prefix + "/admin/index",
        oops_url: $.url_prefix + "/admin/oops.html",
        html_container: "#admin_container",
        GridView: {
            table: {
                tpl: '<div class="row" id="result_table" style="display: block;"><div class="col-xs-10 table-result"><h3 class="header smaller lighter blue"></h3><div class="clearfix"><div class="pull-right tableTools-container"></div></div><div class="table-header">Result</div><div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer"><div class="row"><div class="col-xs-6"><div class="dataTables_length per_page" id="dynamic-table_length"><label>Every page <select name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm"><option value="10">10</option><option value="25">25</option><option value="50">50</option><option value="100">100</option></select> data </label></div></div><div class="col-xs-6"><div id="dynamic-table_filter" class="dataTables_filter"></div></div></div><div class="widget-content"></div></div></div></div>',
                thead: '<thead><tr><th colspan="{{size}}">{{{gridviewTitle}}}</th></tr></thead>'
            },
            pager: {
                tpl: '<div class="col-xs-12" id="grid_pager"><div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate"><ul class="pagination">{{#if is_first_page}}{{else}}<li class="paginate_button firstPage" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_previous"><a class="firstPage" href="#">First</span></a></li>{{/if}}{{#each page_no}}<li class="paginate_button {{#if is_active}}active{{/if}}" aria-controls="dynamic-table" tabindex="0"><a href="#">{{num}}</a></li>{{/each}}{{#if is_last_page}}{{else}}<li class="paginate_button" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next"><a class="lastPage" href="#"><span>Last</span></a></li>{{/if}}</ul></div></div>'
            }
        },
        datagrid: {
            droplist: {
                noselect: "__no__",
                tpl: '<select class="form-control">{{#each options}}<option value="{{ value }}">{{ label }}</option>{{/each}}</select>'
            },
            emptyMessage: "<p>no data</p>",
            per_page: 10,
            selector_num: 5
        },
        IGO1: {
            gv: "#gv",
            fv: "#fv",
            addTitle: "新增",
            editTitle: "修改",
            FormView: "FormView"
        }
        //platforms: ["TWQ", "支付連", "歐付寶"]
    };

    $.login = {
        // check the authenication status
        url: $.config.server + "auth",
        idAttribute: '_id',
        menu: {
            tpl: '<div class="sidebar-dropdown"><a href="#">Navigation</a></div><ul id="nav">{{#each menuitem}}{{#if this.menuitem}}<li class="has_sub"><a href="#" class=""><i class="{{ am_icon }}"></i> {{ am_name }} <span class="pull-right"><i class="icon-chevron-right"></i></span></a><ul>{{#each this.menuitem}}<li><a href="{{ am_url }}"><i class="{{ am_icon }}"></i>{{ am_name }}</a></li>{{/each}}</ul></li>{{else}}<li><a href="{{ am_url }}"><i class="{{ am_icon }}"></i> {{ am_name }}</a></li>{{/if}}{{/each}}</ul>'
        }//,
        //modified by tom, 20150602
        //default menu, will overwrited in index.js
        // total_page: [{
        //     value: '1',
        //     url: '#',
        //     name: '上架管理',
        //     icon: '<i class="menu-icon fa fa-shopping-cart"></i>'
        // }, {
        //     value: '2',
        //     url: 'web_promotion.html',
        //     name: '促銷商品管理',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i> '
        // }, {
        //     value: '2',
        //     url: 'web_ads.html',
        //     name: '輪播/廣告版面管理',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i> '
        // }, {
        //     value: '2',
        //     url: 'web_brand.html',
        //     name: '品牌專區管理',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i> '
        // }, {
        //     value: '2',
        //     url: 'web_info.html',
        //     name: '最新消息/站內公告管理',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i> '
        // }, {
        //     value: '2',
        //     url: 'web_faq.html',
        //     name: '常見問答管理',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i> '
        // }, {
        //     value: '2',
        //     url: 'web_contract.html',
        //     name: '保管服務契約管理',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i> '
        // }, {
        //     value: '2',
        //     url: 'web_check_by_superior.html',
        //     name: '上架管理審核',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i> '
        // }, {
        //     value: '1',
        //     url: 'customer_query.html',
        //     name: '會員資料查詢',
        //     icon: '<i class="menu-icon glyphicon glyphicon-user"></i>'
        // }, {
        //     value: '1',
        //     url: 'transaction_query.html',
        //     name: '交易查詢管理',
        //     icon: '<i class="menu-icon glyphicon glyphicon-tags"></i>'
        // }, {
        //     value: '1',
        //     url: '#',
        //     name: '統計查詢',
        //     icon: '<i class="menu-icon fa fa-bar-chart"></i>'
        // }, {
        //     value: '2',
        //     url: 'transaction_customer.html',
        //     name: '會員數量統計查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'transaction_all.html',
        //     name: '交易量統計查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '1',
        //     url: '#',
        //     name: '業務諮詢/會員資料管理',
        //     icon: '<i class="menu-icon fa fa-briefcase"></i>'
        // }, {
        //     value: '2',
        //     url: 'service_add.html',
        //     name: '新增案件及身份驗證',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'customer_modify.html',
        //     name: '會員資料修改',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'service_query.html',
        //     name: '業務諮詢/會員資料案件查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'service_check_by_superior.html',
        //     name: '業務諮詢案件審核',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'service_count.html',
        //     name: '業務諮詢資料統計查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '1',
        //     url: '#',
        //     name: '風險管理',
        //     icon: '<i class="menu-icon fa fa-question-circle"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_warning.html',
        //     name: '風險警示通知',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_add.html',
        //     name: '會員風險等級設定',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_query.html',
        //     name: '風險案件查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_check_by_superior.html',
        //     name: '風險案件審核',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_IP.html',
        //     name: '相同IP大量交易查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_day.html',
        //     name: '單日累計大量交易查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_customer.html',
        //     name: '相同會員大量交易查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_like.html',
        //     name: '疑似風險交易查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'risk_blacklist.html',
        //     name: '黑名單管理',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '1',
        //     url: 'scheduling.html',
        //     name: '排程資料查詢',
        //     icon: '<i class="menu-icon fa fa-calendar"></i>'
        // }, {
        //     value: '1',
        //     url: '#',
        //     name: '帳務管理',
        //     icon: '<i class="menu-icon fa fa-money"></i>'
        // }, {
        //     value: '2',
        //     url: 'accounts_debatable.html',
        //     name: '爭議款項專戶查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'accounts_money_keep.html',
        //     name: '價金保管專戶查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'accounts_balance.html',
        //     name: '代收款項專戶查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'accounts_detail.html',
        //     name: '手續費收入明細查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'batch_query.html',
        //     name: '批次檔案結果查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'accounts_check_by_superior.html',
        //     name: '餘額調整審核',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '1',
        //     url: '#',
        //     name: '日誌紀錄',
        //     icon: '<i class="menu-icon fa fa-folder-open"></i>'
        // }, {
        //     value: '2',
        //     url: 'systematic.html',
        //     name: '系統日誌紀錄查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'systematic_deal.html',
        //     name: '交易日誌紀錄查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'systematic_web.html',
        //     name: '網站日誌紀錄查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'systematic_operate.html',
        //     name: '會員操作軌跡查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'systematic_internal_operate.html',
        //     name: '行員操作軌跡查詢',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '1',
        //     url: '#',
        //     name: '統計報表',
        //     icon: '<i class="menu-icon fa fa-line-chart"></i>'
        // }, {
        //     value: '2',
        //     url: 'statistic_IP.html',
        //     name: '交易IP紀錄表',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'statistic_ATM.html',
        //     name: '虛擬帳號收款手續費報表',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'statistic_refunds.html',
        //     name: '退款手續費報表',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'statistic_agent.html',
        //     name: '代收款項餘額手續費報表',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'statistic_account.html',
        //     name: '專戶撥付明細報表',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'statistic_argue.html',
        //     name: '爭議款報表',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'statistic_msg.html',
        //     name: '簡訊統計報表',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '1',
        //     url: '#',
        //     name: '參數設定',
        //     icon: '<i class="menu-icon glyphicon glyphicon-cog"></i>'
        // }, {
        //     value: '2',
        //     url: 'rights_user.html',
        //     name: '管理帳號',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'rights_group.html',
        //     name: '管理權限群組設定',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'rights_parameter.html',
        //     name: '系統參數設定',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'rights_notification.html',
        //     name: '郵件/簡訊模板設置',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }, {
        //     value: '2',
        //     url: 'rights_check_by_superior.html',
        //     name: '參數設定審核',
        //     icon: '<i class="menu-icon fa fa-caret-right"></i>'
        // }]
    };


    $.limit = {
        "1": {
            "prepaid": 200000, //帳戶儲值限額
            "single_tx": 50000, //單筆交易限額
            "daily_tx": 100000, //每日累計交易限額
            "month_tx": 200000 //每月累計交易限額
        },
        "2": {
            "prepaid": 100000,
            "single_tx": 50000,
            "daily_tx": 100000,
            "month_tx": 100000
        },
        "3": {
            "prepaid": 10000,
            "single_tx": 10000,
            "daily_tx": 10000,
            "month_tx": 30000
        }
    }


    // $.order = {
    //     sign_selector: [{
    //         label: "篩選簽核狀態",
    //         value: $.config.datagrid.droplist.noselect
    //     }, {
    //         label: "簽核中",
    //         value: "10"
    //     }, {
    //         label: "簽核同意",
    //         value: "20"
    //     }, {
    //         label: "簽核不同意",
    //         value: "-1"
    //     }],
    //     sign_selector_map: {
    //         "10": {
    //             label: "簽核中",
    //             value: "10"
    //         },
    //         "20": {
    //             label: "簽核同意",
    //             value: "20"
    //         },
    //         "-1": {
    //             label: "簽核不同意",
    //             value: "-1"
    //         }
    //     },
    //     ship_selector: [{
    //         label: "篩選出貨狀態",
    //         value: $.config.datagrid.droplist.noselect
    //     }, {
    //         label: "訂單成立，廠商備貨中",
    //         value: "30"
    //     }, {
    //         label: "出貨中",
    //         value: "40"
    //     }, {
    //         label: "已出貨",
    //         value: "50"
    //     }],
    //     ship_selector_map: {
    //         "30": {
    //             label: "訂單成立，廠商備貨中",
    //             value: "30"
    //         },
    //         "40": {
    //             label: "出貨中",
    //             value: "40"
    //         },
    //         "50": {
    //             label: "已出貨",
    //             value: "50"
    //         }
    //     },
    // };

    $.shippment = {
        ship_selector: [{
            label: "篩選出貨單狀態",
            value: $.config.datagrid.droplist.noselect
        }, {
            label: "商家發貨中",
            value: "10"
        }, {
            label: "到達台灣集貨倉",
            value: "20"
        }, {
            label: "到達大陸集貨倉",
            value: "30"
        }, {
            label: "商品入庫",
            value: "40"
        }, {
            label: "商品出庫",
            value: "50"
        }, {
            label: "商家作廢",
            value: "-10"
        }, {
            label: "出貨單取消",
            value: "-20"
        }],
        ship_selector_map: {
            "10": {
                label: "商家發貨中",
                value: "10"
            },
            "20": {
                label: "到達台灣集貨倉",
                value: "20"
            },
            "30": {
                label: "到達大陸集貨倉",
                value: "30"
            },
            "40": {
                label: "入庫",
                value: "40"
            },
            "50": {
                label: "出庫",
                value: "50"
            },
            "-10": {
                label: "商家作廢",
                value: "-10"
            },
            "-20": {
                label: "出貨單取消",
                value: "-20"
            }
        }
    };

})(jQuery);
