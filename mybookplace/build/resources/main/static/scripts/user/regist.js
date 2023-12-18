const signInBtn = document.getElementById("signIn");
const signUpBtn = document.getElementById("signUp");
const fistForm = document.getElementById("form1");
const secondForm = document.getElementById("form2");
const container = document.querySelector(".container");
const form = document.querySelector('submit-btn');

signInBtn.addEventListener("click", () => {
    container.classList.remove("right-panel-active");
});

signUpBtn.addEventListener("click", () => {
    container.classList.add("right-panel-active");
});

fistForm.addEventListener('submit', (e) => checkInfo(e))

function checkInfo(e) {
    const idReg = /^[a-z0-9]{3,20}$/i;
    const pwReg = /^[a-z](?=.*[\!\@\#\$\%\^\&\*])(?=.*[0-9]).{10,30}$/i;
    const koReg = /^[ㄱ-ㅎㅏ-ㅣ가-힣]/g;
    const emailReg = /^[A-Z0-9\.\_\%\+\-]+@[[A-Z0-9\.\-]+(.com|.net|.co.kr|.org)$/i;

    const tempName = e.target.name.value.replace(koReg, "aa");

    if (!idReg.test(e.target.userId.value)) {
        if (e.target.userId.value.length < 5 || e.target.userId.value.length > 20) {
            msg = "아이디는 6자를 넘어야 합니다.";
            e.preventDefault();
        } else {
            msg = "아이디에는 숫자와 영어만 입력할 수 있습니다.";
            e.preventDefault();
        }
    } else if (!pwReg.test(e.target.password.value)) {
        if (e.target.password.value.length < 7 || e.target.password.value.length > 30) {
            msg = "비밀번호의 길이는 8~30자 입니다.";
            e.preventDefault();
        } else {
            console.log(e.target.password.value)
            msg = "비밀번호에는 대소문자, 특수문자가 하나 이상씩 포함되어야합니다.";
            e.preventDefault();
        }
    } else if (tempName.length < 4 || tempName.length > 20) {
        msg = "이름의 길이는 한글 2~10자, 영문 4~20자 입니다.";
        e.preventDefault();
    } else if (!emailReg.test(e.target.email.value)) {
        msg = "이매일 형식에 맞춰주세요."
        e.preventDefault();
    }

    alert(msg);
}