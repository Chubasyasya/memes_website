accountId = 1000;
function loadComments() {
    $.ajax({
        url: "/memesWebApp/comments",
        type: "GET",
        data: { accountId: accountId},
        success: function (data) {
            console.log("Received JSON:", data);
            data.forEach(postHtml => {
                $("#post").append(postHtml);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error loading publications:", error);
        }
    });
}