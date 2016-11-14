<%@include file="../common/admin_head.jsp"%>
<s:if test="@com.eportal.util.Tools@isDisable(#session.admin,10)">
	<jsp:forward page="/admin/error.htm"/>
</s:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<title></title>
</head>
<frameset rows="70,*" frameborder="no" border="0" framespacing="0">
  <frame src="browsePV_top.jsp" name="pvTopFrame" scrolling="no" noresize="noresize" id="pvTopFrame" />
  <frame src="traffic_browsePV.action" name="pvMainFrame" id="pvMainFrame" />
</frameset>
<noframes><body>
</body>
</noframes></html>