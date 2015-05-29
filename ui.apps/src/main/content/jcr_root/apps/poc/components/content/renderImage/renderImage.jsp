<%@include file="/apps/poc/global.jsp" %>
<c:set var="imagePath" value="<%= request.getParameter("damImagePath")%>"/>

<a href="${imagePath}">
    <img class="img-responsive" src="${imagePath}" />
</a>