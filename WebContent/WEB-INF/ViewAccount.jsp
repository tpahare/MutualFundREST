<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
		<br> <br>
		<div class="col-sm-1"></div>
		<div class="col-sm-11">
			<div class="header">
				<h3>Manage Account
			</div>
			<c:choose>
				<c:when test="${Date == null}">
					<h5></h5>
				</c:when>
				<c:otherwise>
					<h5>Note: The last trading date was ${Date }</h5>
				</c:otherwise>
			</c:choose>
			<table width="100%"
				class="table table-bordered table-hover table-responsive">
				<%-- 				<col width="10%"> --%>
				<col width="12.5%">
				<col width="12.5%">
				<col width="12.5%">
				<col width="12.5%">
				<col width="12.5%">
				<col width="12.5%">
				<col width="12.5%">
				<col width="12.5%">
				<col width="12.5%">
				<tr class="title">
					<td><b>Username</b></td>
					<td><b>Name</b></td>
					<td><b>Address</b></td>
					<td><b>Cash</b></td>
					<td><b>Fund Information</b></td>
					<td><b>Change Password</b></td>
					<td><b>Request Check</b></td>
					<td><b>Transaction History</b></td>
				</tr>
				<tr>
					<td><span style="text-align: center"> ${ customer.username }
					</span></td>
					<td><span style="text-align: center"> ${ customer.firstname }
							${ customer.lastname } </span></td>
					<td><span style="text-align: center"> ${ customer.addrline1 }
							${ customer.addrline2 } ${ customer.city }, ${ customer.state } </span></td>
					<td align="right"><span style="text-align: center"> <fmt:formatNumber
								pattern="#,##0.00" value="${customer.cash/100}"
								maxFractionDigits="2" />
					</span></td>
					<td align="center">
						<form action="FundInfo.do" method="POST">
							<input type="hidden" name="sellFund" value="${ customer.cid }" />
							<input type="submit" name="action" value="View"
								class="btn btn-default" />
						</form>
					</td>
					<td align="center">
						<form action="ChangePassword.do" method="POST">
							<input type="hidden" name="changepwdusername"
								value="${ customer.username }" /> <input type="submit"
								name="button" value="Change" class="btn btn-default" />
						</form>
					</td>
					<td align="center">
						<form action="RequestCheck.do" method="POST">
							<input type="hidden" name="requestcheckcid"
								value="${ customer.cid }" /> <input type="submit" name="button"
								value="Request" class="btn btn-default" />
						</form>
					</td>
					<td align="center">
						<form action="ViewSelfTransactionHistory.do" method="POST">
							<input type="hidden" name="viewselftransactionhistorycid"
								value="${ customer.cid }" /> <input type="submit" name="button"
								value="View" class="btn btn-default" />
						</form>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="col-md-2"></div>
	<div class="col-md-1"></div>
</div>
<jsp:include page="template-bottom.jsp" />