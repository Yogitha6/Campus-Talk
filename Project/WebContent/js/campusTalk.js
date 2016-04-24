//Scripts for the application

$("#btnSubscribe").hide();
$("#btnUnsubscribe").hide();
$("#btnNewPost").hide();


//Set parameters for loading forum page
function loadForumPage(){

	var forumId = getUrlParameter("id");
	var userId = Cookies.get("userId");
	var userName;
	
	 $.get("/CampusTalk/rest/CampusTalkAPI/getForum/"+ forumId)
		.done(function(data){
			var forumTitle = data.description; // from session
			$("#forumTitle").text(forumTitle);
			
			$.get("/CampusTalk/rest/CampusTalkAPI/getUser/"+ data.createdBy)
		.done(function(result){
			userName = result.firstname+" "+result.lastname;
			var noOfDays = getNoOfDays(data.createdDate);
			var forumCreatorInfo = 'Created by '+userName+', '+noOfDays+' days ago';
			$("#forumCreatorInfo").text(forumCreatorInfo);
				
		});
		});

	//Get total count of subscribers for this forum
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
	loadPostsAndReplies(forumId);

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
	
	
	//setup search modal
	searchModal();
	
}

//setup search modal
function searchModal(){
    var searchModal = document.getElementById('searchModal');
    var searchBtn = document.getElementById('searchBtn');
    searchBtn.onclick = function( event ) {
        event.preventDefault();
        searchModal.style.display = "block";
        //populateTopics();
    }
    var btnCloseSearch1 = document.getElementById('searchCloseButton1');
    btnCloseSearch1.onclick = function() {
        searchModal.style.display = "none";
    }
    var btnCloseSearch2 = document.getElementById('searchCloseButton2');
    btnCloseSearch2.onclick = function() {
        searchModal.style.display = "none";
    }
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == searchModal) {
            searchModal.style.display = "none";
        }
    }
    
    // populate domain field
    
    var domainSelect = $(".search-form-domain");
    var areaSelect = $(".search-form-area");
    var topicSelect = $(".search-form-topic");
    var defaultOpt = document.createElement("option");
    $.get( "/CampusTalk/rest/CampusTalkAPI/getDomain" )
    .done(function( data ) {
        if(data != null){
            for(var i=0; i<data.length; i++){
                var domainDescription = data[i].domainDescription;
                var domainId = data[i].domainId;
                var opt = document.createElement("option");
                opt.textContent = domainDescription;
                opt.value = domainId;
                domainSelect.append(opt);
            }
        }
    });
    
    domainSelect.select2({
        placeholder: "Select Domain",
        allowClear: true,
        width: '100%'
    });
    
    areaSelect.select2({
        placeholder: "Select Area",
        allowClear: true,
        width: '100%'
    });
    
    topicSelect.select2({
        placeholder: "Select Topic",
        allowClear: true,
        width: '100%'
    });
    
    areaSelect.prop('disabled', true);
    topicSelect.prop('disabled', true);
    $("#userSearch").hide();
    $("#forumSearch").hide();
    
    $("#search-domain").change(function() {
        //debugger;
        areaSelect.find('option:not(:first)').remove();
        topicSelect.find('option:not(:first)').remove();
        areaSelect.prop('disabled', true);
        topicSelect.prop('disabled', true);
        if($(this).val() > 0){
            $.get( "/CampusTalk/rest/CampusTalkAPI/getArea/" + $(this).val() )
            .done(function( data ) {
                if(data != null){
                    for(var i=0; i<data.length; i++){
                        var areaDescription = data[i].areaDescription;
                        var areaId = data[i].areaId;
                        var opt = document.createElement("option");
                        opt.textContent = areaDescription;
                        opt.value = areaId;
                        areaSelect.append(opt);
                    }
                }
            });
            areaSelect.prop('disabled', false);
            $("select.search-form-area").select2({
                placeholder: "Select Area",
                allowClear: true,
                width: '100%'
            });
            $("select.search-form-topic").select2({
                placeholder: "Select Topic",
                allowClear: true,
                width: '100%'
            });
            $("#userSearch").hide();
            $("#forumSearch").hide();
        }
      });
    
    $("#search-area").change(function() {
        //debugger;
        topicSelect.find('option:not(:first)').remove();
        topicSelect.prop('disabled', true);
        if($(this).val() > 0){
            $.get( "/CampusTalk/rest/CampusTalkAPI/getTopics/" + $(this).val() )
            .done(function( data ) {
                if(data != null){
                    for(var i=0; i<data.length; i++){
                        var topicDescription = data[i].topicDescription;
                        var topicId = data[i].topicId;
                        var opt = document.createElement("option");
                        opt.textContent = topicDescription;
                        opt.value = topicId;
                        topicSelect.append(opt);
                    }
                }
            });
            topicSelect.prop('disabled', false);
            $("select.search-form-topic").select2({
                placeholder: "Select Topic",
                allowClear: true,
                width: '100%'
            });
            $("#userSearch").hide();
            $("#forumSearch").hide();
        }
      });
    
    $("#search-topic").change(function() {
        if($(this).val() > 0){
            $("#userSearch").show();
            $("#forumSearch").show();
        }
      });
    $( "#userSearch" ).click(function( event ) {
        event.preventDefault();
        window.location.href = 'resultPage.html?criteria=user&topicId=' + $("#search-topic").val();
    });
    $( "#forumSearch" ).click(function( event ) {
        event.preventDefault();
        window.location.href = 'resultPage.html?criteria=forum&topicId=' + $("#search-topic").val();
    });
    /*
    $(".select2-selection--multiple").css( "background-color", "#f8f8f8" );
    $(".select2-selection--multiple").css( "border", "1px solid blue" );*/
};

//Load posts and replies dynamically
function loadPostsAndReplies(forumId){
	getPostsAndReplies(forumId,function(result){
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
						.append('<div id="noOfRepliesInfo" data-toggle="collapse" data-target="#demo'+i+'" onclick ="toggle('+i+');" class="noOfRepliesInfo"><a href"#" id ="replyCount'+i+'" title ="Click to view replies">'+replyInfo+'</a></div>')
						.append('<div id="postCreator" class="postCreator">'+postCreatorInfo+'</div>'))
						.append('</div>'); 
				$("#blogs").append('<div id="demo'+i+'" class="collapse" style="margin-left: 50px">');
				
				for (var j=0; j < noOfReplies; j++) {
					var replyDescription = result[i].replies[j].description;					
					$("#demo"+i).append('<div class ="postCls" id="replies'+i+'">'+replyDescription+'</div>');
				
				}

				$("#demo"+i).append('<div id="comment'+i+'"><div class="replyText"><input type="text" id ="newReply'+i+'" name="newReply" placeholder="Reply..." style="color: black;font-size: 15px;font-family: Times New Roman, Georgia, Serif;"/><input type="button" name="reply" value="Send" onclick=createReply("'+postId+'","'+i+'") class="replyBtn" /></div></div>');
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
			Cookies.set('userId', data);
            window.location = 'HomePage.html';
		});
	}
}

//Create forum
function createForum()
{   var userId = Cookies.get('userId');
	var forumDescription = $("#forumDescription").val();
	if(forumDescription == null || forumDescription.trim() == ""){
		alert('Please describe forum !!');
	}
	var topicId = $('#selectTopic').val();
	if(topicId == null || topicId == 0){
		alert('Please select a topic !!');
	}
	//alert('userId-'+userId+' topicId-'+topicId+' topic value-'+ $("#selectTopic :selected").text()+' forumDescription-'+forumDescription);
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
			debugger;
			$("#newForumModal").css("display","none");
			window.location = "forumPage.html?id="+data;
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
	var forumId = getUrlParameter("id");
	var userId = Cookies.get("userId");
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
	var userId = Cookies.get("userId");
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
			$("#demo"+index).before('<div class ="postCls" style="margin-left:50px" id="replies'+index+'">'+replyDescription+'</div>');
			$("#replyCount"+index).html($('[id^=replies'+index+']').length+' Reply(s)');	
			$("#newReply"+index).val(" ");
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
	var days = Math.floor((todaysDate - startDate) / (1000 * 60 * 60 * 24));
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
    
    var emailRegExp = /^([a-zA-Z0-9._`-]{4})+@colorado.edu$/;
    var passwordRegExp = /^[a-zA-Z0-9._`-]{4,}$/;
    //validating username and password
    if(emailId.match(emailRegExp)==null)
    {
        $("#form-email").notify("You can Login only with your colorado.edu email Id");
    }
    else if(password.match(passwordRegExp)==null)
    {
        $("#form-password-signup").notify("Invalid password");
    }
    else{
        $.ajax({
            url : "/CampusTalk/rest/CampusTalkAPI/createUser",
                datatype:'json',
                type: "post",
                contentType: "application/json",
                data: JSON.stringify({firstName: firstName, lastName: lastName, emailId: emailId, password: password, major: major, topics: topics}),
            }).done(function(data){
                Cookies.set("userId", data);
                window.location = 'HomePage.html?id=' + data;;
        });
    }
};

//redirect to profile page
function profileLink(){
    window.location = 'profilePage.html?id=' + Cookies.get("userId");
};

//redirect to profile page
function logoutLink(){
    Cookies.remove("userId");
	window.location = 'landingPage.html';
};

//get Home Page
function loadHomePage()
{
  var id = Cookies.get("userId")
  console.log(id);
  if(!id)
	  {
	  	window.location = "landingPage.html";
	  }
  getUserNameforHomePage(id, function(result){
	  $("#userName").text(result.firstname+" "+result.lastname);
  });
  
  getHomePageForumContents(id, function(result){
	  //get the data and set the div contents
	  console.log(result[0]); 
	  var noOfForumPosts = result.length;
	  $.each(result, function(index, val){
		  var url = "forumPage.html";
		  url = url+"?id="+val.forumId;
		  var forumName = "forumName";
		  $("#ForumPosts").append('<center><div class=\"col-lg-12 text-center\">');
		  $("#ForumPosts").append('<h4 class="'+val.forumId+'">'+forumName+'</h4>');
		  var forumId = val.forumId;
		  $.get("/CampusTalk/rest/CampusTalkAPI/getForum/"+ forumId)
			.done(function(data){
				forumName = data.description;
			  	console.log(forumName, forumId);
			  	$("."+forumId).text(forumName);
			});
		  $("#ForumPosts").append('<small>'+val.createdDate+'</small>')
		  		.append('<p>'+val.description+'</p>')
		  		.append('<a href="'+url+'" class="btn btn-default btn-sm">Read More</a><hr></div></center>');
			});
				
			});
  
  getHomePageEventConents(id, function(result){
	  //get the even data and set the div contents
	  console.log(result[0]);
	  var noOfEvents = result.length;
	  for(var i = 0; i<noOfEvents; i++)
		  {
		  $("#Events").append('<center><div class="text-center col-lg-12">')
		  	.append(result[i].eventName+'<br/><small>'+result[i].eventStartDate+'---'+result[i].eventEndDate+'</small><br/>')
		  	.append('<a href="'+result[i].eventLink+'">Read More</a>')
		  	.append('<hr></div></center>');
		  }	
  }
  );
  getHomePageForumMemberships(id, function(result){
	  //get the person forum memberships and set the div contents
	  console.log(result[0]);
	  var noOfForums = result.length;
	  for(var i = 0; i<noOfForums; i++)
		  {
		  var url = "forumPage.html";
		  url = url+"?id="+result[i].forumId;
		  $("#Forums").append('<center><div class="text-center col-lg-12">')
		  	.append('<a href="'+url+'" >'+ result[i].description+'</a>').append('<br/><br/>')
		  	.append('</div></center>');
		  }
  });
  
//setup search modal
  searchModal();
}

function getforumNameById(id, callback)
{
	var forumId = id;
	$.get("/CampusTalk/rest/CampusTalkAPI/getForum/"+ forumId)
	.done(function(data){
		console.log(data);
		callback(data.description);
	});
}
function getUserNameforHomePage(id, callback)
{
	var userId = id;
	 $.get( "/CampusTalk/rest/CampusTalkAPI/getUser/" + userId )
     .done(function( data ) {
    	 console.log(data);
    	 callback(data);
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
				//console.log(data);
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
				console.log("Getting the Home Page Events");
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
				console.log("Getting the Home Page Forum Memberships");
				callback(data);
			}
		});
		}
	}

function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

function initializeProfilePage(){
    var userId = getUrlParameter('id');
    var sessionUserId = Cookies.get("userId");
    if ( userId !== sessionUserId ){
        $("#editBtn").hide();
    }
    if (userId){
        $.get( "/CampusTalk/rest/CampusTalkAPI/getUser/" + userId )
        .done(function( data ) {
            var fullName = data.firstname + ' ' + data.lastname
            $("#fullName").text(fullName);
            $("#emailtext").text(data.emailId);
            $("#major").text(data.major);
        });
        
        $.get( "/CampusTalk/rest/CampusTalkAPI/getUserInterests/" + userId )
        .done(function( data ) {
            if(data != null){
                for(element in data){
                    $("#interests").append(
                            '<li style="color:white;">' + data[element].topicDescription + '</li>');
                }
            }
        });
    }
    searchModal();
}

function initializeResultPage(){
    var criteria = getUrlParameter('criteria');
    var topicId = getUrlParameter('topicId');
    var sessionUserId = Cookies.get("userId");
    
    if (criteria && topicId){
        if ( criteria == "forum" ){
            $("#criteria").text("Forums");
            $.get( "/CampusTalk/rest/CampusTalkAPI/getSearchForums/" + topicId )
            .done(function( data ) {
                for(item in data)
                {
                    var url = "forumPage.html";
                    url = url+"?id="+data[item].forumId;
                    $("#ForumPosts").append('<center><div class="text-center col-lg-12">')
                    .append('<h4><a style="color:black;" href="'+url+'" >'+ data[item].description+'</a></h4>').append('<br/>')
                    .append('</div></center>');
                }
            });
        }
        if ( criteria == "user" ){
            $("#criteria").text("Users");
            $.get( "/CampusTalk/rest/CampusTalkAPI/getSearchUsers/" + topicId )
            .done(function( data ) {
                for(item in data)
                {
                    var username = data[item].firstname + " " + data[item].lastname;
                    var url = "profilePage.html";
                    url = url+"?id="+data[item].userId;
                    $("#ForumPosts").append('<center><div class="text-center col-lg-12">')
                    .append('<h4><a style="color:black;" href="'+url+'" >'+ username +'</a></h4>').append('<br/>')
                    .append('</div></center>');
                }
            });
        }
    }
    searchModal();
    
}

