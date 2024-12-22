<#macro renderPost publication>
    <div class="post">
        <a href="${contextPath}/publication/${publication.id}" class="post-link">
            <div class="post-header">
                <div class="user-avatar"></div>
                <div class="user-info">
                    <span class="user-name">Username</span>
                    <span class="post-date">${publication.date.toString()}</span>
                </div>
            </div>

            <div class="post-content">
                <p>${publication.content}</p>
            </div>
        </a>

            <div class="post-images">
                <#list publication.images as image>
                    <div class="post-image-container">
                        <img
                                src="${contextPath}/image-download?image_name=${image.name}"
                                alt="Image for publication ${publication.id}"
                                class="post-image"
                                onclick="showAddToFolderButton(event, '${image.id}')">

                        <div class="add-to-folder-container" style="display: none;">
                            <button class="add-to-folder-button" onclick="redirectToFolderSelection('${image.id}')">Добавить в папку</button>
                        </div>
                    </div>
                </#list>
            </div>

        <div class="post-footer">
            <div id="like-container">
                <button class="like-button" publication-id="${publication.id}" liked="${publication.liked?c}">
                    👍 Like (<span id="like-count-${publication.id}">${publication.likesAmount}</span>)
                </button>
            </div>
            <span class="comments-info">Comments: ${publication.commentsAmount}</span>
        </div>
    </div>


    <script>
        function showAddToFolderButton(event, imageId) {
            const postImageContainer = event.target.closest('.post-image-container');
            const buttonContainer = postImageContainer.querySelector('.add-to-folder-container');

            buttonContainer.style.display = 'block';

            selectedImageId = imageId;
        }

        function redirectToFolderSelection(imageId) {
            const currentUrl = window.location.href;

            const folderPageUrl = "/memesWebApp/profile/folders?imageId=" + imageId + "&returnUrl=" + encodeURIComponent(currentUrl);

            window.location.href = folderPageUrl;
        }
    </script>
</#macro>

