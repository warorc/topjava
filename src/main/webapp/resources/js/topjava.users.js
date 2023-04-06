const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    );
});

function activateUser(element, id) {
    $.ajax({
        url: ctx.ajaxUrl + "activate",
        type: "POST",
        data: {
            id: id,
            checked: element[0].checked,
        },
        success: function () {
            $("#" + id).attr("data-user-enabled", element[0].checked);
            if (element[0].checked) {
                successNoty("User activated")
            } else {
                successNoty("User deactivated")
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("**********************************");
            console.log(textStatus + ': ' + errorThrown);
            console.log(jqXHR.responseText);
            element[0].checked = !element[0].checked;
            failNoty(jqXHR);
        }
    });
}