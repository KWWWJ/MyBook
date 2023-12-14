const banBtn = document.getElementById('ban-btn');

const userBan = async (id) => {
    await axios.post("banUser", null, { params: { "id": id } });
}

const reviewBan = async (id) => {
    await axios.post("banReview", null, { params: { "id": id } });
}