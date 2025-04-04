document.addEventListener("DOMContentLoaded", function() {
    const searchInput = document.querySelector('input[type="search"]');
    const searchButton = document.querySelector('button[type="submit"]');

    // Проверка, пустое ли поле
    function toggleButtonState() {
        // Дизейблим кнопку, если поле не заполнено
        searchButton.disabled = !searchInput.value.trim();
    }

    // Проверка при вводе текста в поле
    searchInput.addEventListener("input", toggleButtonState);

    // Проверка при загрузке страницы заполнено ли поле
    toggleButtonState();
});