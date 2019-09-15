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
    <title>FastQs Server Page</title>

    <script type="text/javascript" src="jquery.js"></script>
    <script type="text/javascript" src="jquery.label_over.js"></script>
    <script type="text/javascript" src="addQuestion.js"></script>
  </head>
  <body style="font-family: Arial;border: 0 none;">
  
  <div id="content">
    <table align="center" cellspacing="0" cellpadding="0" id="normalTable">
		<tr>
			<td>
				<ul id="list-nav">
					<li><a href="index1.php">Insert Question</a></li>
					<li><a href="pushQuestion.php">Push Question</a></li>
					<li><a href="pushQuestion.php">test</a></li>
					<li><a href="pushQuestion.php">test</a></li>
					<li><a href="pushQuestion.php">test</a></li>
				</ul>
			</td>
		</tr>
		<!--<tr class="normalRow">
		  <td>
			<div id="title">FastQs server page</div>
		  </td>
		</tr> -->
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
<div id="divInsertQuestion" class="section">
					<div class="labeledfield">            
					  <label class="targetLabel" for="txtQuestion">Question...</label>           
					  <input type="text" id="txtQuestion" name="txtQuestion"/>
					</div>
					<div class="labeledfield">  
					  <label class="messageLabel" for="txtQuestionAnswers">Acceptable Answers... (separate with ":")</label>           
					  <textarea id="txtQuestionAnswers" name="txtQuestionAnswers"></textarea>
					</div>
					
					<?
						$mydb = mysql_connect("localhost", "root", "Qa071576") or die(mysql_error());
						mysql_select_db("fastqs") or die(mysql_error());
						$query = "SELECT Topic FROM Topics";
						$topicList = mysql_query($query);
						
						echo("<table class=\"contentTable\" >");
						echo("<th colspan=\"2\">Topics</th>");
						$counter=0;
						$itemCount = mysql_num_rows($topicList);
						while($row=mysql_fetch_array($topicList)){
							$topic = $row['Topic'];
							if ($counter % 2 ==0){
								if ($counter %4 ==0){
									echo("<tr class=\"contentTR\"> <td class=\"alt\" onmouseover=\"this.className='mousehover'\" onmouseout=\"this.className='alt'\">");
									echo("<label><input type=\"radio\" name=\"radioTopic\" class=\"radio\" value=\"".$topic."\"\>".$topic."</label>");
									echo("</td>");
								} else {
									echo("<tr class=\"contentTR\"><td onmouseover=\"this.className='mousehover'\" onmouseout=\"this.className=''\">");
									echo("<label><input type=\"radio\" name=\"radioTopic\" class=\"radio\" value=\"".$topic."\"\>".$topic."</label>");
									echo("</td>");
								}
								if ($counter==$itemCount-1){
									echo("<td></td></tr>");
								}
							} else {
								if ($counter%4==3){
									echo("<td class=\"alt\" onmouseover=\"this.className='mousehover'\" onmouseout=\"this.className='alt'\">");
								} else {
									echo("<td onmouseover=\"this.className='mousehover'\" onmouseout=\"this.className=''\">");
								}
								echo("<label><input type=\"radio\" name=\"radioTopic\" class=\"radio\" value=\"".$topic."\"\>".$topic."</label>");
								echo("</td></tr>");
							}
							
							$counter++;
						}
						echo("</table>");
					?>
					
					<div class="loading" id="testLoading">
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
					<div style="clear:both">
					</div>
					<div class="button" id="insertQuestionButton">
						Insert Question
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