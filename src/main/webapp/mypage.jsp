<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Vending machine</title>
    <script src="https://code.jquery.com/jquery-1.10.2.js"
            type="text/javascript"></script>
    <script src="js/script.js" type="text/javascript"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/bootstrap.min.js"></script>
</head>
<body>
<div class="container pt-5">
    <div class="row">
        <div class="col-md-6">
            <c:if test="${not empty products}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>#</td>
                        <td>Name</td>
                        <td>Quantity</td>
                        <td>Price</td>
                    </tr>
                    </thead>
                    <c:forEach var="product" items="${products}">
                        <tr>
                            <td>${product.productNumber}</td>
                            <td>${product.name}</td>
                            <td>${product.quantity}</td>
                            <td>${product.price}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
        <div class="col-md-6">
            <form>
                <div class="form-group row">
                    <label for="balance" class="col-sm-2 col-form-label">Balance</label>
                    <div class="col-sm-10">
                        <input type="text" readonly class="form-control" id="balance" value="0.00">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="productNumber" class="col-sm-2 col-form-label">Product number</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="productNumber" placeholder="Product number">
                    </div>
                </div>
            </form>

            <form action="/machine" method="post" id="productsForm" role="form">
                <div class="mb-3">
                    <div class="btn-group" role="group">
                        <button id="dollar" type="button" class="btn btn-secondary money">Dollar</button>
                        <button id="nickel" type="button" class="btn btn-secondary money">Nickel</button>
                        <button id="quarter" type="button" class="btn btn-secondary money">Quarter</button>
                        <button id="dime" type="button" class="btn btn-secondary money">Dime</button>
                    </div>
                </div>

                <div>
                    <button id="buy" type="button" name="buyButton" class="btn btn-success">Buy</button>
                    <button id="reset" type="button" class="btn btn-danger">Reset</button>
                </div>
            </form>

            <c:if test="${not empty message}">
                <div class="alert alert-danger alert-message">
                        ${message}
                </div>
            </c:if>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <c:if test="${not empty change}">
                <h2>Your change:</h2>
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <td>Nominal</td>
                        <td>Quantity</td>
                    </tr>
                    </thead>
                    <c:forEach var="cur" items="${change}">
                        <tr>
                            <td>${cur.nominal}</td>
                            <td>${cur.quantity}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </div>
    </div>

</div>
</body>
</html>
