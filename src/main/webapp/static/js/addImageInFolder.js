const params = new URLSearchParams(window.location.search);
const imageId = params.get('imageId');
const returnUrl = params.get('returnUrl');

function handleFolderClick(folderElement) {
    const folderId = folderElement.getAttribute('data-folder-id');

    if (imageId) {
        addImageToFolder(folderId);
    } else {
        window.location.href = `/memesWebApp/profile/folders/${folderId}`;
    }
}

function addImageToFolder(folderId) {
    fetch(`/memesWebApp/addImageToFolder`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ imageId, folderId }),
    })
        .then(response => {
            if (response.ok) {
                window.location.href = returnUrl;
            } else {
                alert('Это изображение уже сохранено в папке');
                window.location.href = returnUrl;
            }
        })
        .catch(error => console.error('Ошибка:', error));
}


