<?php
	include 'query_table.php';

	#$mydb = mysql_connect("localhost", "root", "Qa071576") or die(mysql_error());
	#mysql_select_db("fastqs") or die(mysql_error());
	#when I call this class  I am already connected to a db.
	$returnVal = array();
  $query = "SELECT * FROM Achievements";
	$achievements = good_query_table($query, 0);
	$returnVal['achievements'] = $achievements;
	$query = "SELECT * FROM UserAchievements ORDER BY AchievementID";
	$userAchievements = good_query_table($query);
	$returnVal['user'] =  $userAchievements;
	echo json_encode($returnVal);
?>