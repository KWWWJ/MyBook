const searchText = document.getElementById('search-text');

searchText.addEventListener('keydown', (e) => keydownSerchEvent(e))

function keydownSerchEvent(e) {
    if (e.key == 'Enter') {
        location.href = 'search?page=1&type=Title&search=' + searchText.value;
    }
}
