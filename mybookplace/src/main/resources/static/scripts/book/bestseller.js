const bestsellerArea = document.getElementById('bestseller-list');


const getBestseller = async (page) => {
    const bestsellerData = (await axios.get("getBestseller", { params: { page } })).data.item;
    return bestsellerData;
}

function setBestseller() {
    const currentUrl = window.location.href;
    const urlSearchParams = new URLSearchParams(new URL(currentUrl).search);
    const page = urlSearchParams.get('page');
    const bestseller = getBestseller(page);
    let count = 0;

    bestseller.then(item => {

        item.forEach(book => {
            const classDivElem = document.createElement('div');
            const listLiElem = document.createElement('li');
            const bookImgElem = document.createElement('img');
            bookImgElem.src = book.cover;
            count++;
            listLiElem.append(bookImgElem);
            classDivElem.append(listLiElem);
            bestsellerArea.append(classDivElem)

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

const bestsellerTable = document.getElementById('bestseller-table');

setBestseller();