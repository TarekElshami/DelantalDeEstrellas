let chefItems = document.getElementsByClassName("chef-item");
const popup = document.getElementById("popup-window");
const popupTitle = document.getElementById('chef-name');
const popupDescription = document.getElementById('chef-description');
const popupImage = document.getElementById('chef-image');
const popupBtn = document.getElementById('popup-btn');
const closeBtn = document.getElementById('clsBtn');
const showMore = document.getElementById('showMore');

const closePopUp = () => {
    popup.style.display = "none"
}

for (const chefItem of chefItems) {
    chefItem.addEventListener('click', (e) => {
            let element = e.target.closest('.chef-item')
            let img = element.querySelector('img').getAttribute('src')
            console.log(img)
            let name = element.getAttribute('data-name')
            let description = element.getAttribute('data-description')
            popupBtn.setAttribute('data-id', element.getAttribute('data-id'))
            popupTitle.textContent = name;
            popupImage.src = img;
            popupDescription.textContent = description;
            popup.style.display = "block"
        }
    )
}

const closePopUpBtn = document.getElementById("close-popup");
closePopUpBtn.addEventListener("click", () => {
    closePopUp();
})

closeBtn.addEventListener('click', () => {
    closePopUp();
})

popupBtn.addEventListener('click', () => {
    let id = popupBtn.getAttribute('data-id')
    window.location.href = '/chef/' + id;
})

window.addEventListener('click', (e) => {e.target === popup && closePopUp()})

showMore.addEventListener('click', () => {
    let page = showMore.getAttribute('data-nextPage');
    fetch(`/loadMoreChefs?page=`+page)
        .then(response => response.text())
        .then(html => {
            document.getElementById('chef-container').insertAdjacentHTML('beforeend', html);
        });
    if (!(document.getElementById('noMore') === null)){
        showMore.style.display = "none";
    }
    let nextPage = parseInt(page,10) + 1;
    showMore.setAttribute("data-nextPage",nextPage);
})


