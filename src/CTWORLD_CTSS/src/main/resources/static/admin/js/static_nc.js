(function($) {
	
	// $(document).ready(function() {
			
	// 	pageSetUp();
	// 	var errorClass = 'invalid';
	// 	var errorElement = 'em';
		
	// 	var $checkoutForm = $('#checkout-form').validate({
	// 		errorClass		: errorClass,
	// 		errorElement	: errorElement,
	// 		highlight: function(element) {
	// 	        $(element).parent().removeClass('state-success').addClass("state-error");
	// 	        $(element).removeClass('valid');
	// 	    },
	// 	    unhighlight: function(element) {
	// 	        $(element).parent().removeClass("state-error").addClass('state-success');
	// 	        $(element).addClass('valid');
	// 	    },

	// 	// Rules for form validation
	// 		rules : {
	// 			fname : {
	// 				required : true
	// 			},
	// 			lname : {
	// 				required : true
	// 			},
	// 			email : {
	// 				required : true,
	// 				email : true
	// 			},
	// 			phone : {
	// 				required : true
	// 			},
	// 			country : {
	// 				required : true
	// 			},
	// 			city : {
	// 				required : true
	// 			},
	// 			code : {
	// 				required : true,
	// 				digits : true
	// 			},
	// 			address : {
	// 				required : true
	// 			},
	// 			name : {
	// 				required : true
	// 			},
	// 			card : {
	// 				required : true,
	// 				creditcard : true
	// 			},
	// 			cvv : {
	// 				required : true,
	// 				digits : true
	// 			},
	// 			month : {
	// 				required : true
	// 			},
	// 			year : {
	// 				required : true,
	// 				digits : true
	// 			}
	// 		},
	
	// 		// Messages for form validation
	// 		messages : {
	// 			fname : {
	// 				required : 'Please enter your first name'
	// 			},
	// 			lname : {
	// 				required : 'Please enter your last name'
	// 			},
	// 			email : {
	// 				required : 'Please enter your email address',
	// 				email : 'Please enter a VALID email address'
	// 			},
	// 			phone : {
	// 				required : 'Please enter your phone number'
	// 			},
	// 			country : {
	// 				required : 'Please select your country'
	// 			},
	// 			city : {
	// 				required : 'Please enter your city'
	// 			},
	// 			code : {
	// 				required : 'Please enter code',
	// 				digits : 'Digits only please'
	// 			},
	// 			address : {
	// 				required : 'Please enter your full address'
	// 			},
	// 			name : {
	// 				required : 'Please enter name on your card'
	// 			},
	// 			card : {
	// 				required : 'Please enter your card number'
	// 			},
	// 			cvv : {
	// 				required : 'Enter CVV2',
	// 				digits : 'Digits only'
	// 			},
	// 			month : {
	// 				required : 'Select month'
	// 			},
	// 			year : {
	// 				required : 'Enter year',
	// 				digits : 'Digits only please'
	// 			}
	// 		},
	
	// 		// Do not change code below
	// 		errorPlacement : function(error, element) {
	// 			error.insertAfter(element.parent());
	// 		}
	// 	});
				
	// 	var $registerForm = $("#smart-form-register").validate({
	// 		errorClass		: errorClass,
	// 		errorElement	: errorElement,
	// 		highlight: function(element) {
	// 	        $(element).parent().removeClass('state-success').addClass("state-error");
	// 	        $(element).removeClass('valid');
	// 	    },
	// 	    unhighlight: function(element) {
	// 	        $(element).parent().removeClass("state-error").addClass('state-success');
	// 	        $(element).addClass('valid');
	// 	    },

	// 		// Rules for form validation
	// 		rules : {
	// 			username : {
	// 				required : true
	// 			},
	// 			email : {
	// 				required : true,
	// 				email : true
	// 			},
	// 			password : {
	// 				required : true,
	// 				minlength : 3,
	// 				maxlength : 20
	// 			},
	// 			passwordConfirm : {
	// 				required : true,
	// 				minlength : 3,
	// 				maxlength : 20,
	// 				equalTo : '#password'
	// 			},
	// 			firstname : {
	// 				required : true
	// 			},
	// 			lastname : {
	// 				required : true
	// 			},
	// 			gender : {
	// 				required : true
	// 			},
	// 			terms : {
	// 				required : true
	// 			}
	// 		},

	// 		// Messages for form validation
	// 		messages : {
	// 			login : {
	// 				required : 'Please enter your login'
	// 			},
	// 			email : {
	// 				required : 'Please enter your email address',
	// 				email : 'Please enter a VALID email address'
	// 			},
	// 			password : {
	// 				required : 'Please enter your password'
	// 			},
	// 			passwordConfirm : {
	// 				required : 'Please enter your password one more time',
	// 				equalTo : 'Please enter the same password as above'
	// 			},
	// 			firstname : {
	// 				required : 'Please select your first name'
	// 			},
	// 			lastname : {
	// 				required : 'Please select your last name'
	// 			},
	// 			gender : {
	// 				required : 'Please select your gender'
	// 			},
	// 			terms : {
	// 				required : 'You must agree with Terms and Conditions'
	// 			}
	// 		},

	// 		// Do not change code below
	// 		errorPlacement : function(error, element) {
	// 			error.insertAfter(element.parent());
	// 		}
	// 	});

	// 	var $reviewForm = $("#review-form").validate({
	// 		errorClass		: errorClass,
	// 		errorElement	: errorElement,
	// 		highlight: function(element) {
	// 	        $(element).parent().removeClass('state-success').addClass("state-error");
	// 	        $(element).removeClass('valid');
	// 	    },
	// 	    unhighlight: function(element) {
	// 	        $(element).parent().removeClass("state-error").addClass('state-success');
	// 	        $(element).addClass('valid');
	// 	    },
	// 		// Rules for form validation
	// 		rules : {
	// 			name : {
	// 				required : true
	// 			},
	// 			email : {
	// 				required : true,
	// 				email : true
	// 			},
	// 			review : {
	// 				required : true,
	// 				minlength : 20
	// 			},
	// 			quality : {
	// 				required : true
	// 			},
	// 			reliability : {
	// 				required : true
	// 			},
	// 			overall : {
	// 				required : true
	// 			}
	// 		},

	// 		// Messages for form validation
	// 		messages : {
	// 			name : {
	// 				required : 'Please enter your name'
	// 			},
	// 			email : {
	// 				required : 'Please enter your email address',
	// 				email : '<i class="fa fa-warning"></i><strong>Please enter a VALID email addres</strong>'
	// 			},
	// 			review : {
	// 				required : 'Please enter your review'
	// 			},
	// 			quality : {
	// 				required : 'Please rate quality of the product'
	// 			},
	// 			reliability : {
	// 				required : 'Please rate reliability of the product'
	// 			},
	// 			overall : {
	// 				required : 'Please rate the product'
	// 			}
	// 		},

	// 		// Do not change code below
	// 		errorPlacement : function(error, element) {
	// 			error.insertAfter(element.parent());
	// 		}
	// 	});
		
	// 	var $commentForm = $("#comment-form").validate({
	// 		errorClass		: errorClass,
	// 		errorElement	: errorElement,
	// 		highlight: function(element) {
	// 	        $(element).parent().removeClass('state-success').addClass("state-error");
	// 	        $(element).removeClass('valid');
	// 	    },
	// 	    unhighlight: function(element) {
	// 	        $(element).parent().removeClass("state-error").addClass('state-success');
	// 	        $(element).addClass('valid');
	// 	    },
	// 		// Rules for form validation
	// 		rules : {
	// 			name : {
	// 				required : true
	// 			},
	// 			email : {
	// 				required : true,
	// 				email : true
	// 			},
	// 			url : {
	// 				url : true
	// 			},
	// 			comment : {
	// 				required : true
	// 			}
	// 		},

	// 		// Messages for form validation
	// 		messages : {
	// 			name : {
	// 				required : 'Enter your name',
	// 			},
	// 			email : {
	// 				required : 'Enter your email address',
	// 				email : 'Enter a VALID email'
	// 			},
	// 			url : {
	// 				email : 'Enter a VALID url'
	// 			},
	// 			comment : {
	// 				required : 'Please enter your comment'
	// 			}
	// 		},

	// 		// Ajax form submition
	// 		submitHandler : function(form) {
	// 			$(form).ajaxSubmit({
	// 				success : function() {
	// 					$("#comment-form").addClass('submited');
	// 				}
	// 			});
	// 		},

	// 		// Do not change code below
	// 		errorPlacement : function(error, element) {
	// 			error.insertAfter(element.parent());
	// 		}
	// 	});

	// 	var $contactForm = $("#contact-form").validate({
	// 		errorClass		: errorClass,
	// 		errorElement	: errorElement,
	// 		highlight: function(element) {
	// 	        $(element).parent().removeClass('state-success').addClass("state-error");
	// 	        $(element).removeClass('valid');
	// 	    },
	// 	    unhighlight: function(element) {
	// 	        $(element).parent().removeClass("state-error").addClass('state-success');
	// 	        $(element).addClass('valid');
	// 	    },
	// 		// Rules for form validation
	// 		rules : {
	// 			name : {
	// 				required : true
	// 			},
	// 			email : {
	// 				required : true,
	// 				email : true
	// 			},
	// 			message : {
	// 				required : true,
	// 				minlength : 10
	// 			}
	// 		},

	// 		// Messages for form validation
	// 		messages : {
	// 			name : {
	// 				required : 'Please enter your name',
	// 			},
	// 			email : {
	// 				required : 'Please enter your email address',
	// 				email : 'Please enter a VALID email address'
	// 			},
	// 			message : {
	// 				required : 'Please enter your message'
	// 			}
	// 		},

	// 		// Ajax form submition
	// 		submitHandler : function(form) {
	// 			$(form).ajaxSubmit({
	// 				success : function() {
	// 					$("#contact-form").addClass('submited');
	// 				}
	// 			});
	// 		},

	// 		// Do not change code below
	// 		errorPlacement : function(error, element) {
	// 			error.insertAfter(element.parent());
	// 		}
	// 	});

	// 	var $loginForm = $("#login-form").validate({
	// 		errorClass		: errorClass,
	// 		errorElement	: errorElement,
	// 		highlight: function(element) {
	// 	        $(element).parent().removeClass('state-success').addClass("state-error");
	// 	        $(element).removeClass('valid');
	// 	    },
	// 	    unhighlight: function(element) {
	// 	        $(element).parent().removeClass("state-error").addClass('state-success');
	// 	        $(element).addClass('valid');
	// 	    },
	// 		// Rules for form validation
	// 		rules : {
	// 			email : {
	// 				required : true,
	// 				email : true
	// 			},
	// 			password : {
	// 				required : true,
	// 				minlength : 3,
	// 				maxlength : 20
	// 			}
	// 		},

	// 		// Messages for form validation
	// 		messages : {
	// 			email : {
	// 				required : 'Please enter your email address',
	// 				email : 'Please enter a VALID email address'
	// 			},
	// 			password : {
	// 				required : 'Please enter your password'
	// 			}
	// 		},

	// 		// Do not change code below
	// 		errorPlacement : function(error, element) {
	// 			error.insertAfter(element.parent());
	// 		},
	// 		submitHandler: function(form) {
	// 			alert("yo motherfucker");
	// 			return false;
	// 		}
	// 	});

	// 	var $orderForm = $("#order-form").validate({
	// 		errorClass		: errorClass,
	// 		errorElement	: errorElement,
	// 		highlight: function(element) {
	// 	        $(element).parent().removeClass('state-success').addClass("state-error");
	// 	        $(element).removeClass('valid');
	// 	    },
	// 	    unhighlight: function(element) {
	// 	        $(element).parent().removeClass("state-error").addClass('state-success');
	// 	        $(element).addClass('valid');
	// 	    },
	// 		// Rules for form validation
	// 		rules : {
	// 			name : {
	// 				required : true
	// 			},
	// 			email : {
	// 				required : true,
	// 				email : true
	// 			},
	// 			phone : {
	// 				required : true
	// 			},
	// 			interested : {
	// 				required : true
	// 			},
	// 			budget : {
	// 				required : true
	// 			}
	// 		},

	// 		// Messages for form validation
	// 		messages : {
	// 			name : {
	// 				required : 'Please enter your name'
	// 			},
	// 			email : {
	// 				required : 'Please enter your email address',
	// 				email : 'Please enter a VALID email address'
	// 			},
	// 			phone : {
	// 				required : 'Please enter your phone number'
	// 			},
	// 			interested : {
	// 				required : 'Please select interested service'
	// 			},
	// 			budget : {
	// 				required : 'Please select your budget'
	// 			}
	// 		},

	// 		// Do not change code below
	// 		errorPlacement : function(error, element) {
	// 			error.insertAfter(element.parent());
	// 		}
	// 	});

	// 	// START AND FINISH DATE
	// 	$('#startdate').datepicker({
	// 		dateFormat : 'dd.mm.yy',
	// 		prevText : '<i class="fa fa-chevron-left"></i>',
	// 		nextText : '<i class="fa fa-chevron-right"></i>',
	// 		onSelect : function(selectedDate) {
	// 			$('#finishdate').datepicker('option', 'minDate', selectedDate);
	// 		}
	// 	});
		
	// 	$('#finishdate').datepicker({
	// 		dateFormat : 'dd.mm.yy',
	// 		prevText : '<i class="fa fa-chevron-left"></i>',
	// 		nextText : '<i class="fa fa-chevron-right"></i>',
	// 		onSelect : function(selectedDate) {
	// 			$('#startdate').datepicker('option', 'maxDate', selectedDate);
	// 		}
	// 	});


	
	// })



	// DO NOT REMOVE : GLOBAL FUNCTIONS!
	
	$(document).ready(function() {
		
		pageSetUp();
		
		/* // DOM Position key index //
	
		l - Length changing (dropdown)
		f - Filtering input (search)
		t - The Table! (datatable)
		i - Information (records)
		p - Pagination (paging)
		r - pRocessing 
		< and > - div elements
		<"#id" and > - div with an id
		<"class" and > - div with a class
		<"#id.class" and > - div with an id and class
		
		Also see: http://legacy.datatables.net/usage/features
		*/	

		/* BASIC ;*/
			var responsiveHelper_dt_basic = undefined;
			var responsiveHelper_datatable_fixed_column = undefined;
			var responsiveHelper_datatable_col_reorder = undefined;
			var responsiveHelper_datatable_tabletools = undefined;
			
			var breakpointDefinition = {
				tablet : 1024,
				phone : 480
			};

			$('#dt_basic').dataTable({
				"sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-12 hidden-xs'l>r>"+
					"t"+
					"<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
				"autoWidth" : true,
		        "oLanguage": {
				    "sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>'
				},
				"preDrawCallback" : function() {
					// Initialize the responsive datatables helper once.
					if (!responsiveHelper_dt_basic) {
						responsiveHelper_dt_basic = new ResponsiveDatatablesHelper($('#dt_basic'), breakpointDefinition);
					}
				},
				"rowCallback" : function(nRow) {
					responsiveHelper_dt_basic.createExpandIcon(nRow);
				},
				"drawCallback" : function(oSettings) {
					responsiveHelper_dt_basic.respond();
				}
			});

		/* END BASIC */
		
		/* COLUMN FILTER  */
	    var otable = $('#datatable_fixed_column').DataTable({
	    	//"bFilter": false,
	    	//"bInfo": false,
	    	//"bLengthChange": false
	    	//"bAutoWidth": false,
	    	//"bPaginate": false,
	    	//"bStateSave": true // saves sort state using localStorage
			"sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6 hidden-xs'f><'col-sm-6 col-xs-12 hidden-xs'<'toolbar'>>r>"+
					"t"+
					"<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
			"autoWidth" : true,
			"oLanguage": {
				"sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>'
			},
			"preDrawCallback" : function() {
				// Initialize the responsive datatables helper once.
				if (!responsiveHelper_datatable_fixed_column) {
					responsiveHelper_datatable_fixed_column = new ResponsiveDatatablesHelper($('#datatable_fixed_column'), breakpointDefinition);
				}
			},
			"rowCallback" : function(nRow) {
				responsiveHelper_datatable_fixed_column.createExpandIcon(nRow);
			},
			"drawCallback" : function(oSettings) {
				responsiveHelper_datatable_fixed_column.respond();
			}		
		
	    });
	    
	    // custom toolbar
	    $("div.toolbar").html('<div class="text-right"><img src="img/logo.png" alt="SmartAdmin" style="width: 111px; margin-top: 3px; margin-right: 10px;"></div>');
	    	   
	    // Apply the filter
	    $("#datatable_fixed_column thead th input[type=text]").on( 'keyup change', function () {
	    	
	        otable
	            .column( $(this).parent().index()+':visible' )
	            .search( this.value )
	            .draw();
	            
	    } );
	    /* END COLUMN FILTER */   
    
		/* COLUMN SHOW - HIDE */
		$('#datatable_col_reorder').dataTable({
			"sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-6 hidden-xs'C>r>"+
					"t"+
					"<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
			"autoWidth" : true,
			"oLanguage": {
				"sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>'
			},
			"preDrawCallback" : function() {
				// Initialize the responsive datatables helper once.
				if (!responsiveHelper_datatable_col_reorder) {
					responsiveHelper_datatable_col_reorder = new ResponsiveDatatablesHelper($('#datatable_col_reorder'), breakpointDefinition);
				}
			},
			"rowCallback" : function(nRow) {
				responsiveHelper_datatable_col_reorder.createExpandIcon(nRow);
			},
			"drawCallback" : function(oSettings) {
				responsiveHelper_datatable_col_reorder.respond();
			}			
		});
		
		/* END COLUMN SHOW - HIDE */

		/* TABLETOOLS */
		$('#datatable_tabletools').dataTable({
			
			// Tabletools options: 
			//   https://datatables.net/extensions/tabletools/button_options
			"sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-6 hidden-xs'T>r>"+
					"t"+
					"<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
			"oLanguage": {
				"sSearch": '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>'
			},		
	        "oTableTools": {
	        	 "aButtons": [
	             "copy",
	             "csv",
	             "xls",
	                {
	                    "sExtends": "pdf",
	                    "sTitle": "SmartAdmin_PDF",
	                    "sPdfMessage": "SmartAdmin PDF Export",
	                    "sPdfSize": "letter"
	                },
	             	{
                    	"sExtends": "print",
                    	"sMessage": "Generated by SmartAdmin <i>(press Esc to close)</i>"
                	}
	             ],
	            "sSwfPath": "js/plugin/datatables/swf/copy_csv_xls_pdf.swf"
	        },
			"autoWidth" : true,
			"preDrawCallback" : function() {
				// Initialize the responsive datatables helper once.
				if (!responsiveHelper_datatable_tabletools) {
					responsiveHelper_datatable_tabletools = new ResponsiveDatatablesHelper($('#datatable_tabletools'), breakpointDefinition);
				}
			},
			"rowCallback" : function(nRow) {
				responsiveHelper_datatable_tabletools.createExpandIcon(nRow);
			},
			"drawCallback" : function(oSettings) {
				responsiveHelper_datatable_tabletools.respond();
			}
		});
		
		/* END TABLETOOLS */
	
	})

		

		
	// DO NOT REMOVE : GLOBAL FUNCTIONS!
	
	$(document).ready(function() {
		
		pageSetUp();
		
		// menu
		$("#menu").menu();
	
		/*
		 * AUTO COMPLETE AJAX
		 */
	
		function log(message) {
			$("<div>").text(message).prependTo("#log");
			$("#log").scrollTop(0);
		}
	
		$("#city").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : "http://ws.geonames.org/searchJSON",
					dataType : "jsonp",
					data : {
						featureClass : "P",
						style : "full",
						maxRows : 12,
						name_startsWith : request.term
					},
					success : function(data) {
						response($.map(data.geonames, function(item) {
							return {
								label : item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
								value : item.name
							}
						}));
					}
				});
			},
			minLength : 2,
			select : function(event, ui) {
				log(ui.item ? "Selected: " + ui.item.label : "Nothing selected, input was " + this.value);
			}
		});
	
		/*
		 * Spinners
		 */
		$("#spinner").spinner();
		$("#spinner-decimal").spinner({
			step : 0.01,
			numberFormat : "n"
		});
	
		$("#spinner-currency").spinner({
			min : 5,
			max : 2500,
			step : 25,
			start : 1000,
			numberFormat : "C"
		});
	
		/*
		 * CONVERT DIALOG TITLE TO HTML
		 * REF: http://stackoverflow.com/questions/14488774/using-html-in-a-dialogs-title-in-jquery-ui-1-10
		 */
		$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
			_title : function(title) {
				if (!this.options.title) {
					title.html("&#160;");
				} else {
					title.html(this.options.title);
				}
			}
		}));
	
	
		/*
		* DIALOG SIMPLE
		*/
	
		// Dialog click
		$('#dialog_link1').click(function() {
			$('#dialog_simple1').dialog('open');
			return false;
	
		});


		// Dialog click
		$('#dialog_link2').click(function() {
			$('#dialog_simple2').dialog('open');
			return false;
	
		});


		// Dialog click
		$('#dialog_link3').click(function() {
			$('#dialog_simple3').dialog('open');
			return false;
	
		});


		// Dialog click
		$('#dialog_link4').click(function() {
			$('#dialog_simple4').dialog('open');
			return false;
	
		});
	

		$('#dialog_simple1').dialog({
			autoOpen : false,
			width : 600,
			resizable : false,
			modal : true,
			title : "<div class='widget-header'><h4> 通知設定</h4></div>",
			buttons : [{
				html : "<i class='fa fa-check'></i>&nbsp; 確定",
				"class" : "btn btn-success",
				click : function() {
					$(this).dialog("close");
				}
			}, {
				html : "<i class='fa fa-'></i>&nbsp; 取消",
				"class" : "btn btn-dedaut",
				click : function() {
					$(this).dialog("close");
				}
			}]
		});
	
		$('#dialog_simple2').dialog({
			autoOpen : false,
			width : 600,
			resizable : false,
			modal : true,
			title : "<div class='widget-header'><h4>編輯</h4></div>",
			buttons : [{
				html : "<i class='fa fa-check'></i>&nbsp; 確定",
				"class" : "btn btn-success",
				click : function() {
					$(this).dialog("close");
				}
			}]
		});

		$('#dialog_simple3').dialog({
			autoOpen : false,
			width : 600,
			resizable : false,
			modal : true,
			title : "<div class='widget-header'><h4> 刪除</h4></div>",
			buttons : [{
				html : "<i class='fa fa-check'></i>&nbsp; 確定",
				"class" : "btn btn-success",
				click : function() {
					$(this).dialog("close");
				}
			}, {
				html : "<i class='fa fa-'></i>&nbsp; 取消",
				"class" : "btn btn-dedaut",
				click : function() {
					$(this).dialog("close");
				}
			}]
		});

		$('#dialog_simple4').dialog({
			autoOpen : false,
			width : 600,
			resizable : false,
			modal : true,
			title : "<div class='widget-header'><h4> 刪除</h4></div>",
			buttons : [{
				html : "<i class='fa fa-check'></i>&nbsp; 確定",
				"class" : "btn btn-success",
				click : function() {
					$(this).dialog("close");
				}
			}, {
				html : "<i class='fa fa-'></i>&nbsp; 取消",
				"class" : "btn btn-dedaut",
				click : function() {
					$(this).dialog("close");
				}
			}]
		});
	
		/*
		* DIALOG HEADER ICON
		*/
	
		// Modal Link
		$('#modal_link').click(function() {
			$('#dialog-message').dialog('open');
			return false;
		});
	
		$("#dialog-message").dialog({
			autoOpen : false,
			modal : true,
			title : "<div class='widget-header'><h4><i class='icon-ok'></i> jQuery UI Dialog</h4></div>",
			buttons : [{
				html : "Cancel",
				"class" : "btn btn-default",
				click : function() {
					$(this).dialog("close");
				}
			}, {
				html : "<i class='fa fa-check'></i>&nbsp; OK",
				"class" : "btn btn-primary",
				click : function() {
					$(this).dialog("close");
				}
			}]
	
		});
	
		/*
		 * Remove focus from buttons
		 */
		$('.ui-dialog :button').blur();
	
		/*
		 * Just Tabs
		 */
	
		$('#tabs').tabs();
	
		/*
		 *  Simple tabs adding and removing
		 */
	
		$('#tabs2').tabs();
	
		// Dynamic tabs
		var tabTitle = $("#tab_title"), tabContent = $("#tab_content"), tabTemplate = "<li style='position:relative;'> <span class='air air-top-left delete-tab' style='top:7px; left:7px;'><button class='btn btn-xs font-xs btn-default hover-transparent'><i class='fa fa-times'></i></button></span></span><a href='#{href}'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; #{label}</a></li>", tabCounter = 2;
	
		var tabs = $("#tabs2").tabs();
	
		// modal dialog init: custom buttons and a "close" callback reseting the form inside
		var dialog = $("#addtab").dialog({
			autoOpen : false,
			width : 600,
			resizable : false,
			modal : true,
			buttons : [{
				html : "<i class='fa fa-times'></i>&nbsp; Cancel",
				"class" : "btn btn-default",
				click : function() {
					$(this).dialog("close");
	
				}
			}, {
	
				html : "<i class='fa fa-plus'></i>&nbsp; Add",
				"class" : "btn btn-danger",
				click : function() {
					addTab();
					$(this).dialog("close");
				}
			}]
		});
	
		// addTab form: calls addTab function on submit and closes the dialog
		var form = dialog.find("form").submit(function(event) {
			addTab();
			dialog.dialog("close");
			event.preventDefault();
		});
	
		// actual addTab function: adds new tab using the input from the form above
		function addTab() {
			var label = tabTitle.val() || "Tab " + tabCounter, id = "tabs-" + tabCounter, li = $(tabTemplate.replace(/#\{href\}/g, "#" + id).replace(/#\{label\}/g, label)), tabContentHtml = tabContent.val() || "Tab " + tabCounter + " content.";
	
			tabs.find(".ui-tabs-nav").append(li);
			tabs.append("<div id='" + id + "'><p>" + tabContentHtml + "</p></div>");
			tabs.tabs("refresh");
			tabCounter++;
	
			// clear fields
			$("#tab_title").val("");
			$("#tab_content").val("");
		}
	
		// addTab button: just opens the dialog
		$("#add_tab").button().click(function() {
			dialog.dialog("open");
		});
	
		// close icon: removing the tab on click
		$("#tabs2").on("click", 'span.delete-tab', function() {
	
			var panelId = $(this).closest("li").remove().attr("aria-controls");
			$("#" + panelId).remove();
			tabs.tabs("refresh");
		});
	
		/*
		* ACCORDION
		*/
		//jquery accordion
		
	     var accordionIcons = {
	         header: "fa fa-plus",    // custom icon class
	         activeHeader: "fa fa-minus" // custom icon class
	     };
	     
		$("#accordion").accordion({
			autoHeight : false,
			heightStyle : "content",
			collapsible : true,
			animate : 300,
			icons: accordionIcons,
			header : "h4",
		})
	
		/*
		 * PROGRESS BAR
		 */
		$("#progressbar").progressbar({
	     	value: 25,
	     	create: function( event, ui ) {
	     		$(this).removeClass("ui-corner-all").addClass('progress').find(">:first-child").removeClass("ui-corner-left").addClass('progress-bar progress-bar-success');
			}
		});			

	})

})(jQuery)