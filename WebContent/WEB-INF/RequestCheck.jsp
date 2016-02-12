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

	<div class="col-md-6">
		<br> <br> <br>
		<div class="col-sm-1"></div>
		<div class="col-sm-11">
			<div class="header">
				<h3>Request Check
			</div>
		</div>
		<br> <br> <br> <br> <br>
		<form class="form-horizontal" action="RequestCheck.do" method="POST">
			<!-- <div class="form-group">
    					<label for="username" class="col-sm-4 control-label">Customer Username</label>
    					<div class="col-sm-8">
      						<input type="text" name="username" class="form-control" id="username">
    					</div>
  					</div> -->
			<div class="form-group">
				<label for="amount" class="col-sm-4 control-label">Request
					Amount</label>
				<div class="col-sm-8">
					<%-- <input type="hidden" name="depositcheckcid" value="${ depositcheckcid }" /> --%>
					<input type="text" name="amount" class="form-control" id="amount"
						placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<button type="submit" class="btn btn-primary" name="action"
						value="RequestCheck">Confirm</button>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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