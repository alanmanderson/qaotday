<!--
  copyright (c) 2009 google inc.

  You are free to copy and use this sample.
  License can be found here: http://code.google.com/apis/ajaxsearch/faq/#license
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<link rel="stylesheet" type="text/css" href="main.css" />
    <title>FastQs Push Question</title>
    <script type="text/javascript" src="jquery.js"></script>
    <script type="text/javascript" src="jquery.label_over.js"></script>
	<!--<script type="text/javascript" src="answers.php"></script>-->
    <script type="text/javascript">
      $(function() {        
        $('label.messageLabel').labelOver('labelover');
        $('label.targetLabel').labelOver('labelover'); 
      })
	  
	  $(function() {
		$("#pushQuestionButton").click(function() {
			$('#questionSending').slideToggle('fast');
			var questionID = getSelectedQuestion();
			$.get("ajaxtools.php", { 'cmd': "updateQuestion", 'questionID': questionID },
					function(data){
						
					});
			$.ajax({
			      url: 'send_mqtt.php',
			      type: 'POST',
			      data: {'questid': questionID},
			      dataType: 'text',
			      timeout: 20000,
			      error: function(){				      
					$('#questionSending').slideToggle('fast');
					alert('Failed to communicate to the server. Try again!')                                     
			      },
			      success: function(text){
					$('#questionSending').slideToggle('fast');
			        if (text == '') {
			          alert('Failed to send the message. Try again!')
			        } else {
						$('#pushSent').slideToggle('fast');
						window.location.reload();
					}
			      }
          });
		});
		
		
	  })
	  
	  function getSelectedQuestion(){
		if(!document.myForm.radioQuestion.length){
			return document.myForm.radioQuestion.value;
		}
	    for(i=0; i<document.myForm.radioQuestion.length; i++) {
			if (document.myForm.radioQuestion[i].checked==true) {
				return document.myForm.radioQuestion[i].value;
			}
		}
		return "";
	  }
	  
	  function updateQuestionList(){
	    var url = "http://fastqs.com/pushQuestion.php";
		var first = true;
		for(i=0; i<document.myForm.pushTopicArray.length; i++){
			if (document.myForm.pushTopicArray[i].checked) {
				if (first){
					url = url+"?filter[]="+document.myForm.pushTopicArray[i].value;
					first = false;
				} else {
					url = url+"&filter[]="+document.myForm.pushTopicArray[i].value;
				}
				
			}
		}
		window.location = url;
	  }
	  
	  function updateAnswers(id){
		var xhReq = new XMLHttpRequest();
		xhReq.open("GET", "answers.php?id="+id, false);
		xhReq.send(null);
		var response = xhReq.responseText;
		document.getElementById('answersTable').innerHTML = response;
	  }

    </script>    
  </head>
  <body style="font-family: Arial;border: 0 none;">
  
  <div id="content">
    <table align="center" cellspacing="0" cellpadding="0" id="normalTable">
		<tr>
			<td>
				<ul id="list-nav">
					<li><a href="index1.php">Insert Question</a></li>
					<li><a href="pushQuestion.php">Push Question</a></li>
				</ul>
			</td>
		</tr>
		<tr class="normalRow">
		  <td>
			<div id="title">FastQs server page</div>
		  </td>
		</tr>
		<tr class="normalRow">
		  <td>
			<div class="status">
			Server status:
	<?
	require('SAM/php_sam.php');

	//create a new connection object
	$conn = new SAMConnection();

	//start initialise the connection
	$result = $conn->connect(SAM_MQTT, array(SAM_HOST => '127.0.0.1', SAM_PORT => 1883));      
	if ($result) {
	  $conn->disconnect();
	  print_r("<span class='online'>Online</span>");
	} else {
	  print_r("<span class='offline'>Offline</span>");
	}
	?>        
			</div>
		  </td>
		</tr>
		<tr class="normalRow">
			<td>
				<div id="buttons">
				  <form name="myForm">
<div id="divSelectQuestion" class="section">
					<?
						$mydb = mysql_connect("localhost", "root", "Qa071576") or die(mysql_error());
						mysql_select_db("fastqs") or die(mysql_error());
						
						$filter = $_GET['filter'];
						
						$query = "SELECT Question,ID,TopicID FROM Questions WHERE isPushed=0";
						$first = true;
						foreach($filter as $topicID) {
							if($first) {
								$query = $query." AND (TopicID='".$topicID."'";
								$first = false;
							} else {
								$query = $query." OR TopicID='".$topicID."'";
							}
						}
						if (!$first) {
							$query = $query.")";
						}
						$result = mysql_query($query." ORDER BY TopicID") or die(mysql_error());
						echo("<table id=\"QuestionSelector\" class=\"contentTable\" >");
						echo("<th>Question</th><th>Topic</th>");
						$counter=0;
						while($row=mysql_fetch_array($result)){
							$question = $row['Question'];
							$id = $row['ID'];
							$query = "SELECT Topic FROM Topics WHERE ID='".$row['TopicID']."'";
							$newResult = mysql_query($query) or die(mysql_error());
							$newRow=mysql_fetch_array($newResult);
							$topic=$newRow['Topic'];
							//$topic = "topic";
							if ($counter % 2==0){
								echo("<tr class=\"alt\" onmouseover=\"this.className='mousehover'\" onmouseout=\"this.className='alt'\"><td class=\"radioTD\">");
							} else {
								echo("<tr class=\"contentTR\" onmouseover=\"this.className='mousehover'\" onmouseout=\"this.className='contentTR'\"><td class=\"radioTD\">");
							}
							//$radio = "<input type=\"radio\" name=\"radioQuestion\" class=\"radio\" value=\"".$id."\"";
							//if (counter==0) $radio = $radio." checked/>".$question;
							//else $radio = $radio."/>".$question;
							echo("<label><input type=\"radio\" name=\"radioQuestion\" class=\"radio\" value=\"".$id.
								 "\" onClick=\"updateAnswers('".$id."')\"\>".$question."</label>");
							//echo($radio);
							echo("</td><td>".$topic."</td></tr>");
							$counter++;
						}
						if ($counter==0){
							echo("<tr class=\"contentTR\"><td colspan=\"2\" > There are no results to match this query </td></tr>");
						}
						echo("</table>");
					?>
					<div class="includeWhich" >
						<table class="contentTable">
							<th colspan="2">Include Questions in which topics?</th>
			<?
			
			function makeCheckBox($topic, $id, $filter){
				$chk = "<label><input type=\"checkbox\" name=\"pushTopicArray\" class=\"radio\" value=\"".$id."\" OnClick=\"updateQuestionList();\"";
				if (in_array($id, $filter) || !$filter) {
					$chk=$chk." checked \>".$topic."</label>";
				} else {
					$chk=$chk."\>".$topic."</label>";
				}
				return $chk;
			}
			
				$query = "SELECT Topic,ID FROM Topics";
				$topicList = mysql_query($query);
				$counter=0;
				$itemCount = mysql_num_rows($topicList);
				$filter = $_GET['filter'];
				while($row=mysql_fetch_array($topicList)){
					$topic = $row['Topic'];
					$id = $row['ID'];
					if ($counter % 2 ==0){
						if ($counter %4 ==0){
							echo("<tr class=\"contentTR\"> <td class=\"alt\">");
							// $chk = "<input type=\"checkbox\" name=\"pushTopicArray\" class=\"radio\" value=\"".$id."\" OnClick=\"updateQuestionList();\"";
							// if (in_array($id, $filter)) {
								// $chk=$chk." checked \>".$topic;
							// } else {
								// $chk=$chk."\>".$topic;
							// }
							// echo($chk);
							echo(makeCheckBox($topic, $id, $filter));
							echo("</td>");
						} else {
							echo("<tr class=\"contentTR\"><td>");
							// echo("<input type=\"checkbox\" name=\"pushTopicArray\" class=\"radio\" value=\"".$id."\" OnClick=\"updateQuestionList();\" \>".$topic);
							echo(makeCheckBox($topic, $id, $filter));
							echo("</td>");
						}
						if ($counter==$itemCount-1){
							echo("<td></td></tr>");
						}
					} else {
						if ($counter%4==3){
							echo("<td class=\"alt\">");
						} else {
							echo("<td>");
						}
						// echo("<input type=\"checkbox\" name=\"pushTopicArray\" class=\"radio\" value=\"".$id."\" OnClick=\"updateQuestionList();\" \>".$topic);
						echo(makeCheckBox($topic, $id, $filter));
						echo("</td></tr>");
					}
					$counter++;
				}
			?>
						</table>
					</div>
					
					<div class="loading" id="questionSending">
					  <img src="16x16_loading.gif" border="0" style="float:left"/>
					  <div class="sending" style="float:left"> 
						sending...
					  </div>
					  <div style="clear:both">
					  </div>
					</div> 
					<div class="sent">
						Message sent!
					</div>
					<table width="100%"> 
						<tr>
							<td>
								<div class="button" id="pushQuestionButton">
									Push Selected Question
								</div>
							</td>
						</tr>
					</table>
					
<div class="section">
	<table id="answersTable" class="contentTable">
		<th>Answers</th>
	</table>
</div>
				
</div>

				  </form>
				</div>
			</td>
		</tr>
    </table>
  </div>
  </body>
</html>
​