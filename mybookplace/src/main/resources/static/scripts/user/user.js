const orderBtn = document.getElementsByClassName('order');
const orderId = document.querySelectorAll('.user-review-id');
const orderLike = document.querySelectorAll('.user-review-like');

orderBtn[0].children[0].addEventListener('click', function () {
    console.log('a')
    orderId.forEach(element => {
        element.style.display = 'block';
    });
    orderLike.forEach(element => {
        element.style.display = 'none';
    });
})

orderBtn[0].children[2].addEventListener('click', function () {
    console.log('b')
    orderId.forEach(element => {
        element.style.display = 'none';
    });
    orderLike.forEach(element => {
        element.style.display = 'block';
    });
})
