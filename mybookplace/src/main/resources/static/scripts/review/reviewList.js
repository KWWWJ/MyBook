const haertImg = document.querySelectorAll('.haert-img');
const likesBox = document.querySelectorAll('.likes-box');
const bookLink = document.querySelectorAll('.book-name');
const genreCategory = document.getElementById('genre-order');

likesBox.forEach(element => {
    element.addEventListener('mouseover', function () {
        haertImg.forEach(item => {
            if (item == element.children[0]) {
                item.src = "images/review/red-haert.png";
            }
        })
    })
    element.addEventListener('mouseout', function () {
        haertImg.forEach(item => {
            item.src = "images/review/haert.png";
        })
    })
});

function goBook(id) {
    id = id.toString();
    bookLink.forEach(elem => {
        if (id.length == 13) {
            location.href = 'book?bookid=' + id;
        } else {
            location.href = 'book?bookidci=' + id;
        }
    })
}

function goReview(id) {
    id = id.toString();
    bookLink.forEach(elem => {
        console.log("id : " + id)
        location.href = 'read?id=' + id;
    })
}


axios.get('json/category.json')
    .then(function (response) {
        const data = response.data;
        const categoryList = _.uniqBy(data, 'first');

        categoryList.forEach(item => {
            if (!(item.category == '교사를 위한 책' ||
                item.category == '해외소설' ||
                item.category == '그녀에게' ||
                item.category == '금강산 여행 가는 이에게')) {
                const pElem = document.createElement('p');
                pElem.innerHTML = item.category;
                genreCategory.append(pElem);

                pElem.addEventListener('click', function () {
                    location.href = "/review?page=1&order=id&genre=" + item.category;
                })
            }
        })

    })
