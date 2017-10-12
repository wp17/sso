function onLoad(){
	$("#submit").click(function(){
		login();
	});
	
	$(".jcaptcha-btn").click(function() {
        $(".jcaptcha-img").attr("src", 'jcaptcha.jpg?'+new Date().getTime());
    });
}

function login(){
	var pwd = $('#pwd').val();
	var username = $('#username').val();
	var redirectUrl = $("#redirectUrl").val();
	var jcaptchaCode = $("#jcaptchaCode").val();
	
	var params = {};
	params.pwd = pwd;
	params.username = username;
	params.redirectUrl = redirectUrl;
	params.jcaptchaCode = jcaptchaCode;
	
	var data = $.ajax({url:'login', async:false, data:params, dataType:'json'}).responseText;
	
	if(null != data){
		data = eval("("+data+")");
		var r = data.r;
		if(r==0){
			alert('access denied');
		}else if(r==1){
			var accessToken = data.token;
			var username = data.username;
			var redirectUrl = data.redirectUrl;
			redirectUrl = redirectUrl+"?username="+username+"&token="+accessToken;
			window.location.href = redirectUrl;
		}else if(r==2){
			alert("验证码不正确");
		}
	}
}