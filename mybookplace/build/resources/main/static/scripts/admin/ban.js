const userBan = async (id) => {
    await axios.post("banUser", null, { params: { "id": id } });
    alert('유저가 차단되었습니다.')
}

const reviewBan = async (id) => {
    await axios.post("banReview", null, { params: { "id": id } });
    alert('리뷰가 가려졌습니다.')
}