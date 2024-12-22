<#macro header>
    <div class="header-logged-in stylish-header">
        <div class="logo">
            <a href="${contextPath}" class="logo-link">MYMEMES</a>
        </div>
        <div class="main-links">
            <a href="${contextPath}/generate-random-meme" class="auth-button home-button">Генератор мемов</a>
            <a href="${contextPath}/home" class="auth-button home-button">Главная</a>
            <a href="${contextPath}/feed" class="auth-button feed-button">Лента</a>
        </div>
        <div class="user-info">
            <div class="dropdown">
                <button class="btn btn-link dropdown-toggle" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="${contextPath}/static/images/profile-icon.png" alt="Профиль" class="profile-icon">
                </button>

                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <li><a class="dropdown-item" href="${contextPath}/profile">Профиль</a></li>
                    <li><a class="dropdown-item" href="${contextPath}/profile/settings">Настройки</a></li>
                    <li><a class="dropdown-item" href="${contextPath}/profile/folders">Папки</a></li>
                </ul>
            </div>
        </div>
    </div>
</#macro>
