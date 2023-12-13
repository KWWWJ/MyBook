const bookListArea = document.getElementById('book-list');
const categoryListArea = document.getElementById('category-list');
const openCategory = document.getElementById('open-category');
const categoryNameBox = document.getElementById('category-name');
const orderBtnRrcently = document.getElementsByClassName('order-btn-recently');
const orderBtnBestseller = document.getElementsByClassName('order-btn-bestseller');

const currentUrl = window.location.href;
const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
const page = urlSearchParams.get('page');
const order = urlSearchParams.get('order');
const category = urlSearchParams.get('category');
const categoryName = urlSearchParams.get('categoryname');

axios.get('json/category.json')
    .then(function (response) {
        const data = response.data;
        const categoryList = _.uniqBy(data, 'first');
        const mainCategoryDivElem = document.createElement('div');
        categoryList.forEach((element) => {

            if (!(element.category == '교사를 위한 책' ||
                element.category == '해외소설' ||
                element.category == '그녀에게' ||
                element.category == '금강산 여행 가는 이에게')) {
                const categoryDivElem = document.createElement('div');
                const categoryPElem = document.createElement('p');

                categoryDivElem.className = 'main-category';

                categoryPElem.innerHTML = element.category;

                categoryDivElem.append(categoryPElem);
                mainCategoryDivElem.append(categoryDivElem);

                categoryDivElem.addEventListener('click', function () {
                    location.href = 'genre?page=1&order=' + order + '&categoryid=' + element.CID + '&categoryname=' + element.category;
                })
            }
        })

        categoryListArea.append(mainCategoryDivElem);
        mainCategoryDivElem.className = 'main-category-box';
    })
    .catch(function (error) {
        console.log(error);
    })

const getBookList = async (page, category, categoryId, order) => {
    if (categoryId == null) {
        const bookListData = (await axios.get('getBookList', { params: { page, category } })).data.item;
        return bookListData;
    } else {
        const bookListData = (await axios.get('getGenreList', { params: { page, order, categoryId } })).data.item;
        return bookListData;
    }
}

openCategory.addEventListener('mouseover', function () {
    categoryListArea.classList.add('on');
})
openCategory.addEventListener('mouseout', function () {
    categoryListArea.classList.remove('on');
})

function setBookList() {
    let categoryId = null;
    if (category == null) {
        categoryId = urlSearchParams.get('categoryid');
    }
    if (categoryName == "신간도서" && order == "Bestseller") {
        categoryNameBox.innerHTML = "베스트셀러"
    } else {
        categoryNameBox.innerHTML = categoryName;
    }
    if (order == "ItemNewAll") {
        const bookList = getBookList(page, category, categoryId, order);
        bookListCreate(bookList);
        orderBtnRrcently[0].style.color = 'black';
        orderBtnBestseller[0].style.color = 'rgb(209, 209, 209)';
    } else {
        const bookList = getBookList(page, category, categoryId, order);
        bookListCreate(bookList);
        orderBtnBestseller[0].style.color = 'black';
        orderBtnRrcently[0].style.color = 'rgb(209, 209, 209)';
    }

    orderBtnRrcently[0].addEventListener('click', function () {
        location.href = 'genre?page=1&order=ItemNewAll&categoryid=' + categoryId + '&categoryname=' + categoryName;
    })

    orderBtnBestseller[0].addEventListener('click', function () {
        location.href = 'genre?page=1&&order=Bestseller&categoryid=' + categoryId + '&categoryname=' + categoryName;
    })


}

function bookListCreate(bookList) {
    bookList.then(item => {

        item.forEach(book => {
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
            bookListArea.append(classDivElem);

            classDivElem.className = 'book-info';

            bookImgElem.addEventListener('click', function () {
                if (book.isbn13 != '') {
                    location.href = 'book?bookid=' + book.isbn13;
                } else {
                    location.href = 'book?bookidci=' + book.isbn;
                }

            })
        });
    });
}


setBookList();