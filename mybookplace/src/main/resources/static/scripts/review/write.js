const writeArea = document.getElementById('write-area');
const bookTitle = document.getElementsByClassName('book-name');
const searchArea = document.getElementById('search-book-area');

function autoResize(textarea) {
    textarea.style.height = 'auto';
    textarea.style.height = textarea.scrollHeight + 'px';
}



let inputBookName = null;

function searchOn() {
    bookTitle[0].addEventListener('keydown', function (e) {
        // writeArea.onsubmit = (e) => {
        //     e.preventDefault();
        // }
        if (e.keyCode == 13) {
            searchArea.style.display = 'flex';
            inputBookName = document.getElementById('book-title').value;
            setSearchData();
            e.preventDefault();
            e.stopPropagation();
        }
    })
}

searchOn();


const getSearchData = async (bookTitle) => {
    const searchData = (await axios.get("searchBook", { params: { bookTitle } })).data.item;
    return searchData;
}
function getCategoey(cid) {
    axios.get('json/category.json')
        .then(function (response) {
            const data = response.data;
            const categoryList = _.uniqBy(data, 'first');
            categoryList.forEach((element) => {

                if (!(element.category == '교사를 위한 책' ||
                    element.category == '해외소설' ||
                    element.category == '그녀에게' ||
                    element.category == '금강산 여행 가는 이에게')) {

                    const splitCid = cid.split('>');
                    if (element.category == splitCid[1]) {
                        document.getElementById('genre').value = element.category;
                    }
                }
            })
        })
        .catch(function (error) {
            console.log(error);
        })
}


function setSearchData() {
    const allLiElem = document.querySelectorAll('li');
    allLiElem.forEach(function (liElem) {
        liElem.remove();
    })

    const bookData = getSearchData(inputBookName);
    bookData.then(item => {

        item.forEach(book => {
            const bookImgElem = document.createElement('img');
            const bookDescriptionPElem = document.createElement('p');
            const listLiElem = document.createElement('li');
            bookImgElem.src = book.cover;
            bookDescriptionPElem.innerHTML = '제목 : ' + book.title
                + '<br/>' + '<br/>' + '작가 : ' + book.author
                + '<br/>' + '출판사 : ' + book.publisher
                + '<br/>' + '출행일 : ' + book.pubDate;
            listLiElem.append(bookImgElem);
            listLiElem.append(bookDescriptionPElem);
            searchArea.append(listLiElem)

            listLiElem.addEventListener('click', function () {
                if (book.isbn13 != "") {
                    document.getElementById('book-id').value = book.isbn13;
                    getCategoey(book.categoryName);
                    document.getElementById('book-title').value = book.title;
                    searchArea.style.display = 'none';
                } else {
                    document.getElementById('book-id').value = book.isbn;
                    getCategoey(book.categoryName);
                    document.getElementById('book-title').value = book.title;
                    searchArea.style.display = 'none';
                }
            })
        });
    });
}

