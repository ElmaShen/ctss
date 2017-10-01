(function($) {

	// alert($(".unread-announce").length);
	$(".unread-announce").on("click","tr",function(event) {
		var tr = $(event.target).closest('tr');
		// alert("test"+tr.find("input").length);
		// alert("test="+tr.length);
		var messageId = tr.data('messageid');

		// alert(messageId);
		jQuery.get('../api/admin/announcement/'+messageId, {}, function(data, textStatus, xhr) {
		  //optional stuff to do after success
			var source   = $("#tpl_asi").html();
			var template = Handlebars.compile(source);

			var data2 = {
				announcementContent : data.message.announcementContent
			}
			var html = template(data2);

			$("#asi").html(html);
		});
		

	});

	$(".system_announce").on("click",".read-button",function() {

		// alert("hello world!");

		var chs = $(".system_announce").find("input[type='checkbox']:checked");

		var ids = [];
		for (var i = 0; i < chs.length; i++) {
			ids.push($(chs[i]).data('messageid'));
		}

		if (!ids)
			return false;

		jQuery.ajax({
		  url: '../api/admin/announcement/updateRead',
		  type: 'POST',
		  dataType: 'json',
		  contentType: "application/json; charset=utf-8",
		  data: JSON.stringify({ids: ids}),
		  complete: function(xhr, textStatus) {
		    //called when complete
		  },
		  success: function(data, textStatus, xhr) {
		    //called when successful
		    window.location.reload();
		  },
		  error: function(xhr, textStatus, errorThrown) {
		    //called when there is an error
		  }
		});
		
	})
})(jQuery);