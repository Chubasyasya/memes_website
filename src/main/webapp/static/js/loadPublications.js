let offset = 0;
const limit = 10;

function loadPublications(search = "", sort = "date-desc") {
    console.log("Search:", search);
    console.log("Sort:", sort);
    $.ajax({
        url: "/memesWebApp/feedNext",
        type: "GET",
        data: { offset: offset, limit: limit, search: search, sort: sort },
        success: function (data) {
            console.log("Received JSON:", data);

            if (offset === 0) {
                $("#feed").empty();
            }

            data.forEach(postHtml => {
                $("#feed").append(postHtml);
            });

            offset += limit;
        },
        error: function (xhr, status, error) {
            console.error("Error loading publications:", error);
        }
    });
}

$("#searchButton").on("click", function () {
    const search = $("#searchInput").val();
    const sort = $("#sortFilter").val();
    offset = 0;
    loadPublications(search, sort);
});

$("#loadMore").on("click", function () {
    const search = $("#searchInput").val();
    const sort = $("#sortFilter").val();
    loadPublications(search, sort);
});


$(document).ready(function () {
    loadPublications();
});
