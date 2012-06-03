<html>
<?php
$db_host = 'localhost';
$db_user = 'mdrive';
$db_pwd = 'mdrive';

$database = 'mdrive';
$table = 'COORDINATES_LOG';


if (!mysql_connect($db_host, $db_user, $db_pwd))
    die("Can't connect to database");

mysql_set_charset('utf8');

if (!mysql_select_db($database))
    die("Can't select database");

// sending query
$result = mysql_query("SELECT resolved_number, total_buildings_left, process_time, street_id, comment, resolved_buildings FROM {$table} where total_buildings_left<>0");
if (!$result) {
    die("Query to show fields from table failed");
}
?>
<head>
  <title>Streets Resolver Progress</title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/> 
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Street Id');
        data.addColumn('number', 'Resolved');
<?php
while($row = mysql_fetch_assoc($result))
{
?>
    data.addRow(['<?php echo $row['street_id']; ?>', <?php echo $row['resolved_number']; ?>]);
<?php
}
?>

        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        chart.draw(data, {width: 1000, height: 240, title: 'Resolver Progress',
                          hAxis: {title: 'Street Id', titleTextStyle: {color: 'black'}},
			  vAxis: {title: 'Resolved', titleTextStyle: {color: 'black'}}
                         });
      }
    </script>
</head>
<body>
  <div id="chart_div"></div>
<?php
mysql_data_seek ($result , 0);
while($row = mysql_fetch_assoc($result))
{ 
	echo $row['street_id'];
	echo '&nbsp;';
	echo $row['comment'];
	echo '&nbsp;';
	echo $row['resolved_buildings'];
	echo '<br/>'; 
}
mysql_free_result($result);
?>
</body>
</html>