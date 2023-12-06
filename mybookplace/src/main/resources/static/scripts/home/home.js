const carousel = new bootstrap.Carousel('#event');

let fixedContainer = document.getElementById('fixedContainer');
let lastScrollTop = 0;

function handleScroll() {
    let st = fixedContainer.scrollTop;

    // 이미지가 나타날 스크롤 위치
    let threshold1 = 500;
    let threshold2 = 1000;
    let threshold3 = 1500;

    if (st > lastScrollTop) {
        // 스크롤을 아래로 내릴 때
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
        // 스크롤을 위로 올릴 때
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