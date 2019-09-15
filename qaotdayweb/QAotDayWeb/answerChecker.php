<?php
	function isCorrect($questID, $answer){
		$query = "SELECT Answer FROM QuestionAnswers WHERE questionID='".$questID."'";
		$result = mysql_query($query) or die(mysql_error());
		$answers = explode(" ",$answer);
		while($row=mysql_fetch_array($result)){
			if(in_array(strtoupper($row['Answer']), $answers)){
				return "true";
			} elseif ($answer==strtoupper($row['Answer'])){
				return "true";
			}
		}
		return "false";
	}
	
	function insertAnswerGetRank($questID,$answer,$userID,$correct){
		$query = "SELECT * FROM Answers WHERE QuestionID='".$questID."' AND isCorrect=1";
		$resultList = mysql_query($query);
		$resultCount = mysql_num_rows($resultList);
		echo $userID;
		$isCorrect=0;
		$rank = 0;
		if ($correct=="true") {
			$isCorrect = 1;
			$resultCount++;
			$rank = $resultCount;
		}
		$todaysDate = date("y/m/d , H:i:s", time());
		$strArray = explode(" , ",$todaysDate);
		$query = "INSERT INTO Answers (Answer,QuestionID,an_userID,Date,Time,isCorrect,Rank) VALUES ('"
			.$answer."', '".$questID."', '".$userID."', '".$strArray[0]."', '".$strArray[1]."', '".
			$isCorrect."', '".$rank."')";
		$result = mysql_query($query);
		return $resultCount;
	}
?>