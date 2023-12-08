const bookListArea = document.getElementById('book-list');
const categoryListArea = document.getElementById('category-list');

axios.get('json/category.json')
    .then(function (response) {
        const data = response.data;
        const categoryList = _.uniqBy(data, 'first');
        categoryList.forEach((element) => {
            const categoryDivElem = document.createElement('div');
            const boxDivElem = document.createElement('div');
            categoryDivElem.innerHTML = element.category;
            data.forEach((item) => {
                if (item.first == element.category) {
                    const sudCategoriesLiElem = document.createElement('li');
                    sudCategoriesLiElem.innerHTML = item.category;
                    boxDivElem.append(sudCategoriesLiElem);

                    sudCategoriesLiElem.addEventListener('click', function () {
                        location.href = "genre?page=1&categoryid=" + item.CID;
                        document.getElementsByClassName('category-name')[0].innerHTML = item.category;
                    })
                    categoryDivElem.addEventListener('mouseover', function () {
                        sudCategoriesLiElem.style.display = "block";
                        boxDivElem.style.display = "flex";
                    })
                    categoryDivElem.addEventListener('mouseout', function () {
                        sudCategoriesLiElem.style.display = "none";
                        boxDivElem.style.display = "none";
                    })
                    boxDivElem.className = 'sub-category';

                }
            })
            categoryDivElem.append(boxDivElem);
            categoryListArea.append(categoryDivElem);

            categoryDivElem.className = "main-category";


        })
    })
    .catch(function (error) {
        console.log(error);
    })

const getBookList = async (page, category, categoryId) => {
    console.log("page : " + page);
    console.log("category : " + category);
    console.log("categoryId : " + categoryId);
    if (categoryId == null) {
        const bookListData = (await axios.get("getBookList", { params: { page, category } })).data.item;
        return bookListData;
    } else {
        const bookListData = (await axios.get("getGenreList", { params: { page, categoryId } })).data.item;
        return bookListData;
    }
}

function setBookList() {
    const currentUrl = window.location.href;
    const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
    const page = urlSearchParams.get('page');
    const category = urlSearchParams.get('category');
    let categoryId = null;
    if (category == null) {
        categoryId = urlSearchParams.get('categoryid');
    }
    const bookList = getBookList(page, category, categoryId);
    let count = 0;

    bookList.then(item => {

        item.forEach(book => {
            const classDivElem = document.createElement('div');
            const infoDivElem = document.createElement('div');
            const bookImgElem = document.createElement('img');
            bookImgElem.src = book.cover;
            infoDivElem.innerHTML = "제목 : " + book.title
                + "<br/>" + "<br/>" + "작가 : " + book.author
                + "<br/>" + "출판사 : " + book.publisher
                + "<br/>" + "출행일 : " + book.pubDate;
            count++;
            classDivElem.append(bookImgElem);
            classDivElem.append(infoDivElem);
            bookListArea.append(classDivElem);

            classDivElem.className = 'book-info';

            bookImgElem.addEventListener('click', function () {
                if (book.isbn13 != "") {
                    location.href = "book?bookid=" + book.isbn13;
                } else {
                    location.href = "book?bookidci=" + book.isbn;
                }

            })
        });
    });
}


setBookList();