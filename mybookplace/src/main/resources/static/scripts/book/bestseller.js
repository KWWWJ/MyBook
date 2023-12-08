const bestsellerArea = document.getElementById('bestseller-list');
const bestsellerTable = document.getElementById('bestseller-table');

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

let wrapper = document.querySelector(".bestseller-table-wrapper");

window.addEventListener("scroll", function () {
    // 스크롤이 일정 길이 이상이면 클래스 변경
    let maxScrollHeight = document.documentElement.scrollHeight - window.innerHeight; // 스크롤의 일정 길이 (원하는 값으로 변경)
    console.log(maxScrollHeight)

    // 일정 길이 이상인 경우 클래스 변경
    if (maxScrollHeight > 7000) {
        alert("1");
        bestsellerTable.classList.add("height");
    } else {
        bestsellerTable.classList.remove("height-1");
    }
    if (maxScrollHeight < 7000 && maxScrollHeight > 6660) {
        alert("2");
        bestsellerTable.classList.add("height-1");
    } else {
        bestsellerTable.classList.remove("height");
        bestsellerTable.classList.remove("height-2");
    }
    if (maxScrollHeight < 6660 && maxScrollHeight > 6510) {
        alert("3");
        bestsellerTable.classList.add("height-2");
    } else {
        bestsellerTable.classList.remove("height-1");
        bestsellerTable.classList.remove("height-3");
    }
    if (maxScrollHeight < 6510 && maxScrollHeight > 6050) {
        alert("4");
        bestsellerTable.classList.add("height-3");
    } else {
        bestsellerTable.classList.remove("height-2");
        bestsellerTable.classList.remove("height-4");
    }
    if (maxScrollHeight < 6050 && maxScrollHeight > 5139) {
        alert("5");
        bestsellerTable.classList.add("height-4");
    } else {
        bestsellerTable.classList.remove("height-3");
        bestsellerTable.classList.remove("height-5");
    }
    if (maxScrollHeight < 5139 && maxScrollHeight > 4228) {
        alert("6");
        bestsellerTable.classList.add("height-5");
    } else {
        bestsellerTable.classList.remove("height-4");
    }
});

setBestseller();