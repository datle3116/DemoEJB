<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link
	href='<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css"></c:url>'
	rel="stylesheet"
	integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
	crossorigin="anonymous">
<link rel="stylesheet" href='<c:url value="/assets/main.css"></c:url>'>
<link rel="stylesheet"
	href='<c:url value="/assets/fonts/fontawesome-free-6.0.0/css/all.min.css"></c:url>'>
<title>Demo EJB</title>
</head>

<body>
	<div class="main">
		<h1>todos</h1>
			<input id="addTitle" type="text" class="form-control"
					placeholder="What needs to be done?" name="title" onkeyup="event.keyCode === 13 && addTodo()" required>
		<ul class="todo-list row row-cols-auto">
			<c:forEach var="todos" items="${ todos }" varStatus="loop">
				<li class="col">
					<div class="view">
						<label>${ todos.title }</label> <button class="btn-destroy btn" onclick="deleteTodo(${ todos.id })">
						<i class="fa-solid fa-trash-can destroy-icon">
							</i>
						</button>
					</div>
							<input id="editId" type="text" class="id-edit" value="${ todos.id }" name="id">
							<input id="editTitle" type="text" class="form-control edit"
								value="${ todos.title }" onkeyup="event.keyCode === 13 && editTodo(this, ${ todos.id })" name="title">
				</li>
			</c:forEach>
		</ul>
	</div>
	<script
		src='<c:url value="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></c:url>'
		integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2"
		crossorigin="anonymous"></script>
	<script src='<c:url value="/assets/js/main.js"></c:url>'></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script type="text/javascript">
		function addTodo() {
			var title = document.querySelector("#addTitle").value;
			$.ajax({
				url : "/DemoEJB/add",
				type : "POST",
				data:{
					title: title
				},
				success : function(response) {
					var todoList = document.querySelector(".todo-list");
					todoList.innerHTML = response;
					document.querySelector("#addTitle").value = "";
				},
				error : function(response) {
				}
			});
		}
		function editTodo(selector, id) {
			isEditing = !isEditing;
			var title = selector.value;
			var id = id;

			if(title){
				$.ajax({
					url : "/DemoEJB/edit",
					type : "POST",
					data:{
						id: id,
						title: title
					},
					success : function(response) {
						var todoList = document.querySelector(".todo-list");
						todoList.innerHTML = response;
					},
					error : function(response) {
					}
				});
			} else {
				deleteTodo(id);
			}
			
		}
		function deleteTodo(id) {
			$.ajax({
				url : "/DemoEJB/delete",
				type : "GET",
				data:{
					id: id
				},
				success : function(response) {
					var todoList = document.querySelector(".todo-list");
					todoList.innerHTML = response;
				},
				error : function(response) {
				}
			});
		}
	</script>
</body>

</html>