<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>CFS for Customer</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="//d3js.org/d3.v3.min.js" charset="utf-8"></script>
<script type="text/javascript"
	src="http://www.amcharts.com/lib/3/amcharts.js"></script>
<script type="text/javascript"
	src="http://www.amcharts.com/lib/3/serial.js"></script>
<script type="text/javascript"
	src="http://www.amcharts.com/lib/3/themes/patterns.js"></script>
</head>
<body>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-10"">
			<div class="page-header">
				<h1>
					Carnegie Financial Services <small>Mutual Fund</small>
				</h1>
			</div>
		</div>
		<div class="col-md-1"></div>
	</div>
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
						<li role="presentation"><a href="BuyFund.do">Buy Fund</a></li>
						<li role="presentation" class="active"><a
							href="ResearchFund.do">Research Fund</a></li>
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
					<h3>Research Fund</h3>
				</div>

			</div>
			<br> <br>
			<form class="navbar-form navbar-left" role="search"
				action="ResearchFund.do" method="POST">
				<div class="form-group">
					<input type="text" class="form-control"
						placeholder="Search by Fund Name" name="fundname">
				</div>
				<button type="submit" class="btn btn-default" name="action"
					value="SearchFundName">Search</button>

			</form>

			<form class="navbar-form navbar-left" role="search"
				action="ResearchFund.do" method="POST">
				<button type="submit" class="btn btn-default" name="action"
					value="ShowAll">Show All Funds</button>
			</form>
			<br> <br> <br> <br>
			<div class="list-group">
				<c:choose>
					<c:when test="${fundHistory == null}">
						<c:forEach var="fund" items="${fundList}">
							<a href="ResearchFund.do?fundId=<c:out value ="${fund.fundid}"/>"
								class="list-group-item avtive">${fund.fundName }
								(${fund.ticker })</a>
							<br>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<h2>${fundName }</h2>
						<table class="table">
							<thead>
								<tr>
									<th>Date</th>
									<th>Price</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="datePrice" items="${fundBeans}">
									<tr>
										<td>${datePrice.date }</td>
										<td><fmt:formatNumber pattern="#,##0.00"
												value="${datePrice.price}" maxFractionDigits="2" /></td>
									</tr>
								</c:forEach>
							</tbody>
							<script type="text/javascript">
					AmCharts.makeChart("chartdiv",
				{
					"type": "serial",
					"categoryField": "category",
					"startDuration": 1,
					"theme": "patterns",
					"categoryAxis": {
						"gridPosition": "start"
					},
					"trendLines": [],
					"graphs": [
						{
							"balloonText": "[[title]] as of [[category]] was valued at $[[value]]",
							"bullet": "round",
							"id": "",
							"title": "${fundName }",
							"valueField": "column-1"
						}
					],
					"guides": [],
					"valueAxes": [
						{
							"id": "ValueAxis-1",
							"title": "Price ($)"
						}
					],
					"allLabels": [],
					"balloon": {},
					"legend": {
						"enabled": false,
						"useGraphSettings": false
					},
					"titles": [
						{
							"id": "Title-1",
							"size": 15,
							"text": "How has ${fundName } been doing?"
						}
					],
					"dataProvider": [
					 				<c:forEach var="graph" items="${fundHistory}">
									{
										"category": "${graph.pricedate}",
										"column-1": "${graph.price/100}",
									},						
								</c:forEach>
									]

						
					}
					);
				</script>

							<div id="chartdiv"
								style="width: 100%; height: 400px; background-color: #FFFFFF;"></div>
						</table>

					</c:otherwise>
				</c:choose>
				<jsp:include page="ShowError.jsp" />
			</div>

		</div>

		<script src="js/jquery.js"></script>
		<script src="js/bootstrap.min.js"></script>
</body>
</html>