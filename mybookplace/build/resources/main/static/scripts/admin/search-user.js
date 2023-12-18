const userListArea = document.getElementById('user-list');
const searchUser = document.getElementById('search-user');

const getUser = async (name) => {
    console.log(name)
    const userList = (await axios.post('getUser', null, { params: { 'name': name } })).data;
    console.log(userList)
    return userList;
}

searchUser.addEventListener('keydown', (e) => keydownEvent(e));

function keydownEvent(e) {
    if (e.keyCode == 13 || e.key == 'Enter') {
        setUser();
    }
}

function setUser() {
    const name = searchUser.value;
    const user = getUser(name);

    user.then(item => {

        item.forEach(user => {
            const classDivElem = document.createElement('div');
            const listLiElem = document.createElement('li');
            const adminBtnElem = document.createElement('button');
            const banBtnElem = document.createElement('button');

            listLiElem.innerHTML = 'Name : ' + user.name + ' | ID : ' + user.id + ' | Email : ' + user.email + ' | admin : ' + user.admin + ' | ban : ' + user.ban;
            adminBtnElem.innerHTML = 'admin';
            adminBtnElem.className = 'admin-btn';
            banBtnElem.innerHTML = 'ban';
            banBtnElem.className = 'ban-btn';
            classDivElem.className = 'user-info-box';

            classDivElem.append(listLiElem);
            classDivElem.append(banBtnElem);
            classDivElem.append(adminBtnElem);
            userListArea.append(classDivElem);
            adminBtnElem.addEventListener('click', function () {
                userAdmin(user.id);
            })
            banBtnElem.addEventListener('click', function () {
                userBan(user.id);
            })
        });
    });
}

