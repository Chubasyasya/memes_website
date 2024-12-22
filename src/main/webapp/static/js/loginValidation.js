document.getElementById('loginForm').addEventListener('submit', function(event) {
    var login = document.getElementById('login').value;
    var password = document.getElementById('password').value;
    const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    if (login.trim() === '' || login.length < 3) {
        showAlert('Логин должен быть не пустым и содержать минимум 3 символа.');
        event.preventDefault();
        return;
    }

    if(!emailPattern.test(login)){
        showAlert('Логин не корректный');
        event.preventDefault();
        return;
    }

    if (password.trim() === '' || password.length < 6) {
        showAlert('Пароль должен содержать минимум 6 символов.');
        event.preventDefault();
        return;
    }


    showAlert('Форма отправлена!');
});

function showAlert(message, duration = 3000) {
    const alertDiv = document.getElementById('alertMessage');
    alertDiv.textContent = message;
    alertDiv.style.display = 'block';

    setTimeout(() => {
        alertDiv.style.display = 'none';
    }, duration);
}


