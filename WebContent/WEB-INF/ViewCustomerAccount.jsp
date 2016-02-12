<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

	<div class="col-md-6">
		<br> <br> <br>
		<div class="col-sm-1"></div>
		<div class="col-sm-11">
			<div class="header">
				<h3>Manage Customer Account
			</div>

			<c:choose>
				<c:when test="${Date == null}">
					<h5></h5>
				</c:when>
				<c:otherwise>
					<h5>Note: The last trading date was ${Date }</h5>
				</c:otherwise>
			</c:choose>

			<form class="navbar-form navbar-left" role="search"
				action="ViewCustomerAccountSearch.do" method="POST">
				<div class="form-group">
					<input type="text" class="form-control"
						placeholder="Search by Username" name="username">
				</div>
				<button type="submit" class="btn btn-default">Search</button>
			</form>

			<form class="navbar-form navbar-left" role="showall"
				action="ViewCustomerAccount.do" method="POST">
				<button type="submit" class="btn btn-default">Show All
					Customers</button>
			</form>


			<br>
			<br>
			<br>
			<br>
			<div>
				<jsp:include page="ShowError.jsp" />
			</div>

			<table width="100%"
				class="table table-bordered table-hover table-responsive">
				<col width="10%">
				<col width="10%">
				<col width="30%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<col width="10%">
				<c:choose>
					<c:when test="${customerList.size() > 0}">
						<tr class="title">
							<td><b>Username</b></td>
							<td><b>Name</b></td>
							<td><b>Address</b></td>
							<td><b>Cash (in $)</b></td>
							<td><b>Fund Information</b></td>
							<td><b>Reset Password</b></td>
							<td><b>Deposit Check</b></td>
							<td><b>Transaction History</b></td>
						</tr>
					</c:when>

					<c:otherwise>

					</c:otherwise>
				</c:choose>
				<c:forEach var="customer" items="${customerList}">

					<tr class>

						<td><span style="text-align: center"> ${ customer.username }
						</span></td>
						<td><span style="text-align: center"> ${ customer.firstname }
								${ customer.lastname } </span></td>
						<td><span style="text-align: center"> ${ customer.addrline1 }
								${ customer.addrline2} <br> ${ customer.city }, ${ customer.state }
						</span></td>
						<td align="right"><span style="text-align: left"> <fmt:formatNumber
									pattern="#,##0.00" value="${customer.cash/100}"
									maxFractionDigits="2" />
						</span></td>
						<td align="center">
							<form action="FundInfoEmployee.do" method="POST">
								<input type="hidden" name="customerid" value="${ customer.cid }" />
								<input type="submit" name="button" value="View"
									class="btn btn-default" />
							</form>
						</td>
						<td align="center">
							<form action="ChangeCustomerPassword.do" method="POST">
								<input type="hidden" name="resetpwdusername"
									value="${ customer.username }" /> <input type="submit"
									name="button" value="Reset" class="btn btn-default" />
							</form>
						</td>
						<td align="center">
							<form action="DepositCheck.do" method="POST">
								<input type="hidden" name="depositcheckcid"
									value="${ customer.cid }" /> <input type="submit"
									name="button" value="Deposit" class="btn btn-default" />
							</form>
						</td>
						<td align="center">
							<form action="ViewTransactionHistory.do" method="POST">
								<input type="hidden" name="viewtransactionhistorycid"
									value="${ customer.cid }" /> <input type="submit"
									name="button" value="View" class="btn btn-default" />
							</form>
						</td>
					</tr>
				</c:forEach>

			</table>
		</div>
	</div>
	<div class="col-md-2"></div>
	<div class="col-md-1"></div>
</div>
<jsp:include page="template-bottom.jsp" />
