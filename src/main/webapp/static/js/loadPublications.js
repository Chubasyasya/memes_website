let offset = 0;
const limit = 10;

function loadPublications() {
    $.ajax({
        url: "/memesWebApp/feedNext",
        type: "GET",
        data: { offset: offset, limit: limit },
        success: function (data) {
            console.log("Received JSON:", data);
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

$("#loadMore").on("click", function () {
    loadPublications();
});

$(document).ready(function () {
    loadPublications();
});