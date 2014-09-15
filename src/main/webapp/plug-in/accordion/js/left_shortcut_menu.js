$(function() {
	//easy ui树加载会在文档加载完执行,所以初始化菜单要延迟一秒 by jueyue
	setTimeout(InitLeftMenu,100);

	tabClose();
	tabCloseEven();
	// 释放内存
	$.fn.panel.defaults = $.extend({}, $.fn.panel.defaults, {
		onBeforeDestroy : function() {
			var frame = $('iframe', this);
			if (frame.length > 0) {
				//frame[0].contentWindow.document.write('');
				frame[0].contentWindow.close();
				frame.remove();
			}
			if ($.browser.msie) {
				CollectGarbage();
			}
		}
	});
	
	  $('#maintabs').tabs({ onSelect : function(title) {
	  	rowid="";
	  } });

});

var rowid="";
// 初始化左侧
function InitLeftMenu() {
	$('.easyui-accordion li div').click(function() {
		$('.easyui-accordion li div').removeClass("selected");
		$(this).parent().addClass("selected");
	}).hover(function() {
		$(this).parent().addClass("hover");
	}, function() {
		$(this).parent().removeClass("hover");
	});
    
	//$(".easyui-tree li a").eq(0).trigger("click");
}


function addTab(subtitle, url, icon) {
	// begin author：屈然博 2013-7-12 for：解决firefox 点击一次请求两次的问题
	var progress = $("div.messager-progress");
	if(progress.length){return;}
	// begin author：屈然博 2013-7-12 for：解决firefox 点击一次请求两次的问题
	rowid="";
	$.messager.progress({
		text : '页面加载中....',
		interval : 200
	});
	if (!$('#maintabs').tabs('exists', subtitle)) {
		//判断是否进行iframe方式打开tab，默认为href方式
		if(url.indexOf('isHref') != -1){
			$('#maintabs').tabs('add', {
				title : subtitle,
				href : url,
				closable : true,
				icon : icon
			});		
		}else{
			$('#maintabs').tabs('add', {
				title : subtitle,
				content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
				closable : true,
				icon : icon
			});		
		}

	} else {
		$('#maintabs').tabs('select', subtitle);
		$.messager.progress('close');
	}

	// $('#maintabs').tabs('select',subtitle);
	tabClose();
}

var title_now;
function addLeftOneTab(subtitle, url, icon) {
	rowid="";
	if ($('#maintabs').tabs('exists', title_now)) {
		$('#maintabs').tabs('select', title_now);
			if(title_now!=subtitle)
			{
			addmask();
			$('#maintabs').tabs('update', {
				tab : $('#maintabs').tabs('getSelected'),
				options : {
					title : subtitle,
					href : url,
					cache:false,
					closable : false,
					icon : icon
				}
			});
		}
	} else {
		addmask();
		$('#maintabs').tabs('add', {
			title : subtitle,
			href : url,
			closable : false,
			icon : icon
		});
	}
	if ($.browser.msie) {
		CollectGarbage();
	}
	title_now = subtitle;
	// $('#maintabs').tabs('select',subtitle);
	// tabClose();

}
function addmask() {
	$.messager.progress({
		text : '页面加载中....',
		interval : 100
	});
}
function createFrame(url) {
	var s = '<iframe name="tabiframe" id="tabiframe"  scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:99.5%;overflow-y:hidden;"></iframe>';
	return s;
}

function tabClose() {
	/* 双击关闭TAB选项卡 */
	$(".tabs-inner").dblclick(function() {
		var subtitle = $(this).children(".tabs-closable").text();
		$('#maintabs').tabs('close', subtitle);
	})
	/* 为选项卡绑定右键 */
	$(".tabs-inner").bind('contextmenu', function(e) {
		$('#mm').menu('show', {
			left : e.pageX,
			top : e.pageY
		});

		var subtitle = $(this).children(".tabs-closable").text();

		$('#mm').data("currtab", subtitle);
		// $('#maintabs').tabs('select',subtitle);
		return false;
	});
}
// 绑定右键菜单事件
function tabCloseEven() {
	// 刷新
	$('#mm-tabupdate').click(function() {
		var currTab = $('#maintabs').tabs('getSelected');
		var url = $(currTab.panel('options').content).attr('src');
		$('#maintabs').tabs('update', {
			tab : currTab,
			options : {
				content : createFrame(url)
			}
		})
	})
	// 关闭当前
	$('#mm-tabclose').click(function() {
		var currtab_title = $('#mm').data("currtab");
		$('#maintabs').tabs('close', currtab_title);
	})
	// 全部关闭
	$('#mm-tabcloseall').click(function() {
		$('.tabs-inner span').each(function(i, n) {
			var t = $(n).text();
			$('#maintabs').tabs('close', t);
		});
	});
	// 关闭除当前之外的TAB
	$('#mm-tabcloseother').click(function() {
		$('#mm-tabcloseright').click();
		$('#mm-tabcloseleft').click();
	});
	// 关闭当前右侧的TAB
	$('#mm-tabcloseright').click(function() {
		var nextall = $('.tabs-selected').nextAll();
		if (nextall.length == 0) {
			// msgShow('系统提示','后边没有啦~~','error');
			//alert('后边没有啦~~');
			return false;
		}
		nextall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();
			$('#maintabs').tabs('close', t);
		});
		return false;
	});
	// 关闭当前左侧的TAB
	$('#mm-tabcloseleft').click(function() {
		var prevall = $('.tabs-selected').prevAll();
		if (prevall.length == 0) {
			//alert('到头了，前边没有啦~~');
			return false;
		}
		prevall.each(function(i, n) {
			var t = $('a:eq(0) span', $(n)).text();

            if (t != '首页') {
                $('#maintabs').tabs('close', t);
            }

		});
		return false;
	});

	// 退出
	$("#mm-exit").click(function() {
		$('#mm').menu('hide');
	});
}

$.parser.onComplete = function() {/* 页面所有easyui组件渲染成功后，隐藏等待信息 */
	if ($.browser.msie && $.browser.version < 7) {/* 解决IE6的PNG背景不透明BUG */
	}
	window.setTimeout(function() {
		$.messager.progress('close');
	}, 200);
};
