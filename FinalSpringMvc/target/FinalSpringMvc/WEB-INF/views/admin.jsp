<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false" %>

<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
	
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
		<title>Admin Page</title>
		<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	</head>
	<body>
		<div class="w3-container">
			<h2 class="w3-large">Users want you to approve their registration</h2>
			<table class="w3-table-all">
				<tr>
					<th class="w3-center w3-small">First Name</th>
					<th class="w3-center w3-small">Last Name</th>
					<th class="w3-center w3-small">Email</th>
					<th class="w3-center w3-small">Approve</th>
				</tr>
				
				<c:forEach var="student" items="${users}">
					<tr>
						<td class="w3-center w3-tiny">${student.firstName}</td>
						<td class="w3-center w3-tiny">${student.lastName}</td>
						<td class="w3-center w3-tiny">${student.email}</td>
						<td class="w3-center w3-row" style="display: flex; width: fit-content; margin: 0 auto;">
							<form action="accept" method="post">
								<input type="hidden" name="id" value="${student.id}">
								<input type="hidden" name="email" value="${student.email}">
								<button type="submit" class="w3-button w3-tiny">Accept</button>
							</form>
							<form action="reject" method="post">
								<input type="hidden" name="id" value="${student.id}">
								<input type="hidden" name="email" value="${student.email}">
								<button type="submit" class="w3-button w3-tiny">Reject</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>