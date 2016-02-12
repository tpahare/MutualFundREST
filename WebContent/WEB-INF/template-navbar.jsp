<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>

	<table cellpadding="10" cellspacing="0">
		<tr>
			<!-- Banner row across the top -->
			<td width=15% bgcolor="#E1E1E1">
				<p align="center">
					<img border="0" src="star_favorite.png" height="75">
				</p>
			</td>
			<td bgcolor="#E1E1E1">&nbsp;</td>
			<td width=85% bgcolor="#E1E1E1">
				<p align="center">
					<span id="maintitle">Favorite Tool</span>
				</p>
			</td>
		</tr>

		<!-- Spacer row -->
		<tr>
			<td bgcolor="#EEEEEE" style="font-size: 5px">&nbsp;</td>
			<td colspan="2" style="font-size: 5px">&nbsp;</td>
		</tr>

		<tr>
			<td bgcolor="#EEEEEE" valign="top" height="500">
				<!-- Navigation bar is one table cell down the left side -->
				<p align="left">

					<c:choose>
						<c:when test="${employee == null}">
							<span class="menu-item"><a href="login.do">Login</a></span>
							<br />
						</c:when>

						<c:otherwise>
							<span class="menu-head">${employee.firstname}
								${employee.lastname}</span>
							<br />
							<span class="menu-item"><a href="createEmployee.do">Create
									Employee</a></span>
							<br />
							<span class="menu-item"><a href="createCustomer.do">Create
									Customer</a></span>
							<br />
							<span class="menu-item"><a href="favoriteList.do">Manage
									Your Favorites</a></span>
							<br />

							<span class="menu-item"><a
								href="ChangeCustomerPassword.do">Change Customer Password</a></span>
							<br />
							<span class="menu-item"><a
								href="ChangeEmployeePassword.do">Change Employee Password</a></span>
							<br />
							<span class="menu-item"><a href="EmployeeLogout.do">Logout</a></span>
							<br />



						</c:otherwise>
					</c:choose>

					<span class="menu-item">&nbsp;</span><br /> <span class="menu-head">Employee
						List</span><br />
					<c:forEach var="e" items="${employees}">
						<span class="menu-item"> <a
							href="showList.do?employeeId=${e.eid}"> ${e.firstname}
								${e.lastname} </a>
						</span>
						<br />
					</c:forEach>
				</p>
			</td>

			<td>
				<!-- Padding (blank space) between navbar and content -->
			</td>
			<td valign="top">