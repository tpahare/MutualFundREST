<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-head.jsp" />
<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-2">

		<ul class="nav nav-pills nav-stacked">

			<c:choose>
				<c:when test="${employee == null}">
					<li role="presentation" class="active"><a
						href="EmployeeLogin.do">Login</a></li>
				</c:when>

				<c:otherwise>
					<li role="presentation"><a href="ChangeEmployeePassword.do">Change
							Password</a></li>
					<li role="presentation"><a href="createEmployee.do">Create
							Employee Account</a></li>
					<li role="presentation"><a href="CreateCustomer.do">Create
							Customer Account</a></li>
					<li role="presentation"><a href="ViewCustomerAccount.do">Manage
							Customer Account</a></li>
					<li role="presentation" class="active"><a href="CreateFund.do">Create
							Fund</a></li>
					<li role="presentation"><a href="TransitionDay.do">Transition
							Day</a></li>
					<li role="presentation"><a href="EmployeeLogout.do">Log
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
				<h3>Create Fund</h3>
			</div>
		</div>
		<br> <br> <br> <br> <br>
		<form class="form-horizontal">
			<div class="form-group">
				<label for="fundname" class="col-sm-4 control-label">Fund
					Name</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="fundname"
						name="fundName" value="${form.fundName }" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="ticker" class="col-sm-4 control-label">Fund
					Ticker</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="ticker" name="ticker"
						value="${form.ticker }" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<button type="submit" class="btn btn-primary" name="action"
						value="CreateFund">Create</button>
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