$(function() {        
    $('label.messageLabel').labelOver('labelover');
    $('label.targetLabel').labelOver('labelover');  
})
	  
$(function() {
	$("#insertQuestionButton").click(function() {
		var question = $('#txtQuestion').val();
		var txtAnswers = $('#txtQuestionAnswers').val();
		var topic = getSelectedTopic();
		var answers = txtAnswers.split(":");
		$('#testLoading').slideToggle('fast');
		$.get("ajaxtools.php", { 'cmd': "insertQuestion", 'answers[]': answers, 'question': question, 'topic': topic },
			function(data){
				if(data!="OK"){
					alert("error"+data);
				} else {
					$('#testLoading').slideToggle('fast');
				}
			});
		});
	  })
	  
function getSelectedTopic(){
	for(i=0; i<document.myForm.radioTopic.length; i++) {
		if (document.myForm.radioTopic[i].checked==true) {
			return document.myForm.radioTopic[i].value
		}
	}
	return "";
}
