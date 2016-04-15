//Script for the application

//Login Validation
function login()
{			
	var username = new String(document.getElementById("form-username").value); // $("#id").val()
	var password = new String(document.getElementById("form-password").value);
	var emailRegExp = /^([a-zA-Z0-9._`-]{4})+@colorado.edu$/;
	var passwordRegExp = /^[a-zA-Z0-9._`-]{4,}$/;
	//validating username and password
	if(username.match(emailRegExp)==null)
	{
		alert("You can Login only with your colorado.edu email Id");
	}
	else if(password.match(passwordRegExp)==null)
	{
		alert("Invalid password");
	}
	else
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/login",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({name: username, pwd: password}),
		}).done(function(data){
			console.log("login credentials have been sent for authorization")});
	}
}

//Create forum
function createForum()
{			
	var userId = 100; // get from session
	var topicId = 12; // get from session
	var forumDescription = $("#forumDescription").val();
	alert('userId-'+userId);
	alert('topicId-'+topicId);
	alert('forumDescription-'+forumDescription);
	if(userId != null && topicId != null && forumDescription != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/createForum",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, topicId: topicId, forumDescription: forumDescription}),
		}).done(function(data){
			console.log("user Id, topic Id and forumDescription sent to server")});
	}
}

//Unsubscribe from a forum
function unsubscribe()
{			
	//var userId = '@Request.RequestContext.HttpContext.Session["userId"]';
	//var userId = '<%=Session["userId"]%>'
	//var forumId = '<%=Session["forumId"]%>'
	var userId = 100; // get from session
	var forumId = 11; // get from session
	alert(userId);
	alert(forumId);
	if(userId != null && forumId != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/unsubscribe",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, forumId: forumId}),
		}).done(function(data){
			console.log("user Id and forum Id sent to server")});
	}
}

//Subscribe to a forum
function subscribe()
{			
	var userId = 100; // get from session
	var forumId = 11; // get from session
	alert(userId);
	alert(forumId);
	if(userId != null && forumId != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/subscribe",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, forumId: forumId}),
		}).done(function(data){
			console.log("user Id and forum Id sent to server")});
	}
}

//Create Post
function createPost()
{			
	var userId = 100; // get from session
	var forumId = 11; // get from session
	var postDescription = $("#postDescription").val();
	alert('userId-'+userId);
	alert('forumId-'+forumId);
	alert('postDescription-'+postDescription);
	if(userId != null && forumId != null && postDescription != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/createPost",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, forumId: forumId, postDescription: postDescription}),
		}).done(function(data){
			console.log("user Id, forum Id and postDescription sent to server")});
	}
}

//Create Reply
function createReply()
{			
	var userId = 100; // get from session
	var postId = 5; // get from session
	var replyDescription = $("#replyDescription").val();
	alert('userId-'+userId);
	alert('postId-'+postId);
	alert('replyDescription-'+replyDescription);
	if(userId != null && postId != null && replyDescription != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/createReply",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, postId: postId, replyDescription: replyDescription}),
		}).done(function(data){
			console.log("user Id, forum Id and replyDescription sent to server")});
	}
}
