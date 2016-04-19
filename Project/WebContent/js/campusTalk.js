//Scripts for the application

var userId = 100; // from session
var forumId = 5; // from session
$("#btnSubscribe").hide();
$("#btnUnsubscribe").hide();
$("#btnNewPost").hide();


//Set parameters for loading forum page
function loadForumPage(){

	var forumTitle = 'How to choose design pattern'; // from session
	$("#forumTitle").text(forumTitle);
	var userName = 'Pallavi Madasu'; // from session
	var noOfDays = 20; // getNoOfDays(createdDate) forum created date from session
	var forumCreatorInfo = 'Created by '+userName+', '+noOfDays+' days ago';
	$("#forumCreatorInfo").text(forumCreatorInfo);
	getCountOfSubscribers(forumId,0,function(result){
		var noOfSubscribers = result.countOfSubscribers;
		//console.log('noOfSubscribers - '+noOfSubscribers);
		$("#subscribersInfo").text(noOfSubscribers + ' Member(s) Subscribed ,');
	});

	getCountOfSubscribers(forumId,userId,function(result){
		var subscriptionStatus = false;
		if(result.countOfSubscribers >= 1){
			subscriptionStatus = true;
		}
		//console.log('countOfSubscribers - '+result.countOfSubscribers+', subscriptionStatus - '+subscriptionStatus);
		if(subscriptionStatus){
			$("#btnSubscribe").hide();
			$("#btnUnsubscribe").show();
			$("#btnNewPost").show();
		} else {
			$("#btnSubscribe").show();
			$("#btnUnsubscribe").hide();
			$("#btnNewPost").hide();
		}
	});

	// Load posts and replies dynamically
	loadPostsAndReplies();

	//Get the new post modal
	var modal = document.getElementById('newPostModal');
	var btnNewPost = document.getElementById('btnNewPost');
	btnNewPost.onclick = function() {		    	
		modal.style.display = "block";
	}
	var btnClose = document.getElementById("closePost");
	btnClose.onclick = function() {
		modal.style.display = "none";
	}
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}

	//Get the new forum modal
	var forumModal = document.getElementById('newForumModal');
	var btnNewForum = document.getElementById('btnCreateForum');
	btnNewForum.onclick = function() {		    	
		forumModal.style.display = "block";
		populateTopics();
	}
	var btnCloseForum = document.getElementById("closeForum");
	btnCloseForum.onclick = function() {
		forumModal.style.display = "none";
	}
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == forumModal) {
			forumModal.style.display = "none";
		}
	}
}

//Load posts and replies dynamically
function loadPostsAndReplies(){
	var forumId = 1;
	getPostsAndReplies(1,function(result){
		if(result != null){
			var noOfPosts = result.length; 			
			//console.log('noOfPosts - '+noOfPosts);
			$("#noOfPostsInfo").text(noOfPosts + ' Posts');			
			for (var i=0; i < noOfPosts; i++) {
				var postDescription = result[i].post.description;
				var postCreatedDate = result[i].post.createdDate;
				var noOfPdays = getNoOfDays(postCreatedDate);
				var createdBy = result[i].post.createdBy;
				var creatorName = result[i].postCreatorName;
				var postCreatorInfo = 'By '+creatorName+', '+noOfPdays+' days ago';
				var noOfReplies = result[i].replies.length;
				var replyInfo = noOfReplies + ' Reply(s)';
				var postId = result[i].post.postId;
				//console.log('postId - '+ postId + ', noOfReplies -' +noOfReplies +', postCreator - '+creatorName);

				$("#blogs").append($('<div>')
						.append('<div class="img1"><img class="img-circle" src="./img/profileIcon.jpg" width="30px" height="30px"/></div>')
						.append('<div data-toggle="collapse" data-target="#demo'+i+'" class="postText" onclick ="toggle('+i+');" id="postText">'+postDescription+'</div> <img data-toggle="collapse" class ="allreplies" data-target="#demo'+i+'" src="./img/close.png" onclick ="toggle('+i+');" id ="collapse'+i+'" title ="Click to view replies"/>')
						.append('<div id="noOfRepliesInfo" data-toggle="collapse" data-target="#demo'+i+'" onclick ="toggle('+i+');" class="noOfRepliesInfo"><a href"#" title ="Click to view replies">'+replyInfo+'</a></div>')
						.append('<div id="postCreator" class="postCreator">'+postCreatorInfo+'</div>'))
						.append('</div>'); 
				$("#blogs").append('<div id="demo'+i+'" class="collapse" style="margin-left: 50px">');
				for (var j=0; j < noOfReplies; j++) {
					var replyDescription = result[i].replies[j].description;

					$("#demo"+i).append('<div style="font-family: monospace; border-style: outset; border-bottom-color: lightgrey;" id="replies">'+replyDescription+'-'+j+'</div>');
				}
				$("#demo"+i).append('<div id="comment'+i+'"><div class="replyText"><input type="text" id ="newReply'+i+'" name="newReply" placeholder="Reply..." style="color: black;font-size: 15px;font-family: Times New Roman, Georgia, Serif;"/><input type="button" name="reply" value="Send" onclick=createReply(\'' + postId + '\',\'' + i + '\') class="replyBtn" /></div></div>');
				$("#blogs").append('</div>');			
			}
			$("#blogs").append('<div class="space"></div>');
		};
	});

}

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
			//console.log("login credentials have been sent for authorization");
			window.location = 'HomePage.html';
		});
	}
}

//Create forum
function createForum()
{			
	var forumDescription = $("#forumDescription").val();
	if(forumDescription == null || forumDescription.trim() == ""){
		alert('Please describe forum !!');
	}
	var topicId = $('#selectTopic').val();
	if(topicId == null || topicId == 0){
		alert('Please select a topic !!');
	}
	alert('userId-'+userId+' topicId-'+topicId+' topic value-'+ $("#selectTopic :selected").text()+' forumDescription-'+forumDescription);
	if(userId != null && topicId != null && forumDescription != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/createForum",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, topicId: topicId, forumDescription: forumDescription}),
		}).done(function(data){
			//console.log("user Id, topic Id and forumDescription sent to server");
			$("#newForumModal").css("display","none");
		});
	}
}

//Unsubscribe from a forum
function unsubscribe()
{			
	if(userId != null && forumId != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/unsubscribe",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, forumId: forumId}),
		}).done(function(data){
			//console.log("user Id and forum Id sent to server");
			$("#btnSubscribe").show();
			$("#btnUnsubscribe").hide();
			$("#btnNewPost").hide();
			$("#buttons").toggle().toggle();
		});
	}
}

//Subscribe to a forum
function subscribe()
{	
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
			//console.log("user Id and forum Id sent to server");
			$("#btnSubscribe").hide();
			$("#btnUnsubscribe").show();
			$("#btnNewPost").show();
			$("#buttons").toggle().toggle();
		});
	}
}

//Create Post
function createPost()
{			
	var postDescription = $("#postDescription").val();
	if(postDescription == null || postDescription.trim() == ""){
		alert('Please describe post !!');
	}
	if(userId != null && forumId != null && postDescription != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/createPost",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, forumId: forumId, postDescription: postDescription}),
		}).done(function(data){
			//console.log("user Id, forum Id and postDescription sent to server");
			$("#newPostModal").css("display","none");
		});
	}

}

//Create Reply
function createReply(postId,index)
{		
	var replyDescription = $("#newReply"+index).val();
	if(replyDescription == null || replyDescription.trim() == ""){
		alert('Please reply !!');
	}
	//console.log('createReply - postId'+postId+'userId-'+userId+'newReply-'+replyDescription);
	if(userId != null && postId != null && replyDescription != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/createReply",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId, postId: postId, replyDescription: replyDescription}),
		}).done(function(data){
			//console.log("user Id, post Id and replyDescription sent to server");
		});

	}
}

//Get posts and corresponding replies
function getPostsAndReplies(forumId,callback){
	$.ajax({
		url : "/CampusTalk/rest/CampusTalkAPI/getPostsAndReplies",
		datatype:'json',
		type: "post",
		contentType: "application/json",
		data: JSON.stringify({forumId: forumId}),
	}).done(function(data){
		callback(data);
	});
}

//Get no of subscribers
function getCountOfSubscribers(forumId,userId,callback){
	$.ajax({
		url : "/CampusTalk/rest/CampusTalkAPI/getCountOfSubscribers",
		datatype:'json',
		type: "post",
		contentType: "application/json",
		data: JSON.stringify({forumId: forumId,userId: userId}),
	}).done(function(data){
		callback(data);
	});
}

//Calculate no of days
function getNoOfDays(createdDate){
	startDate = new Date(createdDate);
	todaysDate = new Date();
	var days = Math.round((todaysDate - startDate) / (1000 * 60 * 60 * 24));
	return days;
}

//Get the topics to create forums
function getTopics(callback){
	$.ajax({
		url : "/CampusTalk/rest/CampusTalkAPI/getTopics",
		datatype:'json',
		type: "post",
		contentType: "application/json",
		data: JSON.stringify({}),
	}).done(function(data){
		callback(data);
	});
}

//Toggle expand and collapse images
function toggle(index) {	
	$("#collapse"+index).attr('src',"./img/open.png");	
	if(this.id =="expand"+index){
		this.id ="collapse"+index;
		$("#collapse"+index).attr("src","./img/close.png");	
		$("#collapse"+index).attr("title", "Click to view replies");
	} else{
		this.id ="expand"+index;
		$("#expand"+index).attr('src',"./img/open.png");	
		$("#expand1"+index).attr("title", "Click to hide replies");
	}	 

};

//Populate topic values in create forum modal
function populateTopics(){
	var select = document.getElementById("selectTopic");
	var defaultOpt = document.createElement("option");
	defaultOpt.textContent = "Select..";
	defaultOpt.value = 0;
	select.appendChild(defaultOpt);
	getTopics(function(result){
		if(result != null){
			for(var i=0;i<result.length;i++){
				var topicDescription = result[i].topicDescription;
				var topicId = result[i].topicId;
				var opt = document.createElement("option");
				opt.textContent = topicDescription;
				opt.value = topicId;
				select.appendChild(opt);
			}
		}
	});
}

//Signup Page JavaScript
function initializeTopics(){
    var select = $(".form-topic")
    var defaultOpt = document.createElement("option");
    getTopics(function(result){
        if(result != null){
            for(var i=0;i<result.length;i++){
                var topicDescription = result[i].topicDescription;
                var topicId = result[i].topicId;
                var opt = document.createElement("option");
                opt.textContent = topicDescription;
                opt.value = topicId;
                select.append(opt);
            }
        }
    });
    
    $(".form-topic").select2({
        placeholder: "Select Topic Interested In",
        allowClear: true,
        width: '100%'
    });
    $(".select2-selection--multiple").css( "background-color", "#f8f8f8" );
    $(".select2-selection--multiple").css( "border", "1px solid blue" );
};

function signup(){
    var firstName = $("#form-first-name").val();
    var lastName = $("#form-last-name").val();
    var emailId = $("#form-email").val();
    var password = $("#form-password-signup").val();
    var major = $("#form-major").val()
    var topics = $(".form-topic").val();
    $.ajax({
        url : "/CampusTalk/rest/CampusTalkAPI/createUser",
            datatype:'json',
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({firstName: firstName, lastName: lastName, emailId: emailId, password: password, major: major, topics: topics}),
        }).done(function(data){
            //console.log("user Id, topic Id and forumDescription sent to server");
            $("#newForumModal").css("display","none");
        });
}

//get Home Page
function loadHomePage(id)
{			
  getHomePageForumContents(id, function(result){
	  //get the data and set the div contents
	  console.log(result[0]);
	  
  }

  );
  getHomePageEventConents(id, function(result){
	  //get the even data and set the div contents
	  console.log(result[0]);
  }
  );
  getHomePageForumMemberships(id, function(result){
	  //get the person forum memberships and set the div contents
	  console.log(result[0]);
  });
}

function getHomePageForumContents(id, callback)
{
	var userId = id;
	if(userId != null)
	{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/HomePage_Forums",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId}),
			success: function(data){
				console.log("Getting the Home Page Posts from Forums");
				callback(data);
			}
		});
	}
}

function getHomePageEventConents(id, callback)
{
	var userId = id;
	if(userId != null)
		{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/HomePage_Events",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId}),
			success: function(data){
				console.log("Getting the Home Page Posts from Forums");
				callback(data);
			}
		});
		}
	}

function getHomePageForumMemberships(id, callback)
{
	var userId = id;
	if(userId != null)
		{
		$.ajax({
			url : "/CampusTalk/rest/CampusTalkAPI/HomePage_ForumMemberships",
			datatype:'json',
			type: "post",
			contentType: "application/json",
			data: JSON.stringify({userId: userId}),
			success: function(data){
				console.log("Getting the Home Page Posts from Forums");
				callback(data);
			}
		});
		}
	}

