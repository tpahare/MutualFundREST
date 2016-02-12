<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="form-group">
	<div class="col-sm-4"></div>
	<div class="col-sm-8">
		<c:forEach var="error" items="${errors}">
			<div class="alert alert-danger" role="alert">
				<span class="glyphicon glyphicon-exclamation-sign"
					aria-hidden="true"></span> <span class="sr-only">Error:</span>
				${error}
			</div>
		</c:forEach>
	</div>
</div>