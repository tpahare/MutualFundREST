<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template-head.jsp" />

<%-- <div id="mainPanel">
			
				<div id="welcome">
						<span id="welcome-text">Oops</span>								
				</div>
				<div>
			    	<table class="centertable">
			    		<tr> <td colspan="2"> <hr /> </td> </tr>
			    		<c:forEach var="error" items="${errors}">
				        <tr>
				            <td  colspan="2" style="color: red">${error}</td>
				        </tr>
				        </c:forEach>
				        <tr>
				            <td colspan="2" style="text-align: center;">
				                <input type="button" value="Home Page" onclick="window.location.href=('Index.do')" class="btn">
				            </td>
				        </tr>
				        <tr> <td colspan="2"> <hr /> </td> </tr>
			        </table>
			    </div>  
			   
		</div> --%>
<div class="row">
	<div class="col-md-1"></div>
	<div class="col-md-2">

		<ul class="nav nav-pills nav-stacked">

		</ul>
	</div>

	<div class="col-md-1"></div>
	<div class="col-md-5">
		<br> <br> <br> <br>


		<form class="form-horizontal">
			<div class="form-group">
				<div class="alert alert-success" role="alert">
					<span class="glyphicon glyphicon-ok-sign" aria-hidden="true"></span>
					${errors} <input type="button" value="Home Page"
						onclick="window.location.href=('Index.do')" class="btn">
				</div>
			</div>
		</form>
	</div>
	<div class="col-md-2"></div>
	<div class="col-md-1"></div>
</div>


<jsp:include page="template-bottom.jsp" />

