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
					<li role="presentation"><a href="CreateFund.do">Create
							Fund</a></li>
					<li role="presentation" class="active"><a
						href="TransitionDay.do">Transition Day</a></li>
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
				<h3>Transition Day Input
			</div>
			<c:choose>
				<c:when test="${lastDate == null}">
					<h5></h5>
				</c:when>
				<c:otherwise>
					<h5>Note: The last trading date was ${lastDate }</h5>
				</c:otherwise>
			</c:choose>
		</div>
		<br> <br> <br> <br> <br>
		<form class="form-horizontal" action="TransitionDay.do" method="POST">
			<div class="form-group">
				<label for="date" class="col-sm-4 control-label">Choose Date</label>
				<div class="col-sm-8">
					<input type="Date" name="pricedate" value="" class="form-control"
						id="date" placeholder="yyyy-mm-dd">
				</div>
			</div>

			<br> <br>
			<div class="col-sm-2"></div>
			<div class="col-sm-10">
				<table width="100%"
					class="table table-bordered table-hover table-responsive">
					<col width="33%">
					<col width="33%">
					<col width="33%">
					<tr class="title">
						<td><b>Fund Ticker</b></td>
						<td><b>Fund Name</b></td>
						<td><b>Price</b></td>
					</tr>
					<c:forEach var="fund" items="${fundBeans}">
						<tr>
							<td><span style="text-align: left"> <input
									type="hidden" name="fundid" value=${fund.fundid }> ${ fund.ticker }
							</span></td>
							<td><span style="text-align: left"> ${ fund.fundName }
							</span></td>
							<td><span style="text-align: left"> <input
									type="text" size="33" name="price" value=""
									placeholder="Required" />
							</span></td>
						</tr>
					</c:forEach>
				</table>
				<br> <br>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<button type="submit" class="btn btn-primary" name="action"
						value="InputFund">Confirm</button>
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