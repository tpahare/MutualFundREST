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
					<li role="presentation" class="active"><a
						href="CreateCustomer.do">Create Customer Account</a></li>
					<li role="presentation"><a href="ViewCustomerAccount.do">Manage
							Customer Account</a></li>
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

	<div class="col-md-6">
		<div class="col-sm-1"></div>
		<div class="col-sm-11">
			<div class="header">
				<h3>Create Customer Account
			</div>
		</div>
		<br> <br> <br> <br>
		<form class="form-horizontal" action="CreateCustomer.do" method="POST">
			<div class="form-group">
				<label for="username" class="col-sm-4 control-label">Username</label>
				<div class="col-sm-8">
					<input type="text" name="username" class="form-control"
						id="username" value="${form.username}" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="firstname" class="col-sm-4 control-label">First
					Name</label>
				<div class="col-sm-8">
					<input type="text" name="firstname" class="form-control"
						id="firstname" value="${form.firstname}" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="lastname" class="col-sm-4 control-label">Last
					Name</label>
				<div class="col-sm-8">
					<input type="text" name="lastname" class="form-control"
						id="lastname" value="${form.lastname}" placeholder="Required">
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
				<label for="inputPassword1" class="col-sm-4 control-label">Confirm
					Password</label>
				<div class="col-sm-8">
					<input type="password" name="confirmpassword" class="form-control"
						id="inputPassword1" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="address1" class="col-sm-4 control-label">Address
					Line1</label>
				<div class="col-sm-8">
					<input type="text" name="addrline1" class="form-control"
						id="address1" value="${form.addrline1}" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<label for="address2" class="col-sm-4 control-label">Address
					Line2</label>
				<div class="col-sm-8">
					<input type="text" name="addrline2" value="${form.addrline1}"
						class="form-control" id="address2" placeholder="Optional">
				</div>
			</div>
			<div class="form-group">
				<label for="city" class="col-sm-4 control-label">City</label>
				<div class="col-sm-8">
					<input type="text" name="city" class="form-control"
						value="${form.city}" id="city" placeholder="Required">
				</div>
			</div>

			<div class="form-group">
				<label for="state" class="col-sm-4 control-label">State</label>
				<div class="col-sm-8">
					<!-- <input type="text" class="form-control" id="state" name="state" placeholder="Required"> -->
					<select class="form-control" name="state">
						<option value="AL">AL</option>
						<option value="AK">AK</option>
						<option value="AZ">AZ</option>
						<option value="AR">AR</option>
						<option value="CA">CA</option>
						<option value="CO">CO</option>
						<option value="CT">CT</option>
						<option value="DE">DE</option>
						<option value="DC">DC</option>
						<option value="FL">FL</option>
						<option value="GA">GA</option>
						<option value="HI">HI</option>
						<option value="ID">ID</option>
						<option value="IL">IL</option>
						<option value="IN">IN</option>
						<option value="IA">IA</option>
						<option value="KS">KS</option>
						<option value="KY">KY</option>
						<option value="LA">LA</option>
						<option value="ME">ME</option>
						<option value="MD">MD</option>
						<option value="MA">MA</option>
						<option value="MI">MI</option>
						<option value="MN">MN</option>
						<option value="MS">MS</option>
						<option value="MO">MO</option>
						<option value="MT">MT</option>
						<option value="NE">NE</option>
						<option value="NV">NV</option>
						<option value="NH">NH</option>
						<option value="NJ">NJ</option>
						<option value="NM">NM</option>
						<option value="NY">NY</option>
						<option value="NC">NC</option>
						<option value="ND">ND</option>
						<option value="OH">OH</option>
						<option value="OK">OK</option>
						<option value="OR">OR</option>
						<option value="PA">PA</option>
						<option value="RI">RI</option>
						<option value="SC">SC</option>
						<option value="SD">SD</option>
						<option value="TN">TN</option>
						<option value="TX">TX</option>
						<option value="UT">UT</option>
						<option value="VT">VT</option>
						<option value="VA">VA</option>
						<option value="WA">WA</option>
						<option value="WV">WV</option>
						<option value="WI">WI</option>
						<option value="WY">WY</option>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label for="zip" class="col-sm-4 control-label">Zip Code</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="zip"
						value="${form.zip}" name="zip" placeholder="Required">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<button type="submit" class="btn btn-primary" name="action"
						value="confirm">Confirm</button>
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