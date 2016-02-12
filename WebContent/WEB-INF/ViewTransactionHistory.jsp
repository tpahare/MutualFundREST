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
				<h3>Transaction History</h3>
			</div>
		</div>
		<br> <br> <br> <br>

		<table width="100%"
			class="table table-bordered table-hover table-responsive">
			<col width="16.67%">
			<col width="16.67%">
			<col width="16.67%">
			<col width="16.67%">
			<col width="16.67%">
			<col width="16.67%">
			<tr class="title">
				<td><b>Transaction Date</b></td>
				<td><b>Operation</b></td>
				<td><b>Fund Name</b></td>
				<td><b>Shares</b></td>
				<td><b>Price</b></td>
				<td><b>Amount</b></td>
			</tr>
			<c:forEach var="transaction" items="${transactions}">
				<tr>

					<td><span style="text-align: left"> <input
							type="hidden" value="${ count }"> ${ transaction.executedate }
					</span></td>
					<td><span style="text-align: left"> ${ transaction.transactiontype }
					</span></td>
					<td><span style="text-align: left"> ${ transaction.fundname }
					</span></td>

					<td align="right"><c:choose>
							<c:when test="${transaction.shares > 0}">
								<span style="text-align: left"> <fmt:formatNumber
										pattern="#,##0.000" value="${transaction.shares}"
										maxFractionDigits="3" />
								</span>
							</c:when>
						</c:choose></td>
					<td align="right"><c:choose>
							<c:when test="${transaction.price > 0}">
								<span style="text-align: left"> <fmt:formatNumber
										pattern="#,##0.00" value="${transaction.price}"
										maxFractionDigits="2" />
								</span>
							</c:when>
						</c:choose></td>
					<td align="right"><span style="text-align: left"> <fmt:formatNumber
								pattern="#,##0.00" value="${transaction.amount}"
								maxFractionDigits="2" />
					</span></td>
				</tr>

			</c:forEach>

		</table>

	</div>
	<div class="col-md-2"></div>
	<div class="col-md-1"></div>
</div>
<jsp:include page="template-bottom.jsp" />
