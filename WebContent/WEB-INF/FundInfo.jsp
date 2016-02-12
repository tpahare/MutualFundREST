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
					<li role="presentation" class="active"><a
						href="ViewAccount.do">Manage Account</a></li>
					<li role="presentation"><a href="BuyFund.do">Buy Fund</a></li>
					<li role="presentation"><a href="ResearchFund.do">Research
							Fund</a></li>
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
				<h3>View Fund Information</h3>
				<h5>Note: You can only sell one fund at a time (each "sell"
					button corresponds to only that specific fund)</h5>
			</div>
			<br>

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
					<td><b>Shares to Sell</b></td>
					<td><b>Operation</b></td>
				</tr>
				<c:forEach var="fund" items="${fundInfo}">
					<form action="SellFund.do" method="POST">
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
							<td align="right"><span style="text-align: center"> <input
									type="text" name="shareSell" value="" />
							</span></td>
							<td align="center"><input type="hidden" name="sellFund"
								value="${ customer.cid }" /> <input type="submit" name="button"
								value="Sell" class="btn btn-default" /></td>
						</tr>

					</form>
				</c:forEach>
				<jsp:include page="ShowError.jsp" />
			</table>

		</div>
		<div class="col-md-2"></div>
		<div class="col-md-1"></div>
	</div>
	<jsp:include page="template-bottom.jsp" />