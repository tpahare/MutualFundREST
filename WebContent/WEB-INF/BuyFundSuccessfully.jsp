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

	<div class="col-md-1"></div>
	<div class="col-md-5">
		<br> <br> <br> <br>


		<form class="form-horizontal">
			<div class="form-group">
				<div class="alert alert-success" role="alert">
					<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
					Buy Fund Successfully!
				</div>
			</div>
		</form>
	</div>
	<div class="col-md-2"></div>
	<div class="col-md-1"></div>
</div>
<jsp:include page="template-bottom.jsp" />