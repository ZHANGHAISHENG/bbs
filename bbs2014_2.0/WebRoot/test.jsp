<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI 折叠面板（Accordion） - 折叠内容</title>
  <link rel="stylesheet" href="jquery-ui-1.11.2/jquery-ui.min.css">
  <script src="jquery2.0/jquery-2.0.0.min.js"></script>
  <script src="jquery-ui-1.11.2/jquery-ui.min.js"></script>

  <script>
  $(function() {
    $( "#accordion" ).accordion({
      collapsible: true
    });
  });
  </script>
</head>
<body>
 
<div id="accordion">
  <h3>部分 1</h3>
  <div>
    <p>Mauris mauris ante, blandit et, ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. Curabitur malesuada. Vestibulum a velit eu ante scelerisque vulputate.</p>
  </div>

</div>
 
 
</body>
</html>	