<%@taglib prefix="s" uri="/struts-tags"%>
<s:if test="#session.admin==null">
	<jsp:forward page="/admin/login.jsp"/>
</s:if>