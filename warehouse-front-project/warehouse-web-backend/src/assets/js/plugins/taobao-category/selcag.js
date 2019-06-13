$(function(){
	// 初始化
	function cagInit() {
		var cpcCagId = $("input[name='categoryId']").val();
		
		if(cpcCagId != 0 && cpcCagId != ""){
			$.ajax({
				url : "/category/categorySelectById?id="+cpcCagId,
				dataType : "json",
				type : "get",
				success: function(data) {
					if(data.ret.success){
						var object = data.ret.result.category;
						var level1cid = object.categoryLevel1Id,
						level2cid = object.categoryLevel2Id,
						level3cid = object.categoryLevel3Id,
						level4cid = object.categoryLevel4Id;

						$("#cc-tree-group"+level1cid).addClass("cc-tree-expanded");
						selAct($("#cc-tree-item"+level2cid),2,level2cid,function(){
							if(level3cid) {
								selAct($("#cc-cbox-item"+level3cid),3,level3cid,function(){
									if(level4cid) {
										$("#cc-cbox-item"+level4cid).addClass("cc-selected");
										$("input[categoryId]").val(level4cid);
									}
								});
							}
						});

						// 当前选中
						var level1name = object.categoryLevel1Name,
						level2name = object.categoryLevel2Name,
						level3name = object.categoryLevel3Name,
						level4name = object.categoryLevel4Name;
						if(level4name) {
							$(".cate-wrap").after('<div class="curCate-box">当前选中：<em>'+level1name+'</em> &gt; <em>'+level2name+'</em> &gt; <em>'+level3name+'</em>&gt; <em>'+level4name+'</em></div>');
						} else if(level3name) {
							$(".cate-wrap").after('<div class="curCate-box">当前选中：<em>'+level1name+'</em> &gt; <em>'+level2name+'</em> &gt; <em>'+level3name+'</em></div>');
						} else {
							$(".cate-wrap").after('<div class="curCate-box">当前选中：<em>'+level1name+'</em> &gt; <em>'+level2name+'</em></div>');	
						}
					}else{
						alert(data.ret.resultDes);
					}

				}
			});
		}
	}

	// 类目根目录
	$.ajax({
		url : "/category/categoryRoot",
		dataType : "json",
		type : "get",
		success: function(data) {
			if(data.ret.success){
				var object = data.ret.result.data;
				var modHtml = '';
				$.each(object, function(index, val) {
					modHtml +='<li id="cc-tree-group'+val.id+'" class="cc-tree-group">';
					modHtml +='<div class="cc-tree-gname">'+val.name+'</div>';
					modHtml +='	<ul class="cc-tree-gcont">';
					var item = val.has_child == 1 ? 'class="cc-tree-item cc-hasChild-item"':'class="cc-tree-item"';
					$.each(val.data, function(index, val) {
					modHtml +='<li id="cc-tree-item'+val.id+'" data-id="'+val.id+'" '+item+'>'+val.name+'</li>';
					});
					modHtml +='</ul>';
					modHtml +='</li>';
				});
			    $(".cc-tree-cont").html(modHtml);
			    $(".cc-list-item:first").show();
			    $(".cc-tree .cc-tree-gname").click(function(){
			    	var $this = $(this);
			    	$this.parent().toggleClass("cc-tree-expanded");
			    });
			    // 初始化
			    cagInit();
			}else{
			    alert(data.ret.resultDes);
			}
			
		}
	});

	function selAct(o, level, id, callback) {
		var cpcLevelId = $("input[categoryLevel]");
		var $this = o;
		$(".cc-list-item").eq(level-2).find(".cc-selected").removeClass("cc-selected");
		$this.addClass("cc-selected");
		if($this.hasClass("cc-hasChild-item")) {
			$(".cc-list-item").eq(level-1).show();
			$("input[name='categoryId']").val("");
			if($this.data("clist")) {
				$(".cc-cbox-cont").eq(level-2).html($this.data("clist"));
			} else {
				$("input[name='categoryLevel']").val(level);
				$.ajax({
					url : "/category/subCategorySelect?id="+id+"&level="+level,
					dataType : "json",
					type : "get",
					success: function(data) {
						if(data.ret.success){
							var object = data.ret.result.cateList;
							var cboxHtml = '';
							$.each(object, function(index, val) {
								var item = val.has_child == 1 ? 'class="cc-cbox-item cc-hasChild-item"':'class="cc-cbox-item"';
								cboxHtml+='<li id="cc-cbox-item'+val.id+'" data-id="'+val.id+'" '+item+'>'+val.name+'</li>';
							});
							$(".cc-cbox-cont").eq(level-2).html(cboxHtml);
							$this.data("clist",cboxHtml);
							if(callback) {
								callback();
							}
						}else{
						    alert(data.ret.resultDes);
						}
					}
				});
			}
		}else{
			$("input[name='categoryId']").val(id);
			$("input[name='categoryLevel']").val(level);
		}
	}

	// 二级、三级列表点击事件
	$(".cc-tree-item, .cc-cbox:first .cc-cbox-item").live("click", function() {
		var $this = $(this),
		itemId = $this.data("id"),
		parUl = $this.parent(),
		level = 2;
		if(parUl.hasClass("cc-cbox-cont")) {
			level = 3;
		}else{
			$(".cc-list-item").eq(1).hide();
		}
		$(".cc-list-item:last").hide();
		if(!$this.hasClass("cc-selected")) {
			selAct($this,level,itemId);
		}
		$("input[name='categoryId']").val(itemId);
	});

	// 四级列表
	$(".cc-cbox:last .cc-cbox-item").live("click", function() {
		var $this = $(this),
		cId = $this.data("id");
		if(!$this.hasClass("cc-selected")) {
			$(".cc-cbox:last .cc-selected").removeClass("cc-selected");
			$this.addClass("cc-selected");
			$("input[name='categoryId']").val(cId);
			$("input[name='categoryLevel']").val('4');
		}
	});
});