const genreCategory = document.getElementById('genre-order');
const haertImg = document.getElementsByClassName('haert-img');
let likesCountShow = document.getElementsByClassName('read-likes')[0];

function goBook(id) {
    id = id.toString();
    console.log('id length : ' + id.length);
    bookLink.forEach(elem => {
        if (id.length == 13) {
            console.log('id test : up');
            location.href = 'book?bookid=' + id;
        } else {
            console.log('id test : down');
            location.href = 'book?bookidci=' + id;
        }
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

const isLikes = haertImg[0].src;

const likesSplit = isLikes.split('/');

let tempLikes = likesSplit[5];


haertImg[0].addEventListener('click', function () {
    if (tempLikes == "haert.png") {
        haertImg[0].src = "images/review/red-haert.png";
        likesCountShow.innerHTML = parseInt(likesCountShow.innerHTML) + 1;
    } else {
        haertImg[0].src = "images/review/haert.png";
        likesCountShow.innerHTML = parseInt(likesCountShow.innerHTML) - 1;
        tempLikes = "haert.png";
    }

    likesCount(getId(), isLikes);

})

const likesCount = async (id) => {
    const likes = (await axios.get('likes', { params: { id } })).data.item;
    return likes;
}

function getId() {
    const currentUrl = window.location.href;
    const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
    const id = urlSearchParams.get('id');
    return id
}