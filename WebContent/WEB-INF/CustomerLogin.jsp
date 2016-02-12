<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-head.jsp" />
<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-2">

		<ul class="nav nav-pills nav-stacked">

			<c:choose>
				<c:when test="${customer == null}">
					<li role="presentation" class="active"><a
						href="CustomerLogin.do">Login</a></li>
					<li role="presentation"><a href="Index.do">Back to
							Homepage</a></li>
				</c:when>
				<c:otherwise>
					<!-- <li role="presentation"><a href="ChangePassword.do">Change Password</a></li> -->
					<li role="presentation"><a href="ViewAccount.do">View
							Account</a></li>
					<li role="presentation"><a href="BuyFund.do">Buy Fund</a></li>
					<!-- <li role="presentation"><a href="ViewSelfTransactionHistory.do">Transaction History</a></li> -->
					<li role="presentation"><a href="ResearchFund.do">Research
							Fund</a></li>
					<!-- <li role="presentation"><a href="RequestCheck.do">Request Check</a></li> -->
					<li role="presentation"><a href="CustomerLogout.do">Log
							Out</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>

	<div class="col-md-5">
		<br> <br>
		<div class="col-sm-1"></div>
		<div class="col-sm-11">
			<div class="header">
				<h3>Customer Login
			</div>
		</div>
		<br> <br> <br> <br>

		<form class="form-horizontal" action="CustomerLogin.do" method="POST">
			<div class="form-group">
				<label for="username" class="col-sm-4 control-label">Username</label>
				<div class="col-sm-8">
					<input type="text" name="username" class="form-control"
						id="username" value="${form.username}" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-sm-4 control-label">Password</label>
				<div class="col-sm-8">
					<input type="password" name="password" class="form-control"
						id="inputPassword" placeholder="Required">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<button type="submit" class="btn btn-primary" name="action"
						value="Login">Sign in</button>
				</div>
			</div>
			<jsp:include page="ShowError.jsp" />
		</form>
	</div>
	<div class="col-md-3"></div>
	<div class="col-md-1"></div>
</div>
<jsp:include page="template-bottom.jsp" />