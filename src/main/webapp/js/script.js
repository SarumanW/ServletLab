$(document).ready(function () {
    $('.money').click(function (event) {
        var input = event.target;
        $.ajax({
            url: 'machine',
            method: 'POST',
            data: {
                nominal: $(input).attr('id')
            },
            success: function (amount) {
                var balanceInput = $('#balance');
                var currentBalance = $(balanceInput).val();
                $(balanceInput).val(+currentBalance + +amount);
            }
        });
    });

    $('#buy').click(function () {
        var productNumber = $('#productNumber').val();
        $.ajax({
            url: 'machine',
            method: 'POST',
            data: {
                productNumber: productNumber
            },
            success: function (response) {
                if (!response.success) {
                    var message = $('.alert-message');
                    $(message).text(response.message);
                    $(message).removeClass('d-none');
                    $(message).show();
                } else {
                    var param = JSON.stringify(response.change);
                    window.location = "http://localhost:8082/machine?param=" + encodeURIComponent(param);
                }
            }
        });
    });

    $('#reset').click(function () {
        $('#productNumber').val('');
        $('#balance').val('0.00');
        $('.change').hide();
        $('.alert-message').hide();

        $.ajax({
            url: 'machine',
            method: 'POST',
            data: {
                reset: true
            },
            success: function (response) {
                if(response.change !== undefined) {
                    var param = JSON.stringify(response.change);
                    window.location = "http://localhost:8082/machine?param=" + encodeURIComponent(param);
                }
            }
        });
    });
});