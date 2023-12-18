const bookLink = document.querySelectorAll('.book-name');

function goBook(id, cid) {
    id = id.toString();
    console.log(id);
    bookLink.forEach(elem => {
        if (id.length == 13) {
            location.href = 'book?bookid=' + id;
        } else {
            location.href = 'book?bookidci=' + cid;
        }
    })
}

function goReview(id) {
    id = id.toString();
    bookLink.forEach(elem => {
        console.log("id : " + id)
        location.href = 'read?id=' + id;
    })
}
