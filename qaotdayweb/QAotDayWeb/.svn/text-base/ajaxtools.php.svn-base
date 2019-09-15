<?php 
	#display_errors = On;
	#error_reporting = E_ALL & ~E_NOTICE;

	header("Content-type: text/html; charset=ISO-8859-1");
	$mydb = mysql_connect("localhost", "root", "Qa071576") or die(mysql_error());

	mysql_select_db("fastqs") or die(mysql_error());

	function getUserID($username){
		$query = "SELECT * FROM Users WHERE Username='".$username."'";
		$result = mysql_query($query) or die(mysql_error());
		$row = mysql_fetch_array($result);
		return $row['ID'];
	}
	
	$cmd = $_GET['cmd'];
	if ($cmd=='register'){
		$first = $_GET['first'];
		$last = $_GET['last'];
		$un = $_GET['un'];
		$pw = $_GET['pw'];
		$address = $_GET['address'];
		$city = $_GET['city'];
		$state = $_GET['state'];
		$zipcode = $_GET['zipcode'];
		$email = $_GET['email'];
		$update = $_GET['update'];
		
	  $query = "SELECT Username FROM Users WHERE Username='".$un."'";
		$result = mysql_query($query);
		$unExists = mysql_num_rows($result);
		if ($update=='true' && $un==$_GET['oldUsername']){
			
		} elseif($unExists){
			echo("username_in_use");
			exit;
		}

		if ($update=='false'){
		   $query = "INSERT INTO Users (First,Last,Username,Password,Address,".
		   "City,State,ZipCode,email) VALUES ('".$first."', '".$last."', '".$un."', '".$pw."', '".
			$address."', '".$city."', '".$state."', '".$zipcode."', '".$email."')";
	  	   $response = mysql_query($query);
		} else {
		   $oldUN = $_GET['oldUsername'];
		   $query = "UPDATE Users SET First='".$first."', Last='".$last."', Username='".$un.
		   	  "', Password='".$pw."', Address='".$address."', City='".$city."', State='".
			  $state."', ZipCode='".$zipcode."', email='".$email."' WHERE Username='".$oldUN."'";
		   $response = mysql_query($query);
		   if ($response){
		      echo("OK");
		   } else {
		      echo("ERROR");
		   }
		}
	} elseif ($cmd=='hasCorrectlyAnswered'){
		$username = $_GET['un'];
		$questID = $_GET['questID'];
		
		$userID = $getUserID($username);
		
		$query = "SELECT * FROM Answers WHERE an_userID='".$userID."' AND QuestionID='".$questID."' AND isCorrect=1";
		$result = mysql_query($query);
		if (mysql_num_rows($result)>0){
			echo("true");
		} else {
			echo("false");
		}
	} elseif ($cmd=='answer'){
	  include 'answerChecker.php';
		$questID = $_GET['questID'];
		$answer = strtoupper($_GET['answer']);
		$userID = getUserID($_GET['un']);
		$correct = isCorrect($questID, $answer);
		$resultCount = insertAnswerGetRank($questID,$answer,$userID,$correct);
		echo($resultCount.":".$correct);
	} elseif ($cmd=='isQuestionToday') {
		$topic = $_GET['topic'];
		$query = "Select ID FROM Topics WHERE Topic='".$topic."'";
		$resultList = mysql_query($query);
		$row = mysql_fetch_array($resultList);
		$topic = $row['ID'];
	
		$todaysDate = date("Y/m/d");
		$query = "Select ID,Question FROM Questions WHERE Date='".$todaysDate."' AND TopicID='".$topic."'";
		
		$resultList = mysql_query($query);
		$recordCount = mysql_num_rows($resultList);
		if ($recordCount==0){
			echo("nil");
		} else {
			$row = mysql_fetch_array($resultList);
			echo($row['ID'].":".$row['Question']);
		}
	} elseif ($cmd=='getTopics'){
	  #	$which = $_GET['which'];
		#if ($which=='unreged'){
		#} elseif($which=='reged'){
		#} elseif($which=='all'){
		#} 
		$json = array();
		$i = 0;
		
		$query = "SELECT Topic FROM Topics";
		$resultList = mysql_query($query);
		while ($row = mysql_fetch_array($resultList)){
			$json[$i] = $row['Topic'];
			$i=$i+1;
		}
		echo(implode($json,","));
	} elseif($cmd=='getUserInfo') {
		$username = $_GET['un'];
		$query = "SELECT * FROM Users WHERE Username='".$username."'";
		$result = mysql_query($query);
		$row = mysql_fetch_array($result);
		echo($row['First'].":".$row['Last'].":".$row['Username'].":".$row['Password'].":".$row['Address'].":".$row['City'].
			":".$row['State'].":".$row['ZipCode'].":".$row['email']);
	} elseif($cmd=='setTopics') {
		$userID = getUserID($_GET['un']);
		$topics = $_GET['topics'];
		$query = "DELETE FROM UserTopics WHERE ut_userID='".$userID."'";
		$result = mysql_query($query);
		foreach($topics as $topic){
			$query = "INSERT INTO UserTopics (username,Topic) VALUES ('".$userID."','".$topic."')";
			$result = mysql_query($query);
		}
		echo("Ok");
	} elseif($cmd=='test'){
		echo("test successful");
		$question=$_GET['question'];
		$topicID = 2;
        $query="INSERT INTO Questions (Question, TopicID) VALUES ('".$question."','".$topicID."')";
        $result = mysql_query($query);
        echo($query." ".$result);
	} elseif($cmd=='insertQuestion'){
    $question = $_GET['question'];
		$answers = $_GET['answers'];
		$topic = $_GET['topic'];
		$qryGetTopicID = "SELECT ID FROM Topics WHERE Topic='".$topic."'";
		$result = mysql_query($qryGetTopicID) or die(mysql_error());
		$row = mysql_fetch_array($result) or die(mysql_error());
		$topicID = $row['ID'];
		$query = "INSERT IGNORE INTO Questions (Question, TopicID) VALUES ('".$question."','".$topicID."')";
		$result = mysql_query($query) or die(mysql_error());
		if ($result){
			$questID = mysql_insert_id();
			#$query="SELECT ID FROM Questions WHERE Question='".$question."'";
			#$result = mysql_query($query) or die(mysql_error());
			#$row=mysql_fetch_array($result) or die(mysql_error());
			#$questID = $row['ID'];
			foreach($answers as $answer){
				 $query="INSERT INTO QuestionAnswers (questionID, Answer) ".
						"VALUES('".$questID."', '".$answer."')";
				 $result = mysql_query($query) or die(mysql_error());
				 if (!$result){
					  echo "error inserting answer: ".$answer;
				 }
			}
			echo "OK";
		} else {
			echo "error inserting question: ".$question;
		}
    } elseif($cmd=='updateQuestion'){
		$questionID = $_GET['questionID'];
		$todaysDate = date("y/m/d");
		
		$query = "UPDATE Questions SET Date='".$todaysDate."', isPushed=1 WHERE ID='".$questionID."'";
		echo $query;
		$result = mysql_query($query) or die(mysql_error());
		echo( "done" );
	} elseif($cmd=='getStats'){
		include 'stats.php';
	} elseif($cmd=='getAchievements'){
		include 'achievements.php';
	}
	mysql_close();
?>
