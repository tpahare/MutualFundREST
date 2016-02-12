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
				</c:when>
				<c:otherwise>
					<!-- <li role="presentation"><a href="ChangePassword.do">Change Password</a></li> -->
					<li role="presentation" class="active"><a
						href="ViewAccount.do">Manage Account</a></li>
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

	<div class="col-md-1"></div>
	<div class="col-md-5">
		<br> <br>
		<div class="header">
			<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Change Password
		</div>
		<br> <br> <br>
		<form class="form-horizontal" action="ChangePassword.do" method="POST">
			<div class="form-group">
				<label for="inputPassword" class="col-sm-4 control-label">Old
					Password</label>
				<div class="col-sm-8">
					<input type="password" name="oldPassword" class="form-control"
						id="inputOldPassword" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword1" class="col-sm-4 control-label">New
					Password</label>
				<div class="col-sm-8">
					<input type="password" name="newPassword" class="form-control"
						id="inputNewPassword" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword2" class="col-sm-4 control-label">Confirm
					Password</label>
				<div class="col-sm-8">
					<input type="password" name="confirmPassword" class="form-control"
						id="inputConfirmPassword" placeholder="Required">
				</div>
			</div>
			<br>

			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<button type="submit" name="action" value="Change"
						class="btn btn-primary">Change Password</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="reset" class="btn btn-default">Reset</button>
				</div>
			</div>
			<jsp:include page="ShowError.jsp" />
		</form>
	</div>
	<div class="col-md-2"></div>
	<div class="col-md-1"></div>
</div>
<jsp:include page="template-bottom.jsp" />