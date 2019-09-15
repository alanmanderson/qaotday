<?php
	$mydb = mysql_connect("localhost", "root", "Qa071576") or die(mysql_error());
	mysql_select_db("fastqs") or die(mysql_error());
	
	$question=$_GET['question'];
	$topicID = 2;
    $query="INSERT INTO Questions (Question, TopicID) VALUES ('".$question."','".$topicID."')";
    $result = mysql_query($query);
    echo("query:   ".$query."    result:  ".$result);
?>