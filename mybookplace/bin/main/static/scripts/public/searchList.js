const bookListAreaPublic = document.getElementById('book-list');
const searchResult = document.getElementById('result');
const nonResult = document.getElementsByClassName('non-result');
const orderBtnTitle = document.getElementsByClassName('order-btn-title');
const orderBtnAuthor = document.getElementsByClassName('order-btn-author');

searchText.addEventListener('keydown', (e) => keydownSerchEvent(e))

const getSearchTitleData = async (page, search) => {
    const searchData = (await axios.get("searchBook", { params: { page, search } })).data.item;
    return searchData;
}

const getSearchAuthorData = async (page, search) => {
    const searchData = (await axios.get("searchAuthor", { params: { page, search } })).data.item;
    return searchData;
}

function setSearchList() {
    const currentUrl = window.location.href;
    const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
    const page = urlSearchParams.get('page');
    const search = urlSearchParams.get('search');
    const type = urlSearchParams.get('type');

    searchResult.innerHTML = search;

    if (type == "Title") {
        const titleList = getSearchTitleData(page, search);
        orderset(titleList, search);
        orderBtnTitle[0].style.color = 'black';
        orderBtnAuthor[0].style.color = 'rgb(209, 209, 209)';
    } else {
        const authorList = getSearchAuthorData(page, search);
        orderset(authorList, search);
        orderBtnAuthor[0].style.color = 'black';
        orderBtnTitle[0].style.color = 'rgb(209, 209, 209)';
    }

    orderBtnTitle[0].addEventListener('click', function () {
        location.href = 'search?page=1&type=Title&search=' + search;
    })

    orderBtnAuthor[0].addEventListener('click', function () {
        location.href = 'search?page=1&type=Author&search=' + search;
    })

}

function orderset(bookList, search) {
    bookList.then(item => {
        let count = 0;
        item.forEach(book => {
            count++
            const classDivElem = document.createElement('div');
            const infoDivElem = document.createElement('div');
            const bookImgElem = document.createElement('img');
            bookImgElem.src = book.cover;
            infoDivElem.innerHTML = '제목 : ' + book.title
                + '<br/>' + '<br/>' + '작가 : ' + book.author
                + '<br/>' + '출판사 : ' + book.publisher
                + '<br/>' + '출행일 : ' + book.pubDate;
            classDivElem.append(bookImgElem);
            classDivElem.append(infoDivElem);
            bookListAreaPublic.append(classDivElem);

            classDivElem.className = 'book-info';

            bookImgElem.addEventListener('click', function () {
                if (book.isbn13 != '') {
                    location.href = 'book?bookid=' + book.isbn13;
                } else {
                    location.href = 'book?bookidci=' + book.isbn;
                }

            })
        });
        if (item == "") {
            nonResult[0].innerHTML = "'" + search + "'의 검색 결과가 없습니다.";
        }

    });
}




setSearchList();

