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
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        returnFilteredResult();
        successNoty("Saved");
    });
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

function updateTableWithNewData(data) {
    ctx.datatableApi.clear().rows.add(data).draw();
}

function clearFilter() {
    updateTable();
    $(timeFilterForm)[0].reset();
    successNoty("Filter cleared");
}
