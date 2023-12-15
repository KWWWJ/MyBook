const carousel = new bootstrap.Carousel('#event');
const slideFirst = document.getElementsByClassName('carousel-item');

let fixedContainer = document.getElementById('fixedContainer');
let lastScrollTop = 0;

function handleScroll() {
    let st = fixedContainer.scrollTop;

    let threshold1 = 500;
    let threshold2 = 1000;
    let threshold3 = 1500;

    if (st > lastScrollTop) {
        if (st > threshold1) {
            showImage('.main_tit03');
        }
        if (st > threshold2) {
            showImage('.main_tit02');
        }
        if (st > threshold3) {
            showImage('.main_tit01');
        }
    } else {
        if (st <= threshold3) {
            hideImage('.main_tit01');
        }
        if (st <= threshold2) {
            hideImage('.main_tit02');
        }
        if (st <= threshold1) {
            hideImage('.main_tit03');
        }
    }

    lastScrollTop = st;
}

function showImage(selector) {
    document.querySelector(selector).classList.add('show');
}

function hideImage(selector) {
    document.querySelector(selector).classList.remove('show');
}

fixedContainer.addEventListener('scroll', handleScroll);

slideFirst[0].classList = 'carousel-item active';