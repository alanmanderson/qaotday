<?php
	include 'query_table.php';
	define("TOTAL",0);
	define("CORRECT",1);
	define("RANK",2);
	$userID = getUserID($_GET['username']);
	$qryFields = "Answers.*, Topics.Topic";
	$qryTable = "Answers LEFT JOIN Questions ON Answers.QuestionID=Questions.ID ".
						  "INNER JOIN Topics ON Questions.TopicID=Topics.ID";
	#$qryTable = "Answers";
	#$qryFields = "*";
	$qryCondition = "an_userID='".$userID."'";
	#$query = "SELECT * FROM Answers WHERE username='".$un."'";
	$query = "SELECT ".$qryFields." FROM ".$qryTable." WHERE ".$qryCondition;
	$result = mysql_query($query) or die(mysql_error());
	$totalAnswers = mysql_num_rows($result);
	$totalCorrectAnswers = 0;
	$totalRankSum=0;
	$stats=Array();
	while($row=mysql_fetch_array($result)){
		$topic = $row['Topic'];
		if (!$stats[$topic]){
			$stats[$topic] = Array(1,0,0);
		} else {
			$stats[$topic][TOTAL]=$stats[$topic][TOTAL]+1;
		}
		if ($row['isCorrect']==1){
			$stats[$topic][CORRECT]=$stats[$topic][CORRECT]+1;
			$stats[$topic][RANK]=$stats[$topic][RANK]+$row['Rank'];
			$totalCorrectAnswers++;
			$totalRankSum = $totalRankSum+$row['Rank'];
		}
	}
	foreach ($stats as $topic => $arr) {
		if ($stats[$topic][CORRECT]!=0){
			$stats[$topic][RANK] = $stats[$topic][RANK]/$stats[$topic][CORRECT];
		}
	}
	$stats['Total'] = Array($totalAnswers, $totalCorrectAnswers, 0);
	if ($totalCorrectAnswers!=0){
		$stats['Total'][RANK] = $totalRankSum / $totalCorrectAnswers;
	}
	echo json_encode($stats);
	
	
	#echo($totalAnswers.":".$totalCorrectAnswers.":");
	#if($totalCorrectAnswers!=0){
	#	echo($rankSum/$correctAnswers);
	#} else {
	#	echo 0;
	#}
?>