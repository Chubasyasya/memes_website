<#include "base.ftl">

<#macro head>
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/css/home-style.css">
</#macro>

<#macro content>
    <div class="welcome-section">
        <h1>Добро пожаловать в Мемосеть!</h1>
        <p>Лучшая платформа для обмена мемами и весёлыми картинками!</p>
        <a href="${contextPath}/registration" class="btn btn-light btn-lg mt-3">Присоединиться</a>
    </div>


    <div id="memeCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="${contextPath}/static/images/6fd1967b45833629c65c17566e2204f6.jpg" class="d-block w-100" alt="Мем 1">
            </div>
            <div class="carousel-item">
                <img src="${contextPath}/static/images/81fd4393aa51698a77ad686fd7e67e29.jpg" class="d-block w-100" alt="Мем 2">
            </div>
            <div class="carousel-item">
                <img src="${contextPath}/static/images/0fc82c9632cbd657a6b55b0f93493639.jpg" class="d-block w-100" alt="Мем 3">
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#memeCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Предыдущий</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#memeCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Следующий</span>
        </button>
    </div>

    <section class="features-section text-center">
        <div class="container">
            <div class="row">
                <div class="col-lg-4">
                    <i class="bi bi-image feature-icon"></i>
                    <h3>Лучшие мемы</h3>
                    <p>Просматривайте коллекцию самых популярных мемов.</p>
                </div>
                <div class="col-lg-4">
                    <i class="bi bi-pencil-square feature-icon"></i>
                    <h3>Создание контента</h3>
                    <p>Создавайте свои мемы с помощью встроенных инструментов.</p>
                </div>
                <div class="col-lg-4">
                    <i class="bi bi-people feature-icon"></i>
                    <h3>Общайтесь</h3>
                    <p>Делитесь мемами и общайтесь с друзьями.</p>
                </div>
            </div>
        </div>
    </section>

    <section class="about-section text-center">
        <div class="container">
            <h2>О Мемосети</h2>
            <p>Мемосеть – это уникальная социальная сеть, созданная для любителей юмора. Наша миссия – сделать мир веселее, помогая людям делиться мемами и наслаждаться смешными моментами.</p>
        </div>
    </section>

    <footer class="footer">
        <p>2024 Мемосеть.</p>
        <form action="${contextPath}/change-background" method="post">
            <label for="background">Выберите фон:</label>
            <select name="background" id="background">
                <option value="default-background.jpg">Белая тема</option>
                <option value="black-background.jpg">Черная тема</option>
            </select>
            <button type="submit">Изменить фон</button>
        </form>
    </footer>
</#macro>

<@page/>
