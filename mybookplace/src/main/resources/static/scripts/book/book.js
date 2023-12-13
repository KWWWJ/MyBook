const bookCover = document.getElementById('book-cover');
const bookDescription = document.getElementById('book-description');
const bookInfo = document.getElementById('book-info');
const bookReview = document.getElementById('book-review');
const bestsellerTable = document.getElementById('bestseller-table');
const bookTitle = document.getElementsByClassName('book-title');
const reviewList = document.getElementById('book-review');
const currentUrl = window.location.href;
const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
let bookId = urlSearchParams.get('bookid');

let bookCid = urlSearchParams.get('bookidci');


const getBook = async (bookId, bookCid) => {
    let bookData = null;
    if (bookId != null) {
        bookData = (await axios.get("getbook", { params: { bookId } })).data.item;
    } else {
        bookData = (await axios.get("getbookCI", { params: { bookCid } })).data.item;
    }
    return bookData;
}

function setBook() {
    const book = getBook(bookId, bookCid);

    book.then(item => {

        item.forEach(book => {
            const bookImgElem = document.createElement('img');
            bookImgElem.src = book.cover;
            bookCover.append(bookImgElem);
            bookDescription.innerHTML = book.description;
            bookTitle[0].innerHTML = book.title
            bookInfo.innerHTML = "제목 : " + book.title
                + "<br/>" + "작가 : " + book.author
                + "<br/>" + "출판사 : " + book.publisher
                + "<br/>" + "출행일 : " + book.pubDate;
        });
    });
}

setBook();

const getReview = async (bookId, bookCid) => {
    let reviewData = null;
    if (bookId != null) {
        reviewData = (await axios.get("bookReviewList", { params: { bookId } })).data;
    } else {
        reviewData = (await axios.get("bookReviewListCI", { params: { bookCid } })).data;
    }
    console.log(reviewData)
    return reviewData;
}

function setReview() {
    const review = getReview(bookId, bookCid);
    console.log(review)
    review.then(item => {
        item.forEach(review => {
            const reviewAteaDivElem = document.createElement('div');
            const reviewPElem = document.createElement('p');

            reviewPElem.innerHTML = review.userName
                + "<br/>" + review.title + review.likes;
            reviewAteaDivElem.append(reviewPElem);
            bookReview.append(reviewAteaDivElem);
            reviewAteaDivElem.className = 'review-div';
            reviewAteaDivElem.addEventListener('click', function () {
                location.href = 'read?id=' + review.id;
            })
        })
    })
}

setReview();