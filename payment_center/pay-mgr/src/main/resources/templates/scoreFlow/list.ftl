<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>转账订单</title>
		<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="../css/global.css" media="all">
		<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="../css/table.css" />
	</head>

	<body>
		<div class="admin-main">

			<fieldset class="layui-elem-field">
				<legend>订单列表</legend>
				<div class="layui-field-box layui-form">
					<table class="layui-table admin-table">
						<thead>
							<tr>
                                <th>奖励总额</th>
                                <th>用户ID</th>
                                <th>操作</th>
							</tr>
						</thead>
						<tbody id="content">
						</tbody>
					</table>
				</div>
			</fieldset>
			<div class="admin-table-page">
				<div id="paged" class="page">
				</div>
			</div>
		</div>
		<!--模板-->
		<script type="text/html" id="tpl">
			{{# layui.each(d.list, function(index, item){ }}
			<tr>
                <td>{{ item.currentDealScore }}</td>
                <td>{{ item.userId }}</td>
                <td><button data-currentDealScore="{{ item.currentDealScore }}" data-userId="{{ item.userId }}"   class="layui-btn countExchanage">一键兑换</button></td>
			</tr>
			{{# }); }}
		</script>
		<script type="text/javascript" src="../plugins/layui/layui.js"></script>
		<script>
			
			function countExchanage(currentDealScore,userId){
				alert(userId);
				 $.get('exchanage?currentDealScore='+currentDealScore+'&userId='+userId,function(data,status){
				 	if(status == 'success'){
				 		alert("一键兑换成功!");
				 	}
				 });
			}
			
		
			layui.config({
				base: '../js/'
			});

			layui.use(['paging', 'form'], function() {
				var $ = layui.jquery,
					paging = layui.paging(),
					layerTips = parent.layer === undefined ? layui.layer : parent.layer, //获取父窗口的layer对象
					layer = layui.layer, //获取当前窗口的layer对象
					form = layui.form();
				
                paging.init({
                    openWait: true,
					url: '/countExchange/list?v=' + new Date().getTime(), //地址
					elem: '#content', //内容容器
					params: { //发送到服务端的参数
					},
					type: 'GET',
					tempElem: '#tpl', //模块容器
					pageConfig: { //分页参数配置
						elem: '#paged', //分页容器
						pageSize: 10 //分页大小
					},
					success: function() { //渲染成功的回调
						$('.countExchanage').on('click',function(){
							var currentDealScore,userId;
							currentDealScore = $(this).attr('data-currentDealScore');
							userId = $(this).attr('data-userId');
							$.get('exchanage?currentDealScore='+currentDealScore+'&userId='+userId,function(data,status){
							 	if(status == 'success'){
							 		window.location.reload()
							 	}
							 });
						})
					},
					fail: function(msg) { //获取数据失败的回调
						//alert('获取数据失败')
					},
					complate: function() { //完成的回调
						//alert('处理完成');
						//重新渲染复选框
						form.render('checkbox');
						form.on('checkbox(allselector)', function(data) {
							var elem = data.elem;

							$('#content').children('tr').each(function() {
								var $that = $(this);
								//全选或反选
								$that.children('td').eq(0).children('input[type=checkbox]')[0].checked = elem.checked;
								form.render('checkbox');
							});
						});

                        //绑定所有预览按钮事件
                        $('#content').children('tr').each(function() {
                            var $that = $(this);
                            $that.children('td:last-child').children('a[data-opt=view]').on('click', function() {
                                viewForm($(this).data('id'));
                            });
                        });

						//绑定所有编辑按钮事件						
						$('#content').children('tr').each(function() {
                            var $that = $(this);
                            $that.children('td:last-child').children('a[data-opt=trans]').on('click', function() {
                                layer.confirm('确定重新转账么？', {
                                    btn: ['转账','放弃'] //按钮
                                }, function(){
                                    layer.msg('功能还再开发中...', {icon: 1});
                                }, function(){
                                });
                            });
						});

                        //绑定所有删除按钮事件
                        $('#content').children('tr').each(function() {
                            var $that = $(this);
                            $that.children('td:last-child').children('a[data-opt=del]').on('click', function() {
                                layer.msg($(this).data('id'));
                            });
                        });

					},
				});
				//获取所有选择的列
				$('#getSelected').on('click', function() {
					var names = '';
					$('#content').children('tr').each(function() {
						var $that = $(this);
						var $cbx = $that.children('td').eq(0).children('input[type=checkbox]')[0].checked;
						if($cbx) {
							var n = $that.children('td:last-child').children('a[data-opt=edit]').data('name');
							names += n + ',';
						}
					});
					layer.msg('你选择的名称有：' + names);
				});

				$('#search').on('click', function() {
					 $.get('exchanage',function(data,status){
					 	if(status == 'success'){
					 		alert("一键兑换成功!");
					 	}
  					 });
				});

				var addBoxIndex = -1;

				$('#import').on('click', function() {
					var that = this;
					var index = layer.tips('只想提示地精准些', that, { tips: [1, 'white'] });
					$('#layui-layer' + index).children('div.layui-layer-content').css('color', '#000000');
				});
				function viewForm(id) {
                    //本表单通过ajax加载 --以模板的形式，当然你也可以直接写在页面上读取
                    $.get('/trans_order/view.html?transOrderId=' + id, null, function(form) {
                        addBoxIndex = layer.open({
                            type: 1,
                            title: '订单详情',
                            content: form,
                            shade: false,
                            offset: ['100px', '30%'],
                            area: ['600px', '550px'],
                            zIndex: 19950924,
                            maxmin: false,
                            full: function(elem) {
                                var win = window.top === window.self ? window : parent.window;
                                $(win).on('resize', function() {
                                    var $this = $(this);
                                    elem.width($this.width()).height($this.height()).css({
                                        top: 0,
                                        left: 0
                                    });
                                    elem.children('div.layui-layer-content').height($this.height() - 95);
                                });
                            },
                            end: function() {
                                addBoxIndex = -1;
                            }
                        });
                        layer.full(addBoxIndex);
                    });
				}
				
			});
		</script>
	</body>

</html>