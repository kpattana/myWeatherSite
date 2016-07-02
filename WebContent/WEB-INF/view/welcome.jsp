<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weather Site</title>
</head>
<body>
	<div class="form-container">

		<h2>${message}</h2>

		<form:form method="POST" modelAttribute="city" class="form-horizontal">

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="name">Select
						City</label>
					<div class="col-md-7">
						<form:select path="name" id="name" class="form-control input-sm">
							<form:option value="">Select City</form:option>
							<form:options items="${cities}" />
						</form:select>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-actions floatRight">
					<input type="submit" value="Get Weather"
						class="btn btn-primary btn-sm">
				</div>
			</div>

			<c:choose>
				<c:when test="${display=='1'}">
					<h2>Weather Forecast</h2>
					<table>
						<tr>
							<td>Todays date</td>
							<td>${date}</td>
						</tr>
						<tr>
							<td>City Selected</td>
							<td>${cityName}</td>
						</tr>
						<tr>
							<td>Current forecast</td>
							<td>${forecast}</td>
						</tr>
						<tr>
							<td>Temperature (C)</td>
							<td>${celsius}</td>
						</tr>
						<tr>
							<td>Temperature (F)</td>
							<td>${faranite}</td>
						</tr>
						<tr>
							<td>Sunrise time</td>
							<td>${sunrise}</td>
						</tr>
						<tr>
							<td>Sunset time</td>
							<td>${sunset}</td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${display=='0'}">
							<div>
								<h3>Something seems to have gone wrong</h3>
								<h3>Error message received: ${error}</h3>
							</div>
						</c:when>
					</c:choose>
				</c:otherwise>
			</c:choose>

		</form:form>
	</div>
</body>
</html>