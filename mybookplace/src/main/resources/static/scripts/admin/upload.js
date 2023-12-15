const fileUploadFormEvent = document.getElementById('upload-form-event');
const fileUploadFormBook = document.getElementById('upload-form-book');

fileUploadFormEvent.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(fileUploadFormEvent);

    try {
        const response = await fetch('/upload/event', {
            method: 'POST',
            body: formData,
        });

        const responseData = await response.json();

        if (responseData.success) {
            window.location.href = responseData.redirect;
        } else {
            console.error(responseData.error);
        }
    } catch (error) {
        console.error('An error occurred during file upload:', error);
    }
});

fileUploadFormBook.addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = new FormData(fileUploadFormBook);

    try {
        const response = await fetch('/upload/book', {
            method: 'POST',
            body: formData,
        });

        const responseData = await response.json();

        if (responseData.success) {
            window.location.href = responseData.redirect;
        } else {
            console.error(responseData.error);
        }
    } catch (error) {
        console.error('An error occurred during file upload:', error);
    }
});

function deleteFileEvent(item) {

    const file = item.split("/");

    if (confirm("파일을 삭제하시겠습니까?")) {
        axios.delete("/deleteEevnt/" + file[2])
            .then(response => {
                if (response.status === 200) {
                    alert("파일이 성공적으로 삭제되었습니다.");
                    window.location.href = '/upload';
                } else {
                    alert("파일 삭제에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error("파일 삭제 중 오류 발생:", error);
                alert("파일 삭제 중 오류가 발생했습니다.");
            });
    }
}

function deleteFileBook(item) {

    const file = item.split("/");

    if (confirm("파일을 삭제하시겠습니까?")) {
        axios.delete("/deleteBook/" + file[2])
            .then(response => {
                if (response.status === 200) {
                    alert("파일이 성공적으로 삭제되었습니다.");
                    window.location.href = '/upload';
                } else {
                    alert("파일 삭제에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error("파일 삭제 중 오류 발생:", error);
                alert("파일 삭제 중 오류가 발생했습니다.");
            });
    }
}