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
                    $('.alert-message').text(response.message);
                } else {
                    window.location.href = window.location.href + "?change=" + response.change;
                }
            }
        });
    });
});