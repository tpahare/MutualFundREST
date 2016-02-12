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
					<li role="presentation" class="active"><a
						href="ViewCustomerAccount.do">Manage Customer Account</a></li>
					<li role="presentation"><a href="CreateFund.do">Create
							Fund</a></li>
					<li role="presentation"><a href="TransitionDay.do">Transition
							Day</a></li>
					<li role="presentation"><a href="EmployeeLogout.do">Log
							Out</a></li>
				</c:otherwise>
			</c:choose>

		</ul>
	</div>

	<div class="col-md-1"></div>
	<div class="col-md-5">
		<br> <br>
		<div class="header">
			<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Reset Customer Password
		</div>
		<br> <br>
		<form class="form-horizontal" action="ChangeCustomerPassword.do"
			method="POST">
			<!-- <div class="form-group">
    					<label for="username" class="col-sm-4 control-label">Username</label>
   						 <div class="col-sm-8">
      						<input type="text" name="username" class="form-control" id="username" placeholder="User Name">
   						</div>
  					</div> -->
			<!-- <div class="form-group">
    					<label for="inputPassword" class="col-sm-4 control-label">Old Password</label>
   						 <div class="col-sm-8">
      						<input type="password" name="oldPassword" class="form-control" id="inputPassword" placeholder="Password">
   						</div>
  					</div> -->
			<div class="form-group">
				<label for="inputPassword" class="col-sm-4 control-label">New
					Password</label>
				<div class="col-sm-8">
					<input type="hidden" name="resetpwdusername"
						value="${ resetpwdusername }" /> <input type="password"
						name="newPassword" class="form-control" id="inputPassword"
						placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword1" class="col-sm-4 control-label">Confirm
					Password</label>
				<div class="col-sm-8">
					<input type="password" name="confirmPassword" class="form-control"
						id="inputPassword1" placeholder="Required">
				</div>
			</div>
			<!-- <div class="form-group">
    					<div class="col-sm-offset-4 col-sm-8">
      						<div class="checkbox">
        					<label>
          						<input type="checkbox"> Remember me
        					</label>
      						</div>
    					</div>
  					</div> -->
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<button type="submit" name="action" value="Change"
						class="btn btn-primary">Reset Password</button>
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