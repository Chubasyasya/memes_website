let selectedImageId = null;

function showAddToFolderButton(event, imageId) {
    event.stopPropagation();

    const postImageContainer = event.target.closest('.post-image-container');
    const buttonContainer = postImageContainer.querySelector('.add-to-folder-container');

    buttonContainer.style.display = 'block';

    selectedImageId = imageId;
}

function redirectToFolderSelection(imageId, event) {
    event.preventDefault();

    const currentUrl = window.location.href;

    const folderPageUrl = "/memesWebApp/profile/folders?imageId=" + imageId + "&returnUrl=" + encodeURIComponent(currentUrl);

    window.location.href = folderPageUrl;
}

document.querySelectorAll('.post-link').forEach(postLink => {
    postLink.addEventListener('click', function(event) {
        if (selectedImageId === null) {
            window.location.href = event.currentTarget.href;
        }
    });
});
