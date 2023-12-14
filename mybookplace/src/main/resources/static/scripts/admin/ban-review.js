const banListArea = document.getElementById('ban-list');
const searchReview = document.getElementById('search-user');

const getBanReview = async (id) => {
    const banList = (await axios.post("getBanReview", null, { params: { "id": id } })).data;
    return banList;
}

searchReview.addEventListener('keydown', (e) => keydownEvent(e));

function keydownEvent(e) {
    if (e.keyCode == 13 || e.key == 'Enter') {
        setBanReview();
    }
}

function setBanReview() {
    const id = searchReview.value;
    const banReview = getBanReview(id);
    banReview.then(item => {
        if (item != "redirect:/ban") {
            item.forEach(review => {
                const classDivElem = document.createElement('div');
                const listLiElem = document.createElement('li');
                listLiElem.innerHTML = review.title
                    + "<br/>" + review.bookName;
                classDivElem.append(listLiElem);
                banListArea.append(classDivElem)

                listLiElem.className = 'ban-items';

                listLiElem.addEventListener('click', function () {
                    location.href = 'read?id=' + review.id;
                })
            });
        }
    });
}

