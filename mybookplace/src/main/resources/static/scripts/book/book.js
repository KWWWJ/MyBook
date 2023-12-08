const bookCover = document.getElementById('book-cover');
const bookDescription = document.getElementById('book-description');
const bookInfo = document.getElementById('book-info');
const bookReview = document.getElementById('book-review');
const bestsellerTable = document.getElementById('bestseller-table');

const getbook = async (bookId) => {
    let bookData = null;
    if (bookId.length === 13) {
        bookData = (await axios.get("getbook", { params: { bookId } })).data.item;
    } else {
        const bookIdCI = bookId;
        bookData = (await axios.get("getbookCI", { params: { bookIdCI } })).data.item;
    }
    return bookData;
}

function setBook() {
    const currentUrl = window.location.href;
    const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
    let bookId = urlSearchParams.get('bookid');
    if (bookId == null) {
        bookId = urlSearchParams.get('bookidci');
    }

    const book = getbook(bookId);

    book.then(item => {

        item.forEach(book => {
            const bookImgElem = document.createElement('img');
            bookImgElem.src = book.cover;
            bookCover.append(bookImgElem);
            bookDescription.innerHTML = book.description;
            bookInfo.innerHTML = "작가 : " + book.author
                + "<br/>" + "출판사 : " + book.publisher
                + "<br/>" + "출행일 : " + book.pubDate;
        });
    });
}

setBook();