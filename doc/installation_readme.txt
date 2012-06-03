Apache Tomcat
add URIEncoding="UTF-8" to conf/server.xml  <Connector> tag
otherwise russian will not be shown correct on page

Add to application start parameters:
-Dfile.encoding=UTF-8
this is for DBUnit, there is a bug, when it loads CSV files and OS charset is not UTF-8 (fixed in 2.4.9 version)