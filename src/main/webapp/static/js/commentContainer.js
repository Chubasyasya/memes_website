

document.querySelectorAll('.commentButton').forEach(function (button) {
    button.addEventListener('click', function () {
        var commentSection = button.nextElementSibling;
        if (commentSection.classList.contains('hidden')) {
            commentSection.classList.remove('hidden');
            commentSection.classList.add('show');
        } else {
            commentSection.classList.remove('show');
            commentSection.classList.add('hidden');
        }
        console.log("show or hide comments")
    });
});