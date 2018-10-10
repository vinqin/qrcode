<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>二维码生成器</title>
</head>
<body>

<form action="encode" method="post">

    <textarea name="content" clos=",20" rows="30" warp="virtual" style="width:700px; height:300px;"
              autocomplete="off" placeholder="二维码内容"></textarea> <br>
    <table cellspacing="15">
        <th>输出格式</th>
        <th>纠错级别</th>
        <tr>
            <td>
                <select name="format">
                    <option value="PNG">PNG</option>
                    <option value="JPEG">JPEG</option>
                    <option value="GIF">GIF</option>
                </select>
            </td>

            <td>
                <select name="valid">
                    <option value="L">L 7%</option>
                    <option value="M">M 15%</option>
                    <option value="Q">Q 25%</option>
                    <option value="H">H 30%</option>
                </select>
            </td>
        </tr>
    </table>
    <br>
    <br>
    <input type="submit" value="生成QR码">
</form>

</body>
</html>
