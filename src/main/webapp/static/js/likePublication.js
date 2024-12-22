document.addEventListener("DOMContentLoaded", function () {
    const likeButtons = document.querySelectorAll(".like-button");

    likeButtons.forEach((likeButton) => {
        likeButton.addEventListener("click", function () {
            const publicationId = likeButton.getAttribute("publication-id");
            const isLiked = likeButton.getAttribute("liked") === "true";
            const action = isLiked ? "unlike" : "like";

            console.log({ publicationId, action });

            fetch("/memesWebApp/like-publication", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    publicationId: publicationId,
                    action: action,
                }),
            })
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("Ошибка при обработке запроса");
                    }
                    return response.json();
                })
                .then((data) => {
                    likeButton.setAttribute("liked", data.isLiked ? "true" : "false");
                    document.getElementById(`like-count-${publicationId}`).textContent = data.likeCount;
                })
                .catch((error) => {
                    console.error("Ошибка:", error);
                });
        });
    });
});
