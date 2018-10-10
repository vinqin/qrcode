<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>二维码解析器</title>
</head>
<body>

<form action="decode" enctype="multipart/form-data" method="post">
    <input type="file" name="file" value="选择图片"/>
    <input type="submit" value="上传"/>
</form>
<hr>

<c:if test="${!(empty sessionScope.MSG)}">
    <script lang="javascript">
        alert("${sessionScope.MSG}");
    </script>
    <%
        session.removeAttribute("MSG");
    %>
</c:if>

<c:if test="${!(empty sessionScope.result)}">
    文件解码结果：<br>
    ${sessionScope.result}
    <%
        session.removeAttribute("result");
    %>
</c:if>

</body>
</html>
