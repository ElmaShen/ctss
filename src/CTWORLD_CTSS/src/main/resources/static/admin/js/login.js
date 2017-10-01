(function($){
    function auth() {
        GET($.login.url, {}, function(response) {
                if (response.rtnCode == "0") 
                    window.location.href = $.config.after_login_url;
            }, defaultErrorHandler
        );
    }

    auth();
    
	function encode(input) {  
		var keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="; 
		var output = "";  
		var chr1, chr2, chr3 = "";  
		var enc1, enc2, enc3, enc4 = "";  
		var i = 0;  
		do {  
		    chr1 = input.charCodeAt(i++);  
		    chr2 = input.charCodeAt(i++);  
		    chr3 = input.charCodeAt(i++);  
		    enc1 = chr1 >> 2;  
		    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
		    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
		    enc4 = chr3 & 63;  
		    if (isNaN(chr2)) {  
		        enc3 = enc4 = 64;  
		    } else if (isNaN(chr3)) {  
		        enc4 = 64;  
		    }  
		    output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)  
		            + keyStr.charAt(enc3) + keyStr.charAt(enc4);  
		    chr1 = chr2 = chr3 = "";  
		    enc1 = enc2 = enc3 = enc4 = "";  
		} while (i < input.length);  
		
		return output;  
	} 



	var errorClass = 'invalid';
	var errorElement = 'em';

	var $loginForm = $("#login-form").validate({
		errorClass		: errorClass,
		errorElement	: errorElement,
		highlight: function(element) {
	        $(element).parent().removeClass('state-success').addClass("state-error");
	        $(element).removeClass('valid');
	    },
	    unhighlight: function(element) {
	        $(element).parent().removeClass("state-error").addClass('state-success');
	        $(element).addClass('valid');
	    },
		// Rules for form validation
		rules : {
			account : {
				required : true
			},
			password : {
				required : true,
				minlength : 3,
				maxlength : 20
			}
		},

		// Messages for form validation
		messages : {
			account : {
				required : 'Please enter your account'
			},
			password : {
				required : 'Please enter your password'
			}
		},

		// Do not change code below
		errorPlacement : function(error, element) {
			error.insertAfter(element.parent());
		},
		submitHandler: function(form) {
			var account = $('input[name="account"]').val();
			var password = $('input[name="password"]').val();
			var userType = $('select[name="userType"]').val();
			//password = SHA1(password);
			password = encode(password);  

			POST($.config.server + "auth", {
					account: account,
					password: password,
					userType: userType
				}, function(data) {
					if (data.rtnCode < 0)
						alert(data.rtnMsg);
					if (data.url) {
						window.location.href = data.url;
						return;
					}
					if (data.rtnCode >= 0) {
						window.location.href = $.config.after_login_url;
					}

				}, defaultErrorHandler);
			return false;
		}
	});
})(jQuery)