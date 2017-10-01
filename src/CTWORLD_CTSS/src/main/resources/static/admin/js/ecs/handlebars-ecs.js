// handlebars-lb
// Terry 2015/03/23 create
(function($) {
    if (typeof Handlebars !== "undefined") {

        if (typeof $.lb === 'undefined') $.lb = {};

        Handlebars.registerHelper('ifCond', function(v1, v2, options) {
            if (v1 == v2) {
                return options.fn(this);
            }
            return options.inverse(this);
        });

        Handlebars.registerHelper('ifnot', function(v1, v2, options) {
            if (v1 != v2) {
                return options.fn(this);
            }
            return options.inverse(this);
        });
        Handlebars.registerHelper('multiple', function(v1, v2, options) {
            return v1*v2;
        });
        Handlebars.registerHelper('breaklines', function(text) {
            text = Handlebars.Utils.escapeExpression(text);
            text = text.replace(/(\r\n|\n|\r)/gm, '<br>');
            return new Handlebars.SafeString(text);
        });
        Handlebars.registerHelper('multiple_pager_width', function(v1, options) {

            if (v1 > 5) v1 = 5;
            return 120 + (70 / 4) * (v1 - 2);
        });
        Handlebars.registerHelper('sqlToJsDate', function(v1, options) {
            if (!v1) return;
            var date = sqlToJsDate(v1);
            if (!date) return;
            return date.ToString('yyyy/MM/dd');
        });
        Handlebars.registerHelper('sqlToJsDateTime', function(v1, options) {
            if (!v1) return;
            var date = sqlToJsDate(v1);
            if (!date) return;
            return date.ToString('yyyy/MM/dd hh:mm:ss');
        });
        Handlebars.registerHelper('ifGreaterThan', function(v1, v2, options) {
            if (v1 >= v2) return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('doTrStatus', function(v1, options) {
            return $.lb.doTrStatus(v1);
        });
        Handlebars.registerHelper('doMmType', function(v1, options) {
            return $.lb.doMmType(v1);
        });
        Handlebars.registerHelper('doMmStatus', function(v1, options) {
            return $.lb.memberStatus(v1);
        });
        Handlebars.registerHelper('isValid', function(mmStatus, options) {
            if (mmStatus == "1")
                return options.fn(this);
            else
                return options.inverse(this);
        });
        Handlebars.registerHelper('doRiskType', function(v1, options) {
            return $.lb.riskType(v1);
        });
        Handlebars.registerHelper('doServiceType', function(v1, options) {
            return $.lb.doServiceType(v1);
        });
        Handlebars.registerHelper('canPay', function(v1, options) {
            console.log("v1 = " + v1);
            if (v1 == "2" || v1 == "3" || v1 == "4" || v1 == "6")
                return options.fn(this);
            else 
                return options.inverse(this);
        });
        Handlebars.registerHelper('canRecv', function(v1, options) {
            if (v1 == "4" || v1 == "5" || v1 == "6")
                return options.fn(this);
            else 
                return options.inverse(this);
        });

        Handlebars.registerHelper('conbineAddress', function(postCode, city, area, address, options) {

            return $.lb.conbineAddress(postCode, city, area, address);
        });


        Handlebars.registerHelper('recvPaymentTool', function(v1, options) {
            var num = parseInt(v1);
            var arr = [];
            if (v1==0) return "無";
            var str = "";
            if(isNaN(num)) return str;
            if (num % 2) arr.push("ATM轉帳");
            num = Math.floor(num/2);
            if (num % 2) arr.push("Web-ATM");
            num = Math.floor(num/2);
            if (num % 2) arr.push("儲值支付");
            return arr.join(' , ');
        });

        Handlebars.registerHelper('lb_currency', function(val, options) {
            return $.lb.currency(val);
        });


        Handlebars.registerHelper("timestampToDateTime", function(timestamp, options) {
            return $.lb.timestampToDateTime(timestamp)
        });

        Handlebars.registerHelper("timestampToDate", function(timestamp, options) {

            return $.lb.timestampToDate(timestamp);
        });

        Handlebars.registerHelper('isnull', function(v1, v2, options) {
            if (v1) {
                return v1;
            }
            return v2;
        });

        Handlebars.registerHelper("expireTime", function(options) {

            var countdownnumber = 10 * 60; //count down 10mins in seconds
            var countdownid;
            countdownfunc();

            function countdownfunc() {
                if (countdownnumber <= 0) {
                    //alert time's up
                    clearTimeout(countdownid);

                    //時間到登出
                    var Model = getBBModel($.login);

                    var data = {};

                    //隨便給一個 idAttribute
                    data[$.login.idAttribute] = 1;

                    var user = new Model(data);

                    user.destroy({
                        async: false,
                        success: function(model, response, options) {
                            window.location.href = $.config.login_url;
                        },
                        error: function(model, response, options) {
                            window.location.href = $.config.login_url;
                        }
                    });
                    alert('閒置時間過長，自動登出');

                    window.location.href = $.config.login_url;

                } else {

                    out = '';
                    mins = Math.floor(countdownnumber / 60); //minutes
                    secs = Math.floor(countdownnumber % 60); //seconds

                    if (mins < 10)
                        mins = "0" + mins.toString();
                    if (secs < 10)
                        secs = "0" + secs.toString();
                    out += mins + ":" + secs;

                    if ($("#expireTime").length > 0) {
                        if ($("#expireTime").attr('reload')) {
                            countdownnumber = 10 * 60;
                            $("#expireTime").removeAttr('reload');

                        } else $("#expireTime").html(out);
                    }
                    // $("#expireTime").innerHTML = out;

                    countdownnumber--;

                    if (countdownid) {
                        clearTimeout(countdownid);
                    }
                    countdownid = setTimeout(countdownfunc, 1000);
                }
                // return out;
            }

        });

        Handlebars.registerHelper("memberType", function(type, options) {

            return $.lb.doMmType(type);
        });

        Handlebars.registerHelper("ifUpgrade", function(type, options) {

            if ($.lb.ifUpgrade(type)) {
                return options.fn(this);
            }
            return options.inverse(this);
        });

        Handlebars.registerHelper("styleChanged", function(before, after, options){
            if (before == after) //unchanged
                return "";
            else
                return "color:red;";
        })

        Handlebars.registerHelper("doPaymentToolType", function(v1, options) {
            return $.lb.doPaymentToolType(v1);
        })

        Handlebars.registerHelper("ifCertify", function(status, options) {

            if ($.lb.ifCertify(status)) {
                return options.fn(this);
            }
            return options.inverse(this);
        });
        Handlebars.registerHelper("verifyStatus", function(status, options) {

            return $.lb.verifyStatus(status);
        });
        Handlebars.registerHelper("lb_status", function(status, options) {

            return $.lb.lb_status(status);
        });

        Handlebars.registerHelper("bankAccountStatus", function(status, options) {

            return $.lb.bankAccountStatus(status);
        });
        Handlebars.registerHelper("doPaymentSheetType", function(type, options) {
            return $.lb.doPaymentSheetType(type);
        });

        Handlebars.registerHelper('status_parse', function(v1, options) {
            return $.lb.status_parse(v1);
            
        });

        Handlebars.registerHelper('payment_parse', function(v1, options) {
            
            if(v1=="cash") return "Cash";
            else if(v1 =="credit") return "Credit Card";
            else if (v1==null) return "No Pay" ;
            else return v1;
           
        });

        Handlebars.registerHelper('driver_parse', function(v1, options) {
            
            if (v1==null) return "No Diver" ;
            else return v1;
           
        });

        Handlebars.registerHelper('licese_parse', function(v1, options) {
            
             if (v1==null) return "No license plate" ;
             else return v1;
           
        });

        $.lb.status_parse = function(status) {
            try {
                if(status=="0") rtn="New Order";
                else if(status=="10") rtn="Diver Confirmed";
                else if(status=="20") rtn="Accepted";
                else if(status=="30") rtn="Pending";
                else if(status=="40") rtn="Completed";
                else if(status=="5") rtn="Order need nearby driver";
                else if(status=="-10") rtn="Order cancel";
                return rtn;

            } catch (e) {
                return '';
            }
        }

        Handlebars.registerHelper('ifSeller', function(v1, options) {
            if (v1 == "r") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('ifBuyerButton', function(v1, options) {
            if (v1 == "20" || v1 == "30") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('ifBuyerSafeKeep', function(v1, options) {
            if (v1 == "20") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('ifSellerButton1', function(v1, options) {
            if (v1 == "30") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('ifSellerButton2', function(v1, options) {
            if (v1 == "20" || v1 == "30") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('ifBuyerPausePayment', function(v1, options) {
            if (v1 == "40") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('ifCanceled', function(v1, options) {
            if (v1 == "Y") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper('ifAlreadyPay', function(v1, options) {
            if (v1 == "20" || v1 == "30" || v1 == "40") return options.fn(this);
            else return options.inverse(this);
        });
        Handlebars.registerHelper("assistantType", function(status, options) {

            return $.lb.assistantType(status);
        });
        Handlebars.registerHelper("auditStatus", function(status, options) {

            return $.lb.auditStatus(status);
        });
        Handlebars.registerHelper("AdminTitle", function(title, options) {

            return $.lb.AdminTitle(title);
        });
        Handlebars.registerHelper("WHStatus", function(status, options) {

            return $.lb.WHStatus(status);
        });
        Handlebars.registerHelper("isBuyer", function(v1, options) {
            if ($.lb.isBuyer(v1)) {
                return options.fn(this);
            }
            return options.inverse(this);
        });
        Handlebars.registerHelper("canUpgrade", function(v1, options) {
            if ($.lb.canUpgrade(v1)) {
                return options.fn(this);
            }
            return options.inverse(this);
        });
        Handlebars.registerHelper("isAbled", function(mmStatus, options) {
            if ($.lb.isAbled(mmStatus)) {
                return options.fn(this);
            }
            return options.inverse(this);
        });
        Handlebars.registerHelper("isPersonalBuyer", function(v1, options) {
            if ($.lb.isPersonalBuyer(v1)) {
                return options.fn(this);
            }
            return options.inverse(this);
        });
        $.lb.conbineAddress = function(postCode, city, area, address) {

            if (!postCode || !city || !area || !address)
                return '';
            return '(' + postCode + ')' + city + area + address;
        }

        $.lb.currency = function(val) {
            try {
                if (val) {
                    while (/(\d+)(\d{3})/.test(val.toString())) {
                        val = val.toString().replace(/(\d+)(\d{3})/, '$1' + ',' + '$2');
                    }
                      val = '$ ' + val;
                    return val;
                }
                return "$ 0";
            } catch (e) {
                return "貨幣格式有錯:" + val;
            }
        };

        $.lb.timestampToDateTime = function(timestamp) {
            try {
                var date = new Date(timestamp);
                return date.ToString('yyyy/MM/dd hh:mm:ss');

            } catch (e) {
                return '';
            }
        }

        $.lb.timestampToDate = function(timestamp) {
            try {
                var date = new Date(timestamp);
                return date.ToString('yyyy/MM/dd');

            } catch (e) {
                return '';
            }
        }

        $.lb.ifUpgrade = function(type) {
            try {
                switch (parseInt(type)) {
                    case 0:
                        return false;
                        break;
                    case 1:
                        return true;
                        break;
                    case 2:
                        return true;
                        break;
                    case 3:
                        return true;
                        break;
                    case 4:
                        return false;
                        break;
                    case 5:
                        return false;
                        break;
                    case 6:
                        return false;
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }
        $.lb.doTrStatus = function(v1) {
            try {
                switch (v1) {
                    case "0":
                        return '待付款';
                        break;
                    case "20":
                        return '價金保管中';
                        break;
                    case "30":
                        return '價金保管期延長';
                        break;
                    case "40":
                        return '暫停付款中';
                        break;
                    case "50":
                        return '已完成';
                        break;
                    case "60":
                        return '退款交易處理中';
                        break;
                    case "70":
                        return '已退款';
                        break;
                    case "80":
                        return '爭議款項處理中';
                        break;
                    case "90":
                        return '爭議款項已處理';
                        break;
                    case "-10":
                        return '付款失敗';
                        break;
                    case "-20":
                        return '取消交易';
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }
        $.lb.memberStatus = function(status) {
            try {
                switch (parseInt(status)) {
                    case 0:
                        return "未驗證";
                        break;
                    case 1:
                        return "已驗證";
                        break;
                    case 2:
                        return "";//"已驗證未開通";
                        break;
                    case 3:
                        return "";//"已刪除";
                        break;
                    case 4:
                        return "已關閉";
                        break;
                    case 5:
                        return "驗證失敗";
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }
        $.lb.isAbled = function(mmStatus)
        {
            if (mmStatus == "1") return true;
            else return false;
        }
        $.lb.ifCertify = function(status) {
            try {
                switch (parseInt(status)) {
                    case 0:
                        return true;
                        break;
                    case 1:
                        return false;
                        break;
                    case 2:
                        return false;
                        break;
                    case 3:
                        return false;
                        break;
                    case 4:
                        return false;
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }

        $.lb.verifyStatus = function(status) {
            try {
                switch (parseInt(status)) {
                    case 0:
                        return "未驗證";
                        break;
                    case 1:
                        return "已驗證";
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }
        $.lb.lb_status = function(status) {
            try {
                switch (parseInt(status)) {
                    case 0:
                        return "啟用";
                        break;
                    case 1:
                        return "停用";
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }


        $.lb.bankAccountStatus = function(status) {
            try {
                switch (parseInt(status)) {
                    case 1:
                        return "未驗證";
                        break;
                    case 2:
                        return "已驗證";
                        break;
                    case 3:
                        return "驗證中";
                        break;
                    case 4:
                        return "已刪除";
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }

        $.lb.assistantType = function(status) {
            try {
                switch (parseInt(status)) {
                    case 0:
                        return "一般助理";
                        break;
                    case 10:
                        return "特別助理";
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }

        $.lb.auditStatus = function(status) {
            try {
                switch (status) {
                    case '0':
                        return "待審核";
                        break;
                    case '1':
                        return "已審核";
                        break;
                    case '2':
                        return "已退回";
                        break;
                    default:
                        return "";
                        break;
                }
            } catch (e) {
                return '';
            }
        }

        $.lb.doMmType = function(v1) {
            try {
                if (v1 == "0") return '';
                else if (v1 == "1") return '';
                else if (v1 == "2") return '第一類個人使用者';
                else if (v1 == "3") return '';
                else if (v1 == "4") return '第二類個人使用者';
                else if (v1 == "5") return '';
                else if (v1 == "6") return '第二類法人使用者';
                else return;
            } catch (e) {
                return '';
            }
        }

        $.lb.riskType = function(v1) {

            if (v1 == '0') return '一般帳戶';
            else if (v1 == '1') return '停權帳戶(黑名單)';
            else if (v1 == '2') return '警示帳戶';
            else if (v1 == '3') return '止付帳戶';
            else if (v1 == '4') return '凍結帳戶';
            else return '';
        }

        $.lb.doPaymentToolType = function (v1) {
            if (v1=="w") return 'Web-ATM';
            else if (v1=="a") return 'ATM轉帳';
            else if (v1=="lpp") return '儲值支付';
            else if(v1=="BIO") return 'BIO';
            else if(v1=="ACH") return 'ACH';
            else if(v1=="FEE") return '手續費';
            else if(v1=="ADJ") return '餘額調整';
            else return '';
        }

        $.lb.doPaymentSheetType = function (v1) {
            if (v1=="p") return '商品付款按鈕';
            else if (v1=="e") return 'Email指示付款';
            else if (v1=="m") return '訂購單付款按鈕';
            else if (v1=="s") return '電商平台付款'
            else return '';
        }
        
        $.lb.doServiceType = function (v1) {
            if (v1 == '0') return '非會員案件';
            else if (v1 == '1') return '升級第二類使用者認證';
            else if (v1 == '2') return '調節額度';
            else if (v1 == '3') return '價金保管期調整';
            else if (v1 == '4') return '爭議款項';
            else if (v1 == '5') return '調節手續費';
            else if (v1 == '6') return '登入密碼重置';
            else if (v1 == '7') return 'email修改';
            else if (v1 == '8') return '手機號碼修改';
            else if (v1 == '9') return '其他';
            else if (v1 == '10') return '第二類使用者註冊認證';
            else return ;
        }

        $.lb.actionType = function (v1) {
            if (v1 == '0') return '匯款交易';
            else if (v1 == '1') return '取消/部分取消交易';
            else if (v1 == '2') return '退款';
            else if (v1 == '3') return '餘額提領';
            else if (v1 == '4') return '爭議款';
            else if (v1 == '5') return '價金轉代收';
            else return ;
        }

        $.lb.FeeActionType = function (v1) {
            if (v1 == '0') return '匯款交易手續費';
            else if (v1 == '1') return '取消/部分取消交易手續費';
            else if (v1 == '2') return '退款手續費';
            else if (v1 == '3') return '餘額提領手續費';
            else if (v1 == '4') return '爭議款手續費';
            else if (v1 == '5') return '價金轉代收手續費';
            else return ;
        }
        $.lb.AdminTitle = function (v1) {
            if (v1 == '1') return '主控';
            else if (v1 == '2') return '會計主管';
            else if (v1 == '3') return '主管';
            else if (v1 == '4') return '經辦';
            else return '';
        }
        $.lb.WHStatus = function (v1) {
            if (v1 == '0') return '提領中';
            else if (v1 == '1') return '提領成功';
            else if (v1 == '2') return '提領失敗';
            else return '';
        }
        $.lb.isBuyer = function(v1) {

            try {
                return (parseInt(v1)<4);
            } catch (e) {
                return false;
            }
        };
        $.lb.canUpgrade = function(v1) {

            try {
                return (parseInt(v1)<4 || v1 == "5");
            } catch (e) {
                return false;
            }
        };
        $.lb.isPersonalBuyer = function (v1) {
            try {
                return (parseInt(v1)<3)
            } catch(e) {
                return false;
            }
        }
    } else {
        // alert('you have not loaded Handlebars');
    }
})(jQuery);
