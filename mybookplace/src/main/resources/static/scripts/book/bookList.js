const bookListArea = document.getElementById('book-list');


const getBookList = async (page, category) => {
    console.log("page : " + page);
    console.log("menu : " + category);
    const bookListData = (await axios.get("getBookList", { params: { page, category } })).data.item;
    return bookListData;
}

function setBestseller() {
    const currentUrl = window.location.href;
    const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
    const page = urlSearchParams.get('page');
    const category = urlSearchParams.get('category');
    const bookList = getBookList(page, category);
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


setBestseller();