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
				<h3>View Fund Information
			</div>
		</div>
		<br> <br> <br> <br>

		<table width="100%" class="table table-bordered table-hover">
			<col width="12.5%">
			<col width="12.5%">
			<col width="12.5%">
			<col width="12.5%">
			<col width="12.5%">
			<col width="12.5%">
			<col width="12.5%">
			<col width="12.5%">
			<tr class="title">
				<td><b>Fund Ticker</b></td>
				<td><b>Fund Name</b></td>
				<td><b>Shares</b></td>
				<td><b>Position Value</b></td>
				<!-- <td>Shares to Sell</td>
				<td>Operation</td>-->
			</tr>
			<form action="SellFund.do" method="POST">
				<c:forEach var="fund" items="${fundInfo}">
					<tr>
						<td><span style="text-align: center"> <input
								type="hidden" name="fundid" value=${ fund.fundid }> ${ fund.ticker }
						</span></td>
						<td><span style="text-align: center"> ${ fund.fundname }
						</span></td>
						<td align="right"><span style="text-align: center"> <fmt:formatNumber
									pattern="#,##0.000" value="${fund.share/1000}"
									maxFractionDigits="3" />
						</span></td>
						<td align="right"><span style="text-align: center"> <fmt:formatNumber
									pattern="#,##0.00" value="${fund.amount/100000}"
									maxFractionDigits="2" />
						</span></td>
					</tr>
				</c:forEach>
			</form>

		</table>

	</div>
	<div class="col-md-2"></div>
	<div class="col-md-1"></div>
</div>
<jsp:include page="template-bottom.jsp" />
