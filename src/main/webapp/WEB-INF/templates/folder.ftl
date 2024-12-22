<#include "base.ftl">
<#macro title>
    Folder
</#macro>

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/folder-style.css">
</#macro>

<#macro content>

    <script>
        let tablePage = 1;
        const limit = 10;
        const folderId = "${folderId}";
        let loadMoreButton;
        const imageContainer = document.createElement("div");
        imageContainer.id = "image-container";

        function loadImages() {
            const url = `${contextPath}/folder/images?tablePage=` + tablePage + `&limit=` + limit + `&folderId=`+folderId;
            console.log(url);
            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Сервер вернул ошибку: ' + response.status);
                    }
                    return response.json();
                })
                .then(images => {
                    if (images.length > 0) {
                        images.forEach(image => {
                            let name = image.name;
                            console.log(name);
                            fetch(`${contextPath}/image-download?image_name=` + name)
                                .then(response => response.blob())
                                .then(blob => {
                                    const imgUrl = URL.createObjectURL(blob);
                                    const img = document.createElement("img");
                                    img.src = imgUrl;
                                    imageContainer.appendChild(img);
                                });
                        });

                        tablePage++;
                    } else {
                        console.log('Все изображения загружены.');
                        loadMoreButton.style.display = "none";
                    }
                })
                .catch(error => console.error('Ошибка в загрузке изображений:', error));
        }

        window.onload = function() {
            loadImages();
        }

        loadMoreButton = document.createElement("button");
        loadMoreButton.textContent = "Загрузить еще";
        loadMoreButton.id = "load-more-button";
        loadMoreButton.addEventListener("click", loadImages);

        document.body.appendChild(imageContainer);
        document.body.appendChild(loadMoreButton);
    </script>

</#macro>
<@page/>
