const bookListArea = document.getElementById('book-list');
const categoryListArea = document.getElementById('category-list');
const openCategory = document.getElementById('open-category');
const categoryNameBox = document.getElementById('category-name');

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
                    location.href = 'genre?page=1&categoryid=' + element.CID + '&categoryname=' + element.category;
                })
            }
        })

        categoryListArea.append(mainCategoryDivElem);
        mainCategoryDivElem.className = 'main-category-box';
    })
    .catch(function (error) {
        console.log(error);
    })

const getBookList = async (page, category, categoryId) => {
    if (categoryId == null) {
        const bookListData = (await axios.get('getBookList', { params: { page, category } })).data.item;
        return bookListData;
    } else {
        const bookListData = (await axios.get('getGenreList', { params: { page, categoryId } })).data.item;
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
    const currentUrl = window.location.href;
    const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
    const page = urlSearchParams.get('page');
    const category = urlSearchParams.get('category');
    const categoryName = urlSearchParams.get('categoryname');
    let categoryId = null;
    if (category == null) {
        categoryId = urlSearchParams.get('categoryid');
    }
    const bookList = getBookList(page, category, categoryId);

    categoryNameBox.innerHTML = categoryName;

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