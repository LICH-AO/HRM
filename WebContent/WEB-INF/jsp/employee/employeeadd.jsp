<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<title>人事管理系统——添加员工</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
	<meta http-equiv="description" content="This is my page" />
	<link href="${ctx}/css/css.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${ctx}/js/ligerUI/skins/Aqua/css/ligerui-dialog.css"/>
	<link href="${ctx}/js/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${ctx }/js/jquery-1.11.0.js"></script>
    <script type="text/javascript" src="${ctx }/js/jquery-migrate-1.2.1.js"></script>
	<script src="${ctx}/js/ligerUI/js/core/base.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDrag.js" type="text/javascript"></script> 
	<script src="${ctx}/js/ligerUI/js/plugins/ligerDialog.js" type="text/javascript"></script>
	<script src="${ctx}/js/ligerUI/js/plugins/ligerResizable.jss" type="text/javascript"></script>
	<link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />
	<script language="javascript" type="text/javascript" src="${ctx }/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript">
	
	 
	    $(function()
	    {
			$("#cardId").blur(function(){
				
				//异步请求
				$.ajax("${ctx}/getcardid.action",// 发送请求的URL字符串。
					{
						dataType : "json", // 预期服务器返回的数据类型。
			   			type : "post", //  请求方式 POST或GET
					    contentType:"application/json", //  发送信息至服务器时的内容编码类型
					    // 发送到服务器的数据。
					    data:JSON.stringify({cardId : $("#cardId").val()}),
					    async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
					    // 请求成功后的回调函数。
					   success :function(data){
						   if(!data)
						   {
							   $("#cardmsgid").html('身份证已经存在，请重新输入！');
						   }else
						   {
							   $("#cardmsgid").html('');
						   }
					   }//,
					   // 请求出错时调用的函数
					  // error:function(){
					//	   alert("数据发送失败");
					 //  }
				});
			});
			
	    	/** 员工表单提交 */
			$("#employeeForm").submit(function(){
				var name = $("#name");
				var cardId = $("#cardId");
				var sex = $("sex").val;
				var education = $("#education");
				var email = $("#email");
				var phone = $("#phone");
				var tel = $("#tel");
				var party = $("#party");
				var qqNum = $("#qqNum");
				var address = $("#address");
				var postCode = $("#postCode");
				var birthday = $("#birthday");
				var race = $("#race");
				var speciality = $("#speciality");
				var hobby = $("#hobby");
				var msg = "";
				if ($.trim(name.val()) == ""){
					msg = "姓名不能为空！";
					name.focus();
				}else if ($.trim(cardId.val()) == ""){
					msg = "身份证号码不能为空！";
					cardId.focus();
				}/**else if (!/^[1-9]\d{16}[0-9A-Za-z]$/.test($.trim(cardId.val()))){
					msg = "身份证号码格式不正确！";
					cardId.focus();
				}*/else if($.trim(sex) == ""){
					msg = "性别不能为空！";
					sex.focus();
				}
				else if ($.trim(education.val()) == ""){
					msg = "学历不能为空！";
					education.focus();
				}else if ($.trim(email.val()) == ""){
					msg = "邮箱不能为空！";
					email.focus();
				}else if (!/^\w+@\w{2,3}\.\w{2,6}$/.test($.trim(email.val()))){
					msg = "邮箱格式不正确！";
					email.focus();
				}else if ($.trim(phone.val()) == ""){
					msg = "手机号码不能为空！";
					phone.focus();
				}/**else if (!/^1[3|5|8]\d{9}$/.test($.trim(phone.val()))){
					msg = "手机号码格式不正确！";
					phone.focus();
				}*/else if ($.trim(tel.val()) == ""){
					msg = "电话号码不能为空！";
					tel.focus();
				}/**else if (!/^0\d{2,3}-?\d{7,8}$/.test($.trim(tel.val()))){
					msg = "电话号码格式不正确！";
					tel.focus();
				}*/else if ($.trim(party.val()) == ""){
					msg = "政治面貌不能为空！";
					party.focus();
				}else if ($.trim(qqNum.val()) == ""){
					msg = "QQ号码不能为空！";
					qqNum.focus();
				}/**else if (!/^\d{6,}$/.test($.trim(qqNum.val()))){
					msg = "QQ号码格式不正确！";
					qqNum.focus();
				}*/else if ($.trim(address.val()) == ""){
					msg = "地址不能为空！";
					address.focus();
				}else if ($.trim(postCode.val()) == ""){
					msg = "邮政编码不能为空！";
					postCode.focus();
				}/**else if (!/^[1-9]\d{5}$/.test($.trim(postCode.val()))){
					msg = "邮政编码格式不正确！";
					postCode.focus();
				}*/else if ($.trim(birthday.val()) == ""){
					msg = "出生日期不能为空！";
					birthday.focus();
				}/**else if (!birthday.val()){
 					!/^\d{4}-\d{2}-\d{2}$/.test($.trim(birthday.val()))
					msg = "出生日期格式不正确！";
					birthday.focus();
				}*/else if ($.trim(race.val()) == ""){
					msg = "民族不能为空！";
					race.focus();
				}else if ($.trim(speciality.val()) == ""){
					msg = "专业不能为空！";
					speciality.focus();
				}else if ($.trim(hobby.val()) == ""){
					msg = "爱好不能为空！";
					hobby.focus();
				}
				if (msg != ""){
					$.ligerDialog.error(msg);
					return false;
				}else{
					return true;
				}
				$("#employeeForm").submit();
			});
	    });
		

	</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr><td height="10"></td></tr>
  <tr>
    <td width="15" height="32"><img src="${ctx}/images/main_locleft.gif" width="15" height="32"></td>
	<td class="main_locbg font2"><img src="${ctx}/images/pointer.gif">&nbsp;&nbsp;&nbsp;当前位置：员工管理  &gt; ${txt }</td>
	<td width="15" height="32"><img src="${ctx}/images/main_locright.gif" width="15" height="32"></td>
  </tr>
</table>

<table width="100%" height="90%" border="0" cellpadding="5" cellspacing="0" class="main_tabbor">
  <tr valign="top">
    <td>
    	 <form action="${ctx}/employeeadd.atcion" id="employeeForm" method="post">
		 	<!-- 隐藏表单，flag表示添加标记 -->
		 	<input name="userName" type="hidden" value="${user_session.username}">
    	 	<input type="hidden" name="flag" value="2">
    	 	<input type="hidden" name="id" value="${employee.id}"/>
		  <table width="100%" border="0" cellpadding="0" cellspacing="10" class="main_tab">
		    <tr><td class="font3 fftd">
		    	<table>
		    		<tr>
		    			<td class="font3 fftd">姓名：<input type="text" name="name" id="name" value="${employee.name }" size="20"/></td>
		    			<td class="font3 fftd">身份证号码：<input type="text" name="cardId" id="cardId" value="${employee.cardId }" size="20" /><span id="cardmsgid" style="color:#F00"></span></td>
		    		</tr>
		    		<tr>
		    			<td class="font3 fftd">性别：
									<select name="sex" style="width:143px;" id="sex">
					    			<option value="0">--请选择性别--</option>
					    			<c:choose>
						    			<c:when test="${employee.sex == '1' }">
						    				<option value="1" selected="selected">男</option>
						    				<option value="2" >女</option>
						    			</c:when>
						    			<c:otherwise>
						    			<option value="1" >男</option>
						    				<option value="2" selected="selected">女</option>
						    			</c:otherwise>
					    			</c:choose>
					    		</select></td>
		    			<td class="font3 fftd">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：
		    			 <select name="job_id" style="width:143px;">
					    			<option value="0">--请选择职位--</option>
					    			<c:forEach items="${requestScope.jobList}" var="job">
					    			<c:choose>
					    				<c:when test="${job.id == '3'}">
					    					<option value="${job.id}"  selected="selected">${job.name}</option>
					    				</c:when>
					    				<c:otherwise>
					    				<option value="${job.id}">${job.name}</option>
					    				</c:otherwise>
					    			</c:choose>
					    			</c:forEach>
					    		</select>
					    </td>
		    		</tr>
		    		<tr>
		    			<td class="font3 fftd">学历：<input name="education" id="education" value="${employee.education }" size="20"/></td>
		    			<td class="font3 fftd">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱：<input name="email" value="${employee.email }" id="email" size="20"/></td>
		    		</tr>
		    		<tr>
		    			<td class="font3 fftd">手机：<input name="phone" id="phone" size="20" value="${employee.phone }"/></td>
		    			<td class="font3 fftd">电&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;话：<input name="tel" value="${employee.tel }" id="tel" size="20"/></td>
		    		</tr>
		    		
		    	</table>
		    </td></tr>
			<tr><td class="main_tdbor"></td></tr>
			
			<tr>
				<td class="font3 fftd">
					政治面貌：<input name="party" id="party" size="40" value="${employee.party }"/>&nbsp;&nbsp;
					QQ&nbsp;&nbsp;号码：<input name="qqNum" id="qqNum" size="20" value="${employee.qqNum }"/>
				</td>
			</tr>
			<tr><td class="main_tdbor"></td></tr>
			
			<tr>
				<td class="font3 fftd">
					联系地址：<input name="address" id="address" size="40" value="${employee.address }"/>&nbsp;&nbsp;
					邮政编码：<input name="postCode" id="postCode" size="20" value="${employee.postCode }"/>
				</td>
			</tr>
			<tr><td class="main_tdbor"></td></tr>
			
			<tr>
				<td class="font3 fftd">
				<!-- onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'}); -->
					出生日期：<input type="date"  cssClass="Wdate"  
					name="birthday" id="birthday" value="${employee.birthday }" size="40"/>&nbsp;&nbsp;
					民&nbsp;&nbsp;&nbsp;&nbsp;族：<input name="race" id="race" size="20" value="${employee.race }"/>
				</td>
			</tr>
			<tr><td class="main_tdbor"></td></tr>
			
			<tr>
				<td class="font3 fftd">
					所学专业：<input  name="speciality" id="speciality" value="${employee.speciality }" size="40"/>&nbsp;&nbsp;
					爱&nbsp;&nbsp;&nbsp;&nbsp;好：<input name="hobby" id="hobby" size="20" value="${employee.hobby }"/>
				</td>
			</tr>
			<tr><td class="main_tdbor"></td></tr>
			
			<tr>
				<td class="font3 fftd">
					备&nbsp;&nbsp;&nbsp;&nbsp;注：<input name="remark" id="remark" value="${employee.remark }" size="40"/>
					&nbsp;&nbsp;所属部门：
					<select  name="dept_id" style="width:100px;">
						   <option value="0">--部门选择--</option>
						   <c:forEach items="${requestScope.deptList}" var="dept">
						 	  <c:choose>
			    				<c:when test="${employee.dept.name == dept.name}">
			    					<option value="${dept.id}" selected="selected">${dept.name}</option>
			    				</c:when>
			    				<c:otherwise>
			    					<option value="${dept.id }">${dept.name }</option>
			    				</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
					</select>
				</td>
			</tr>
			<tr><td class="main_tdbor"></td></tr>
			
			<tr><td align="left" class="fftd">
			<input type="submit" value="${sub }" name="submit">&nbsp;&nbsp;<input type="reset" value="取消 "></td></tr>
		  </table>
		 </form>
	</td>
  </tr>
</table>
<div style="height:10px;"></div>
</body>
</html>