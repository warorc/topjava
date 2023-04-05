const mealAjaxUrl = "profile/meals/";
const timeFilterForm = $('#filterForm');

const ctx = {
    ajaxUrl: mealAjaxUrl,
    filterAjaxUrl: mealAjaxUrl + "filter",
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
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
                    "desc"
                ]
            ]
        })
    );
});

function saveMeal() {
    save(returnFilteredResult);
}

function returnFilteredResult() {
    $.ajax({
        url: ctx.ajaxUrl + "filter",
        type: "GET",
        data: timeFilterForm.serialize(),
    }).done(function (data) {
        updateTableWithNewData(data);
    });
}

function filterTable() {
    returnFilteredResult();
    successNoty("Filtered");
}

function clearFilter() {
    updateTable();
    $(timeFilterForm)[0].reset();
    successNoty("Filter cleared");
}


