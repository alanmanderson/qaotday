<?php
	
	$mydb = mysql_connect("localhost", "root", "Qa071576") or die(mysql_error());
	mysql_select_db("fastqs") or die(mysql_error());
	
	$id = $_GET['id'];
	$query = "SELECT Answer FROM QuestionAnswers WHERE questionID='".$id."'";
	$result = mysql_query($query);
	echo "<th>Answers</th>";
	$counter = 0;
	while($row=mysql_fetch_array($result)){
		if ($counter % 2==0){
			echo "<tr><td>".$row['Answer']."</td></tr>";
		} else {
			echo "<tr class=\"alt\"><td>".$row['Answer']."</td></tr>";
		}
		
		$counter++;
	}
?>