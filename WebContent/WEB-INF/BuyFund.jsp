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
					<li role="presentation"><a href="ViewAccount.do">Manage
							Account</a></li>
					<li role="presentation" class="active"><a href="BuyFund.do">Buy
							Fund</a></li>
					<li role="presentation"><a href="ResearchFund.do">Research
							Fund</a></li>
					<li role="presentation"><a href="CustomerLogout.do">Log
							Out</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>

	<div class="col-md-5">
		<br> <br>
		<div class="col-sm-1"></div>
		<div class="col-sm-11">
			<div class="header">
				<h3>Buy Fund</h3>
			</div>
			<br>

			<form class="form-horizontal" action="BuyFund.do" method="POST">
				<div class="form-group">
					<label for="symbol" class="col-sm-4 control-label">Fund
						Symbol</label>
					<div class="col-sm-8">
						<select class="form-control" name="fundsymbol">
							<c:forEach var="fund" items="${fundList}">

								<option value="${ fund.ticker }">${ fund.ticker }</option>

							</c:forEach>

						</select>

					</div>
				</div>

				<div class="form-group">
					<label for="amount" class="col-sm-4 control-label">Dollar
						Amount</label>
					<div class="col-sm-8">
						<input type="text" name="money" class="form-control" id="amount"
							placeholder="Required">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-4 col-sm-8">
						<button type="submit" name="action" value="BuyFund"
							class="btn btn-primary">Confirm Purchase</button>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<button type="reset" class="btn btn-default">Reset</button>
					</div>
				</div>
				<jsp:include page="ShowError.jsp" />
				<h5>Please use the below table for reference to buy funds</h5>
				<br>
				<div class="list-group">
					<table width="100%"
						class="table table-bordered table-hover table-responsive">
						<col width="50%">
						<col width="50%">
						<tr class="title">
							<td><b>Fund Name</b></td>
							<td><b>Fund Ticker</b></td>
						</tr>
						<c:forEach var="fund" items="${fundList}">
							<tr>
								<td><span style="text-align: left"> ${ fund.fundName }
								</span></td>
								<td><span style="text-align: left"> ${ fund.ticker }
								</span></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</form>
		</div>
		<div class="col-md-3"></div>
		<div class="col-md-1"></div>
	</div>
	<jsp:include page="template-bottom.jsp" />